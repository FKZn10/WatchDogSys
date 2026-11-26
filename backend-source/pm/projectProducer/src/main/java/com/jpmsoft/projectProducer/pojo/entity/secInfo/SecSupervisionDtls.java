package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 入围监督委员会
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
@TableName("Sec_Supervision_Dtls")
public class SecSupervisionDtls {

    @TableId(type = IdType.INPUT)
    private String ssdId;
    private String scsId;
    private String ssdUname;
    private String ssdPhone;
    private String ssdCompany;
    private String ssdRemark;
    private Integer ssdSort;
}
