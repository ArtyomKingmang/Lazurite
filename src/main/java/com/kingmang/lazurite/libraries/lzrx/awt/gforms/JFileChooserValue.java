package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;

import javax.swing.*;
import java.io.File;
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

    private LzrValue showOpenDialog(LzrValue... args) {
        fileChooser.showOpenDialog(null);
        return LzrNumber.ZERO;
    }

    private LzrValue showSaveDialog(LzrValue... args) {
        try {
            fileChooser.showSaveDialog(null);
        } catch (Exception ignored) {}

        return LzrNumber.ZERO;
    }
    private LzrValue getTextFromFile(LzrValue... args) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            String fileContent = new String(Files.readAllBytes(selectedFile.toPath()));
            return new LzrString(fileContent);
        } catch (IOException ex) {
            throw new LzrException("IOException", ex.getMessage());
        }
    }






}
