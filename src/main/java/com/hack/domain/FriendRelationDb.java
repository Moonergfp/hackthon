package com.hack.domain;

import lombok.Data;

@Data
public class FriendRelationDb {
    private int id;
    private int userId;     //被邀请者
    private int askedUserId; //邀请者
}
