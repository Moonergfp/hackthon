package com.hack.test;

import lombok.Data;
import org.junit.*;
import org.junit.Test;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 测试数字转换
 */
public class NumberTest {

    @org.junit.Test
    public void doubleTest() {
        Double d = 2.578;
        DecimalFormat df = new DecimalFormat("#.00");
        System.out
                .println(new BigDecimal(d).setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue());
    }

    public void validate (@Valid User user) {

        System.out.println("user===>"+user);
    }
    @Test
    public void test(){
        User user = new User();
        user.setAge(234234234);
        this.validate(user);

    }

}

@Data
class User {
   @Digits(integer = 3, fraction = 3)
   private Integer age;

}
