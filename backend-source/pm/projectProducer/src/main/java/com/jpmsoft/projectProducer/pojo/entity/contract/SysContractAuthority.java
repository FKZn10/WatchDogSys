package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 合同权限配置
 */
@Data
@TableName("SYS_Contract_Authority")
public class SysContractAuthority {

    @TableId(type = IdType.INPUT)
    private String caId;
    private String conId;
    private String caCId;
    private String caCreateUid;
    private String caCreateUname;
    private Date caCreateTime;
    private String teId;
    private String caType;
    private String caUserId;
}
