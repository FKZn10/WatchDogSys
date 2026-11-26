package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.response.NoMajproWeeklyNewspaperResponse;
import com.jpmsoft.projectProducer.pojo.entity.other.MajproWeeklyNewspaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MajproWeeklyNewspaperMapper extends BaseMapper<MajproWeeklyNewspaper> {
    List<String> selectProids(@Param("params") Map<String, Object> params);

    /**
     * 获取没在指定时间内填写周报的重点项目
     *
     * @param page
     * @param query
     * @return
     */
    List<NoMajproWeeklyNewspaperResponse> getNoMajproWeeklyNewspaperList(@Param("page") Page<NoMajproWeeklyNewspaperResponse> page, @Param("params") Map<String, Object> query, @Param("authSql") String authSql);
}
