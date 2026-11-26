package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import lombok.Data;

import java.io.Serializable;

/**
 * 专家库逻辑判断基础配置信息
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:57
 */
@Data
public class QsExpertLogicalConfDTO implements Serializable {
    /**
     * 请假申请需要提前多少个小时申请
     * 缺省8 既前一天的18点之前
     */
    private Integer vacationHours = 8;
    /**
     * 暂停申请需要提前X天申请
     * 缺省3
     */
    private Integer pauseDays = 3;
    /**
     * 年度内累计暂停X天将自动停用专家
     * 缺省180
     */
    private Integer pauseYearSum = 180;
    /**
     * 抽取时是否根据申请单位排除其单位下的专家
     * 缺省false
     */
    private Boolean excludeCpy = false;

    /**
     * 专家最大年龄
     * 缺省60
     */
    private Integer maxAge = 60;
    /**
     * 收到通知后三天内(工作日)可以申诉
     * 缺省3
     */
    private Integer appealDay = 3;
    /**
     * 专家月抽取次数
     * 超过次数不会再被抽取
     */
    private Integer monthExNum = 2;

    /**
     * 参加集团系统工作时间 最小限制年数
     * -1不限制
     */
    private Integer exSysWorkYear = 5;
}
