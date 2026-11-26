package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsManageSystemDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsManageSystem;

public interface IQsManageSystemService extends IService<QsManageSystem> {


    BaseResponse createOrUpdate(QsManageSystemDto request);


    BaseResponse getQsManageSystemList(QsManageSystemDto request, String uid);


    BaseResponse getQsManageSystemById(String id);


    BaseResponse getQsNodePersonByMark(String pId);
}
