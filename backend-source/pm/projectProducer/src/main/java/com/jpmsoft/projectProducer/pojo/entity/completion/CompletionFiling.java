package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 竣工备案
 */
@Data
@TableName("Completion_Filing")
public class CompletionFiling {

    @TableId(type = IdType.INPUT)
    private String cfId;
    private String pId;
    private String secId;
    private String conId;
    private String cfType;
    private String cfContractorId;
    private String cfRemark;
    private String cfDoc;
    private String cfCreateUid;
    private String cfCreateUname;
    private Date cfCreateTime;
    private Integer cfState;
    private Date cfStateTime;
    private String teId;
}
