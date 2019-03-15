package com.qf58.bd.kylin.entities.cubes;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 主要存的是Cube的构建后的信息
 * User: weicaijia
 * Date: 2018/9/18 10:15
 * Time: 14:15
 */
public class CubeEntity implements Serializable {

    private static final long serialVersionUID = 3632187821663867025L;

    /**
     * UUID
     */
    private String uuid;

    /**
     * cube名称
     */
    private String name;

    /**
     * 拥有者
     */
    private String owner;

    /**
     * 描述
     */
    private String descriptor;

    /**
     * 状态
     */
    private String status;

    /**
     * 所属model
     */
    private String model;

    /**
     * 所属project
     */
    private String project;

    /**
     * 最新构建时间
     * 单位：毫秒ms
     */
    private Long lastBuildTime;

    /**
     * 最新构建时间
     * 字符显示
     */
    private String lastBuildTimeStr;



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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Long getLastBuildTime() {
        return lastBuildTime;
    }

    public void setLastBuildTime(Long lastBuildTime) {
        this.lastBuildTime = lastBuildTime;
    }

    public String getLastBuildTimeStr() {
        return lastBuildTimeStr;
    }

    public void setLastBuildTimeStr(String lastBuildTimeStr) {
        this.lastBuildTimeStr = lastBuildTimeStr;
    }

    @Override
    public String toString() {
        return "CubeEntity{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", status='" + status + '\'' +
                ", model='" + model + '\'' +
                ", project='" + project + '\'' +
                ", lastBuildTime=" + lastBuildTime +
                ", lastBuildTimeStr='" + lastBuildTimeStr + '\'' +
                '}';
    }
}
