package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.NoMajproWeeklyNewspaperRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(value = "江苏住建厅", tags = "江苏住建厅")
@RequestMapping("/v1/majpro")
public class MajproController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 获取没在指定时间内填写周报的重点项目
     *
     * @param request
     * @return
     */
    @ApiOperation("获取没在指定时间内填写周报的重点项目")
    @PostMapping("/getNoMajproWeeklyNewspaperList")
    public BaseResponse getNoMajproWeeklyNewspaperList(@RequestBody NoMajproWeeklyNewspaperRequest request) {
        log.info("getNoMajproWeeklyNewspaperList");
        return baseFeign.getNoMajproWeeklyNewspaperList(request);
    }
}