package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 保函办退
 */
@Data
@TableName("Guarantee_Letter_Back")
public class GuaranteeLetterBack {
    @TableId(type = IdType.INPUT)
    private String glbId;
    private String glsId;
    private String glbCompanyId;
    private Date glbDate;
    private String glbBase;
    private String glbRemark;
    private String glbCreateUid;
    private String glbCreateUname;
    private Date glbCreateTime;
    private Integer glbState;
    private Date glbStateTime;
    private Boolean glbCompletion;
    private String teId;
}
