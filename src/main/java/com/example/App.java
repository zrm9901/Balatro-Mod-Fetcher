package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class App {
    static String modsFolder;
    class MyThread extends Thread {
        private String url;
        public MyThread(String url) {
            this.url = url;
        }
        @Override
        public void run() {
            clone(this.url);
        }
        public static void clone(String url) {
            String[] folder = url.split("/");
            File  path = new File(modsFolder,folder[folder.length -1]);
            if (new File(modsFolder).exists()) {
                if (path.exists()) {
                    try {
                        FileUtils.deleteDirectory(path);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                try {
                    Git.cloneRepository().setURI(url).setDirectory(path).setDepth(1).call();
                    System.out.println(url + " cloned to mods folder");
                    extract(path);
                } catch (Exception e) {
                    System.out.println("something went wrong cloning the repository " );
                    e.printStackTrace();
                }
            }
        }
    }
    public static void extract(File path) {
        File[] dirs = path.listFiles();
        boolean hasSubDir = true;
        for (int x = 0; x < dirs.length; x++) {
            if (dirs[x].getName().equals("assets")) {
                hasSubDir = false;
            }
        }
        if (hasSubDir) {
            for (int x = 0; x < dirs.length; x++) {
                if (!dirs[x].getName().equals(".github") && !dirs[x].getName().equals(".git") && !dirs[x].getName().equals("README.md") && !dirs[x].getName().equals(".gitignore")) {
                    try {
                        if (new File(modsFolder, dirs[x].getName()).exists()) {
                            FileUtils.deleteDirectory(new File(modsFolder, dirs[x].getName()));
                        }
                        Files.move(Paths.get(path + "/" + dirs[x].getName()), Paths.get(modsFolder + "/" + dirs[x].getName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        System.out.println("something went wong" );
                        e.printStackTrace();
                    }
                }
            }
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
            System.out.println("Something went wrong reading file" );
            e.printStackTrace();
            return read();
        }
    }
    public static boolean checkPath(String steamApps) {
        try {
            String path = steamApps + "/compatdata/2379780/pfx/drive_c/users/steamuser/AppData/Roaming/Balatro/Mods";
            String directory = steamApps + "/common/Balatro";
            File check = new File(path);
            System.out.println(check);
            File direct = new File(directory);        
            if (!check.exists() && direct.exists()) {
                modsFolder = path;
                System.out.println(check.exists());
                System.out.println(direct.exists());
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
                                    
                                    downloadFile(assetDownloadUrl, directory + File.separator + assetName);
                                    System.out.println("Download complete: " + assetName);
                                    String zipFilePath = (directory + File.separator + assetName);
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
                                } catch (Exception e) {
                                    e.getStackTrace();
                                    return false;
                            }
                        }} else {
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
            System.out.println("Error fetching lovely" );
            e.printStackTrace();
            return false;
        }
    }
    public static void downloadFile(String fileUrl, String destinationPath) throws IOException {
        try {
            URI uri = new URI(fileUrl);
            URL url = uri.toURL();  
            try (InputStream in = url.openStream();
                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream(destinationPath)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    private static String getLatestReleaseTag() {
        String apiUrl = "https://api.github.com/repos/ethangreen-dev/lovely-injector/releases/latest";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl).build();
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
            System.out.println("failed to fetch latest release: ");
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) {
        App app = new App();
        ArrayList<String> mods = read();
        System.out.println("Running as user: " + System.getProperty("user.name"));
        if (!mods.isEmpty()) {
            if (!checkPath(System.getProperty("user.home") + "/.var/app/com.valvesoftware.Steam/.local/share/Steam/steamapps")) {
                System.out.println("If your os is supported, lovely has been downloaded into your Balatro folder. If you are unsure if your system is supported, check in \"Steam>Balatro>Properties>Browse Local Files\" and loook for version.dll. If you dont see it, open an issue on github with your OS, Store, and Mods and Game path. Make sure you open your game again to create the mods folder to clone into. Also, Please add \"WINEDLLOVERRIDES=\"version=n,b\"%command%\"  to you rsteam lauch options. Best of luck modding, zrm9901!");
            } else if (modsFolder == null){
                System.out.println("could not find mods folder or game directory folder. make an issue on github to add it. For now please manually input the path to your steamApps folder\nFor example: /home/zrm9901/.var/app/com.valvesoftware.Steam/.local/share/Steam/steamapps");
                Scanner sc = new Scanner(System.in);
                String directory = sc.nextLine();
                if (!checkPath(directory)) {
                    System.out.println("If your os is supported, lovely has been downloaded into your Balatro folder. If you are unsure if your system is supported, check in \"Steam>Balatro>Properties>Browse Local Files\" and loook for version.dll. If you dont see it, open an issue on github with your OS, Store, and Mods and Game path. Make sure you open your game again to create the mods folder to clone into. Also, Please add \"WINEDLLOVERRIDES=\"version=n,b\"%command%\"  to you rsteam lauch options. Best of luck modding, zrm9901!");
                }
                modsFolder = directory + "/compatdata/2379780/pfx/drive_c/users/steamuser/AppData/Roaming/Balatro/Mods";
                sc.close();
                System.out.println("cloning now");
                if (mods.size() > 0) {
                    for (int i = 0; i < mods.size(); i++) {
                        MyThread thread = app.new MyThread(mods.get(i));
                        thread.start();
                    }
                } else {
                    System.out.println("Arralist mods is empty");
                }
            } else {
                System.out.println("cloning now");
                if (mods.size() > 0) {
                    for (int i = 0; i < mods.size(); i++) {
                        MyThread thread = app.new MyThread(mods.get(i));
                        thread.start();
                    }
                } else {
                    System.out.println("Arralist mods is empty");
                }
            }
        } else {
            System.out.println("No mods found in mods.txt / no mods.txt found. Make sure you have a mods.txt with at least one repository in it.");
        }
    } 
}
