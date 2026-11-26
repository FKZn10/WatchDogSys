package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionDto;
import com.jpmsoft.userProducer.pojo.entity.SysPosition;

import java.util.List;


public interface ISysPositionService extends IService<SysPosition> {


    /**
     * 根据部门获取岗位信息
     *
     * @param request
     * @return
     */
    BaseResponse GetPositionList(PositionDto request);

    /**
     * 根据部门ID获取岗位Select
     *
     * @param request
     * @return
     */
    BaseResponse GetPSelectByDID(PositionDto request);

    /**
     * 新增or修改岗位信息
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdatePosition(PositionDto request);

    /**
     * 删除岗位
     *
     * @param request
     * @return
     */
    BaseResponse DeletePosition(PositionDto request);


    List<SysPosition> GetPositionList();

    List<SysPosition> GetPositionList(List<String> idlist);

    /**
     * 设置责任岗位
     *
     * @param pId
     * @param enable
     * @return
     */
    BaseResponse setDuty(String pId, boolean enable);

    /**
     * 校验部门下是否有岗位信息
     *
     * @param did
     * @return
     */
    boolean departmentHavPosition(String did);


    BaseResponse getPositionByRole(String roleId);
}
