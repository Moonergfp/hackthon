package com.hack.domain;

import lombok.Data;

@Data
public class UserDb {
    private int id;
    private String acct;           //账号
    private String pwd;
    private String name;        //用户名
    private String headPic;     //用户头像
    private String token;       //token数据
}
