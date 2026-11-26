package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.PositionUserPDto;
import com.jpmsoft.userProducer.pojo.dto.UserPositionPDTO;
import com.jpmsoft.userProducer.service.ISysPositionUserPService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "用户岗位", value = "用户岗位")
@RequestMapping("/v1/OrganizationManage")
public class SysPositionUserPController {
    @Resource
    private ISysPositionUserPService sysPositionUserPService;

    @ApiOperation("查询项目下集团本部项目组织机构人员或者项目管理单位项目组织机构人员信息 jtOrGl入参jt则查询集团本部 ")
    @GetMapping("/getProjectPositionUser")
    public BaseResponse getProjectPositionUser(@RequestParam String pId, @RequestParam String jtOrGl) {
        log.info("getProjectPositionUser");
        return sysPositionUserPService.getProjectPositionUser(pId, jtOrGl);
    }

    @PostMapping("/CreateOrDeletePP_U")
    public BaseResponse CreateOrDeletePP_U(@RequestBody @Validated PositionUserPDto request) {
        log.info("CreateOrDeletePP_U");
        return sysPositionUserPService.CreateOrDeletePP_U(request);
    }

    @ApiOperation("更新用户项目岗位关系")
    @PostMapping("/updatePositionUserP")
    public BaseResponse updatePositionUserP(@RequestBody PositionUserPDto request) {
        log.info("updatePositionUserP");
        return sysPositionUserPService.updatePositionUserP(request);
    }

    @ApiOperation("上移下移用户岗位排序")
    @GetMapping("/updatePupSortUpOrDown")
    public BaseResponse updateSortUpOrDown(@RequestParam String pupId, @RequestParam String pdId, @RequestParam String action) {
        log.info("updateSortUpOrDown");
        return sysPositionUserPService.updateSortUpOrDown(pupId, pdId, action);
    }

    @PostMapping("/createOrDeletePUPByBath")
    public BaseResponse createOrDeletePUPByBath(@RequestBody Map<String, String> datas) {
        log.info("createOrDeletePUPByBath");
        if (datas.containsKey("models")) {
            List<ReportedPersonnelDtlModel> models = JacksonUtils.parseArray(datas.get("models"), ReportedPersonnelDtlModel.class);
            return sysPositionUserPService.createOrDeletePUPByBath(models);
        }
        return BaseResponse.fail(BaseMsg.notReqParam);
    }

    @GetMapping("/getProjectPositionUserGlManager")
    public UserPositionPDTO getProjectPositionUserGlManager(@RequestParam("pId") String pId) {
        log.info("getProjectPositionUserGlManager");
        return sysPositionUserPService.getProjectPositionUserGlManager(pId);
    }
}
