package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAudit;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertAuditVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:16
 */
public interface SecExpertAuditMapper extends BaseMapper<SecExpertAudit> {

    List<SecExpertAuditVO> getSecExpertAuditList(@Param("page") IPage<SecExpertAuditVO> page, @Param("params") Map<String, Object> params);
}
