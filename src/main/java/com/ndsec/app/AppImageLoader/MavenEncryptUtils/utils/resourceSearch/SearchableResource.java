package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.resourceSearch;

public abstract class SearchableResource implements ISearchableResource {
    protected String getCanNotRepresentedAsFileExceptionMsg() {
        return "Files in this resource could " +
                "not be represented as File object";
    }
}
