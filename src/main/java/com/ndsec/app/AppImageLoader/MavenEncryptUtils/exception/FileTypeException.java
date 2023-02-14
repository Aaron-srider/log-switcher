package com.ndsec.app.AppImageLoader.MavenEncryptUtils.exception;

public class FileTypeException extends RuntimeException {

    private FileType expected;
    private FileType actual;

    public enum FileType {
        DIR,
        REGULAR_FILE
    }

    public FileTypeException(String filePath, FileType expected,
                             FileType actual) {
        super("Expected file type is :{" + expected + "} but actual is: {" + actual + "}" +
                ".FilePath: {" + filePath + "}");
        this.actual = actual;
        this.expected = expected;
    }

}
