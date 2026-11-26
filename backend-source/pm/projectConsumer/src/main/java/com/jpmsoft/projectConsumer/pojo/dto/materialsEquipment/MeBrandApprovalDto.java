package com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 项目品牌报审
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:17
 */
@Data
public class MeBrandApprovalDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -1338338502312530429L;
    @ProcessFields(type = ProcessFieldEnum.id)
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
    //region 表单附件
    @FileFields(index = 1)
    private String baBidFile;
    @FileFields(index = 2)
    private String baLicenseFile;
    @FileFields(index = 3)
    private String baQaFile;
    @FileFields(index = 4)
    private String baAgentFile;
    @FileFields(index = 5)
    private String baSamplePic;
    @FileFields(index = 6)
    private String baInfoPic;
    @FileFields(index = 7)
    private String baPhysicalPic;
    @FileFields(index = 8)
    private String baOtherFile;


    @FileFields(index = 1, type = "baBidFileIds")
    private String baBidFileIds;
    @FileFields(index = 2, type = "baLicenseFileIds")
    private String baLicenseFileIds;
    @FileFields(index = 3, type = "baQaFileIds")
    private String baQaFileIds;
    @FileFields(index = 4, type = "baAgentFileIds")
    private String baAgentFileIds;
    @FileFields(index = 5, type = "baSamplePicIds")
    private String baSamplePicIds;
    @FileFields(index = 6, type = "baInfoPicIds")
    private String baInfoPicIds;
    @FileFields(index = 7, type = "baPhysicalPicIds")
    private String baPhysicalPicIds;
    @FileFields(index = 8, type = "baOtherFileIds")
    private String baOtherFileIds;
    //endregion

    @JsonProperty("pName")
    private String pName;
    private String conName;
    private String secName;
    private List<MeBrandApprovalDtlDto> meBrandApprovalDtls;
}
