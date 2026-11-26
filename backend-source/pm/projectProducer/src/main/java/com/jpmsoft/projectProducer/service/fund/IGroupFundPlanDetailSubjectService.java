package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.fund.GroupFundPlanDetailSubjectDTO;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanDetailSubject;

import java.util.List;

/**
 * 资金计划明细-概算关联表 服务类
 */
public interface IGroupFundPlanDetailSubjectService extends IService<GroupFundPlanDetailSubject> {
    /**
     * 批量创建资金计划明细-概算关联表
     *
     * @param subjectList
     * @return
     */
    BaseResponse createSubjectBatch(List<GroupFundPlanDetailSubjectDTO> subjectList);

    /**
     * 根据资金计划明细ID获取关联的概算信息
     *
     * @param id
     * @return
     */
    List<GroupFundPlanDetailSubjectDTO> getByGfpId(String id);
}
