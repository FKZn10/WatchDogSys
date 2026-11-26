package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysCommonExternalDTO;
import com.jpmsoft.userProducer.pojo.entity.SysCommonExternal;

/**
 * 常用功能外部系统入口
 */
public interface ISysCommonExternalService extends IService<SysCommonExternal> {
    /**
     * 按父级关系获取外部系统入口树。
     *
     * @param request 可选筛选条件
     * @return 按排序值排列的层级入口节点
     */
    BaseResponse getSysCommonExternalTree(SysCommonExternalDTO request);

    /**
     * 创建或更新外部系统入口并校验父级关系。
     *
     * @param request 入口信息
     * @return 保存后的入口
     */
    BaseResponse createOrUpdateSysCommonExternal(SysCommonExternalDTO request);

    /**
     * 按主键读取外部系统入口详情。
     *
     * @param id 入口ID
     * @return 入口详情
     */
    BaseResponse getSysCommonExternalById(String id);

    /**
     * 删除无子节点且未被常用功能引用的入口。
     *
     * @param id 入口ID
     * @return 删除结果
     */
    BaseResponse deleteSysCommonExternalById(String id);

    /**
     * 切换入口启用状态，不改动下级节点自身状态。
     *
     * @param id 入口ID
     * @return 更新后的入口
     */
    BaseResponse switchSysCommonExternalById(String id);

    /**
     * 在同级入口中上移或下移指定节点。
     *
     * @param id     当前入口ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    BaseResponse upOrDownSysCommonExternalSort(String id, String action);
}
