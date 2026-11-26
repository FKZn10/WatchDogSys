package com.jpmsoft.projectProducer.pojo.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BatchhandlingTaskListDto {
    /**
     * 业务表单ID 即Sys_File表ID字段
     */
    public String fileId;
    /**
     * 下一步操作人 即流程接收人
     */
    public String processed;
    /**
     * 处理意见
     */
    public String opinion;
    /**
     * 当前操作人
     */
    @JsonProperty("uId")
    public String uId;
    /**
     * 当前操作人
     */
    public String operator;

}
