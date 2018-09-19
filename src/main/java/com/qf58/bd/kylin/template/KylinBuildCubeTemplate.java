package com.qf58.bd.kylin.template;

import com.qf58.bd.kylin.connect.KylinHelper;
import com.qf58.bd.kylin.dto.QueryFiled;
import com.qf58.bd.kylin.dto.SelectField;
import com.qf58.bd.kylin.entities.KylinQueryBaseResult;
import com.qf58.bd.kylin.enums.QueryTimeTypeEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/17 16:36
 * Time: 14:15
 */
public class KylinBuildCubeTemplate {


    /**
     * 拼成常用的查询模板
     * @param selectFields 待查询的度量以及想获取的名称 (可为空)
     * @param queryFields 额外的查询条件,可以填写成维度 (可为空)
     * @param sequence 是否正序 (可为空)
     * @param tableName 表名 (必填)
     * @param timeField 表内时间列名 (必填)
     * @param startTime 查询开始时间 (可为空)
     * @param endTime 查询结束时间 (可为空)
     * @param queryTimeTypeEnum 整合类型(日、月、年)
     * @return
     *
     * 默认按时间正序排列
     * 默认查询时间段是一年以内
     * 默认整合类型为 日期
     *
     */
    public static String queryStrBulid(List<SelectField> selectFields, List<QueryFiled> queryFields, Boolean sequence, String tableName, String timeField, String startTime, String endTime, QueryTimeTypeEnum queryTimeTypeEnum){
        if (sequence == null){
            sequence = true;
        }
        if (tableName==null || tableName==""){
            return null;
        }
        if (timeField==null || timeField==""){
            return null;
        }
        if (queryTimeTypeEnum==null){
            queryTimeTypeEnum = QueryTimeTypeEnum.DAY_TYPE;
        }
        StringBuffer sb = new StringBuffer();
        String sqlStage1 = "select substring("+ tableName +"."+timeField+",1,"+ queryTimeTypeEnum.getDesc() +") as static_time,count(1) as static_num ";
        sb.append(sqlStage1);
        if (selectFields!=null){
            if (selectFields.size()!=0){
                for (SelectField selectField : selectFields) {
                    sb.append(","+selectField.getColumn()+" as "+selectField.getName());
                }
            }
        }
        String sqlStage2 = " from " + tableName;
        sb.append(sqlStage2);
        //时间空处理
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (startTime==null||startTime==""){
            startTime = sdf.format(new Date());
        }
        if (endTime==null||endTime==""){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.YEAR,-1);
            endTime = sdf.format(calendar.getTime());
        }
        //时间切割处理
        if (queryTimeTypeEnum==QueryTimeTypeEnum.MONTH_TYPE){
            startTime = startTime.substring(0,7);
            endTime = endTime.substring(0,7);
        }else if (queryTimeTypeEnum==QueryTimeTypeEnum.YEAR_TYPE){
            startTime = startTime.substring(0,4);
            endTime = endTime.substring(0,4);
        }
        String sqlStage3 = " where substring("+tableName+"."+timeField + ",1,"+ queryTimeTypeEnum.getDesc() +") between '"+ startTime+"' and '"+ endTime+"'";
        sb.append(sqlStage3);

        //添加筛选条件
        String sqlStage4 = fillQueryCondition(queryFields);
        sb.append(sqlStage4);

        String sqlStage5 = " group by substring("+ tableName +"."+timeField+",1,"+ queryTimeTypeEnum.getDesc() +") order by substring("+ tableName +"."+timeField+",1,"+ queryTimeTypeEnum.getDesc() +")";
        sb.append(sqlStage5);
        if (sequence){
            sb.append(" desc");
        }
        return sb.toString();
    }

    /**
     * 补充where后面自定义维度查询
     * @param queryFiledList 待查维度list
     * @return
     */
    private static String fillQueryCondition(List<QueryFiled> queryFiledList){
        String newSql = "";
        if (queryFiledList!=null){
            if (queryFiledList.size()!=0){
                for (QueryFiled queryField : queryFiledList) {
                    newSql = newSql + " and " + queryField.getColumn()+ " = " + queryField.getValue() + " ";
                }
            }
        }
        return newSql;
    }


    /**
     * @param sql 查询sql语句
     * @return 成功返回ResultSet
     */
    public static List<KylinQueryBaseResult> queryKylinSQL(Connection conn, PreparedStatement ps, ResultSet rs, String sql) {
        List<KylinQueryBaseResult> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                KylinQueryBaseResult kylinQueryBaseResult = new KylinQueryBaseResult();
                String staticTime = rs.getString("static_time");
                String staticNum =  rs.getString("static_num");
                kylinQueryBaseResult.setStaticTime(staticTime);
                kylinQueryBaseResult.setStaticNum(staticNum);

                List<Map<String,String>> columnsList = new ArrayList<>();
                String result;
                result = sql.split("from")[0];
                result = result.substring(81,result.length());
                if (result!=null && !"".equals(result)){
                    String[] strings = result.split(",");
                    for (String temp : strings) {
                        String nameTemp = temp.split("as")[1].trim();
                        String valueTemp = rs.getString(nameTemp);
                        Map<String,String> map = new HashMap<>();
                        map.put("name",nameTemp);
                        map.put("value",valueTemp);
                        columnsList.add(map);
                    }
                }

                kylinQueryBaseResult.setConditionList(columnsList);
                list.add(kylinQueryBaseResult);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            KylinHelper.close(conn,ps,rs);
        }

        return list;
    }




    public static void main(String[] args) {


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

        List list = new ArrayList();
        list.add(queryFiled1);
        list.add(queryFiled2);


        String s1 = queryStrBulid(selectList,list,true,"t_clue","create_time","2018-01-01","2018-02-01",QueryTimeTypeEnum.DAY_TYPE);




        System.out.println(s1);

    }

}
