package com.tong.bd.kylin.entities.cubes;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/18 10:53
 * Time: 14:15
 */
public class Function implements Serializable {

    private static final long serialVersionUID = 8879173920499605720L;

    private String expression;

    private Map<String,String> parameter;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Map<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(Map<String, String> parameter) {
        this.parameter = parameter;
    }
}
