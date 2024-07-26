package com.kingmang.lazurite.libraries.lzr.utils.lfs;


import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public final class lfs implements Library {

   private static Map<Integer, FileInfo> files;

    @Override
    public void init() {
        files = new HashMap<>();
        LzrMap lfs = new LzrMap(10);
        LzrMap write = new LzrMap(11);
        LzrMap read = new LzrMap(11);
        lfs.set("isDir", fileToBoolean(File::isDirectory));

        lfs.set("isFile", fileToBoolean(File::isFile));
        lfs.set("fileSize", new fileSize());

        lfs.set("open", new open());
        lfs.set("close", new close());

        lfs.set("copy", new copy());
        lfs.set("delete", fileToBoolean(File::delete));
        lfs.set("scanDir", new listFiles());
        lfs.set("mkdir", fileToBoolean(File::mkdir));
        lfs.set("rename", new rename());

        read.set("boolean", new readBoolean());
        read.set("byte", new readByte());
        read.set("char", new readChar());
        read.set("short", new readShort());
        read.set("int", new readInt());
        read.set("line", new readLine());
        read.set("utf", new readUTF());
        read.set("long", new readLong());
        read.set("bytes", new readBytes());
        read.set("allBytes", new readAllBytes());
        read.set("float", new readFloat());
        read.set("double", new readDouble());
        read.set("text", new readText());

        write.set("boolean", new WBool());
        write.set("byte", new WByte());
        write.set("char", new WChar());
        write.set("short", new WShort());
        write.set("int", new WInt());
        write.set("long", new WLong());
        write.set("float", new WFloat());
        write.set("double", new WDouble());
        write.set("UTF", new WUTF());
        write.set("line", new WLine());
        write.set("text", new WText());

        lfs.set("write", write);
        lfs.set("read", read);
        Variables.define("lfs", lfs);
    }


    
    private static class open implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.checkAtLeast(1, args.length);
            
            final File file = Console.fileInstance(args[0].asString());
            try {
                if (args.length > 1) {
                    return process(file, args[1].asString().toLowerCase());
                }
                return process(file, "r");
            } catch (IOException ioe) {
                return LzrNumber.MINUS_ONE;
            }
        }
        
        private LzrValue process(File file, String mode) throws IOException {
            DataInputStream dis = null;
            BufferedReader reader = null;
            if (mode.contains("rb")) {
                dis = new DataInputStream(new FileInputStream(file));
            } else if (mode.contains("r")) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            }
            
            DataOutputStream dos = null;
            BufferedWriter writer = null;
            final boolean append = mode.contains("+");
            if (mode.contains("wb")) {
                dos = new DataOutputStream(new FileOutputStream(file, append));
            } else if (mode.contains("w")) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), StandardCharsets.UTF_8));
            }
            
            final int key = files.size();
            files.put(key, new FileInfo(file, dis, dos, reader, writer));
            return LzrNumber.of(key);
        }
    }
    
    private abstract static class FileFunction implements Function {
        
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            if (args.length < 1) throw new LzrException("ArgumentsMismatchException ","File descriptor expected");
            final int key = args[0].asInt();
            try {
                return execute(files.get(key), args);
            } catch (IOException ioe) {
                return LzrNumber.MINUS_ONE;
            }
        }
        
        protected abstract LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException;
    }

    private static class listFiles extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrArray.of(Objects.requireNonNull(fileInfo.file.list()));
        }
    }

    private static class copy implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(2, args.length);
            try {
                final FileInputStream is = new FileInputStream(fileFrom(args[0]));
                final FileOutputStream os = new FileOutputStream(fileFrom(args[1]));
                final FileChannel ic = is.getChannel();
                ic.transferTo(0, ic.size(), os.getChannel());
                is.close();
                os.close();
                return LzrNumber.ONE;
            } catch (IOException ioe) {
                return LzrNumber.MINUS_ONE;
            }
        }
    }

    private static class rename implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(2, args.length);
            return LzrNumber.fromBoolean( fileFrom(args[0]).renameTo(fileFrom(args[1])) );
        }
    }
    
    private static class fileSize extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.of(fileInfo.file.length());
        }
    }

    
    private static class WBool extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            fileInfo.dos.writeBoolean(args[1].asInt() != 0);
            return LzrNumber.ONE;
        }
    }
    
    private static class WByte extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            fileInfo.dos.writeByte((byte) args[1].asInt());
            return LzrNumber.ONE;
        }
    }


    
    private static class WChar extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            final char ch = (args[1].type() == Types.NUMBER)
                    ? ((char) args[1].asInt())
                    : args[1].asString().charAt(0);
            fileInfo.dos.writeChar(ch);
            return LzrNumber.ONE;
        }
    }
    
    private static class WShort extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            fileInfo.dos.writeShort((short) args[1].asInt());
            return LzrNumber.ONE;
        }
    }
    
    private static class WInt extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            fileInfo.dos.writeInt(args[1].asInt());
            return LzrNumber.ONE;
        }
    }
    
    private static class WLong extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            final long value;
            if (args[1].type() == Types.NUMBER) {
                value = ((LzrNumber)args[1]).asLong();
            } else {
                value = (long) args[1].asNumber();
            }
            fileInfo.dos.writeLong(value);
            return LzrNumber.ONE;
        }
    }
    
    private static class WFloat extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            final float value;
            if (args[1].type() == Types.NUMBER) {
                value = ((LzrNumber)args[1]).asFloat();
            } else {
                value = (float) args[1].asNumber();
            }
            fileInfo.dos.writeFloat(value);
            return LzrNumber.ONE;
        }
    }
    
    private static class WDouble extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            fileInfo.dos.writeDouble(args[1].asNumber());
            return LzrNumber.ONE;
        }
    }
    
    private static class WUTF extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            fileInfo.dos.writeUTF(args[1].asString());
            return LzrNumber.ONE;
        }
    }
    
    private static class WLine extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            fileInfo.writer.write(args[1].asString());
            fileInfo.writer.newLine();
            return LzrNumber.ONE;
        }
    }

    private static class WText extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            fileInfo.writer.write(args[1].asString());
            return LzrNumber.ONE;
        }
    }
    private static class readBoolean extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.fromBoolean(fileInfo.dis.readBoolean());
        }
    }

    private static class readByte extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.of(fileInfo.dis.readByte());
        }
    }

    private static class readBytes extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            final LzrArray array = (LzrArray) args[1];
            int offset = 0, length = array.size();
            if (args.length > 3) {
                offset = args[2].asInt();
                length = args[3].asInt();
            }

            final byte[] buffer = new byte[length];
            final int read = fileInfo.dis.read(buffer, 0, length);
            for (int i = 0; i < read; i++) {
                array.set(offset + i, LzrNumber.of(buffer[i]));
            }
            return LzrNumber.of(read);
        }
    }

    private static class readAllBytes extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            final int bufferSize = 4096;
            final byte[] buffer = new byte[bufferSize];
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read;
            while ((read = fileInfo.dis.read(buffer, 0, bufferSize)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            baos.close();
            return LzrArray.of(baos.toByteArray());
        }
    }

    private static class readChar extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.of((short)fileInfo.dis.readChar());
        }
    }

    private static class readShort extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.of(fileInfo.dis.readShort());
        }
    }

    private static class readInt extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.of(fileInfo.dis.readInt());
        }
    }

    private static class readLong extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.of(fileInfo.dis.readLong());
        }
    }

    private static class readFloat extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.of(fileInfo.dis.readFloat());
        }
    }

    private static class readDouble extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return LzrNumber.of(fileInfo.dis.readDouble());
        }
    }

    private static class readUTF extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return new LzrString(fileInfo.dis.readUTF());
        }
    }

    private static class readLine extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            return new LzrString(fileInfo.reader.readLine());
        }
    }

    private static class readText extends FileFunction {

        private static final int BUFFER_SIZE = 4096;

        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            final StringBuilder result = new StringBuilder();
            final char[] buffer = new char[BUFFER_SIZE];
            int read;
            while ((read = fileInfo.reader.read(buffer, 0, BUFFER_SIZE)) != -1) {
                result.append(buffer, 0, read);
            }
            return new LzrString(result.toString());
        }
    }

    
    private static class close extends FileFunction {
        @Override
        protected LzrValue execute(FileInfo fileInfo, LzrValue[] args) throws IOException {
            if (fileInfo.dis != null) {
                fileInfo.dis.close();
            }
            if (fileInfo.dos != null) {
                fileInfo.dos.close();
            }
            if (fileInfo.reader != null) {
                fileInfo.reader.close();
            }
            if (fileInfo.writer != null) {
                fileInfo.writer.close();
            }
            return LzrNumber.ONE;
        }
    }

    private static File fileFrom(LzrValue value) {
        if (value.type() == Types.NUMBER) {
            return files.get(value.asInt()).file;
        }
        return Console.fileInstance(value.asString());
    }

    private interface FileToBooleanFunction {

        boolean apply(File file);
    }

    private static Function fileToBoolean(FileToBooleanFunction f) {
        return args -> {
            Arguments.check(1, args.length);
            return LzrNumber.fromBoolean(f.apply(fileFrom(args[0])));
        };
    }
    
    private static class FileInfo {
        File file;
        DataInputStream dis;
        DataOutputStream dos;
        BufferedReader reader;
        BufferedWriter writer;

        public FileInfo(File file, DataInputStream dis, DataOutputStream dos, BufferedReader reader, BufferedWriter writer) {
            this.file = file;
            this.dis = dis;
            this.dos = dos;
            this.reader = reader;
            this.writer = writer;
        }
    }
}
