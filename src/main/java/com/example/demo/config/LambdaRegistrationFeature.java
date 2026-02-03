package com.example.demo.config;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Lambda 表达式注册到 GraalVM Native Image
 * 自动扫描指定包下的类并注册 Lambda 捕获类
 *
 * @author ztp
 * @since 2023-08-18
 */
public class LambdaRegistrationFeature implements Feature {

    private static final Logger log = LoggerFactory.getLogger(LambdaRegistrationFeature.class);

    private static final String CLASS_SUFFIX = ".class";

    /**
     * 需要扫描的包路径
     */
    private static final String[] SCAN_PACKAGES = {
            "com.example.demo.repository",
            "com.example.demo.service",
            "com.example.demo.controller",
    };

    @Override
    public void duringSetup(DuringSetupAccess access) {
        log.info("Starting Lambda registration for GraalVM Native Image...");

        for (String packageName : SCAN_PACKAGES) {
            try {
                List<Class<?>> classes = scanClasses(packageName);
                for (Class<?> clazz : classes) {
                    RuntimeSerialization.registerLambdaCapturingClass(clazz);
                    log.info("Registered lambda capturing class: {}", clazz.getName());
                }
            } catch (Exception e) {
                log.warn("Failed to scan package: {}", packageName, e);
            }
        }

        log.info("Lambda registration completed.");
    }

    /**
     * 扫描指定包下的所有类
     *
     * @param packageName 包名
     * @return 类列表
     */
    private List<Class<?>> scanClasses(String packageName) throws IOException {
        List<Class<?>> classes = new ArrayList<>();
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> resources = classLoader.getResources(packagePath);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();

            if ("file".equals(protocol)) {
                scanClassesFromDirectory(new File(resource.getFile()), packageName, classes);
            } else if ("jar".equals(protocol)) {
                scanClassesFromJar(resource, packagePath, classes);
            }
        }

        return classes;
    }

    /**
     * 从目录扫描类
     */
    private void scanClassesFromDirectory(File directory, String packageName, List<Class<?>> classes) {
        if (!directory.exists()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scanClassesFromDirectory(file, packageName + "." + file.getName(), classes);
            } else if (file.getName().endsWith(CLASS_SUFFIX)) {
                String className = packageName + "." + file.getName().replace(CLASS_SUFFIX, "");
                loadClass(className, classes);
            }
        }
    }

    /**
     * 从 JAR 文件扫描类
     */
    private void scanClassesFromJar(URL resource, String packagePath, List<Class<?>> classes) throws IOException {
        JarURLConnection jarConnection = (JarURLConnection) resource.openConnection();
        try (JarFile jarFile = jarConnection.getJarFile()) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.startsWith(packagePath) && entryName.endsWith(CLASS_SUFFIX)) {
                    String className = entryName.replace('/', '.').replace(CLASS_SUFFIX, "");
                    loadClass(className, classes);
                }
            }
        }
    }

    /**
     * 加载类并添加到列表
     */
    private void loadClass(String className, List<Class<?>> classes) {
        try {
            // 排除内部类和匿名类
            if (!className.contains("$")) {
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
            }
        } catch (ClassNotFoundException | NoClassDefFoundError _) {
            log.debug("Could not load class: {}", className);
        }
    }
}
