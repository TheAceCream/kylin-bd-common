package com.qf58.bd.kylin.entities;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * Description:域实体
 *
 * User: weicaijia
 * Date: 2018/9/14 16:59
 * Time: 14:15
 */
public class FieldEntity implements Serializable {

    private static final long serialVersionUID = -789787117638114983L;

    /**
     * 列名
     */
    private String columnName;
    /**
     * 类型
     */
    private String classType;
    /**
     * 大小
     */
    private int columnSize;
    /**
     * 小数位数
     */
    private int decimal_digits;

    /**
     * 是否可为空
     */
    private int nullable;

    public FieldEntity() { }

    public FieldEntity(String columnName, String classType, int columnSize, int decimal_digits, int nullable) {
        this.columnName = columnName;
        this.classType = classType;
        this.columnSize = columnSize;
        this.decimal_digits = decimal_digits;
        this.nullable = nullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimal_digits() {
        return decimal_digits;
    }

    public void setDecimal_digits(int decimal_digits) {
        this.decimal_digits = decimal_digits;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }
}
