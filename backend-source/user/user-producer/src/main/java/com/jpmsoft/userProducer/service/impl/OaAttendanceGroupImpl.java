package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.OaAttendanceGroupMapper;
import com.jpmsoft.userProducer.pojo.entity.OaAttendanceGroup;
import com.jpmsoft.userProducer.pojo.vo.UserAttendanceGroupVo;
import com.jpmsoft.userProducer.service.IOaAttendanceGroupService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OaAttendanceGroupImpl extends ServiceImpl<OaAttendanceGroupMapper, OaAttendanceGroup> implements IOaAttendanceGroupService {
    @Override
    public List<UserAttendanceGroupVo> getOaAttendanceGroupByUid(String uId) {
        List<OaAttendanceGroup> ags = baseMapper.getOaAttendanceGroupByUid(uId);
        List<UserAttendanceGroupVo> data = new ArrayList<>();
        for (OaAttendanceGroup ag : ags) {
            UserAttendanceGroupVo groupVo = new UserAttendanceGroupVo();
            groupVo.setAgName(ag.getAgName());
            groupVo.setAgId(ag.getAgId());
            data.add(groupVo);
        }
        return data;
    }
}
