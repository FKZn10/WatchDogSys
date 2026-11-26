package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.SecInfo.SecInfoBudgetDeclareDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecInfoBudgetDeclareDtlMapper extends BaseMapper<SecInfoBudgetDeclareDtl> {


    List<SecInfoBudgetDeclareDtl> getDataList(@Param("pId") String pId, @Param("ppId") String ppId);


    List<SecInfoBudgetDeclareDtlDto> getSecInfoBudgetDeclareDtlBySecIds(@Param("secIdList") List<String> secIdList);


    void delSecInfoBudgetDeclareDtlByBpId(@Param("bpId") String bpId);

    List<SecInfoBudgetDeclareDtl> getSecInfoBudgetDeclareDtlByIds(@Param("sibdIdList") List<String> sibdIdList);
}
