package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectRiskRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectRiskResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectRisk;

import java.util.List;

/**
 * 项目管理大纲体系管理风险指标
 *
 * @author tianhelin
 * @date 2025/6/11
 */
public interface IOutlineProjectRiskService extends IService<OutlineProjectRisk> {
    /**
     * 根据项目管理大纲体系id查询风险指标
     *
     * @param oplId
     * @return
     */
    List<OutlineProjectRiskResponse> getOutlineProjectRiskByOplId(String oplId);

    /**
     * 根据项目管理大纲管理id查询风险指标
     *
     * @param opmId
     * @return
     */
    List<OutlineProjectRiskResponse> getAllOutlineProjectRiskByOpmId(String opmId);

    /**
     * 新增项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createOutlineProjectRisk(OutlineProjectRiskRequest request, String token);

    /**
     * 删除项目管理大纲体系管理风险指标
     *
     * @param id
     * @param token
     * @return
     */
    BaseResponse deleteOutlineProjectRiskById(String id, String token);

    /**
     * 修改项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse updateOutlineProjectRisk(OutlineProjectRiskRequest request, String token);

    /**
     * 根据riskId和oplId判断项目风险指标是否存在
     *
     * @param riskId
     * @param oplId
     * @return
     */
    Boolean riskExist(String riskId, String oplId);

    /**
     * 查询项目管理大纲体系管理风险指标
     *
     * @param id
     * @return
     */
    BaseResponse getOutlineProjectRiskById(String id);

    /**
     * 分页查询项目管理大纲体系管理风险指标
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineProjectRiskPage(OutlineProjectRiskRequest request);

    List<OutlineProjectRiskResponse> getProjectRiskLedgerByOpmIds(List<String> ids);
}
