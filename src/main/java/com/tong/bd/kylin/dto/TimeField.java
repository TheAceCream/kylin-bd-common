package com.tong.bd.kylin.dto;

import com.tong.bd.kylin.enums.QueryTimeTypeEnum;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/19 16:07
 * Time: 14:15
 */
public class TimeField implements Serializable {

    private static final long serialVersionUID = -7574277579124863593L;
    /**
     * 表中时间的列名
     */
    private String timeColumn;

    /**
     * 查询开始时间
     */
    private String startTime;

    /**
     * 查询结束时间
     */
    private String endTime;

    /**
     * 时间单位
     */
    private QueryTimeTypeEnum queryTimeTypeEnum;

    public String getTimeColumn() {
        return timeColumn;
    }

    public void setTimeColumn(String timeColumn) {
        this.timeColumn = timeColumn;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public QueryTimeTypeEnum getQueryTimeTypeEnum() {
        return queryTimeTypeEnum;
    }

    public void setQueryTimeTypeEnum(QueryTimeTypeEnum queryTimeTypeEnum) {
        this.queryTimeTypeEnum = queryTimeTypeEnum;
    }
}
