package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.dto.ProYearInvPlanDto;
import com.jpmsoft.fileUpload.pojo.entity.InvProjectPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface InvProjectPlanMapper extends BaseMapper<InvProjectPlan> {

    List<ProYearInvPlanDto> getProYearInvListByCodes(@Param("pCodes") List<String> pCodes);

    List<ProYearInvPlanDto> getProInvListByCodes(@Param("pCodes") List<String> pCodes);

}
