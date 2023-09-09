package com.kingmang.lazurite.runtime;

import com.kingmang.lazurite.base.ClassDeclarations;
import com.kingmang.lazurite.lib.Function;
import com.kingmang.lazurite.LZREx.LZRExeption;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassValue implements Value {

    public String name;
    public Map<String, Value> fields;
    public List<String> fieldsName;
    private Function toString;
    private boolean isStatic;

    public ClassValue(String name, List<String> fieldsName) {
        this.name = name;
        this.fieldsName = fieldsName;
        this.fields = new HashMap<>();
    }
    public ClassValue(String name, List<String> fieldsName, boolean isStatic) {
        this.name = name;
        this.fieldsName = fieldsName;
        this.fields = new HashMap<>();
        this.isStatic = isStatic;
    }
    public boolean isStatic(){
        return isStatic;
    }
    public void setStatic(boolean aStatic){
        isStatic = aStatic;
    }
    public int getArgsCount() {
        return fieldsName.size();
    }

    public String getArg(int index) {
        return fieldsName.get(index);
    }

    public void setField(String key, Value value) {
        if (key.equals("toString")) {
            if (!(value instanceof FunctionValue)) throw new LZRExeption("BadToStringException", "Expected function as toString");
            toString = (Function) ((FunctionValue) value).getValue();
        }
        fields.put(key, value);
    }
    public Value getField(String key) {
        Value result = fields.get(key);
        if (result == null) throw new LZRExeption("UnboundStructField", "Unbound field '" + key + "'");
        return result;
    }

    @Override
    public Object raw() {
        return asString();
    }

    @Override
    public int asInt() {
        throw new LZRExeption("TypeException", "Cannot cast struct to a number");
    }


    @Override
    public double asNumber() {
        throw new LZRExeption("TypeException", "Cannot cast struct to a number");
    }

    @Override
    public String asString() {
        if (toString != null)
            return toString.execute(new Value[] {}).toString();
        return "#Class<" + hashCode() + ">";
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public int compareTo(@NotNull Value o) {
        return 0;
    }
}