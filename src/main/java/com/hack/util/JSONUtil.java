package com.hack.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class JSONUtil {

    private static Logger log = LoggerFactory.getLogger(JSONUtil.class);

    /**
     * @return 构造返回json
     * @author heshi
     */
    public static JSONObject constructResponse(int code, String msg, Object data) {
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("msg", msg);
        jo.put("data", data);
        return jo;
    }


    /**
     * 返回的数据进行格式转换，并且进行 html 转义
     *
     * @param code 状态标示
     * @param msg  描述信息
     * @param data 需要返回的数据
     * @return
     */
    public static JSONObject constructHtmlResponse(int code, String msg, Object data) {
        // &lt; 对应符号< , &gt; 对应符号>
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("msg", msg);
        jo.put("data", data);
//        String json = jo.toJSONString().replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("'", "&acute;").replaceAll("<", "&lt;").replaceAll(">","&gt;");
//        return JSONObject.parseObject(json);
        return jo;
    }

    /**
     * 返回的数据进行格式转换，并且进行 html 转义
     *
     * @return
     */
    public static JSONObject escapeHtmlOjbect(JSONObject jo) {
        Iterator<String> keys = jo.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jo.get(key);
            if (value instanceof JSONObject) {
                return escapeHtmlOjbect((JSONObject) value);
            } else if (value instanceof JSONArray) {
                value = escapeHtmlArray((JSONArray) value);
            } else if (value instanceof String) {
                String valueStr = value.toString();
                if (valueStr != null && !"".equals(valueStr)) {
                    valueStr = valueStr.toString().replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("'", "&acute;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                    jo.put(key, valueStr);
                }
            }
        }
        return jo;
    }

    public static JSONArray escapeHtmlArray(JSONArray ja) {
        Iterator<Object> keys = ja.iterator();
        while (keys.hasNext()) {
            Object object = keys.next();
            if (object instanceof JSONObject) {
                object = escapeHtmlOjbect((JSONObject) object);
            } else if (object instanceof JSONArray) {
                object = escapeHtmlArray((JSONArray) object);
            } else if (object instanceof String) {
                String valueStr = object.toString();
                if (valueStr != null && !"".equals(valueStr)) {
                    valueStr = valueStr.toString().replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("'", "&acute;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                    object = valueStr;
                }
            }
        }
        return ja;
    }

    public static void main(String[] args) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("age", 24);
//        jsonObject.put("name", "test<>");
//
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jo = new JSONObject();
//        jo.put("poiname","\"test&test\'");
//        jo.put("id", 66);
//        jsonArray.add(jo);
//
//        JSONObject jo2 = new JSONObject();
//        jo2.put("poiname","\"test&test\'test<script>");
//        jo2.put("id", 88);
//        jsonArray.add(jo2);
//
//        jsonObject.put("ja", jsonArray);

    }


    /**
     * @param code
     * @param msg
     * @param data
     * @return 构造返回JSON
     * @author heshi
     */
    public static JSONObject constructResponseJSON(int code, String msg, String data) {
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("msg", msg);
        jo.put("data", data);
        return jo;
    }

    /*
     * author: heshi
     * date:2013-11-06
     * purpose: 构造异常返回json
     *
     */
    public static JSONObject constructExceptionJSON(int code, String msg, String data) {
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("msg", msg);
        jo.put("data", data);
        return jo;
    }


}
