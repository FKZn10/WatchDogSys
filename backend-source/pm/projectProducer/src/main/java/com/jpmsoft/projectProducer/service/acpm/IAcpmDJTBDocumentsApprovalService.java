package com.jpmsoft.projectProducer.service.acpm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmDJTBDocumentsApprovalDto;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmDJTBDocumentsApproval;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:56
 */
public interface IAcpmDJTBDocumentsApprovalService extends IService<AcpmDJTBDocumentsApproval> {

    BaseResponse createOrUpdate(AcpmDJTBDocumentsApprovalDto request);

    BaseResponse getList(AcpmDJTBDocumentsApprovalDto request);

    BaseResponse getById(String id);
}
