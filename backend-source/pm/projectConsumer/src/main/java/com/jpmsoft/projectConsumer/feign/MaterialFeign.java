package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.*;
import com.jpmsoft.common.model.requestAndResponse.MeEnter.MeDetectionGuideModel;
import com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 见证送检
 */
@FeignClient(name = "project-producer", contextId = "material")
public interface MaterialFeign {
    /***********************************************材料设备进场报审***************************************************/

    @PostMapping("/v1/meEnterApply/createOrUpdateMeEnterApply")
    BaseResponse createOrUpdateMeEnterApply(@RequestBody MeEnterApplyRequest request);

    @PostMapping("/v1/meEnterApply/getMeEnterApplyList")
    BaseResponse getMeEnterApplyList(@RequestBody MeEnterApplyRequest request, @RequestHeader String token);

    @GetMapping("/v1/meEnterApply/getMeEnterApplyByEaId")
    BaseResponse getMeEnterApplyByEaId(@RequestParam String eaId);

    /***********************************************材料设备品种库***************************************************/
    @PostMapping("/v1/meTypeLibrary/createOrUpdateMeTypeLibrary")
    BaseResponse createOrUpdateMeTypeLibrary(@RequestBody MeTypeLibraryRequest request);

    @PostMapping("/v1/meTypeLibrary/getMeTypeLibraryList")
    BaseResponse getMeTypeLibraryList(@RequestBody MeTypeLibraryRequest request);

    @GetMapping("/v1/meTypeLibrary/upOrDownMeTypeLibrarySort")
    BaseResponse upOrDownMeTypeLibrarySort(@RequestParam String metId, @RequestParam String action, @RequestHeader String token);

    @GetMapping("/v1/meTypeLibrary/delMeTypeLibraryById")
    BaseResponse delMeTypeLibraryById(@RequestParam String id);

    /***********************************************材料设备进场报审清单***************************************************/
    @PostMapping("/v1/meEnterApply/createOrUpdateMeEnterApplyDtl")
    BaseResponse createOrUpdateMeEnterApplyDtl(@RequestBody MeEnterApplyDtlRequest request);

    @PostMapping("/v1/meEnterApplyDtl/getMeEnterApplyDtlList")
    BaseResponse getMeEnterApplyDtlList(@RequestBody MeEnterApplyDtlRequest request, @RequestHeader String token);

    /***********************************************材料设备检测指引***************************************************/

    @PostMapping("/v1/meDetectionGuide/createOrUpdateMeDetectionGuide")
    BaseResponse createOrUpdateMeDetectionGuide(@RequestBody MeDetectionGuideModel model);

    @PostMapping("/v1/meDetectionGuide/getMeDetectionGuideList")
    BaseResponse getMeDetectionGuideList(@RequestBody MeDetectionGuideModel model, @RequestHeader String token);

    @GetMapping("/v1/meDetectionGuide/getMeDetectionGuideByDgId")
    BaseResponse getMeDetectionGuideByDgId(@RequestParam String dgId);
    @GetMapping("/v1/meDetectionGuide/approvalData")
    BaseResponse approvalData(@RequestParam String dgId);
    @GetMapping("/v1/meDetectionGuide/setMeDetectionGuideData")
    BaseResponse setMeDetectionGuideData(@RequestParam String dgId);
    /***********************************************见证送检***************************************************/
    @PostMapping("/v1/meWitnessInspection/createOrUpdateMeWitnessInspection")
    BaseResponse createOrUpdateMeWitnessInspection(@RequestBody MeWitnessInspectionRequest request);

    @PostMapping("/v1/meWitnessInspection/updateMeWitnessInspectionDtl")
    BaseResponse updateMeWitnessInspectionDtl(@RequestBody MeWitnessInspectionRequest request);

    @GetMapping("/v1/meWitnessInspection/return2InitWitnessInspectionDtl")
    BaseResponse return2InitWitnessInspectionDtl(@RequestParam String wiId);

