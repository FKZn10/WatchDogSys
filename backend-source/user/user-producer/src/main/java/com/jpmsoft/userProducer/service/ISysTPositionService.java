package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.TPositionDto;
import com.jpmsoft.userProducer.pojo.entity.SysTPosition;

import java.util.List;


public interface ISysTPositionService extends IService<SysTPosition> {

    /**
     * 根据项目部门模板获取项目岗位模板列表
     *
     * @param request
     * @return
     */
    BaseResponse GetT_PositionList(TPositionDto request);

    /**
     * 新增or修改项目岗位模板信息
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdateT_Position(TPositionDto request);


    /**
     * 删除项目岗位模板
     *
     * @param request
     * @return
     */
    BaseResponse DeleteT_Position(TPositionDto request);


    List<SysTPosition> getTPositionByTdId(String tdId);

}
