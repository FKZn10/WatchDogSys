package com.jpmsoft.projectProducer.service.osm;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmBreachPenaltyDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmBreachPenalty;

/**
 * 违约处罚(OsmBreachPenalty)表服务接口
 *
 * @author c
 * @since 2024-12-05 11:54:59
 */
public interface IOsmBreachPenaltyService extends IService<OsmBreachPenalty> {


    BaseResponse getBreachPenaltyList(OsmBreachPenaltyDto osmBreachPenaltyDto);

    BaseResponse updateosmBreachPenalty(OsmBreachPenaltyDto request);

}
