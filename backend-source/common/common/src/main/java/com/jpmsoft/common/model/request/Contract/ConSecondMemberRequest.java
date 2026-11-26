package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 乙方项目主要人员
 */
@Data
public class ConSecondMemberRequest {
    private String id;
    private String conId;
    private String postionName;
    private String uName;
    private String uPhone;
}
