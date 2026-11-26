package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.DepartmentDto;
import com.jpmsoft.userProducer.pojo.entity.SysDepartment;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;

import java.util.List;


public interface ISysDepartmentService extends IService<SysDepartment> {

    BaseResponse getDepartmentTreeList(DepartmentDto request);

    /**
     * 北京工行专用
     *
     * @param request
     * @return
     */
    BaseResponse getBjDepartmentTreeList(DepartmentDto request);

    BaseResponse CUDDepartment(DepartmentDto request);

    BaseResponse OnDropDepartment(DepartmentDto request);

    List<SysDepartment> getDepartmentList(String teId);

    List<String> getUserDepartment(LogUserInfoVo user);

    BaseResponse getUserDepartmentSelect(String uId);

    List<Object> getUserDepartmentSelectByUid(String uId);

    /**
     * 获取用户所有组织机构及其下机组织机构ID
     *
     * @param uId
     * @return
     */
    List<String> getAllChildDepartment(String uId);

    /**
     * 获取用户所在组织机构及其下级组织机构的单位部门的单位ID
     *
     * @param uId
     * @return
     */
    List<String> getAllChildDepCompany(String uId);

    /**
     * 获取所有单位部门以及其下级部门ID
     *
     * @param cId
     * @return
     */
    List<String> getAllChildDepartmentByCid(String cId);

    /**
     * 用户ID获取其所在部门
     *
     * @param uId
     * @return
     */
    BaseResponse getUserDepartmentByUid(String uId);

    /**
     * 用户ID获取其所在部门ID
     *
     * @param uId
     * @return
     */
    BaseResponse getUserDepIdsByUid(String uId);

    /**
     * 根据项目id获取所属部门的所属单位id信息
     *
     * @param pId
     * @return
     */
    BaseResponse getDepartmentCidByPid(String pId);

    BaseResponse getLowCodeDepartmentTreeList(DepartmentDto request);

    BaseResponse getLowCodeDepartmentTreeListByLevel(DepartmentDto request);

    BaseResponse getLowCodeDepartmentList(DepartmentDto request);

    /**
     * 查询用户所有上级部门为单位部门的部门ID
     *
     * @param departmentIds
     * @return
     */
    BaseResponse getUserCompanyDep(List<String> departmentIds,String token);
}
