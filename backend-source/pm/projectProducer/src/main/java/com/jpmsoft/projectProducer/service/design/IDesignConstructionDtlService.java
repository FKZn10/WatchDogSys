package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConstructionDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConstructionDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/25 14:12
 */
public interface IDesignConstructionDtlService extends IService<DesignConstructionDtl> {


    void createOrUpdateConstructionDtls(List<DesignConstructionDtlDTO> data, String dcId,String token);


    void deleteByDcId(String dcId,String token);

    List<DesignConstructionDtlDTO> getDesConstDtlListByDcId(String dcId);
}
