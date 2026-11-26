package com.jpmsoft.projectProducer.pojo.vo.materialsEquipment;

import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:03
 */
@Data
public class MeBrandLibraryApplyVo extends BaseFileResponse implements Serializable {

    private static final long serialVersionUID = 1920452217560867186L;
    private String blaId;
    private String brandId;
    private String blaCode;
    private String blaTitle;
    private String blaRemark;
    private Date blaDate;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    private List<MeBrandLibraryVo> meBrandLibrarys;
}
