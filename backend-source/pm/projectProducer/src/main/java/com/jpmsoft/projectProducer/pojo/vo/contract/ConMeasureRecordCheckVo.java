package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 计量单审核记录
 *
 * @author: 李文涛 aaron
 * @date 2022/07/13 08:29
 */
@Data
public class ConMeasureRecordCheckVo implements Serializable {


    private static final long serialVersionUID = 9116786100541294181L;

    private String mearcId;
    private String mearId;
    private Float mearcAmount;
    private String mearcFile;
    private Date mearcTime;
    private String mearcUid;
    private String mearcUname;
    private String mearcCid;
    private String mearcNodeName;
    private String mearcExplain;
    private BigDecimal mearcExamineTotal;

    private String mearcCName;
    private String mearcCAbbName;
    private Integer mearcFileCount;
}