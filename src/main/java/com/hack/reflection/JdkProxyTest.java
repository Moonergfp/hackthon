package com.hack.reflection;

import org.junit.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * 测试jdk代理
 */
public class JdkProxyTest {

    @org.junit.Test
    public void prohibitAddTest() {
        final List<String> li = new MyList();
        System.out.println(this.getClass().getClassLoader());
        System.out.println(String.class.getClassLoader());
        List<String> liProxy =(List<String>)
                Proxy.newProxyInstance(li.getClass().getClassLoader(),li.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("add".equals(method.getName())) {
                            throw new IllegalArgumentException("禁止使用add方法");
                        }
                        return method.invoke(li, args);
                    }
                });

        liProxy.add("hello");
        System.out.println(liProxy);
    }

    /**
     * 禁用add方法
     */
    class MyList implements List {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public boolean add(Object o) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean addAll(Collection c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Object get(int index) {
            return null;
        }

        @Override
        public Object set(int index, Object element) {
            return null;
        }

        @Override
        public void add(int index, Object element) {

        }

        @Override
        public Object remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator listIterator() {
            return null;
        }

        @Override
        public ListIterator listIterator(int index) {
            return null;
        }

        @Override
        public List subList(int fromIndex, int toIndex) {
            return null;
        }

        @Override
        public boolean retainAll(Collection c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection c) {
            return false;
        }

        @Override
        public boolean containsAll(Collection c) {
            return false;
        }

        @Override
        public Object[] toArray(Object[] a) {
            return new Object[0];
        }
    }

}
