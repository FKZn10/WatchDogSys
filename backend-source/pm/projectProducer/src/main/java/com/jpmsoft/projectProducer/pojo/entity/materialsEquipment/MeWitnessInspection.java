package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 见证送检
 */
@Data
@TableName("Me_Witness_Inspection")
public class MeWitnessInspection {

    @TableId(type = IdType.INPUT)
    private String wiId;
    private String eadId;
    private String pId;
    private String conId;
    private Integer wiFrequency;
    private String wiUnitId;
    private String wiUnitContacts;
    private String wiUnitContactsTel;
    private String wiDeliveryman;
    private String wiDeliverymanTel;
    private String wiWitness;
    private String wiWitnessTel;
    private String wiOrderNo;
    private String wiSamplingMethod;
    private Date wiSamplingStartDate;
    private Date wiSamplingEndDate;
    private Date wiStartDate;
    private Date wiEndDate;
    private String wiEnsureSample;
    private String wiPicture;
    private String wiSamplingSupervisor;
    private String wiCreateUid;
    private String wiCreateUname;
    private Date wiCreateTime;
    private Integer wiState;
    private Date wiStateTime;
    private String eaId;
    private String teId;
    private String wiCode;
    private String wiDetectionCode;

}
