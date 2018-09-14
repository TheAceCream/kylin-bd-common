package com.qf58.bd.kylin;


import com.qf58.bd.kylin.connect.KylinConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/14 16:12
 * Time: 14:15
 */
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        logger.info("--------info--logger------------");
        logger.error("-------error---logger------------");
        logger.debug("-------debug---logger------------");
        System.out.println("Hello World!");
        Connection kylinConnection = new KylinConnection("org.apache.kylin.jdbc.Driver", "jdbc:kylin://172.16.11.119:7070/test", "ADMIN", "KYLIN").getConnection();
        Statement statement = kylinConnection.createStatement();



    }



}
