package com.ndsec.app.AppImageLoader.MavenEncryptUtils.output;

import java.io.IOException;
import java.io.InputStream;

public interface IRead {

    IRead DEFAULT = new ReadHelper();

    byte[] read(InputStream in) throws IOException;

    //byte[] read(String classFilePathString) throws IOException;
}
