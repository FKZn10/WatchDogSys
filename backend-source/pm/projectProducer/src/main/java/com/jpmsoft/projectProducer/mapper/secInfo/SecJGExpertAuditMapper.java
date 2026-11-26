package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertAudit;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGExpertAuditVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecJGExpertAuditMapper extends BaseMapper<SecJGExpertAudit> {


    List<SecJGExpertAuditVO> getSecExpertAuditList(@Param("page") IPage<SecJGExpertAuditVO> page,@Param("params") Map<String, Object> params);


}
