package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.fileUpload.pojo.dto.ProProjectCodeDto;
import com.jpmsoft.fileUpload.pojo.entity.PreProject;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IPreProjectService extends IService<PreProject> {

    List<Map> getPreProjectByIds(List<SysTableColumn> columns, List<String> ids);

    List<Map> getContractByIds(List<SysTableColumn> columns, List<String> ids);

    List<PreProject> getPreProjectByCodes(List<String> codes);

    List<PreProject> getApprovaledPreProjectByCodes(List<String> codes);

    /**
     * 赋值工程报告的PID和PName值
     * 以及创建人名称
     *
     * @param datas
     * @param uId
     * @param type  1周报 2月报 3年度计划
     * @return
     */
    BaseResponse assignOaProReportPid(List<OaProjectReportModel> datas, String uId, Integer type);

    /**
     * 发改委批复项目代码批量查询项目ID
     *
     * @param pProjectCodes
     * @return
     */
    List<ProProjectCodeDto> getPidByProjectCodes(List<String> pProjectCodes,List<String> pCodes);

    /**
     * 校验当前年度的季度是否已存在季报
     *
     * @param year
     * @param quarter
     * @return
     */
    Boolean havingOaQuarterlyReport(Integer year, Integer quarter);

    ResponseEntity<byte[]> getProjectListExcel(PreProjectRequest request, String token, HttpServletResponse response);

    ResponseEntity<byte[]> getContractListExcel(ConContractRequest request, String token, HttpServletResponse response);

    /**
     * 省试点项目 导出
     */
    ResponseEntity<byte[]> getProvPilotProjectExcel(ProvPilotProjectModel request, String token, HttpServletResponse response);

    /**
     * 省试点企业 导出
     */
    ResponseEntity<byte[]> getProvPilotEnterpriseExcel(ProvPilotEnterpriseModel request, String token, HttpServletResponse response);
}
