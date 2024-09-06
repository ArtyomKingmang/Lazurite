package com.kingmang.lazurite.libraries.lzrx.awt.graph;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

@SuppressWarnings({"unused", "ClassName"})
public class graph implements Library {

    private static final LzrNumber MINUS_ONE = new LzrNumber(-1);

    private static JFrame frame;
    private static CanvasPanel panel;
    private static Graphics2D graphics;
    private static BufferedImage img;
    private static LzrNumber lastKey;
    private static LzrArray mouseHover;

    public static void initColors() {

        Variables.set("RED", new LzrNumber(16711688));
        Variables.set("GREEN", new LzrNumber(65309));
        Variables.set("BLUE", new LzrNumber(5887));
        Variables.set("WHITE", new LzrNumber(16777215));
        Variables.set("BLACK", new LzrNumber(0));
        Variables.set("PURPLE", new LzrNumber(9109759));
        Variables.set("PINK", new LzrNumber(16761037));
        Variables.set("YELLOW", new LzrNumber(16776960));
    }

    public static void initKeys() {
        final LzrMap keys = new LzrMap(60);

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
    public void initConstant() {
        initColors();
        initKeys();
    }
    public void init() {
        initConstant();

        Keyword.put("background", new background());
        Keyword.put("dispose", new dispose());
        Keyword.put("rotate", new rotate());
        Keyword.put("scale", new scale());
        Keyword.put("font", new font());
        Keyword.put("translate", new translate());
        Keyword.put("Frame", new Frame());
        Keyword.put("fill3d", intConsumer4Convert(graph::fill3d));
        Keyword.put("cube", intConsumer4Convert(graph::cube));
        Keyword.put("keyPressed", new KeyPressed());
        Keyword.put("mouseHover", new MouseHover());
        Keyword.put("line", intConsumer4Convert(graph::line));
        Keyword.put("lellipse", intConsumer4Convert(graph::lellipse));
        Keyword.put("ellipse", intConsumer4Convert(graph::ellipse));
        Keyword.put("lrect", intConsumer4Convert(graph::lrect));
        Keyword.put("rect", intConsumer4Convert(graph::rect));
        Keyword.put("clip", intConsumer4Convert(graph::clip));
        Keyword.put("text", new DrawText());
        Keyword.put("fill", new fill());
        Keyword.put("Redraw", new Redraw());

        lastKey = MINUS_ONE;
        mouseHover = new LzrArray(new LzrValue[]{LzrNumber.ZERO, LzrNumber.ZERO});
    }


    private static Function intConsumer4Convert(IntConsumer4 consumer) {
        return args -> {
            if (args.length != 4) throw new RuntimeException("Four args expected");
            int x = (int) args[0].asNumber();
            int y = (int) args[1].asNumber();
            int w = (int) args[2].asNumber();
            int h = (int) args[3].asNumber();
            consumer.accept(x, y, w, h);
            return LzrNumber.ZERO;
        };
    }

    @FunctionalInterface
    private interface IntConsumer4 {
        void accept(int i1, int i2, int i3, int i4);
    }

    private static class CanvasPanel extends JPanel {

        public CanvasPanel(int width, int height) {
            setPreferredSize(new Dimension(width, height));
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphics = img.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            setFocusable(true);
            requestFocus();
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    lastKey = new LzrNumber(e.getKeyCode());
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    lastKey = MINUS_ONE;
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseHover.set(0, new LzrNumber(e.getX()));
                    mouseHover.set(1, new LzrNumber(e.getY()));

                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);

        }
    }



    private static class Frame implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            String title = "";
            int width = 640;
            int height = 480;
            switch (args.length) {
                case 1:
                    title = String.valueOf(args[0].raw());
                    break;
                case 2:
                    title = "";
                    width = (int) args[0].asNumber();
                    height = (int) args[1].asNumber();
                    break;
                case 3:
                    title = String.valueOf(args[0].raw());
                    width = (int) args[1].asNumber();
                    height = (int) args[2].asNumber();
                    break;
            }
            panel = new CanvasPanel(width, height);

            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            return LzrNumber.ZERO;
        }
    }

    private static class KeyPressed implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            return lastKey;
        }
    }

    private static class MouseHover implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            return mouseHover;
        }
    }

    private static class DrawText implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            if (args.length != 3) throw new RuntimeException("Four args expected");
            int x = (int) args[1].asNumber();
            int y = (int) args[2].asNumber();
            graphics.drawString(String.valueOf(args[0].raw()), x, y);
            return LzrNumber.ZERO;
        }
    }


    private static class Redraw implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            panel.invalidate();
            panel.repaint();
            return LzrNumber.ZERO;
        }
    }

    private static class translate implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            graphics.translate(args[0].asNumber(),args[1].asNumber());
            return LzrNumber.ZERO;
        }
    }

    private static class scale implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            graphics.scale(args[0].asNumber(),args[1].asNumber());
            return LzrNumber.ZERO;
        }
    }

    private static class font implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
           graphics.setFont(new Font(args[0].asString(), 0, args[1].asInt()));
            return LzrNumber.ZERO;
        }
    }


    private static class dispose implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            graphics.dispose();
            return LzrNumber.ZERO;
        }
    }


    private static class rotate implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            if (args.length == 1) {
                graphics.rotate(args[0].asNumber());
                return LzrNumber.ZERO;
            } else if (args.length == 3) {
                graphics.rotate(args[0].asNumber(), args[1].asNumber(), args[2].asNumber());
            } else{
                if (args.length >= 3) throw new RuntimeException("Three args expected");
            }
            return LzrNumber.ZERO;
        }
    }


    private static class background implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            if (args.length == 1) {
                int rgb = (int) args[0].asNumber();
                panel.setBackground(new Color(rgb));
                return LzrNumber.ZERO;
            }
            int r = (int) args[0].asNumber();
            int g = (int) args[1].asNumber();
            int b = (int) args[2].asNumber();
            panel.setBackground(new Color(r, g, b));
            return LzrNumber.ZERO;
        }
    }

    private static class fill implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            if (args.length == 1) {
                graphics.setColor(new Color((int) args[0].asNumber()));
                return LzrNumber.ZERO;
            }
            int r = (int) args[0].asNumber();
            int g = (int) args[1].asNumber();
            int b = (int) args[2].asNumber();
            graphics.setColor(new Color(r, g, b));
            return LzrNumber.ZERO;
        }
    }

    private static void line(int x1, int y1, int x2, int y2) {
        graphics.drawLine(x1, y1, x2, y2);

    }
    private static void lellipse(int x, int y, int w, int h) {
        graphics.drawOval(x, y, w, h);
    }
    private static void ellipse(int x, int y, int w, int h) {
        graphics.fillOval(x, y, w, h);
    }
    private static void lrect(int x, int y, int w, int h) {
        graphics.drawRect(x, y, w, h);
    }
    private static void cube(int x, int y, int w, int h) {
        graphics.draw3DRect(x,y,w,h,true);
    }

    private static void fill3d(int x, int y, int w, int h) {graphics.fill3DRect(x,y,w,h,true);}
    private static void rect(int x, int y, int w, int h) {graphics.fillRect(x, y, w, h);}
    private static void clip(int x, int y, int w, int h) {
        graphics.setClip(x, y, w, h);
    }
}