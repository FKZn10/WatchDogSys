package com.jpmsoft.projectProducer.pojo.vo.blxw;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BlxwInfoNoticeCloseVo extends BlxwInfoNoticeVo{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date closeTime;
    //闭环情况反馈
    private String closeFeedback;
    private String supportingDocument;
    //备注
    private String remark;
}