package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblemDtl;

import java.util.List;

/**
 * 项目问题详情（清单）
 */
public interface IOaProblemDtlService extends IService<OaProblemDtl> {

    void createOrUpdateOaProblemDtl(List<OaProblemDtlModel> datas, String pmId);

    void deleteOaProblemDtlByPmId(String pmId);

    List<OaProblemDtl> getOaProblemDtlByPmId(String pmId);

    BaseResponse getOaProblemDtlList(OaProblemDtlModel request, String uId);

    /**
     * 更新问题完成状态
     *
     * @param pmdId
     * @param pmdStatus
     */
    void refreshProblemDtlStatus(String pmdId, int pmdStatus);
}
