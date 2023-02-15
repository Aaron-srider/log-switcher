package com.ndsec.app.AppImageLoader.MavenEncryptUtils.javaFiles;

import java.util.Objects;

public class Line {

    JavaFile javafile;

    int index;

    String str;

    public Line() {
    }

    public Line(String str, JavaFile javafile, int index) {
        this.str = str;
        this.javafile = javafile;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Line index(int index) {
        setIndex(index);
        return this;
    }

    public String getStr() {
        return this.str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Line str(String str) {
        setStr(str);
        return this;
    }

    public JavaFile getJavafile() {
        return this.javafile;
    }

    public void setJavafile(JavaFile javafile) {
        this.javafile = javafile;
    }

    public Line javafile(JavaFile javafile) {
        setJavafile(javafile);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Line)) {
            return false;
        }
        Line line = (Line) o;
        return Objects.equals(javafile, line.javafile) && index == line.index && Objects.equals(str, line.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(javafile, index, str);
    }

    @Override
    public String toString() {
        return "{" +
                " javafile='" + getJavafile() + "'" +
                ", index='" + getIndex() + "'" +
                ", str='" + getStr() + "'" +
                "}";
    }

    public void trim() {
        str.trim();
    }

    public boolean isLogLine() {
        String copyStr = new String(this.str);
        copyStr = copyStr.trim();

        if (copyStr.startsWith("//")) {
            copyStr = strAfterStripCommentFlag();
            copyStr = cleanStartWhite(copyStr);
        }

        if (copyStr.startsWith("log.debug")) {
            return true;
        } else if (copyStr.startsWith("log.info")) {
            return true;
        } else if (copyStr.startsWith("log.warn")) {
            return true;
        } else if (copyStr.startsWith("log.trace")) {
            return true;
        } else if (copyStr.startsWith("log.error")) {
            return true;
        }
        return false;
    }

    private String cleanStartWhite(String str) {
        return str.trim();
    }

    public void stripCommentFlag() {
        if (!this.startWithCommentFlag()) {
            return;
        }
        int commentFlagLen = getCommentFlagLength();
        str = str.substring(commentFlagLen, str.length());
    }

    public String strAfterStripCommentFlag() {
        String copy = new String(this.str);
        copy = copy.trim();
        if (!copy.startsWith("//")) {
            return new String(this.str.trim());
        }
        int commentFlagLen = getCommentFlagLength();
        String strAfterStripCommentFlag = str.substring(commentFlagLen, str.length());
        return strAfterStripCommentFlag;
    }

    public static void main(String[] args) {
        Line line = new Line();
        line.setStr("    // log.debug");
        System.out.println(line.isLogLine());
        System.out.println(line.strAfterStripCommentFlag());
        ;

        line.setStr(" // Test");
        System.out.println(line.isLogLine());
        System.out.println(line.strAfterStripCommentFlag());
        ;

    }

    public void requestUpdateJavaFile() {
        javafile.requestUpdate(this);
    }

    private int getCommentFlagLength() {
        str = this.str.trim();
        int i = 0;
        while (nextIsSlash(i)) {
            i++;
        }
        return i;

    }

    private boolean nextIsSlash(int nowIndex) {
        return str.charAt(nowIndex) == '/';
    }

    private boolean startWithCommentFlag() {
        return str.trim().startsWith("//");
    }

    public boolean isNotLogLine() {
        return !isLogLine();
    }

    public void comment() {
        if (str.startsWith("//")) {
            return;
        }
        str = "// " + str;
    }

}
