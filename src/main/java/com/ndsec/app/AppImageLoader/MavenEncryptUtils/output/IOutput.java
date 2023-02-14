package com.ndsec.app.AppImageLoader.MavenEncryptUtils.output;

import java.io.InputStream;

public interface IOutput {

    //IOutput DEFAULT = new OutputHelper();

    void output(InputStream in, String outputPathString);
    //
    void output(byte[] bytes, String outputPathString);
}
