package com.tong.bd.kylin.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/14 17:14
 * Time: 14:15
 */
public enum KylinBaseExceptionCode{

    CONNECTION_ERROR(10001L, "获取链接失败"),
    PARAM_INVALID(10002L, "参数错误"),
    SQL_INVALID(10003L, "SQL语句错误"),
    TABLE_ALREADY_EXIST(20001L, "表已存在，不能重复创建"),
    TABLE_NOT_EXIST(20002L, "表不存在");

    Long code;
    String msg;

    KylinBaseExceptionCode(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static KylinBaseExceptionCode getByCode(Long code){
        if (code == null){
            return null;
        }
        for (KylinBaseExceptionCode kylinBaseExceptionCode : KylinBaseExceptionCode.values()) {
            if (kylinBaseExceptionCode.getCode().equals(code)) {
                return kylinBaseExceptionCode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "KylinBaseExceptionCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
