package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/measure")
public class MeasureController {
    
    @Resource
    private BaseFeign baseFeign;

    /**
     * 工程联系单测算是否被使用
     * @param measureId
     * @return
     */
    @PostMapping("/countEngineeringContactOrderMeasureUsed")
    public BaseResponse countEngineeringContactOrderMeasureUsed(String measureId){
        return baseFeign.countEngineeringContactOrderMeasureUsed(measureId);
    }

    /**
     * 工程量签证测算是否被使用
     * @param measureId
     * @return
     */
    @PostMapping("/countEngineeringQuantitiesVisaMeasureUsed")
    public BaseResponse countEngineeringQuantitiesVisaMeasureUsed(String measureId){
        return baseFeign.countEngineeringQuantitiesVisaMeasureUsed(measureId);
    }

    /**
     * 清标测算是否被使用
     * @param measureId
     * @return
     */
    @PostMapping("/countClearSectionMeasureUsed")
    public BaseResponse countClearSectionMeasureUsed(String measureId){
        return baseFeign.countClearSectionMeasureUsed(measureId);
    }

    /**
     * 建设单位指令单测算是否被使用
     * @param measureId
     * @return
     */
    @PostMapping("/countConstructionUnitOrderMeasureUsed")
    public BaseResponse countConstructionUnitOrderMeasureUsed(String measureId){
        return baseFeign.countConstructionUnitOrderMeasureUsed(measureId);
    }

    /**
     * 工程变更测算是否被使用
     * @param measureId
     * @return
     */
    @PostMapping("/countEngineeringChangeMeasureUsed")
    public BaseResponse countEngineeringChangeMeasureUsed(String measureId){
        return baseFeign.countEngineeringChangeMeasureUsed(measureId);
    }

    /**
     * 合同价测算是否被使用
     * @param measureId
     * @return
     */
    @PostMapping("/countContractPriceMeasureUsed")
    public BaseResponse countContractPriceMeasureUsed(String measureId){
        return baseFeign.countContractPriceMeasureUsed(measureId);
    }

    /**
     * 标底测算是否被使用
     * @param measureId
     * @return
     */
    @PostMapping("/countSectionLowestPriceMeasureUsed")
    public BaseResponse countSectionLowestPriceMeasureUsed(String measureId){
        return baseFeign.countSectionLowestPriceMeasureUsed(measureId);
    }
    
}
