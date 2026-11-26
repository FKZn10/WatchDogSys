package com.jpmsoft.projectProducer.pojo.entity.completion;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("building_info_sub")
public class BuildingInfoSub {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 信息库ID（对应建筑信息库ID）
     */
    private String birId;

    /**
     * 分项工程名称
     */
    private String bisName;

    /**
     * 分项工程分类
     */
    private String bisCategory;

    /**
     * 数量（Decimal(18,6)）
     */
    private BigDecimal bisNum;

    /**
     * 单位
     */
    private String bisUnit;

    /**
     * 质保起始时间
     */
    private Date bisWarrantyStart;

    /**
     * 质保结束时间
     */
    private Date bisWarrantyEnd;

    /**
     * 质保状态（未生效/在保/已过期）
     */
    private String bisWarranty;

    /**
     * 厂商单位
     */
    private String bisCompany;

    /**
     * 厂商联系人
     */
    private String bisFirmContact;

    /**
     * 厂商联系人电话
     */
    private String bisFirmPhone;

    /**
     * 创建人
     */
    private String createUname;

    /**
     * 创建人ID
     */
    private String createUid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 分项工程父级ID
     */
    private String bisParentId;

}
