package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 计量单审核记录
 * @author: 李文涛 aaron
 * @date 2022/07/13 08:29
 */
@Data
public class ConMeasureRecordCheckDto extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = -2174543207222322706L;
    /**
     * 
     */
    private String mearcId;

    /**
     * 
     */
    private String mearId;

    /**
     * 
     */
    private Float mearcAmount;

    /**
     * 
     */
    private String mearcFile;

    /**
     * 
     */
    private Date mearcTime;

    /**
     * 
     */
    private String mearcUid;

    /**
     * 
     */
    private String mearcUname;

    /**
     * 
     */
    private String mearcCid;

    /**
     * 
     */
    private String mearcNodeName;

    /**
     * 
     */
    private String mearcExplain;
    private BigDecimal mearcExamineTotal;

    private String mearcFileIds;
}