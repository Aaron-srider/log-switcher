package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils;

import com.ndsec.app.AppImageLoader.MavenEncryptUtils.exception.FileTypeException;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.logApi.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileUtils
{
    public static String stripNameSuffix(String filename)
    {
        int i = filename.lastIndexOf(".");
        if (i != -1)
        {
            return filename.substring(0, i);
        }
        return filename;
    }

    public static String getFileSuffix(String filename)
    {
        int i = filename.lastIndexOf(".");
        if (i != -1)
        {
            return filename.substring(i + 1);
        }
        return "";
    }
    public static boolean deleteAll(String absolutePath) {
        if (Files.exists(Paths.get(absolutePath)) && Files.isDirectory(Paths.get(absolutePath))) {
            //List<String> allFilePath = new ArrayList<>();
            try (Stream<Path> walk = Files.walk(Paths.get(absolutePath))) {
                walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .peek((file) ->
                    {
                        String fileAbsolutePath = file.getAbsolutePath();
                        //allFilePath.add(fileAbsolutePath);
                    })
                    .forEach(File::delete);
                return true;
            }
            catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean deleteEmptyDir(String dirPath, Log log) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            throw new RuntimeException(new FileNotFoundException(dirPath));
        }

        if (dir.isFile()) {
            throw new FileTypeException(
                    dirPath,
                    FileTypeException.FileType.DIR,
                    FileTypeException.FileType.REGULAR_FILE);
        }

        try {
            Files.walkFileTree(Paths.get(dirPath), new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    log.debug("check dir empty: "+ dir);
                    Stream<Path> list = Files.list(dir);
                    long fileNum = list.count();

                    if (fileNum <= 0) {
                        log.debug("dir is empty, delete it: " + dir);

                        try {
                            Files.deleteIfExists(dir);
                        }
                        catch (Exception e) {
                            log.debug(e.getMessage());
                        }
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e)
                        throws IOException {

                    if(!Files.exists(dir)) {
                        return FileVisitResult.CONTINUE;
                    }
                    Stream<Path> list = Files.list(dir);
                    long fileNum = list.count();

                    if (fileNum <= 0) {
                        log.debug("dir is empty, delete it: " + dir);
                        try {
                            Files.deleteIfExists(dir);
                        }
                        catch (Exception ex) {
                            log.debug(e.getMessage());
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return true;
    }
}
