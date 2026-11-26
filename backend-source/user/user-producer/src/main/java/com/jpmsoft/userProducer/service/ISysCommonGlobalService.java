package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysCommonGlobalDTO;
import com.jpmsoft.userProducer.pojo.entity.SysCommonGlobal;

/**
 * 全局默认功能菜单或外部系统入口
 */
public interface ISysCommonGlobalService extends IService<SysCommonGlobal> {
    /**
     * 创建或更新管理员配置的全局常用功能。
     *
     * @param request 功能目标和展示设置
     * @return 保存后的配置
     */
    BaseResponse createOrUpdateSysCommonGlobal(SysCommonGlobalDTO request);

    /**
     * 分页查询全局常用功能，并补齐菜单或外部入口的展示信息。
     *
     * @param request 分页参数及可选的ID、目标、置顶和状态筛选条件
     * @return 按置顶和排序值排列的当前页及总数
     */
    BaseResponse getSysCommonGlobalList(SysCommonGlobalDTO request);

    /**
     * 按ID读取全局常用功能设置。
     *
     * @param id 配置ID
     * @return 配置详情
     */
    BaseResponse getSysCommonGlobalById(String id);

    /**
     * 删除全局配置，保留用户已保存的个人设置。
     *
     * @param id 配置ID
     * @return 删除结果
     */
    BaseResponse deleteSysCommonGlobalById(String id);

    /**
     * 切换全局配置的发布状态。
     *
     * @param id 配置ID
     * @return 更新后的配置
     */
    BaseResponse switchSysCommonExternalById(String id);

    /**
     * 交换两个全局配置在列表中的位置。
     *
     * @param id      第一个配置ID
     * @param otherId 第二个配置ID
     * @return 交换结果
     */
    BaseResponse changeSort(String id, String otherId);

    /**
     * 在同一置顶分组内上移或下移全局常用功能。
     *
     * @param id     当前配置ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    BaseResponse upOrDownSysCommonGlobalSort(String id, String action);
}
