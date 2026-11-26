package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.DynamicsCode;

import java.util.List;
import java.util.Map;

public interface IDynamicsCodeService extends IService<DynamicsCode> {


    List<Map> selectList(String sql);

    Map selectOne(String sql);

    Integer selectCount(String sql);
}
