package com.hack.factory;

public class SimplePizzaFactory {

    public static AbstractPizza create(String type ){
        if ("CheesePizza".equals(type)) {
            return new CheesePizza();
        } else if ("GreekPizza".equals(type)) {
            return new CheesePizza();
        }
        return null;   //这里暂时返回null,空指针可以暂时忽略
    }
}
