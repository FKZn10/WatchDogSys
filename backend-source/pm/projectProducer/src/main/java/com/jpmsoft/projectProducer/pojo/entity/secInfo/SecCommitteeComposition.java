package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 定标委员会及监督委员会构成
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:33
 */
@Data
@TableName("Sec_Committee_Composition")
public class SecCommitteeComposition {

    @TableId(type = IdType.INPUT)
    private String sccId;
    private String secId;
    private String sccTenderee;
    private String sccBiddingType;
    private String sccScope;
    private String sccRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
}
