package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 质询答辩监督组人员
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
@TableName("Sec_Defense_Supervision")
public class SecDefenseSupervision {

    @TableId(type = IdType.INPUT)
    private String sdsId;
    private String sqId;
    private String sdsUname;
    private String sdsPhone;
    private String sdsCompany;
    private String sdsRemark;
    private Integer sdsSort;
}
