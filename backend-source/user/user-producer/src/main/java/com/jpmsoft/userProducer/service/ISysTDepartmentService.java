package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.ImportTDepartmentDto;
import com.jpmsoft.userProducer.pojo.dto.TDepartmentDto;
import com.jpmsoft.userProducer.pojo.entity.SysTDepartment;


public interface ISysTDepartmentService extends IService<SysTDepartment> {

    /**
     * 获取部门模板树
     *
     * @return
     */
    BaseResponse getT_Department_TreeList(String cId, String toId);

    /**
     * 新增修改删除部门模板
     *
     * @param request
     * @return
     */
    BaseResponse CUDT_Department(TDepartmentDto request);

    /**
     * 同级项目部门拖动改变排序
     *
     * @param request
     * @return
     */
    BaseResponse OnDropT_Department(TDepartmentDto request);


    /**
     * 导入项目部门模板
     *
     * @param request
     * @return
     */
    BaseResponse ImportT_Department(ImportTDepartmentDto request);

    /**
     * 同步组织机构模板
     *
     * @param request
     * @return
     */
    BaseResponse syncTDepartment(ImportTDepartmentDto request);

    boolean haveInfoWithTOrgan(String toId);

    /**
     * 同步部分项目部门模板到指定模板
     *
     * @param request
     * @return
     */
    BaseResponse syncTDepartmentToOrganization(ImportTDepartmentDto request);
}
