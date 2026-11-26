package com.jpmsoft.common.model.request.project;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PreProjectIDsRequest implements Serializable {

    private static final long serialVersionUID = 781593064986193340L;
    private List<String> projectIds;
}
