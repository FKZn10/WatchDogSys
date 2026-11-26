package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.peinfo.PeMoneyReturnDTO;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMoneyReturn;
import com.jpmsoft.projectProducer.pojo.vo.peInfo.PeMoneyReturnVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/25 18:44
 */
public interface PeMoneyReturnMapper extends BaseMapper<PeMoneyReturn> {


    List<PeMoneyReturnDTO> getPeMoneyReturnList(@Param("page") IPage<PeMoneyReturnDTO> page, @Param("params") Map<String, Object> params);

    PeMoneyReturnVO getListAmtSum(@Param("params") Map<String, Object> params);

    BigDecimal getPeMoneySumByConId(@Param("conId") String conId);

    PeMoneyReturnDTO getPeMoneyReturnById(@Param("id") String id);


}
