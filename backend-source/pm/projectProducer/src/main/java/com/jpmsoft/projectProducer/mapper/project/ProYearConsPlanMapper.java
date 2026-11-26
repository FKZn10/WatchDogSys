package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.project.ProYearConsPlan;
import com.jpmsoft.projectProducer.pojo.vo.project.ProYearConsPlanVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:11
 */
public interface ProYearConsPlanMapper extends BaseMapper<ProYearConsPlan> {

    List<ProYearConsPlanVo> getProYearConsPlanList(@Param("page") IPage<ProYearConsPlanVo> page, @Param("params") Map<String, Object> params);

    ProYearConsPlanVo getProYearConsPlanById(@Param("id") String id);
}
