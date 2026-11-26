package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 工程变更令
 *
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:19
 */
@Data
@TableName("Con_Eco")
public class ConEco {

    @TableId(type = IdType.INPUT)
    private String ecoId;
    private String ecId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String ecoCode;
    private String ecoContent;
    private String ecoPartyCid;
    private String ecoEngineerName;
    private Integer ecoArticle;
    private Integer ecoNextOne;
    private Integer ecoNextTwo;
    private Integer ecoNextThree;
    private Integer ecoNextFour;
    private String ecoExplain;
    private String ecoRemark;
    private String ecoCreateUid;
    private String ecoCreateUname;
    private Date ecoCreateTime;
    private Integer ecoState;
    private Date ecoStateTime;
    private String teId;

    //变更责任主体其他
    private String ecResponsiblePartyQt;
    //工程变更主要原因其他
    private String ecMainCauseQt;
}
