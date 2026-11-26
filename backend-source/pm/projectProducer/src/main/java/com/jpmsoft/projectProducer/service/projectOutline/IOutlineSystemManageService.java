package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemManageRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineSystemManageResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemManage;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.LinkedHashMap;

/**
 * 体系模板管理
 *
 * @author tianhelin
 * @date 2025/6/11
 */
public interface IOutlineSystemManageService extends IService<OutlineSystemManage> {
    /**
     * 获取体系模板分页
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineSystemManagePage(OutlineSystemManageRequest request);

    /**
     * 获取体系模板详情
     *
     * @param id
     * @return
     */
    BaseResponse getOutlineSystemManageById(String id);

    OutlineSystemManageResponse selectOutlineSystemManageById(String id, Boolean includeSubtables);

    /**
     * 查询体系模板详情
     * 内部调用
     *
     * @param id
     * @return
     */
    OutlineSystemManageResponse selectOutlineSystemManageById(String id);

    /**
     * 创建或更新体系模板
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createOrUpdateOutlineSystemManage(OutlineSystemManageRequest request, String token);

    /**
     * 删除体系模板
     *
     * @param request
     * @return
     */
    BaseResponse deleteOutlineSystemManageById(OutlineSystemManageRequest request);

    /**
     * 批量更新体系模板状态
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse batchUpdateSystemManageStatus(OutlineSystemManageRequest request, String token);

    /**
     * 复制体系模板
     *
     * @param id
     * @param token
     * @return
     */
    BaseResponse copyOutlineSystemManageById(String id, String token);

    /**
     * 刷新最后更新时间
     *
     * @param osmId
     * @param user
     */
    void refreshLastUpdateTime(String osmId, LogUserInfoVo user);

    /**
     * 导入项目管理大纲Word
     * 内部调用
     *
     * @param map
     * @param osmId
     * @param token
     * @return
     */
    BaseResponse importOutlineProjectWord(LinkedHashMap<String, String> map, String osmId, String token);
}
