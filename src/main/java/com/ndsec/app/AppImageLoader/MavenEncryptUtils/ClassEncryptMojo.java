package com.ndsec.app.AppImageLoader.MavenEncryptUtils;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.ndsec.app.AppImageLoader.MavenEncryptUtils.constant.CommonConstants;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.javaFiles.JavaFile;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.javaFiles.JavaFiles;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.javaFiles.Line;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.javaFiles.ProjectSourceCodeRoot;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.FileUtils;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.StrUtils;
import com.ndsec.app.AppImageLoader.MavenEncryptUtils.utils.TempDir;

@Mojo(name = "log-switcher")
public class ClassEncryptMojo extends AbstractMojo {

    /**
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     * @readonly
     */
    @Parameter(required = false, defaultValue = "${project.build.sourceDirectory}")
    private File sourceDirectory;

    @Parameter(alias = "with-log", defaultValue = CommonConstants.FALSE)
    private String withLog;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private File target;

    @Parameter(required = false)
    List<String> keeps;

    @Parameter(required = false)
    List<String> keeppackages;

    @Parameter(required = false)
    List<String> encryptpackages;

    @Parameter(required = false)
    List<String> encrypts;

    String classpath;

    String soLibPath;

    public File getClassPathDir() throws MojoExecutionException {
        File classPathDir = new File(target.getAbsolutePath() + File.separator +
                "classes");
        if (!classPathDir.exists()) {
            throw new MojoExecutionException("Classes folder not exists");
        }
        this.classpath = classPathDir.getAbsolutePath();
        return classPathDir;
    }

    public void deleteEncryptedClassFileFromClassPath(List<String> encrypted) {
        for (String absolutePath : encrypted) {
            FileUtils.deleteAll(absolutePath);
        }
    }

    public void cleanAfterEncrypt(List<String> encrypted) {
        deleteEncryptedClassFileFromClassPath(encrypted);

        TempDir.getSingleton().deleteTempDir();
    }

    public void execute() throws MojoExecutionException {
        System.out.println(StrUtils.ft("sourceDirectory:{}", sourceDirectory));
        JavaFiles javaFiles = getJavaFiles();
        for (JavaFile javaFile : javaFiles) {
            getLog().debug("java file need to process: " + javaFile.getPath());
        }

        if (withLog()) {
            getLog().info("build with log version");
            for (JavaFile javaFile : javaFiles) {
                for (Line line : javaFile) {
                    getLog().debug(CommonConstants.LOG_SWITCHER + " : process line: " + line.getStr());
                    line.trim();
                    if (line.isLogLine()) {
                        getLog().debug("line is a log line, process it");
                        getLog().info("uncomment log line: " + line.getStr());
                        line.stripCommentFlag();
                        getLog().info("request for java file update");


                        line.requestUpdateJavaFile();
                        getLog().info("line processing result: " + line.getStr());
                    } else {
                        getLog().debug("line is not a log line, don't process");
                    }
                }

            }

        } else {
            getLog().info("build without log version");
            for (JavaFile javaFile : javaFiles) {
                for (Line line : javaFile) {
                    getLog().debug(CommonConstants.LOG_SWITCHER + " : process line: " + line.getStr());
                    line.trim();
                    if (line.isLogLine()) {
                        getLog().debug("line is not a log line, process it");
                        getLog().info("comment log line: " + line.getStr());
                        line.comment();
                        getLog().info("request for java file update");

                        line.requestUpdateJavaFile();
                        getLog().info("line processing result: " + line.getStr());
                    } else {
                        getLog().debug("line is a log line, don't process");
                    }

                }
            }
        }

        // try {
        // // getLog().info("hello " + name);
        // List<String> classFilesInAppImage = new ArrayList<>();
        // try {
        // getClassPathDir();
        // getLog().info("0");
        // releaseSoLibIntoSystemTempDir();

        // getLog().info("1");
        // Config encryptConfig = prepareEncryptConfig();
        // getLog().info("2");
        // getLog().info(encryptConfig.toString());

        // AppImageMaker appImageMaker = new AppImageMaker(encryptConfig, new
        // MojoLog(this));
        // try {
        // classFilesInAppImage = appImageMaker.produceAppImage();
        // } catch (IOException e) {
        // throw new RuntimeException(e);
        // }

        // generatePackageModeFlagFile();
        // generatePlatformFlagFile(encryptConfig);
        // } finally {
        // cleanAfterEncrypt(classFilesInAppImage);
        // FileUtils.deleteEmptyDir(classpath, new MojoLog(this));
        // }
        // } catch (Exception ex) {
        // throw new MojoExecutionException("plugin running error", ex);
        // }

    }

    private JavaFiles getJavaFiles() {
        ProjectSourceCodeRoot projectSourceCodeRoot = getProjectSourceCodeRoot();
        JavaFiles javaFiles = projectSourceCodeRoot.getAllSourceCodes();
        return javaFiles;
    }

    private ProjectSourceCodeRoot getProjectSourceCodeRoot() {
        ProjectSourceCodeRoot projectSourceCodeRoot = new ProjectSourceCodeRoot(sourceDirectory.getAbsolutePath());
        return projectSourceCodeRoot;
    }

    private boolean withLog() {
        return this.withLog.equals("true");
    }

}
