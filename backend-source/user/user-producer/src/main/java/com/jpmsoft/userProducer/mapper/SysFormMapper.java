package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.dto.SysFormDTO;
import com.jpmsoft.userProducer.pojo.entity.SysForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 9:08
 */
public interface SysFormMapper extends BaseMapper<SysForm> {

    List<SysFormDTO> getSysFormList(@Param("page") IPage<SysFormDTO> page,@Param("params")  Map<String, Object> params);
}
