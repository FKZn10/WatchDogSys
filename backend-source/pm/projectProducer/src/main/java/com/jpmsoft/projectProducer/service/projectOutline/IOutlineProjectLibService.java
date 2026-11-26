package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectLibRequest;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectManageRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectLibResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectLib;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

/**
 * 项目管理大纲体系目录节点
 *
 * @author tianhelin
 * @date 2025/6/11
 */
public interface IOutlineProjectLibService extends IService<OutlineProjectLib> {
    /**
     * 获取大纲体系树形结构
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineProjectLibTree(OutlineProjectLibRequest request);

    /**
     * 查询大纲体系树形结构
     * 内部调用
     *
     * @param request
     * @return
     */
    List<OutlineProjectLibResponse> selectOutlineProjectLibTree(OutlineProjectLibRequest request);

    /**
     * 查询大纲体系（自定结构）
     * 内部调用
     *
     * @param request
     * @param treeMode
     * @param withIndexPrefix
     * @param withOutContent
     * @param withLevel
     * @return
     */
    List<OutlineProjectLibResponse> selectOutlineProjectLibTree(OutlineProjectLibRequest request, Boolean treeMode, Boolean withIndexPrefix, Boolean withOutContent, Boolean withLevel, Boolean prettyPrint);

    /**
     * 获取大纲体系详情
     *
     * @param id
     * @return
     */
    BaseResponse getOutlineProjectLibById(String id);

    /**
     * 创建或更新大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createOrUpdateOutlineProjectLib(OutlineProjectLibRequest request, String token);

    /**
     * 删除大纲体系
     *
     * @param id
     * @param token
     * @return
     */
    BaseResponse deleteOutlineProjectLibById(String id, String token);

    /**
     * 删除子表数据
     *
     * @param oplIds
     */
    void deleteOutlineSubtables(List<String> oplIds);

    /**
     * 根据opmId删除子表数据
     *
     * @param opmId
     */
    void deleteOutlineSubtablesByOpmId(String opmId);

    /**
     * 批量创建项目大纲
     *
     * @param request
     * @return
     */
    BaseResponse batchCreateOutlineProjectLib(OutlineProjectManageRequest request);

    /**
     * 获取大纲体系列表
     *
     * @param opmId
     * @return
     */
    BaseResponse getOutlineProjectLibListByOpmId(String opmId);

    /**
     * 更新项目大纲排序
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse updateOutlineProjectLibSort(OutlineProjectLibRequest request, String token);

    /**
     * 获取项目对应的大纲体系树形结构
     *
     * @param pId
     * @param oplName
     * @param opmCode
     * @return
     */
    BaseResponse getOutlineProjectLibListByPid(String pId, String oplName, String opmCode);

    /**
     * 刷新项目管理最后更新时间
     *
     * @param oplId
     * @param user
     */
    void refreshProjectManageLastUpdateTime(String oplId, LogUserInfoVo user);

    /**
     * 查找或替换大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse searchOrReplaceOutlineProjectLib(OutlineProjectLibRequest request, String token);

    /**
     * 校验当前内容是否包含其他项目
     *
     * @param opmId
     * @return
     */
    BaseResponse verifyProjectLibWithProjectName(String opmId);

    /**
     * 根据大纲体系id获取FileId
     *
     * @param oplId
     * @return
     */
    BaseResponse getFileIdByOplId(String oplId);

    /**
     * 根据项目id和大纲体系编码获取大纲体系id
     *
     * @param pId
     * @param tCode
     * @return
     */
    BaseResponse getOplIdByPidAndTCode(String pId, String tCode);

    /**
     * 检查大纲叶子节点是否为空
     *
     * @param opmId
     * @return
     */
    BaseResponse checkEmptyOutlineProjectLib(String opmId);
}
