package com.kingmang.lazurite.libraries.lzr.jloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JClassLoader extends URLClassLoader {

    private final String packageName;
    private final String methodName;
    private final Object[] arguments;

    public JClassLoader(String jarPath, String packageName, String methodName, Object[] arguments) throws MalformedURLException {
        super(new URL[]{new File(jarPath).toURI().toURL()});
        this.packageName = packageName;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    public void loadAndInvokeMethod(String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String className = packageName + "." + name;
        Class<?> clazz = findClass(name);
        Method method = clazz.getDeclaredMethod(methodName, getParameterTypes(arguments));
        method.setAccessible(true);
        try{
            method.invoke(clazz.getDeclaredConstructor().newInstance(), arguments);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

    }

    private Class<?>[] getParameterTypes(Object[] arguments) {
        Class<?>[] parameterTypes = new Class[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            parameterTypes[i] = arguments[i].getClass();
        }
        return parameterTypes;
    }
}