package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationSupervision;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecCalibrationSupervisionVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:44
 */
public interface SecCalibrationSupervisionMapper extends BaseMapper<SecCalibrationSupervision> {

    SecCalibrationSupervisionVO getSecCalibrationSupervisionById(@Param("id") String id);
}
