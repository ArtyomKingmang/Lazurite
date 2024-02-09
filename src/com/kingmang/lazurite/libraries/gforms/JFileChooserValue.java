package com.kingmang.lazurite.libraries.gforms;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.runtime.Lzr.LzrFunction;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Lzr.LzrString;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        set("getFileText", new LzrFunction(this::getSelectedFile));
    }

    private Value showOpenDialog(Value... args){
        fileChooser.showOpenDialog(null);
        return LzrNumber.ZERO;
    }
    private Value getSelectedFile(Value... args){
        File selectedFile = fileChooser.getSelectedFile();
        try {
            String fileContent = new String(Files.readAllBytes(selectedFile.toPath()));
            return new LzrString(fileContent);
        } catch (IOException ex) {
            throw new LZRException("IOException", "");
        }
    }




}
