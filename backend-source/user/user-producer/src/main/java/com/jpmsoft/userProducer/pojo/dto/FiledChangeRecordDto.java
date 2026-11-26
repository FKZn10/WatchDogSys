package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 表字段修改记录
 */
@Data
public class FiledChangeRecordDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -2067581491499156693L;
    private String fcrTable;
    private String fcrFiled;
    private String fcrType;
    private String fcrKey;
    private String fcrOld;
    private String fcrNew;
    private String fcrUpdateUid;
    private String fcrUpdateUname;

    private String actId;
    private String actName;
}
