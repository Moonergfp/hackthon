package com.hack.lambda.collector;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 实现自定义Collector
 */
public class  ToListCollector<T> implements Collector{
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>,T> accumulator() {
        return (list,item)->list.add(item);
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1,list2)->{list1.addAll(list2);return list1;};
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
