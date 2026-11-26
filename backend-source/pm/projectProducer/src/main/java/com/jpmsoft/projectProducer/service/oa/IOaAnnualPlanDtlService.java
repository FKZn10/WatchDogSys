package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanDtl;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaAnnualPlanChangeDtlVo;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:05
 */
public interface IOaAnnualPlanDtlService extends IService<OaAnnualPlanDtl> {


    void createOrUpdateOaAnnualPlanDtl(List<OaAnnualPlanDtlDto> datas, String apId);

    void delOaAnnualPlanDtlByApId(String apId);

    void approval2RefershAnnualPlan(List<OaAnnualPlanChangeDtlVo> dtls, String apId);


    BaseResponse getOaAnnualPlanDtlList(OaAnnualPlanDto request);

    List<OaAnnualPlanDtl> getOaAnnualPlanDtlByYearAndPids(List<String> pIds, Integer year);

}
