package com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.graphics;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.ColorValue;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.ImageValue;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.NotNull;

import static com.kingmang.lazurite.core.Converters.*;
import static com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue.FX_EFFECT_TYPE;

public class GraphicsValue extends LzrMap {

        private final GraphicsContext graphics;

        public GraphicsValue(GraphicsContext graphics) {
            super(64);
            this.graphics = graphics;
            init();
        }

        private void init() {
            set("applyEffect", this::applyEffect);
            set("appendSVGPath", this::appendSVGPath);
            set("arc", this::arc);
            set("arcTo", this::arcTo);
            set("beginPath", voidToVoid(graphics::beginPath));
            set("bezierCurveTo", this::bezierCurveTo);
            set("clearRect", double4ToVoid(graphics::clearRect));
            set("clip", voidToVoid(graphics::clip));
            set("closePath", voidToVoid(graphics::closePath));
            set("image", this::lzrimage);
            set("fill", voidToVoid(graphics::fill));
            set("arc", this::fillArc);
            set("ellipse", double4ToVoid(graphics::fillOval));
            set("polygon", this::fillPolygon);
            set("rect", double4ToVoid(graphics::fillRect));
            set("roundRect", this::fillRoundRect);
            set("text", this::fillText);
            set("getFill", this::getFill);
            set("getFillRule", enumOrdinal(graphics::getFillRule));
            set("getGlobalAlpha", voidToDouble(graphics::getGlobalAlpha));
            set("getGlobalBlendMode", enumOrdinal(graphics::getGlobalBlendMode));
            set("getLineCap", enumOrdinal(graphics::getLineCap));
            set("getLineDashOffset", voidToDouble(graphics::getLineDashOffset));
            set("getLineJoin", enumOrdinal(graphics::getLineJoin));
            set("getLineWidth", voidToDouble(graphics::getLineWidth));
            set("getMiterLimit", voidToDouble(graphics::getMiterLimit));
            set("getStroke", this::getStroke);
            set("getTextAlign", enumOrdinal(graphics::getTextAlign));
            set("getTextBaseline", enumOrdinal(graphics::getTextBaseline));
            set("isPointInPath", this::isPointInPath);
            set("lineTo", double2ToVoid(graphics::lineTo));
            set("moveTo", double2ToVoid(graphics::moveTo));
            set("quadraticCurveTo", double4ToVoid(graphics::quadraticCurveTo));
            set("restore", voidToVoid(graphics::restore));
            set("rotate", doubleToVoid(graphics::rotate));
            set("save", voidToVoid(graphics::save));
            set("scale", double2ToVoid(graphics::scale));
            set("setEffect", this::setEffect);
            set("setFill", this::setFill);
            set("setFillRule", this::setFillRule);
            set("setGlobalAlpha", doubleToVoid(graphics::setGlobalAlpha));
            set("setGlobalBlendMode", this::setGlobalBlendMode);
            set("setLineCap", this::setLineCap);
            set("setLineDashOffset", doubleToVoid(graphics::setLineDashOffset));
            set("setLineJoin", this::setLineJoin);
            set("setLineWidth", doubleToVoid(graphics::setLineWidth));
            set("setMiterLimit", doubleToVoid(graphics::setMiterLimit));
            set("setStroke", this::setStroke);
            set("setTextAlign", this::setTextAlign);
            set("setTextBaseline", this::setTextBaseline);
            set("stroke", voidToVoid(graphics::stroke));
            set("strokeArc", this::strokeArc);
            final LzrMap stroke = new LzrMap(7);
            stroke.set("line", double4ToVoid(graphics::strokeLine));
            stroke.set("ellipse", double4ToVoid(graphics::strokeOval));
            stroke.set("polygon", this::strokePolygon);
            stroke.set("polyline", this::strokePolyline);
            stroke.set("rect", double4ToVoid(graphics::strokeRect));
            stroke.set("roundRect", this::strokeRoundRect);
            stroke.set("text", this::strokeText);
            set("stroke", stroke);
            set("transform", this::transform);
            set("translate", double2ToVoid(graphics::translate));
        }

