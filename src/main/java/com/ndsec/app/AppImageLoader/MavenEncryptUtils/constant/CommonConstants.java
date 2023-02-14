package com.ndsec.app.AppImageLoader.MavenEncryptUtils.constant;

public class CommonConstants {
    public static final String LOG_SWITCHER = "LOG_SWITCHER";
    public static final String APP_IMAGE_NAME = "app-image";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static final String CLASS_FILE_SUFFIX = ".class";
    public static final String ENCRYPTION_WORKING_DIR_NAME = "app-image-dir";

    /**
     * Native lib file name. The native lib is under the resources.
     * <p>
     * You should change this value everytime you change the name of
     * so lib file, in order to ensure the so lib can be found in the classpath
     * correctly.
     */
    public static final String X86_LIB_NAME = "libndsec_app_image.so";

    public static final String X86_LIB_PATH = "ndsec/app/x86_64";

    public static final String DEFAULT_PACK_NAME = X86_LIB_NAME;

}
