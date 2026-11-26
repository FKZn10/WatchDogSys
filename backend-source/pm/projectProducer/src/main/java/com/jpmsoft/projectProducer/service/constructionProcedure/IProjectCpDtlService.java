package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.BatchUpdatePerson;
import com.jpmsoft.common.model.request.TimeFrameRequest;
import com.jpmsoft.common.model.request.proCp.ProCpDtlTemporaryRequest;
import com.jpmsoft.common.model.request.proCp.ProjectCpDtlRequest;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.common.model.response.proCp.ProjectCpDtlResponse;
import com.jpmsoft.projectProducer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibrary;
import com.jpmsoft.projectProducer.pojo.request.ProjectCpInfoRequest;

import java.util.List;

public interface IProjectCpDtlService extends IService<ProjectCpDtl> {

    List<ProjectCpDtl> generateProjectCpWithUnCpProject(List<ProjectCpLibrary> libraries, String cpId);

    /**
     * 创建或修改
     *
     * @param proCplTemolateDtls 模板详情集合
     * @param cpId               项目建设程序ID
     */
    void createOrUpdateProCpDtl(List<ProjectCpLibraryRequset> proCplTemolateDtls, String cpId);

    /**
     * 根据项目建设程序ID删除相关数据
     *
     * @param cpId 项目建设程序ID
     */
    void deleteProCpDtlByCpId(String cpId);

    /**
     * 重设上下级状态
     *
     * @param data
     * @param resetFile 是否同步file表状态
     */
    void resetAllCpDtlState(List<ProjectCpDtl> data, boolean resetFile);

    /**
     * 根据项目建设程序ID获取树形数据
     *
     * @param cpId
     * @return
     */
    BaseResponse getProCpDtlTreeByCpId(String cpId, String teId);

    /**
     * 城建 根据项目建设程序ID获取数据
     *
     * @param cpId
     * @return
     */
    BaseResponse getProCpDtlTreeByCpIdNJCJ(String cpId, String teId);

    /**
     * 提交办理结果
     *
     * @param request
     * @return
     */
    BaseResponse submitProcessingResults(ProjectCpDtlRequest request);

    /**
     * 判断是否已经存在File表 用户识别当前成功上传是新增操作还是修改操作
     *
     * @param cpdId
     * @return
     */
    BaseResponse proCpDtlHaveSysFile(String cpdId);

    /**
     * 获取
     *
     * @param cpId
     * @return
     */
    BaseResponse getProCpDtlIdListByCpId(String cpId);


    /**
     * 根据项目获取其已勾选的程序
     * ID列表
     *
     * @param pId
     * @return
     */
    BaseResponse getProCpDtlIdListByPid(String pId);


    /**
     * 建设程序统计时
     * 所有手续
     * 已完成 未完成
     * 超时未完成
     *
     * @param request
     * @return
     */
    BaseResponse getProCpDtlListByCpId(ProjectCpDtlRequest request);

    BaseResponse getProCpDtlListByStatistics(ProjectCpDtlRequest request);

    List<ProjectCpDtl> getProCpDtlByCpIds(List<String> cpIds);

    /**
     * 办理统计
     * 所有手续
     * 已完成 未完成
     * 超时未完成
     * 分页查询
     *
     * @param request
     * @return
     */
    BaseResponse getProCpDtlByUser(ProjectCpDtlRequest request);


    /**
     * 项目ID获取建设项目详情
     *
     * @param pId
     * @return
     */
    BaseResponse getProCpDtlByPid(String pId, String teId);

    /**
     * 项目ID获取建设项目详情
     * 带文件信息
     *
     * @param request
     * @return
     */
    BaseResponse getProCpDtlTreeByPid(ProjectCpDtlRequest request);

    /**
     * ID删除建设项目详情
     *
     * @param cpDtlId
     * @return
     */
    BaseResponse deleteProCpDtlByCpDtlId(String cpDtlId);

