package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.logApi;

import org.apache.maven.plugin.Mojo;

public class MojoLog implements Log {

    private Mojo mojo;

    public MojoLog(Mojo mojo) {
        this.mojo = mojo;
    }

    @Override
    public void debug(String msg) {
        mojo.getLog().debug(msg);
    }

    @Override
    public void info(String msg) {
        mojo.getLog().info(msg);
    }

    @Override
    public void error(String msg) {
        mojo.getLog().error(msg);
    }
}
