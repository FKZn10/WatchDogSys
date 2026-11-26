package com.jpmsoft.projectProducer.pojo.vo.constructionProcedure;

import com.jpmsoft.common.model.response.proCp.ProjectCpDtlResponse;
import com.jpmsoft.common.model.response.proCp.ProjectCpResponse;
import lombok.Data;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/7/1 8:55
 */
@Data
public class ProjectCpAndDtlVo extends ProjectCpResponse {

    private static final long serialVersionUID = -6490389550792701567L;

    private List<ProjectCpDtlResponse> proCpDtls;
}
