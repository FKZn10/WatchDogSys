package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 当前编码
 */
@Data
@TableName("SYS_Sequence_Current_Code")
public class SysSequenceCurrentCode {

    @TableId(type = IdType.INPUT)
    private String ccId;
    private String sId;
    private String pId;
    private String conId;
    private Integer ccNo;
    private String ccCode;
    private String ccReset;
    private String secId;

}
