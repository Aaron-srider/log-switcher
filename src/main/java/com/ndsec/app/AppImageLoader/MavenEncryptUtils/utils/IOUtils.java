package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {

    public static void copy(InputStream in, OutputStream out) {
        try (
                BufferedInputStream bufferedIn = new BufferedInputStream(in);
                BufferedOutputStream bufferedOut = new BufferedOutputStream(out);) {
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((len = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, len);
            }
            bufferedOut.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyWithOutClosing(InputStream in, OutputStream out) {
        try {
            BufferedInputStream bufferedIn = new BufferedInputStream(in);
            BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((len = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, len);
            }
            bufferedOut.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyThenClose(InputStream in, OutputStream out) {
        try (
                BufferedInputStream bufferedIn = new BufferedInputStream(in);
                BufferedOutputStream bufferedOut = new BufferedOutputStream(out);) {
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((len = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, len);
            }
            bufferedOut.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readAllBytes(InputStream in) {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        in);) {
            byte[] buffer = new byte[1024 * 1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List<String> convertFileToLines(String path) {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(path));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
