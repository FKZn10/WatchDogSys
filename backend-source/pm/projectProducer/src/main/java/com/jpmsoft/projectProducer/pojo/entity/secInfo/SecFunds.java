package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 标段经费选择
 *
 * @Author: 李文涛 aaron
 * @date: 2024/2/19 8:39
 */
@Data
@TableName("Sec_Funds")
public class SecFunds {

    @TableId(type = IdType.INPUT)
    private String id;
    //标段id
    private String secId;
    //经费卡号
    private String sfCard;
    //经费账号
    private String sfAccount;
    //经费名称
    private String sfName;
    //经费科目
    private String sfSubject;
    //经费主管部门
    private String sfDepartment;
    //经费负责人
    private String sfHead;
    //经费负责人
    private Integer sfSort;
    //经费代码
    private String sfCode;
    //经费负责人工号
    private String sfHeadAccount;
    //经费负责人名称
    private String sfHeadName;
    //经费主管部门代码
    private String sfDepartmentCode;
    //经费主管部门名称
    private String sfDepartmentName;
    //使用金额
    private BigDecimal sfMoney;
    //是否主经费（是主经费该值为1，否则为0）
    private String sfMain;
}