        private LzrValue applyEffect(LzrValue[] args) {
            if (args[0].type() != FX_EFFECT_TYPE) {
                throw new LzrException("TypeException: ", "Effect expected, found " + Types.typeToString(args[0].type()));
            }
            graphics.applyEffect((Effect) args[0].raw());
            return LzrNumber.ZERO;
        }

        private LzrValue arc(LzrValue[] args) {
            graphics.arc(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber());
            return LzrNumber.ZERO;
        }

        private LzrValue appendSVGPath(LzrValue[] args) {
            graphics.appendSVGPath(args[0].asString());
            return LzrNumber.ZERO;
        }

        private LzrValue arcTo(LzrValue[] args) {
            graphics.arcTo(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber());
            return LzrNumber.ZERO;
        }

        private LzrValue bezierCurveTo(LzrValue[] args) {
            graphics.bezierCurveTo(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber());
            return LzrNumber.ZERO;
        }

        private LzrValue lzrimage(LzrValue[] args) {
            Arguments.checkAtLeast(3, args.length);
            if (!(args[0] instanceof ImageValue)) {
                throw new LzrException("TypeException: ", "ImageFX expected");
            }
            final Image image = ((ImageValue) args[0]).image;

            if (args.length >= 9) {
                graphics.drawImage(image,
                        args[1].asNumber(), args[2].asNumber(),
                        args[3].asNumber(), args[4].asNumber(),
                        args[5].asNumber(), args[6].asNumber(),
                        args[7].asNumber(), args[8].asNumber()
                        );
                return LzrNumber.ZERO;
            }

            if (args.length >= 5) {
                // x y w h
                graphics.drawImage(image,
                        args[1].asNumber(), args[2].asNumber(),
                        args[3].asNumber(), args[4].asNumber()
                        );
                return LzrNumber.ZERO;
            }
            
            graphics.drawImage(image, args[1].asNumber(), args[2].asNumber());
            return LzrNumber.ZERO;
        }

        private LzrValue fillArc(LzrValue[] args) {
            graphics.fillArc(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber(),
                    ArcType.values()[args[6].asInt()]);
            return LzrNumber.ZERO;
        }

        private LzrValue fillPolygon(LzrValue[] args) {
            final LzrArray xarr = (LzrArray) args[0];
            final LzrArray yarr = (LzrArray) args[1];

            final int size = xarr.size();
            final double[] xPoints = new double[size];
            final double[] yPoints = new double[size];
            for (int i = 0; i < size; i++) {
                xPoints[i] = xarr.get(i).asNumber();
                yPoints[i] = yarr.get(i).asNumber();
            }

            graphics.fillPolygon(xPoints, yPoints, args[2].asInt());
            return LzrNumber.ZERO;
        }

        private LzrValue fillRoundRect(LzrValue[] args) {
            graphics.fillRoundRect(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber() );
            return LzrNumber.ZERO;
        }

        private LzrValue fillText(LzrValue[] args) {
            if (args.length < 4) {
                // str x y
                graphics.fillText(args[0].asString(), args[1].asNumber(),
                        args[2].asNumber());
            } else {
                graphics.fillText(args[0].asString(), args[1].asNumber(),
                        args[2].asNumber(), args[3].asNumber());
            }
            return LzrNumber.ZERO;
        }

        private LzrValue getFill(LzrValue[] args) {
            return new ColorValue((Color)graphics.getFill());
        }

        private LzrValue getStroke(LzrValue[] args) {
            return new ColorValue((Color)graphics.getStroke());
        }

        private LzrValue isPointInPath(LzrValue[] args) {
            return LzrNumber.fromBoolean(graphics.isPointInPath(args[0].asNumber(), args[1].asNumber()));
        }

