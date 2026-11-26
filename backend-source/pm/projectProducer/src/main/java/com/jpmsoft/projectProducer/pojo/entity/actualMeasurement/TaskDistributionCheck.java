package com.jpmsoft.projectProducer.pojo.entity.actualMeasurement;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author jjz
 */
@Data
@TableName("task_distribution_check")
public class TaskDistributionCheck {

    @TableId
    private String tdcId;

    private String tdId;

    // 检查项id
    private String ciId;

    // 检查期限
    private Date checkPeriodDate;

    // 检查人员
    private String checkPersonUid;

    // 检查类型
    private String checkType;

    // 序号
    private Integer orderNo;

    // 检查项测量数据
    private String checkItemMeasureData;

    // 检查项检查结果
    private String checkItemCheckResult;

    // 计算点数据
    private String computePointData;

    // 检查点X轴
    private Float checkPointX;

    // 检查点Y轴
    private Float checkPointY;

    // 是否已测量  01 未测量 02 已测量
    private String measuredFlag;

    // 测量时间
    private Date measureTime;

    // 检查项说明文字
    private String checkItemExplainText;

    // 检查项表达式
    private String checkItemExpression;

    // 计算点公式
    private String computePointFormula;

    // 爆点问题标识 01 爆点 02 非爆点
    private String hfHgFlag;


    private String qstId;

    private String qsrId;

}
