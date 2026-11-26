package com.jpmsoft.common.model.request.loadBoard;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectLoadRequest extends BaseLoadBoardRequest {
    private static final long serialVersionUID = -8737129479024842292L;
    private String id;
    private String projectName;
    private String projectCode;
    private String projectType;
    private String projectStatus;
    private String projectUseUnit;
    private List<String> projectTypes;
    private List<String> projectStatuses;
}