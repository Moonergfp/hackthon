package com.hack.test;


public class MyDto extends  BaseDto {
    private String name;
    private  Mydd mydd;

    public Mydd getMydd() {
        return mydd;
    }

    public void setMydd(Mydd mydd) {
        this.mydd = mydd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Mydd{
   private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
