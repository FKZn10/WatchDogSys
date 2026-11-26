package com.jpmsoft.projectProducer.mapper.investment;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MeasureMapper {
    int countEngineeringContactOrderMeasureUsed(@Param("measureId") String measureId);

    int countEngineeringQuantitiesVisaMeasureUsed(@Param("measureId") String measureId);

    int countClearSectionMeasureUsed(@Param("measureId") String measureId);

    int countConstructionUnitOrderMeasureUsed(@Param("measureId") String measureId);

    int countEngineeringChangeMeasureUsed(@Param("measureId") String measureId);

    int countContractPriceMeasureUsed(@Param("measureId") String measureId);

    int countSectionLowestPriceMeasureUsed(@Param("measureId") String measureId);
}
