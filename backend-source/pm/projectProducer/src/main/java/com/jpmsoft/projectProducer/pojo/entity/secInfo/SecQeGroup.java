package com.jpmsoft.projectProducer.pojo.entity.secInfo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 资格审查小组
 */
@Data
@TableName("Sec_QE_Group")
public class SecQeGroup {

    @TableId(type = IdType.INPUT)
    private String qegId;
    private String secId;
    private String qegRemark;
    private String qegCreateUid;
    private String qegCreateUname;
    private Date qegCreateTime;
    private Integer qegState;
    private Date qegStateTime;
    private String qegScope;
}
