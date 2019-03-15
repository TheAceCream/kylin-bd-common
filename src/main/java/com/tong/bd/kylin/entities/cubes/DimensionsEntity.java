package com.tong.bd.kylin.entities.cubes;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/18 10:42
 * Time: 14:15
 */
public class DimensionsEntity implements Serializable {

    private static final long serialVersionUID = 5476707371020546490L;

    /**
     * 维度名
     */
    private String name;

    /**
     * 所属Table
     */
    private String table;

    /**
     * 列名
     */
    private String column;

    /**
     * 衍生
     */
    private List<String> derived;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public List<String> getDerived() {
        return derived;
    }

    public void setDerived(List<String> derived) {
        this.derived = derived;
    }

    @Override
    public String toString() {
        return "DimensionsEntity{" +
                "name='" + name + '\'' +
                ", table='" + table + '\'' +
                ", column='" + column + '\'' +
                ", derived=" + derived +
                '}';
    }
}
