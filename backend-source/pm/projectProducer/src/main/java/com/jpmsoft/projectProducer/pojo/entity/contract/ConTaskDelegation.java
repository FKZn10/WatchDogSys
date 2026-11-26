package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 任务委托单
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/5 13:51
 */
@Data
@TableName("Con_Task_Delegation")
public class ConTaskDelegation extends BaseEntity {

    private String pId;
    private String conId;
    private String ctdType;
    private String ctdCode;
    private String ctdProNo;
    private String ctdSuperviseNo;
    private Date ctdDate;
    private String ctdCpy;
    private String ctdContacts;
    private String ctdContactsTel;
    private String ctdSoutCpy;
    private String ctdSoutContacts;
    private String ctdSoutContactsTel;
    private String ctdQualityCpy;
    private String ctdProAddress;
    private String ctdSampler;
    private String ctdWitness;
    private String ctdSamplerTel;
    private String ctdWitnessTel;
    private String ctdProType;
    private Date ctdMeasure;
    private Date ctdDeliverables;
    private String ctdRequire;
    private String ctdContent;
    private String ctdScope;
    private String ctdRemark;
    private String teId;

    private String conAnnualId;
}
