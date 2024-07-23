package com.kingmang.lazurite.libraries.lzr.collections.linkedList;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrReference;
import com.kingmang.lazurite.runtime.values.LzrValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class linkedList implements Library {

    @Override
    public void init() {
        LzrMap linkedList = new LzrMap(6);

        linkedList.set("new", args -> {
            Arguments.check(0, args.length);
            return new LzrReference(new LinkedList<>());
        });

        linkedList.set("add", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as linkedList object");
            LinkedList<LzrValue> st = (LinkedList<LzrValue>) ((LzrReference) s).ref;
            st.add(args[1]);
            return args[1];
        });

        linkedList.set("remove", args -> {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as linkedList object");
            LinkedList<LzrValue> st = (LinkedList<LzrValue>) ((LzrReference) s).ref;
            return st.remove();
        });

        linkedList.set("set", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as linkedList object");
            LinkedList<LzrValue> st = (LinkedList<LzrValue>) ((LzrReference) s).ref;
            return st.set(args[1].asInt(), s);
        });

        linkedList.set("get", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as linkedList object");
            LinkedList<LzrValue> st = (LinkedList<LzrValue>) ((LzrReference) s).ref;
            return st.get(args[1].asInt());
        });

        linkedList.set("toArray", args -> {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as linkedList object");
            LinkedList<LzrValue> st = (LinkedList<LzrValue>) ((LzrReference) s).ref;
            LzrValue[] calls = st.toArray(new LzrValue[]{});
            return new LzrArray(Arrays.asList(calls));
        });


        Variables.define("linkedList", linkedList);
    }
}
