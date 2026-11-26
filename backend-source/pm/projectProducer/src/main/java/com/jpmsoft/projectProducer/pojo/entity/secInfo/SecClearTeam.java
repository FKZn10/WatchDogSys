package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 清标小组
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:29
 */
@Data
@TableName("Sec_Clear_Team")
public class SecClearTeam {

    @TableId(type = IdType.INPUT)
    private String sctId;
    private String secId;
    private String sctTenderee;
    private String sctBiddingType;
    private String sctScope;
    private String sctRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

}
