package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAudit;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditDetail;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertAuditDetailVO;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:19
 */
public interface ISecExpertAuditDetailService extends IService<SecExpertAuditDetail> {

    void delete2Create(List<SecExpertAuditDetailDTO> data,String seaId);

    void deleteBySeaId(String seaId);


    List<String> getExIdList(String seaId);


    List<SecExpertAuditDetailVO> getDetailList(String seaId);

    List<SecExpertAuditDetailVO> getSecExpertAuditDetailListGroup(Integer timeYear);

    SecExpertAudit getParentByCId(String cId);
}
