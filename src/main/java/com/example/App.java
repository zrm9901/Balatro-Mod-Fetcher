package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import org.eclipse.jgit.api.Git;
import java.nio.file.*;
import org.apache.commons.io.FileUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;
import org.json.JSONArray;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

class App {
    static class MyThread extends Thread {
        String path;
        String x;
        public MyThread() {
        }
        @Override
        public void run() {
        }
    }
    public static ArrayList<String> read() {
        ArrayList<String> read = new ArrayList<>();
        File mods = new File("mods.txt");
        try {
            Scanner sc = new Scanner(mods);
            while (sc.hasNextLine()) {
                read.add(sc.nextLine());
            }
            sc.close();
            return read;
        } catch (Exception e) {
            System.out.println("Something went wrong reading file");
            return read();
        }
    }
    public static void clone(String path, String url) {
        
    }
    public static boolean checkPath(String path, String directory, int os, String platform) {
        
        
        
        
        
        
        
        try {
            File check = new File(path);
            File direct = new File(directory);
            if (!check.exists() && direct.exists()) {

                System.out.println("Mods folder not detected, fetching lovely");
                String apiUrl = ("https://api.github.com/repos/ethangreen-dev/lovely-injector/releases/tags/" + getLatestReleaseTag());
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(apiUrl).build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        JSONObject release = new JSONObject(responseBody);

                        JSONArray assets = release.getJSONArray("assets");
                        if (assets.length() > 0) {
                            for (int i = 0; i < assets.length(); i++) {
                                try {
                                    JSONObject asset = assets.getJSONObject(i);
                                    String assetName = asset.getString("name");
                                    String assetDownloadUrl = asset.getString("browser_download_url");;
                                    
                                    if (os ==0 && i == 0) {
                                        downloadFile(assetDownloadUrl, directory + "/" + assetName);
                                        System.out.println("Download complete: " + assetName);
                                        String zipFilePath = (directory + "/" + assetName);
                                        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
                                            ZipEntry zipEntry;
                                            String fileName = "version.dll";
                                            // Iterate through each entry in the ZIP file
                                            while ((zipEntry = zis.getNextEntry()) != null) {
                                                if (zipEntry.getName().equals(fileName)) {
                                                    File outputFile = new File(directory, fileName);
                                
                                                    // Ensure parent directories exist
                                                    outputFile.getParentFile().mkdirs();
                                
                                                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                                                        byte[] buffer = new byte[1024];
                                                        int length;
                                                        while ((length = zis.read(buffer)) > 0) {
                                                            fos.write(buffer, 0, length);
                                                        }
                                                    }
                                
                                                    System.out.println("File extracted to: " + outputFile.getAbsolutePath());
                                                    return false;
                                                }
                                            }
                                        }


                                    } else if (os == 1 && i == 1) {
                                        downloadFile(assetDownloadUrl, directory + "/" + assetName);
                                        System.out.println("Download complete: " + assetName);
                                        String zipFilePath = (directory + "/" + assetName);
                                        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
                                            ZipEntry zipEntry;
                                            String fileName = "version.dll";
                                            // Iterate through each entry in the ZIP file
                                            while ((zipEntry = zis.getNextEntry()) != null) {
                                                if (zipEntry.getName().equals(fileName)) {
                                                    File outputFile = new File(directory, fileName);
                                
                                                    // Ensure parent directories exist
                                                    outputFile.getParentFile().mkdirs();
                                
                                                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                                                        byte[] buffer = new byte[1024];
                                                        int length;
                                                        while ((length = zis.read(buffer)) > 0) {
                                                            fos.write(buffer, 0, length);
                                                        }
                                                    }
                                
                                                    System.out.println("File extracted to: " + outputFile.getAbsolutePath());
                                                    return false;
                                                }
                                            }
                                        }




                                    } else if (os == 2 && i == 2) {
                                        downloadFile(assetDownloadUrl, directory + "/" + assetName);
                                        System.out.println("Download complete: " + assetName);
                                        String zipFilePath = (directory + "/" + assetName);
                                        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
                                            ZipEntry zipEntry;
                                            String fileName = "version.dll";
                                            // Iterate through each entry in the ZIP file
                                            while ((zipEntry = zis.getNextEntry()) != null) {
                                                if (zipEntry.getName().equals(fileName)) {
                                                    File outputFile = new File(directory, fileName);
                                                    // Ensure parent directories exist
                                                    outputFile.getParentFile().mkdirs();
                                
                                                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                                                        byte[] buffer = new byte[1024];
                                                        int length;
                                                        while ((length = zis.read(buffer)) > 0) {
                                                            fos.write(buffer, 0, length);
                                                        }
                                                    }
                                                    System.out.println("File extracted to: " + outputFile.getAbsolutePath());
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("no assets found");
                        }
                    } else {
                        System.out.println("cant fetch release");
                    }
                }
                return false;
            }  else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error fetching lovely");
            return false;
        }
    }
    public static void downloadFile(String fileUrl, String destinationPath) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(destinationPath)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
    private static String getLatestReleaseTag() {
        String apiUrl = "https://api.github.com/repos/ethangreen-dev/lovely-injector/releases/latest";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                System.out.println(jsonObject.getString("tag_name"));
                return jsonObject.getString("tag_name"); // Get the latest tag
            } else {
                System.err.println("Failed to fetch latest release: " + response.message());
                return null;
            }
        } catch (Exception e) {
            System.out.println("failed to fetch latest release");
            return null;
        }
    }
    public static void main(String[] args) {

        ArrayList<String> mods = read();
        if (!mods.isEmpty()) {
            if (!checkPath((System.getProperty("user.home") + "\\AppData/Roaming\\Balatro\\Mods"), "C\\Program Files (x86)\\Steam\\steamapps\\common\\Balatro", 2, System.getProperty("os.name")) || 
            !checkPath("/home/zrm9901/.var/app/com.valvesoftware.Steam/.local/share/Steam/steamapps/compatdata/2379780/pfx/drive_c/users/steamuser/AppData/Roaming/Balatro/Mods", "/home/zrm9901/.var/app/com.valvesoftware.Steam/.local/share/Steam/steamapps/common/Balatro", 2, System.getProperty("os.name")))
            {
                System.out.println("Lovely, downloaded and extracted, please start Balatro at least once to populate the mods folder");
            } else {
                System.out.println("cloning now");
            }

        } else {
            System.out.println("No mods found in mods.txt / no mods.txt found. Make sure you have a mods.txt with at least one repository in it.");
        }
    }
}