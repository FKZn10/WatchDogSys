package com.jpmsoft.projectProducer.pojo.vo.materialsEquipment;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 核价单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:27
 */
@Data
public class MePriceListVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -3440360224409011124L;
    //region 流程表固定字段
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    //endregion
    private String conId;
    private String plCode;
    private String plApplicant;
    private String plPm;
    private String plReason;
    private String plRemark;

    private String conName;
    private String plApplicantName;
    private String plApplicantAbbName;
    private String conPartyBName;
    private String conPartyBAbbName;
    private List<MePriceListDtlVo> mePriceListDtls;

}
