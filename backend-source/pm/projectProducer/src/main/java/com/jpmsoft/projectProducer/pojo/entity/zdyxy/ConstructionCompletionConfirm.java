package com.jpmsoft.projectProducer.pojo.entity.zdyxy;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("construction_completion_confirm")
public class ConstructionCompletionConfirm implements Serializable {
    private static final long serialVersionUID = 1922779691414715325L;
    /**
     * 主键
     */
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;

    /**
     * 报修Id
     */
    private String mrId;
    private String confirmSerialNumber;
    /**
     * 工程地点
     */
    private String constructionSite;

    /**
     * 建设单位
     */
    private String constructionUnit;

    /**
     * 开工时间
     */
    private Date constructionStartTime;

    /**
     * 施工单位
     */
    private String constructionContractor;

    /**
     * 审计单位
     */
    private String auditUnit;

    /**
     * 竣工时间
     */
    private Date constructionCompletionTime;

    /**
     * 验收时间
     */
    private Date acceptanceTime;

    /**
     * 主要工作内容及工作量
     */
    private String mainWorkContent;

    /**
     * 工程形象照片
     */
    private String constructionImage;

    /**
     * 竣工图纸
     */
    private String constructionDrawing;

    /**
     * 审计单位意见
     */
    private String auditUnitOpinion;

    /**
     * 建设单位意见
     */
    private String constructionUnitOpinion;

    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    /**
     * 备注
     */
    private String remark;
}