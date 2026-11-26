package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSourcesFundsModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSourcesFunds;

import java.util.List;

public interface ISecSourcesFundsService extends IService<SecSourcesFunds> {

    void createOrUpdateSecSourcesFunds(List<SecSourcesFundsModel> datas, String secId);

    /**
     * 批量
     *
     * @param datas
     * @param secId
     */
    void createOrUpdateSecSourcesFundsByBath(List<List<SecSourcesFundsModel>> datas, List<String> secIds);

    void delSecSourcesFundsBySecId(String secId);

    void delSecSourcesFundsBySecIds(List<String> secIds);

    /**
     * 招标计划ID删除
     *
     * @param bpId
     */
    void delSecSourcesFundsByBpId(String bpId);

    /**
     * 批量查询标段资金来源
     *
     * @param secIds
     * @return
     */
    List<SecSourcesFundsModel> getSecSourcesFundsBySecIds(List<String> secIds);

    /**
     * 查询标段资金来源
     *
     * @param secIds
     * @return
     */
    List<SecSourcesFundsModel> getSecSourcesFundsBySecId(String secId);
}
