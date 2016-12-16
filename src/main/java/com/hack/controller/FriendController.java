package com.hack.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.hack.conf.PropertyUtil;
import com.hack.dao.FriendRelationDao;
import com.hack.dao.UserDao;
import com.hack.domain.FriendRelationDb;
import com.hack.domain.UserDb;
import com.hack.util.HttpUtil;
import com.hack.util.JSONUtil;
import com.hack.util.LogConstant;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@RequestMapping("/friend")
public class FriendController {

    @Resource
    private FriendRelationDao friendRelationDao;


    @ResponseBody
    @RequestMapping("/addFriend")
    public Object addFriend(FriendRelationDb friendRelationDb){
        LogConstant.runLog.info("#FriendController.addFriend#friendRelationDb={}", friendRelationDb);
        if (friendRelationDb == null || friendRelationDb.getUserId() <=0 || friendRelationDb.getAskedUserId() <= 0 ) {
            return new JSONUtil().constructResponse(403, "参数错误", null);
        }
        try {
            FriendRelationDb friendRelationDb2 = new FriendRelationDb();
            friendRelationDb2.setUserId(friendRelationDb.getAskedUserId());
            friendRelationDb2.setAskedUserId(friendRelationDb.getUserId());
            int num1 = friendRelationDao.insert(friendRelationDb);
            int num2 = friendRelationDao.insert(friendRelationDb2);
            LogConstant.runLog.info("#GroupController.add#num1={},num2={}",num1,num2);
            if(num1 <= 0 || num2<=0 ){
                return new JSONUtil().constructResponse(401, "成功",null );
            }
        } catch (Exception e) {
            LogConstant.runLog.error("#GroupController.add#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        return new JSONUtil().constructResponse(200, "成功",null );
    }


    @ResponseBody
    @RequestMapping("/list")
    public Object list(int userId){
        LogConstant.runLog.info("#FriendController.list#userId={}", userId);
        if (userId <= 0) {
            return new JSONUtil().constructResponse(403, "参数错误", null);
        }
        List<UserDb> users = null;
        JSONArray ja = null;
        try {
             users = friendRelationDao.getFriendList(userId);
            if(!CollectionUtils.isEmpty(users)){
                ja = new JSONArray();
                for(UserDb userDb : users){
                    JSONObject userJobj = new JSONObject();
                    userJobj.put("userId",userDb.getId());
                    userJobj.put("userName",userDb.getName());
                    userJobj.put("headPic",userDb.getHeadPic());
                    ja.add(userJobj);
                }
            }
        } catch (Exception e) {
            LogConstant.runLog.error("#GroupController.add#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        return new JSONUtil().constructResponse(200, "成功",ja);
    }

}