        private LzrValue setEffect(LzrValue[] args) {
            if (args[0].type() != FX_EFFECT_TYPE) {
                throw new LzrException("TypeException: ", "Effect expected, found " + Types.typeToString(args[0].type()));
            }
            graphics.setEffect((Effect) args[0].raw());
            return LzrNumber.ZERO;
        }

        private LzrValue setFill(LzrValue[] args) {
            graphics.setFill((Color) args[0].raw());
            return LzrNumber.ZERO;
        }

        private LzrValue setFillRule(LzrValue[] args) {
            graphics.setFillRule(FillRule.values()[args[0].asInt()]);
            return LzrNumber.ZERO;
        }

        private LzrValue setGlobalBlendMode(LzrValue[] args) {
            graphics.setGlobalBlendMode(BlendMode.values()[args[0].asInt()]);
            return LzrNumber.ZERO;
        }

        private LzrValue setLineCap(LzrValue[] args) {
            graphics.setLineCap(StrokeLineCap.values()[args[0].asInt()]);
            return LzrNumber.ZERO;
        }

        private LzrValue setLineJoin(LzrValue[] args) {
            graphics.setLineJoin(StrokeLineJoin.values()[args[0].asInt()]);
            return LzrNumber.ZERO;
        }

        private LzrValue setStroke(LzrValue[] args) {
            graphics.setStroke((Color) args[0].raw());
            return LzrNumber.ZERO;
        }

        private LzrValue setTextAlign(LzrValue[] args) {
            graphics.setTextAlign(TextAlignment.values()[args[0].asInt()]);
            return LzrNumber.ZERO;
        }

        private LzrValue setTextBaseline(LzrValue[] args) {
            graphics.setTextBaseline(VPos.values()[args[0].asInt()]);
            return LzrNumber.ZERO;
        }

        private LzrValue strokeArc(LzrValue[] args) {
            graphics.strokeArc(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber(),
                    ArcType.values()[args[6].asInt()]);
            return LzrNumber.ZERO;
        }

        private LzrValue strokePolygon(LzrValue[] args) {
            final LzrArray xarr = (LzrArray) args[0];
            final LzrArray yarr = (LzrArray) args[1];

            final int size = xarr.size();
            final double[] xPoints = new double[size];
            final double[] yPoints = new double[size];
            for (int i = 0; i < size; i++) {
                xPoints[i] = xarr.get(i).asNumber();
                yPoints[i] = yarr.get(i).asNumber();
            }

            graphics.strokePolygon(xPoints, yPoints, args[2].asInt());
            return LzrNumber.ZERO;
        }

        private LzrValue strokePolyline(LzrValue[] args) {
            final LzrArray xarr = (LzrArray) args[0];
            final LzrArray yarr = (LzrArray) args[1];

            final int size = xarr.size();
            final double[] xPoints = new double[size];
            final double[] yPoints = new double[size];
            for (int i = 0; i < size; i++) {
                xPoints[i] = xarr.get(i).asNumber();
                yPoints[i] = yarr.get(i).asNumber();
            }

            graphics.strokePolyline(xPoints, yPoints, args[2].asInt());
            return LzrNumber.ZERO;
        }

        private LzrValue strokeRoundRect(LzrValue[] args) {
            graphics.strokeRoundRect(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber() );
            return LzrNumber.ZERO;
        }

        private LzrValue strokeText(LzrValue[] args) {
            if (args.length < 4) {
                // str x y
                graphics.strokeText(args[0].asString(), args[1].asNumber(),
                        args[2].asNumber());
            } else {
                graphics.strokeText(args[0].asString(), args[1].asNumber(),
                        args[2].asNumber(), args[3].asNumber());
            }
            return LzrNumber.ZERO;
        }

        private LzrValue transform(LzrValue[] args) {
            graphics.transform(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber());
            return LzrNumber.ZERO;
        }

        @NotNull
        @Override
        public String toString() {
            return "GraphicsValue " + asString();
        }
    }