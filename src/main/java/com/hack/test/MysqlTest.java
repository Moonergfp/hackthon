package com.hack.test;

import lombok.Data;
import org.junit.Test;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlTest {
    @Test
    public void testInsert(){
        Person p = new Person();
        p.setName("hello");
        int id =insertPerson(p);
        System.out.println( "id===>" + id);

    }

    public static Person getPersonById(int id) {//通过id查找信息
        String sql = "select from person where id=" + id;
        PreparedStatement ps = OpMysql.selectMysql(sql);
        Person p = null;
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p = new Person();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setAge(rs.getInt(3));
                p.setSex(rs.getString(4));
                p.setPhoneNumber(rs.getString(5));
                p.setQqNumber(rs.getString(6));
                p.setEmail(rs.getString(7));
                p.setPassword(rs.getString(8));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    public static boolean deletePersonById(int id) {
        String sqlS = "delete from teacher where id=" + id;
        PreparedStatement ps = OpMysql.insertMysql(sqlS);
        try {
            ps.execute();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    public static int insertPerson(Person person) {
        String sql = "insert into person (`name`,`age`,`sex`,`phonenumber`,`qq`,`email`,`password`)values(?,?,?,?,?,?,?)";
        PreparedStatement ps = OpMysql.insertMysql(sql, Statement.RETURN_GENERATED_KEYS);
        //PreparedStatement ps = OpMysql.insertMysql(sql);
        Integer id  = null;
        try {
            ps.setString(1, person.getName());
            ps.setInt(2, person.getAge());
            ps.setString(3, person.getSex());
            ps.setString(4, person.getPhoneNumber());
            ps.setString(5, person.getQqNumber());
            ps.setString(6, person.getEmail());
            ps.setString(7, person.getPassword());
            //ps.executeUpdate();
            //if(rs.next()){
            //	id=rs.getInt(1);
            //}
           int num =  ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id  = rs.getInt(1);
            }
            //}
            //ps.execute();
            //PreparedStatement pstat=OpMysql.selectMysql("SELECT LAST_INSERT_ID()");
            //ResultSet rs=pstat.executeQuery();
            //while(rs.next()){
            //i//d=rs.getInt(1);
            //}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}

class OpMysql {//操作数据库的类

    public static PreparedStatement selectMysql(String sql) {//查询语句，返回查询结果
        PreparedStatement ps = null;
        try {
            ps = ConnMysql.getConnMysql().prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ps;
    }

    public static PreparedStatement insertMysql(String sql) {//插入语句，返回是否插入
        PreparedStatement ps = null;
        try {
            ps = ConnMysql.getConnMysql().prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ps;
    }

    public static PreparedStatement deleteMysql(String sql) {//删除语句，返回是否删除
        PreparedStatement ps = null;
        try {
            ps = ConnMysql.getConnMysql().prepareStatement(sql);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ps;
    }

    public static PreparedStatement updateMysql(String sql) {//更新语句，返回是否更新
        PreparedStatement ps = null;
        try {
            ps = ConnMysql.getConnMysql().prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ps;
    }

    public static PreparedStatement insertMysql(String sql,
                                                int returnGeneratedKeys) {
        PreparedStatement ps = null;
        try {
            ps = ConnMysql.getConnMysql().prepareStatement(sql,returnGeneratedKeys);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ps;
    }
}

@Data
class Person {
    private int id;
    private String name;
    private int age;
    private String sex;
    private String phoneNumber;
    private String qqNumber;
    private String email;
    private String password;

    public Person() {
        super();
        // TODO Auto-generated constructor stub
    }


    public Person(int id, String name, int age, String sex,
                  String phoneNumber, String qqNumber, String email, String password) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.qqNumber = qqNumber;
        this.email = email;
        this.password = password;
    }
}

class ConnMysql {
    public static Connection getConnMysql() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8", "root", "123456");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }

    public static void closeConnMysql(ResultSet rs, PreparedStatement ps, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}