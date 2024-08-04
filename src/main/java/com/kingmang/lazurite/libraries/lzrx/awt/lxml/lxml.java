package com.kingmang.lazurite.libraries.lzrx.awt.lxml;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;

public class lxml implements Library {
    @Override
    public void init() {
        LzrMap xml = new LzrMap(1);

        xml.set("read", new LzrFunction((LzrValue... args) -> {
            Arguments.check(1, args.length);
            try {
                SAXReader reader = new SAXReader();
                Document result = reader.read(new File(args[0].toString()));
                return new LzrXmlValue(result);
            } catch (DocumentException e) {
                throw new LzrException("BadXML", "Path doesn't exists or file is corrupted");
            }
        }));


        Variables.define("xml", xml);
    }


}