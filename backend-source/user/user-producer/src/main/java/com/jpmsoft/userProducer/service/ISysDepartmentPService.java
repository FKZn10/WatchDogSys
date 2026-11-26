package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.UserDepartmentPRequest;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.userProducer.pojo.dto.DepartmentPDto;
import com.jpmsoft.userProducer.pojo.entity.SysDepartmentP;

import java.util.List;


public interface ISysDepartmentPService extends IService<SysDepartmentP> {

    List<SysDepartmentP> selectByPid(String pid,String parentId);

    BaseResponse getDepartment_PTreeList(DepartmentPDto request);

    BaseResponse CUDDepartment_P(DepartmentPDto request);

    BaseResponse OnDropDepartment_P(DepartmentPDto request);

    void insert(SysDepartmentP sysDepartmentP);

    Boolean havingRecordByPid(String pId);

    /**
     * 复制项目组织机构
     *
     * @param copyFrom 来源项目ID
     * @param copyTo   目标项目ID
     * @return
     */
    BaseResponse copyProjectDepartmentP(String copyFrom, String copyTo);

    /**
     * 用户ID获取项目部门下的所有项目ID
     *
     * @param uId
     * @return
     */
    List<String> getUserAllSysDepPProject(String uId);

    BaseResponse AddUserDepartmentP(UserDepartmentPRequest request);

    /**
     * 删除指定业主单位
     *
     * @param request
     * @return
     */
    BaseResponse deleteOwnerDepartmentP(DepartmentPDto request);

    /**
     * 同步业主单位
     *
     * @param pId
     * @param pdCode
     * @param ownerUnit
     * @return
     */
    BaseResponse syncPpOwnerUnit(String pId, String pdCode, String ownerUnit);

    /**
     * 同步部分项目部门到所有项目
     *
     * @param request
     * @return
     */
    BaseResponse syncDepartmentToProject(DepartmentPDto request);

    /**
     * 根据项目ID和单位IDs获取项目部门编码
     *
     * @param pId
     * @param cIds
     * @return
     */
    BaseResponse getPdCodeByCIds(String pId, String cIds);

    BaseResponse syncSafeResponsibilityPostPerson(SafeResponsibilityPostPersonDTO request);
}
