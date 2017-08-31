package com.hack.lambda;

import org.junit.Test;

import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

public class TestToIntFunction {


    @Test
    public void test(){


        ToIntFunction<User> tf = User::getAge;
        ToIntFunction<User> tf2 = u->u.getAge();
        ToIntFunction<User> tf3 = User::get;


    }


}
class User{
    private int age;

    public static int get(User u){
        return u.getAge();
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
