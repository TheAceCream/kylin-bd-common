package com.tong.bd.kylin.connect;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/18 17:50
 * Time: 14:15
 */
public class KylinHelper {


    public static ResultSet executeQuery(Connection connection, String sql, Object... params) throws SQLException {
        if (StringUtils.isBlank(sql)) {
            System.out.println("sql语句不为空");
            return null;
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        System.out.println("sql--> " + sql);
        try {
            ps = connection.prepareStatement(sql);
            if(null != params) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i+1, params[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    public static void close(Connection connection,Statement statement,ResultSet resultSet) {
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
