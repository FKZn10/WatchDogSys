package com.jpmsoft.projectProducer.pojo.vo.visaChange;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:51
 */
@Data
public class ConDccsVO  extends ProjectCommonRes implements Serializable {

    private String dccsId;
    private String conId;
    private String dccsCode;
    private String dccsName;
    private String dccsMajor;
    private String dccsMainUnit;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String conName;
    private String dccsMainUnitName;
    private List<ConDccsCompanyVO> conDccsCompanys;
}
