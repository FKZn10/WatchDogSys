package com.jpmsoft.projectConsumer.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqChangeDto;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqBudgetDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqDtlMeasureDTO;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "project-producer", contextId = "conboq")
public interface ConBoqFeign {

    @PostMapping("/v1/conbop/getBoqList")
    BaseResponse getBoqList(@RequestBody Page page);

    /**
     * 新建
     */
    @PostMapping("/v1/conbop/createBoq")
    BaseResponse createBoq(@RequestBody RequestBean requestBean);

    /**
     * 工程清单查看
     */
    @GetMapping("/v1/conbop/getBoqById")
    BaseResponse getBoqById(@RequestBody RequestBean requestBean);

    /**
     * 工程清单修改
     */
    @PostMapping("/v1/conbop/updateBop")
    BaseResponse updateBop(@RequestBody RequestBean requestBean);

    /**
     * 保存
     */
    @PostMapping("/v1/conbopDtl/saveBoqDtl")
    BaseResponse saveBoqDtl(@RequestBody RequestBean requestBean);
    @PostMapping("/v1/conbopDtl/createOne")
    BaseResponse createOne(@RequestBody ConBoqDtlDto request);

    @PostMapping("/v1/conbopDtl/updateOne")
    BaseResponse updateOne(@RequestBody ConBoqDtlDto request);

    @GetMapping("/v1/conbopDtl/deleteOne")
    BaseResponse deleteOne(@RequestParam String boqdId);

    @PostMapping("/v1/conbopDtl//deleteBath")
    BaseResponse deleteBath(@RequestBody List<String> ids);

    /**
     * 增删改
     */
    @PostMapping("/v1/conbopDtl/createOrUpdateOrdeleteConBoqDtl")
    BaseResponse createOrUpdateOrdeleteConBoqDtl(@RequestBody RequestBean requestBean);

    /**
     * 审核
     *
     * @param conBoqPojo
     * @return
     */
    @PostMapping("/v1/conbop/updateBoqState")
    BaseResponse updateBoqState(@RequestBody RequestBean requestBean);

    /**
     * @param conBoqPojo
     * @return
     */
    @PostMapping("/v1/conbop/updateSnState")
    BaseResponse updateSnState(@RequestBody RequestBean requestBean);

    /**
     * 清除临时表
     */
    @PostMapping("/v1/conbop/deleteConBoqDtlTemp")
    BaseResponse deleteConBoqDtlTemp(@RequestBody RequestBean requestBean);


    /**
     * 查询临时表
     */
    @PostMapping("/v1/conbopDtl/getConBoqDtlTempList")
    BaseResponse getConBoqDtlTempList(@RequestBody RequestBean requestBean);

    @GetMapping("/v1/conbopDtl/getConBoqDtlByConIdAndCode")
    BaseResponse getConBoqDtlByConIdAndCode(@RequestParam String conId, @RequestParam String boqdCode);


    @GetMapping("/v1/conbopDtl/getConBoqDtlTreeByConId")
    BaseResponse getConBoqDtlTreeByConId(@RequestParam String conId,@RequestParam(required = false,name = "boqId") String boqId,@RequestParam(required = false,name = "parentId") String parentId);

    @GetMapping("/v1/conbopDtl/getConBoqDtlParentByIds")
    BaseResponse getConBoqDtlParentByIds(@RequestBody List<String> boqdIds);

    @PostMapping("/v1/conbopDtl/getConBoqDtlBudgetList")
    BaseResponse getConBoqDtlBudgetList(@RequestBody ConBoqBudgetDto request);

    @PostMapping("/v1/conbopDtl/getConBoqDtlAndMeasureTree")
    BaseResponse getConBoqDtlAndMeasureTree(@RequestBody ConBoqDtlMeasureDTO request);
    @GetMapping("/v1/conbopDtl/getConBoqDtlByBoqIdAndParent")
    BaseResponse getConBoqDtlByBoqIdAndParent(@RequestParam String boqId, @RequestParam String parentId);
    /**
     * 导入清单数据
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/v1/conbopDtl/importExcel")
    BaseResponse importExcel(@RequestBody RequestBean requestBean);


    @PostMapping("/v1/conbop/createOrUpdateConBoq")
    BaseResponse createOrUpdateConBoq(@RequestBody ConBoqDto request);

    @PostMapping("/v1/conbop/getConBoqList")
    BaseResponse getConBoqList(@RequestBody ConBoqDto request, @RequestHeader String token);

    @GetMapping("/v1/conbop/getConBoqById")
    BaseResponse getConBoqById(@RequestParam String boqId,@RequestParam(required = false) Boolean queryDtl);


    /**
     * 新增或修改变更清单主表及明细。
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    @PostMapping("/v1/conBoqChange/createOrUpdateConBoqChange")
    BaseResponse createOrUpdateConBoqChange(@RequestBody ConBoqChangeDto request);

    /**
     * 分页查询变更清单。
     *
     * @param request 分页及筛选条件
     * @return 操作结果
     */
    @PostMapping("/v1/conBoqChange/getConBoqChangeList")
    BaseResponse getConBoqChangeList(@RequestBody ConBoqChangeDto request);

    /**
     * 查询变更主表及明细。
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @GetMapping("/v1/conBoqChange/getConBoqChangeById")
    BaseResponse getConBoqChangeById(@RequestParam("id") String id);

    /**
     * 删除变更主表及明细。
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @GetMapping("/v1/conBoqChange/deleteConBoqChangeById")
    BaseResponse deleteConBoqChangeById(@RequestParam("id") String id);

    /**
     * 将审批通过的变更同步到正式清单。
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @GetMapping("/v1/conBoqChange/syncConBoqChange")
    BaseResponse syncConBoqChange(@RequestParam("id") String id);
}
