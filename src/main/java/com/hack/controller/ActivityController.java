package com.hack.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Interner;
import com.google.common.collect.Lists;
import com.hack.dao.ActivityDao;
import com.hack.dao.GroupDao;
import com.hack.domain.ActivityDb;
import com.hack.domain.GroupDb;
import com.hack.util.JSONUtil;
import com.hack.util.LogConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 活动管理
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityDao activityDao;
    @Resource
    private GroupDao groupDao;

    @ResponseBody
    @RequestMapping("/create")
    public Object create(ActivityDb activityDb) {
        LogConstant.runLog.info("#ActivityController.activityDb#activityDb={}", activityDb);
        boolean succeed = false;
        try {
            checkArgument(!Strings.isNullOrEmpty(activityDb.getTitle()), "title为空");
            checkArgument(!Strings.isNullOrEmpty(activityDb.getText()), "活动描述为空");
            succeed = activityDao.insert(activityDb) > 0 ? true : false;
            LogConstant.runLog.info("#ActivityController.create#create succeed = {}", succeed);
        } catch (Exception e) {
            e.printStackTrace();
            LogConstant.runLog.error("#ActivityController.create#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        if (succeed) {
            return new JSONUtil().constructResponse(200, "成功", activityDb.getId());
        } else {
            return new JSONUtil().constructResponse(401, "失败", activityDb.getId());
        }
    }


    /**
     * 查询所有活动
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        LogConstant.runLog.info("#ActivityController.activityDb#list");
        List<ActivityDb> activityDbs = null;
        try {
            activityDbs = activityDao.list();
            LogConstant.runLog.info("#ActivityController.create#activityDbs={}", activityDbs);
            if (!org.springframework.util.CollectionUtils.isEmpty(activityDbs)) {
                //查询活动群组列表
                for (ActivityDb aivicttyDb : activityDbs) {
                    String groupIds = aivicttyDb.getGroupIdList();
                    if (Strings.isNullOrEmpty(groupIds)) {
                        continue;
                    }
                    List<Integer> groupIdList = Lists.transform(Lists.newArrayList(Splitter.on(",").split(groupIds)), new Function<String, Integer>() {
                        @Override
                        public Integer apply(String input) {
                            if(input == null || "null".equals(input) || "NULL".equals(input)){
                                return 0;
                            }
                            return Integer.valueOf(input);
                        }
                    });
                    List<GroupDb> groupDbs = groupDao.getByIds(groupIdList);
                    aivicttyDb.setGroupList(groupDbs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogConstant.runLog.error("#ActivityController.create#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        return new JSONUtil().constructResponse(200, "成功", activityDbs);
    }


    @ResponseBody
    @RequestMapping("/getById")
    public Object getById(int id) {
        LogConstant.runLog.info("#ActivityController.getById#id={}", id);
        ActivityDb activityDb = null;
        try {
            activityDb = activityDao.getById(id);
            LogConstant.runLog.info("#ActivityController.getById#activityDb={}", activityDb);
            if(activityDb == null){
                return new JSONUtil().constructResponse(401, "活动不存在", activityDb);
            }
            String groupIds = activityDb.getGroupIdList();
            if (!Strings.isNullOrEmpty(groupIds)) {
                List<Integer> groupIdList = Lists.transform(Lists.newArrayList(Splitter.on(",").split(groupIds)), new Function<String, Integer>() {
                    @Override
                    public Integer apply(String input) {
                        if(input == null || "null".equals(input) || "NULL".equals(input)){
                            return 0;
                        }
                        return Integer.valueOf(input);
                    }
                });
                List<GroupDb> groupDbs = groupDao.getByIds(groupIdList);
                activityDb.setGroupList(groupDbs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogConstant.runLog.error("#ActivityController.create#sys error", e);
            return new JSONUtil().constructResponse(500, "系统错误", null);
        }
        return new JSONUtil().constructResponse(200, "成功", activityDb);

    }
}
