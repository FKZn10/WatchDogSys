package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.requestAndResponse.SelectModel;
import lombok.Data;

import java.util.List;

@Data
public class PreProjectSolutionDetailApplicationModel {
    private String type;
    private String types;
    private List<SelectModel> list;
    private String value;
}

