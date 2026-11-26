package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecOpenComplaintsDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenComplaints;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/11/28 13:50
 */
public interface ISecOpenComplaintsService extends IService<SecOpenComplaints> {

    BaseResponse createOrUpdateSecOpenComplaints(SecOpenComplaintsDto request);

    BaseResponse getSecOpenComplaintsList(SecOpenComplaintsDto request);

    BaseResponse getSecOpenComplaintsById(String id);

}
