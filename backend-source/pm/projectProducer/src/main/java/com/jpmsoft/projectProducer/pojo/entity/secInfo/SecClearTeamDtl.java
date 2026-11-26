package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 清标小组清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
@TableName("Sec_Clear_Team_Dtl")
public class SecClearTeamDtl {

    @TableId(type = IdType.INPUT)
    private String ctdId;
    private String sctId;
    private String ctdUname;
    private String ctdPhone;
    private String ctdCompany;
    private String ctdRemark;
    private Integer ctdSort;
}
