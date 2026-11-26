package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 招标计划信息 合同外工程及费用 备注表
 */
@Data
@TableName("Sec_Info_Subject_Remark")
public class SecInfoSubjectRemark {
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 关联id
     */
    private String mainId;

    /**
     * 估算概算科目id
     */
    private String subIebId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;
}
