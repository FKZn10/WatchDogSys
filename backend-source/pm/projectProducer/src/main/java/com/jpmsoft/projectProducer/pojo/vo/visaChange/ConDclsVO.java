
package com.jpmsoft.projectProducer.pojo.vo.visaChange;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计变更记录单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/7 8:34
 */
@Data
public class ConDclsVO extends ProjectCommonRes implements Serializable {

    private String dclsId;
    private String conId;
    private String dclsCode;
    private String dclsProposedUnit;
    private Date dclsTime;
    private String dclsName;
    private String dclsMajor;
    private String dclsChangeReason;
    private String dclsPreChange;
    private String dclsModifiedScheme;
    private BigDecimal dclsPreTotal;
    private BigDecimal dclsModifiedTotal;
    private BigDecimal dclsFirstTotal;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String dcoId;
    private String dcbId;

    private String conName;
    private String dcoProjectName;
    private String dcoName;
}
