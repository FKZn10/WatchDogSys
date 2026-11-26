package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.OaAttendanceGroup;
import com.jpmsoft.userProducer.pojo.vo.UserAttendanceGroupVo;

import java.util.List;


public interface IOaAttendanceGroupService extends IService<OaAttendanceGroup> {

    List<UserAttendanceGroupVo> getOaAttendanceGroupByUid(String uId);
}
