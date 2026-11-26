package com.jpmsoft.fileUpload.pojo.dto;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 归档文件名称规则
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:03
 */
@Data
public class ArchiveFileNameRuleDTO extends BasePageEntity implements Serializable {


    private static final long serialVersionUID = 7307775510931407512L;

    private String id;
    private Integer afnrSort;
    private String afnrType;
    private String afnrValue;
    private String afnId;
    private String afnrShipTable;
    private String afnrShipCode;
    private String afnrDicCode;
    private String afnrShipCurrent;
}
