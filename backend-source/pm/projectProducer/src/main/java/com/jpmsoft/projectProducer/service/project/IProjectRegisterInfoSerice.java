package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectRegisterInfo;

public interface IProjectRegisterInfoSerice extends IService<ProjectRegisterInfo> {
    /**
     * 预校验同项目能否发起申报或论证。
     *
     * @param request 登记ID、业务类型、论证目的及可选的当前单据ID
     * @return 成功或具体冲突原因
     */
    BaseResponse checkProjectRegisterInfo(ProjectRegisterInfoModel request);

    BaseResponse createOrUpdatePreProjectRegisterInfo(ProjectRegisterInfoModel request);

    BaseResponse deletePreProjectRegisterInfo(ProjectRegisterInfoModel request);

    BaseResponse getPreProjectRegisterInfoList(ProjectRegisterInfoModel request,String uId);

    BaseResponse getPreProjectRegisterInfoById(String id);

    BaseResponse modifyPreProjectRegisterInfo(ProjectRegisterInfoModel request);

    BaseResponse getPreProjectRegisterInfoModifyInfo(ProjectRegisterInfoModel request, String uid);

    /**
     * 项目申报流程结束后同步到项目表 Pre_Project，供前端/其他服务调用
     *
     * @param id 项目申报信息 id
     */
    BaseResponse syncPreProject(String id);

    /**
     * 项目申报同步项目表补偿：查询流程已结束但未同步的记录并重新同步，供定时任务调用
     */
    BaseResponse syncPreProjectCompensate();

    /**
     * 按类型统计当前用户可见的最新申报或论证，同一登记已有申报时不再计入论证。
     *
     * @param registerType 必填，01项目申报、02项目论证
     * @return 总数、审批中和流程结束数量
     */
    BaseResponse getDeclarationStatistics(String registerType);
}
