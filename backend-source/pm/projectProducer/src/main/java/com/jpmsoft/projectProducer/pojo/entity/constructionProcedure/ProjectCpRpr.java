package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Project_Cp_Rpr")
public class ProjectCpRpr {

    @TableId(type = IdType.INPUT)
    private String id;
    private String cpdId;
    private String fileName;
    private String fileCode;
    private Date fileApprovalTime;
    private Date fileExpirationDate;
    private Boolean manyTimes;
    private Boolean isEnd;
    private String pcrDoc;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String operationType;
    private String notes;
    private String taskId;
    private String cpId;
    private String pId;
    private String fileExpire;
    private String remarks;
}
