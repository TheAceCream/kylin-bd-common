package com.qf58.bd.kylin.test;

import com.qf58.bd.kylin.connect.KylinConnPool;
import com.qf58.bd.kylin.connect.PropertyLoader;
import com.qf58.bd.kylin.dto.QueryFiled;
import com.qf58.bd.kylin.dto.SelectField;
import com.qf58.bd.kylin.dto.TimeField;
import com.qf58.bd.kylin.entities.KylinQueryBaseResult;
import com.qf58.bd.kylin.enums.QueryTimeTypeEnum;
import com.qf58.bd.kylin.template.KylinBuildCubeTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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

        TimeField timeField = new TimeField();
        timeField.setTimeColumn("create_time");
//        timeField.setStartTime("2018-01-01");
//        timeField.setEndTime("2018-02-01");
        timeField.setQueryTimeTypeEnum(QueryTimeTypeEnum.DAY_TYPE);

        SelectField selectField = new SelectField();
        selectField.setColumn("count(1)");
        selectField.setName("name1");

        SelectField selectField2 = new SelectField();
        selectField2.setColumn("count(1)");
        selectField2.setName("name2");

        List selectList = new ArrayList();
        selectList.add(selectField);
        selectList.add(selectField2);



        QueryFiled queryFiled1 = new QueryFiled();
        queryFiled1.setColumn("source_terminal");
        queryFiled1.setValue(3);

        QueryFiled queryFiled2 = new QueryFiled();
        queryFiled2.setColumn("submit_type");
        queryFiled2.setValue(1);

        List queryList = new ArrayList();
        queryList.add(queryFiled1);
        queryList.add(queryFiled2);


//
        KylinConnPool kylinConnPool = new KylinConnPool(driverName,url,user,password);
        Connection connection = kylinConnPool.getConnection();


        String sql = KylinBuildCubeTemplate.queryStrBuild(null,null,true,"t_clue",timeField);
        List<KylinQueryBaseResult> kylinQueryBaseResults = KylinBuildCubeTemplate.queryKylinGroupSQL(connection,sql);
        System.out.println(kylinQueryBaseResults);



//        String sql2 = KylinBuildCubeTemplate.queryCountBuild(null,null,"t_clue",timeField);
//        List<Map<String,String>> kylinQuerySimpleResults = KylinBuildCubeTemplate.queryKylinSimpleSQL(connection,sql2);
//        System.out.println(kylinQuerySimpleResults);



    }


}
