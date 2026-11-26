package com.jpmsoft.common.model.response.ActualMeasurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jjz
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDistributionCheckModel extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 6124419360390767707L;

    private String tdcId;

    private String tdId;

    // 检查项id
    private String ciId;

    // 检查期限
    private Date checkPeriodDate;

    // 检查人员
    private String checkPersonUid;

    // 检查人员
    private String checkPersonUname;

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

    // 是否已测量
    private String measuredFlag;

    // 检查项名称
    private String ciName;

    private String ciParentName;

    // 检查项编码
    private String ciCode;

    //检查项类型
    private String ciType;

    // 检查项表达式
    private String ciExpection;

    // 计算点公式
    private String ciComputeExpection;

    // 检查项描述
    private String ciDescribe;

    // 检查项指标
    private String ciIndicator;

    private String ciMethodData;

    // 目录的id
    private String dmId;

    private Date measureTime;

    private String checkItemExplainText;

    // 检查项表达式
    private String checkItemExpression;

    // 计算点公式
    private String computePointFormula;



    private String hfHgFlag;

    @JsonProperty("pName")
    private String pName;

    private String diRegionName;

    private String diRegionCode;

    private String diRegionGroup;

    private String diDrawing;

    private String taskName;

    private String taskCode;


    // 标识三种状态，01-待分配 02-待整改 03-已整改
    private String taskStatus;

    /**
     * 检测状态 01 该处需要检测 02 该处已完成检测 03 该处需要整改
     */
    private String checkStatus;

    // 动态指标ID
    private String dynamicId;

    // 动态指标名称
    private String dynamicName;

    // 是否使用动态指标
    private Integer isUseDynamic;

}
