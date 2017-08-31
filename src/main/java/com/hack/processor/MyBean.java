package com.hack.processor;

import org.springframework.beans.factory.InitializingBean;

public class MyBean implements InitializingBean{

    private String desc;
    private String remark;

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        System.out.println("调用desc方法,dec-->" + desc);
        this.desc = desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        System.out.println("remark,remark-->" + remark);
        this.remark = remark;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MyBean,afterPropertiesSet-->");
        this.desc="desc2";

    }


    @Override
    public String toString() {
        return "com.hack.processor.MyBean{" +
                "desc='" + desc + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
