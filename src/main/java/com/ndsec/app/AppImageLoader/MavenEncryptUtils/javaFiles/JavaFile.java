package com.ndsec.app.AppImageLoader.MavenEncryptUtils.javaFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.IOUtils;

public class JavaFile implements Iterable<Line> {
    String path;
    List<Line> lines = new ArrayList<>();

    public JavaFile() {
    }

    public JavaFile(String path) {
        this.path = path;
    }

    public JavaFile(String path, List<Line> lines) {
        this.path = path;
        this.lines = lines;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public JavaFile path(String path) {
        setPath(path);
        return this;
    }

    public JavaFile lines(List<Line> lines) {
        setLines(lines);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof JavaFile)) {
            return false;
        }
        JavaFile javaFile = (JavaFile) o;
        return Objects.equals(path, javaFile.path) && Objects.equals(lines, javaFile.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, lines);
    }

    @Override
    public String toString() {
        return "{" +
                " path='" + getPath() + "'" +
                ", lines='" + getLines() + "'" +
                "}";
    }

    @Override
    public Iterator<Line> iterator() {
        return lines.iterator();
    }

    public void requestUpdate(Line line) {
        int lineNum = line.getIndex();
        String lineStr = line.getStr();

        List<String> originLines = IOUtils.convertFileToLines(path);

        originLines.set(lineNum, lineStr);
        updateFile(originLines);
    }

    private void updateFile(List<String> updatedLines) {
        try (BufferedWriter out = Files.newBufferedWriter(Paths.get(path));) {
            for (String updatedLine : updatedLines) {
                out.write(updatedLine + "\n");
            }
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
