package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 招标文件控制价
 * @author: 李文涛 aaron
 * @date: 2022/8/4 11:13
 */
@Data
@TableName("Sec_Doc_Control")
public class SecDocControl extends BaseEntity {

    private String secId;
    //编制单位
    private String dcPreparedBy;
    //编制单位编制价
    private BigDecimal dcPreparedPrice;
    //建设单位审核造价
    private BigDecimal dcConstructPrice;
    //总建筑面积经济指标(元/㎡)
    private BigDecimal dcEconomicIndicator;
    //不可竞争费合计
    private BigDecimal dcNonCompetitive;
    //工程总价、单位工程、措施费等清单计价合计汇总表
    private String dcBoqDoc;
    //造价咨询出具盖章的招标控制价封面扫描件
    private String dcConsultUnitDoc;
    //政府造价审核备案回执
    private String dcFilingReceipt;
    //招标范围
    private String dcScope;
    //招标金额
    private BigDecimal dcAmount;
    //定标方式
    private String dcDeterminType;
    //计价模式
    private String dcPriceMode;
    //报价上限
    private BigDecimal dcUpperLimit;
    //工程规模及特征
    private String dcFeatures;
    //工期要求
    private String dcExecutiveStandard;
    //资金来源
    private String dcSourceFunds;
    //履约担保形式
    private String dcPerformanceGuaranteeType;
    //履约担保金
    private BigDecimal dcPerformanceBond;
    //金额比例
    private Float dcPerfBondRatio;
    private String dcRemark;
    private String teId;
}
