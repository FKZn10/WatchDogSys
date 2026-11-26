package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.SysCarbonCopyRequest;
import com.jpmsoft.common.model.request.TaskRequest;
import com.jpmsoft.common.model.request.UserCarbonCopyRequest;
import com.jpmsoft.projectProducer.pojo.entity.other.CarbonCopy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CarbonCopyService extends IService<CarbonCopy> {

}
