package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAudit;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditDetail;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertAuditDetailVO;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:16
 */
public interface SecExpertAuditDetailMapper extends BaseMapper<SecExpertAuditDetail> {
    List<SecExpertAuditDetailVO> getSecExpertAuditDetailListGroup(Integer year);

    SecExpertAudit getParentByCId(String cId);
}
