package com.hack.jackson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

import java.io.IOException;

public class TestMapper {

    /**
     * 测试多余属性装换POJO
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        String str = "{\"name\":\"zhangsan\",\"value\":1}";
        ObjectMapper objectMapper = new ObjectMapper();
        TestMapper1 t1 = objectMapper.readValue(str, TestMapper1.class);
        System.out.println(t1);
        TestMapper2 t2 = objectMapper.readValue(str, TestMapper2.class); //报异常UnrecognizedPropertyException
        System.out.println(t2);

    }

    /**
     * 测试JsonProperty注解
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        String str = "{\"name_x\":\"zhangsan\",\"value\":1}";
        ObjectMapper objectMapper = new ObjectMapper();
        TestMapper3 t1 = objectMapper.readValue(str, TestMapper3.class);
        System.out.println(t1);


        TestMapper3 t2  = new TestMapper3();
        t2.setName("zhangsan");
        System.out.println(objectMapper.writeValueAsString(t2));

    }


    /**
     * 测试JsonProperty注解required
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        String str = "{\"name\":\"zhangsan\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        TestMapper4 t1 = objectMapper.readValue(str, TestMapper4.class);
        System.out.println(t1);

    }


    @Test
    public void test10() throws IOException {
        String str = "{\"name\":\"zhangsan\",\"value\":1}";
        TestMapper1 t1 = JSON.parseObject(str, TestMapper1.class);
        System.out.println(t1);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TestMapper1 {
        private String name;
    }


    @Data
    static class TestMapper2 {
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TestMapper3 {
        @JsonProperty(value="name_x")
        private String name;
    }

    @Getter
    @Setter
    @ToString
    static class TestMapper4 {
        private String name;
        private String desc;

        public TestMapper4() {
        }

//        @JsonCreator
//        public TestMapper4(String name ,@JsonProperty(required = true) String desc) {
//            this.name = name;
//            this.desc = desc;
//        }

        @JsonCreator
        public TestMapper4(@JsonProperty(value = "name") String name , @JsonProperty(value = "desc",required = true) String desc) {
            this.name = name;
            this.desc = desc;
        }

    }

}
