package com.jpmsoft.common.model.request.project;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

/**
 * @author yb.Sun
 * @date 2024/11/11 15:21
 */
@Data
public class ProjectRequest extends BaseFileDto {
    private String id;
    public String actid;
    public String actflowid;
}
