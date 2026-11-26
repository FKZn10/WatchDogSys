package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 询价小组成员
 */
@Data
@TableName("Sec_Inquiry_Team_Group")
public class SecInquiryTeamGroup {

    @TableId(type = IdType.INPUT)
    private String sitgId;
    private String sitId;
    private String sitgUid;
    private String sitgUname;
    private String sitgCid;
    private String sitgCname;
}
