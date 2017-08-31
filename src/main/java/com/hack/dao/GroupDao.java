package com.hack.dao;

import com.hack.cons.TableCons;
import com.hack.domain.GroupDb;
import com.hack.domain.UserDb;
import com.hack.vo.GroupMem;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

import static org.apache.ibatis.jdbc.SelectBuilder.*;


public interface GroupDao {

    String INSERT_FILEDS = "group_name,remark,des,group_pic";
    String SELECT_FIELDS = "id," + INSERT_FILEDS;
    String INSERT_VALUES = "#{groupName},#{remark},#{des},#{groupPic}";

    @Insert("insert into " + TableCons.GROUP_TABLE + "(" + INSERT_FILEDS + ") values (" + INSERT_VALUES + ")")
    @Options(useGeneratedKeys = true)
    int insert(GroupDb groupDb);

    @Select("select " + SELECT_FIELDS + " from " + TableCons.GROUP_TABLE + " where id=#{id}")
    GroupDb getById(int id);

    @SelectProvider(type = SqlProvider.class, method = "getByIds")
    List<GroupDb> getByIds(@Param("groupIdList") List<Integer> groupIdList);

//    @SelectProvider(type = SqlProvider.class, method = "getUserGroupAndMems")
//    List<GroupMem> getUserGroupAndMems(int userId);

    class SqlProvider {
        public String getByIds(Map<String,Object> param) {
            List<Integer> ids = ( List<Integer>) param.get("groupIdList");
            BEGIN();
            SELECT(SELECT_FIELDS);
            FROM(TableCons.GROUP_TABLE);
            WHERE("id in ("+ StringUtils.join(ids,",")+")");
            return SQL();
        }



    }

}
