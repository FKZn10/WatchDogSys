package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEc;

import java.util.List;

public interface IConEcService extends IService<ConEc> {

    BaseResponse createOrUpdateConEc(ConEcRequest request);

    BaseResponse updateConEc(ConEcRequest request);

    BaseResponse deleteConEcByEcId(String ecId);

    BaseResponse getConEcByEcId(String ecId);

    BaseResponse getConEcByConId(String conId);

    BaseResponse getConEcCountByConId(String conId);

    BaseResponse getConEcList(ConEcRequest request, String uId);

    BaseResponse getConEcListByPermission(ConEcRequest request, String uId);

    BaseResponse getConEcStandingBook(ConEcRequest request, String uId);

    BaseResponse getConEcListNoAuth(ConEcRequest request);

    /**
     * 判断工程建设单是否关联事项申请
     * (是否根据工程建设单ID创建了事项申请)
     *
     * @param clId
     * @return
     */
    Boolean contactListAssociateData(String clId);


    BaseResponse getConEcStatisticsOverview(String teId);

    /**
     * 获取变更总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getConEcStatisticsOverviewList(ConEcRequest request, String teId);

    BaseResponse getConEcStatisticsByGrade(String teId);

    BaseResponse getConEcStatisticsChangeSituation(String teId);

    BaseResponse getConEcStatisticsByRanking(Integer size, String teId);

    /**
     * 审批通过作废关联的前变更
     *
     * @param ecId
     * @return
     */
    BaseResponse approve2CancelPreEc(String ecId);

    void processSyn(String uuid);

    BaseResponse changeTtems(String conId);


    List<ConEc> selectApprovedByIds(List<String> ids);

    BaseResponse saveBgzl(ConEcRequest request);

    BaseResponse invalidConEcByEcId(String ecId);

    BaseResponse isMajorChangeTtems(ConEcRequest request);

    BaseResponse getConEcListClasInfo(ConEcRequest request, String uid);

    BaseResponse checkClzgAndZlje(ConEcRequest request);

    /**
     * 获取合同累计变更金额统计
     *
     * @param conId
     * @return
     */
    BaseResponse getMoneyStatistics(String conId, String itemCategory);
}