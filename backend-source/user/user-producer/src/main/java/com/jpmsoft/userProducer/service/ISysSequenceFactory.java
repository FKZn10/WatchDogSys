package com.jpmsoft.userProducer.service;

import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDtlDto;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDto;

import java.util.List;


public interface ISysSequenceFactory {

    String timeRuleGenerate(String pattern);

    String uuidRuleGenerate();

    String sqlRuleGenerate(String sql);

    String constRuleGenerate(String con, String tableField, String fieldValue, Object request);

    String numberRuleGenerate(Integer sNo, Integer sStep, String side, Integer width, String charFiled);

    String classRuleGenerate(SysSequenceSetDto model, String className);

    /**
     * 重置
     *
     * @param model
     * @param pId
     * @param conId
     * @param secId
     * @param data
     * @param request
     * @param refershNo 是否更新自增值(生成编码时不更新自增值，表单操作结束后更新)
     * @return
     */
    SysSequenceSetDto sequenceRest(SysSequenceSetDto model, String pId, String conId, String secId,
                                   List<SysSequenceSetDtlDto> data, Object request, boolean refershNo);


    String getCode(SysSequenceSetDto model, String pId, String conId, String secId, Object request, boolean refershNo);

}
