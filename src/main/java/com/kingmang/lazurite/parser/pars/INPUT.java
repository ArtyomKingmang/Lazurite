package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.lib.Function;
import com.kingmang.lazurite.runtime.StringValue;
import com.kingmang.lazurite.runtime.Value;

import java.util.Scanner;

public final class INPUT implements Function {

    @Override
    public Value execute(Value... args) {
        Scanner sc = new Scanner(System.in);
            return new StringValue(sc.nextLine());

    }
}
