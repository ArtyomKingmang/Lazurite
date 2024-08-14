package com.kingmang.lazurite.libraries.lzrx.awt.robot;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntConsumer;

import static com.kingmang.lazurite.libraries.lzrx.awt.robot.robot.SYMBOL_CODES;
import static com.kingmang.lazurite.libraries.lzrx.awt.robot.robot.TYPING_DELAY;

public final class robot implements Library {
    public static Robot lzrRobot;

    public static final int CLICK_DELAY = 200;
    public static final int TYPING_DELAY = 50;
    static LzrMap robotKeywords;
    public static void initKeys() {
        LzrMap keys = new LzrMap(70);
        Variables.define("CLICK_DELAY", new LzrNumber(CLICK_DELAY));
        Variables.define("TYPING_DELAY", new LzrNumber(TYPING_DELAY));

        keys.set("UP", new LzrNumber(KeyEvent.VK_UP));
        keys.set("DOWN", new LzrNumber(KeyEvent.VK_DOWN));
        keys.set("LEFT", new LzrNumber(KeyEvent.VK_LEFT));
        keys.set("RIGHT", new LzrNumber(KeyEvent.VK_RIGHT));

        keys.set("0", new LzrNumber(KeyEvent.VK_0));
        keys.set("1", new LzrNumber(KeyEvent.VK_1));
        keys.set("2", new LzrNumber(KeyEvent.VK_2));
        keys.set("3", new LzrNumber(KeyEvent.VK_3));
        keys.set("4", new LzrNumber(KeyEvent.VK_4));
        keys.set("5", new LzrNumber(KeyEvent.VK_5));
        keys.set("6", new LzrNumber(KeyEvent.VK_6));
        keys.set("7", new LzrNumber(KeyEvent.VK_7));
        keys.set("8", new LzrNumber(KeyEvent.VK_8));
        keys.set("9", new LzrNumber(KeyEvent.VK_9));

        keys.set("A", new LzrNumber(KeyEvent.VK_A));
        keys.set("B", new LzrNumber(KeyEvent.VK_B));
        keys.set("C", new LzrNumber(KeyEvent.VK_C));
        keys.set("D", new LzrNumber(KeyEvent.VK_D));
        keys.set("E", new LzrNumber(KeyEvent.VK_E));
        keys.set("F", new LzrNumber(KeyEvent.VK_F));
        keys.set("G", new LzrNumber(KeyEvent.VK_G));
        keys.set("H", new LzrNumber(KeyEvent.VK_H));
        keys.set("I", new LzrNumber(KeyEvent.VK_I));
        keys.set("J", new LzrNumber(KeyEvent.VK_J));
        keys.set("K", new LzrNumber(KeyEvent.VK_K));
        keys.set("L", new LzrNumber(KeyEvent.VK_L));
        keys.set("M", new LzrNumber(KeyEvent.VK_M));
        keys.set("N", new LzrNumber(KeyEvent.VK_N));
        keys.set("O", new LzrNumber(KeyEvent.VK_O));
        keys.set("P", new LzrNumber(KeyEvent.VK_P));
        keys.set("Q", new LzrNumber(KeyEvent.VK_Q));
        keys.set("R", new LzrNumber(KeyEvent.VK_R));
        keys.set("S", new LzrNumber(KeyEvent.VK_S));
        keys.set("T", new LzrNumber(KeyEvent.VK_T));
        keys.set("U", new LzrNumber(KeyEvent.VK_U));
        keys.set("V", new LzrNumber(KeyEvent.VK_V));
        keys.set("W", new LzrNumber(KeyEvent.VK_W));
        keys.set("X", new LzrNumber(KeyEvent.VK_X));
        keys.set("Y", new LzrNumber(KeyEvent.VK_Y));
        keys.set("Z", new LzrNumber(KeyEvent.VK_Z));

        keys.set("TAB", new LzrNumber(KeyEvent.VK_TAB));
        keys.set("ALT", new LzrNumber(KeyEvent.VK_ALT));
        keys.set("SHIFT", new LzrNumber(KeyEvent.VK_SHIFT));
        keys.set("CAPS_LOCK", new LzrNumber(KeyEvent.VK_CAPS_LOCK));
        keys.set("CONTROL", new LzrNumber(KeyEvent.VK_CONTROL));
        keys.set("ENTER", new LzrNumber(KeyEvent.VK_ENTER));
        keys.set("ESCAPE", new LzrNumber(KeyEvent.VK_ESCAPE));

        keys.set("F1", new LzrNumber(KeyEvent.VK_F1));
        keys.set("F2", new LzrNumber(KeyEvent.VK_F2));
        keys.set("F3", new LzrNumber(KeyEvent.VK_F3));
        keys.set("F4", new LzrNumber(KeyEvent.VK_F4));
        keys.set("F5", new LzrNumber(KeyEvent.VK_F5));
        keys.set("F6", new LzrNumber(KeyEvent.VK_F6));
        keys.set("F7", new LzrNumber(KeyEvent.VK_F7));
        keys.set("F8", new LzrNumber(KeyEvent.VK_F8));
        keys.set("F9", new LzrNumber(KeyEvent.VK_F9));
        keys.set("F10", new LzrNumber(KeyEvent.VK_F10));
        keys.set("F11", new LzrNumber(KeyEvent.VK_F11));
        keys.set("F12", new LzrNumber(KeyEvent.VK_F12));
        Variables.define("KEY", keys);
    }
    public static final Map<Character, Integer> SYMBOL_CODES;
    static {
        SYMBOL_CODES = new HashMap<>(10);
        SYMBOL_CODES.put('_', KeyEvent.VK_MINUS);
        SYMBOL_CODES.put(':', KeyEvent.VK_SEMICOLON);
    }

