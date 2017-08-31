package com.hack.classloader;

import org.junit.Test;

import java.net.URL;

public class ClassLoaderTest {
    public static int a = 2;
    static{
        a = 5;
        b = 3;
    }

    public static int b = 2;

    public static void main(String[] args){
        System.out.println(a);
        System.out.println(b);
    }



    @Test
    public void testBootstrap(){
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }

        System.out.println("############");
        System.out.println(System.getProperty("sun.boot.class.path"));
    }

    @Test
    public void printHierarchy(){
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();    //获得加载ClassLoaderTest.class这个类的类加载器
        while(loader != null) {
            System.out.println(loader);
            loader = loader.getParent();    //获得父类加载器的引用
        }
        System.out.println(loader);
    }
}
