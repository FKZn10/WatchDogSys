package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 材料设备品种库
 */
@Data
@TableName("Me_Type_Library")
public class MeTypeLibrary {
    @TableId(type = IdType.INPUT)
    private String metId;
    private String metName;
    private String metCode;
    private Integer metSort;
    private String metManageType;
    private String metUnit;
    private String metParentId;
    private String metType;
    private String metTag;
    private Integer metState;
    private String metCreateUid;
    private String metCreateUname;
    private Date metCreateTime;
}