    @PostMapping("/v1/meWitnessInspection/getMeWitnessInspectionList")
    BaseResponse getMeWitnessInspectionList(@RequestBody MeWitnessInspectionRequest request, @RequestHeader String token);

    @GetMapping("/v1/meWitnessInspection/getMeWitnessInspectionByWiId")
    BaseResponse getMeWitnessInspectionByWiId(@RequestParam String wiId);

    @GetMapping("/v1/meWitnessInspection/getMeWitnessInspectionByConId")
    BaseResponse getMeWitnessInspectionByConId(@RequestParam String conId);

    @GetMapping("/v1/meWitnessInspection/getMeWitnessInspectionByOrderNo")
    BaseResponse getMeWitnessInspectionByOrderNo(@RequestParam String wiOrderNo);

    /***********************************************见证送检清单***************************************************/
    @PostMapping("/v1/meWitnessDtl/getMeWitnessDtlList")
    BaseResponse getMeWitnessDtlList(@RequestBody MeWitnessDtlRequest request, @RequestHeader String token);

    @PostMapping("/v1/meWitnessDtl/getMeWitnessDtlInspeList")
    BaseResponse getMeWitnessDtlInspeList(@RequestBody MeWitnessDtlRequest request, @RequestHeader String token);

    /***********************************************检测报告***************************************************/

    @PostMapping("/v1/meInspectionReport/createOrUpdateMeInspectionReport")
    BaseResponse createOrUpdateMeInspectionReport(@RequestBody MeInspectionReportRequest request);

    @PostMapping("/v1/meInspectionReport/getMeInspectionReportList")
    BaseResponse getMeInspectionReportList(@RequestBody MeInspectionReportRequest request, @RequestHeader String token);

    @GetMapping("/v1/meInspectionReport/getMeInspectionReportByIrId")
    BaseResponse getMeInspectionReportByIrId(@RequestParam String irId);


    /***********************************************进场验货***************************************************/
    @PostMapping("/v1/meEnterInspection/createOrUpdateMeEnterInspection")
    BaseResponse createOrUpdateMeEnterInspection(@RequestBody MeEnterInspectionRequest request);

    @PostMapping("/v1/meEnterInspection/getMeEnterInspectionList")
    BaseResponse getMeEnterInspectionList(@RequestBody MeEnterInspectionRequest request, @RequestHeader String token);

    @GetMapping("/v1/meEnterInspection/getMeEnterInspectionByEiId")
    BaseResponse getMeEnterInspectionByEiId(@RequestParam String eiId);

    /***********************************************使用报审***************************************************/

    @PostMapping("/v1/meUseApply/createOrUpdateMeUseApply")
    BaseResponse createOrUpdateMeUseApply(@RequestBody MeUseApplyReuqest request);

    @PostMapping("/v1/meUseApply/getMeUseApplyList")
    BaseResponse getMeUseApplyList(@RequestBody MeUseApplyReuqest request, @RequestHeader String token);

    @GetMapping("/v1/meUseApply/getMeUseApplyByUaId")
    BaseResponse getMeUseApplyByUaId(@RequestParam String uaId);

    /***********************************************核价单***************************************************/


    @PostMapping("/v1/mePriceList/createOrUpdateMePriceList")
    BaseResponse createOrUpdateMePriceList(@RequestBody MePriceListDto request);

    @PostMapping("/v1/mePriceList/getMePriceListList")
    BaseResponse getMePriceListList(@RequestBody MePriceListDto request, @RequestHeader String token);

    @GetMapping("/v1/mePriceList/getMePriceListById")
    BaseResponse getMePriceListById(@RequestParam String id);

    /***********************************************材料设备品牌库***************************************************/

    @PostMapping("/v1/meBrandLibrary/createOrUpdateMeBrandLibrary")
    BaseResponse createOrUpdateMeBrandLibrary(@RequestBody MeBrandLibraryDto request);

