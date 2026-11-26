package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Contract.GuaranteeLetterBackResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.GuaranteeLetterBack;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface GuaranteeLetterBackMapper extends BaseMapper<GuaranteeLetterBack> {


    List<GuaranteeLetterBackResponse> getGuaranteeLetterBackList(@Param("page") IPage<GuaranteeLetterBackResponse> page, @Param("params") Map<String, Object> params);


    GuaranteeLetterBackResponse getGuaranteeLetterBackByglbId(@Param("glbId") String glbId);

    @Select("select mn.*  from  Guarantee_Letter_Back mn left join  Guarantee_Letter_Sign ls on mn.Gls_Id=ls.Gls_Id where ls.Con_Id=#{conId} and mn.Glb_State!=3 and ls.Gls_State!=3")
    List<GuaranteeLetterBack> getGuaranteeLetterBackByConId(@Param("conId") String conId);
}
