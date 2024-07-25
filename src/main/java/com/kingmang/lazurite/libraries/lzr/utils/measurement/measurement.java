package com.kingmang.lazurite.libraries.lzr.utils.measurement;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;

import java.util.concurrent.TimeUnit;

public class measurement implements Library {
    @Override
    public void init() {
        LzrMap m = new LzrMap(2);
        m.set("new", args -> {
            Arguments.check(0, args.length);
            return new LzrReference(new TimeMeasurement());
        });

        m.set("start", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as measurement object");
            TimeMeasurement st = (TimeMeasurement) ((LzrReference) s).getRef();
            st.start(args[1].toString());
            return new LzrString("ok");
        });

        m.set("stop", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as measurement object");
            TimeMeasurement st = (TimeMeasurement) ((LzrReference) s).getRef();
            st.stop(args[1].toString());
            return new LzrString("ok");
        });

        m.set("pause", args -> {
            Arguments.check(2, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as measurement object");
            TimeMeasurement st = (TimeMeasurement) ((LzrReference) s).getRef();
            st.pause(args[1].toString());
            return new LzrString("ok");
        });

        m.set("summary", args -> {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as measurement object");
            TimeMeasurement st = (TimeMeasurement) ((LzrReference) s).getRef();
            System.out.println("======================");
            System.out.println(st.summary(TimeUnit.MILLISECONDS, true));
            return new LzrString("ok");
        });

        Variables.define("measurement", m);
    }
}

