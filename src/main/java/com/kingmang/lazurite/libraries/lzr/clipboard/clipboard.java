package com.kingmang.lazurite.libraries.lzr.clipboard;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class clipboard implements Library {

	public static LzrValue get() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.stringFlavor;

		if (clipboard.isDataFlavorAvailable(flavor)) {
			String text;
			try {
				text = (String) clipboard.getData(flavor);
			} catch (UnsupportedFlavorException e) {
				throw new LzrException("RuntimeException: ", "UnsupportedFlavorException");
			} catch (IOException e) {
				throw new LzrException("RuntimeException: ", "IOException");
			}
			return new LzrString(text);
		}

		return LzrNumber.ZERO;
	}

	@Override
	public void init() {
		LzrMap clipboardMap = new LzrMap(5);

		clipboardMap.set("get", (args) -> {
			Arguments.check(0, args.length);
			return clipboard.get();
		});

		clipboardMap.set("has", (args) -> {
			Arguments.check(0, args.length);
			LzrValue text = clipboard.get();

			return text != LzrNumber.ZERO && text != LzrString.EMPTY
					? LzrNumber.ONE : LzrNumber.ZERO;
		});

		clipboardMap.set("set", (args) -> {
			Arguments.check(1, args.length);
			clipboard.set(args[0].asString());
			return LzrNumber.ZERO;
		});

		clipboardMap.set("add", (args) -> {
			Arguments.check(1, args.length);
			LzrValue text = clipboard.get();

			if (text == LzrNumber.ZERO)
				throw new LzrException("ClipboardException: ", "Failed to read clipboard");

			clipboard.set(text.asString() + args[0].asString());
			return LzrNumber.ZERO;
		});

		clipboardMap.set("clear", (args) -> {
			Arguments.check(0, args.length);
			clipboard.set("");
			return LzrNumber.ZERO;
		});

		Variables.define("clipboard", clipboardMap);
	}

	public static void set(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(text);
		clipboard.setContents(stringSelection, null);
	}
}