package com.jpmsoft.projectProducer.pojo.entity.zdyxy;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("construction_drawing")
public class ConstructionDrawing implements Serializable {
    private static final long serialVersionUID = -3056460742537259661L;
    /**
     * 主键
     */
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;

    private String constructionDrawingName;
    /**
     * 施工图附件上传
     */
    private String constructionDrawingCode;
    /**
     * 设计合同附件上传
     */
    private String designContractAttachmentFile;

    /**
     * 施工图附件上传
     */
    private String constructionDrawingAttachmentFile;

    /**
     * 施工图附件最终版
     */
    private String constructionDrawingFinalVersionFile;

    /**
     * 发起单位
     */
    private String initiatingUnit;

    /**
     * 发起人
     */
    private String initiatingPerson;

    /**
     * 发起时间
     */
    private Date initiatingTime;

    /**
     * 发起人联系方式
     */
    private String initiatingPersonPhone;

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
    private String context;
}