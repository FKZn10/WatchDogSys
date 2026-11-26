package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import com.jpmsoft.jpbpm.pojo.entity.SecInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface SecInfoService extends IService<SecInfo> {

    void updateByBpId(String bpId, Integer state, Date stateTime);

}
