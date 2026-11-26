package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertSupplement;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertSupplementVO;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecSupplementRecordDtlVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:17
 */
public interface SecExpertSupplementMapper extends BaseMapper<SecExpertSupplement> {


    SecExpertSupplementVO selectSupplementById(@Param("id") String id);

    List<SecExpertSupplementVO> selectList(@Param("page") IPage<SecExpertSupplementVO> page,@Param("params") Map<String, Object> params);
}
