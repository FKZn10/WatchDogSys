package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SysFileTemplateDtlDTO;
import com.jpmsoft.userProducer.pojo.entity.SysFileTemplateDtl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 文档模板扩展
 *
 * @author tianhelin
 * @date 2026/01/17
 */
@Mapper
public interface SysFileTemplateDtlMapper extends BaseMapper<SysFileTemplateDtl> {
    List<SysFileTemplateDtlDTO> getSysFileTemplateDtlBytIds(@Param("tIds") Collection<String> tIds);
}
