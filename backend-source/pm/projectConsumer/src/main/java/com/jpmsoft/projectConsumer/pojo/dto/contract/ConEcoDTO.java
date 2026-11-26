
package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程变更令
 *
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:19
 */
@Data
public class ConEcoDTO extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = 8980282298998756052L;

    @ProcessFields(type = ProcessFieldEnum.id)
    private String ecoId;
    private String ecId;
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
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String ecoCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String ecoCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date ecoCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer ecoState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date ecoStateTime;
    private String teId;

    private String conName;
    private String conCode;
    private BigDecimal conMoneyTotal;
    private String ecName;
    private String ecCode;
    private String ecoPartyName;
}
