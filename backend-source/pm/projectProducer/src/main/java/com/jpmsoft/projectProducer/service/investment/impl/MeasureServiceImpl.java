package com.jpmsoft.projectProducer.service.investment.impl;

import com.jpmsoft.projectProducer.mapper.investment.MeasureMapper;
import com.jpmsoft.projectProducer.service.investment.IMeasureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MeasureServiceImpl implements IMeasureService {

    @Resource
    private MeasureMapper measureMapper;

    @Override
    public int countEngineeringContactOrderMeasureUsed(String measureId) {
        int count = measureMapper.countEngineeringContactOrderMeasureUsed(measureId);
        return count;
    }

    @Override
    public int countEngineeringQuantitiesVisaMeasureUsed(String measureId) {
        int count = measureMapper.countEngineeringQuantitiesVisaMeasureUsed(measureId);
        return count;
    }

    @Override
    public int countClearSectionMeasureUsed(String measureId) {
        int count = measureMapper.countClearSectionMeasureUsed(measureId);
        return count;
    }

    @Override
    public int countConstructionUnitOrderMeasureUsed(String measureId) {
        int count = measureMapper.countConstructionUnitOrderMeasureUsed(measureId);
        return count;
    }

    @Override
    public int countEngineeringChangeMeasureUsed(String measureId) {
        int count = measureMapper.countEngineeringChangeMeasureUsed(measureId);
        return count;
    }

    @Override
    public int countContractPriceMeasureUsed(String measureId) {
        int count = measureMapper.countContractPriceMeasureUsed(measureId);
        return count;
    }

    @Override
    public int countSectionLowestPriceMeasureUsed(String measureId) {
        int count = measureMapper.countSectionLowestPriceMeasureUsed(measureId);
        return count;
    }
}
