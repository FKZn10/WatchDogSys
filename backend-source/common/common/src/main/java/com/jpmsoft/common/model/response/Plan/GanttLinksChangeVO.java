package com.jpmsoft.common.model.response.Plan;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务关联线
 */
@Data
public class GanttLinksChangeVO implements Serializable {


    private static final long serialVersionUID = -5886245473106551547L;
    private String linkId;
    private String planId;
    private String linkSource;
    private String linkTarget;
    private Integer linkType;
}
