package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 标段采购明细
 *
 * @Author: 李文涛 aaron
 * @date: 2024/1/17 13:44
 */
@Data
@TableName("Sec_Procurement_Details")
public class SecProcurementDetails {
    @TableId(type = IdType.INPUT)
    private String id;
    //标段ID
    private String secId;
    //采购目录
    private String pdCatalog;
    //物资名称
    private String pdMaterialName;
    //预算单价
    private BigDecimal pdPrice;
    //数量
    private BigDecimal pdNum;
    //进口
    private Boolean pdImports;
    //品牌
    private String pdBrand;
    //型号
    private String pdModel;
    //品牌二
    private String pdBrandTwo;
    //型号二
    private String pdModelTwo;
    //高危仪器设备
    private Boolean pdRiskDevice;
    //论证报告
    private String pdProofFile;
    //主要技术参数
    private String pdMain;
    //售后服务要求
    private String pdSalesAsk;
    //其他相关材料附件
    private String pdOtherFile;
    //其他相关材料图片
    private String pdOtherImg;
    //服务内容
    private String pdServiceContent;
    //币种
    private String pdCurrency;
    //数量单位
    private String pdAaus;
    //序号
    private Integer pdSort;
    //采购目录名称
    private String pdCatalogName;
    //国家地区
    private String pdCountry;
}
