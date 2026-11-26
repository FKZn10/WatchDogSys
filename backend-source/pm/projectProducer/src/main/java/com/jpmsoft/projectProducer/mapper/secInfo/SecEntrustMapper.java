package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecEntrust;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecEntrustVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/31 11:01
 */
public interface SecEntrustMapper extends BaseMapper<SecEntrust> {

    SecEntrustVo getSecEntrustById(@Param("id") String id);

    List<SecEntrustVo> getSecEntrustList(@Param("page") IPage<SecEntrustVo> page, @Param("params") Map<String, Object> params);

    SecEntrustVo getSecEntrustBySecId(@Param("secId") String secId);
}