    /**
     * 修改单条信息
     *
     * @return
     */
    BaseResponse updateProCpDtlByCpDtlId(ProjectCpDtlRequest request);


    /**
     * 库详情ID查询建设详情总数
     *
     * @param cplId
     * @return
     */
    Integer getProCpDtlCountByCplId(String cplId);

    /**
     * 建设程序ID 查询非新建的建设程序详情数量
     *
     * @param cpId
     * @return
     */
    Integer getProCpDtlCountByCpId(String cpId);

    /**
     * 更改子节点的所有父节点
     *
     * @param entity 当前节点状态更改后的实体
     * @param dtlId
     * @param state
     */
    void updateProCpDtlAllParentState(ProjectCpDtl entity, String dtlId, Integer state,String cleanCreateTime);


    /**
     * 项目程序与详情数量统计
     *
     * @return
     */
    BaseResponse statisticalProCpAndDtl(TimeFrameRequest request);

    /**
     * 根据时间范围统计建设项目详情
     *
     * @param request
     * @return
     */
    BaseResponse statisticalProCpDtlByTimeFrame(TimeFrameRequest request);

    /**
     * 项目建设程序完成情况列表
     *
     * @param request
     * @return
     */
    BaseResponse getProCpDtlComplateList(ProjectCpDtlRequest request);

    /**
     * 根据用户t用户id获取
     *
     * @param request
     * @return
     */
    BaseResponse getProCpDtlTemporaryListByuid(ProCpDtlTemporaryRequest request);

    /**
     * 批量修改责任人
     *
     * @param request
     * @return
     */
    BaseResponse batchUpdateCpDtlPerson(BatchUpdatePerson request);

    /**
     * 批量修改办理人
     *
     * @param request
     * @return
     */
    BaseResponse batchUpdateCpDtlCreateUser(BatchUpdatePerson request);


    /**
     * 批量修改责任人
     * 查询项目部门的项目经理
     *
     * @param request
     * @return
     */
    BaseResponse batchUpdateCpDtlPersonWithDepPm(BatchUpdatePerson request, String token);

    /**
     * 批量修改办理人
     * 查询项目部门的项目经理
     *
     * @param request
     * @return
     */
    BaseResponse batchUpdateCpDtlCreateUserWithDepPm(BatchUpdatePerson request, String token);


    /**
     * 更新可用状态
     *
     * @param cpdId
     * @param cpdStatus 0不可用 1可用
     */
    void updateCpDtlStatus(String cpdId, Integer cpdStatus);

    /**
     * ID查询建设程序详情
     *
     * @param cpdId
     * @return
     */
    BaseResponse getCpDtlById(String cpdId);


    /**
     * 同步建设程序父级节点的办理状态
     *
     * @param cpdId
     * @return
     */
    BaseResponse setCpDtlStatus(String cpdId);

    /**
     * 批量修改项目建设程序详情成果上传附件类型
     *
     * @param request
     * @return
     */
    BaseResponse updateFileTypeBath(LibraryFileTypeDTO request);


    List<ProjectCpDtlResponse> getProjectCpDtlByCpIds(List<String> cpIds);

    List<ProjectCpDtl> getAllByCpId(String cpId);

    ProjectCpDtl getByCpAndCpl(String cpId,String cplId);

    BaseResponse dealWith(String id);
    BaseResponse dealWithNotPlan(String id);

    BaseResponse getInfo(ProjectCpInfoRequest request);

    BaseResponse createProjectCpInfo(ProjectCpDtl projectCpDtl);

    BaseResponse cancelProjectCpInfo(String cpdId, String cprId);

    BaseResponse  createProjectCpDtlBath(List<ProjectCpDtl> list);

    BaseResponse selectProjectCpRprByCpdId(String cpdId,String uId,String uType);

    BaseResponse getGantSectionListByPid(String pId);

    BaseResponse updateCpdTimeByCprId(ProjectCpDtlRequest request);

    BaseResponse getAllCpDtlList(TimeFrameRequest request);
}