    void initMouseFunctions() {
        robotKeywords.set("mousePress", convertFunction(lzrRobot::mousePress));
        robotKeywords.set("mouseRelease", convertFunction(lzrRobot::mouseRelease));
        robotKeywords.set("mouseWheel", convertFunction(lzrRobot::mouseWheel));
        robotKeywords.set("mouseMove", new MouseMove());
    }
    @Override
    public void init() {
        initKeys();
        boolean isRobotInitialized = initialize();
        robotKeywords = new LzrMap(14);
        if (isRobotInitialized) {
            initMouseFunctions();
            robotKeywords.set("click", convertFunction(robot::click));
            robotKeywords.set("delay", convertFunction(lzrRobot::delay));
            robotKeywords.set("setAutoDelay", convertFunction(lzrRobot::setAutoDelay));
            robotKeywords.set("keyPress", convertFunction(lzrRobot::keyPress));
            robotKeywords.set("keyRelease", convertFunction(lzrRobot::keyRelease));
            robotKeywords.set("typeText", new TypeText());
            robotKeywords.set("toClipboard", new RobotClipboard.ToClipboard());
            robotKeywords.set("fromClipboard", new RobotClipboard.FromClipboard());
        }
        robotKeywords.set("execProcess", new Execute(Execute.Mode.EXEC));
        robotKeywords.set("execProcessAndWait", new Execute(Execute.Mode.EXEC_AND_WAIT));
        Variables.define("robot", robotKeywords);

    }


    private static boolean initialize() {
        try {
            lzrRobot = new Robot();
            return true;
        } catch (AWTException ex) {
            throw new LzrException("RuntimeException ", "Unable to create robot instance" + ex);
        }
    }

    private static Function convertFunction(IntConsumer consumer) {
        return args -> {
            Arguments.check(1, args.length);

            try {
                consumer.accept(args[0].asInt());
            } catch (IllegalArgumentException ignored) {}

            return LzrNumber.ZERO;
        };
    }

