package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanSummaryDetailDto;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanSummaryDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanSummary;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanSummaryRequest;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface IGroupFundPlanSummaryService extends IService<GroupFundPlanSummary> {

    BaseResponse createOrUpdateGroupFundPlanSummary(GroupFundPlanSummaryDto request);

    BaseResponse getGroupFundPlanSummaryList(GroupFundPlanSummaryRequest request, String uid);

    BaseResponse getGroupFundPlanSummaryById(String id);

    BaseResponse getCompanyGroupFundPlanSummaryById(String id);

    BaseResponse getBlocFundPlanSummaryById(String id);

    BaseResponse approvalData(GroupFundPlanSummaryRequest request);

    BaseResponse getGroupFundPlanSummaryInfo(GroupFundPlanSummaryRequest request,String uid);


    BaseResponse rollbackData(GroupFundPlanSummaryRequest request);

    /**
     * 获取成员企业下各个事业部/分支机构的项目资金计划信息
     *
     * @param request
     * @param user
     * @return
     */
    BaseResponse getCompanyProjectFundPlan(GroupFundPlanSummaryRequest request, LogUserInfoVo user);

    /**
     * 获取集团下各个成员企业的项目资金计划信息
     *
     * @param request
     * @return
     */
    BaseResponse getGroupProjectFundPlan(GroupFundPlanSummaryRequest request);

    /**
     * 根据pid获取资金计划汇总详情
     *
     * @param request
     * @return
     */
    BaseResponse getGroupFundPlanSummaryDetailByPid(GroupFundPlanSummaryDetailDto request);

    /**
     * 刷新资金计划历史数据
     *
     * @param request
     * @return
     */
    BaseResponse refreshHistoryData(GroupFundPlanSummaryDto request);

    /**
     * 根据id删除资金计划汇总信息
     *
     * @param id
     * @return
     */
    BaseResponse deleteGroupFundPlanSummaryById(String id);

    /**
     * 根据项目资金计划id判断是否存在关联的资金计划汇总数据
     *
     * @param id
     * @return
     */
    boolean existUseByGfpId(String id);

    /**
     * 判断是否能够提交
     *
     * @param request
     * @return
     */
    BaseResponse existProject(GroupFundPlanSummaryRequest request);
}
