package com.tong.bd.kylin.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/14 17:14
 * Time: 14:15
 */
public class KylinBaseException extends RuntimeException{

    public long code;
    public String msg;

    public KylinBaseException(String msg){ super(msg); }

    public KylinBaseException(long code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public KylinBaseException(KylinBaseExceptionCode apiCode) {
        this(apiCode.getCode(), apiCode.getMsg());
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setException(KylinBaseExceptionCode kylinBaseExceptionCode){
        this.code = kylinBaseExceptionCode.code;
        this.msg = kylinBaseExceptionCode.msg;
    }

}
