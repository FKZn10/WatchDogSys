package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanRequest;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanTZRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlan;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.apache.poi.ss.usermodel.Workbook;

import java.math.BigDecimal;
import java.util.List;

public interface ISecBiddingPlanService extends IService<SecBiddingPlan> {


    BaseResponse refreshSecBiddingPlanSign();

    BaseResponse createOrUpdateSecBiddingPlan(SecBiddingPlanRequest request);

    BaseResponse getSecBiddingPlanList(SecBiddingPlanRequest request, String uId);

    /**
     * 城建地址逻辑
     *
     * @param request
     * @param user
     * @return
     */
    BaseResponse getSecBiddingPlanListCjdz(SecBiddingPlanRequest request, LogUserInfoVo user);


    BaseResponse getSecBiddingPlanById(String bpId);

    /**
     * 通过项目获取招标计划数据-城建地址
     * 与getSecBiddingPlanById一样
     * @param pId
     * @return
     */
    BaseResponse getSecBiddingPlanByPIdNJCJ(String pId);

    BaseResponse appoval2CreateSecInfoSysFile(SecInfoRequest request);

    BaseResponse getSecBiddingPlanGroupByProject(SecBiddingPlanRequest request, String uId);

    /**
     * 获取筛选字段列表
     * @param request
     * @param uid
     * @return
     */
    BaseResponse getTZClassInfo(SecBiddingPlanTZRequest request, String uid);

    /**
     * 通过项目获取招标计划数据
     * @param pId
     * @return
     */
    BaseResponse getSecBiddingPlanByPId(String pId);

    SecBiddingPlan getSecBiddingPlanByPIdList(List<String> bpIds);

    BaseResponse judgmentSecBiddingPlanByPId(String pId);

    String judgmentSecBiddingPlanType(String pId);

    BaseResponse judgmentSecBiddingPlanAmount(String type, String id, BigDecimal money);

    BaseResponse getSecBiddingPlanSubjectListByPId(String pId, String type, String excludeExtra, String bpcId);

    BaseResponse getSecBiddingPlanSubjectListByPIdTree(String pId, String type, String excludeExtra, String bpcId);

    BaseResponse getSecFileRelationIdByPId(String pId);

    /**
     * 通过项目获取估算概算总金额
     *
     * @param pId
     * @param type
     * @return
     */
    BaseResponse getEstimatedBudgetAmountByPId(String pId, String type);

    /**
     * 导出招标计划明细Excel
     *
     * @param bpId
     * @return
     */
    Workbook getSecBiddingPlanDetailExportExcel(String bpId);

    /**
     * 前置校验
     *
     * @param tCode
     * @param secId
     * @return
     */
    BaseResponse getSecPrecheck(String tCode, String secId);

    /**
     * 更新指定计划的项目分管领导
     *
     * @param bpId
     * @param responsibleLeader
     * @return
     */
    BaseResponse updateSecBiddingPlanResponsibleLeader(String bpId, String responsibleLeader);

    /**
     * 更新或新增由旧版招标计划转换的新版招标计划
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateFromOldSecBiddingPlan(SecBiddingPlanRequest request);
}
