package com.hack.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hack.conf.PropertyUtil;
import com.hack.dao.ActivityDao;
import com.hack.dao.GroupDao;
import com.hack.dao.GroupUserRelationDao;
import com.hack.dao.UserDao;
import com.hack.domain.ActivityDb;
import com.hack.domain.GroupDb;
import com.hack.domain.GroupUserRelationDb;
import com.hack.domain.UserDb;
import com.hack.exception.HackException;
import com.hack.service.GroupService;
import com.hack.util.HttpUtil;
import com.hack.util.JSONUtil;
import com.hack.util.LogConstant;
import com.hack.vo.GroupBatch;
import com.hack.vo.GroupMem;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.acl.Group;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupDao groupDao;
    @Resource
    private GroupUserRelationDao groupUserRelationDao;
    @Resource
    private ActivityDao activityDao;
    @Resource
    private GroupService groupService;


    private static final String getTokenUrl = PropertyUtil.getProperty("getTokenUrl");
    private static final String createGroupUrl = PropertyUtil.getProperty("createGroupUrl");
    private static final String joinGroupUrl = PropertyUtil.getProperty("joinGroupUrl");
    private static final String bakGroupUrl = PropertyUtil.getProperty("bakGroupUrl");


    /**
     * 创建群组
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
    @Transactional
    public Object batchCreate(@RequestBody GroupBatch batch) {
        LogConstant.runLog.info("#GroupController.batchCreate#batch={}", batch);
        List<Integer> groupIdList = null;
        try {
            checkArgument(batch.getActivityId() > 0, "活动id为空");
            checkArgument(!CollectionUtils.isEmpty(batch.getGroupList()), "组列表为空");
            groupIdList = groupService.batchCreateDb(batch.getActivityId(), batch.getGroupList());
            LogConstant.runLog.info("#GroupController.batchCreate#groupIdList={}", groupIdList);
        } catch (IllegalArgumentException e) {
            LogConstant.runLog.error("#GroupController.batchCreate#param error", e);
            return new JSONUtil().constructResponse(402, e.getMessage(), null);
        } catch (Exception e) {
            LogConstant.runLog.error("#GroupController.batchCreate#param error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        if (!CollectionUtils.isEmpty(groupIdList)) {
            return new JSONUtil().constructResponse(200, "成功", groupIdList);
        } else {
            return new JSONUtil().constructResponse(401, "失败", null);
        }

    }


    /**
     * 创建群组
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/create")
    @Transactional
    public Object create(GroupDb groupDb) {
        LogConstant.runLog.info("#GroupController.create#groupDb={}", groupDb);
        boolean createGroupSucceed = false;
        boolean createRYGroupSucceed = false;
        try {
            checkArgument(!Strings.isNullOrEmpty(groupDb.getGroupName()), "名字为空");
            createGroupSucceed = groupDao.insert(groupDb) > 0 ? true : false;
            LogConstant.runLog.info("#GroupController.create#createGroupSucceed = {}", createGroupSucceed);
            if (createGroupSucceed) {
                Map<String, String> param = Maps.newHashMap();
                param.put("userId", String.valueOf(groupDb.getOwnerId()));
                param.put("groupId", String.valueOf(groupDb.getId()));
                param.put("groupName", groupDb.getGroupName());
                String resp = HttpUtil.postNoRetry(createGroupUrl, param, 10000, 10000);
                LogConstant.runLog.info("#GroupController.create#createRYGroupUrl resp= {}", resp);

                if (!Strings.isNullOrEmpty(resp)) {
                    JSONObject jsonObject = JSON.parseObject(resp);
                    if (jsonObject.getInteger("code") == 200) {
                        createRYGroupSucceed = true;
                    }
                }
            }
        } catch (IllegalArgumentException e2) {
            LogConstant.runLog.error("#UserController.register#sys error", e2);
            return new JSONUtil().constructResponse(401, e2.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            LogConstant.runLog.error("#GroupController.create#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }

        if (createRYGroupSucceed && createGroupSucceed) {
            return new JSONUtil().constructResponse(200, "成功", groupDb.getId());
        } else {
            return new JSONUtil().constructResponse(401, "失败", groupDb.getId());
        }
    }


    public static  void main(String[] args){
       int n = 5;
        System.out.println(new Random().nextInt(n));
    }

    @ResponseBody
    @RequestMapping("/{groupId}")
    public Object getById(@PathVariable(value = "groupId") int groupId) {
        LogConstant.runLog.info("#GroupController.getById#groupId={}", groupId);
        if (groupId <= 0) {
            return new JSONUtil().constructResponse(403, "参数错误", null);
        }
        GroupDb groupDb = null;
        JSONObject result = null;
        try {
            groupDb = groupDao.getById(groupId);
            if (groupDb != null) {
                result = new JSONObject();
                result.put("groupId", groupDb.getId());
                result.put("groupName", groupDb.getGroupName());
                result.put("des", groupDb.getDes());
                List<UserDb> users = groupUserRelationDao.getUsersByGroupId(groupDb.getId());
                if (!CollectionUtils.isEmpty(users)) {
                    JSONArray jaUser = new JSONArray();
                    for (UserDb userDb : users) {
                        JSONObject userJobj = new JSONObject();
                        userJobj.put("userId", userDb.getId());
                        userJobj.put("userName", userDb.getName());
                        userJobj.put("headPic", userDb.getHeadPic());
                        jaUser.add(userJobj);
                    }
                    result.put("groupMems", jaUser);
                }
            }

        } catch (Exception e) {
            LogConstant.runLog.error("#GroupController.getById#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        return new JSONUtil().constructResponse(200, "成功", result);
    }


    /**
     * 加入群组
     *
     * @param groupUserRelationDb
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    @Transactional
    public Object add(GroupUserRelationDb groupUserRelationDb) {
        LogConstant.runLog.info("#GroupController.add#groupUserRelationDb={}", groupUserRelationDb);
        try {
            checkArgument(groupUserRelationDb.getGroupId() > 0, "参数错误");
            checkArgument(groupUserRelationDb.getUserId() > 0, "参数错误");
            GroupDb groupDb = groupDao.getById(groupUserRelationDb.getGroupId());
            if (groupDb == null) {
                return new JSONUtil().constructResponse(402, "群不存在", null);
            }
            //查询是否入群
            GroupUserRelationDb gRDb = groupUserRelationDao.getByGroupIdAndUserId(groupUserRelationDb.getGroupId(), groupUserRelationDb.getUserId());
            if (gRDb != null) {
                return new JSONUtil().constructResponse(200, "已加入", null);
            }
            int num = groupUserRelationDao.insert(groupUserRelationDb);
            LogConstant.runLog.info("#GroupController.add#num={}", num);
            if (num <= 0) {
                return new JSONUtil().constructResponse(401, "成功", null);
            }
            Map<String, String> param = Maps.newHashMap();
            param.put("userId", String.valueOf(groupUserRelationDb.getUserId()));
            param.put("groupId", String.valueOf(groupUserRelationDb.getGroupId()));
            param.put("groupName", groupDb.getGroupName());
            String resp = HttpUtil.postNoRetry(joinGroupUrl, param, 10000, 10000);
            LogConstant.runLog.info("#GroupController.add#join rong yun  resp= {}", resp);

            if (!Strings.isNullOrEmpty(resp)) {
                JSONObject jsonObject = JSON.parseObject(resp);
                if (jsonObject.getInteger("code") != 200) {
                    return new JSONUtil().constructResponse(401, "操作失败", null);
                }
            }

        } catch (IllegalArgumentException e2) {
            LogConstant.runLog.error("#UserController.register#sys error", e2);
            return new JSONUtil().constructResponse(401, e2.getMessage(), null);
        } catch (Exception e) {
            LogConstant.runLog.error("#GroupController.add#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        return new JSONUtil().constructResponse(200, "成功", null);
    }

    /**
     * 退出群组
     *
     * @param groupUserRelationDb
     * @return
     */
    @ResponseBody
    @RequestMapping("/back")
    public Object back(GroupUserRelationDb groupUserRelationDb) {
        LogConstant.runLog.info("#GroupController.back#groupUserRelationDb={}", groupUserRelationDb);
        try {
            checkArgument(groupUserRelationDb.getGroupId() > 0, "参数错误");
            checkArgument(groupUserRelationDb.getUserId() > 0, "参数错误");
            int num = groupUserRelationDao.deleteByGroupIdAndUserId(groupUserRelationDb.getGroupId(), groupUserRelationDb.getUserId());
            LogConstant.runLog.info("#GroupController.back#num={}", num);
            if (num <= 0) {
                return new JSONUtil().constructResponse(401, "操作失败", null);
            }

            GroupDb groupDb = groupDao.getById(groupUserRelationDb.getGroupId());
            if (groupDb != null) {
                Map<String, String> param = Maps.newHashMap();
                param.put("userId", String.valueOf(groupUserRelationDb.getUserId()));
                param.put("groupId", String.valueOf(groupUserRelationDb.getGroupId()));
                String resp = HttpUtil.postNoRetry(bakGroupUrl, param, 10000, 10000);
                LogConstant.runLog.info("#GroupController.add#back rong yun  resp= {}", resp);
                System.out.println("#GroupController.add#back rong yun  resp=" + resp);

                if (!Strings.isNullOrEmpty(resp)) {
                    JSONObject jsonObject = JSON.parseObject(resp);
                    if (jsonObject.getInteger("code") != 200) {
                        return new JSONUtil().constructResponse(401, "操作失败", null);
                    }
                }
            }
        } catch (IllegalArgumentException e2) {
            LogConstant.runLog.error("#UserController.register#sys error", e2);
            return new JSONUtil().constructResponse(401, e2.getMessage(), null);
        } catch (Exception e) {
            LogConstant.runLog.error("#GroupController.back#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        return new JSONUtil().constructResponse(200, "成功", null);
    }

    /**
     * 通过用户id获取群组列表以及群组中的成员列表
     * @param userId
     * @return
     */
//    @ResponseBody
//    @RequestMapping("/getUserGroupAndMems")
//    public Object getUserGroupAndMems(int userId) {
//        LogConstant.runLog.info("#GroupController.getUserGroupAndMems#userId={}", userId);
//        if(userId <= 0){
//            return new JSONUtil().constructResponse(401,"参数错误" , null);
//        }
//        List<GroupMem> groupMemList = null;
//        try{
//            List<GroupUserRelationDb> groupUserRelationDbList =  groupUserRelationDao.getByUserId(userId);
//            if(CollectionUtils.isEmpty(groupUserRelationDbList)){
//                return JSONUtil.constructResponse(200, "成功", groupMemList);
//            }
//            List<Integer> groupIdList = Lists.newArrayList();
//            for(GroupUserRelationDb groupUserRelationDb : groupUserRelationDbList){
//                groupIdList.add(groupUserRelationDb.getId());
//            }
//            = groupDao.getUserGroupAndMems(userId);
//        }
//
//    }







}
