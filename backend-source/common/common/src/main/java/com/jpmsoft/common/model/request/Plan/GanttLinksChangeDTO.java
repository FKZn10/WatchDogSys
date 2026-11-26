package com.jpmsoft.common.model.request.Plan;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务关联线
 */
@Data
public class GanttLinksChangeDTO implements Serializable {

    private static final long serialVersionUID = 2429314357050522539L;

    private String linkId;
    private String planId;
    private String linkSource;
    private String linkTarget;
    private Integer linkType;
}
