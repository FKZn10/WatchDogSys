package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityLevelDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityLevel;

import java.util.List;

/**
 * 安全责任层级服务。
 */
public interface ISafeResponsibilityLevelService extends IService<SafeResponsibilityLevel> {
    /**
     * 新增或修改责任层级。
     */
    BaseResponse createOrUpdateSafeResponsibilityLevel(SafeResponsibilityLevelDTO request, String uid, String uname);

    /**
     * 筛选责任层级树，命中节点保留全部祖先节点。
     */
    BaseResponse getSafeResponsibilityLevelTree(SafeResponsibilityLevelDTO request);

    /**
     * 删除空责任层级。
     */
    BaseResponse deleteSafeResponsibilityLevel(SafeResponsibilityLevelDTO request, String uid, String uname);

    /**
     * 查询指定体系的层级树。
     */
    List<SafeResponsibilityLevelDTO> getLevelTreeBySystemId(String systemId, String keyword);

    /**
     * 删除体系下全部层级、岗位和职责。
     */
    void deleteBySystemId(String systemId);

    /**
     * 复制体系层级、岗位和职责。
     */
    void copyBySystem(String sourceSystemId, String targetSystemId, String uid, String uname);
}
