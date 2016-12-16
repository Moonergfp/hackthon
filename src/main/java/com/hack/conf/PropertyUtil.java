package com.hack.conf;

import com.google.common.base.Strings;
import com.hack.util.LogConstant;

import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyUtil {

    private static Properties properties = new Properties();

    static {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(PropertyUtil.class.getClassLoader()
                    .getResourceAsStream("conf.properties"), "UTF-8");
            properties.load(inputStreamReader);
        } catch (Exception ex) {
            LogConstant.runLog.error("[PropertiesUtil]读取配置文件出错了", ex);
            throw new RuntimeException("读取登录配置文件");
        }
    }

    public static String getProperty(String key, String defaultValue) {
        String value = (String) properties.get(key);
        if (Strings.isNullOrEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

    public static String getProperty(String key) {
        String value = (String) properties.get(key);
        return value;
    }
}
