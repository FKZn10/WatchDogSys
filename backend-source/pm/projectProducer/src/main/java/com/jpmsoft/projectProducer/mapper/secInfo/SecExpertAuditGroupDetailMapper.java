package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditGroupDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditGroupDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 集团专家评审
 */
@Mapper
public interface SecExpertAuditGroupDetailMapper extends BaseMapper<SecExpertAuditGroupDetail> {
    List<SecExpertAuditGroupDetailDTO> getDetailList(@Param("id") String id);
}
