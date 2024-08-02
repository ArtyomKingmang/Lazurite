package com.kingmang.lazurite.libraries.lzr.net.socket;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrReference;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class socket implements Library {

    @Override
    public void init() {
        final LzrMap socket = new LzrMap(4);

        socket.set("server", args -> {
            Arguments.check(1, args.length);
            try {
                return new LzrReference(new ServerSocket(args[0].asInt()));
            } catch (IOException e) {
                throw new LzrException(e.toString(), e.getMessage());
            }
        });

        socket.set("socket", args -> {
            Arguments.check(2, args.length);
            String host_name = args[0].toString();
            int port = args[1].asInt();
            try {
                return new LzrReference(new Socket(host_name, port));
            } catch (IOException e) {
                throw new LzrException(e.toString(), e.getMessage());
            }

        });


        socket.set("accept", args -> {
            Arguments.check(1, args.length);
            ServerSocket s = (ServerSocket) ((LzrReference) args[0]).getRef();
            try {
                return new LzrReference(s.accept());
            } catch (IOException e) {
                throw new LzrException(e.toString(), e.getMessage());
            }
        });


        Variables.define("socket", socket);
    }

}
