package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.QuotationRequestDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.QuotationRequest;

import java.util.List;

/**
 * 标段询价调研明细服务。
 */
public interface IQuotationRequestService extends IService<QuotationRequest> {

    /**
     * 同步标段明细；null 保留，空列表清空，非空列表替换明细集合。
     *
     * @param data  询价调研明细
     * @param secId 所属标段ID
     */
    void createOrUpdate(List<QuotationRequestDTO> data, String secId);

    /**
     * 查询标段的询价调研明细。
     *
     * @param secId 标段ID
     * @return 明细列表，无数据时返回空列表
     */
    List<QuotationRequestDTO> selectBySecId(String secId);

    /**
     * 删除标段的全部询价调研明细。
     *
     * @param secId 标段ID
     */
    void deleteBySecId(String secId);

    /**
     * 删除招标计划下全部标段的询价调研明细。
     *
     * @param bpId 招标计划ID
     */
    void deleteByBpId(String bpId);
}
