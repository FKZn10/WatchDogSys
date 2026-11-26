package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWorkSchema;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/23 9:38
 */
public interface ISecWorkSchemaService extends IService<SecWorkSchema> {

    BaseResponse createOrUpdateSecWorkSchema(SecWorkSchemaDTO request);

    BaseResponse getSecWorkSchemaList(SecWorkSchemaDTO request);

    BaseResponse getSecWorkSchemaByWsId(String wsId);

    BaseResponse getSecWorkSchemaBySecId(String secId);

    BaseResponse getSecWorkSchemaListClassInfo(SecWorkSchemaDTO request, LogUserInfoVo user);

    BaseResponse getSecWorkSchemaListClass(SecWorkSchemaDTO request, LogUserInfoVo user);

}