    private static synchronized void click(int buttons) {
        lzrRobot.mousePress(buttons);
        lzrRobot.delay(CLICK_DELAY);
        lzrRobot.mouseRelease(buttons);
    }

}

class MouseMove implements Function {
    @Override
    public @NotNull LzrValue execute(@NotNull LzrValue... args) {
        Arguments.check(2, args.length);

        try {
            robot.lzrRobot.mouseMove(args[0].asInt(), args[1].asInt());
        } catch (IllegalArgumentException ignored) {}

        return LzrNumber.ZERO;
    }

}
class TypeText implements Function{

    @Override
    public @NotNull LzrValue execute(@NotNull LzrValue... args) {
        Arguments.check(1, args.length);

        try {
            typeText(args[0].asString());
        } catch (IllegalArgumentException ignored) {}

        return LzrNumber.ZERO;
    }

    private static synchronized void typeText(String text) {
        for (char ch : text.toCharArray())
            typeSymbol(ch);
    }

    private static void typeSymbol(char ch) {
        int code = KeyEvent.getExtendedKeyCodeForChar(ch);

        boolean isUpperCase = Character.isLetter(ch) && Character.isUpperCase(ch);
        boolean needPressShift = isUpperCase;

        if (!isUpperCase) {
            final int symbolIndex = "!@#$%^&*()".indexOf(ch);
            if (symbolIndex != -1) {
                needPressShift = true;
                code = '1' + symbolIndex;
            } else if (SYMBOL_CODES.containsKey(ch)) {
                needPressShift = true;
                code = SYMBOL_CODES.get(ch);
            }
        }

        if (code == KeyEvent.VK_UNDEFINED)
            return;

        if (needPressShift) {
            // press shift
            robot.lzrRobot.keyPress(KeyEvent.VK_SHIFT);
            robot.lzrRobot.delay(TYPING_DELAY);
        }

        robot.lzrRobot.keyPress(code);
        robot.lzrRobot.delay(TYPING_DELAY);
        robot.lzrRobot.keyRelease(code);

        if (needPressShift) {
            // release shift
            robot.lzrRobot.delay(TYPING_DELAY);
            robot.lzrRobot.keyRelease(KeyEvent.VK_SHIFT);
            robot.lzrRobot.delay(TYPING_DELAY);
        }
    }

}


class RobotClipboard{
    static final class ToClipboard implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(1, args.length);
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new StringSelection(args[0].asString()), null);
            return LzrNumber.ZERO;
        }
    }

    static final class FromClipboard implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            try {
                Object data = Toolkit.getDefaultToolkit().getSystemClipboard()
                        .getData(DataFlavor.stringFlavor);
                return new LzrString(data.toString());
            } catch (Exception ex) {
                return LzrString.EMPTY;
            }
        }
    }
}


final class Execute implements Function {

    public enum Mode { EXEC, EXEC_AND_WAIT }

    private final Mode mode;

    public Execute(Mode mode) {
        this.mode = mode;
    }

    @Override
    public @NotNull LzrValue execute(@NotNull LzrValue... args) {
        Arguments.checkAtLeast(1, args.length);

        try {
            final Process process;
            if (args.length > 1) {
                process = Runtime.getRuntime().exec(toStringArray(args));
            } else if (args[0].type() == Types.ARRAY) {
                final LzrArray array = (LzrArray) args[0];
                process = Runtime.getRuntime().exec(toStringArray(array.getCopyElements()));
            } else
                process = Runtime.getRuntime().exec(args[0].asString());

            if (mode == Mode.EXEC_AND_WAIT)
                return LzrNumber.of(process.waitFor());
            return LzrNumber.ZERO;
        } catch (Exception ex) {
            return LzrNumber.ZERO;
        }
    }

    private static String[] toStringArray(LzrValue[] values) {
        final String[] strings = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strings[i] = values[i].asString();
        }
        return strings;
    }
}