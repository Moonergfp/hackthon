package com.hack.lambda;

import com.google.common.collect.Lists;
import lombok.Data;
import org.junit.Test;

import java.util.List;

public class TestSet {

    @Test
    public void test() {
        List<TestUser> us = Lists.newArrayList();


        TestUser t1 = new TestUser();
        TestUser t2 = new TestUser();
        us.add(t1);
        us.add(t2);

        us.stream().forEach(testUser ->{
            testUser.setName("hello");

        });
        System.out.println(us);



    }

}

@Data
class TestUser {
    String name ;
}
