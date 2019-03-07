package com.chezhibao.bigdata.dbms.server.enums;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
public enum TaskStatusEnum {
    /**
     * 任务创建
     */
    CREATE(0),
    /**
     * 任务等待执行
     */
    WAIT(1),
    /**
     * 任务执行中
     */
    INVOKE(2),
    /**
     * 任务完成
     */
    COMPLETE(3),
    /**
     * 任务取消
     */
    CANCEL(4)
    ;
    private Integer code;

    TaskStatusEnum(Integer code) {
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
}
