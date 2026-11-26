package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.StorageChangeDetail;
import com.jpmsoft.common.model.request.SecInfo.InsExpertRequest;
import com.jpmsoft.common.model.request.SecInfo.InsExpertSearchDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionSearchDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.request.StorageChangeRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("v1/insideExpert")
@Api(tags = "内部专家库", value = "内部专家库")
public class InsideExpertController {

    @Autowired
    private SecFeign secFeign;


    @ApiOperation("创建或者修改内部专家")
    @PostMapping("/createOrUpdateInsideExpert")
    public BaseResponse createOrUpdateInsideExpert(@RequestBody InsExpertRequest insExpertRequest) {
        return secFeign.createOrUpdateInsideExpert(insExpertRequest);
    }

    @ApiOperation("查询条件")
    @PostMapping("/getInsExpertClassInfo")
    public BaseResponse getInsExpertClassInfo(@RequestBody InsExpertSearchDTO request) {
        return secFeign.getInsExpertClassInfo(request);
    }


    @ApiOperation("查询非专家库的内部专家")
    @GetMapping("/queryNotInLib")
    public BaseResponse queryNotInLib() {
        return secFeign.queryNotInLib();
    }

    @ApiOperation("查询专家库的内部专家")
    @GetMapping("/queryInLib")
    public BaseResponse queryInLib() {
        return secFeign.queryInLib();
    }


    @ApiOperation("查询非专家库的内部专家分页")
    @GetMapping("/queryNotInLibByPage")
    public BaseResponse queryNotInLibByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return secFeign.queryNotInLibByPage(page,size);
    }

    @ApiOperation("查询专家库的内部专家分页")
    @GetMapping("/queryInLibByPage")
    public BaseResponse queryInLibByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return secFeign.queryInLibByPage(page, size);
    }


    /**
     * @param judgeExtractionSearchDTO
     * @return
     */


    @ApiOperation("抽取专家")
    @PostMapping("/chooseExpert")
    public BaseResponse chooseExpert(@RequestBody JudgeExtractionSearchDTO judgeExtractionSearchDTO) {
        return secFeign.chooseExpert(judgeExtractionSearchDTO);
    }

    @ApiOperation("新增或修改评委抽取表")
    @PostMapping("/createOrUpdateJudgeExtraction")
    public BaseResponse createOrUpdateJudgeExtraction(@RequestBody JudgeExtractionDTO judgeExtraction) {
        return secFeign.createOrUpdateJudgeExtraction(judgeExtraction);
    }

    @ApiOperation("删除评委抽取记录")
    @GetMapping("/deleteJudgeExtraction")
    public BaseResponse deleteJudgeExtraction(@RequestParam String judgeExtractionId) {
        return secFeign.deleteJudgeExtraction(judgeExtractionId);
    }

    @ApiOperation("查看评委抽取记录详情")
    @GetMapping("/queryJudgeExtraction")
    public BaseResponse queryJudgeExtraction(@RequestParam String judgeExtractionId) {
        return secFeign.queryJudgeExtraction(judgeExtractionId);
    }

    @ApiOperation("根据标段id查看评委抽取记录详情")
    @GetMapping("/getJudgeExtractionBySecId")
    public BaseResponse getJudgeExtractionBySecId(@RequestParam String secId) {
        return secFeign.getJudgeExtractionBySecId(secId);
    }

    @ApiOperation("分页查询")
    @PostMapping("/queryJudgeExtractionByPage")
    public BaseResponse queryJudgeExtractionByPage(@RequestBody JudgeExtractionSearchDTO request) {
        return secFeign.queryJudgeExtractionByPage(request);
    }

    @ApiOperation("分页查询过滤条件")
    @PostMapping("/getJudgeExtractionClassInfo")
    public BaseResponse getJudgeExtractionClassInfo(@RequestBody JudgeExtractionSearchDTO request, @RequestHeader String token) {
        return secFeign.getJudgeExtractionClassInfo(request, token);
    }

    @ApiOperation("集团专家抽取")
    @PostMapping("/expertExtraction")
    public BaseResponse expertExtraction(@RequestBody JudgeExtractionSearchDTO request) {
        return secFeign.expertExtraction(request);
    }

    @PostMapping("/CreateOrUpdateStorageChangeDetail")
    public BaseResponse CreateOrUpdateStorageChangeDetail(@RequestBody StorageChangeDetail request) {
        return secFeign.CreateOrUpdateStorageChangeDetail(request);
    }

    /**
     * @param exId
     * @return
     */

    @ApiOperation("获取某个专家详情")
    @GetMapping("/getExpertByExId")
    public BaseResponse getExpertByExId(@RequestParam("exId") String exId) {
        return secFeign.getExpertByExId(exId);
    }

    @ApiOperation("专家入库变更")
    @PostMapping("/inLibraryOrChange")
    public BaseResponse inLibraryOrChange(@RequestBody StorageChangeRequest request) {
        return secFeign.inLibraryOrChange(request);
    }

    @ApiOperation("专家入库变更后回写状态")
    @GetMapping("/rewriteInsExpert")
    public BaseResponse rewriteInsExpert(@RequestParam("storageChangeId") String storageChangeId) {
        return secFeign.rewriteInsExpert(storageChangeId);
    }

    @ApiOperation("删除")
    @GetMapping("/deleteInsExpert")
    public BaseResponse deleteInsExpert(@RequestParam("exId") String exId) {
        return secFeign.deleteInsExpert(exId);
    }

    @ApiOperation("驳回")
    @GetMapping("/reject")
    public BaseResponse reject(@RequestParam("exId") String exId) {
        return secFeign.reject(exId);
    }

    @ApiOperation("分页查询内部专家")
    @PostMapping("/queryExpertByPage")
    public BaseResponse queryExpertByPage(@RequestBody InsExpertSearchDTO request) {
        return secFeign.queryExpertByPage(request);
    }

    @ApiOperation("分页查询入库变更记录")
    @GetMapping("/queryStorageChange")
    public BaseResponse queryStorageChange(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return secFeign.queryStorageChange(page, size);
    }

    @ApiOperation("查询入库变更详情")
    @GetMapping("/queryStorageChangeDetail")
    public BaseResponse queryStorageChange(@RequestParam("id") String id) {
        return secFeign.queryStorageDetail(id);
    }

}
