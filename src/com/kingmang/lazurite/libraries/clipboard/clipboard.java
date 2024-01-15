package com.kingmang.lazurite.libraries.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.IOException;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.core.Arguments;

public class clipboard implements Library {
	
	@Override
	public void init() {
		KEYWORD.put("copy", new copy());
		KEYWORD.put("paste", new paste());
	}
	
	private class copy implements Function {
		@Override
		public Value execute(Value... args) {
			Arguments.check(1, args.length);
			String text = args[0].asString();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection stringSelection = new StringSelection(text);
			clipboard.setContents(stringSelection, null);
			return LZRNumber.ZERO;
		}
	}
	
	private class paste implements Function {
		@Override
		public Value execute(Value... args) {
			Arguments.check(0, args.length);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			DataFlavor flavor = DataFlavor.stringFlavor;
			if (clipboard.isDataFlavorAvailable(flavor)) {
				String text = null;
				try {
					text = (String) clipboard.getData(flavor);
				} catch (UnsupportedFlavorException e) {
					throw new LZRException("RuntimeException: ","UnsupportedFlavorException");
				} catch (IOException e) {
					throw new LZRException("RuntimeException: ","IOException");
				}
				return new LZRString(text);
			}
			return LZRNumber.ZERO;
		}
	}
}
