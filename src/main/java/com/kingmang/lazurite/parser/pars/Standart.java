package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.runtime.LZR.*;
import com.kingmang.lazurite.runtime.UserDefinedFunction;
import com.kingmang.lazurite.runtime.Value;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Standart {

    public static final class ECHO implements Function {

        @Override
        public Value execute(Value... args) {
            final StringBuilder sb = new StringBuilder();
            for (Value arg : args) {
                sb.append(arg.asString());
                sb.append(" ");
            }
            Console.println(sb.toString());
            return LZRNumber.ZERO;
        }
    }

    public static final class INPUT implements Function {
        @Override
        public Value execute(Value... args) {
            Scanner sc = new Scanner(System.in);
            return new LZRString(sc.nextLine());
        }
    }

    public static final class STR {

        private STR() { }

        static LZRArray getBytes(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final String charset = (args.length == 2) ? args[1].asString() : "UTF-8";
            try {
                return LZRArray.of(args[0].asString().getBytes(charset));
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException(uee);
            }
        }



    }

    public static final class LEN implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(1, args.length);

            final Value val = args[0];
            final int length;
            switch (val.type()) {
                case Types.ARRAY:
                    length = ((LZRArray) val).size();
                    break;
                case Types.MAP:
                    length = ((LZRMap) val).size();
                    break;
                case Types.STRING:
                    length = ((LZRString) val).length();
                    break;
                case Types.FUNCTION:
                    final Function func = ((LZRFunction) val).getValue();
                    if (func instanceof UserDefinedFunction) {
                        length = ((UserDefinedFunction) func).getArgsCount();
                    } else {
                        length = 0;
                    }
                    break;
                default:
                    length = 0;

            }
            return LZRNumber.of(length);
        }
    }

    public static final class SPRINTF implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkAtLeast(1, args.length);

            final String format = args[0].asString();
            final Object[] values = new Object[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                values[i - 1] = (args[i].type() == Types.NUMBER)
                        ? args[i].raw()
                        : args[i].asString();
            }
            return new LZRString(String.format(format, values));
        }
    }

    public static final class SUBSTR implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkOrOr(2, 3, args.length);

            final String input = args[0].asString();
            final int startIndex = args[1].asInt();

            String result;
            if (args.length == 2) {
                result = input.substring(startIndex);
            } else {
                final int endIndex = args[2].asInt();
                result = input.substring(startIndex, endIndex);
            }

            return new LZRString(result);
        }
    }

    public static final class PARSE {
        private PARSE() { }

        static Value parseInt(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LZRNumber.of(Integer.parseInt(args[0].asString(), radix));
        }
        static Value parseLong(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LZRNumber.of(Long.parseLong(args[0].asString(), radix));
        }


    }


}
