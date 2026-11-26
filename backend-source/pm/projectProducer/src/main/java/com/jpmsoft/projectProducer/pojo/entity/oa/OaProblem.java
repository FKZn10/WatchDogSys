package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目问题登记表
 */
@Data
@TableName("OA_Problem")
public class OaProblem {
    @TableId(type = IdType.INPUT)
    private String pmId;
    private String pId;
    private String secId;
    private String conId;
    private String pmTitle;
    private String pmRemark;
    private String pmCreateUid;
    private String pmCreateUname;
    private Date pmCreateTime;
    private Integer pmState;
    private Date pmStateTime;
    private String pmCode;
    private String teId;
}
