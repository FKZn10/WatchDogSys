package com.jpmsoft.jpbpm.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.SysExtendValue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysExtendValueService {

    BaseResponse createOrUpdateSysExtendValue(List<SysExtendValue> extendConfs,String customerId);

    BaseResponse getSysExtendValueByFileId(String fileId,String customerId);

    BaseResponse getSysExtendValueByFileIds(List<String> fileIds,String customerId);
}
