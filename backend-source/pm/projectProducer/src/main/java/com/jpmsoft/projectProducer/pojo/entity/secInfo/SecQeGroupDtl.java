package com.jpmsoft.projectProducer.pojo.entity.secInfo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 资格审查小组清单
 */
@Data
@TableName("Sec_QE_Group_Dtl")
public class SecQeGroupDtl {
    @TableId(type = IdType.INPUT)
    private String qegdId;
    private String qegId;
    private String qegdUid;
    private Date qegdCreateTime;
    private Integer qegdSort;
    private String qegdMobilePhone;
    private String qegdUserName;
    private String qegdCid;
    private String qegdRemark;
}
