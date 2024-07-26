package com.kingmang.lazurite.libraries.lzr.lang.reflection;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Instantiable;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public final class reflection implements Library {
    @Override
    public void init() {

        Variables.define("boolean.class", new JavaClassValue(boolean.class));
        Variables.define("boolean[].class", new JavaClassValue(boolean[].class));
        Variables.define("boolean[][].class", new JavaClassValue(boolean[][].class));
        Variables.define("byte.class", new JavaClassValue(byte.class));
        Variables.define("byte[].class", new JavaClassValue(byte[].class));
        Variables.define("byte[][].class", new JavaClassValue(byte[][].class));
        Variables.define("short.class", new JavaClassValue(short.class));
        Variables.define("short[].class", new JavaClassValue(short[].class));
        Variables.define("short[][].class", new JavaClassValue(short[][].class));
        Variables.define("char.class", new JavaClassValue(char.class));
        Variables.define("char[].class", new JavaClassValue(char[].class));
        Variables.define("char[][].class", new JavaClassValue(char[][].class));
        Variables.define("int.class", new JavaClassValue(int.class));
        Variables.define("int[].class", new JavaClassValue(int[].class));
        Variables.define("int[][].class", new JavaClassValue(int[][].class));
        Variables.define("long.class", new JavaClassValue(long.class));
        Variables.define("long[].class", new JavaClassValue(long[].class));
        Variables.define("long[][].class", new JavaClassValue(long[][].class));
        Variables.define("float.class", new JavaClassValue(float.class));
        Variables.define("float[].class", new JavaClassValue(float[].class));
        Variables.define("float[][].class", new JavaClassValue(float[][].class));
        Variables.define("double.class", new JavaClassValue(double.class));
        Variables.define("double[].class", new JavaClassValue(double[].class));
        Variables.define("double[][].class", new JavaClassValue(double[][].class));
        Variables.define("String.class", new JavaClassValue(String.class));
        Variables.define("String[].class", new JavaClassValue(String[].class));
        Variables.define("String[][].class", new JavaClassValue(String[][].class));
        Variables.define("Object.class", new JavaClassValue(Object.class));
        Variables.define("Object[].class", new JavaClassValue(Object[].class));
        Variables.define("Object[][].class", new JavaClassValue(Object[][].class));

        Keyword.put("isNull", this::isNull);
        Keyword.put("JUpload", this::JUpload);
        Keyword.put("JClass", this::JClass);
        Keyword.put("JObject", this::JObject);
        Keyword.put("LzrValue", this::LZRValue);
    }

    private LzrValue JUpload(LzrValue[] args) {
        String path = args[0].asString();
        String addressLib = args[1].asString();
        try {
            URLClassLoader child = new URLClassLoader(
                    new URL[] { new URL("file:" + path) },
                    this.getClass().getClassLoader()
            );
            Library module = (Library) Class.forName(addressLib + ".invoker", true, child).newInstance();
            module.init();
        } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new LzrException(e.getLocalizedMessage(), e.getMessage());
        }

        return LzrNumber.MINUS_ONE;
    }



    private static class JavaClassValue extends LzrMap implements Instantiable {

        public static LzrValue classOrNull(Class<?> clazz) {
            if (clazz == null) return LzrNull.INSTANCE;
            return new JavaClassValue(clazz);
        }

        private final Class<?> clazz;

        public JavaClassValue(Class<?> clazz) {
            super(25);
            this.clazz = clazz;
            init(clazz);
        }

        private void init(Class<?> clazz) {
            set("isAnnotation", LzrNumber.fromBoolean(clazz.isAnnotation()));
            set("isAnonymousClass", LzrNumber.fromBoolean(clazz.isAnonymousClass()));
            set("isArray", LzrNumber.fromBoolean(clazz.isArray()));
            set("isEnum", LzrNumber.fromBoolean(clazz.isEnum()));
            set("isInterface", LzrNumber.fromBoolean(clazz.isInterface()));
            set("isLocalClass", LzrNumber.fromBoolean(clazz.isLocalClass()));
            set("isMemberClass", LzrNumber.fromBoolean(clazz.isMemberClass()));
            set("isPrimitive", LzrNumber.fromBoolean(clazz.isPrimitive()));
            set("isSynthetic", LzrNumber.fromBoolean(clazz.isSynthetic()));

            set("modifiers", LzrNumber.of(clazz.getModifiers()));

            set("canonicalName", new LzrString(clazz.getCanonicalName()));
            set("name", new LzrString(clazz.getName()));
            set("simpleName", new LzrString(clazz.getSimpleName()));
            set("typeName", new LzrString(clazz.getTypeName()));
            set("genericString", new LzrString(clazz.toGenericString()));

            set("getComponentType", new LzrFunction(v -> classOrNull(clazz.getComponentType()) ));
            set("getDeclaringClass", new LzrFunction(v -> classOrNull(clazz.getDeclaringClass()) ));
            set("getEnclosingClass", new LzrFunction(v -> classOrNull(clazz.getEnclosingClass()) ));
            set("getSuperclass", new LzrFunction(v -> new JavaClassValue(clazz.getSuperclass()) ));

            set("getClasses", new LzrFunction(v -> array(clazz.getClasses()) ));
            set("getDeclaredClasses", new LzrFunction(v -> array(clazz.getDeclaredClasses()) ));
            set("getInterfaces", new LzrFunction(v -> array(clazz.getInterfaces()) ));

            set("asSubclass", new LzrFunction(this::asSubclass));
            set("isAssignableFrom", new LzrFunction(this::isAssignableFrom));
            set("new", new LzrFunction(this::newInstance));
            set("cast", new LzrFunction(this::cast));
        }

        private LzrValue asSubclass(LzrValue[] args) {
            Arguments.check(1, args.length);
            return new JavaClassValue(clazz.asSubclass( ((JavaClassValue)args[0]).clazz ));
        }

        private LzrValue isAssignableFrom(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.fromBoolean(clazz.isAssignableFrom( ((JavaClassValue)args[0]).clazz ));
        }

        @NotNull
        @Override
        public LzrValue newInstance(@NotNull LzrValue[] args) {
            return findConstructorAndInstantiate(args, clazz.getConstructors());
        }

        private LzrValue cast(LzrValue[] args) {
            Arguments.check(1, args.length);
            return objectToValue(clazz, clazz.cast(((ObjectValue)args[0]).object));
        }

        @Override
        public boolean containsKey(LzrValue key) {
            return getValue(clazz, null, key.asString()) != null;
        }

        @Override
        public LzrValue get(@NotNull LzrValue key) {
            if (super.containsKey(key)) {
                return super.get(key);
            }
            return getValue(clazz, null, key.asString());
        }

        @NotNull
        @Override
        public String toString() {
            return "ClassValue " + clazz.toString();
        }
    }

    private static class ObjectValue extends LzrMap {

        public static LzrValue objectOrNull(Object object) {
            if (object == null) return LzrNull.INSTANCE;
            return new ObjectValue(object);
        }

        private final Object object;

        public ObjectValue(Object object) {
            super(2);
            this.object = object;
        }

        @Override
        public boolean containsKey(@NotNull LzrValue key) {
            return get(key) != null;
        }

        @Override
        public LzrValue get(LzrValue key) {
            return getValue(object.getClass(), object, key.asString());
        }

        @NotNull
        @Override
        public String asString() {
            return object.toString();
        }

        @NotNull
        @Override
        public String toString() {
            return "ObjectValue " + asString();
        }
    }

    private LzrValue isNull(LzrValue[] args) {
        Arguments.checkAtLeast(1, args.length);
        for (LzrValue arg : args) {
            if (arg.raw() == null) return LzrNumber.ONE;
        }
        return LzrNumber.ZERO;
    }

    private LzrValue JClass(LzrValue[] args) {
        Arguments.check(1, args.length);

        final String className = args[0].asString();
        try {
            return new JavaClassValue(Class.forName(className));
        } catch (ClassNotFoundException ce) {
            throw new RuntimeException("Class " + className + " not found.", ce);
        }
    }


    private LzrValue JObject(LzrValue[] args) {
        Arguments.check(1, args.length);
        if (Objects.equals(args[0], LzrNull.INSTANCE)) return LzrNull.INSTANCE;
        return new ObjectValue(valueToObject(args[0]));
    }

    private LzrValue LZRValue(LzrValue[] args) {
        Arguments.check(1, args.length);
        if (args[0] instanceof ObjectValue) {
            return objectToValue( ((ObjectValue) args[0]).object );
        }
        return LzrNull.INSTANCE;
    }



    private static LzrValue getValue(Class<?> clazz, Object object, String key) {
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
                return LzrFunction.EMPTY;
            }
            return new LzrFunction(methodsToFunction(object, methods));
        } catch (SecurityException ex) {
            // ignore and go to the next step
        }

        return LzrNull.INSTANCE;
    }

    private static LzrValue findConstructorAndInstantiate(LzrValue[] args, Constructor<?>[] ctors) {
        for (Constructor<?> ctor : ctors) {
            if (ctor.getParameterCount() != args.length) continue;
            if (isNotMatch(args, ctor.getParameterTypes())) continue;
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
                if (isNotMatch(args, method.getParameterTypes())) continue;
                try {
                    final Object result = method.invoke(object, valuesToObjects(args));
                    if (method.getReturnType() != void.class) {
                        return objectToValue(result);
                    }
                    return LzrNumber.ONE;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    // skip
                }
            }
            final String className = (object == null ? "null" : object.getClass().getName());
            throw new RuntimeException("Method for " + args.length + " arguments"
                    + " not found or non accessible in " + className);
        };
    }

    private static boolean isNotMatch(LzrValue[] args, Class<?>[] types) {
        for (int i = 0; i < args.length; i++) {
            final LzrValue arg = args[i];
            final Class<?> clazz = types[i];

            if (Objects.equals(arg, LzrNull.INSTANCE)) continue;

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

            return true;
        }
        return false;
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

    @NotNull
    private static LzrArray array(@NotNull Class<?>[] classes) {
        return new LzrArray(classes.length, index -> JavaClassValue.classOrNull(classes[index]));
    }

    private static LzrValue objectToValue(Object o) {
        if (o == null) return LzrNull.INSTANCE;
        return objectToValue(o.getClass(), o);
    }

    private static LzrValue objectToValue(Class<?> clazz, Object o) {
        if (o == null || o.equals(LzrNull.INSTANCE)) return LzrNull.INSTANCE;
        if (clazz.isPrimitive()) {
            if (int.class.isAssignableFrom(clazz))
                return LzrNumber.of((int) o);
            if (boolean.class.isAssignableFrom(clazz))
                return LzrNumber.fromBoolean((boolean) o);
            if (double.class.isAssignableFrom(clazz))
                return LzrNumber.of((double) o);
            if (float.class.isAssignableFrom(clazz))
                return LzrNumber.of((float) o);
            if (long.class.isAssignableFrom(clazz))
                return LzrNumber.of((long) o);
            if (byte.class.isAssignableFrom(clazz))
                return LzrNumber.of((byte) o);
            if (char.class.isAssignableFrom(clazz))
                return LzrNumber.of((char) o);
            if (short.class.isAssignableFrom(clazz))
                return LzrNumber.of((short) o);
        }
        if (Number.class.isAssignableFrom(clazz)) {
            return LzrNumber.of((Number) o);
        }
        if (String.class.isAssignableFrom(clazz)) {
            return new LzrString((String) o);
        }
        if (CharSequence.class.isAssignableFrom(clazz)) {
            return new LzrString( ((CharSequence) o).toString() );
        }
        if (o instanceof LzrValue) {
            return (LzrValue) o;
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

    private static LzrValue arrayToValue(Class<?> clazz, Object o) {
        final int length = Array.getLength(o);
        final LzrArray result = new LzrArray(length);
        final Class<?> componentType = clazz.getComponentType();
        int i = 0;
        if (boolean.class.isAssignableFrom(componentType)) {
            for (boolean element : (boolean[]) o) {
                result.set(i++, LzrNumber.fromBoolean(element));
            }
        } else if (byte.class.isAssignableFrom(componentType)) {
            for (byte element : (byte[]) o) {
                result.set(i++, LzrNumber.of(element));
            }
        } else if (char.class.isAssignableFrom(componentType)) {
            for (char element : (char[]) o) {
                result.set(i++, LzrNumber.of(element));
            }
        } else if (double.class.isAssignableFrom(componentType)) {
            for (double element : (double[]) o) {
                result.set(i++, LzrNumber.of(element));
            }
        } else if (float.class.isAssignableFrom(componentType)) {
            for (float element : (float[]) o) {
                result.set(i++, LzrNumber.of(element));
            }
        } else if (int.class.isAssignableFrom(componentType)) {
            for (int element : (int[]) o) {
                result.set(i++, LzrNumber.of(element));
            }
        } else if (long.class.isAssignableFrom(componentType)) {
            for (long element : (long[]) o) {
                result.set(i++, LzrNumber.of(element));
            }
        } else if (short.class.isAssignableFrom(componentType)) {
            for (short element : (short[]) o) {
                result.set(i++, LzrNumber.of(element));
            }
        } else {
            for (Object element : (Object[]) o) {
                result.set(i++, objectToValue(element));
            }
        }
        return result;
    }

    private static Object[] valuesToObjects(LzrValue[] args) {
        Object[] result = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            result[i] = valueToObject(args[i]);
        }
        return result;
    }

    private static Object valueToObject(LzrValue value) {
        if (Objects.equals(value, LzrNull.INSTANCE)) return null;
        switch (value.type()) {
            case Types.NUMBER:
                return value.raw();
            case Types.STRING:
                return value.asString();
            case Types.ARRAY:
                return arrayToObject((LzrArray) value);
        }
        if (value instanceof ObjectValue) {
            return ((ObjectValue) value).object;
        }
        if (value instanceof JavaClassValue) {
            return ((JavaClassValue) value).clazz;
        }
        return value.raw();
    }

    private static Object arrayToObject(LzrArray value) {
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
