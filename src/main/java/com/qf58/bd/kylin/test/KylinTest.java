package com.qf58.bd.kylin.test;

import com.qf58.bd.kylin.connect.KylinConnPool;
import com.qf58.bd.kylin.connect.PropertyLoader;
import com.qf58.bd.kylin.entities.KylinQueryBaseResult;
import com.qf58.bd.kylin.enums.QueryTimeTypeEnum;
import com.qf58.bd.kylin.template.KylinBuildCubeTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
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

        PropertyLoader propertyLoader = new PropertyLoader("conf/kylin.properties");
        Properties properties = propertyLoader.getProps();
        String driverName = (String) properties.get("kylin.driverName");
        String url = (String) properties.get("kylin.url");
        String user = (String) properties.get("kylin.user");
        String password = (String) properties.get("kylin.password");

        KylinConnPool kylinConnPool = new KylinConnPool(driverName,url,user,password);
        Connection connection = kylinConnPool.getConnection();


        String sql = KylinBuildCubeTemplate.queryStrBulid(null,null,true,"t_clue","create_time","2018-01-01","2018-02-01",QueryTimeTypeEnum.DAY_TYPE);
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        List<KylinQueryBaseResult> kylinQueryBaseResults = KylinBuildCubeTemplate.queryKylinSQL(connection,ps,resultSet,sql);
        System.out.println(kylinQueryBaseResults);



    }


}
