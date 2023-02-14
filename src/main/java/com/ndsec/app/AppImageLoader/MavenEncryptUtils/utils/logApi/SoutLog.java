package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.logApi;

public class SoutLog implements Log {
    @Override
    public void debug(String msg) {
        System.out.println(msg);
    }

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void error(String msg) {
        System.out.println(msg);
    }


}
