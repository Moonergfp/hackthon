package com.hack.lambda;

import java.util.function.Function;

public class TestMethod2 {

    public static <T> int counter(T[] vals, MyFunc<T> f, T v) {
        int count = 0;

        for (int i = 0; i < vals.length; i++) {
            if (f.func(vals[i], v))
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int count;
        HighTemp[] weekDayHighs = { new HighTemp(89), new HighTemp(82), new HighTemp(90), new HighTemp(89),
                new HighTemp(89), new HighTemp(91), new HighTemp(84), new HighTemp(83) };
        // HighTemp::sameTemp 为实例方法引用
        count = counter(weekDayHighs, HighTemp::sameTemp, new HighTemp(89));
        System.out.println(count + " days had a high of 89");
        HighTemp[] weekDayHighs2 = { new HighTemp(31), new HighTemp(12), new HighTemp(24), new HighTemp(19),
                new HighTemp(18), new HighTemp(12), new HighTemp(-1), new HighTemp(13) };

        count = counter(weekDayHighs2, HighTemp::sameTemp, new HighTempChild(12));
//        count = counter(weekDayHighs2, HighTemp::sameNumber, new HighTempChild(12));
        System.out.println(count + " days had a high of 12");
        int count1 = counter(weekDayHighs2, (a,b)->{return a.sameTemp(b);}, new HighTempChild(12));
        System.out.println("count1==>"+count1);

        count = counter(weekDayHighs, HighTemp::lessThanTemp, new HighTemp(89));
        System.out.println(count + " days had a high less than 89");

        count = counter(weekDayHighs2, HighTemp::lessThanTemp, new HighTemp(19));
        System.out.println(count + " days had a high of less than 19");


        MyFunc<String> strFunc = MyStaticClass::func;
        MyFunc<MyStaticClass> strFunc2 = MyStaticClass::func2;


        String a="xx";
        Function<String, String> upper = String::toUpperCase;

    }
}

class MyStaticClass{
    static boolean func(String v1,String v2){
       return v1.equals(v2);
    }

    boolean func2(MyStaticClass v1){
        return this.equals(v1);
    }
}


interface MyFunc<T> {
    boolean func(T v1, T v2);
}

class HighTemp {
    private int hTemp;

    HighTemp(int ht) {
        hTemp = ht;
    }

    public boolean sameNumber(int num){
       return hTemp == num;
    }

    public boolean sameTemp(HighTemp ht2) {
        return hTemp == ht2.hTemp;
    }

    public boolean lessThanTemp(HighTemp ht2) {
        return hTemp < ht2.hTemp;
    }
}

class HighTempChild extends HighTemp {

    public HighTempChild(int ht) {
        super(ht);
    }
}









