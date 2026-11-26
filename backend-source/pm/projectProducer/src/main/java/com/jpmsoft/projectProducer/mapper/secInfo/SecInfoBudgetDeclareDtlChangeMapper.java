package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecInfoBudgetDeclareDtlChangeRes;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtlChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecInfoBudgetDeclareDtlChangeMapper extends BaseMapper<SecInfoBudgetDeclareDtlChange> {


    void delBySicId(@Param("sicIdList") List<String> sicIdList);


    List<SecInfoBudgetDeclareDtlChangeRes> getSecInfoBudgetDeclareDtlChangeList(@Param("sicIdList") List<String> sicIdList);


}
