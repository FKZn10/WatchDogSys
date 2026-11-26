package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysCommonUserDTO;
import com.jpmsoft.userProducer.pojo.entity.SysCommonUser;

import java.util.List;

/**
 * 用户常用功能及个性化设置
 */
public interface ISysCommonUserService extends IService<SysCommonUser> {
    /**
     * 保存当前用户的常用功能设置。
     *
     * @param request 目标及个人设置
     * @return 保存后的设置
     */
    BaseResponse createOrUpdateSysCommonUser(SysCommonUserDTO request);

    /**
     * 批量添加或修改当前用户的常用功能，任一项失败时回滚整批。
     *
     * @param requests 待添加或修改的功能及个人设置
     * @return 保存后的个人设置列表
     */
    BaseResponse batchCreateOrUpdateSysCommonUser(List<SysCommonUserDTO> requests);

    /**
     * 分页获取当前用户常用功能；尚未编辑时直接读取全局配置。
     *
     * @param request page、size及可选筛选条件
     * @return 当前页和筛选后的总数
     */
    BaseResponse getSysCommonUserList(SysCommonUserDTO request);

    /**
     * 按列表项ID读取当前用户设置。
     *
     * @param id 个人设置ID或全局配置ID
     * @return 设置详情
     */
    BaseResponse getSysCommonUserById(String id);

    /**
     * 从当前用户列表中移除功能。
     *
     * @param id 个人设置ID或全局配置ID
     * @return 移除结果
     */
    BaseResponse deleteSysCommonUserById(String id);

    /**
     * 切换当前用户对功能的显示状态。
     *
     * @param id 个人设置ID或全局配置ID
     * @return 更新后的设置
     */
    BaseResponse switchSysCommonUserById(String id);

    /**
     * 交换当前用户列表中两个功能的位置。
     *
     * @param id      第一个列表项ID
     * @param otherId 第二个列表项ID
     * @return 交换结果
     */
    BaseResponse changeSort(String id, String otherId);

    /**
     * 在同一置顶分组内上移或下移当前用户的常用功能。
     *
     * @param id     当前列表项ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    BaseResponse upOrDownSysCommonUserSort(String id, String action);
}
