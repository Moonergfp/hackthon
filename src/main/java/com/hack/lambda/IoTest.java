package com.hack.lambda;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 使用io读取读取文件任意定制行的lambda表达式
 */
public class IoTest {

    public static String readLine(BufferReaderProcessor bp) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("/tmp/test/settings2.xml"))) {
            return bp.read(br);
        }
    }

    public static void main(String[] args) throws Exception {
        int num = 10;
        String str = IoTest.readLine((BufferedReader br) -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < num; i++) {
               sb.append(br.readLine() + "\n") ;
            }
            return sb.toString();
        });
        System.out.println(str);
    }

}
