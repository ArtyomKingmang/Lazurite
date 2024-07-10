package com.kingmang.lazurite.runtime.specification;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Specification {

    private final ArrayList<SpecificationType> types;

    public Specification(ArrayList<SpecificationType> types) {
        this.types = types;
    }

    public Specification() {
        this.types = new ArrayList<>();
    }

    @Override
    public String toString() {
        return types.toString();
    }
}
