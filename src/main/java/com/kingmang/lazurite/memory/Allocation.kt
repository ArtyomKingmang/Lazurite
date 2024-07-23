package com.kingmang.lazurite.memory;

import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Allocation implements LzrValue {

    private LinkedList<LzrValue> list;

    @Getter
    private int allocated, defaultAlloc;

    public Allocation(LinkedList<LzrValue> list, int allocated) {
        this.list = list; Storage.segment(allocated);
        this.allocated = allocated;
        this.defaultAlloc = allocated;
    }

    public Allocation(int allocated, LzrValue... values) {
        this.list = new LinkedList<>(List.of(values));
        this.allocated = allocated;
        this.defaultAlloc = allocated;
    }

    public Allocation(int size) {
        this.list = new LinkedList<>();
        this.allocated = size;
        this.defaultAlloc = size;
    }

    public LzrValue toList() {
        return new LzrArray(list);
    }

    public void segment(LzrValue obj) {
        allocated -= StorageUtils.size(obj);
    }

    public void clear() {
        allocated = defaultAlloc;
        list.clear();
    }

    public void setList(LinkedList<LzrValue> list) {
        this.list = list;
    }

    public void setAllocated(int allocated) {
        this.allocated = allocated;
    }

    public void setDefaultAlloc(int defaultAlloc) {
        this.defaultAlloc = defaultAlloc;
    }

    @Override
    public String toString() {
        return "allocation " + hashCode();
    }


    public LinkedList<LzrValue> getList() {
        return list;
    }

    @Override
    public Object raw() {
        return list;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public double asNumber() {
        return 0;
    }

    @NotNull
    @Override
    public String asString() {
        return "";
    }

    @NotNull
    @Override
    public int[] asArray() {
        return new int[0];
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public int compareTo(@NotNull LzrValue o) {
        return 0;
    }
}
