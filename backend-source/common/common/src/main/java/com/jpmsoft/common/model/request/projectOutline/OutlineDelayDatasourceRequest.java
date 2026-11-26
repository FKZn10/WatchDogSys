package com.jpmsoft.common.model.request.projectOutline;

import lombok.Data;

/**
 * 大纲延迟数据源请求类
 *
 * @author tianhelin
 * @date 2025/8/11
 */
@Data
public class OutlineDelayDatasourceRequest {
    private String id;
    private String oplId;
    private String odId;
    private String tCode;
    private String pId;
    private String sfId;
}