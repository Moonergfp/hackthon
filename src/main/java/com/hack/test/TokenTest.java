package com.hack.test;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.hack.conf.PropertyUtil;
import com.hack.util.HttpUtil;
import com.hack.util.LogConstant;
import com.hack.util.SHAUtil;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class TokenTest {

    public String getSignature() {
        String appSecret = PropertyUtil.getProperty("appSecret");
        String none = PropertyUtil.getProperty("hack");
        System.out.println(appSecret);
        LogConstant.runLog.info("#appSecret={}#", appSecret);

        String signature = SHAUtil.getSha1(appSecret + none + DateTime.now().getMillis() / 1000);
        return signature;
    }

    @Test
    public void getToken() throws IOException {
        String url = "http://api.cn.ronghub.com/user/getToken.json";

        Map<String, String> param = Maps.newHashMap();
        param.put("userId", "10023");
        param.put("name", "10023");
        param.put("portraitUri", "http://p0.meituan.net/460.280/deal/f4ae26308ae29c0b7f151a962dbeca3a52723.jpg");
        String reponse = HttpUtil.postNoRetry(url, param, 3000, 3000);
        System.out.println(new String(reponse.getBytes(Charsets.UTF_8)));


    }


}
