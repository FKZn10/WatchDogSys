package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectRegisterRequest;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegister;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface IPreProjectRegisterService extends IService<PreProjectRegister> {

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdatePreProjectRegister(PreProjectRegisterRequest request);

    /**
     * 删除
     *
     * @param request
     * @return
     */
    BaseResponse deletePreProjectRegister(PreProjectRegisterRequest request);

    /**
     * 审批
     *
     * @param request
     * @return
     */
    BaseResponse approvalPreProjectRegister(PreProjectRegisterRequest request);

    /**
     * 分页查询项目注册
     *
     * @param request
     * @return
     */
    BaseResponse getPreProjectRegisterList(PreProjectRegisterRequest request);

    /**
     * 分页查询项目注册
     * 统计
     * @param request
     * @return
     */
    BaseResponse getPreProjectRegisterStatisticsList(PreProjectRegisterRequest request);

    /**
     * 组织机构部门鉴权
     */
    BaseResponse getPreProjectRegisterByDepCompany(PreProjectRegisterRequest request, LogUserInfoVo user);

    /**
     * ID查询单条项目注册
     *
     * @param request
     * @return
     */
    BaseResponse getPreProjectRegisterById(PreProjectRegisterRequest request);

    /**
     * ID查询单条项目注册
     *
     * @param request
     * @return
     */
    BaseResponse getPreProjectRegisterWYById(PreProjectRegisterRequest request);
}
