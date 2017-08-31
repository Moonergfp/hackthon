package com.hack.factory;

/**
 * 抽象披萨基类
 */
public abstract class AbstractPizza {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void prepare() {
        System.out.println(name + " prepare");
    }

    public void cook() {
        System.out.println(name + " cook");
    }

    public void cut(){
        System.out.println(name + " cut");
    }

    public void box(){
        System.out.println(name + " box");
    }
}
