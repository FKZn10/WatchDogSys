package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineLibRiskRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineLibRiskResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineLibRisk;

import java.util.List;

/**
 * 体系模板风险指标
 *
 * @author tianhelin
 * @date 2025/6/11
 */
public interface IOutlineLibRiskService extends IService<OutlineLibRisk> {
    BaseResponse createOutlineLibRisk(OutlineLibRiskRequest request, String token);

    void deleteByOslId(String oslId);

    void deleteByOsmId(String osmId);

    Boolean riskExist(String riskId, String oslId);

    /**
     * 根据体系库id获取体系库风险
     *
     * @param oslId
     * @return
     */
    List<OutlineLibRiskResponse> getOutlineLibRiskByOslId(String oslId);

    BaseResponse getOutlineLibRiskById(String id);

    BaseResponse getOutlineLibRiskPage(OutlineLibRiskRequest request);

    /**
     * 根据体系库id集合获取体系库风险
     *
     * @param oslIds
     * @return
     */
    List<OutlineLibRiskResponse> getAllOutlineLibRiskByOslIds(List<String> oslIds);

    BaseResponse updateOutlineLibRisk(OutlineLibRiskRequest request, String token);

    BaseResponse deleteOutlineLibRiskById(String id, String token);
}
