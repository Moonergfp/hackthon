package com.hack.factory;

/**
 * 预定pizza类
 */
public class OrderPizza2 extends AbstractPizza {

    public static AbstractPizza order(String type) {
        return SimplePizzaFactory.create(type);
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
