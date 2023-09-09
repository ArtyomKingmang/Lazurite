package com.kingmang.lazurite.modules.KEYS;

import com.kingmang.lazurite.lib.KEYWORD;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Variables;

import java.awt.event.KeyEvent;

public class KEYS implements Module {

    public void init() {
        Variables.set("Key_UP", new NumberValue(KeyEvent.VK_UP));
        Variables.set("Key_DOWN", new NumberValue(KeyEvent.VK_DOWN));
        Variables.set("Key_LEFT", new NumberValue(KeyEvent.VK_LEFT));
        Variables.set("Key_RIGHT", new NumberValue(KeyEvent.VK_RIGHT));

        Variables.set("Key_0", new NumberValue(KeyEvent.VK_0));
        Variables.set("Key_1", new NumberValue(KeyEvent.VK_1));
        Variables.set("Key_2", new NumberValue(KeyEvent.VK_2));
        Variables.set("Key_3", new NumberValue(KeyEvent.VK_3));
        Variables.set("Key_4", new NumberValue(KeyEvent.VK_4));
        Variables.set("Key_5", new NumberValue(KeyEvent.VK_5));
        Variables.set("Key_6", new NumberValue(KeyEvent.VK_6));
        Variables.set("Key_7", new NumberValue(KeyEvent.VK_7));
        Variables.set("Key_8", new NumberValue(KeyEvent.VK_8));
        Variables.set("Key_9", new NumberValue(KeyEvent.VK_9));

        Variables.set("Key_A", new NumberValue(KeyEvent.VK_A));
        Variables.set("Key_B", new NumberValue(KeyEvent.VK_B));
        Variables.set("Key_C", new NumberValue(KeyEvent.VK_C));
        Variables.set("Key_D", new NumberValue(KeyEvent.VK_D));
        Variables.set("Key_E", new NumberValue(KeyEvent.VK_E));
        Variables.set("Key_F", new NumberValue(KeyEvent.VK_F));
        Variables.set("Key_G", new NumberValue(KeyEvent.VK_G));
        Variables.set("Key_H", new NumberValue(KeyEvent.VK_H));
        Variables.set("Key_I", new NumberValue(KeyEvent.VK_I));
        Variables.set("Key_J", new NumberValue(KeyEvent.VK_J));
        Variables.set("Key_K", new NumberValue(KeyEvent.VK_K));
        Variables.set("Key_L", new NumberValue(KeyEvent.VK_L));
        Variables.set("Key_M", new NumberValue(KeyEvent.VK_M));
        Variables.set("Key_N", new NumberValue(KeyEvent.VK_N));
        Variables.set("Key_O", new NumberValue(KeyEvent.VK_O));
        Variables.set("Key_P", new NumberValue(KeyEvent.VK_P));
        Variables.set("Key_Q", new NumberValue(KeyEvent.VK_Q));
        Variables.set("Key_R", new NumberValue(KeyEvent.VK_R));
        Variables.set("Key_S", new NumberValue(KeyEvent.VK_S));
        Variables.set("Key_T", new NumberValue(KeyEvent.VK_T));
        Variables.set("Key_U", new NumberValue(KeyEvent.VK_U));
        Variables.set("Key_V", new NumberValue(KeyEvent.VK_V));
        Variables.set("Key_W", new NumberValue(KeyEvent.VK_W));
        Variables.set("Key_X", new NumberValue(KeyEvent.VK_X));
        Variables.set("Key_Y", new NumberValue(KeyEvent.VK_Y));
        Variables.set("Key_Z", new NumberValue(KeyEvent.VK_Z));

        Variables.set("Key_TAB", new NumberValue(KeyEvent.VK_TAB));
        Variables.set("Key_Y", new NumberValue(KeyEvent.VK_CAPS_LOCK));
        Variables.set("Key_Z", new NumberValue(KeyEvent.VK_CONTROL));
        Variables.set("Key_FIRE", new NumberValue(KeyEvent.VK_ENTER));
        Variables.set("Key_ESCAPE", new NumberValue(KeyEvent.VK_ESCAPE));

        Variables.set("Key_F1", new NumberValue(KeyEvent.VK_F1));
        Variables.set("Key_F2", new NumberValue(KeyEvent.VK_F2));
        Variables.set("Key_F3", new NumberValue(KeyEvent.VK_F3));
        Variables.set("Key_F4", new NumberValue(KeyEvent.VK_F4));
        Variables.set("Key_F5", new NumberValue(KeyEvent.VK_F5));
        Variables.set("Key_F6", new NumberValue(KeyEvent.VK_F6));
        Variables.set("Key_F7", new NumberValue(KeyEvent.VK_F7));
        Variables.set("Key_F8", new NumberValue(KeyEvent.VK_F8));
        Variables.set("Key_F9", new NumberValue(KeyEvent.VK_F9));
        Variables.set("Key_F10", new NumberValue(KeyEvent.VK_F10));
        Variables.set("Key_F11", new NumberValue(KeyEvent.VK_F11));
        Variables.set("Key_F12", new NumberValue(KeyEvent.VK_F12));
    }
}