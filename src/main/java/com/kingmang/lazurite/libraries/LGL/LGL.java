package com.kingmang.lazurite.libraries.LGL;

import com.kingmang.lazurite.base.*;
import com.kingmang.lazurite.libraries.libraries;
import static com.kingmang.lazurite.base.Converters.*;
import java.awt.Dimension;
import java.lang.reflect.Modifier;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.kingmang.lazurite.runtime.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.TextAlignment;
import javax.swing.JFrame;


public final class LGL implements libraries {
    
    private static final int FX_EFFECT_TYPE = 5301;
    private static final int FX_COLOR_TYPE = 5302;
    
    private static JFrame frame;
    private static JFXPanel panel;
    private static GraphicsContext graphics;
    private static Canvas canvas;
    
    private enum Events {

        MOUSE_CLICKED(MouseEvent.MOUSE_CLICKED),

        MOUSE_MOVED(MouseEvent.MOUSE_MOVED),
        MOUSE_PRESSED(MouseEvent.MOUSE_PRESSED),
        MOUSE_RELEASED(MouseEvent.MOUSE_RELEASED),
        
        KEY_PRESSED(KeyEvent.KEY_PRESSED),
        KEY_RELEASED(KeyEvent.KEY_RELEASED),
        KEY_TYPED(KeyEvent.KEY_TYPED);
        


        private final EventType<? extends Event> handler;

        private Events(EventType<? extends Event> handler) {
            this.handler = handler;
        }

        public EventType<? extends Event> getHandler() {

            return handler;
        }
    }

    public static void initConstants() {
        // Color class
        final Map<Value, Value> colors = Arrays.stream(Color.class.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f -> f.getType().equals(Color.class))
                .collect(Collectors.toMap(
                        f -> new StringValue(f.getName()),
                        f -> {
                            try { return new ColorValue((Color) f.get(Color.class)); }
                            catch (IllegalAccessException ex) { return null; }
                        }));

        colors.put(new StringValue("rgb"), new FunctionValue(new rgbColor()));
        colors.put(new StringValue("hsb"), new FunctionValue(new hsbColor()));
        colors.put(new StringValue("web"), new FunctionValue(new webColor()));
        Variables.define("Color", new MapValue(colors));

        final MapValue arcType = new MapValue(ArcType.values().length);
        for (ArcType value : ArcType.values()) {
            arcType.set(value.name(), NumberValue.of(value.ordinal()));
        }


        final MapValue fillRule = new MapValue(FillRule.values().length);
        for (FillRule value : FillRule.values()) {
            fillRule.set(value.name(), NumberValue.of(value.ordinal()));
        }


        final MapValue blendMode = new MapValue(BlendMode.values().length);
        for (BlendMode value : BlendMode.values()) {
            blendMode.set(value.name(), NumberValue.of(value.ordinal()));
        }


        final MapValue lineCap = new MapValue(StrokeLineCap.values().length);
        for (StrokeLineCap value : StrokeLineCap.values()) {
            lineCap.set(value.name(), NumberValue.of(value.ordinal()));
        }


        final MapValue lineJoin = new MapValue(StrokeLineJoin.values().length);
        for (StrokeLineJoin value : StrokeLineJoin.values()) {
            lineJoin.set(value.name(), NumberValue.of(value.ordinal()));
        }


        final MapValue textAlignment = new MapValue(TextAlignment.values().length);
        for (TextAlignment value : TextAlignment.values()) {
            textAlignment.set(value.name(), NumberValue.of(value.ordinal()));
        }


        final MapValue vPos = new MapValue(VPos.values().length);
        for (VPos value : VPos.values()) {
            vPos.set(value.name(), NumberValue.of(value.ordinal()));
        }
        Variables.define("VPos", vPos);

        final MapValue events = new MapValue(Events.values().length);
        for (Events value : Events.values()) {
            events.set(value.name(), NumberValue.of(value.ordinal()));
        }
        Variables.define("Events", events);

        final MapValue mouseButton = new MapValue(MouseButton.values().length);
        for (MouseButton value : MouseButton.values()) {
            mouseButton.set(value.name(), NumberValue.of(value.ordinal()));
        }
        Variables.define("MouseButton", mouseButton);

        final MapValue keyCodes = new MapValue(KeyCode.values().length);
        for (KeyCode value : KeyCode.values()) {
            keyCodes.set(value.name(), NumberValue.of(value.ordinal()));
        }
        Variables.define("KeyCode", keyCodes);
    }

