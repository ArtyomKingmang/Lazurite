package com.kingmang.lazurite.libraries.graph;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
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
        Variables.set("Key_UP", new LZRNumber(KeyEvent.VK_UP));
        Variables.set("Key_DOWN", new LZRNumber(KeyEvent.VK_DOWN));
        Variables.set("Key_LEFT", new LZRNumber(KeyEvent.VK_LEFT));
        Variables.set("Key_RIGHT", new LZRNumber(KeyEvent.VK_RIGHT));

        Variables.set("Key_0", new LZRNumber(KeyEvent.VK_0));
        Variables.set("Key_1", new LZRNumber(KeyEvent.VK_1));
        Variables.set("Key_2", new LZRNumber(KeyEvent.VK_2));
        Variables.set("Key_3", new LZRNumber(KeyEvent.VK_3));
        Variables.set("Key_4", new LZRNumber(KeyEvent.VK_4));
        Variables.set("Key_5", new LZRNumber(KeyEvent.VK_5));
        Variables.set("Key_6", new LZRNumber(KeyEvent.VK_6));
        Variables.set("Key_7", new LZRNumber(KeyEvent.VK_7));
        Variables.set("Key_8", new LZRNumber(KeyEvent.VK_8));
        Variables.set("Key_9", new LZRNumber(KeyEvent.VK_9));

        Variables.set("Key_A", new LZRNumber(KeyEvent.VK_A));
        Variables.set("Key_B", new LZRNumber(KeyEvent.VK_B));
        Variables.set("Key_C", new LZRNumber(KeyEvent.VK_C));
        Variables.set("Key_D", new LZRNumber(KeyEvent.VK_D));
        Variables.set("Key_E", new LZRNumber(KeyEvent.VK_E));
        Variables.set("Key_F", new LZRNumber(KeyEvent.VK_F));
        Variables.set("Key_G", new LZRNumber(KeyEvent.VK_G));
        Variables.set("Key_H", new LZRNumber(KeyEvent.VK_H));
        Variables.set("Key_I", new LZRNumber(KeyEvent.VK_I));
        Variables.set("Key_J", new LZRNumber(KeyEvent.VK_J));
        Variables.set("Key_K", new LZRNumber(KeyEvent.VK_K));
        Variables.set("Key_L", new LZRNumber(KeyEvent.VK_L));
        Variables.set("Key_M", new LZRNumber(KeyEvent.VK_M));
        Variables.set("Key_N", new LZRNumber(KeyEvent.VK_N));
        Variables.set("Key_O", new LZRNumber(KeyEvent.VK_O));
        Variables.set("Key_P", new LZRNumber(KeyEvent.VK_P));
        Variables.set("Key_Q", new LZRNumber(KeyEvent.VK_Q));
        Variables.set("Key_R", new LZRNumber(KeyEvent.VK_R));
        Variables.set("Key_S", new LZRNumber(KeyEvent.VK_S));
        Variables.set("Key_T", new LZRNumber(KeyEvent.VK_T));
        Variables.set("Key_U", new LZRNumber(KeyEvent.VK_U));
        Variables.set("Key_V", new LZRNumber(KeyEvent.VK_V));
        Variables.set("Key_W", new LZRNumber(KeyEvent.VK_W));
        Variables.set("Key_X", new LZRNumber(KeyEvent.VK_X));
        Variables.set("Key_Y", new LZRNumber(KeyEvent.VK_Y));
        Variables.set("Key_Z", new LZRNumber(KeyEvent.VK_Z));

        Variables.set("Key_TAB", new LZRNumber(KeyEvent.VK_TAB));
        Variables.set("Key_CAPS_LOCK", new LZRNumber(KeyEvent.VK_CAPS_LOCK));
        Variables.set("Key_CONTROL", new LZRNumber(KeyEvent.VK_CONTROL));
        Variables.set("Key_ENTER", new LZRNumber(KeyEvent.VK_ENTER));
        Variables.set("Key_ESCAPE", new LZRNumber(KeyEvent.VK_ESCAPE));

        Variables.set("Key_F1", new LZRNumber(KeyEvent.VK_F1));
        Variables.set("Key_F2", new LZRNumber(KeyEvent.VK_F2));
        Variables.set("Key_F3", new LZRNumber(KeyEvent.VK_F3));
        Variables.set("Key_F4", new LZRNumber(KeyEvent.VK_F4));
        Variables.set("Key_F5", new LZRNumber(KeyEvent.VK_F5));
        Variables.set("Key_F6", new LZRNumber(KeyEvent.VK_F6));
        Variables.set("Key_F7", new LZRNumber(KeyEvent.VK_F7));
        Variables.set("Key_F8", new LZRNumber(KeyEvent.VK_F8));
        Variables.set("Key_F9", new LZRNumber(KeyEvent.VK_F9));
        Variables.set("Key_F10", new LZRNumber(KeyEvent.VK_F10));
        Variables.set("Key_F11", new LZRNumber(KeyEvent.VK_F11));
        Variables.set("Key_F12", new LZRNumber(KeyEvent.VK_F12));
    }
    public void initConstant(){
        initColors();
        initKeys();
    }
    public void init() {
        initConstant();
        Keyword.put("background", new background());
        Keyword.put("dispose", new background());
        Keyword.put("dispose", new dispose());
        Keyword.put("LImage", new LImage());
        Keyword.put("image", new image());
        Keyword.put("rotate", new rotate());
        Keyword.put("scale", new scale());
        Keyword.put("font", new font());
        Keyword.put("stroke", new stroke());
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
            if (args.length != 3) throw new RuntimeException("Three args expected");
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
           graphics.setFont((Font) args[0]);
            return LZRNumber.ZERO;
        }
    }
    private static class stroke implements Function {
        @Override
        public Value execute(Value... args) {
            graphics.setStroke((Stroke) args[0]);
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
                graphics.rotate(args[0].asNumber(), args[1].asNumber(), args[3].asNumber());
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