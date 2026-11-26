package com.jpmsoft.common.model.response.Oa;

import lombok.Data;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/2/5 11:07
 * @Description:
 * @Version: 1.0
 */
@Data
public class OaNoticeProjectDeptResponse {

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 部门id
     */
    private String depId;

    /**
     * 部门名称
     */
    private String depName;

}
