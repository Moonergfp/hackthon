package com.hack.lambda.collector;

import com.google.common.collect.Lists;
import lombok.Data;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS.optional;

/**
 * 测试最大最小
 */
public class TestMaxMin {
    private List<String> ls = Lists.newArrayList();

    @Before
    public void init() {
        ls.add("a");
        ls.add("bb");
        ls.add("ccc");
    }

    @Test
    public void testMaxBy() {
        Optional<String> optional = ls.stream().max(Comparator.comparing(String::length));
        System.out.println("max===>" + optional.get());
    }

    @Test
    public void testMinBy() {
        Optional<String> optional = ls.stream().min(Comparator.comparing(String::length));
        System.out.println("max===>" + optional.get());
    }
}
