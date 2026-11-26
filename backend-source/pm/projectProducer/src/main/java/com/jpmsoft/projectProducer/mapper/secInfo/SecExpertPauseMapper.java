package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertPauseDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertPause;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertPauseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:00
 */
public interface SecExpertPauseMapper extends BaseMapper<SecExpertPause> {

    List<SecExpertPauseVO> getSecExpertPauseList(@Param("page") IPage<SecExpertPauseVO> page, @Param("params") Map<String, Object> params);

    SecExpertPauseVO getSecExpertPauseById(@Param("id") String id);


    Integer getPauseDays(@Param("exId") String exId,@Param("year")  String year);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause, @Param("params") SecExpertPauseDTO request);

    FileTemplateTree getFileTemplateByFileCode(@Param("fileCode") String fileCode);
}
