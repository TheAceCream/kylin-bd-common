package com.qf58.bd.kylin.entities.cubes;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/18 10:42
 * Time: 14:15
 */
public class MeasuresEntity implements Serializable {

    private static final long serialVersionUID = -2038963966476256326L;

    private String name;

    private Function function;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "MeasuresEntity{" +
                "name='" + name + '\'' +
                ", function=" + function +
                '}';
    }
}
