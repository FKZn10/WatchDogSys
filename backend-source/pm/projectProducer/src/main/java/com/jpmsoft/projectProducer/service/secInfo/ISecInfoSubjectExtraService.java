package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectExtraDTO;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectExtra;

import java.util.List;

/**
 * 科目信息扩展服务
 */
public interface ISecInfoSubjectExtraService extends IService<SecInfoSubjectExtra> {
    /**
     * 创建或编辑科目信息扩展
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateSecInfoSubjectExtra(SecInfoSubjectExtraDTO request);

    /**
     * 删除科目信息扩展
     *
     * @param id
     * @return
     */
    BaseResponse deleteSecInfoSubjectExtraById(String id);

    /**
     * 根据secIds查询科目信息扩展
     *
     * @param secIds
     * @param bpPlanPhase
     * @return
     */
    List<SecInfoSubjectResponse> getSecInfoSubjectExtraBySecIds(List<String> secIds, String bpPlanPhase);

    /**
     * 同步二类费扩展
     *
     * @param bpcId
     * @param bpId
     * @return
     */
    BaseResponse approval2UpdateSecInfoSubjectExtra(String bpcId, String bpId);
}
