package com.ndsec.app.AppImageLoader.MavenEncryptUtils.properties;

import java.util.List;

public class Config
{

    private String x86Only;
    private String classpath;
    private String output;
    private String resultName;
    private String encryptAll;
    private List<String> keep;
    private List<String> encrypt;
    private List<String> encryptpackages;
    private String soPath;

    private List<String> keeppackages;

    public String getX86Only() {
        return x86Only;
    }

    public void setX86Only(String x86Only) {
        this.x86Only = x86Only;
    }

    public List<String> keeppackage() {
        return keeppackages;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public void setEncryptAll(String encryptAll) {
        this.encryptAll = encryptAll;
    }

    public void setKeep(List<String> keep) {
        this.keep = keep;
    }

    public void setEncrypt(List<String> encrypt) {
        this.encrypt = encrypt;
    }

    public void setSoPath(String soPath) {
        this.soPath = soPath;
    }

    public void setKeeppackages(List<String> keeppackages) {
        this.keeppackages = keeppackages;
    }

    public String classpath(){
        return classpath;
    }

    public String output(){
        return output;
    }

    public String resultName(){
        return resultName;
    }

    public String encryptAll() {
        return encryptAll;
    }

    public List<String> keep(){
        return keep;
    }

    public List<String> encrypt(){
        return encrypt;
    }

    public List<String> encryptpackages(){
        return encryptpackages;
    }

    public void setEncryptpackages(List<String> encryptpackages){
        this.encryptpackages = encryptpackages;
    }

    public String soPath(){
        return soPath;
    }

    @Override
    public String toString() {
        return "Config{" +
                "x86Only='" + x86Only + '\'' +
                ", classpath='" + classpath + '\'' +
                ", output='" + output + '\'' +
                ", resultName='" + resultName + '\'' +
                ", encryptAll='" + encryptAll + '\'' +
                ", keep=" + keep +
                ", encrypt=" + encrypt +
                ", encryptpackages=" + encryptpackages +
                ", soPath='" + soPath + '\'' +
                ", keeppackages=" + keeppackages +
                '}';
    }
}
