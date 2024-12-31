package com.example;

/**
 * Hello world!
 *
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import org.eclipse.jgit.api.Git;
import java.nio.file.*;
import org.apache.commons.io.FileUtils;
public class App {
    
    public static void clone(String path, String x) {
        String[] split = x.split("/");
        String dir = split[split.length - 1];
        File directory = new File(path + dir);
        try {
            System.out.println("Cloning Into:" + directory);
            ArrayList<File> a= new ArrayList<>();
            Git clone = Git.cloneRepository()
                .setURI(x)
                .setDirectory(directory)
                .setDepth(1)
                .call();
            clone.close();
            try {
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        a.add(file);
                    }
                }
                File base = new File(directory + "\\assets");
                File git = new File(directory + "\\.git");
                File github = new File(directory + "\\.github");
                File README = new File(directory + "\\README.MD");
                if (!a.contains(base)) {
                    for (File v : a) {
                        if (!v.equals(git) && !v.equals(github) && !v.equals(README)) {
                            Path u = Paths.get(path + "\\" + v.getName());
                            File f = new File(path + "\\" + v.getName());
                            if (Files.exists(u)) {
                                FileUtils.deleteDirectory(f);
                                try {
                                    FileUtils.copyDirectory(v, f);
                                } catch (Exception e) {
                                    System.out.println("AAAAAAAAAA");
                                }
                                
                            } else {
                                System.out.println(v.getAbsolutePath());
                                System.out.println(f.getAbsolutePath());
                                try {
                                    FileUtils.copyDirectory(v, f);
                                } catch (Exception e) {
                                    System.out.println("AAAAAAAAAA");
                                }
                            }
                        }
                    }
                    
                }
            } catch (Exception e) {
                System.out.println("this went wrong");
            }
            
        } catch (Exception e) {
            System.out.println("error cloning repository");
        }

        
    }

    public static void main(String[] args) {
        boolean windows = true;
        ArrayList<String> modsList = new ArrayList<>();
        try {
            File mods = new File("mods.txt");
            Scanner sc = new Scanner(mods);
            while (sc.hasNextLine()) {
                modsList.add(sc.nextLine());
            }
            sc.close();
            
        } catch (Exception e) {
            System.out.println("something went wrong");
        }
        System.out.println("Enter the correct OS for your computer \nWindows: 1 \nLinux: 2 \nMac: 3");
        Scanner sc = new Scanner(System.in);
        int os;
        while (true) {
            try {
                os = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Enter the correct OS for your computer \nWindows: 1 \nLinux: 2 \nMac: 3");
            }
        }
        String path;
        Path d;
        File c;
        switch (os) {
            case 1:
                for (String x : modsList) {
                    String[] split = x.split("/");
                    if (windows) {
                        path = System.getenv("APPDATA") + "\\Balatro\\Mods\\";
                        d = Paths.get(path + split[split.length -1]);
                        c = new File(path + split[split.length -1]);
                        if (Files.exists(d)) {
                            System.out.println("it exists");
                            try {
                                FileUtils.deleteDirectory(c);
                            } catch (Exception e) {
                                System.out.println("it failed");
                            }
                            clone(path, x);
                            
                        } else {
                            clone(path, x);
                        }
                    }
                }
            case 2:
                System.out.println("I dont have the mdos folder for your system, please create an issue on github with your OS, the store you got the game from, and the mods directory");
                break;
            case 3:
                System.out.println("I dont have the mdos folder for your system, please create an issue on github with your OS, the store you got the game from, and the mods directory");
                break;
        }
    }
}
