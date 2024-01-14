package com.kingmang.lazurite.libraries.processingApi;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import processing.core.PApplet;

public class Sketch extends PApplet{

   public void settings(){
        size(500, 500);
    }

    public void draw(){
        ellipse(mouseX, mouseY, 50, 50);
    }

    public void mousePressed(){
        background(64);
    }

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        Sketch mySketch = new Sketch();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public static class run implements Function {

        @Override
        public Value execute(Value... args) {

           return LZRNumber.ZERO;
        }
    }

/*
    public static final class setup implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkAtLeast(1, args.length);
            Function body;
            if (args[0].type() == Types.FUNCTION) {
                body = ((LZRFunction) args[0]).getValue();
            } else {
                body = Keyword.get(args[0].asString());
            }
            return LZRNumber.ZERO;
        }
    }
*/
}
