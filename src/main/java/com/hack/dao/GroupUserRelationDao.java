package com.hack.dao;

import com.hack.cons.TableCons;
import com.hack.domain.GroupUserRelationDb;
import com.hack.domain.UserDb;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


@Repository
public interface GroupUserRelationDao {

    String INSERT_FILEDS = "group_id,user_id";
    String SELECT_FIELDS = "id," + INSERT_FILEDS;
    String INSERT_VALUES = "#{groupId},#{userId}";

    @Insert("insert into " + TableCons.GROUP_RELATION_TABLE + "(" + INSERT_FILEDS + ") values (" + INSERT_VALUES + ")")
    @Options(useGeneratedKeys = true)
    int insert(GroupUserRelationDb groupDb);

    @SelectProvider(type = Provider.class, method = "getUsersByGroupId")
    List<UserDb> getUsersByGroupId(int groupId);

    @Delete("delete from " + TableCons.GROUP_RELATION_TABLE + " where group_id=#{groupId} and user_id=#{userId}")
    int deleteByGroupIdAndUserId(@Param("groupId") int groupId, @Param("userId") int userId);


    class Provider {
        public String getUsersByGroupId(int groupId) {
            BEGIN();
            SELECT("u.id,acct,name,head_pic");
            FROM(TableCons.GROUP_RELATION_TABLE + " gr");
            LEFT_OUTER_JOIN(TableCons.USER_TABLE + " u on gr.user_id=u.id");
            WHERE("gr.group_id=#{groupId}");
            return SQL();
        }

    }

}
