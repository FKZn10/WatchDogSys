package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocControl;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecDocControlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 11:16
 */
public interface SecDocControlMapper extends BaseMapper<SecDocControl> {

    List<SecDocControlVo> getSecDocControlList(@Param("params") Map<String, Object> params);

    Integer getSecDocControlListCount(@Param("params") Map<String, Object> params);

    SecDocControlVo getSecDocControlById(@Param("id") String id);
}
