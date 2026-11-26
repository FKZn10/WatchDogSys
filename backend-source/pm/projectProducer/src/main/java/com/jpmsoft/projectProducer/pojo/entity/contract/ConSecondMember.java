package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 乙方项目主要人员
 */
@Data
@TableName("Con_Second_Member")
public class ConSecondMember {
    private String id;
    private String conId;
    private String postionName;
    private String uName;
    private String uPhone;
}
