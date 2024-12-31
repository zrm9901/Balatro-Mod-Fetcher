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
    
    static String path = System.getenv("APPDATA") + "\\Balatro\\Mods\\";
    
    public static void clone(String x) {
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
        for (String x : modsList) {
            String[] split = x.split("/");
            Path d = Paths.get(path + "\\" + split[split.length -1]);
            File c = new File(path + "\\" + split[split.length -1]);
            if (Files.exists(d)) {
                System.out.println("it exists");
                try {
                    FileUtils.deleteDirectory(c);
                } catch (Exception e) {
                    System.out.println("it failed");
                }
                clone(x);
                
            } else {
                clone(x);
            }
            
        }
    }
}
