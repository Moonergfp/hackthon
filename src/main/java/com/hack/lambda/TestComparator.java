package com.hack.lambda;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Comparator.comparing;

/**
 * 测试局部变量
 */
public class TestComparator {

    @Test
    public void test(){
        List<Apple> ls = Lists.newArrayList();
        ls.sort(comparing(a -> a.getWeight()));
    }
    static class Apple{
        private int weight;

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

}

