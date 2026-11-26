package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 招标计划项目关系表
 */
@Data
@TableName("Sec_Bidding_Project")
public class SecBiddingProject {

    @TableId(type = IdType.INPUT)
    private String sbpId;
    //招标计划id
    private String bpId;
    //项目id
    private String pId;
    //招采计划id
    private String ppId;
    private String sbpCreateUid;

}
