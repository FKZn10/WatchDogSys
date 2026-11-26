package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQuotationUnit;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecQuotationUnitVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:56
 */
public interface SecQuotationUnitMapper extends BaseMapper<SecQuotationUnit> {

    List<SecQuotationUnitVo> getSecQuotationUnitBySecId(@Param("secId") String secId);
}
