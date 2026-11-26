package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecWinNoticeDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinNoticeDtl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SecWinNoticeDtlMapper extends BaseMapper<SecWinNoticeDtl> {
    List<SecWinNoticeDtlDTO> getSecWinNoticeDtlByWnId(@Param("wnId") String wnId);
}
