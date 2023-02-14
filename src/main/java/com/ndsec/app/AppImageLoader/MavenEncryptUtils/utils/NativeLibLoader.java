package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils;

import com.ndsec.app.AppImageLoader.MavenEncryptUtils.constant.LibNames;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.resourceSearch.ResourceSearcherFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NativeLibLoader {

    private static final NativeLibLoader singleton = new NativeLibLoader();
    List<ArchType> supportedArchTypes = new ArrayList<>(Arrays.asList(
            ArchType.MINGW64, ArchType.AARCH_64, ArchType.X86_64, ArchType.MIPS_64
    ));

    public static NativeLibLoader getSingleton() {
        return singleton;
    }

    private ArchType normalizeArch(String archName) {
        if (archName.matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
            return ArchType.X86_64;
        }
        if (archName.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
            return ArchType.X86_32;
        }

        if (archName.matches("^(arm|arm32)$")) {
            return ArchType.ARM_32;
        }
        if ("aarch64".equals(archName)) {
            return ArchType.AARCH_64;
        }
        if (archName.matches("^(mips|mips32)$")) {
            return ArchType.MIPS_32;
        }
        if (archName.matches("^(mipsel|mips32el)$")) {
            return ArchType.MIPSEL_32;
        }
        if ("mips64".equals(archName)) {
            return ArchType.MIPS_64;
        }
        if ("mips64el".equals(archName)) {
            return ArchType.MIPSEL_64;
        }

        return ArchType.UNKNOWN;
    }

    public ArchType determineArchType() {
        String archName = System.getProperty("os.arch");
        if (archName == null) {
            return ArchType.UNKNOWN;
        }
        ArchType archType = normalizeArch(archName);
        if (supportedPlatform(archType)) {
            return archType;
        }
        return ArchType.UNKNOWN;
    }

    public boolean supportedPlatform(ArchType archType) {
        return supportedArchTypes.contains(archType);
    }

    public boolean thisDevPlatformIsSupported() {
        ArchType thisDevPlatformArch = determineArchType();
        return supportedPlatform(thisDevPlatformArch);
    }

    public void load() {
        ArchType libType = determineArchType();

        switch (libType) {
            case X86_64:
                loadLib("ndsec/app/x86_64", LibNames.X86_64);
                break;
            case AARCH_64:
                loadLib("ndsec/app/aarch64", LibNames.AARCH_64);
                break;
            case MIPS_64:
                loadLib("ndsec/app/mips64", LibNames.MIPS_64);
                break;
            case MINGW64:
                loadLib("ndsec/app/mingw64", LibNames.MINGW_64);
                break;
            case UNKNOWN:
            default:
                throw new RuntimeException("Load lib failed. Lib type unknown");
        }
    }

    private void loadLib(String resourcesRelativeLibDir, String libName) {

        // copy so lib from classpath to system default temp dir
        String appTmpPath = TempDir.getSingleton().getTempDirString();

        String resourcesRelativeLibPath = PathUtils.filePathBuilder()
                                                   .ct(resourcesRelativeLibDir)
                                                   .ct(libName)
                                                   .build();

        createTempDirForLib(appTmpPath, resourcesRelativeLibDir);

        String releasedLibPath = getReleasedLibPath(
                appTmpPath,
                resourcesRelativeLibPath
        );

        releaseLib2TempDir(resourcesRelativeLibPath, releasedLibPath);

        // return the absolute path of so lib
        //return soLibAbsolutePath;
        System.load(releasedLibPath);
    }

    private String getReleasedLibPath(String tmpPath, String resourcesRelativeLibPath) {
        return PathUtils.filePathBuilder()
                        .ct(tmpPath)
                        .ct(resourcesRelativeLibPath)
                        .build();
    }

    private void releaseLib2TempDir(String resourcesRelativeLibPath,
                                    String releasedLibPath) {

        InputStream libInputStream =
                ResourceSearcherFactory.getSystemSearcher()
                                       .getResourceStream(PathUtils.trimHeadSeparator(resourcesRelativeLibPath));
        if (libInputStream == null) {
            // So lib not found, throws exception
            throw new RuntimeException("Lib not found in the resources");
        }

        try {
            Files.deleteIfExists(Paths.get(releasedLibPath));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // release so lib
        try {
            IOUtils.copy(libInputStream,
                    Files.newOutputStream(Paths.get(releasedLibPath)));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void createTempDirForLib(String tmpPath, String resourcesRelativeLibDir) {
        String soLibDirPath = PathUtils.filePathBuilder()
                                       .ct(tmpPath)
                                       .ct(resourcesRelativeLibDir)
                                       .build();

        try {
            Files.createDirectories(Paths.get(soLibDirPath));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public enum ArchType {

        UNKNOWN("unknown"),

        MINGW64("mingw64"),

        X86_64("x86_64"), X86_32("x86_32"),
        ARM_32("arm_32"), AARCH_64("aarch_64"),
        MIPS_32("mips_32"), MIPSEL_32("mipsel_32"), MIPS_64("mips_64"), MIPSEL_64("mipsel_64");

        private String name;

        ArchType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    //public static void main(String[] args) {
    //    System.out.println(new NativeLibLoader().determineArchType());
    //    System.out.println(ResourceSearcherFactory.getSystemSearcher()
    //                                              .getResourceStream("dir/a.dat"));
    //}

}
