package com.hack.vo;

import com.hack.domain.GroupDb;
import lombok.Data;

import java.util.List;

@Data
public class GroupBatch {
    private int activityId;
    private List<GroupDb> groupList;
}
