package com.hack.dao;

import com.hack.cons.TableCons;
import com.hack.domain.FriendRelationDb;
import com.hack.domain.UserDb;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.apache.ibatis.jdbc.SqlBuilder.*;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public interface FriendRelationDao {

    String INSERT_FILEDS = "user_id,asked_user_id";
    String SELECT_FIELDS="id," + INSERT_FILEDS;
    String INSERT_VALUES="#{userId},#{askedUserId}";

    @Insert("insert into " + TableCons.FRIEND_RELATION_TABLE+ "(" + INSERT_FILEDS + ") values (" + INSERT_VALUES + ")")
    @Options(useGeneratedKeys = true)
    int insert(FriendRelationDb friendRelationDb);

    @SelectProvider(type=Provider.class,method="getFriendList")
    List<UserDb> getFriendList(int userId);

    class Provider{
        public String getFriendList(int userId){
            BEGIN();
            SELECT("u.id,acct,name,head_pic");
            FROM(TableCons.FRIEND_RELATION_TABLE +" f");
            LEFT_OUTER_JOIN(TableCons.USER_TABLE +" u on f.user_id=u.id");
            WHERE("f.asked_user_id=#{userId}");

            return SQL();
        }

    }


}
