package com.jpmsoft.projectProducer.service.NewOA;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.NewOA.PreProjectRegister_NewOaRequest;
import com.jpmsoft.projectProducer.pojo.entity.NewOA.PreProjectRegister_NewOa;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface IPreProjectRegister_NewOaService extends IService<PreProjectRegister_NewOa> {

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdatePreProjectRegister_NewOa(PreProjectRegister_NewOaRequest request);

    /**
     * 删除
     *
     * @param request
     * @return
     */
    BaseResponse deletePreProjectRegister_NewOa(PreProjectRegister_NewOaRequest request);

    /**
     * 审批
     *
     * @param request
     * @return
     */
    BaseResponse approvalPreProjectRegister_NewOa(PreProjectRegister_NewOaRequest request);

    /**
     * 分页查询项目注册
     *
     * @param request
     * @return
     */
    BaseResponse getPreProjectRegister_NewOaList(PreProjectRegister_NewOaRequest request);

    /**
     * 分页查询项目注册
     * 统计
     * @param request
     * @return
     */
    BaseResponse getPreProjectRegister_NewOaStatisticsList(PreProjectRegister_NewOaRequest request);

    /**
     * 组织机构部门鉴权
     */
    BaseResponse getPreProjectRegister_NewOaByDepCompany(PreProjectRegister_NewOaRequest request, LogUserInfoVo user);

    /**
     * ID查询单条项目注册
     *
     * @param request
     * @return
     */
    BaseResponse getPreProjectRegister_NewOaById(PreProjectRegister_NewOaRequest request);

    /**
     * ID查询单条项目注册
     *
     * @param request
     * @return
     */
    BaseResponse getPreProjectRegister_NewOaWYById(PreProjectRegister_NewOaRequest request);
}
