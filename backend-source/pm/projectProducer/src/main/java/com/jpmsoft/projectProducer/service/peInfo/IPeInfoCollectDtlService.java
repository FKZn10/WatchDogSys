package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoCollectDtlRequest;
import com.jpmsoft.common.model.response.PeInfoCollectDtlResponse;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoCollectDtl;

import java.util.List;

public interface IPeInfoCollectDtlService extends IService<PeInfoCollectDtl> {


    /**
     * 查询未生成清单的所有履约评价
     *
     * @param request
     * @return
     */
    BaseResponse getPeInfoNotGenerateCollectDtl(PeInfoCollectDtlRequest request, String uId);


    BaseResponse getPeInfoCollectDtlList(PeInfoCollectDtlRequest request, String uId);

    /**
     * 根据履约评价表ID批量生成汇总清单表
     *
     * @param peIds 履约评价ID合集
     * @param pecId
     */
    void createOrUpdateCollectToCreateDtl(List<String> peIds, String pecId);

    /**
     * 关联汇总ID删除数据
     * 删除汇总表时
     *
     * @param pecId
     */
    void deleteByPecId(String pecId);

    /**
     * 根据PEID删除数据
     * 退回时
     *
     * @param peId
     */
    void deleteByPeId(String peId);

    /**
     * 导出查询汇总清单
     *
     * @param authSql
     * @return
     */
    List<PeInfoCollectDtlResponse> getPeInfoCollectDtlListWhenExport(String authSql, String pecId);


}
