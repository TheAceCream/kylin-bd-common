package com.tong.bd.kylin.connect;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/18 17:34
 * Time: 14:15
 */
public class KylinConnPool {

    private static String driverName;
    private static String url;
    private static String user;
    private static String password;


    public KylinConnPool(String driverName, String url, String user, String password) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
    }


    private static final KylinConnPool instance = new KylinConnPool(driverName,url,user,password);

    private static ComboPooledDataSource comboPooledDataSource;

//    static {
//        try {
//            try {
//                Class.forName(driverName);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            comboPooledDataSource = new ComboPooledDataSource();
//            comboPooledDataSource.setDriverClass(driverName);
//            comboPooledDataSource.setJdbcUrl(url);
//            comboPooledDataSource.setUser(user);
//            comboPooledDataSource.setPassword(password);
//            // 下面是设置连接池的一配置
//            comboPooledDataSource.setMaxPoolSize(20);
//            comboPooledDataSource.setMinPoolSize(5);
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//        }
//    }

    public synchronized Connection getConnection() throws PropertyVetoException {
        Connection connection = null;
        try {
            try {
                Class.forName(driverName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass(driverName);
            comboPooledDataSource.setJdbcUrl(url);
            comboPooledDataSource.setUser(user);
            comboPooledDataSource.setPassword(password);
            // 下面是设置连接池的一配置
            comboPooledDataSource.setMaxPoolSize(20);
            comboPooledDataSource.setMinPoolSize(5);
            connection = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static KylinConnPool getInstance() {
        return instance;
    }



}
