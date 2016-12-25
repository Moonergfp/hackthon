package com.hack.service;

import com.hack.util.LogConstant;
import org.springframework.stereotype.Service;

/**
 * Created by guofeipeng on 16/1/10.
 */

@Service
public class LogService {

    public void test(){
        LogConstant.runLog.info("#LogService test#");
    }
}
