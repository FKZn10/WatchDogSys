package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionUserDto;
import com.jpmsoft.userProducer.pojo.dto.SysPositionUserDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionUser;

import java.util.List;


public interface ISysPositionUserService extends IService<SysPositionUser> {

    /**
     * 新增or删除岗位用户关联信息
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrDeletePU(SysPositionUserDto request);

    BaseResponse updatePositionUser(SysPositionUserDto request);

    /**
     * 上移下移序号
     *
     * @param puId
     * @param action
     * @return
     */
    BaseResponse updateSortUpOrDown(String puId, String dId, String action);

    /**
     * 删除岗位 关联删除
     *
     * @param pId
     */
    void deletePositionUserByPid(String pId);


    /**
     * 查询用户岗位
     *
     * @param request
     * @return
     */
    BaseResponse GetUserPositionList(PositionUserDto request);


    /**
     * 组织机构部门ID获取用户ID
     *
     * @param dIds
     * @return
     */
    List<String> getUserIdsByDids(List<String> dIds);

    /**
     * 设置主部门
     *
     * @param puId   用户岗位关系ID
     * @param enable 是否为主部门
     * @return
     */
    BaseResponse setMainDep(String puId, Boolean enable);

    /**
     * 设置主负责人
     *
     * @param puId   用户岗位关系ID
     * @param dId    部门ID
     * @param enable 是否为主部门
     * @return
     */
    BaseResponse setMainDuty(String puId,String dId, Boolean enable);

    /**
     * 设置为负责人
     *
     * @param puId
     * @param enable
     * @return
     */
    BaseResponse setDuty(String puId, Boolean enable);
}
