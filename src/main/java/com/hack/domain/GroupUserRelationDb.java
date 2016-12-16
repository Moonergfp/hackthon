package com.hack.domain;

import lombok.Data;

@Data
public class GroupUserRelationDb {
    private int id;
    private int groupId;
    private int userId;
}
