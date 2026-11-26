package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeTempResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeTemp;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IOutlineNodeTempService extends IService<OutlineNodeTemp> {

    BaseResponse createOutlineNodeTemp(OutlineNodeTempRequest request, String token);

    BaseResponse getOutlineNodeTempList(OutlineNodeTempRequest request);

    Boolean tCodeExist(String tCode, String oslId);

    void deleteByOslId(String oslId);

    void deleteByOsmId(String osmId);

    /**
     * 体系模板-目录节点ID获取体系模板-关联业务功能
     *
     * @param oslIds
     * @return
     */
    List<OutlineNodeTempResponse> getAllOutlineNodeTempByOslIds(List<String> oslIds);

    BaseResponse updateOutlineNodeTemp(OutlineNodeTempRequest request, String token);

    BaseResponse deleteOutlineNodeTempById(String id, String token);

    /**
     * 体系模板-大纲体系ID获取体系模板-目录节点
     *
     * @param osmId
     * @return
     */
    List<OutlineNodeTempResponse> getAllOutlineNodeTempByOsmId(String osmId);

    /**
     * 根据oslId列表获取对应的oslid和tcode
     *
     * @param oslIds
     * @return
     */
    Map<String, Set<String>> getTCodesGroupedByOslIds(List<String> oslIds);
}
