package com.hack.dao;

import com.hack.cons.TableCons;
import com.hack.domain.GroupDb;
import com.hack.domain.UserDb;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupDao {

    String INSERT_FILEDS = "group_name,remark";
    String SELECT_FIELDS="id," + INSERT_FILEDS;
    String INSERT_VALUES="#{groupName},#{remark}";

    @Insert("insert into " + TableCons.GROUP_TABLE  + "(" + INSERT_FILEDS + ") values (" + INSERT_VALUES + ")")
    @Options(useGeneratedKeys = true)
    int insert(GroupDb groupDb);

    @Select("select "+ SELECT_FIELDS+ " from "+TableCons.GROUP_TABLE+ " where id=#{id}")
    GroupDb getById(int id);

}
