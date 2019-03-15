package com.tong.bd.kylin.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/19 10:55
 * Time: 14:15
 */
public enum QueryTimeTypeEnum {

    DAY_TYPE(1, "10"),
    MONTH_TYPE(2, "7"),
    YEAR_TYPE(3, "4"),;

    private Integer code;
    private String desc;

    QueryTimeTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static QueryTimeTypeEnum getByCode(Long code) {
        if (code != null) {
            for (QueryTimeTypeEnum queryTimeTypeEnum : QueryTimeTypeEnum.values()) {
                if (code.equals(queryTimeTypeEnum.getCode())) {
                    return queryTimeTypeEnum;
                }
            }
        }
        return null;
    }


}
