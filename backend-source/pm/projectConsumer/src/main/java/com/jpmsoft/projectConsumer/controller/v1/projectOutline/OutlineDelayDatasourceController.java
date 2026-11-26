package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "体系模板延迟渲染数据源", tags = "体系模板延迟渲染数据源")
@RequestMapping("/v1/projectOutline/delay")
public class OutlineDelayDatasourceController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 处理延迟渲染
     *
     * @param result
     * @param pId
     */
    @ApiOperation("处理延迟渲染")
    @PostMapping("/handleDelayRender")
    public BaseResponse handleDelayRender(@RequestBody(required = false) List<OutlineProjectTempResponse> result, @RequestParam("pId") String pId) {
        log.info("handleDelayRender");
        if (ValidateUtil.isEmpty(result)) {
            result = Collections.emptyList();
        }
        return outlineFeign.handleDelayRender(result, pId);
    }
}