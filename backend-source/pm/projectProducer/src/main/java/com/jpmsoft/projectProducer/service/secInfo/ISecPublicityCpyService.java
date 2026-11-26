package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecPublicityCpyDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPublicityCpy;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/25 10:45
 */
public interface ISecPublicityCpyService extends IService<SecPublicityCpy> {

    void createOrUpdateSecPublicityCpy(List<SecPublicityCpyDto> datas, String prId);

    void delSecPublicityCpyByPrId(String prId);
}
