package com.kingmang.lazurite.runner;


import com.kingmang.lazurite.utils.Handler;
import com.kingmang.lazurite.editors.Editor;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public class Runner {



    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            console();
        } else {
            command(args);
        }
    }

    private static void command(String[] cmd) throws IOException {
        switch (cmd[0]) {
            case "help", "-h" -> RunnerInfo.Command();
            case "editor", "-e" -> Editor.openEditor();
            case "version", "-v" -> versionCMD();
            case "run", "-r" -> runCMD(cmd, false);
            case "preprocess", "-pr" -> runCMD(cmd, true);
            case "new", "-n" -> newCMD(cmd);
            default -> System.out.println("Command not found!");
        }
    }


    private static void console() throws IOException {
        RunnerInfo.Console();

        boolean works = true;

        while (works) {
            System.out.println("\n------------------------------------------------------------\n");
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

            String[] cmd = sc.readLine().split(" ");
            switch (cmd[0]) {
                case "help", "-h" -> RunnerInfo.Console();
                case "editor", "-e" -> Editor.openEditor();
                case "version", "-v" -> versionCMD();
                case "run", "-r" -> runCMD(cmd, false);
                case "preprocess", "-pr" -> runCMD(cmd, true);
                case "new", "-n" -> newCMD(cmd);
                case "cls" -> {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
                case "exit" -> works = false;
                default -> System.out.println("Command not found!");
            }
        }
    }

    private static void versionCMD() {
        System.out.println("---------------------------------");
        System.out.println("Lazurite version: " + RunnerInfo.getVersion());
        System.out.println("---------------------------------");
    }

    private static void runCMD(String[] cmd, boolean preprocess) throws IOException {
        if (cmd.length > 1) {

            // Checking path is project or file
            File dir = new File(System.getProperty("user.dir"), cmd[1]);
            boolean is_dir = dir.isDirectory();
            if (is_dir) {
                File project_file = new File(dir, "project.toml");
                String run_file = getRunFile(project_file);
                if (run_file != null) {
                    File run_path = new File(dir, run_file);
                    if (run_path.exists()) {
                        try {
                            if(preprocess)Handler.Run(run_path.getPath(), false);
                            if(!preprocess)Handler.Run(run_path.getPath());
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Correct entry form: r <file>");
                        } catch (FileNotFoundException ex) {
                            System.out.println("File " + cmd[1] + " not found");
                        }
                    } else {
                        System.out.printf("There is no %s file in this path", run_file);
                    }
                } else {
                    System.out.println("Can't run code without file or project");
                }
            } else {
                try {

                    if(preprocess)Handler.Run(cmd[1], false);
                    if(!preprocess)Handler.Run(cmd[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Correct entry form: r <file>");
                } catch (FileNotFoundException ex) {
                    System.out.println("File " + cmd[1] + " not found");
                }
            }
        } else {
            File dir = new File(System.getProperty("user.dir"));
            File project_file = new File(dir, "project.toml");
            String run_file = getRunFile(project_file);
            if (run_file != null) {
                File run_path = new File(dir, run_file);
                if (run_path.exists()) {
                    try {
                        if(preprocess)Handler.Run(run_path.getPath(), false);
                        if(!preprocess)Handler.Run(run_path.getPath());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Correct entry form: r <file>");
                    } catch (FileNotFoundException ex) {
                        System.out.println("File " + run_file + " not found");
                    }
                } else {
                    System.out.printf("There is no %s file in this path", run_file);
                }
            } else {
                System.out.println("Can't run code without file or project.toml");
            }
        }
    }

    private static String getRunFile(File project_file) {
        Toml project_toml = new Toml().read(project_file);
        Toml project = project_toml.getTable("package");

        if (project.contains("run_file")) {
            return project.getString("run_file");
        } else if (project.contains("lib_file")) {
            System.out.println("Can't run library package.");
        }
        return null;
    }

    private static void newCMD(String[] cmd) throws IOException {
        String name = cmd[cmd.length-1];
        if (name.startsWith("-") || !name.matches("^[a-z0-9\\-]+$")) {
            System.out.println("Name of project should contain only `a-z`, `0-9` and `-`");
            return;
        }
        boolean is_lib = cmd[1].equals("--lib") || cmd[1].equals("-l");
        File dir = new File(String.format("%s/%s", System.getProperty("user.dir"), name));
        final Path path = dir.toPath();

        if (Files.exists(path) && Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                if (directory.iterator().hasNext()) {
                    directory.close();
                    System.out.println("Can't create project " + name + " in existing folder that contains something in it");
                    return;
                }
            }
            Files.createDirectory(new File(String.format("%s/src", path)).toPath());
            Files.createDirectory(new File(RunnerInfo.getPathToLzrLibs()).toPath());
        } else {
            Files.createDirectory(path);
            Files.createDirectory(new File(String.format("%s/src", path)).toPath());
        }   Files.createDirectory(new File(RunnerInfo.getPathToLzrLibs()).toPath());

        if (is_lib) {
            try {
                new FileWriter(path + "/src/lib.lzr").close();
            } catch (IOException e) {
                System.out.println("IOException");
            }
        } else {
            try {
                FileWriter writer = new FileWriter(path
                        + "/src/main.lzr");
                writer.write("print(\"Hello World\")");
                writer.close();
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }

        try {
            Map<String, Object> settings_map = new HashMap<>() {{
                put("package", new HashMap<>() {{
                    put("name", name);
                    put("version", "0.1.0");
                    //put("lzr_libraries", RunnerInfo.getPathToLzrLibs());
                    if (is_lib) {
                        put("lib_file", "/src/lib.lzr");
                    } else {
                        put("run_file", "/src/main.lzr");
                    }
                }});
            }};

            File settings = new File(path + "/project.toml");
            TomlWriter writer = new TomlWriter();
            writer.write(settings_map, settings);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}





