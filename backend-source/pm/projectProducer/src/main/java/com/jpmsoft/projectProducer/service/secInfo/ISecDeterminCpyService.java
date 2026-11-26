package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminCpyDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDeterminCpy;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/25 11:02
 */
public interface ISecDeterminCpyService extends IService<SecDeterminCpy> {

    void createOrUpdateSecDeterminCpy(List<SecDeterminCpyDto> datas, String drId);

    void delSecDeterminCpyByDrId(String drId);
}
