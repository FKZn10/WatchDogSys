package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectManageRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectManage;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * 项目管理大纲体系管理
 *
 * @author tianhelin
 * @date 2025/6/11
 */
public interface IOutlineProjectManageService extends IService<OutlineProjectManage> {
    /**
     * 获取项目管理大纲管理分页
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineProjectManagePage(OutlineProjectManageRequest request);

    /**
     * 获取项目管理详情
     *
     * @param id
     * @return
     */
    BaseResponse getOutlineProjectManageById(String id);

    OutlineProjectManageResponse selectOutlineProjectManageById(String id);

    /**
     * 创建或更新项目管理
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createOrUpdateOutlineProjectManage(OutlineProjectManageRequest request, String token);

    /**
     * 删除项目管理
     *
     * @param id
     * @param token
     * @return
     */
    BaseResponse deleteOutlineProjectManageById(String id, String token);

    /**
     * 批量更新项目状态
     *
     * @param request
     * @return
     */
    BaseResponse batchUpdateProjectManageStatus(OutlineProjectManageRequest request);

    /**
     * 批量更新项目管理启用状态
     *
     * @param request
     * @return
     */
    BaseResponse batchUpdateProjectManageOpmStatus(OutlineProjectManageRequest request);

    /**
     * 将模板转化成项目管理大纲
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse coverSystemManageToProjectManage(OutlineProjectManageRequest request, String token);

    /**
     * 获取项目对应的项目管理大纲
     *
     * @param pId
     * @param opmCode
     * @return
     */
    BaseResponse getOutlineProjectManageByPid(String pId, String opmCode);

    /**
     * 获取项目对应的项目管理大纲列表
     *
     * @param pId
     * @return
     */
    BaseResponse getOutlineProjectManageByPidList(String pId);

    /**
     * 刷新最后更新时间
     *
     * @param opmId
     * @param user
     */
    void refreshLastUpdateTime(String opmId, LogUserInfoVo user);

    /**
     * 获取前置功能未完善的模板
     *
     * @param opmId
     * @return
     */
    BaseResponse getPreTypeVerifyIds(String opmId);

    /**
     * 获取大纲台账统计
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineProjectLedgerCount(OutlineProjectManageRequest request);

    /**
     * 获取大纲台账分页
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineProjectLedgerPage(OutlineProjectManageRequest request);
}
