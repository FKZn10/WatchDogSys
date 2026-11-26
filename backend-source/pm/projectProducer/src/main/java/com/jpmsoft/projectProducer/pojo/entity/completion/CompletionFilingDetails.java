package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("completion_filing_details")
public class CompletionFilingDetails {

    /**
     * 主键 id
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 主表 id
     */
    @TableField("cf_id")
    private String cfId;

    /**
     * 文件名
     */
    @TableField("cfd_name")
    private String cfdName;

    /**
     * fileId
     */
    @TableField("cfd_file")
    private String cfdFile;
}
