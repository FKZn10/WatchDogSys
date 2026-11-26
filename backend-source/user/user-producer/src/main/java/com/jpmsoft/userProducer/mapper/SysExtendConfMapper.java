package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.SysExtendConfDTO;
import com.jpmsoft.userProducer.pojo.entity.SysExtendConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/10 11:12
 */
public interface SysExtendConfMapper extends BaseMapper<SysExtendConf> {


    List<SysExtendConfDTO> getSysExtendConfByTCodes(@Param("codes") List<String> codes);
}
