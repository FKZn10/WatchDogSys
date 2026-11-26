package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PowerDto;
import com.jpmsoft.userProducer.pojo.entity.SysPower;

import java.util.List;

public interface ISysPowerService extends IService<SysPower> {

    BaseResponse getPowerTreeList(String title, String powerCode, String teId);

    /**
     * CUD操作
     *
     * @param request
     * @return
     */
    BaseResponse CUDPower(PowerDto request);

    /**
     * @param filterAdmin 是否过滤掉仅限admin操作的菜单(powerAdmin)
     * @return
     */
    BaseResponse getPowerTree(Boolean filterAdmin, String teId);

    @Deprecated
    Integer isParentPower(String powerId, String status);

    /**
     * 获取所有状态为启用的数据
     *
     * @return
     */
    List<SysPower> getAllPowerList(String teId);

    BaseResponse getVueRoute(String powerCode, String teId);

    /**
     * 获取用户资源菜单
     * 排除后端管理节点
     *
     * @param powerCode
     * @return
     */
    BaseResponse getUserVueRoute(String powerCode, String token);

    /**
     * 上下移动排序
     *
     * @param powerId
     * @param action
     * @return
     */
    BaseResponse upOrDownPowerSort(String powerId, String action, String teId);


    List<SysPower> allPowerList(String teId);


    /**
     * 验证编码对应是否是根节点数据
     *
     * @param pwCode
     * @return
     */
    Boolean checkIsSubSystem(String pwCode);


    /**
     * 获取系统类型的菜单
     *
     * @return
     */
    BaseResponse getSystemPower(String teId);


    /**
     * 根据权限ID+上级权限编码
     * 获取其按钮权限（同时标记是否勾选）
     *
     * @param roleId
     * @param code
     * @return
     */
    BaseResponse getPowerCheckByRoleAndCode(String roleId, String code);

    /**
     * 根据上级删除所有下级
     *
     * @param code
     * @return
     */
    BaseResponse deleteByParentCode(String code);


    BaseResponse  BatchSavePower(List<PowerDto>  request,String teId);
}
