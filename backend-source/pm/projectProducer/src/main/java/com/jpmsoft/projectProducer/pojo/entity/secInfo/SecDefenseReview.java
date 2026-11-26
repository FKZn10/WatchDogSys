package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 质询答辩评审组人员
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
@TableName("Sec_Defense_Review")
public class SecDefenseReview {

    @TableId(type = IdType.INPUT)
    private String sdrId;
    private String sqId;
    private String sdrUname;
    private String sdrPhone;
    private String sdrCompany;
    private String sdrRemark;
    private Integer sdrSort;
}
