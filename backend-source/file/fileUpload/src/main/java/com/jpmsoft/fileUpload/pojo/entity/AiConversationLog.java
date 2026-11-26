package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * AI提问日志
 */
@Data
@TableName("ai_conversation_log")
public class AiConversationLog {

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

    /**
     * 会话ID，用于关联多个提问和回答
     */
    private String conversationId;

    /**
     * 原始提示词，未经过处理的原始输入
     */
    private String clRawPrompt;

    /**
     * 原始回答内容，未经过处理的原始输出
     */
    private String clRawAnswer;
}
