package com.tong.bd.kylin.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/19 14:31
 * Time: 14:15
 */
public class QueryFiled implements Serializable {

    private static final long serialVersionUID = 7527824430148935246L;

    /**
     * 列名
     */
    private String column;

    /**
     * 查询值
     */
    private Object value;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QueryFiled{" +
                "column='" + column + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
