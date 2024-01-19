package com.kingmang.lazurite.libraries.graph;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class graph implements Library {

    private static final LZRNumber MINUS_ONE = new LZRNumber(-1);

    private static JFrame frame;
    private static CanvasPanel panel;
    private static Graphics2D graphics;
    private static BufferedImage img;
    static BufferedImage image;

    private static LZRNumber lastKey;
    private static LZRArray mouseHover;

    public static void initColors() {
        Variables.set("RED", new LZRNumber(16711688));
        Variables.set("GREEN", new LZRNumber(65309));
        Variables.set("BLUE", new LZRNumber(5887));
        Variables.set("WHITE", new LZRNumber(16777215));
        Variables.set("BLACK", new LZRNumber(0));
        Variables.set("PURPLE", new LZRNumber(9109759));
        Variables.set("PINK", new LZRNumber(16761037));
        Variables.set("YELLOW", new LZRNumber(16776960));
    }

    public static void initKeys() {
        LZRMap keys = new LZRMap(70);
        keys.set("UP", new LZRNumber(KeyEvent.VK_UP));
        keys.set("DOWN", new LZRNumber(KeyEvent.VK_DOWN));
        keys.set("LEFT", new LZRNumber(KeyEvent.VK_LEFT));
        keys.set("RIGHT", new LZRNumber(KeyEvent.VK_RIGHT));

        keys.set("0", new LZRNumber(KeyEvent.VK_0));
        keys.set("1", new LZRNumber(KeyEvent.VK_1));
        keys.set("2", new LZRNumber(KeyEvent.VK_2));
        keys.set("3", new LZRNumber(KeyEvent.VK_3));
        keys.set("4", new LZRNumber(KeyEvent.VK_4));
        keys.set("5", new LZRNumber(KeyEvent.VK_5));
        keys.set("6", new LZRNumber(KeyEvent.VK_6));
        keys.set("7", new LZRNumber(KeyEvent.VK_7));
        keys.set("8", new LZRNumber(KeyEvent.VK_8));
        keys.set("9", new LZRNumber(KeyEvent.VK_9));

        keys.set("A", new LZRNumber(KeyEvent.VK_A));
        keys.set("B", new LZRNumber(KeyEvent.VK_B));
        keys.set("C", new LZRNumber(KeyEvent.VK_C));
        keys.set("D", new LZRNumber(KeyEvent.VK_D));
        keys.set("E", new LZRNumber(KeyEvent.VK_E));
        keys.set("F", new LZRNumber(KeyEvent.VK_F));
        keys.set("G", new LZRNumber(KeyEvent.VK_G));
        keys.set("H", new LZRNumber(KeyEvent.VK_H));
        keys.set("I", new LZRNumber(KeyEvent.VK_I));
        keys.set("J", new LZRNumber(KeyEvent.VK_J));
        keys.set("K", new LZRNumber(KeyEvent.VK_K));
        keys.set("L", new LZRNumber(KeyEvent.VK_L));
        keys.set("M", new LZRNumber(KeyEvent.VK_M));
        keys.set("N", new LZRNumber(KeyEvent.VK_N));
        keys.set("O", new LZRNumber(KeyEvent.VK_O));
        keys.set("P", new LZRNumber(KeyEvent.VK_P));
        keys.set("Q", new LZRNumber(KeyEvent.VK_Q));
        keys.set("R", new LZRNumber(KeyEvent.VK_R));
        keys.set("S", new LZRNumber(KeyEvent.VK_S));
        keys.set("T", new LZRNumber(KeyEvent.VK_T));
        keys.set("U", new LZRNumber(KeyEvent.VK_U));
        keys.set("V", new LZRNumber(KeyEvent.VK_V));
        keys.set("W", new LZRNumber(KeyEvent.VK_W));
        keys.set("X", new LZRNumber(KeyEvent.VK_X));
        keys.set("Y", new LZRNumber(KeyEvent.VK_Y));
        keys.set("Z", new LZRNumber(KeyEvent.VK_Z));

        keys.set("TAB", new LZRNumber(KeyEvent.VK_TAB));
        keys.set("CAPS_LOCK", new LZRNumber(KeyEvent.VK_CAPS_LOCK));
        keys.set("CONTROL", new LZRNumber(KeyEvent.VK_CONTROL));
        keys.set("ENTER", new LZRNumber(KeyEvent.VK_ENTER));
        keys.set("ESCAPE", new LZRNumber(KeyEvent.VK_ESCAPE));

        keys.set("F1", new LZRNumber(KeyEvent.VK_F1));
        keys.set("F2", new LZRNumber(KeyEvent.VK_F2));
        keys.set("F3", new LZRNumber(KeyEvent.VK_F3));
        keys.set("F4", new LZRNumber(KeyEvent.VK_F4));
        keys.set("F5", new LZRNumber(KeyEvent.VK_F5));
        keys.set("F6", new LZRNumber(KeyEvent.VK_F6));
        keys.set("F7", new LZRNumber(KeyEvent.VK_F7));
        keys.set("F8", new LZRNumber(KeyEvent.VK_F8));
        keys.set("F9", new LZRNumber(KeyEvent.VK_F9));
        keys.set("F10", new LZRNumber(KeyEvent.VK_F10));
        keys.set("F11", new LZRNumber(KeyEvent.VK_F11));
        keys.set("F12", new LZRNumber(KeyEvent.VK_F12));
        Variables.define("KEY", keys);
    }
    public void initConstant(){
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
        Keyword.put("cube", intConsumer4Convert(graph::Cude));
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
        Keyword.put("Redraw", (Function) new Redraw());
        lastKey = MINUS_ONE;
        mouseHover = new LZRArray(new Value[]{LZRNumber.ZERO, LZRNumber.ZERO});
    }


    private static Function intConsumer4Convert(IntConsumer4 consumer) {
        return args -> {
            if (args.length != 4) throw new RuntimeException("Four args expected");
            int x = (int) args[0].asNumber();
            int y = (int) args[1].asNumber();
            int w = (int) args[2].asNumber();
            int h = (int) args[3].asNumber();
            consumer.accept(x, y, w, h);
            return LZRNumber.ZERO;
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
                    lastKey = new LZRNumber(e.getKeyCode());
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    lastKey = MINUS_ONE;
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseHover.set(0, new LZRNumber(e.getX()));
                    mouseHover.set(1, new LZRNumber(e.getY()));
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
        public Value execute(Value... args) {
            String title = "";
            int width = 640;
            int height = 480;
            switch (args.length) {
                case 1:
                    title = args[0].raw().toString();
                    break;
                case 2:
                    title = "";
                    width = (int) args[0].asNumber();
                    height = (int) args[1].asNumber();
                    break;
                case 3:
                    title = args[0].raw().toString();
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
            return LZRNumber.ZERO;
        }
    }

    private static class KeyPressed implements Function {
        @Override
        public Value execute(Value... args) {
            return lastKey;
        }
    }

    private static class LImage implements Function{
        @Override
        public Value execute(Value... args) {
            try {
                File file = new File(args[0].asString());
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return LZRNumber.ZERO;
        }
    }

    private static class image implements Function{

        @Override
        public Value execute(Value... args) {
            graphics.drawImage(image,args[0].asInt(),args[1].asInt(),100,100,null);
            return LZRNumber.ZERO;
        }
    }
    private static class MouseHover implements Function {

        @Override
        public Value execute(Value... args) {
            return mouseHover;
        }
    }

    private static class DrawText implements Function {

        @Override
        public Value execute(Value... args) {
            if (args.length != 3) throw new RuntimeException("Four args expected");
            int x = (int) args[1].asNumber();
            int y = (int) args[2].asNumber();
            graphics.drawString(args[0].raw().toString(), x, y);
            return LZRNumber.ZERO;
        }
    }


    private static class Redraw implements Function {
        @Override
        public Value execute(Value... args) {
            panel.invalidate();
            panel.repaint();
            return LZRNumber.ZERO;
        }
    }

    private static class translate implements Function {
        @Override
        public Value execute(Value... args) {
            graphics.translate(args[0].asNumber(),args[1].asNumber());
            return LZRNumber.ZERO;
        }
    }

    private static class scale implements Function {
        @Override
        public Value execute(Value... args) {
            graphics.scale(args[0].asNumber(),args[1].asNumber());
            return LZRNumber.ZERO;
        }
    }

    private static class font implements Function {
        @Override
        public Value execute(Value... args) {
           graphics.setFont(new Font(args[0].asString(), 0, args[1].asInt()));
            return LZRNumber.ZERO;
        }
    }


    private static class dispose implements Function {
        @Override
        public Value execute(Value... args) {
            graphics.dispose();
            return LZRNumber.ZERO;
        }
    }




    private static class rotate implements Function {
        @Override
        public Value execute(Value... args) {
            if (args.length == 1) {
                graphics.rotate(args[0].asNumber());
                return LZRNumber.ZERO;
            } else if (args.length == 3) {
                graphics.rotate(args[0].asNumber(), args[1].asNumber(), args[2].asNumber());
            }else{
                if (args.length >= 3) throw new RuntimeException("Three args expected");
            }
            return LZRNumber.ZERO;
        }
    }


    private static class background implements Function {

        @Override
        public Value execute(Value... args) {
            if (args.length == 1) {
                int rgb = (int) args[0].asNumber();
                panel.setBackground(new Color(rgb));
                return LZRNumber.ZERO;
            }
            int r = (int) args[0].asNumber();
            int g = (int) args[1].asNumber();
            int b = (int) args[2].asNumber();
            panel.setBackground(new Color(r, g, b));
            return LZRNumber.ZERO;
        }
    }

    private static class fill implements Function {

        @Override
        public Value execute(Value... args) {
            if (args.length == 1) {
                graphics.setColor(new Color((int) args[0].asNumber()));
                return LZRNumber.ZERO;
            }
            int r = (int) args[0].asNumber();
            int g = (int) args[1].asNumber();
            int b = (int) args[2].asNumber();
            graphics.setColor(new Color(r, g, b));
            return LZRNumber.ZERO;
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
    private static void Cude(int x, int y, int w, int h) {
        graphics.draw3DRect(x,y,w,h,true);
    }
    private static void fill3d(int x, int y, int w, int h) {graphics.fill3DRect(x,y,w,h,true);}
    private static void rect(int x, int y, int w, int h) {graphics.fillRect(x, y, w, h);}
    private static void clip(int x, int y, int w, int h) {
        graphics.setClip(x, y, w, h);
    }
}