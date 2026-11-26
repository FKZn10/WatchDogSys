package com.jpmsoft.projectProducer.mapper.conBoq;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlMeasureDTO;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtl;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqDtlMeasureVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqDtlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConBoqDtlMapper extends BaseMapper<ConBoqDtl> {
    List<ConBoqDtlVo> getConBoqDtlByBoqId(@Param("boqId") String boqId);
    List<ConBoqDtlVo> getConBoqDtlByBoqIdAndParent(@Param("boqId") String boqId,@Param("parentId") String parentId);

    List<ConBoqDtlMeasureVo> getConBoqDtlAndMeasureTree(@Param("params") ConBoqDtlMeasureDTO params);

    List<ConBoqDtlDto> getConBoqDtlByConId(@Param("conId") String conId);
    List<ConBoqDtlVo> getConBoqDtlVoByConId(@Param("conId") String conId);

    List<ConBoqDtlVo> getConBoqDtlByConIdAndCode(@Param("conId") String conId, @Param("boqdCode") String boqdCode);

    List<ConBoqDtlVo> getConBoqDtlParentByIds(@Param("boqdIds") List<String> boqdIds);

    List<ConBoqDtlVo> getConBoqDtlBudgetList(@Param("page") IPage<ConBoqDtlVo> page, @Param("params") Map<String, Object> params);


    Integer  insertBatchBoqDtl(@Param("entities") List<ConBoqDtl> entities);

    List<ConBoqDtlVo> getConBoqDtlByBoqIdAndParentCon(String conId, String boqId, String parentId);
}
