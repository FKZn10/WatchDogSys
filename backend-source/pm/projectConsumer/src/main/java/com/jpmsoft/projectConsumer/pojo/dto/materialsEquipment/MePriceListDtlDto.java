package com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 核价单清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:31
 */
@Data
public class MePriceListDtlDto implements Serializable {


    private static final long serialVersionUID = -7975518418902161314L;
    private String id;
    private String plId;
    /***
     * 货物名称
     */
    private String pldName;
    /***
     * 型号规格
     */
    private String pldModel;
    /***
     * 拟用货物注册商标
     */
    private String pldRegisteredTrademark;
    /***
     * 拟用货物生产厂家
     */
    private String pldGoodsUsedFactory;
    /***
     * 拟采购时间
     */
    private Date pldProcurementTime;
    /***
     * 单位
     */
    private String pldUnit;
    /***
     * 数量
     */
    private BigDecimal pldNumber;
    private BigDecimal pldAmount;
    /**
     * 甲方报价
     */
    private BigDecimal pldAmountFirstParty;
    /**
     * 乙方报价
     */
    private BigDecimal pldAmountSecondParty;
    /***
     * 备注
     */
    private String pldRemark;
    private Integer pldSort;
}
