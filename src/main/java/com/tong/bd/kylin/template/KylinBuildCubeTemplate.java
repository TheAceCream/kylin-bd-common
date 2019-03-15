package com.tong.bd.kylin.template;

import com.tong.bd.kylin.connect.KylinHelper;
import com.tong.bd.kylin.dto.QueryFiled;
import com.tong.bd.kylin.dto.SelectField;
import com.tong.bd.kylin.dto.TimeField;
import com.tong.bd.kylin.entities.KylinQueryBaseResult;
import com.tong.bd.kylin.enums.QueryTimeTypeEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class KylinBuildCubeTemplate {

    /**
     * 查询数据统计
     * @param selectFields 待查询的度量以及想获取的名称 (可为空)
     * @param queryFields 额外的查询条件,可以填写成维度 (可为空)
     * @param timeField 时间相关 (必填)
     * timeColumn 时间列名(必填)
     * startTime 查询开始时间 (可为空)
     * endTime 查询结束时间 (可为空)
     * @return
     */
    public static String queryCountBuild(List<SelectField> selectFields,List<QueryFiled> queryFields,String tableName,TimeField timeField){
        if (timeField==null){
            return null;
        }
        String timeColumn = timeField.getTimeColumn();
        if (timeColumn==null){
            return null;
        }
        String startTime = timeField.getStartTime();
        String endTime = timeField.getEndTime();

        StringBuffer sb = new StringBuffer();
        String sqlStage1 = "select count(1) as static_num ";
        sb.append(sqlStage1);
        if (selectFields!=null){
            if (selectFields.size()!=0){
                for (SelectField selectField : selectFields) {
                    sb.append(","+selectField.getColumn()+" as "+selectField.getName());
                }
            }
        }
        String sqlStage2 = "from " + tableName + " where 1=1 ";
        sb.append(sqlStage2);
        //如果不设置开始和结束时间 则查所有
        if (startTime != null && !"".equals(startTime)){
            sb.append("and " + tableName + "." + timeColumn + " >= '"+startTime+"' ");
        }
        if (endTime != null && !"".equals(endTime)){
            sb.append("and " + tableName + "."+ timeColumn + " < '"+endTime+"' ");
        }
        //添加筛选条件
        String sqlStage4 = fillQueryCondition(queryFields);
        sb.append(sqlStage4);
        return sb.toString();
    }


    /**
     * 拼成常用的查询模板
     * @param selectFields 待查询的度量以及想获取的名称 (可为空)
     * @param queryFields 额外的查询条件,可以填写成维度 (可为空)
     * @param sequence 是否正序 (可为空)
     * @param timeField 时间相关 (必填)
     *  timeColumn 时间列名 (必填)
     *  startTime 查询开始时间 (可为空)
     *  endTime 查询结束时间 (可为空)
     *  queryTimeTypeEnum 整合类型(日、月、年)
     * @return
     *
     * 默认按时间正序排列
     * 默认查询时间段是一年以内
     * 默认整合类型为 日期
     *
     */
    public static String queryStrBuild(List<SelectField> selectFields, List<QueryFiled> queryFields, Boolean sequence, String tableName, TimeField timeField){
        if (sequence == null){
            sequence = true;
        }
        if (tableName==null || tableName==""){
            return null;
        }
        if (timeField==null){
            return null;
        }
        if (timeField.getQueryTimeTypeEnum()==null){
            timeField.setQueryTimeTypeEnum(QueryTimeTypeEnum.DAY_TYPE);
        }

        String timeColumn = timeField.getTimeColumn();
        if (timeColumn==null){
            return null;
        }
        String startTime = timeField.getStartTime();
        String endTime = timeField.getEndTime();
        QueryTimeTypeEnum queryTimeTypeEnum = timeField.getQueryTimeTypeEnum();

        StringBuffer sb = new StringBuffer();
        String sqlStage1 = "select substring("+ tableName +"."+ timeColumn +",1,"+ queryTimeTypeEnum.getDesc() +") as static_time,count(1) as static_num ";
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
        if (startTime==null|| "".equals(startTime)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.YEAR,-1);
            startTime = sdf.format(calendar.getTime());
        }
        if (endTime==null || "".equals(endTime)){
            endTime = sdf.format(new Date());
        }
        //时间切割处理
        if (queryTimeTypeEnum == QueryTimeTypeEnum.MONTH_TYPE){
            startTime = startTime.substring(0,7);
            endTime = endTime.substring(0,7);
        }else if (timeField.getQueryTimeTypeEnum()==QueryTimeTypeEnum.YEAR_TYPE){
            startTime = startTime.substring(0,4);
            endTime = endTime.substring(0,4);
        }
        String sqlStage3 = " where substring("+tableName+"."+ timeColumn + ",1,"+ queryTimeTypeEnum.getDesc() +") between '"+ startTime +"' and '"+ endTime +"'";
        sb.append(sqlStage3);

        //添加筛选条件
        String sqlStage4 = fillQueryCondition(queryFields);
        sb.append(sqlStage4);

        String sqlStage5 = " group by substring("+ tableName +"."+ timeColumn +",1,"+  queryTimeTypeEnum.getDesc() +") order by substring("+ tableName +"."+ timeColumn +",1,"+ queryTimeTypeEnum.getDesc() +")";
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
     * 返回的是时间加上时间对应的度量
     * @param sql 查询sql语句
     * @return 成功返回ResultSet
     */
    public static List<KylinQueryBaseResult> queryKylinGroupSQL(Connection conn, String sql) {
        List<KylinQueryBaseResult> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
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
                columnsList = fillMapList(result,rs);
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

    public static List<Map<String,String>> queryKylinSimpleSQL(Connection conn, String sql){
        List<Map<String,String>> columnsList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String result;
                result = sql.split("from")[0];
                result = result.substring(7, result.length());
                columnsList = fillMapList(result, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            KylinHelper.close(conn,ps,rs);
        }
        return columnsList;
    }

    /**
     * 填充一下List<Map>结构
     * @param result
     * @param rs
     * @return
     * @throws SQLException
     */
    private static List fillMapList(String result,ResultSet rs) throws SQLException {
        List<Map<String,String>> list = new ArrayList<>();
        if (result!=null && !"".equals(result)){
            String[] strings = result.split(",");
            for (String temp : strings) {
                String nameTemp = temp.split("as")[1].trim();
                String valueTemp = rs.getString(nameTemp);
                Map<String,String> map = new HashMap<>();
                map.put("name",nameTemp);
                map.put("value",valueTemp);
                list.add(map);
            }
        }
        return list;
    }





}


