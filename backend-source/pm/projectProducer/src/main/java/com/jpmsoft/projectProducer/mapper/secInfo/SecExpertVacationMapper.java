package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertVacation;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertVacationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:00
 */
public interface SecExpertVacationMapper extends BaseMapper<SecExpertVacation> {

    List<SecExpertVacationVO> getSecExpertVacationList(@Param("page") IPage<SecExpertVacationVO> page, @Param("params") Map<String, Object> params);

    SecExpertVacationVO getSecExpertVacationById(@Param("id") String id);

    Integer getVacationDays(@Param("exId") String exId, @Param("year") String year);
}
