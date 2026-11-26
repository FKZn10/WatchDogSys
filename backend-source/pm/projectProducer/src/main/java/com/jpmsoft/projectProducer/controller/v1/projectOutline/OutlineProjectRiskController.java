package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectRiskRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目管理大纲体系管理风险指标
 *
 * @author tianhelin
 * @date 2025/6/17
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectOutline/projectRisk")
public class OutlineProjectRiskController {
    @Resource
    private IOutlineProjectRiskService outlineProjectRiskService;

    /**
     * 新增项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/createOutlineProjectRisk")
    public BaseResponse createOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token) {
        log.info("createOutlineProjectRisk");
        return outlineProjectRiskService.createOutlineProjectRisk(request, token);
    }

    /**
     * 删除项目管理大纲体系管理风险指标
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteOutlineProjectRiskById")
    public BaseResponse deleteOutlineProjectRiskById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineProjectRiskById");
        return outlineProjectRiskService.deleteOutlineProjectRiskById(id, token);
    }

    /**
     * 修改项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/updateOutlineProjectRisk")
    public BaseResponse updateOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token) {
        log.info("updateOutlineProjectRisk");
        return outlineProjectRiskService.updateOutlineProjectRisk(request, token);
    }

    /**
     * 查询项目管理大纲体系管理风险指标
     *
     * @param id
     * @return
     */
    @GetMapping("/getOutlineProjectRiskById")
    public BaseResponse getOutlineProjectRiskById(@RequestParam("id") String id) {
        log.info("getOutlineProjectRiskById");
        return outlineProjectRiskService.getOutlineProjectRiskById(id);
    }

    /**
     * 分页查询项目管理大纲体系管理风险指标
     *
     * @param request
     * @return
     */
    @PostMapping("/getOutlineProjectRiskPage")
    public BaseResponse getOutlineProjectRiskPage(@RequestBody OutlineProjectRiskRequest request) {
        log.info("getOutlineProjectRiskPage");
        return outlineProjectRiskService.getOutlineProjectRiskPage(request);
    }

    /**
     * 自动保存项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/autoSaveOutlineProjectRisk")
    public BaseResponse autoSaveOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineProjectRisk");
        if (ValidateUtil.isEmpty(request.getId())) {
            return outlineProjectRiskService.createOutlineProjectRisk(request, token);
        } else {
            return outlineProjectRiskService.updateOutlineProjectRisk(request, token);
        }
    }

    /**
     * 根据项目管理大纲id获取项目风险指标
     *
     * @param opmId
     * @return
     */
    @GetMapping("/getOutlineProjectRiskListByOpmId")
    public BaseResponse getOutlineProjectRiskListByOpmId(@RequestParam("opmId") String opmId) {
        log.info("getOutlineProjectRiskListByOpmId");
        return BaseResponse.ok(outlineProjectRiskService.getAllOutlineProjectRiskByOpmId(opmId));
    }
}
