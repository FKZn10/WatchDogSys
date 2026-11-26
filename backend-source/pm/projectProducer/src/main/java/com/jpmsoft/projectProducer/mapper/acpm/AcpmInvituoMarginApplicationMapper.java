package com.jpmsoft.projectProducer.mapper.acpm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmInvituoMarginApplication;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmInvituoMarginApplicationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:35
 */

public interface AcpmInvituoMarginApplicationMapper extends BaseMapper<AcpmInvituoMarginApplication> {

    List<AcpmInvituoMarginApplicationVo> getList(@Param("params") Map<String, Object> params);

    Integer getCount(@Param("params") Map<String, Object> params);

    AcpmInvituoMarginApplicationVo getById(@Param("id") String id);
}
