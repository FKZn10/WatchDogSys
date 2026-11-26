package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtl;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtlCheck;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundPlanDtlVo;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:05
 */
public interface IFundPlanDtlService extends IService<FundPlanDtl> {

    void createOrUpdateFundPlanDtl(List<FundPlanDtlDto> fundPlanDtls, List<FundPlanDtlDto> fundPlanDtls2,List<FundPlanDtlDto> fundPlanDtls3,String fpId, String pId,String fpType);

    void delFundPlanDtlByFpID(String fpId);

    void delFundPlanDtlByFpID1(String fpId);

    void delFundPlanDtlByFpID2(String fpId);

    void delFundPlanDtlByFpID3(String fpId);

    void delFundPlanDtlByIds(List<String> ids);

    void updateFinalInfo(FundPlanDtlCheck check);

    BaseResponse getFundPlanDtlList(FundPlanDtlDto request);

    List<FundPlanDtlVo> getAllFundPlanDtlByFpId(String fpId);

    List<FundPlanDtl> getFundPlanDtlByFpId(String fpId);

    List<FundPlanDtl> getFundPlanDtlByFpId1(String fpId, String fpType);

    List<FundPlanDtl> getFundPlanDtlByFpId2(String fpId, String fpType);
}
