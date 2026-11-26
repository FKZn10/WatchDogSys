package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionPDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionP;

import java.util.List;


public interface ISysPositionPService extends IService<SysPositionP> {


    BaseResponse getProjectPositionP(String pId, String bbOrJt);

    List<SysPositionP> selectPositionPByPdId(String pdId);

    BaseResponse GetPosition_PList(PositionPDto request);

    BaseResponse getPositionPByUid(String uId, String pId);

    /**
     * 新增or修改项目岗位信息
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdatePosition_P(PositionPDto request);


    /**
     * 删除项目岗位
     *
     * @param request
     * @return
     */
    BaseResponse DeletePosition_P(PositionPDto request);


    /**
     * 根据项目部门ID获取项目岗位Select
     *
     * @param request
     * @return
     */
    BaseResponse GetP_PSelectByDID(PositionPDto request);


    void insert(SysPositionP sysPositionP);

    List<SysPositionP> getPositionPByPdId(String pdId);

    BaseResponse getPositionPByRole(String roleId);
}
