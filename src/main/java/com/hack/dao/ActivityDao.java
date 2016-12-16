package com.hack.dao;

import com.hack.cons.TableCons;
import com.hack.domain.ActivityDb;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActivityDao {

    String INSERT_FILEDS = "title,text,group_id_list";
    String SELECT_FIELDS="id," + INSERT_FILEDS;
    String INSERT_VALUES="#{title},#{text},#{groupIdList}";

    @Insert("insert into " + TableCons.ACTIVITY_TABLE + "(" + INSERT_FILEDS + ") values (" + INSERT_VALUES + ")")
    @Options(useGeneratedKeys = true)
    int insert(ActivityDb userDb);

    @Select("select "+ SELECT_FIELDS+ " from "+TableCons.ACTIVITY_TABLE+ " where id=#{id}")
    ActivityDb getById(int id);


    @Update("update " +TableCons.ACTIVITY_TABLE + " set group_id_list=#{groupIdList} where id =#{id}")
    int updateGroupList(@Param("id") int id,@Param("groupIdList") String groupIdList);

    @Select("select "+ SELECT_FIELDS+ " from "+TableCons.ACTIVITY_TABLE)
    List<ActivityDb> list();
}
