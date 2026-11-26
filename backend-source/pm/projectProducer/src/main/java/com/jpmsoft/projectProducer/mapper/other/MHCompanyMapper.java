package com.jpmsoft.projectProducer.mapper.other;

import com.jpmsoft.projectProducer.pojo.vo.other.MHCompanyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MHCompanyMapper {

    List<MHCompanyVO> getCompanyListByApplyType(@Param("applyType") String applyType);
}
