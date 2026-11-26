package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecWinUnitRequest;
import com.jpmsoft.common.model.response.SecInfo.SecWinUnitResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinUnit;

import java.util.List;

public interface ISecWinUnitService extends IService<SecWinUnit> {

    void createOrUpdateSecWinUnit(List<SecWinUnitRequest> models, String wnId);

    void deleteSecWinUnitByWnId(String wnId);


    /**
     * 删除中标书先校验是否有中标单位数据
     *
     * @param wuId
     * @return
     */
    Boolean havingSecWinUnitByWnId(String wuId);

    List<SecWinUnitResponse> getWinUnitListByWnId(String wnId);
}
