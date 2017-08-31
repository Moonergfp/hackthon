package com.hack.lambda;

import java.io.BufferedReader;

@FunctionalInterface
public interface BufferReaderProcessor{
    String read(BufferedReader br) throws Exception ;
}
