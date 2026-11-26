package com.jpmsoft.projectProducer.pojo.entity.blxw;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("Blxw_Type")
public class BlxwType extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 190673413373692697L;
    private String id;
    private String blxwName;
    private String blxwParentId;
    private String blxwPath;
    private Integer blxwSort;
    //标准化处理措施
    private String blxwMeasures;
    //不良行为等级
    private String blxwLevel;
    //扣分情况
    private Double blxwDeductScore;
}
