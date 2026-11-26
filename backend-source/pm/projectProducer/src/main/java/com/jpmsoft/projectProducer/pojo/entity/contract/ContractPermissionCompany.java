package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 合同权限配置-合同参与单位
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 16:44
 */
@Data
@TableName("Contract_Permission_Company")
public class ContractPermissionCompany {

    @TableId(type = IdType.INPUT)
    private String id;
    private String cId;
    private String conId;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Integer cpcSort;
}