    @PostMapping("/v1/meBrandLibrary/getMeBrandLibraryList")
    BaseResponse getMeBrandLibraryList(@RequestBody MeBrandLibraryDto request, @RequestHeader String token);

    @GetMapping("/v1/meBrandLibrary/getMeBrandLibraryById")
    BaseResponse getMeBrandLibraryById(@RequestParam String id);

    @GetMapping("/v1/meBrandLibrary/getMeBrandLibraryByMetId")
    BaseResponse getMeBrandLibraryByMetId(@RequestParam String metId);

    @GetMapping("/v1/meBrandLibrary/syncMeBrandLibraryApplyState")
    BaseResponse syncMeBrandLibraryApplyState(@RequestParam String blaId);

    /***********************************************材料设备品牌库建立（更新）审批表***************************************************/

    @PostMapping("/v1/meBrandLibraryApply/createOrUpdateMeBrandLibraryApply")
    BaseResponse createOrUpdateMeBrandLibraryApply(@RequestBody MeBrandLibraryApplyDto request);

    @PostMapping("/v1/meBrandLibraryApply/getMeBrandLibraryApplyList")
    BaseResponse getMeBrandLibraryApplyList(@RequestBody MeBrandLibraryApplyDto request, @RequestHeader String token);

    @GetMapping("/v1/meBrandLibraryApply/getMeBrandLibraryApplyById")
    BaseResponse getMeBrandLibraryApplyById(@RequestParam String id);

    /***********************************************材料设备品牌推荐表***************************************************/

    @PostMapping("/v1/meBrandRecommend/createOrUpdateMeBrandRecommend")
    BaseResponse createOrUpdateMeBrandRecommend(@RequestBody MeBrandRecommendDto request);

    @PostMapping("/v1/meBrandRecommend/getMeBrandRecommendList")
    BaseResponse getMeBrandRecommendList(@RequestBody MeBrandRecommendDto request, @RequestHeader String token);

    @GetMapping("/v1/meBrandRecommend/getMeBrandRecommendById")
    BaseResponse getMeBrandRecommendById(@RequestParam String id);

    /***********************************************项目品牌报审***************************************************/

    /**
     * 统计项目、合同下已有报审单的个数，无记录时返回 0。
     *
     * @param pId   项目 ID
     * @param conId 合同 ID
     * @return 报审单个数
     */
    @GetMapping("/v1/meBrandApproval/getMeBrandApprovalReviewsCount")
    BaseResponse getMeBrandApprovalReviewsCount(@RequestParam("pId") String pId, @RequestParam("conId") String conId);

    @PostMapping("/v1/meBrandApproval/createOrUpdateMeBrandApproval")
    BaseResponse createOrUpdateMeBrandApproval(@RequestBody MeBrandApprovalDto request);

    @PostMapping("/v1/meBrandApproval/getMeBrandApprovalList")
    BaseResponse getMeBrandApprovalList(@RequestBody MeBrandApprovalDto request, @RequestHeader String token);

    @GetMapping("/v1/meBrandApproval/getMeBrandApprovalById")
    BaseResponse getMeBrandApprovalById(@RequestParam String id);

    @GetMapping("/v1/meBrandApproval/getMeBrandApprovalDetailsById")
    BaseResponse getMeBrandApprovalDetailsById(@RequestParam String id);

    /***********************************************认质认价***************************************************/

    @PostMapping("/v1/meQualityPrice/createOrUpdateMeQualityPrice")
    BaseResponse createOrUpdateMeQualityPrice(@RequestBody MeQualityPriceDto request);

    @PostMapping("/v1/meQualityPrice/getMeQualityPriceList")
    BaseResponse getMeQualityPriceList(@RequestBody MeQualityPriceDto request, @RequestHeader String token);

    @GetMapping("/v1/meQualityPrice/getMeQualityPriceById")
    BaseResponse getMeQualityPriceById(@RequestParam String id);


    @GetMapping("/v1/meTypeLibrary/getDownPullListWithTree")
    BaseResponse getMeTypeLibraryDownPullListWithTree();

}
