package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreCommandPost;
import com.jpmsoft.projectProducer.pojo.vo.project.ComInvestmentSumVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PreCommandPostMapper extends BaseMapper<PreCommandPost> {


    List<PreCommandPostModel> getPreCommandPostList(@Param("page") IPage<PreCommandPostModel> page, @Param("params") Map<String, Object> params);

    ComInvestmentSumVo getComInvestmentSum(@Param("params") Map<String, Object> params);

    PreCommandPostModel getPreCommandPostById(@Param("comId") String comId);
}
