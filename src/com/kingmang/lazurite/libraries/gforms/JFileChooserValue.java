package com.kingmang.lazurite.libraries.gforms;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.runtime.Lzr.LzrFunction;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Lzr.LzrString;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class JFileChooserValue extends JComponentValue {

    final JFileChooser fileChooser;

    public JFileChooserValue(JFileChooser fileChooser) {
        super(1, fileChooser);
        this.fileChooser = fileChooser;
        init();
    }

    private void init() {
        set("showOpenDialog", new LzrFunction(this::showOpenDialog));
        set("showSaveDialog", new LzrFunction(this::showSaveDialog));
        set("getTextFromFile", new LzrFunction(this::getTextFromFile));
    }

    private Value showOpenDialog(Value... args){
        fileChooser.showOpenDialog(null);
        return LzrNumber.ZERO;
    }

    private Value showSaveDialog(Value... args){
        try {
            fileChooser.showSaveDialog(null);
        }catch(Exception ignored){}

        return LzrNumber.ZERO;
    }
    private Value getTextFromFile(Value... args){
        File selectedFile = fileChooser.getSelectedFile();
        try {
            String fileContent = new String(Files.readAllBytes(selectedFile.toPath()));
            return new LzrString(fileContent);
        } catch (IOException ex) {
            throw new LZRException("IOException", "");
        }
    }






}
