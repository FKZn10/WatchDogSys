package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SecInfoMapper;
import com.jpmsoft.jpbpm.pojo.entity.SecInfo;
import com.jpmsoft.jpbpm.service.SecInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SecInfoImpl extends ServiceImpl<SecInfoMapper, SecInfo> implements SecInfoService {


    @Override
    public void updateByBpId(String bpId, Integer state, Date stateTime) {
        baseMapper.updateByBpId(bpId,state,stateTime);
    }

}
