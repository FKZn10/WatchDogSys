package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditGroupDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 集团专家评审
 */
@Mapper
public interface SecExpertAuditGroupMapper extends BaseMapper<SecExpertAuditGroup> {
    List<SecExpertAuditGroupDTO> getSecExpertAuditGroupList(@Param("page") IPage<SecExpertAuditGroupDTO> page, @Param("params") Map<String, Object> query);
}
