package com.ndsec.app.AppImageLoader.MavenEncryptUtils.javaFiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class JavaFiles implements Iterable<JavaFile> {

    List<JavaFile> javaFileList = new ArrayList<>();


    public JavaFiles() {
    }

    public JavaFiles(List<JavaFile> javaFileList) {
        this.javaFileList = javaFileList;
    }

    public List<JavaFile> getJavaFileList() {
        return this.javaFileList;
    }

    public void setJavaFileList(List<JavaFile> javaFileList) {
        this.javaFileList = javaFileList;
    }

    public JavaFiles javaFileList(List<JavaFile> javaFileList) {
        setJavaFileList(javaFileList);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof JavaFiles)) {
            return false;
        }
        JavaFiles javaFiles = (JavaFiles) o;
        return Objects.equals(javaFileList, javaFiles.javaFileList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(javaFileList);
    }

    @Override
    public String toString() {
        return "{" +
            " javaFileList='" + getJavaFileList() + "'" +
            "}";
    }


    @Override
    public Iterator<JavaFile> iterator() {
        return javaFileList.iterator();
    }

}
