package com.ndsec.app.AppImageLoader.MavenEncryptUtils.javaFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.IOUtils;

public class ProjectSourceCodeRoot {

    String sourceDir;

    public ProjectSourceCodeRoot() {
    }

    public ProjectSourceCodeRoot(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getSourceDir() {
        return this.sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public ProjectSourceCodeRoot sourceDir(String sourceDir) {
        setSourceDir(sourceDir);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProjectSourceCodeRoot)) {
            return false;
        }
        ProjectSourceCodeRoot projectSourceCodeRoot = (ProjectSourceCodeRoot) o;
        return Objects.equals(sourceDir, projectSourceCodeRoot.sourceDir);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sourceDir);
    }

    @Override
    public String toString() {
        return "{" +
                " sourceDir='" + getSourceDir() + "'" +
                "}";
    }

    public JavaFiles getAllSourceCodes() {
        // diveIn();
        File sourceFootFile = new File(sourceDir);
        List<JavaFile> javaFileList = diveIn(sourceFootFile);
        JavaFiles javaFiles = new JavaFiles(javaFileList);
        return javaFiles;
    }

    private List<JavaFile> diveIn(File sourceFootFile) {
        List<JavaFile> javaFileList = new ArrayList<>();
        if (sourceFootFile.isDirectory()) {
            for (File subFile : sourceFootFile.listFiles()) {
                javaFileList.addAll(diveIn(subFile));
            }
        } else {
            if (sourceFootFile.getName().endsWith(".java")) {
                String path = sourceFootFile.getAbsolutePath();
                List<String> lineStrs = IOUtils.convertFileToLines(path);
                JavaFile javaFile = new JavaFile(path);
                List<Line> lines = convertStringListToLine(lineStrs, javaFile);
                javaFile.setLines(lines);
                javaFileList.add(javaFile);
            }
        }
        return javaFileList;
    }

    private List<Line> convertStringListToLine(List<String> lineStrs, JavaFile javaFile) {
        List<Line> results = new ArrayList<>();
        for (int i = 0; i < lineStrs.size(); i++) {
            String line = lineStrs.get(i);
            results.add(new Line(line, javaFile, i));
        }
        return results;
    }

}
