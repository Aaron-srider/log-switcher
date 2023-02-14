package com.ndsec.app.AppImageLoader.MavenEncryptUtils.output;


import com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.logApi.Log;

import java.io.*;

public class OutputHelper implements IOutput {

    Log log;

    public OutputHelper(Log log) {
        this.log = log;
    }

    private void createParent(String outputPathString) {
        File outputFile = new File(outputPathString);

        File outputParentFile = outputFile.getParentFile();
        if (!outputParentFile.exists()) {
            outputParentFile.mkdirs();
        }
    }

    @Override
    public void output(InputStream in, String outputPathString) {
        createParent(outputPathString);
        try (BufferedOutputStream encryptedClassFileOut =
                     new BufferedOutputStream(new FileOutputStream(outputPathString));) {
            int len;
            byte[] buffer=  new byte[1024*1024];
            while((len=in.read(buffer)) !=-1) {
                encryptedClassFileOut.write(buffer, 0, len);
            }
            encryptedClassFileOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //
    @Override
    public void output(byte[] bytes, String outputPathString) {

        createParent(outputPathString);
        try (BufferedOutputStream encryptedClassFileOut =
                     new BufferedOutputStream(new FileOutputStream(outputPathString));) {
            encryptedClassFileOut.write(bytes);
            encryptedClassFileOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
