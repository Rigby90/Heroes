package com.herocraftonline.dev.heroes.datasource;

import com.herocraftonline.dev.heroes.Heroes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

public class Downloader {

    private final String UPDATE_SITE = "http://bukkit.onarandombox.com/libs/";
    private final String[] FILES = new String[]{"h2.jar", "mysql.jar"};

    public Downloader() {
        try {
            performUpdates();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performUpdates() throws IOException {
        Heroes.log("- Checking for Dependencies");

        ArrayList<UpdateFile> filesToUpdate = new ArrayList<UpdateFile>();
        for (String fileName : FILES) {
            File localFile = new File("lib" + File.separator + fileName);

            if (localFile != null && !localFile.exists() && !localFile.isDirectory()) {
                filesToUpdate.add(new UpdateFile(localFile, UPDATE_SITE, fileName));
            }
        }

        int amount = filesToUpdate.size();
        if (!(amount > 0)) {
            return;
        }

        File libFolder = new File("lib");
        if (libFolder.exists() && !libFolder.isDirectory()) {
            Heroes.log(Level.SEVERE, "- The folder 'lib' cannot be created, it appears to be a file.");
            return;
        } else if (!libFolder.exists()) {
            Heroes.log("- Creating 'lib' folder");
            libFolder.mkdirs();
        }

        if (amount > 1) {
            Heroes.log("- Downloading " + amount + " dependencies.");
        } else {
            Heroes.log("- Downloading 1 dependency.");
        }

        Iterator<UpdateFile> iterator = filesToUpdate.iterator();

        while (iterator.hasNext()) {
            UpdateFile update = iterator.next();

            Heroes.log("  - Downloading : '" + update.getFileName() + "' to '" + update.getLocalFileLocation() + "'");

            URL url = new URL(update.getRemoteLocation());
            File localFile = new File(update.getLocalFileLocation());

            if (localFile.exists()) {
                localFile.delete();
            }

            InputStream inputStream = url.openStream();
            OutputStream outputStream = new FileOutputStream(localFile);

            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            inputStream.close();
            outputStream.close();

            Heroes.log("    + Download Complete!");
            iterator.remove();
        }

        Heroes.log("- Dependencies up to date.");
    }

    private class UpdateFile {

        private File localFile;
        private String remoteURL;
        private String remoteFileName;

        public UpdateFile(File localFile, String remoteURL, String remoteFileName) {
            this.localFile = localFile;
            this.remoteURL = remoteURL;
            this.remoteFileName = remoteFileName;
        }

        public String getFileName() {
            return this.remoteFileName;
        }

        public String getRemoteLocation() {
            return this.getURL() + this.getFileName();
        }

        public String getURL() {
            return this.remoteURL;
        }

        public String getLocalFileLocation() {
            return this.localFile.toString();
        }
    }
}
