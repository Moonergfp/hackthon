package com.hack.lambda;

import org.junit.Test;

import java.util.function.IntFunction;

public class TestMethod {
    public static int stringOp(StringFunc sf, String s) {
        return sf.func(s);
    }
    public static void main(String[] args) {
        String inStr = "lambda add power to Java";
        //MyStringOps::strReverse 相当于实现了接口方法func() ，并在接口方法func()中作了MyStringOps.strReverse()操作
        int outStr = stringOp(MyStringOps::strReverse, inStr);
        int outStr2 = stringOp((s)->{return MyStringOps.strReverse(s);}, inStr);
        System.out.println("Original string: " + inStr);
        System.out.println("String reserved: " + outStr);
        System.out.println("String reserved: " + outStr2);



        IntFunction<int[]> arrayMaker = int[]::new;
        IntFunction<int[]> arrayMaker2 = MyTest::aaa;


        MyTest myTest = new MyTest();
        IntFunction<int[]> arrayMaker3 = myTest::bbb;


        int[] array = arrayMaker.apply(10); // 创建数组 int[10]
    }

    @Test
    public void testCompatible(){
        Compatible c = new Compatible();
        Child s1 = new Child();
        s1.age=10;
        Child s2 = new Child();
        s2.age=2;
        System.out.println( c.ttt(Compatible::test,s1,s2));
    }

}

class MyTest{
    public static int[]  aaa(int n){
       return new int[n];
    }

    public int[] bbb(int n){
        return new int[n];
    }


}

interface StringFunc {
    int func(String n);
}
class MyStringOps {
    //静态方法： 反转字符串
    public static Integer strReverse(String str) {
        String result = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result.length();
    }
}



//##########
class Subject{
  public int age;
}
class Child extends  Subject{
}

class Compatible{
   public static int test(Subject s1 ,Subject s2){
       return s1.age - s2.age;

   }

    public int ttt(Compare c ,Subject s1,Subject s2){
        return c.test(s1,s2);
    }
}
interface Compare{
   int test(Subject c1, Subject c2);
}

