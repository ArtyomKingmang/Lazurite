package com.kingmang.lazurite.libraries.iceCream.core;

public class CallerDetails {
    public String className;
    public String methodName;
    public String lineNumber;
    public String fileName;

    public String getClassName() {
        return className;
    }

    public CallerDetails setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public CallerDetails setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public CallerDetails setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public CallerDetails setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
}
