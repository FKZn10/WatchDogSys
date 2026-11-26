package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditGroupDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditGroupDetail;

import java.util.List;

/**
 * 集团专家评审
 */
public interface ISecExpertAuditGroupDetailService extends IService<SecExpertAuditGroupDetail> {
    void delete2Create(List<SecExpertAuditGroupDetailDTO> secExpertAuditDetails, String id);

    void deleteBySeaId(String seaId);

    List<SecExpertAuditGroupDetailDTO> getDetailList(String id);
}
