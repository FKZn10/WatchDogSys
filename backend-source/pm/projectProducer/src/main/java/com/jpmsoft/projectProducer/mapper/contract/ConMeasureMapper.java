package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasure;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureApplyAmtVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
public interface ConMeasureMapper extends BaseMapper<ConMeasure> {

    List<ConMeasureVo> getConMeasureList(@Param("page") IPage<ConMeasureVo> page, @Param("params") Map<String, Object> params);


    ConMeasureVo getConMeasureById(@Param("id") String id);


    ConMeasureApplyAmtVO statisticsApplyAndAmt(@Param("id") String id);

    ConMeasureApplyAmtVO statisticsAmtByConId(@Param("conId") String conId);

    List<Map<String, Object>> getAllValues(
            @Param("selectClause") String selectClause,
            @Param("params") ConMeasureDto params
    );

    List<ConMeasureVo> getConMeasureInfoList(
            @Param("page") Page<ConMeasureDto> page,
            @Param("params") ConMeasureDto request);
}




