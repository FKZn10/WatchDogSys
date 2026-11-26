package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandApprovalDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandApproval;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:27
 */
public interface IMeBrandApprovalService extends IService<MeBrandApproval> {

    /**
     * 统计项目、合同下已有报审单的个数，无记录时返回 0。
     *
     * @param pId   项目 ID
     * @param conId 合同 ID
     * @return 报审单个数
     */
    BaseResponse getMeBrandApprovalReviewsCount(String pId, String conId);

    BaseResponse createOrUpdateMeBrandApproval(MeBrandApprovalDto request);

    BaseResponse getMeBrandApprovalList(MeBrandApprovalDto request,String uId);

    BaseResponse getMeBrandApprovalById(String id);
    BaseResponse getMeBrandApprovalDetailsById(String id);
}
