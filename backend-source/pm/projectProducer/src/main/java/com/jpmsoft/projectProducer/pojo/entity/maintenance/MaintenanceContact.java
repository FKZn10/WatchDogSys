package com.jpmsoft.projectProducer.pojo.entity.maintenance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 维保联系人
 */
@Data
@TableName("Maintenance_Contact")
public class MaintenanceContact {
    @TableId(type = IdType.INPUT)
    private String mcId;
    private String pId;
    private String secId;
    private String conId;
    private String mcCId;
    private String mcUnitType;
    private String mcUnitLeader;
    private String mcUnitLeaderTel;
    private String mcSiteLeader;
    private String mcSiteLeaderTel;
    private String mcCreateUid;
    private String mcCreateUname;
    private Date mcCreateTime;
    private Integer mcState;
    private Date mcStateTime;
    private String teId;
}
