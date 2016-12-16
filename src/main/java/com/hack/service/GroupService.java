package com.hack.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hack.conf.PropertyUtil;
import com.hack.dao.ActivityDao;
import com.hack.dao.GroupDao;
import com.hack.dao.GroupUserRelationDao;
import com.hack.domain.GroupDb;
import com.hack.exception.HackException;
import com.hack.util.HttpUtil;
import com.hack.util.LogConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class GroupService {

    @Resource
    private GroupDao groupDao;
    @Resource
    private ActivityDao activityDao;

    private static final String createGroupUrl = PropertyUtil.getProperty("createGroupUrl");
    private List<String> groupPicUrls = Lists.newArrayList(
            "http://p1.meituan.net/440.320/sjstpic/6a3d722ee9c17539010b15b6980a97f776564.jpg",
            "http://p0.meituan.net/440.320/sjstpic/c3b3ff72da77aee63b77556ad9a21254425323.jpg",
            "http://p1.meituan.net/kuailv/15efe176736b7ce2bad1a387458119b3121461.png",
            "http://p1.meituan.net/kuailv/6901d0fb4a8cc827ac431eb672b56f969941.jpeg",
            "http://p0.meituan.net/kuailv/ddd08b157c7eda797cbea093a1d1fe2110190.jpg",
            "http://p0.meituan.net/kuailv/5c5dd95cd0bfa29ac7d602244843d63616100.jpg",
            "http://p0.meituan.net/kuailv/70b2c1ab6281cc1605667cb1f28896219493.png");

    @Transactional(rollbackFor = Exception.class)
    public List<Integer> batchCreateDb(int activityId, List<GroupDb> groupDbList) throws IOException {
        List<Integer> groupIdList = Lists.newArrayList();
        for (GroupDb groupDb : groupDbList) {
            checkArgument(!Strings.isNullOrEmpty(groupDb.getGroupName()),"组名为空");
            groupDb.setGroupPic(groupPicUrls.get(new Random().nextInt(groupPicUrls.size())));
            boolean createGroupSucceed = groupDao.insert(groupDb) > 0 ? true : false;
            groupIdList.add(groupDb.getId());
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
                    if (jsonObject.getInteger("code") != 200) {
                        throw new RuntimeException("创建分组失败RY");
                    }
                }
            }else{
                throw new RuntimeException("创建分组失败");
            }
        }
        int num = activityDao.updateGroupList(activityId, StringUtils.join(groupIdList, ","));
        LogConstant.runLog.error("#GroupController.batchCreate#updateGroupList num={}", num);
        if (num <= 0) {
            throw new HackException("批量保存组列表失败");
        }
        return groupIdList;
    }

}
