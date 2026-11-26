package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQePreTrial;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecQePreTrialVo;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:18
 */
public interface SecQePreTrialMapper extends BaseMapper<SecQePreTrial> {

    SecQePreTrialVo getSecQePreTrialById(@Param("id") String id);
}
