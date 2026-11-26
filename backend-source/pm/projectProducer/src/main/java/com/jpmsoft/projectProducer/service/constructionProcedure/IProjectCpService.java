package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCp;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;


public interface IProjectCpService extends IService<ProjectCp> {

    ProjectCp selectByCpdId(String cpdId);

    BaseResponse projectApprove2generateCp(String pid, LogUserInfoVo user);

    /**
     * 给没有项目建设程序的项目生成全量项目建设程序库的项目建设程序
     *
     * @return
     */
    BaseResponse generateProjectCpWithUnCpProject(LogUserInfoVo user);

    BaseResponse createOrUpdateProjectCp(ProjectCpRequest request);

    BaseResponse getProCpList(ProjectCpRequest request, String uId);

    /**
     * ID删除记录
     *
     * @param cpId
     * @return
     */
    BaseResponse deleteProCpByCpId(String cpId);

    /**
     * 建设程序统计
     *
     * @param request
     * @return
     */
    BaseResponse statisticalProCp(ProjectCpRequest request);

    /**
     * 办理统计
     *
     * @param request
     * @return
     */
    BaseResponse statisticalUserProCp(ProjectCpRequest request);

    Boolean getProjectCpBycpltId(String cpltId);

    BaseResponse getCpIdByPid(String pId);

    BaseResponse getProjectCpByCpId(String cpId);


    /**
     * 获取项目建设程序以及详情列表
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getProCpAndDtlList(ProjectCpRequest request, String uId);


    ProjectCp selectByPid(String pId);

    /**
     * 根据Cpl_Id和Plan_Id查询是否需要多次办理
     *
     * @param cpdCplId
     * @param pId
     * @return
     */
    BaseResponse getTaskIsManyByCplIdPlanId(String cpdCplId, String pId);

    BaseResponse getProCpLedgerNJCJ(ProjectCpRequest request);

    /**
     * 根据项目id和项目建设配置生成项目配置
     *
     * @param request
     * @return
     */
    BaseResponse generateProjectCpWithPid(ProjectCpRequest request);
}
