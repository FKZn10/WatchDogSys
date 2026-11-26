package com.jpmsoft.common.model.request.proCp;

import lombok.Data;

/**
 * 项目建设程序库-风险点防控措施关联表
 */
@Data
public class ProjectCpLibraryRiskRequest {
    private String id;
    private String cplId;
    private String rpmId;
}
