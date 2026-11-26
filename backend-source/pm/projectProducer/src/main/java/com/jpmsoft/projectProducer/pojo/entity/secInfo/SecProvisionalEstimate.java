package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 暂估价
 */
@Data
@TableName("Sec_Provisional_Estimate")
public class SecProvisionalEstimate {

    @TableId(type = IdType.INPUT)
    private String peId;
    //项目Id
    private String pId;
    //合同Id
    private String conId;
    //暂估价分类
    private String peType;
    //专业工程名称
    private String peProfessionalName;
    //总承包单位名称
    private String peOverallName;
    //暂估价类型
    private String peProType;
    //材料/设备编号
    private String peCode;
    //材料/设备名称
    private String peName;
    //规格型号
    private String peModel;
    //计量单位
    private String peMeasure;
    //【合同中暂估】单价/价格
    private BigDecimal peEstimatePrice;
    //【合同中暂估】数量
    private Integer peEstimateNumber;
    //【合同中暂估】合价
    private BigDecimal peEstimateCombine;
    //【总承包单位上报】单价/价格
    private BigDecimal peOverallPrice;
    //【总承包单位上报】合价
    private BigDecimal peOverallCombine;
    //【全咨/监理单位审查】单价/价格
    private BigDecimal peSupervisorPrice;
    //【全咨/监理单位审查】合价
    private BigDecimal peSupervisorCombine;
    //【造价咨询审核】单价/价格
    private BigDecimal peCostPrice;
    //【造价咨询审核】合价
    private BigDecimal peCostCombine;
    //【实际采购】单价/价格
    private BigDecimal peBuyertPrice;
    //【实际采购】合价
    private BigDecimal peBuyerCombine;
    //【增减】单价
    private String peRegulationType;
    //单价金额
    private BigDecimal peRegulationPrice;
    //【增减】合价
    private String peRegulationMold;
    //合价金额
    private BigDecimal peRegulationCombine;
    //定价方式
    private String pePricingMode;
    //审批单位
    private String peApprovalCompany;
    //审批时间
    private Date peStateTime;
    //创建人Id
    private String peCreateUid;
    //创建人
    private String peCreateUname;
    //流程状态
    private Integer state;
    private Date stateTime;
    private Date peCreateTime;
    private String peRemark;
    private Float peRate;
    private String teId;
}
