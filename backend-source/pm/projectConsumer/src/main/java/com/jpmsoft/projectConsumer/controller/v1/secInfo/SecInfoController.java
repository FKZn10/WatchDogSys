package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.InvestmentSecInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoTZRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProjectModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecInfoAmountDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "标段信息", value = "标段信息")
@RequestMapping("/v1/secInfo")
public class SecInfoController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改标段信息")
    @PostMapping("/createOrUpdateSecInfo")
    public BaseResponse createOrUpdateSecInfo(@RequestBody SecInfoRequest request) {
        log.info("createOrUpdateSecInfo");
        return secFeign.createOrUpdateSecInfo(request);
    }

    @ApiOperation(value = "获取标段信息列表")
    @PostMapping("/getSecInfoList")
    public BaseResponse getSecInfoList(@RequestBody SecInfoRequest request, @RequestHeader String token) {
        log.info("getSecInfoList");
        return secFeign.getSecInfoList(request, token);
    }

    @ApiOperation(value = "获取标段信息列表(以项目信息为主)")
    @PostMapping("/getSecInfoListGroupByProject")
    public BaseResponse getSecInfoListGroupByProject(@RequestBody SecInfoRequest request, @RequestHeader String token) {
        log.info("getSecInfoListGroupByProject");
        return secFeign.getSecInfoListGroupByProject(request, token);
    }

    @ApiOperation(value = "获取标段信息列表(默认为一个项目对应多个标段,根据项目排序合并)")
    @PostMapping("/getSecInfoListOne2Many")
    public BaseResponse getSecInfoListOne2Many(@RequestBody SecInfoRequest request) {
        log.info("getSecInfoListOne2Many");
        return secFeign.getSecInfoListOne2Many(request);
    }

    @ApiOperation(value = "获取标段信息列表-不鉴权")
    @PostMapping("/getSecInfoListNoAuth")
    public BaseResponse getSecInfoListNoAuth(@RequestBody SecInfoRequest request) {
        log.info("getSecInfoListNoAuth");
        return secFeign.getSecInfoListNoAuth(request);
    }


    @ApiOperation(value = "ID删除标段信息")
    @GetMapping("/deleteSecInfoById")
    public BaseResponse deleteSecInfoById(@RequestParam String secId) {
        log.info("deleteSecInfoById");
        return secFeign.deleteSecInfoById(secId);
    }

    @ApiOperation(value = "项目ID查询标段信息")
    @PostMapping("/getSecInfoByPId")
    public BaseResponse getSecInfoByPId(@RequestBody SecInfoRequest request, @RequestHeader String token) {
        log.info("getSecInfoByPId");
        return secFeign.getSecInfoByPId(request, token);
    }

    @ApiOperation(value = "项目ID查询标段信息(城建定制)")
    @PostMapping("/getCjSecInfoByPId")
    public BaseResponse getCjSecInfoByPId(@RequestBody SecInfoRequest request, @RequestHeader String token) {
        log.info("getCjSecInfoByPId");
        return secFeign.getCjSecInfoByPId(request, token);
    }

    @ApiOperation(value = "ID修改标段信息 招标方式")
    @PostMapping("/updateSecBiddingTypeById")
    public BaseResponse updateSecBiddingTypeById(@RequestBody SecInfoRequest request) {
        log.info("updateSecBiddingTypeById");
        return secFeign.updateSecBiddingTypeById(request);
    }


    @ApiOperation(value = "ID查询标段信息")
    @GetMapping("/getSecInfoById")
    public BaseResponse getSecInfoById(@RequestParam String secId) {
        log.info("getSecInfoById");
        return secFeign.getSecInfoById(secId);
    }

    @ApiOperation(value = "分页获取标段项目列表")
    @PostMapping("/getSecProjectList")
    public BaseResponse getSecProjectList(@RequestBody SecProjectModel request, @RequestHeader String token) {
        log.info("getSecProjectList");
        return secFeign.getSecProjectList(request, token);
    }

    @ApiOperation(value = "分页获取标段项目列表(统计)")
    @PostMapping("/getSecProjectStatisticsList")
    public BaseResponse getSecProjectStatisticsList(@RequestBody SecProjectModel request, @RequestHeader String token) {
        log.info("getSecProjectStatisticsList");
        return secFeign.getSecProjectStatisticsList(request, token);
    }

    @ApiOperation(value = "更新标段流程状态已经业务处理")
    @PostMapping("/updateApproveStatusSecInfo")
    public BaseResponse updateApproveStatusSecInfo(@RequestBody SecInfoRequest request) {
        log.info("updateApproveStatusSecInfo");
        return secFeign.updateApproveStatusSecInfo(request);
    }

    @ApiOperation(value = "判断标段是否可以办理")
    @GetMapping("/secInfoCanHandle")
    public BaseResponse secInfoCanHandle(@RequestParam String secId) {
        log.info("secInfoCanHandle");
        return secFeign.secInfoCanHandle(secId);
    }

    /**
     * 判断名称重复
     * 排除作废
     *
     * @param name
     * @param secId
     * @return
     */
    @ApiOperation(value = "判断名称重复 排除作废")
    @GetMapping("/existDuplicateName")
    public BaseResponse existDuplicateName(@RequestParam String name, @RequestParam(required = false) String secId) {
        log.info("existDuplicateName");
        return secFeign.existDuplicateName(name, secId);
    }


    @ApiOperation(value = "获取项目概算树（项目概算）")
    @PostMapping("/getSecInfoAmountTree")
    public BaseResponse getSecInfoAmountTree(@RequestBody SecInfoAmountDto planAmountDto) {
        log.info("获取项目概算树（项目概算）");
        return secFeign.getSecInfoAmountTree(planAmountDto);
    }

    @ApiOperation(value = "获取筛选字段列表")
    @PostMapping("/getSecInfoTZClassInfo")
    public BaseResponse getSecInfoTZClassInfo(@RequestBody SecInfoTZRequest request, @RequestHeader String token) {
        log.info("getSecInfoTZClassInfo");
        return secFeign.getSecInfoTZClassInfo(request, token);
    }


    @PostMapping("/getInvestmentSecInfo")
    public BaseResponse getInvestmentSecInfo(@RequestBody InvestmentSecInfoRequest request) {
        log.info("getInvestmentSecInfo");
        return secFeign.getInvestmentSecInfo(request);
    }



    @GetMapping("/getSecBiddingTypeByIds")
    public BaseResponse getSecBiddingTypeById(@RequestParam String secIds) {
        log.info("getSecBiddingTypeByIds");
        return secFeign.getSecBiddingTypeByIds(secIds);
    }


    @ApiOperation("招标管理台账，广东工业大学")
    @PostMapping("/getSecInfoGDGY")
    public BaseResponse getSecInfoGDGY(@RequestBody SecInfoRequest request) {
        log.info("getSecInfoGDGY");
        return secFeign.getSecInfoGDGY(request);
    }

    @ApiOperation("通过标段ID获取最新的招标信息（广工）")
    @GetMapping("/getConSecInfoGgById")
    public BaseResponse getConSecInfoGgById(@RequestParam("secId") String secId) {
        log.info("getConSecInfoGgById");
        return secFeign.getConSecInfoGgById(secId);
    }
}
