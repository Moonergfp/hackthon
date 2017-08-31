package com.hack.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.print.DocFlavor;
import java.util.List;

/**
 * 活动
 */
@Data
public class ActivityDb {
    private int id;
    private String title; // 账号
    private String text;
    private String groupIdList; // 用户名
    private List<GroupDb> groupList;
}
