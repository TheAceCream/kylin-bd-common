package com.qf58.bd.kylin.test;

import com.qf58.bd.kylin.connect.KylinConnPool;
import com.qf58.bd.kylin.connect.KylinConnection;

import java.sql.*;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/14 16:12
 * Time: 14:15
 */
public class KylinTest {


    public static void main(String[] args) throws Exception {
        //加载JDBC
//        Driver driver = (Driver)Class.forName("org.apache.kylin.jdbc.Driver").newInstance();
//
//        //配置用户名和密码
//        Properties info = new Properties();
//        info.put("user","ADMIN");
//        info.put("password","KYLIN");
//
//        //连接服务
//        Connection conn = driver.connect("jdbc:kylin://172.16.11.119:7070/test",info);
//        Statement state = conn.createStatement();
//        ResultSet resultSet = state.executeQuery("select count(1) as static_num from t_clue where T_CLUE.SOURCE_TERMINAL=3 " +
//                "and T_CLUE.INPUT_TYPE=2 and T_CLUE.CREATE_TIME between '2018-01-01' and '2018-06-01'");
//
//        System.out.print("static_num:\t");
//
//        while (resultSet.next()){
//            String col1 = resultSet.getString(1);
//            System.out.println(col1);
//        }

        Connection conn = null;
        Statement state = null;
        ResultSet resultSet = null;
        KylinConnPool kylinConnPool = new KylinConnPool();
        try {
            // 2.从池子中获取连接
            conn = kylinConnPool.getConnection();
            String sql = "select count(1) as static_num from t_clue where T_CLUE.SOURCE_TERMINAL=3 and T_CLUE.INPUT_TYPE=2 and T_CLUE.CREATE_TIME between '2018-01-01' and '2018-06-01'";
            state = conn.createStatement();
            resultSet = state.executeQuery(sql);
            System.out.print("static_num:\t");
            while (resultSet.next()){
            String col1 = resultSet.getString(1);
            System.out.println(col1);
        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            KylinConnection.close(conn,state,resultSet);
        }


    }


}
