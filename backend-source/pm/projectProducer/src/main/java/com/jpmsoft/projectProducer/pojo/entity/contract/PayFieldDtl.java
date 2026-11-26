package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 支付填写清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/5 14:50
 */
@Data
@TableName("Pay_Field_Dtl")
@Deprecated
public class PayFieldDtl {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String pfValue;
    private String payId;
    private String pfId;
    private String createUid;
    private String createUname;
    private Date createTime;

}
