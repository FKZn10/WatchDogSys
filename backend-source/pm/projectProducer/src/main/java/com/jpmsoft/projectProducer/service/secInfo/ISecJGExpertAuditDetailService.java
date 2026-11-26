package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertAuditDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertAuditDetail;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGExpertAuditDetailVO;

import java.util.List;

public interface ISecJGExpertAuditDetailService extends IService<SecJGExpertAuditDetail> {


    void deleteCreate(List<SecJGExpertAuditDetailDTO> secJGExpertAuditDetails, String seaId);

    void deleteBySeaId(String seaId);


    List<SecJGExpertAuditDetailVO> getDetailList(String seaId);


    List<String> getExIdList(String seaId);

}
