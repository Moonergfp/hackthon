package com.hack.vo;

import com.hack.domain.UserDb;
import lombok.Data;

import java.util.List;

@Data
public class GroupMem{
    private int groupId;
    private String groupName;
    private String des;         //描述
    private String groupPic;    //组图片
    List<UserDb> userList;
}