    @Override
    public void init() {
        initConstants();
        KEYWORD.put("LWindow", new CreateWindow());
        KEYWORD.put("Redraw", new Redraw());
        
        KEYWORD.put("Blend", new BlendEffect());
        KEYWORD.put("Bloom", new BloomEffect());
        KEYWORD.put("BoxBlur", new BoxBlurEffect());
        KEYWORD.put("ColorAdjust", new ColorAdjustEffect());
        KEYWORD.put("ColorInput", new ColorInputEffect());
        KEYWORD.put("DropShadow", new DropShadowEffect());
        KEYWORD.put("GaussianBlur", new GaussianBlurEffect());
        KEYWORD.put("Glow", new GlowEffect());
        KEYWORD.put("InnerShadow", new InnerShadowEffect());
        KEYWORD.put("Lighting", new LightingEffect());
        KEYWORD.put("MotionBlur", new MotionBlurEffect());
        KEYWORD.put("PerspectiveTransform", new PerspectiveTransformEffect());
        KEYWORD.put("Reflection", new ReflectionEffect());
        KEYWORD.put("SepiaTone", new SepiaToneEffect());
        KEYWORD.put("Shadow", new ShadowEffect());
        
        KEYWORD.put("EFilter", new addEventFilter());
        KEYWORD.put("EHandler", new addEventHandler());
        KEYWORD.put("loadImage", new createImage());
    }
    
    private static class ColorValue implements Value {
        
        private final Color color;
        
        public ColorValue(Color effect) {
            this.color = effect;
        }
        
        @Override
        public Object raw() {
            return color;
        }
        
        @Override
        public int asInt() {
            final int a = (int) (color.getOpacity() * 255) & 0xFF;
            final int r = (int) (color.getRed() * 255) & 0xFF;
            final int g = (int) (color.getGreen() * 255) & 0xFF;
            final int b = (int) (color.getBlue() * 255) & 0xFF;
            return ((a << 24) | (r << 16) | (g << 8) | b);
        }
        
        @Override
        public double asNumber() {
            return asInt();
        }
        
        @Override
        public String asString() {
            return color.toString();
        }
        
        @Override
        public int type() {
            return FX_COLOR_TYPE;
        }
        
        @Override
        public int compareTo(Value o) {
            // we don't need this
            return 0;
        }
        
        @Override
        public String toString() {
            return "JavaFX Color " + color;
        }
    }
    

    
    private static class rgbColor implements Function {

        @Override
        public Value execute(Value... args) {
            int r = args[0].asInt();
            int g = args[1].asInt();
            int b = args[2].asInt();
            double opacity = (args.length >= 4) ? args[3].asNumber() : 1d;
            return new ColorValue(Color.rgb(r, g, b, opacity));
        }
    }
    
    private static class hsbColor implements Function {

        @Override
        public Value execute(Value... args) {
            double h = args[0].asNumber();
            double s = args[1].asNumber();
            double b = args[2].asNumber();
            double opacity = (args.length >= 4) ? args[3].asNumber() : 1d;
            return new ColorValue(Color.hsb(h, s, b, opacity));
        }
    }
    
    private static class webColor implements Function {

        @Override
        public Value execute(Value... args) {
            return new ColorValue(Color.web(args[0].asString(),
                    (args.length >= 2) ? args[1].asNumber() : 1d ));
        }
    }
    

    private static class EffectValue implements Value {
        
        private final Effect effect;
        
        public EffectValue(Effect effect) {
            this.effect = effect;
        }
        
        @Override
        public Object raw() {
            return effect;
        }
        
        @Override
        public int asInt() {
            throw new _TExeprion("Cannot cast JavaFX Effect to integer");
        }
        
        @Override
        public double asNumber() {
            throw new _TExeprion("Cannot cast JavaFX Effect to number");
        }
        
        @Override
        public String asString() {
            return effect.toString();
        }
        
        @Override
        public int type() {
            return FX_EFFECT_TYPE;
        }
        
        @Override
        public int compareTo(Value o) {
            // we don't need this
            return 0;
        }
        
