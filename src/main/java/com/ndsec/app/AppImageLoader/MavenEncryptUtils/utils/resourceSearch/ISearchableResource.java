package com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.resourceSearch;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ISearchableResource {

    List<String> getSearchPath() throws
            NoSuchFieldException,
            IllegalAccessException,
            IOException;

    InputStream getResourceStream(String resourceName);

    /**
     * List resources names in specific directory.
     *
     * @param dirName Directory relative name in resource search path. Cannot start
     *                with "/", must end with "/"; For example, "com/example/"
     * @return Names of all resources in the dirName path;
     */
    List<String> listResourceAt(String dirName) throws IOException;

    /**
     * Indicate whether any file found from this resource can be represented as
     * java.io.File object.
     *
     * @return True if any file found from this resource can be represented as
     * java.io.File object, false otherwise.
     */
    boolean canReadAsFile();

    /**
     * Get the file object of resource indicated by resourcePath.
     *
     * @param resourcePath Path of resource.
     * @return File object of resource indicated by resourcePath if resource
     * is found, null otherwise.
     * @throws IllegalStateException Before invoking this method, canReadAsFile method should be
     *                               invoked first. If the result is false, this method should throw
     *                               IllegalStateException.
     */
    File getFile(String resourcePath) throws IllegalStateException;
}
