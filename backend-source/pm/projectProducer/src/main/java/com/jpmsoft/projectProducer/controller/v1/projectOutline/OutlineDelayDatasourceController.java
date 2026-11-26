package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineDelayDatasourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 体系模板延迟渲染数据源
 *
 * @author tianhelin
 * @date 2025/08/21
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectOutline/delay")
public class OutlineDelayDatasourceController {
    @Resource
    private IOutlineDelayDatasourceService outlineDelayDatasourceService;

    /**
     * 处理延迟渲染
     *
     * @param result
     * @param pId
     */
    @PostMapping("/handleDelayRender")
    public BaseResponse handleDelayRender(@RequestBody(required = false) List<OutlineProjectTempResponse> result, @RequestParam("pId") String pId) {
        log.info("handleDelayRender");
        if (ValidateUtil.isEmpty(result)) {
            result = Collections.emptyList();
        }
        outlineDelayDatasourceService.handleDelayRender(result, pId);
        return BaseResponse.ok();
    }

    /**
     * 处理所有的延迟渲染
     *
     * @return
     */
    @GetMapping("/handleAllDelayRender")
    public BaseResponse handleAllDelayRender() {
        log.info("handleAllDelayRender");
        outlineDelayDatasourceService.handleAllDelayRender();
        return BaseResponse.ok();
    }
}
