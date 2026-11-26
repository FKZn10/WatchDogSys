package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 工程验收——标段信息记录表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:33
 */
@Data
@TableName("Completion_Acceptance_Section")
public class CompletionAcceptanceSection {

    @TableId(type = IdType.INPUT)
    private String casId;
    private String caId;
    private String secId;
}
