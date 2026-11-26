package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 专家选取申请
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/8 16:15
 */
@Data
@TableName("Sec_Expert_Select")
public class SecExpertSelect extends BaseEntity {

    @JsonProperty("pId")
    private String pId;
    private String secId;
    //技术类人数
    private Integer esTechnologyNum;
    //经济类人数
    private Integer esEconomicsNum;
    //专家总人数
    private Integer esExpertNum;
    //备注
    private String esRemark;
    //申请单位
    private String esCompany;
    //联系人
    private String esContact;
    //联系电话
    private String esTel;
    //项目所属行业
    private String esBusiness;
    //使用方式
    private String esMode;
    //类型
    private String esCategory;
    //类别
    private String esType;
    //系统最迟备案时间
    private Date esFilingTime;
    private String teId;

    //开标地点
    private String esLocation;
    //开标时间
    private Date esBidTime;
    //其他说明
    private String esOtherTxt;
    //技术类人员抽取申请专业详情
    private String esExList;
    //技术类专家总人数
    private Integer esTechnologySum;
    //经济类专家总人数
    private Integer esEconomicsSum;
}
