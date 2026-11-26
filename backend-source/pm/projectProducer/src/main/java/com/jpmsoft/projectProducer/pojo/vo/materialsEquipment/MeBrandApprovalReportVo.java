package com.jpmsoft.projectProducer.pojo.vo.materialsEquipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目品牌报审
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:17
 */
@Data
public class MeBrandApprovalReportVo extends BaseFileResponse implements Serializable {

    private static final long serialVersionUID = -1338338502312530429L;
    private String baId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String secId;
    private String baCode;
    private String baPm;
    private String baCategory;
    private String baType;
    private String baProfessional;
    private String baTechnical;
    private String baRemark;
    /**
     * 本次报审品种名称
     */
    private String baMetNames;
    /**
     * 本次报审品牌名称
     */
    private String baBrands;
    /**
     * 本次报审品牌数量
     */
    private String badCounts;
    /**
     * 报审次数
     */
    private Integer reviewsCount;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    //region 表单附件
    private String baBidFile;
    private String baLicenseFile;
    private String baQaFile;
    private String baAgentFile;
    private String baSamplePic;
    private String baInfoPic;
    private String baPhysicalPic;
    private String baOtherFile;
    //endregion

    @JsonProperty("pName")
    private String pName;
    private String conName;
    private String secName;

    private String baTypeName;
    private String baProfessionalName;
}
