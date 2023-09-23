package com.kingmang.lazurite.modules.Reflection;

import com.kingmang.lazurite.base.*;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class Reflection implements Module {

    private static final Value NULL = new NullValue();



    @Override
    public void init() {

        KEYWORD.put("isNull", this::isNull);
        KEYWORD.put("JClass", this::JClass);
        KEYWORD.put("JObject", this::JObject);
        KEYWORD.put("LzrValue", this::LzrValue);
    }


    private static class NullValue implements Value {

        @Override
        public Object raw() {
            return null;
        }

        @Override
        public int asInt() {
            return 0;
        }

        @Override
        public double asNumber() {
            return 0;
        }

        @Override
        public String asString() {
            return "null";
        }

        @Override
        public int type() {
            return 482862660;
        }

        @Override
        public int compareTo(Value o) {
            if (o.raw() == null) return 0;
            return -1;
        }

        @Override
        public String toString() {
            return asString();
        }
    }

    private static class ClassValue extends LZRMap implements Instantiable {

        public static Value classOrNull(Class<?> clazz) {
            if (clazz == null) return NULL;
            return new ClassValue(clazz);
        }

        private final Class<?> clazz;

        public ClassValue(Class<?> clazz) {
            super(25);
            this.clazz = clazz;
            init(clazz);
        }

        private void init(Class<?> clazz) {
            set("isAnnotation", LZRNumber.fromBoolean(clazz.isAnnotation()));
            set("isAnonymousClass", LZRNumber.fromBoolean(clazz.isAnonymousClass()));
            set("isArray", LZRNumber.fromBoolean(clazz.isArray()));
            set("isEnum", LZRNumber.fromBoolean(clazz.isEnum()));
            set("isInterface", LZRNumber.fromBoolean(clazz.isInterface()));
            set("new", new LZRFunction(this::newInstance));

        }


        @Override
        public Value newInstance(Value[] args) {
            return findConstructorAndInstantiate(args, clazz.getConstructors());
        }



        @Override
        public boolean containsKey(Value key) {
            return getValue(clazz, null, key.asString()) != null;
        }

        @Override
        public Value get(Value key) {
            if (super.containsKey(key)) {
                return super.get(key);
            }
            return getValue(clazz, null, key.asString());
        }

        @Override
        public String toString() {
            return "ClassValue " + clazz.toString();
        }
    }

    private static class ObjectValue extends LZRMap {



        private final Object object;

        public ObjectValue(Object object) {
            super(2);
            this.object = object;
        }

        @Override
        public boolean containsKey(Value key) {
            return get(key) != null;
        }

        @Override
        public Value get(Value key) {
            return getValue(object.getClass(), object, key.asString());
        }

        @Override
        public String asString() {
            return object.toString();
        }

        @Override
        public String toString() {
            return "ObjectValue " + asString();
        }
    }


    private Value isNull(Value[] args) {
        Arguments.checkAtLeast(1, args.length);
        for (Value arg : args) {
            if (arg.raw() == null) return LZRNumber.ONE;
        }
        return LZRNumber.ZERO;
    }

    private Value JClass(Value[] args) {
        Arguments.check(1, args.length);

        final String className = args[0].asString();
        try {
            return new ClassValue(Class.forName(className));
        } catch (ClassNotFoundException ce) {
            throw new RuntimeException("Class " + className + " not found.", ce);
        }
    }

    private Value JObject(Value[] args) {
        Arguments.check(1, args.length);
        if (args[0] == NULL) return NULL;
        return new ObjectValue(valueToObject(args[0]));
    }

    private Value LzrValue(Value[] args) {
        Arguments.check(1, args.length);
        if (args[0] instanceof ObjectValue) {
            return objectToValue( ((ObjectValue) args[0]).object );
        }
        return NULL;
    }



    private static Value getValue(Class<?> clazz, Object object, String key) {
        // Trying to get field
        try {
            final Field field = clazz.getField(key);
            return objectToValue(field.getType(), field.get(object));
        } catch (NoSuchFieldException | SecurityException |
                IllegalArgumentException | IllegalAccessException ex) {
            // ignore and go to the next step
        }

        // Trying to invoke method
        try {
            final Method[] allMethods = clazz.getMethods();
            final List<Method> methods = new ArrayList<>();
            for (Method method : allMethods) {
                if (method.getName().equals(key)) {
                    methods.add(method);
                }
            }
            if (methods.isEmpty()) {
                return LZRFunction.EMPTY;
            }
            return new LZRFunction(methodsToFunction(object, methods));
        } catch (SecurityException ex) {
            // ignore and go to the next step
        }

        return NULL;
    }
    
    private static Value findConstructorAndInstantiate(Value[] args, Constructor<?>[] ctors) {
        for (Constructor<?> ctor : ctors) {
            if (ctor.getParameterCount() != args.length) continue;
            if (!isMatch(args, ctor.getParameterTypes())) continue;
            try {
                final Object result = ctor.newInstance(valuesToObjects(args));
                return new ObjectValue(result);
            } catch (InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                // skip
            }
        }
        throw new RuntimeException("Constructor for " + args.length + " arguments"
                + " not found or non accessible");
    }

    private static Function methodsToFunction(Object object, List<Method> methods) {
        return (args) -> {
            for (Method method : methods) {
                if (method.getParameterCount() != args.length) continue;
                if (!isMatch(args, method.getParameterTypes())) continue;
                try {
                    final Object result = method.invoke(object, valuesToObjects(args));
                    if (method.getReturnType() != void.class) {
                        return objectToValue(result);
                    }
                    return LZRNumber.ONE;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    // skip
                }
            }
            final String className = (object == null ? "null" : object.getClass().getName());
            throw new RuntimeException("Method for " + args.length + " arguments"
                + " not found or non accessible in " + className);
        };
    }

    private static boolean isMatch(Value[] args, Class<?>[] types) {
        for (int i = 0; i < args.length; i++) {
            final Value arg = args[i];
            final Class<?> clazz = types[i];

            if (arg == NULL) continue;

            final Class<?> unboxed = unboxed(clazz);
            boolean assignable = unboxed != null;
            final Object object = valueToObject(arg);
            assignable = assignable && (object != null);
            if (assignable && unboxed.isArray() && object.getClass().isArray()) {
                final Class<?> uComponentType = unboxed.getComponentType();
                final Class<?> oComponentType = object.getClass().getComponentType();
                assignable = assignable && (uComponentType != null);
                assignable = assignable && (oComponentType != null);
                assignable = assignable && (uComponentType.isAssignableFrom(oComponentType));
            } else {
                assignable = assignable && (unboxed.isAssignableFrom(object.getClass()));
            }
            if (assignable) continue;

            return false;
        }
        return true;
    }

    private static Class<?> unboxed(Class<?> clazz) {
        if (clazz == null) return null;
        if (clazz.isPrimitive()) {
            if (int.class == clazz) return Integer.class;
            if (boolean.class == clazz) return Boolean.class;
            if (double.class == clazz) return Double.class;
            if (float.class == clazz) return Float.class;
            if (long.class == clazz) return Long.class;
            if (byte.class == clazz) return Byte.class;
            if (char.class == clazz) return Character.class;
            if (short.class == clazz) return Short.class;
            if (void.class == clazz) return Void.class;
        }
        return clazz;
    }



    private static Value objectToValue(Object o) {
        if (o == null) return NULL;
        return objectToValue(o.getClass(), o);
    }

    private static Value objectToValue(Class<?> clazz, Object o) {
        if (o == null || o == NULL) return NULL;
        if (clazz.isPrimitive()) {
            if (int.class.isAssignableFrom(clazz))
                return LZRNumber.of((int) o);
            if (boolean.class.isAssignableFrom(clazz))
                return LZRNumber.fromBoolean((boolean) o);
            if (double.class.isAssignableFrom(clazz))
                return LZRNumber.of((double) o);
            if (float.class.isAssignableFrom(clazz))
                return LZRNumber.of((float) o);
            if (long.class.isAssignableFrom(clazz))
                return LZRNumber.of((long) o);
            if (byte.class.isAssignableFrom(clazz))
                return LZRNumber.of((byte) o);
            if (char.class.isAssignableFrom(clazz))
                return LZRNumber.of((char) o);
            if (short.class.isAssignableFrom(clazz))
                return LZRNumber.of((short) o);
        }
        if (Number.class.isAssignableFrom(clazz)) {
            return LZRNumber.of((Number) o);
        }
        if (String.class.isAssignableFrom(clazz)) {
            return new LZRString((String) o);
        }
        if (CharSequence.class.isAssignableFrom(clazz)) {
            return new LZRString( ((CharSequence) o).toString() );
        }
        if (o instanceof Value) {
            return (Value) o;
        }
        if (clazz.isArray()) {
            return arrayToValue(clazz, o);
        }
        final Class<?> componentType = clazz.getComponentType();
        if (componentType != null) {
            return objectToValue(componentType, o);
        }
        return new ObjectValue(o);
    }

    private static Value arrayToValue(Class<?> clazz, Object o) {
        final int length = Array.getLength(o);
        final LZRArray result = new LZRArray(length);
        final Class<?> componentType = clazz.getComponentType();
        int i = 0;
        if (boolean.class.isAssignableFrom(componentType)) {
            for (boolean element : (boolean[]) o) {
                result.set(i++, LZRNumber.fromBoolean(element));
            }
        } else if (byte.class.isAssignableFrom(componentType)) {
            for (byte element : (byte[]) o) {
                result.set(i++, LZRNumber.of(element));
            }
        } else if (char.class.isAssignableFrom(componentType)) {
            for (char element : (char[]) o) {
                result.set(i++, LZRNumber.of(element));
            }
        } else if (double.class.isAssignableFrom(componentType)) {
            for (double element : (double[]) o) {
                result.set(i++, LZRNumber.of(element));
            }
        } else if (float.class.isAssignableFrom(componentType)) {
            for (float element : (float[]) o) {
                result.set(i++, LZRNumber.of(element));
            }
        } else if (int.class.isAssignableFrom(componentType)) {
            for (int element : (int[]) o) {
                result.set(i++, LZRNumber.of(element));
            }
        } else if (long.class.isAssignableFrom(componentType)) {
            for (long element : (long[]) o) {
                result.set(i++, LZRNumber.of(element));
            }
        } else if (short.class.isAssignableFrom(componentType)) {
            for (short element : (short[]) o) {
                result.set(i++, LZRNumber.of(element));
            }
        } else {
            for (Object element : (Object[]) o) {
                result.set(i++, objectToValue(element));
            }
        }
        return result;
    }
    
    private static Object[] valuesToObjects(Value[] args) {
        Object[] result = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            result[i] = valueToObject(args[i]);
        }
        return result;
    }

    private static Object valueToObject(Value value) {
        if (value == NULL) return null;
        switch (value.type()) {
            case Types.NUMBER:
                return value.raw();
            case Types.STRING:
                return value.asString();
            case Types.ARRAY:
                return arrayToObject((LZRArray) value);
        }
        if (value instanceof ObjectValue) {
            return ((ObjectValue) value).object;
        }
        if (value instanceof ClassValue) {
            return ((ClassValue) value).clazz;
        }
        return value.raw();
    }

    private static Object arrayToObject(LZRArray value) {
        final int size = value.size();
        final Object[] array = new Object[size];
        if (size == 0) {
            return array;
        }
        
        Class<?> elementsType = null;
        for (int i = 0; i < size; i++) {
            array[i] = valueToObject(value.get(i));
            if (i == 0) {
                elementsType = array[0].getClass();
            } else {
                elementsType = mostCommonType(elementsType, array[i].getClass());
            }
        }
        
        if (elementsType.equals(Object[].class)) {
            return array;
        }
        return typedArray(array, size, elementsType);
    }
    
    private static <T, U> T[] typedArray(U[] elements, int newLength, Class<?> elementsType) {
        @SuppressWarnings("unchecked")
        T[] copy = (T[]) Array.newInstance(elementsType, newLength);
        System.arraycopy(elements, 0, copy, 0, Math.min(elements.length, newLength));
        return copy;
    }
    
    private static Class<?> mostCommonType(Class<?> c1, Class<?> c2) {
        if (c1.equals(c2)) {
            return c1;
        } else if (c1.isAssignableFrom(c2)) {
            return c1;
        } else if (c2.isAssignableFrom(c1)) {
            return c2;
        }
        final Class<?> s1 = c1.getSuperclass();
        final Class<?> s2 = c2.getSuperclass();
        if (s1 == null && s2 == null) {
            final List<Class<?>> upperTypes = Arrays.asList(
                    Object.class, void.class, boolean.class, char.class,
                    byte.class, short.class, int.class, long.class,
                    float.class, double.class);
            for (Class<?> type : upperTypes) {
                if (c1.equals(type) && c2.equals(type)) {
                    return s1;
                }
            }
            return Object.class;
        } else if (s1 == null || s2 == null) {
            if (c1.equals(c2)) {
                return c1;
            }
            if (c1.isInterface() && c1.isAssignableFrom(c2)) {
                return c1;
            }
            if (c2.isInterface() && c2.isAssignableFrom(c1)) {
                return c2;
            }
        }
        
        if (s1 != null) {
            return mostCommonType(s1, c2);
        } else {
            return mostCommonType(c1, s2);
        }
    }

}
