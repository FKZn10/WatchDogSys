package com.jpmsoft.projectProducer.service.investment;

public interface IMeasureService {
    int countEngineeringContactOrderMeasureUsed(String measureId);

    int countEngineeringQuantitiesVisaMeasureUsed(String measureId);

    int countClearSectionMeasureUsed(String measureId);

    int countConstructionUnitOrderMeasureUsed(String measureId);

    int countEngineeringChangeMeasureUsed(String measureId);

    int countContractPriceMeasureUsed(String measureId);

    int countSectionLowestPriceMeasureUsed(String measureId);
}
