package com.qf58.bd.kylin.entities.cubes;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Cube的配置详情
 * User: weicaijia
 * Date: 2018/9/18 11:33
 * Time: 14:15
 */
public class CubeDescEntity implements Serializable {

    private static final long serialVersionUID = -1707136017330008116L;

    /**
     * UUID
     */
    private String uuid;

    /**
     * cube名称
     */
    private String name;

    /**
     * model名称
     */
    private String modelName;

    /**
     * 描述
     */
    private String description;

    /**
     * 最后修改时间
     * 单位：毫秒ms
     */
    private Long lastModified;

    /**
     * 最后修改时间
     * 字符显示
     */
    private String lastModifiedStr;

    /**
     * 维度
     */
    List<DimensionsEntity> dimensions;

    /**
     * 度量
     */
    List<MeasuresEntity> measures;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DimensionsEntity> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<DimensionsEntity> dimensions) {
        this.dimensions = dimensions;
    }

    public List<MeasuresEntity> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasuresEntity> measures) {
        this.measures = measures;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedStr() {
        return lastModifiedStr;
    }

    public void setLastModifiedStr(String lastModifiedStr) {
        this.lastModifiedStr = lastModifiedStr;
    }

    @Override
    public String toString() {
        return "CubeDescEntity{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", modelName='" + modelName + '\'' +
                ", description='" + description + '\'' +
                ", lastModified=" + lastModified +
                ", lastModifiedStr='" + lastModifiedStr + '\'' +
                ", dimensions=" + dimensions +
                ", measures=" + measures +
                '}';
    }
}
