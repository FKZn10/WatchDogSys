package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标文件发布
 */
@Data
@TableName("Sec_Document_Release")
public class SecDocumentRelease {

    @TableId(type = IdType.INPUT)
    private String sdrId;
    private String secId;
    //工期
    private BigDecimal sdrStage;
    //开标时间
    private Date sdrBidOpenTime;
    //招标控制价
    private BigDecimal sdrControlPrice;
    //公告发布地址
    private String sdrUrl;
    //招标范围
    private String sdrContent;
    private String sdrRemarks;
    private String sdrCreateUid;
    private String sdrCreateUname;
    private Date sdrCreateTime;
    private Integer state;
    private Date stateTime;
    private String teId;
}
