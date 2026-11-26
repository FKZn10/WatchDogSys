package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecControlCpyDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecControlCpy;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/24 20:34
 */
public interface ISecControlCpyService extends IService<SecControlCpy> {

    void createOrUpdateControlCpy(List<SecControlCpyDto> datas, String cpId);

    void delControlCpyByCpId(String cpId);
}
