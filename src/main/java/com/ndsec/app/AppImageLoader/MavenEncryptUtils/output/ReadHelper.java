package com.ndsec.app.AppImageLoader.MavenEncryptUtils.output;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadHelper implements IRead {
    @Override
    public byte[] read(InputStream in) throws IOException
    {

        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        byte[] bytes;
        int len;
        byte[] buffer = new byte[1024 * 1024];
        BufferedInputStream bin = new BufferedInputStream(in);
        while ((len = bin.read(buffer)) != -1)
        {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        byteArrayOutputStream.flush();
        bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }
    //@Override
    //public byte[] read(String classFilePathString) throws IOException {
    //
    //    File classFile = new File(classFilePathString);
    //    if (!classFile.exists()) {
    //        throw new NoClassDefFoundError("没有找到文件！");
    //    }
    //    try (FileInputStream fileInputStream = new FileInputStream(classFile);) {
    //       return this.read(fileInputStream);
    //    } catch (IOException e) {
    //        log.error("Read error！");
    //        e.printStackTrace();
    //        return new byte[0];
    //    }
    //
    //}
}
