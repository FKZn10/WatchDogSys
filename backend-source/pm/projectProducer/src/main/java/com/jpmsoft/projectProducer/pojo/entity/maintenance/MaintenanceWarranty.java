package com.jpmsoft.projectProducer.pojo.entity.maintenance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 质量保修书
 */
@Data
@TableName("Maintenance_Warranty")
public class MaintenanceWarranty {

    @TableId(type = IdType.INPUT)
    private String mwId;
    private String pId;
    private String secId;
    private String conId;
    private String mwCId;
    private String mwRemark;
    private String mwCreateUid;
    private String mwCreateUname;
    private Date mwCreateTime;
    private Integer mwState;
    private Date mwStateTime;
    private String teId;
}
