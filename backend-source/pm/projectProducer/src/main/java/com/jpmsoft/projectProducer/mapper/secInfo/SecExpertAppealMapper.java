package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAppeal;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertAppealVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:01
 */
public interface SecExpertAppealMapper extends BaseMapper<SecExpertAppeal> {

    List<SecExpertAppealVO> getSecExpertAppealList(@Param("page") IPage<SecExpertAppealVO> page, @Param("params") Map<String, Object> params);

    SecExpertAppealVO getSecExpertAppealById(@Param("id") String id);
}
