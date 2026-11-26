package com.jpmsoft.projectProducer.pojo.entity.enghonorpunish;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 工程处罚(EngPunish)实体类
 *
 * @author c
 * @since 2024-12-03 10:40:10
 */
@Data
@TableName("Eng_Punish")
public class EngPunish implements Serializable {
    private static final long serialVersionUID = -12631274502834527L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 处罚名称
     */
    private String punishName;
    /**
     * 处罚类型
     */
    private String punishType;
    /**
     * 处罚所属项目
     */
    private String punishProject;

    /**
     * 处罚金额（万元）
     */
    private BigDecimal punishMoney;
    /**
     * 处罚机构
     */
    private String punishUnit;

    /**
     * 处罚时间
     */
    private Date punishTime;
    /**
     * 处罚文件
     */
    private String punishFile;
    /**
     * 处罚情况说明
     */
    private String punishRemark;
    /**
     * 创建人id
     */
    private String createUid;
    /**
     * 创建人姓名
     */
    private String createUname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审批状态
     */
    private Integer state;
    /**
     * 审批时间
     */
    private Date stateTime;


}

