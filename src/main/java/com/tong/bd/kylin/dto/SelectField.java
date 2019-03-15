package com.tong.bd.kylin.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/19 10:17
 * Time: 14:15
 */
public class SelectField implements Serializable {

    private static final long serialVersionUID = -488686138320961323L;

    /**
     * 列中的名称
     */
    private String column;

    /**
     * 获取时的名称
     */
    private String name;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SelectField{" +
                "column='" + column + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
