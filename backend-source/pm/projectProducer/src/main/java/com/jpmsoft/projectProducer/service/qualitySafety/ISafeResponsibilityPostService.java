package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityPost;

import java.util.List;
import java.util.Map;

/**
 * 安全责任岗位服务。
 */
public interface ISafeResponsibilityPostService extends IService<SafeResponsibilityPost> {
    /**
     * 新增或修改岗位及职责。
     */
    BaseResponse createOrUpdateSafeResponsibilityPost(SafeResponsibilityPostDTO request, String uid, String uname);

    /**
     * 查询岗位列表。
     */
    BaseResponse getSafeResponsibilityPostList(SafeResponsibilityPostDTO request);

    /**
     * 删除岗位及职责。
     */
    BaseResponse deleteSafeResponsibilityPost(SafeResponsibilityPostDTO request, String uid, String uname);

    /**
     * 按体系查询岗位并组装职责。
     */
    Map<String, List<SafeResponsibilityPostDTO>> getPostMapBySystemId(String systemId, String keyword);

    /**
     * 删除体系下全部岗位及职责。
     */
    void deleteBySystemId(String systemId);

    /**
     * 复制来源体系岗位及职责。
     */
    void copyBySystem(String sourceSystemId, String targetSystemId, Map<String, String> levelIdMap,
                      String uid, String uname);
}
