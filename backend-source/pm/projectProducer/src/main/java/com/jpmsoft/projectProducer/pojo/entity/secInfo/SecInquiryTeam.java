package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 询价小组
 */
@Data
@TableName("Sec_Inquiry_Team")
public class SecInquiryTeam {

    @TableId(type = IdType.INPUT)
    private String sitId;
    private String secId;
    private String sitScope;
    private String sitRemarks;
    private String sitCreateUid;
    private Date sitCreateTime;
    private String sitCreateUname;
    private Integer state;
    private Date stateTime;


}
