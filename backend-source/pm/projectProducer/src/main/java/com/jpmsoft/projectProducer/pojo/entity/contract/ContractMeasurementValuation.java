package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 合同计量计价低代码主表
 */
@Data
@TableName("contract_measurement_valuation")
public class ContractMeasurementValuation {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String pId;
    private String conId;
    private String attachment;
}
