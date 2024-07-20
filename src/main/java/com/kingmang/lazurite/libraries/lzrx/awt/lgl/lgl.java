package com.kingmang.lazurite.libraries.lzrx.awt.lgl;


import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.color.Hsb;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.color.NewColor;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects.*;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.event.Events;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.image.LoadImage;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.ColorValue;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.graphics.GraphicsValue;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.color.Rgb;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.color.Web;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "ClassName"})
public final class lgl implements Library {

    private static JFXPanel panel;
    private static Canvas canvas;

    @Override
    public void init() {
        final LzrMap lgl = new LzrMap(2);
        lgl.set("createFrame", new LFrame());
        lgl.set("redraw", new Redraw());

        Variables.define("lgl", lgl);
        LzrMap effect = new LzrMap(14);
        effect.set("Blend", new LBlend());
        effect.set("Bloom", new LBloom());
        effect.set("BoxBlur", new LBoxBlur());
        effect.set("ColorAdjust", new LColorAdjust());
        effect.set("ColorInput", new LColorInput());
        effect.set("DropShadow", new LDropShadow());
        effect.set("GaussianBlur", new LGaussianBlur());
        effect.set("Glow", new LGlow());
        effect.set("InnerShadow", new LInnerShadow());
        effect.set("Lighting", new LLighting());
        effect.set("MotionBlur", new LMotionBlur());
        effect.set("PerspectiveTransform", new LPerspectiveTransform());
        effect.set("Reflection", new LReflection());
        effect.set("SepiaTone", new LSepiaTone());
        effect.set("Shadow", new LShadow());
        Variables.define("effect", effect);

        Keyword.put("loadImage", new LoadImage());


        final Map<LzrValue, LzrValue> colors = Arrays.stream(Color.class.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f -> f.getType().equals(Color.class))
                .collect(Collectors.toMap(
                        f -> new LzrString(f.getName()),
                        f -> {
                            try { return new ColorValue((Color) f.get(Color.class)); }
                            catch (IllegalAccessException ex) { return null; }
                        }));
        colors.put(new LzrString("new"), new LzrFunction(new NewColor()));
        colors.put(new LzrString("rgb"), new LzrFunction(new Rgb()));
        colors.put(new LzrString("hsb"), new LzrFunction(new Hsb()));
        colors.put(new LzrString("web"), new LzrFunction(new Web()));
        Variables.define("Color", new LzrMap(colors));

        final LzrMap arcType = new LzrMap(ArcType.values().length);
        for (ArcType value : ArcType.values()) {
            arcType.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("ArcType", arcType);

        final LzrMap fillRule = new LzrMap(FillRule.values().length);
        for (FillRule value : FillRule.values()) {
            fillRule.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("FillRule", fillRule);

        final LzrMap blendMode = new LzrMap(BlendMode.values().length);
        for (BlendMode value : BlendMode.values()) {
            blendMode.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("BlendMode", blendMode);

        final LzrMap lineCap = new LzrMap(StrokeLineCap.values().length);
        for (StrokeLineCap value : StrokeLineCap.values()) {
            lineCap.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("StrokeLineCap", lineCap);

        final LzrMap lineJoin = new LzrMap(StrokeLineJoin.values().length);
        for (StrokeLineJoin value : StrokeLineJoin.values()) {
            lineJoin.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("StrokeLineJoin", lineJoin);

        final LzrMap textAlignment = new LzrMap(TextAlignment.values().length);
        for (TextAlignment value : TextAlignment.values()) {
            textAlignment.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("TextAlignment", textAlignment);

        final LzrMap vPos = new LzrMap(VPos.values().length);
        for (VPos value : VPos.values()) {
            vPos.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("VPos", vPos);

        final LzrMap events = new LzrMap(Events.values().length + 2);
        for (Events value : Events.values()) {
            events.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        events.set("Filter", new EventFilter());
        events.set("Handler", new EventHandler());
        Variables.define("Event", events);

        final LzrMap mouseButton = new LzrMap(MouseButton.values().length);
        for (MouseButton value : MouseButton.values()) {
            mouseButton.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("MouseButton", mouseButton);

        final LzrMap keyCodes = new LzrMap(KeyCode.values().length);
        for (KeyCode value : KeyCode.values()) {
            keyCodes.set(value.name(), LzrNumber.of(value.ordinal()));
        }
        Variables.define("KeyCode", keyCodes);

    }

    
    private static class LFrame implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            String title = "";
            int width = 640;
            int height = 480;
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
            GraphicsContext graphics = canvas.getGraphicsContext2D();

            JFrame frame = new JFrame(title);
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
            return new GraphicsValue(graphics);
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
    
    private static class EventFilter implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            final Function handler = ((LzrFunction) args[1]).getValue();
            final Events event = Events.values()[args[0].asInt()];
            canvas.addEventFilter(event.getHandler(), e -> handleEvent(e, handler));
            return LzrNumber.ZERO;
        }
    }
    
    private static class EventHandler implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            final Function handler = ((LzrFunction) args[1]).getValue();
            final Events event = Events.values()[args[0].asInt()];
            canvas.addEventHandler(event.getHandler(), e -> handleEvent(e, handler));
            return LzrNumber.ZERO;
        }
    }
    
    private static void handleEvent(Event event, final Function handler) {
        if (event instanceof MouseEvent) {
            handleMouseEvent((MouseEvent) event, handler);
        } else if (event instanceof KeyEvent) {
            handleKeyEvent((KeyEvent) event, handler);
        } else if (event instanceof DragEvent) {
            handleDragEvent((DragEvent) event, handler);
        }
    }
    
    private static void handleMouseEvent(MouseEvent e, final Function handler) {
        final LzrMap map = new LzrMap(25);
        map.set("button", LzrNumber.of(e.getButton().ordinal()));
        map.set("clickCount", LzrNumber.of(e.getClickCount()));
        map.set("sceneX", LzrNumber.of(e.getSceneX()));
        map.set("sceneY", LzrNumber.of(e.getSceneY()));
        map.set("screenX", LzrNumber.of(e.getScreenX()));
        map.set("screenY", LzrNumber.of(e.getScreenY()));
        map.set("mouseX", LzrNumber.of(e.getX()));
        map.set("mouseY", LzrNumber.of(e.getY()));
        map.set("mouseZ", LzrNumber.of(e.getZ()));
        map.set("isAltDown", LzrNumber.fromBoolean(e.isAltDown()));
        map.set("isConsumed", LzrNumber.fromBoolean(e.isConsumed()));
        map.set("isControlDown", LzrNumber.fromBoolean(e.isControlDown()));
        map.set("isDragDetect", LzrNumber.fromBoolean(e.isDragDetect()));
        map.set("isMetaDown", LzrNumber.fromBoolean(e.isMetaDown()));
        map.set("isMiddleButtonDown", LzrNumber.fromBoolean(e.isMiddleButtonDown()));
        map.set("isPopupTrigger", LzrNumber.fromBoolean(e.isPopupTrigger()));
        map.set("isPrimaryButtonDown", LzrNumber.fromBoolean(e.isPrimaryButtonDown()));
        map.set("isSecondaryButtonDown", LzrNumber.fromBoolean(e.isSecondaryButtonDown()));
        map.set("isShiftDown", LzrNumber.fromBoolean(e.isShiftDown()));
        map.set("isShortcutDown", LzrNumber.fromBoolean(e.isShortcutDown()));
        map.set("isStillSincePress", LzrNumber.fromBoolean(e.isStillSincePress()));
        map.set("isSynthesized", LzrNumber.fromBoolean(e.isSynthesized()));
        handler.execute(map);
    }
    
    private static void handleKeyEvent(final KeyEvent e, final Function handler) {
        final LzrMap map = new LzrMap(10);
        map.set("code", LzrNumber.of(e.getCode().ordinal()));
        map.set("character", new LzrString(e.getCharacter()));
        map.set("text", new LzrString(e.getText()));
        map.set("isAltDown", LzrNumber.fromBoolean(e.isAltDown()));
        map.set("isConsumed", LzrNumber.fromBoolean(e.isConsumed()));
        map.set("isControlDown", LzrNumber.fromBoolean(e.isControlDown()));
        map.set("isMetaDown", LzrNumber.fromBoolean(e.isMetaDown()));
        map.set("isShiftDown", LzrNumber.fromBoolean(e.isShiftDown()));
        map.set("isShortcutDown", LzrNumber.fromBoolean(e.isShortcutDown()));
        handler.execute(map);
    }
    
    private static void handleDragEvent(final DragEvent e, final Function handler) {
        final LzrMap map = new LzrMap(10);
        map.set("sceneX", LzrNumber.of(e.getSceneX()));
        map.set("sceneY", LzrNumber.of(e.getSceneY()));
        map.set("screenX", LzrNumber.of(e.getScreenX()));
        map.set("screenY", LzrNumber.of(e.getScreenY()));
        map.set("mouseX", LzrNumber.of(e.getX()));
        map.set("mouseY", LzrNumber.of(e.getY()));
        map.set("mouseZ", LzrNumber.of(e.getZ()));
        map.set("isAccepted", LzrNumber.fromBoolean(e.isAccepted()));
        map.set("isConsumed", LzrNumber.fromBoolean(e.isConsumed()));
        map.set("isDropCompleted", LzrNumber.fromBoolean(e.isDropCompleted()));
        handler.execute(map);
    }
    
}
