package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectChangeNameRequest;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectChangeName;

public interface IPreProjectChangeNameService extends IService<PreProjectChangeName> {
    /**
     * 新增或者修改
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdateProChangeName(PreProjectChangeNameRequest request);

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    BaseResponse getPreProjectChangeNameByPage(PreProjectChangeNameRequest request);

    /**
     * ID查询
     *
     * @param pcnId
     * @return
     */
    BaseResponse getPreProjectChangeNameById(String pcnId);

    /**
     * 删除
     *
     * @param request
     * @return
     */
    BaseResponse deleteProChangeNameById(PreProjectChangeNameRequest request);


    /**
     * 修改审批状态
     *
     * @param request
     * @return
     */
    BaseResponse updateProChangeNameFlowState(PreProjectChangeNameRequest request);

    /**
     * 获取项目曾用名List
     *
     * @param pId
     * @return
     */
    BaseResponse getProjectNameUsedBeforeListById(String pId);

    /**
     * 审批通过更新项目名称
     *
     * @param pcnId
     * @return
     */
    BaseResponse approve2RefreshProjectName(String pcnId);
}
