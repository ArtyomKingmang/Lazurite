package com.kingmang.lazurite.libraries.lzr.collections.queue;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class queue implements Library {
    @Override
    public void init() {
        final LzrMap queue = new LzrMap(6);

        queue.set("new", args -> {
            Arguments.check(0, args.length);
            return new LzrReference(new ConcurrentLinkedQueue<LzrString>());
        });

        queue.set("add", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as queue object");
            ConcurrentLinkedQueue<LzrValue> st = (ConcurrentLinkedQueue<LzrValue>) ((LzrReference) s).ref;
            st.add(args[1]);
            return args[1];
        });

        queue.set("remove", args -> {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as queue object");
            ConcurrentLinkedQueue<LzrValue> st = (ConcurrentLinkedQueue<LzrValue>) ((LzrReference) s).ref;
            return st.remove();
        });

        queue.set("peek", args -> {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as queue object");
            ConcurrentLinkedQueue<LzrValue> st = (ConcurrentLinkedQueue<LzrValue>) ((LzrReference) s).ref;
            return st.peek();
        });

        queue.set("toArray", args -> {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as queue object");
            ConcurrentLinkedQueue<LzrValue> st = (ConcurrentLinkedQueue<LzrValue>) ((LzrReference) s).ref;
            LzrValue[] calls = st.toArray(new LzrValue[]{});
            LinkedList<LzrValue> result = new LinkedList<>();
            for (LzrValue call : calls) {
                result.add(call);
            }
            return new LzrArray(result);
        });

        queue.set("isEmpty", args -> {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as queue object");
            ConcurrentLinkedQueue<LzrValue> st = (ConcurrentLinkedQueue<LzrValue>) ((LzrReference) s).ref;
            return new LzrString(String.valueOf(st.isEmpty()));
        });

        Variables.define("queue", queue);
    }
}
