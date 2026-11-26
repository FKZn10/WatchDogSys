package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Contract.GuaranteeLetterSignResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.GuaranteeLetterSign;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface GuaranteeLetterSignMapper extends BaseMapper<GuaranteeLetterSign> {

    List<GuaranteeLetterSignResponse> getGuaranteeLetterSignList(@Param("page") IPage<GuaranteeLetterSignResponse> page, @Param("params") Map<String, Object> params);


    GuaranteeLetterSignResponse geteGuaranteeLetterSignByglsId(@Param("glsId") String glsId);

    GuaranteeLetterSignResponse getGuaranteeLetterSignByContract(@Param("conId") String conId);
}
