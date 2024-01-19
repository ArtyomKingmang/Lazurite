package com.kingmang.lazurite.libraries.clipboard;

import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.*;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.runtime.LZR.*;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Arguments;

public class clipboard implements Library {

	@Override
	public void init() {
		LZRMap clipboard = new LZRMap(5);
		clipboard.set("get", new getText());
		clipboard.set("has", new hasText());
		clipboard.set("set", new setText());
		clipboard.set("add", new addText());
		clipboard.set("clear", new clear());
		Variables.define("clipboard", clipboard);
	}

	public static Value get() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.stringFlavor;
		if (clipboard.isDataFlavorAvailable(flavor)) {
			String text = null;
			try {
				text = (String) clipboard.getData(flavor);
			} catch (UnsupportedFlavorException e) {
				throw new LZRException("RuntimeException: ", "UnsupportedFlavorException");
			} catch (IOException e) {
				throw new LZRException("RuntimeException: ", "IOException");
			}
			return new LZRString(text);
		}
		return LZRNumber.ZERO;
	}

	public static void set(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(text);
		clipboard.setContents(stringSelection, null);
	}



	private class getText implements Function {
		@Override
		public Value execute(Value... args) {
			Arguments.check(0, args.length);
			return clipboard.get();
		}
	}

	private class hasText implements Function {
		@Override
		public Value execute(Value... args) {
			Arguments.check(0, args.length);
			Value text = clipboard.get();
			if (text != LZRNumber.ZERO && text != LZRString.EMPTY) {
				return LZRNumber.ONE;
			}
			return LZRNumber.ZERO;
		}
	}

	private class setText implements Function {
		@Override
		public Value execute(Value... args) {
			Arguments.check(1, args.length);
			clipboard.set(args[0].asString());
			return LZRNumber.ZERO;
		}
	}
	
	private class addText implements Function {
		@Override
		public Value execute(Value... args) {
			Arguments.check(1, args.length);
			Value text = clipboard.get();
			if (text == LZRNumber.ZERO) {
				throw new LZRException("ClipboardException: ", "Failed to read clipboard");
			}
			clipboard.set(text.asString() + args[0].asString());
			return LZRNumber.ZERO;
		}
	}

	private class clear implements Function {
		@Override
		public Value execute(Value... args) {
			Arguments.check(0, args.length);
			clipboard.set("");
			return LZRNumber.ZERO;
		}
	}
}