        @Override
        public String toString() {
            return "JavaFX Effect " + effect;
        }
    }
    
    private static class BlendEffect implements Function {
        @Override
        public Value execute(Value... args) {
            Blend effect = new Blend();
            if (args.length >= 1) {
                effect.setMode(BlendMode.values()[args[0].asInt()]);
            }
            if (args.length >= 3) {
                effect.setBottomInput((Effect)args[1].raw());
                effect.setTopInput((Effect)args[2].raw());
            }
            if (args.length >= 4) {
                effect.setOpacity(args[3].asNumber());
            }
            return new EffectValue(effect);
        }
    }
    
    private static class BloomEffect implements Function {
        @Override
        public Value execute(Value... args) {
            Bloom effect = new Bloom();
            if (args.length >= 1) {
                effect.setThreshold(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }
    
    private static class BoxBlurEffect implements Function {
        @Override
        public Value execute(Value... args) {
            BoxBlur effect = new BoxBlur();
            if (args.length >= 3) {
                effect.setWidth(args[0].asNumber());
                effect.setHeight(args[1].asNumber());
                effect.setIterations(args[2].asInt());
            }
            if (args.length >= 4) {
                effect.setInput((Effect)args[3].raw());
            }
            return new EffectValue(effect);
        }
    }
    
    private static class ColorAdjustEffect implements Function {
        @Override
        public Value execute(Value... args) {
            return new EffectValue(new ColorAdjust(
                    args[0].asNumber(), args[1].asNumber(), args[2].asNumber(), args[3].asNumber()));
        }
    }
    
    private static class ColorInputEffect implements Function {
        @Override
        public Value execute(Value... args) {
            return new EffectValue(new ColorInput(
                    args[0].asNumber(), args[1].asNumber(), args[2].asNumber(), args[3].asNumber(), (Color) args[4].raw()));
        }
    }
    
    private static class DropShadowEffect implements Function {
        @Override
        public Value execute(Value... args) {
            DropShadow effect;
            switch (args.length) {
                case 2:
                    effect = new DropShadow(args[0].asNumber(), (Color) args[1].raw());
                    break;
                case 4:
                    effect = new DropShadow(args[0].asNumber(),
                            args[1].asInt(), args[2].asInt(),
                            (Color) args[3].raw());
                    break;
                case 6:
                    effect = new DropShadow(BlurType.values()[args[0].asInt()], (Color) args[1].raw(),
                            args[2].asNumber(), args[3].asNumber(), args[4].asNumber(), args[5].asNumber());
                    break;
                default:
                    effect = new DropShadow();
            }
            return new EffectValue(effect);
        }
    }
    
    private static class GaussianBlurEffect implements Function {
        @Override
        public Value execute(Value... args) {
            GaussianBlur effect = new GaussianBlur();
            if (args.length >= 1) {
                effect.setRadius(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }
    
    private static class GlowEffect implements Function {
        @Override
        public Value execute(Value... args) {
            Glow effect = new Glow();
            if (args.length >= 1) {
                effect.setLevel(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }
    
    private static class InnerShadowEffect implements Function {
        @Override
        public Value execute(Value... args) {
            InnerShadow effect;
            switch (args.length) {
                case 2:
                    effect = new InnerShadow(args[0].asNumber(), (Color) args[1].raw());
                    break;
                case 4:
                    effect = new InnerShadow(args[0].asNumber(),
                            args[1].asInt(), args[2].asInt(),
                            (Color) args[3].raw());
                    break;
                case 6:
                    effect = new InnerShadow(BlurType.values()[args[0].asInt()], (Color) args[1].raw(),
                            args[2].asNumber(), args[3].asNumber(), args[4].asNumber(), args[5].asNumber());
                    break;
                default:
                    effect = new InnerShadow();
            }
            return new EffectValue(effect);
        }
    }
    
    private static class LightingEffect implements Function {
        @Override
        public Value execute(Value... args) {
            Light light;
            final ArrayValue l = (ArrayValue) args[0];
            switch (l.size()) {
                case 3:
                    light = new Light.Distant(l.get(0).asNumber(), l.get(1).asNumber(), (Color) l.get(2).raw());
                    break;
                case 4:
                    light = new Light.Point(l.get(0).asNumber(), l.get(1).asNumber(), l.get(2).asNumber(), (Color) l.get(3).raw());
                    break;
                case 5:
                    light = new Light.Spot(l.get(0).asNumber(), l.get(1).asNumber(), l.get(2).asNumber(), l.get(3).asNumber(), (Color) l.get(4).raw());
                    break;
                default:
                    light = null;
            }
            Lighting effect = new Lighting(light);
            if (args.length >= 2) {
                effect.setSurfaceScale(args[1].asNumber());
            }
            if (args.length >= 3) {
                effect.setDiffuseConstant(args[2].asNumber());
            }
            if (args.length >= 5) {
                effect.setSpecularConstant(args[3].asNumber());
                effect.setSpecularExponent(args[4].asNumber());
            }
            if (args.length >= 7) {
                effect.setBumpInput((Effect) args[5].raw());
                effect.setContentInput((Effect) args[6].raw());
            }
            return new EffectValue(effect);
        }
    }
    
    private static class MotionBlurEffect implements Function {
        @Override
        public Value execute(Value... args) {
            MotionBlur effect = new MotionBlur();
            if (args.length >= 2) {
                effect.setAngle(args[0].asNumber());
                effect.setRadius(args[1].asNumber());
            }
            if (args.length >= 3) {
                effect.setInput((Effect)args[2].raw());
            }
            return new EffectValue(effect);
        }
    }
    
    private static class PerspectiveTransformEffect implements Function {
        @Override
        public Value execute(Value... args) {
            return new EffectValue(new PerspectiveTransform(
                    args[0].asNumber(), args[1].asNumber(), args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber(), args[6].asNumber(), args[7].asNumber() ));
        }
    }
    
    private static class ReflectionEffect implements Function {
        @Override
        public Value execute(Value... args) {
            return new EffectValue(new Reflection(
                    args[0].asNumber(), args[1].asNumber(), args[2].asNumber(), args[3].asNumber()));
        }
    }
    
    private static class SepiaToneEffect implements Function {
        @Override
        public Value execute(Value... args) {
            SepiaTone effect = new SepiaTone();
            if (args.length >= 1) {
                effect.setLevel(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }
    
    private static class ShadowEffect implements Function {
        @Override
        public Value execute(Value... args) {
            Shadow effect;
            switch (args.length) {
                case 2:
                    effect = new Shadow(args[0].asNumber(), (Color) args[1].raw());
                    break;
                case 3:
                    effect = new Shadow(BlurType.values()[args[0].asInt()], (Color) args[1].raw(),
                            args[2].asNumber());
                    break;
                default:
                    effect = new Shadow();
            }
            return new EffectValue(effect);
        }
    }
//</editor-fold>
    
    private static class ImageFXValue extends MapValue {

        private final Image image;

        public ImageFXValue(Image image) {
            super(8);
            this.image = image;
            init();
        }

        private void init() {
            set("width", NumberValue.of(image.getWidth()));
            set("height", NumberValue.of(image.getHeight()));
            set("smooth", NumberValue.fromBoolean(image.isSmooth()));

        }


        @Override
        public String toString() {
            return "JavaFX Image " + image;
        }
    }

    private static class createImage implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkAtLeast(1, args.length);
            final Image result;
            switch (args.length) {
                case 1:

                    result = new Image(args[0].asString());
                    break;
                case 2:
                default:

                    result = new WritableImage(args[0].asInt(), args[1].asInt());
                    break;
                case 3:

                    final int w = args[0].asInt();
                    final int h = args[1].asInt();
                    final int size = w * h;
                    final WritableImage writableImage = new WritableImage(w, h);
                    final PixelWriter pw = writableImage.getPixelWriter();
                    final WritablePixelFormat<IntBuffer> format = WritablePixelFormat.getIntArgbInstance();
                    final int[] buffer = new int[size];
                    final ArrayValue array = (ArrayValue) args[2];
                    for (int i = 0; i < size; i++) {
                        buffer[i] = array.get(i).asInt();
                    }
                    pw.setPixels(0, 0, w, h, format, buffer, 0, w);
                    result = writableImage;

            }
            return new ImageFXValue(result);
        }
    }

    

    public static class GraphicsFXValue extends MapValue {

        private final GraphicsContext graphics;

        public GraphicsFXValue(GraphicsContext graphics) {
            super(64);
            this.graphics = graphics;
            init();
        }

        private void init() {
            set("setEffect", this::setEffect);
            set("LFill", this::LFill);
            set("setLineCap", this::setLineCap);
            set("setLineWidth", doubleToVoid(graphics::setLineWidth));
            set("setMiterLimit", doubleToVoid(graphics::setMiterLimit));
            set("setStroke", this::setStroke);
            set("setTextAlign", this::setTextAlign);
            set("setTextBaseline", this::setTextBaseline);
            set("stroke", voidToVoid(graphics::stroke));
            set("LLine", double4ToVoid(graphics::strokeLine));
            set("LEllipse", double4ToVoid(graphics::strokeOval));
            set("LPolygon", this::LPolygon);
            set("LRect", double4ToVoid(graphics::strokeRect));
            set("transform", this::transform);
            set("translate", double2ToVoid(graphics::translate));

            set("LImage", this::drawImage);
            set("LFEllipse", double4ToVoid(graphics::fillOval));
            set("LFPolygon", this::LFPolygon);
            set("LFRect", double4ToVoid(graphics::fillRect));
            set("LText", this::LText);

            set("moveTo", double2ToVoid(graphics::moveTo));

            set("rotate", doubleToVoid(graphics::rotate));
            set("save", voidToVoid(graphics::save));
            set("scale", double2ToVoid(graphics::scale));

        }


        private Value drawImage(Value... args) {
            Arguments.checkAtLeast(3, args.length);
            if (!(args[0] instanceof ImageFXValue)) {
                throw new _TExeprion("ImageFX expected");
            }
            final Image image = ((ImageFXValue) args[0]).image;

            if (args.length >= 9) {
                graphics.drawImage(image,
                        args[1].asNumber(), args[2].asNumber(),
                        args[3].asNumber(), args[4].asNumber(),
                        args[5].asNumber(), args[6].asNumber(),
                        args[7].asNumber(), args[8].asNumber()
                        );
                return NumberValue.ZERO;
            }

            if (args.length >= 5) {
                // x y w h
                graphics.drawImage(image,
                        args[1].asNumber(), args[2].asNumber(),
                        args[3].asNumber(), args[4].asNumber()
                        );
                return NumberValue.ZERO;
            }
            
            graphics.drawImage(image, args[1].asNumber(), args[2].asNumber());
            return NumberValue.ZERO;
        }


        private Value LFPolygon(Value... args) {
            final ArrayValue xarr = (ArrayValue) args[0];
            final ArrayValue yarr = (ArrayValue) args[1];

            final int size = xarr.size();
            final double[] xPoints = new double[size];
            final double[] yPoints = new double[size];
            for (int i = 0; i < size; i++) {
                xPoints[i] = xarr.get(i).asNumber();
                yPoints[i] = yarr.get(i).asNumber();
            }

            graphics.fillPolygon(xPoints, yPoints, args[2].asInt());
            return NumberValue.ZERO;
        }



        private Value LText(Value... args) {
            if (args.length < 4) {
                // str x y
                graphics.fillText(args[0].asString(), args[1].asNumber(),
                        args[2].asNumber());
            } else {
                graphics.fillText(args[0].asString(), args[1].asNumber(),
                        args[2].asNumber(), args[3].asNumber());
            }
            return NumberValue.ZERO;
        }





        private Value setEffect(Value... args) {
            if (args[0].type() != FX_EFFECT_TYPE) {
                throw new _TExeprion("Effect expected, found " + Types.typeToString(args[0].type()));
            }
            graphics.setEffect((Effect) args[0].raw());
            return NumberValue.ZERO;
        }

        private Value LFill(Value... args) {
            graphics.setFill((Color) args[0].raw());
            return NumberValue.ZERO;
        }



        private Value setLineCap(Value... args) {
            graphics.setLineCap(StrokeLineCap.values()[args[0].asInt()]);
            return NumberValue.ZERO;
        }



        private Value setStroke(Value... args) {
            graphics.setStroke((Color) args[0].raw());
            return NumberValue.ZERO;
        }

        private Value setTextAlign(Value... args) {
            graphics.setTextAlign(TextAlignment.values()[args[0].asInt()]);
            return NumberValue.ZERO;
        }

        private Value setTextBaseline(Value... args) {
            graphics.setTextBaseline(VPos.values()[args[0].asInt()]);
            return NumberValue.ZERO;
        }



        private Value LPolygon(Value... args) {
            final ArrayValue xarr = (ArrayValue) args[0];
            final ArrayValue yarr = (ArrayValue) args[1];

            final int size = xarr.size();
            final double[] xPoints = new double[size];
            final double[] yPoints = new double[size];
            for (int i = 0; i < size; i++) {
                xPoints[i] = xarr.get(i).asNumber();
                yPoints[i] = yarr.get(i).asNumber();
            }

            graphics.strokePolygon(xPoints, yPoints, args[2].asInt());
            return NumberValue.ZERO;
        }







        private Value transform(Value... args) {
            graphics.transform(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber());
            return NumberValue.ZERO;
        }

        @Override
        public String toString() {
            return "GraphicsFXValue " + asString();
        }
    }
    
    private static class CreateWindow implements Function {

        @Override
        public Value execute(Value... args) {
            String title = "";
            int width = 0;
            int height = 0;
            switch (args.length) {
                case 1:
                    title = args[0].asString();
                    break;
                case 2:
                    width = args[0].asInt();
                    height = args[1].asInt();
                    break;
                case 3:
                    title = args[0].asString();
                    width = args[1].asInt();
                    height = args[2].asInt();
                    break;
            }
            panel = new JFXPanel();
            panel.setPreferredSize(new Dimension(width, height));
            panel.setFocusable(true);
            canvas = new Canvas(width, height);
            canvas.setFocusTraversable(true);
            canvas.requestFocus();
            graphics = canvas.getGraphicsContext2D();
            
            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            
            Platform.runLater(() -> {
                Group root = new Group();
                Scene scene = new Scene(root, Color.WHITE);
                root.getChildren().add(canvas);
                panel.setScene(scene);
            });
            return new GraphicsFXValue(graphics);
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
    
    private static class addEventFilter implements Function {
        @Override
        public Value execute(Value... args) {
            final Function handler = ((FunctionValue) args[1]).getValue();
            final Events event = Events.values()[args[0].asInt()];
            canvas.addEventFilter(event.getHandler(), e -> handleEvent(e, handler));
            return NumberValue.ZERO;
        }
    }
    
    private static class addEventHandler implements Function {
        @Override
        public Value execute(Value... args) {
            final Function handler = ((FunctionValue) args[1]).getValue();
            final Events event = Events.values()[args[0].asInt()];
            canvas.addEventHandler(event.getHandler(), e -> handleEvent(e, handler));
            return NumberValue.ZERO;
        }
    }
    
    private static void handleEvent(Event event, final Function handler) {
        if (event instanceof MouseEvent) {
            handleMouseEvent((MouseEvent) event, handler);
        } else if (event instanceof KeyEvent) {
            handleKeyEvent((KeyEvent) event, handler);
        }
    }
    
    private static void handleMouseEvent(MouseEvent e, final Function handler) {
        final MapValue map = new MapValue(25);
        map.set("button", NumberValue.of(e.getButton().ordinal()));
        map.set("clickCount", NumberValue.of(e.getClickCount()));
        map.set("mouseX", NumberValue.of(e.getSceneX()));
        map.set("mouseY", NumberValue.of(e.getSceneY()));
        handler.execute(map);
    }
    
    private static void handleKeyEvent(final KeyEvent e, final Function handler) {
        final MapValue map = new MapValue(10);
        map.set("code", NumberValue.of(e.getCode().ordinal()));
        map.set("character", new StringValue(e.getCharacter()));
        map.set("isAltDown", NumberValue.fromBoolean(e.isAltDown()));
        map.set("isConsumed", NumberValue.fromBoolean(e.isConsumed()));
        map.set("isControlDown", NumberValue.fromBoolean(e.isControlDown()));
        map.set("isMetaDown", NumberValue.fromBoolean(e.isMetaDown()));
        map.set("isShiftDown", NumberValue.fromBoolean(e.isShiftDown()));
        map.set("isShortcutDown", NumberValue.fromBoolean(e.isShortcutDown()));
        handler.execute(map);
    }


    
}
