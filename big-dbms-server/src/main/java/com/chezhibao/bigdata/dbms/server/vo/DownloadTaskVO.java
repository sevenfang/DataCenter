package com.chezhibao.bigdata.dbms.server.vo;

import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;
import lombok.Data;
import lombok.Getter;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/13.
 */
@Data
public class DownloadTaskVO {
    /**
     * 对应的操作
     */
    private Operation operation;
    /**
     * 任务对象
     */
    private TaskInfoBO taskInfoBO;


    @Getter
    public enum Operation {

        /**
         * 新建任务
         */
        CREATE,
        /**
         * 删除任务
         */
        DELETE,
        /**
         * 删除任务
         */
        DOWNLOAD,
        /**
         * 更新任务
         */
        UPDATE,;
    }
}
