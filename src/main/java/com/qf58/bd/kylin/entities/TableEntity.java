package com.qf58.bd.kylin.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * Description: 表实体
 *
 * User: weicaijia
 * Date: 2018/9/14 17:11
 * Time: 14:15
 */
public class TableEntity implements Serializable {

    private static final long serialVersionUID = 8326244813281462261L;

    private String tableName;

    private List<FieldEntity> fields;

    public TableEntity() {
    }

    public TableEntity(String tableName, List<FieldEntity> fields) {
        this.tableName = tableName;
        this.fields = fields;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<FieldEntity> getFields() {
        return fields;
    }

    public void setFields(List<FieldEntity> fields) {
        this.fields = fields;
    }
}
