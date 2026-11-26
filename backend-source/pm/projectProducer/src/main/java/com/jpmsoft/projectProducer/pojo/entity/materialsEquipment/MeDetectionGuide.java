package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 材料设备检测指引
 */
@Data
@TableName("Me_Detection_Guide")
public class MeDetectionGuide {

    @TableId(type = IdType.INPUT)
    private String dgId;
    //检测类别
    private String dgType;
    //产品类别
    private String dgProductCategory;
    //产品名称 样品名称
    private String dgProduct;
    //检测项目
    private String dgItem;
    //取样数量
    private String dgCount;
    //检测周期
    private String dgCycle;
    //委托时间
    private String dgEntrustedTime;
    //检测依据
    private String dgTestBasis;
    //备注
    private String dgRemark;
    private String dgCreateUid;
    private String dgCreateUname;
    private Date dgCreateTime;
    private Integer dgState;
    private Date dgStateTime;
    //规格
    private String dgSpecification;
    //生产厂家
    private String dgManufacturer;
    //代表数量
    private BigDecimal dgRepresentativeQuantity;
    private String  dgRepresentativeQuantityType;
    //样品数量
    private Integer dgSampleQuantity;
    //取样地点
    private String dgSamplePlace;
    //使用部位
    private String dgUsePart;
    /**
     * 是否有检测报告
     * 1：有检测报告
     * 2：没有检测报告
     */
    private Integer haveReport;
    private String teId;
}
