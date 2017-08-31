package com.hack.test;

import java.util.function.Function;

public class TestMethod {
}

interface StringFunc {
    String func(String n);
}
class MyStringOps {
    //普通方法： 反转字符串
    public String strReverse(String str) {
        String result = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result;
    }
}
class MethodRefDemo2 {
    public static String stringOp(StringFunc sf, String s) {
        return sf.func(s);
    }
    public static void main(String[] args) {
        String inStr = "lambda add power to Java";
        MyStringOps strOps = new MyStringOps();//实例对象
        //MyStringOps::strReverse 相当于实现了接口方法func() ，并在接口方法func()中作了MyStringOps.strReverse()操作
        StringFunc sf = strOps::strReverse;
        String outStr = stringOp(strOps::strReverse, inStr);


        System.out.println("Original string: " + inStr);
        System.out.println("String reserved: " + outStr);


        MyFunc<MyStaticClass> func2 = MyStaticClass::func2;
    }
}
interface MyFunc<T> {
    boolean func(T v1, T v2);
}
class MyStaticClass{
    boolean func2(MyStaticClass v1){
        return this.equals(v1);
    }
}

