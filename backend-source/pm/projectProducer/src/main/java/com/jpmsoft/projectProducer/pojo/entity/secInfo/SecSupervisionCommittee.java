package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 监督委员会
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
@TableName("Sec_Supervision_Committee")
public class SecSupervisionCommittee {

    @TableId(type = IdType.INPUT)
    private String ssdId;
    private String sccId;
    private String ssdUname;
    private String ssdPhone;
    private String ssdCompany;
    private String ssdRemark;
    private Integer ssdSort;
}
