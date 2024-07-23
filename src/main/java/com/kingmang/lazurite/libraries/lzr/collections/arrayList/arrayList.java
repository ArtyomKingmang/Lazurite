package com.kingmang.lazurite.libraries.lzr.collections.arrayList;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class arrayList implements Library {

    @Override
    public void init() {
         LzrMap arrayList = new LzrMap(6);

        arrayList.set("new", args -> {
            Arguments.check(0, args.length);
            return new LzrReference(new ArrayList<LzrValue>());
        });

        arrayList.set("add", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as arrayList object");
            ArrayList<LzrValue> st = (ArrayList<LzrValue>) ((LzrReference) s).ref;
            st.add(args[1]);
            return args[1];
        });

        arrayList.set("remove", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as arrayList object");
            ArrayList<LzrValue> st = (ArrayList<LzrValue>) ((LzrReference) s).ref;
            return st.remove(args[1].asInt());
        });

        arrayList.set("set", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as arrayList object");
            ArrayList<LzrValue> st = (ArrayList<LzrValue>) ((LzrReference) s).ref;
            return st.set(args[1].asInt(), s);
        });

        arrayList.set("get", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as arrayList object");
            ArrayList<LzrValue> st = (ArrayList<LzrValue>) ((LzrReference) s).ref;
            return st.get(args[1].asInt());
        });

        arrayList.set("toArray", args -> {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as arrayList object");
            ArrayList<LzrValue> st = (ArrayList<LzrValue>) ((LzrReference) s).ref;
            LzrValue[] calls = st.toArray(new LzrValue[]{});
            return new LzrArray(Arrays.asList(calls));
        });


        Variables.define("arrayList", arrayList);
    }
}
