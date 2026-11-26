package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("design_Issued")
public class DesignIssued {

    @TableId
    private String id;

    private Date createTime;

    private String createUid;

    private String createUname;

    private Integer State;

    private Date StateTime;

    private String diEntityId;

    private String diModule;

    // 01 下发 02 不下发 03 补充下发
    private String diIsIssued;

    // 01 是勘察 设计 单位  02 是项目管理单位
    private String diCompanyType;

    private String driFormType;

}
