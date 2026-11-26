package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 招标工作方案及询价文件
 *
 */
@Data
@TableName("Sec_Work_Schema_Inquiry_Doc")
public class SecWorkSchemaInquiryDoc extends BaseEntity {

    private String secId;
    //备注
    private String wsidRemark;
    //询价文件
    private String inquiryDoc;
}
