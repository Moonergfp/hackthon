package com.hack.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.hack.conf.PropertyUtil;
import com.hack.dao.UserDao;
import com.hack.domain.UserDb;
import com.hack.util.HttpUtil;
import com.hack.util.JSONUtil;
import com.hack.util.LogConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

import static com.google.common.base.Preconditions.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserDao userDao;

    private static final String getTokenUrl = PropertyUtil.getProperty("getTokenUrl");
    private static final String basicHeadPicUrl = PropertyUtil.getProperty("basicHeadPicUrl");

    @ResponseBody
    @RequestMapping("/register")
    public Object register(UserDb userDb) {
        LogConstant.runLog.info("#UserController.register#userDb={}", userDb);
        boolean succeed = false;
        try {
            checkArgument(!Strings.isNullOrEmpty(userDb.getAcct()), "账号为空");
            checkArgument(!Strings.isNullOrEmpty(userDb.getName()), "名字为空");
            checkArgument(!Strings.isNullOrEmpty(userDb.getPwd()), "密码为空");
            userDb.setHeadPic("http://p0.meituan.net/kuailv/70b2c1ab6281cc1605667cb1f28896219493.png");
            succeed = userDao.insert(userDb) > 0 ? true : false;
            LogConstant.runLog.info("#UserController.register#register succeed = {}", succeed);
        } catch (IllegalArgumentException e2) {
            LogConstant.runLog.error("#UserController.register#sys error", e2);
            return new JSONUtil().constructResponse(401, e2.getMessage(), null);
        } catch (Exception e) {
            LogConstant.runLog.error("#UserController.register#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }

        if (succeed) {
            return new JSONUtil().constructResponse(200, "成功", userDb.getId());
        } else {
            return new JSONUtil().constructResponse(401, "失败", userDb.getId());
        }
    }


    @ResponseBody
    @RequestMapping("/login")
    public Object login(String acct, String pwd) {
        LogConstant.runLog.info("#UserController.login#acct={},pwd=#{}", acct, pwd);
        boolean succeed = false;

        JSONObject result = new JSONObject();
        try {
            checkArgument(!Strings.isNullOrEmpty(acct), "账号为空");
            checkArgument(!Strings.isNullOrEmpty(pwd), "密码为空");
            //获取用户
            UserDb userDb = userDao.getByAcctAndPwd(acct, pwd);
            if (userDb == null) {
                return new JSONUtil().constructResponse(402, "用户不存在", null);
            }
            if(!Strings.isNullOrEmpty(userDb.getToken())){
                result.put("userId",userDb.getId());
                result.put("headPic",userDb.getHeadPic());
                result.put("token",userDb.getToken());
                result.put("acct",userDb.getAcct());
                result.put("name",userDb.getName());
                return new JSONUtil().constructResponse(200, "成功", result);
            }
            int userId = userDb.getId();
            //获取token
            Map<String, String> param = Maps.newHashMap();
            param.put("userId", String.valueOf(userId));
            param.put("name", userDb.getName());
            param.put("portraitUri", basicHeadPicUrl);
            String reponse = HttpUtil.postNoRetry(getTokenUrl, param, 5000, 5000);
            LogConstant.runLog.info("#UserController.login#reponse={}", reponse);
            JSONObject jsonResult = JSON.parseObject(reponse);
            if (jsonResult != null && jsonResult.getInteger("code") == 200) {
                succeed = true;
                result.put("userId",userDb.getId());
                result.put("headPic",userDb.getHeadPic());
                result.put("token",jsonResult.getString("token"));
                result.put("acct",userDb.getAcct());
                result.put("name",userDb.getName());
                //更新token
                userDao.updateToken(userDb.getId(), jsonResult.getString("token"));
            }
        } catch (IllegalArgumentException e2) {
            LogConstant.runLog.error("#UserController.register#sys error", e2);
            return new JSONUtil().constructResponse(401, e2.getMessage(), null);
        } catch (Exception e) {
            LogConstant.runLog.error("#UserController.register#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        if (succeed) {
            return new JSONUtil().constructResponse(200, "成功", result);
        } else {
            return new JSONUtil().constructResponse(401, "失败", null);
        }
    }

    @ResponseBody
    @RequestMapping("/info/{userId}")
    public Object info(@PathVariable(value = "userId") int userId) {
        LogConstant.runLog.info("#UserController.info#userId={}", userId);
        if (userId <= 0) {
            return new JSONUtil().constructResponse(403, "参数错误", null);
        }
        UserDb userDb = null;
        try {
            userDb = userDao.getById(userId);
        } catch (Exception e) {
            LogConstant.runLog.error("#UserController.info#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        return new JSONUtil().constructResponse(200, "成功", userDb);
    }

}
