package com.jpmsoft.common.model.requestAndResponse.Blxw;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class BlxwTypeModel extends BaseFileRequest {
    private String id;
    private String blxwName;
    private String blxwParentId;
    private String blxwPath;
    //标准化处理措施
    private String blxwMeasures;
    //不良行为等级
    private String blxwLevel;
    //扣分情况
    private Double blxwDeductScore;
    private Integer blxwSort;
    private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date stateTime;
    private String createUid;
    private String createUname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private List<BlxwTypeModel> childNodes;
}
