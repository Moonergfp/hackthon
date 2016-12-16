package com.hack.domain;

import lombok.Data;

@Data
public class GroupDb {
    private int id;
    private int ownerId;        //群主
    private String groupName;
    private String des;         //描述
    private String remark;
}
