package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecWinUnitResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SecWinUnitMapper extends BaseMapper<SecWinUnit> {

    List<SecWinUnitResponse> getWinUnitListByWnId(@Param("wnId") String wnId);
}
