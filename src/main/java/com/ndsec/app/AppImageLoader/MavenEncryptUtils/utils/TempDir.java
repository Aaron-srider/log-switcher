package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils;

import com.ndsec.app.AppImageLoader.MavenEncryptUtils.constant.CommonConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.PathUtils.filePathBuilder;

public class TempDir {

    private static final TempDir single = new TempDir();
    private String tempDir;

    private TempDir() {

    }

    public static TempDir getSingleton() {
        return single;
    }

    public String getTempDirString() {
        if (tempDir == null) {
            synchronized (this) {
                if (tempDir == null) {
                    try {
                        String systemTmpPath = System.getProperty("java.io" +
                                ".tmpdir");
                        systemTmpPath = PathUtils.filePathBuilder()
                                                 .ct(systemTmpPath)
                                                 .ct(UUID.randomUUID()
                                                         .toString())
                                                 .build();
                        Files.createDirectories(Paths.get(systemTmpPath));
                        this.tempDir = systemTmpPath;
                        return tempDir;
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return tempDir;
    }


    public synchronized void deleteTempDir() {
        if (tempDir != null) {
            FileUtils.deleteAll(tempDir);
            tempDir = null;
        }
    }

    public File getWorkingDir() {
        String tempDir = TempDir.getSingleton().getTempDirString();
        // create working dir
        String wd = filePathBuilder()
                .ct(tempDir)
                .ct(CommonConstants.ENCRYPTION_WORKING_DIR_NAME)
                .build();
        Path wdPath = Paths.get(wd);
        if (Files.exists(wdPath)) {
            return wdPath.toFile();
        }

        FileUtils.deleteAll(wdPath.toFile()
                                  .getAbsolutePath());

        try {
            Files.createDirectories(wdPath);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        File workingDir = wdPath.toFile();
        if (!workingDir.exists()) {
            throw new RuntimeException("Working dir creating failed");
        }

        return workingDir;
    }

}
