package com.jpmsoft.common.model.request.Plan;

import lombok.Data;

/**
 * 模板任务关联线
 */
@Data
public class GanttLinksTemplateDTO {

    private String linkId;
    private String ptId;
    private String linkSource;
    private String linkTarget;
    private Integer linkType;
}
