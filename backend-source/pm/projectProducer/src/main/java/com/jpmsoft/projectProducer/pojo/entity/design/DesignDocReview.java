package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 设计文件审核
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/19 9:27
 */
@Data
@TableName("Design_Doc_Review")
public class DesignDocReview extends BaseEntity {
    //设计文件名称
    private String drName;
    //设计单位
    private String drUnitId;
    //所属项目
    @JsonProperty("pId")
    private String pId;
    //合同id
    private String conId;
    //设计文件类型
    private String drType;
    //设计阶段
    private String drStage;
    //上传时间
    private Date drUploadTime;
    //设计文件
    private String drFile;
    //文件描述
    private String drDescribe;
}
