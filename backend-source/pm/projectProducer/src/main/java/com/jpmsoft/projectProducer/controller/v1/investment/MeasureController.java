package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.investment.IMeasureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/measure")
public class MeasureController {

    @Resource
    private IMeasureService measureService;

    /**
     * 工程联系单测算被使用数量
     * @param measureId
     * @return
     */
    @PostMapping("/countEngineeringContactOrderMeasureUsed")
    public BaseResponse countEngineeringContactOrderMeasureUsed(String measureId){
        int usedCount = measureService.countEngineeringContactOrderMeasureUsed(measureId);
        return BaseResponse.ok(usedCount);
    }

    /**
     * 工程量签证测算被使用数量
     * @param measureId
     * @return
     */
    @PostMapping("/countEngineeringQuantitiesVisaMeasureUsed")
    public BaseResponse countEngineeringQuantitiesVisaMeasureUsed(String measureId){
        int usedCount = measureService.countEngineeringQuantitiesVisaMeasureUsed(measureId);
        return BaseResponse.ok(usedCount);
    }

    /**
     * 清标测算被使用数量
     * @param measureId
     * @return
     */
    @PostMapping("/countClearSectionMeasureUsed")
    public BaseResponse countClearSectionMeasureUsed(String measureId){
        int usedCount = measureService.countClearSectionMeasureUsed(measureId);
        return BaseResponse.ok(usedCount);
    }

    /**
     * 建设单位指令单测算被使用数量
     * @param measureId
     * @return
     */
    @PostMapping("/countConstructionUnitOrderMeasureUsed")
    public BaseResponse countConstructionUnitOrderMeasureUsed(String measureId){
        int usedCount = measureService.countConstructionUnitOrderMeasureUsed(measureId);
        return BaseResponse.ok(usedCount);
    }

    /**
     * 工程变更测算被使用数量
     * @param measureId
     * @return
     */
    @PostMapping("/countEngineeringChangeMeasureUsed")
    public BaseResponse countEngineeringChangeMeasureUsed(String measureId){
        int usedCount = measureService.countEngineeringChangeMeasureUsed(measureId);
        return BaseResponse.ok(usedCount);
    }

    /**
     * 合同价测算被使用数量
     * @param measureId
     * @return
     */
    @PostMapping("/countContractPriceMeasureUsed")
    public BaseResponse countContractPriceMeasureUsed(String measureId){
        int usedCount = measureService.countContractPriceMeasureUsed(measureId);
        return BaseResponse.ok(usedCount);
    }

    /**
     * 标底测算被使用数量
     * @param measureId
     * @return
     */
    @PostMapping("/countSectionLowestPriceMeasureUsed")
    public BaseResponse countSectionLowestPriceMeasureUsed(String measureId){
        int usedCount = measureService.countSectionLowestPriceMeasureUsed(measureId);
        return BaseResponse.ok(usedCount);
    }

}
