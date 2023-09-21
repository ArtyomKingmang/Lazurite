package com.kingmang.lazurite.modules.Graph;

import com.kingmang.lazurite.LZREx.LZRExeption;
import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.lib.Functions;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.ArrayValue;
import com.kingmang.lazurite.runtime.NumberValue;
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
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Graph implements Module {

    private static final NumberValue MINUS_ONE = new NumberValue(-1);

    private static JFrame frame;
    private static CanvasPanel panel;
    private static Graphics2D graphics;
    private static BufferedImage img;

    private static NumberValue lastKey;
    private static ArrayValue mouseHover;

    public static void initColors() {
        Variables.set("RED", new NumberValue(16711688));
        Variables.set("GREEN", new NumberValue(65309));
        Variables.set("BLUE", new NumberValue(5887));
        Variables.set("WHITE", new NumberValue(16777215));
        Variables.set("BLACK", new NumberValue(0));
        Variables.set("PURPLE", new NumberValue(9109759));
        Variables.set("PINK", new NumberValue(16761037));
        Variables.set("YELLOW", new NumberValue(16776960));
    }

    public static void initKeys() {
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
        Variables.set("Key_CAPS_LOCK", new NumberValue(KeyEvent.VK_CAPS_LOCK));
        Variables.set("Key_CONTROL", new NumberValue(KeyEvent.VK_CONTROL));
        Variables.set("Key_ENTER", new NumberValue(KeyEvent.VK_ENTER));
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
    public void init() {
        initColors();
        initKeys();
        KEYWORD.put("background", new background());
        KEYWORD.put("LImage", new LImage());
        KEYWORD.put("image", new image());
        KEYWORD.put("rotate", new rotate());
        KEYWORD.put("scale", new scale());
        KEYWORD.put("font", new font());
        KEYWORD.put("stroke", new stroke());
        KEYWORD.put("translate", new translate());
        KEYWORD.put("Frame", new Frame());
        KEYWORD.put("fill3d", intConsumer4Convert(Graph::fill3d));
        KEYWORD.put("cube", intConsumer4Convert(Graph::Cude));
        KEYWORD.put("keyPressed", new KeyPressed());
        KEYWORD.put("mouseHover", new MouseHover());
        KEYWORD.put("line", intConsumer4Convert(Graph::line));
        KEYWORD.put("lellipse", intConsumer4Convert(Graph::lellipse));
        KEYWORD.put("ellipse", intConsumer4Convert(Graph::ellipse));
        KEYWORD.put("lrect", intConsumer4Convert(Graph::lrect));
        KEYWORD.put("rect", intConsumer4Convert(Graph::rect));
        KEYWORD.put("clip", intConsumer4Convert(Graph::clip));
        KEYWORD.put("text", new DrawText());
        KEYWORD.put("fill", new fill());
        KEYWORD.put("Redraw", (Function) new Redraw());
        lastKey = MINUS_ONE;
        mouseHover = new ArrayValue(new Value[]{NumberValue.ZERO, NumberValue.ZERO});
    }


    private static Function intConsumer4Convert(IntConsumer4 consumer) {
        return args -> {
            if (args.length != 4) throw new RuntimeException("Four args expected");
            int x = (int) args[0].asNumber();
            int y = (int) args[1].asNumber();
            int w = (int) args[2].asNumber();
            int h = (int) args[3].asNumber();
            consumer.accept(x, y, w, h);
            return NumberValue.ZERO;
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
                    lastKey = new NumberValue(e.getKeyCode());
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    lastKey = MINUS_ONE;
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseHover.set(0, new NumberValue(e.getX()));
                    mouseHover.set(1, new NumberValue(e.getY()));
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
                    title = "Simple Application";
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
            return NumberValue.ZERO;
        }
    }

    private static class KeyPressed implements Function {
        @Override
        public Value execute(Value... args) {
            return lastKey;
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
            return NumberValue.ZERO;
        }
    }


    private static class Redraw implements Function {
        @Override
        public Value execute(Value... args) {
            panel.invalidate();
            panel.repaint();
            return NumberValue.ZERO;
        }
    }

    private static class translate implements Function {
        @Override
        public Value execute(Value... args) {
            graphics.translate(args[0].asNumber(),args[1].asNumber());
            return NumberValue.ZERO;
        }
    }

    private static class scale implements Function {
        @Override
        public Value execute(Value... args) {
            graphics.scale(args[0].asNumber(),args[1].asNumber());
            return NumberValue.ZERO;
        }
    }

    private static class font implements Function {
        @Override
        public Value execute(Value... args) {
           graphics.setFont((Font) args[0]);
            return NumberValue.ZERO;
        }
    }
    private static class stroke implements Function {
        @Override
        public Value execute(Value... args) {
            graphics.setStroke((Stroke) args[0]);
            return NumberValue.ZERO;
        }
    }
    private static class LImage implements Function {
        @Override
        public Value execute(Value... args) {
            try {
                ImageIO.read(new File("C:\\Projects\\MavenSandbox\\src\\main\\resources\\img.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return NumberValue.ZERO;
        }
    }

    private static class image implements Function {
        @Override
        public Value execute(Value... args) {


            int x = (int) args[1].asNumber();
            int y = (int) args[2].asNumber();
            if (args.length == 3){
                graphics.drawImage((Image) args[0], x, y, null);
            }else if(args.length == 4) {
                graphics.drawImage((Image) args[0], x, y, (ImageObserver) args[3]);
            }else{
                throw new LZRExeption("RuntimeExeption", "Three args expected");
            }
            return NumberValue.ZERO;
        }
    }



    private static class rotate implements Function {
        @Override
        public Value execute(Value... args) {
            if (args.length == 1) {
                graphics.rotate(args[0].asNumber());
                return NumberValue.ZERO;
            } else if (args.length == 3) {
                graphics.rotate(args[0].asNumber(), args[1].asNumber(), args[3].asNumber());
            }else{
                if (args.length >= 3) throw new RuntimeException("Three args expected");
            }
            return NumberValue.ZERO;
        }
    }


    private static class background implements Function {

        @Override
        public Value execute(Value... args) {
            if (args.length == 1) {
                panel.setBackground(new Color((int) args[0].asNumber()));
                return NumberValue.ZERO;
            }
            int r = (int) args[0].asNumber();
            int g = (int) args[1].asNumber();
            int b = (int) args[2].asNumber();
            panel.setBackground(new Color(r, g, b));
            return NumberValue.ZERO;
        }
    }

    private static class fill implements Function {

        @Override
        public Value execute(Value... args) {
            if (args.length == 1) {
                graphics.setColor(new Color((int) args[0].asNumber()));
                return NumberValue.ZERO;
            }
            int r = (int) args[0].asNumber();
            int g = (int) args[1].asNumber();
            int b = (int) args[2].asNumber();
            graphics.setColor(new Color(r, g, b));
            return NumberValue.ZERO;
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