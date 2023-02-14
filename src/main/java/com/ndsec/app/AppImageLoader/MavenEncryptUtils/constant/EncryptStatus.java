package com.ndsec.app.AppImageLoader.MavenEncryptUtils.constant;

public enum EncryptStatus {
    ENCRYPTED(1),
    NOT_ENCRYPT_YET(0)
    ;

    Integer flag;

    EncryptStatus(Integer flag) {
        this.flag = flag;
    }
}
