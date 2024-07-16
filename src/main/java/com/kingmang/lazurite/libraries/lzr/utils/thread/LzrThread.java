package com.kingmang.lazurite.libraries.lzr.utils.thread;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import org.jetbrains.annotations.NotNull;

public final class LzrThread implements Function {

    @Override
    public @NotNull LzrValue execute(@NotNull LzrValue... args) {
        Arguments.checkAtLeast(1, args.length);

        Function body;
        if (args[0].type() == Types.FUNCTION) {
            body = ((LzrFunction) args[0]).getValue();
        } else {
            body = Keyword.get(args[0].asString());
        }

        final LzrValue[] params = new LzrValue[args.length - 1];
        if (params.length > 0) {
            System.arraycopy(args, 1, params, 0, params.length);
        }

        final Thread thread = new Thread(() -> body.execute(params));
        thread.setUncaughtExceptionHandler(Console::handleException);
        thread.start();
        return LzrNumber.ZERO;
    }

}
