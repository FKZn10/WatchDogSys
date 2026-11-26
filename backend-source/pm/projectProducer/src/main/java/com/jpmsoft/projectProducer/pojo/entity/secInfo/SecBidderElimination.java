package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 过多投标人淘汰
 */
@Data
@TableName("Sec_Bidder_Elimination")
public class SecBidderElimination {

    @TableId(type = IdType.INPUT)
    private String sbeId;
    private String secId;
    private Integer sbeNumberBidders;
    private Integer sbeNumberQualified;
    private Integer sbeNumberBidLinks;
    private String sbeBidSummaryFile;
    private String sbeNumberFinalists;
    private String sbeCreateUid;
    private String sbeCreateUname;
    private Date sbeCreateTime;
    private Integer sbeState;
    private Date sbeStateTime;
    private String teId;
}
