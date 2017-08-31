package com.hack.factory;

/**
 * 预定pizza类
 */
public class OrderPizza extends AbstractPizza {

    public static AbstractPizza order(String type) {
        if ("CheesePizza".equals(type)) {
            return new CheesePizza();
        } else if ("GreekPizza".equals(type)) {
            return new CheesePizza();
        }
        return null;   //这里暂时返回null,空指针可以暂时忽略
    }

    public static void main(String[] args) {
        String type = "CheesePizza";
        AbstractPizza pizza = order(type);
        pizza.prepare();
        pizza.cook();
        pizza.cut();
        pizza.box();

    }
}
