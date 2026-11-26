package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.ProProjectRegisterRequest;
import com.jpmsoft.projectProducer.pojo.entity.project.ProProjectRegister;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * 项目登记主子表业务服务。
 */
public interface IProProjectRegisterService extends IService<ProProjectRegister> {

    /**
     * 在同一事务中新增或修改项目登记及参建单位，并同步单据信息、附件。
     *
     * @param request 整表单保存参数
     * @param user    当前登录用户
     * @return 保存后的登记ID
     */
    BaseResponse createOrUpdateProProjectRegister(ProProjectRegisterRequest request, LogUserInfoVo user);

    /**
     * 按项目名称、代码、类型、投资主体、政府投资标识及审批状态分页查询。
     *
     * @param request 筛选及分页参数
     * @return 当前页记录和总条数
     */
    BaseResponse getProProjectRegisterList(ProProjectRegisterRequest request);

    /**
     * 查询项目登记及参建单位、附件详情。
     *
     * @param id 登记ID
     * @return 登记详情，记录不存在时返回失败
     */
    BaseResponse getProProjectRegisterById(String id);

    /**
     * 删除允许编辑的项目登记、参建单位及单据记录。
     *
     * @param id 登记ID
     * @return 删除结果
     */
    BaseResponse deleteProProjectRegister(String id);

    /**
     * 统计当前用户可见的全部年度项目登记。
     *
     * @return 总数、审核中和已审核数量
     */
    BaseResponse getRegistrationStatistics();
}
