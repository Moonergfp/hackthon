package com.hack.lambda;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntsteramTest {


   @Test
   public void testRange(){;
      List<Integer> ls = IntStream.range(2,10).boxed().collect(Collectors.toList());
      System.out.println(ls);
   }

}
