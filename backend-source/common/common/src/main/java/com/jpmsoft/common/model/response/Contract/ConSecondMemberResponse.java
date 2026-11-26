package com.jpmsoft.common.model.response.Contract;

import lombok.Data;

/**
 * 乙方项目主要人员
 */
@Data
public class ConSecondMemberResponse {
    private String id;
    private String conId;
    private String postionName;
    private String uName;
    private String uPhone;
}
