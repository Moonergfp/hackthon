package com.hack.classloader.hierarchy;

public class ClassLoaderTest {
    public void printHierarchy() {
        ClassLoader loader = ClassLoaderTest.class.getClassLoader(); // 获得加载ClassLoaderTest.class这个类的类加载器
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent(); // 获得父类加载器的引用
        }
        System.out.println(loader);
    }

    public static void main(String[] args) {
        ClassLoaderTest t = new ClassLoaderTest();
        t.printHierarchy();
    }
}
