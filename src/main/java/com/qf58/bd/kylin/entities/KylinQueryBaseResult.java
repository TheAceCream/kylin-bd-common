package com.qf58.bd.kylin.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/19 11:23
 * Time: 14:15
 */
public class KylinQueryBaseResult implements Serializable {

    private static final long serialVersionUID = -861551789472201553L;

    /**
     * 时间
     */
    private String staticTime;

    /**
     * 默认度量
     */
    private String staticNum;

    /**
     * 查询条件
     */
    private List<Map<String,String>> conditionList;


    public String getStaticTime() {
        return staticTime;
    }

    public void setStaticTime(String staticTime) {
        this.staticTime = staticTime;
    }

    public String getStaticNum() {
        return staticNum;
    }

    public void setStaticNum(String staticNum) {
        this.staticNum = staticNum;
    }

    public List<Map<String, String>> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Map<String, String>> conditionList) {
        this.conditionList = conditionList;
    }

    @Override
    public String toString() {
        return "KylinQueryBaseResult{" +
                "staticTime='" + staticTime + '\'' +
                ", staticNum='" + staticNum + '\'' +
                ", conditionList=" + conditionList +
                '}';
    }
}