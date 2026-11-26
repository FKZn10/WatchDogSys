package com.jpmsoft.common.model.requestAndResponse.SafeResponsibility;

import lombok.Data;

/**
 * 安全责任体系模板复制请求。
 */
@Data
public class SafeResponsibilityCopyDTO {
    private String sourceSystemId;
    private String pId;
    private String conId;
    private String systemName;
}
