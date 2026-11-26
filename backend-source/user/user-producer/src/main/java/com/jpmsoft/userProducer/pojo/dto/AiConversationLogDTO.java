package com.jpmsoft.userProducer.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * AI提问日志
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AiConversationLogDTO extends BaseModeRequest {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 提示词、问题
     */
    private String clPrompt;
    /**
     * 回答内容
     */
    private String clAnswer;
    /**
     * 调用AI执行的操作类型
     */
    private String aiType;
    private Date createTime;
    private String createUid;
    private String createUname;
}
