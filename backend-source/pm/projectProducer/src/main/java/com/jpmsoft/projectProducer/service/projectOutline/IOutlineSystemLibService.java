package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemLibRequest;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemManageRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineSystemLibResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemLib;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

/**
 * 体系模板-目录节点
 *
 * @author tianhelin
 * @date 2025/6/11
 */
public interface IOutlineSystemLibService extends IService<OutlineSystemLib> {
    /**
     * 获取体系库树形结构
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineSystemLibTree(OutlineSystemLibRequest request);

    /**
     * 查询体系库树形结构
     * 内部调用
     *
     * @param request
     * @return
     */
    List<OutlineSystemLibResponse> selectOutlineSystemLibTree(OutlineSystemLibRequest request);

    /**
     * 查询体系库树形结构
     * 内部调用
     *
     * @param request
     * @param treeMode
     * @return
     */
    List<OutlineSystemLibResponse> selectOutlineSystemLibTree(OutlineSystemLibRequest request, Boolean treeMode);

    /**
     * 获取体系库详情
     *
     * @param id
     * @return
     */
    BaseResponse getOutlineSystemLibById(String id);

    /**
     * 创建或更新体系库
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createOrUpdateOutlineSystemLib(OutlineSystemLibRequest request, String token);

    /**
     * 批量创建体系库
     *
     * @param request
     * @return
     */
    BaseResponse batchCreateOutlineSystemLib(OutlineSystemManageRequest request);

    /**
     * 删除体系库
     *
     * @param id
     * @param token
     * @return
     */
    BaseResponse deleteOutlineSystemLibById(String id, String token);

    /**
     * 删除子表数据
     *
     * @param osmId
     */
    void deleteOutlineSubtablesByOsmId(String osmId);

    /**
     * 批量更新体系库启用状态
     *
     * @param request
     * @return
     */
    BaseResponse batchUpdateSystemLibOslStatus(OutlineSystemLibRequest request, String token);

    /**
     * 更新体系库树排序
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse updateOutlineSystemLibSort(OutlineSystemLibRequest request, String token);

    /**
     * 刷新体系模板管理最后更新时间
     *
     * @param oslId
     * @param user
     */
    void refreshSystemManageLastUpdateTime(String oslId, LogUserInfoVo user);

    /**
     * 查找或替换大纲体系模板
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse searchOrReplaceOutlineSystemLib(OutlineSystemLibRequest request, String token);

    /**
     * 检查模板叶子节点是否为空
     *
     * @param osmId
     * @param token
     * @return
     */
    BaseResponse checkEmptyOutlineSystemLib(String osmId, String token);

    /**
     * 在lib有编制模板的情况下，判断添加前置业务后，对应的数据源是否添加完全
     * 若未完全，自动关联对应的数据源
     *
     * @param osmId
     * @param token
     * @return
     */
    BaseResponse checkAndAutoAddDatasource(String osmId, String token);
}
