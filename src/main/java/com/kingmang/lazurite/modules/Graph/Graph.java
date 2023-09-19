package com.kingmang.lazurite.modules.Graph;

import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.modules.Module;
import com.kingmang.lazurite.runtime.ArrayValue;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Graph implements Module {

    private static final NumberValue MINUS_ONE = new NumberValue(-1);

    private static JFrame frame;
    private static CanvasPanel panel;
    private static Graphics2D graphics;
    private static BufferedImage img;

    private static NumberValue lastKey;
    private static ArrayValue mouseHover;

    public void init() {
        KEYWORD.put("Frame", new CreateWindow());
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
        KEYWORD.put("fill", new SetColor());
        KEYWORD.put("Redraw", (Function) new Redraw());
        lastKey = MINUS_ONE;
        mouseHover = new ArrayValue(new Value[]{NumberValue.ZERO, NumberValue.ZERO});
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

    private static void fill3d(int x, int y, int w, int h) {
        graphics.fill3DRect(x,y,w,h,true);
    }

    private static void rect(int x, int y, int w, int h) {
        graphics.fillRect(x, y, w, h);
    }

    private static void clip(int x, int y, int w, int h) {
        graphics.setClip(x, y, w, h);
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

    private static class CreateWindow implements Function {

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

    private static class SetColor implements Function {

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

}