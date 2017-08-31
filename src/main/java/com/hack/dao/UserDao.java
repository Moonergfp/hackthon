package com.hack.dao;

import com.hack.cons.TableCons;
import com.hack.domain.UserDb;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


public interface UserDao {

    String INSERT_FILEDS = "acct,name,head_pic,pwd,token";
    String SELECT_FIELDS="id," + INSERT_FILEDS;
    String INSERT_VALUES="#{acct},#{name},#{headPic},#{pwd},#{token}";

    @Insert("insert into " + TableCons.USER_TABLE + "(" + INSERT_FILEDS + ") values (" + INSERT_VALUES + ")")
    @Options(useGeneratedKeys = true)
    int insert(UserDb userDb);

    @Select("select "+ SELECT_FIELDS+ " from "+TableCons.USER_TABLE+ " where id=#{id}")
    UserDb getById(int id);

    @Select("select "+ SELECT_FIELDS+ " from "+TableCons.USER_TABLE+ " where acct=#{acct} and pwd=#{pwd}")
    UserDb getByAcctAndPwd(@Param("acct") String acct,@Param("pwd") String pwd);


    @Update("update " +TableCons.USER_TABLE+" set token=#{token} where id=#{id} " )
    int updateToken(@Param("id") int id,@Param("token") String token);
}
