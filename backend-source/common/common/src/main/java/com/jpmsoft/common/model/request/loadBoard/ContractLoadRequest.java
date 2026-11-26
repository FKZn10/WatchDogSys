package com.jpmsoft.common.model.request.loadBoard;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContractLoadRequest extends BaseLoadBoardRequest {
    private static final long serialVersionUID = -4069473529604789827L;
    private String contractType;
    private String contractStatus;
    private String projectName;
    private String projectCode;
    private String contractName;
    private String contractCode;
    private List<String> contractTypes;
    private List<String> contractStatuses;
}