package com.jpmsoft.common.model.request.loadBoard;

import lombok.Data;

import java.util.List;
@Data
public class SiteManagementRequest extends BaseLoadBoardRequest{
    private static final long serialVersionUID = 986023332739054839L;
    private List<Integer> monthYearCompareRange;
    private Integer isFinished;
    private Integer year;
}