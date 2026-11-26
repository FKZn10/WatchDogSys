package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * AI信息提取记录
 */
@Data
@TableName("ai_info_extract_record")
public class AiInfoExtractRecord {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 附件MD5值
     */
    private String fileKey;
    /**
     * 问题MD5值
     */
    private String questionKey;
    /**
     * 文档模板
     */
    private String tempCode;
    /**
     * 调用AI执行的操作类型
     */
    private String aiType;
    /**
     * AI识别结果
     */
    private String aiResult;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String status;
}
