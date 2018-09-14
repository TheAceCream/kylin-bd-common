package com.qf58.bd.kylin.connect;

import java.sql.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * Description: Kylin连接获取工具
 *
 * User: weicaijia
 * Date: 2018/9/14 16:47
 * Time: 14:15
 */
public class KylinConnection {

    private static String driverName;
    private static String url;
    private static String user;
    private static String password;

    private static final String PRO_FILE_LOCATION = "conf/kylin.properties";
    private static final String PRO_DRIVER = "kylin.driverName";
    private static final String PRO_URL = "kylin.url";
    private static final String PRO_USER = "kylin.user";
    private static final String PRO_PASSWORD = "kylin.password";



    public KylinConnection(String driverName, String url, String user, String password) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    static {
        try {
            PropertyLoader propertyLoader = new PropertyLoader(PRO_FILE_LOCATION);
            Properties properties = propertyLoader.getProps();
            driverName = (String) properties.get(PRO_DRIVER);
            url = (String) properties.get(PRO_URL);
            user = (String) properties.get(PRO_USER);
            password = (String) properties.get(PRO_PASSWORD);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 获取连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url,user,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 释放资源
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            resultSet = null;
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            statement = null;
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
