package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMeasure;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureApplyAmtVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/17 13:49
 */
public interface ConPayMeasureMapper extends BaseMapper<ConPayMeasure> {

    List<ConMeasureApplyAmtVO> getApplyAndAmtByPayId(@Param("payId") String payId);

    List<ConMeasureApplyAmtVO> getUnMeasureApplyAndAmtByConId(@Param("conId") String conId);
}
