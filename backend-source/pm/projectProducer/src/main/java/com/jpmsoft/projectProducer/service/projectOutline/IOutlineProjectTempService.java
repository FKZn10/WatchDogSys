package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectTemp;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/17
 */
public interface IOutlineProjectTempService extends IService<OutlineProjectTemp> {
    /**
     * 新增项目管理大纲体系-关联业务功能
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createOutlineProjectTemp(OutlineProjectTempRequest request, String token);

    /**
     * 删除项目管理大纲体系-关联业务功能
     *
     * @param id
     * @param token
     * @return
     */
    BaseResponse deleteOutlineProjectTempById(String id, String token);

    /**
     * 根据tCode和oplId判断是否存在
     *
     * @param tCode
     * @param oplId
     * @return
     */
    Boolean tCodeExist(String tCode, String oplId);

    /**
     * 修改项目管理大纲体系-关联业务功能
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse updateOutlineProjectTemp(OutlineProjectTempRequest request, String token);

    /**
     * 查询项目大类
     *
     * @param id
     * @return
     */
    BaseResponse getOutlineProjectTempById(String id);

    /**
     * 分页查询项目大类
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineProjectTempPage(OutlineProjectTempRequest request);

    /**
     * 根据oplIds查询项目大类
     *
     * @param oplIds
     * @return
     */
    List<OutlineProjectTempResponse> getOutlineProjectTempByOplIds(List<String> oplIds);
}
