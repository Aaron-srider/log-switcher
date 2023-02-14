package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.resourceSearch;

public class ResourceSearcherFactory {

    public static ISearchableResource getSystemSearcher() {
        return SystemSearchableResource.getSingleton();
    }

    //public static ISearchableResource getCustomSearcher() {
    //    return CustomSearchableResource.getSingleton();
    //}

}
