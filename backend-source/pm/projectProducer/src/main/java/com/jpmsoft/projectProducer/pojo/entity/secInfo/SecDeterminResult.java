package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 定标结果
 */
@Data
@TableName("Sec_Determin_Result")
public class SecDeterminResult {

    @TableId(type = IdType.INPUT)
    private String drId;
    //标段id
    private String secId;
    private String drRemark;
    //定标类型
    private String drCalibrationType;
    //定标日期
    private Date drCalibrationDate;
    //定标方式
    private String drCalibrationMode;
    //候选人数
    private Integer drNumberVacancies;
    //中标单位
    private String drBidWinner;
    private String drCreateUid;
    private String drCreateUname;
    private Date drCreateTime;
    private Integer drState;
    private Date drStateTime;
    //择优方式
    private String drPreferential;
    //入围择优要素
    private String drSelectedFactor;
    //定标择优要素
    private String drCalibrateFactor;
    private String teId;
    //排名
    private Integer drRank;
    //供应商名称
    private String drSupplierName;
    //供应商报价
    private BigDecimal drSupplierPrice;
    //供应商评分
    private BigDecimal drSupplierScore;
    //供应商中标价
    private BigDecimal drSupplierBid;
    //定标候选
    private Integer drCandidate;
    //代理供应商名称
    private String drAgentName;
    //招标公告时间
    private Date drPrTime;
    //开标时间
    private Date drBidTime;
    //开标地址
    private String drLocation;
    //采购方式名称
    private String drProcurementName;
    //成交供应商名称
    private String drSuccessfulName;
    //成交金额
    private BigDecimal drSuccessfulAmt;
    //成交文本金额
    private String drSuccessfulTxtAmt;
    //供应商联系人
    private String drSupplierContacts;
    //供应商联系方式
    private String drSupplierPhone;
    //第三方对接ID(北林成交表ID)
    private String drThirdId;
}
