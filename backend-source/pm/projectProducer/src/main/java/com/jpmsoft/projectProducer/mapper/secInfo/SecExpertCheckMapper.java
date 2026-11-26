package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertCheckDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheck;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertCheckVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:00
 */
public interface SecExpertCheckMapper extends BaseMapper<SecExpertCheck> {


    List<SecExpertCheckVO> getSecExpertCheckList(@Param("page") IPage<SecExpertCheckVO> page, @Param("params") Map<String, Object> params);

    SecExpertCheckModel getSecExpertCheckById(@Param("id") String id);

    List<SecSelectionRecordDtlModel> getSecdById(@Param("id") String id);

    SecExpertCheckModel getSecExpertCheckBySrId(@Param("srId") String srId);

    List<SecSelectionRecordDtlModel> getSelectionRecordDtl(@Param("srId") String srId);

    List<SecExpertCheckDTO> getSecExpertCheckTaskTimeout(@Param("params") Map<String, Object> params);
}
