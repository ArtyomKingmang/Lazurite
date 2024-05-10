package com.kingmang.lazurite.runtime.specification;

import java.util.ArrayList;

public class Specification {

    private ArrayList<SpecificationType> types;

    public Specification(ArrayList<SpecificationType> types) {
        this.types = types;
    }

    public Specification() {
        this.types = new ArrayList<>();
    }

    public ArrayList<SpecificationType> getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return types.toString();
    }
}
