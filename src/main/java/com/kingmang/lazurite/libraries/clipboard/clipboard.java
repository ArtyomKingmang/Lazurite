package com.kingmang.lazurite.libraries.clipboard;

import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.*;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.Types.*;
import com.kingmang.lazurite.runtime.LzrValue;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Arguments;

public class clipboard implements Library {

	@Override
	public void init() {
		LzrMap clipboard = new LzrMap(5);
		clipboard.set("get", new getText());
		clipboard.set("has", new hasText());
		clipboard.set("set", new setText());
		clipboard.set("add", new addText());
		clipboard.set("clear", new clear());
		Variables.define("clipboard", clipboard);
	}

	public static LzrValue get() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.stringFlavor;
		if (clipboard.isDataFlavorAvailable(flavor)) {
			String text = null;
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

	public static void set(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(text);
		clipboard.setContents(stringSelection, null);
	}



	private class getText implements Function {
		@Override
		public LzrValue execute(LzrValue... args) {
			Arguments.check(0, args.length);
			return clipboard.get();
		}
	}

	private class hasText implements Function {
		@Override
		public LzrValue execute(LzrValue... args) {
			Arguments.check(0, args.length);
			LzrValue text = clipboard.get();
			if (text != LzrNumber.ZERO && text != LzrString.EMPTY) {
				return LzrNumber.ONE;
			}
			return LzrNumber.ZERO;
		}
	}

	private class setText implements Function {
		@Override
		public LzrValue execute(LzrValue... args) {
			Arguments.check(1, args.length);
			clipboard.set(args[0].asString());
			return LzrNumber.ZERO;
		}
	}
	
	private class addText implements Function {
		@Override
		public LzrValue execute(LzrValue... args) {
			Arguments.check(1, args.length);
			LzrValue text = clipboard.get();
			if (text == LzrNumber.ZERO) {
				throw new LzrException("ClipboardException: ", "Failed to read clipboard");
			}
			clipboard.set(text.asString() + args[0].asString());
			return LzrNumber.ZERO;
		}
	}

	private class clear implements Function {
		@Override
		public LzrValue execute(LzrValue... args) {
			Arguments.check(0, args.length);
			clipboard.set("");
			return LzrNumber.ZERO;
		}
	}
}