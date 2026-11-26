package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementApply;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecProcurementApplyVo;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:18
 */
public interface SecProcurementApplyMapper extends BaseMapper<SecProcurementApply> {

    SecProcurementApplyVo getSecProcurementApplyById(@Param("id") String id);
}
