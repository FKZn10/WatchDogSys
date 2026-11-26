package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeMeetingApprovalRequest;
import com.jpmsoft.common.model.response.peInfo.PeMeetingApprovalRes;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMeetingApproval;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:13
 */
public interface IPeMeetingApprovalService extends IService<PeMeetingApproval> {

    BaseResponse createOrUpdatePeMeetingApproval(PeMeetingApprovalRequest request);

    List<PeMeetingApprovalRes> getPeMeetingApprovalList(PeMeetingApprovalRequest request, String uId);

    Integer getPeMeetingApprovalCount(PeMeetingApprovalRequest request, String uId);

    PeMeetingApprovalRes getPeMeetingApprovalById(String maId);

    BaseResponse updatePeStateByMaId(String peState, String maId);
}
