package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.NoMajproWeeklyNewspaperRequest;
import com.jpmsoft.projectProducer.service.other.IMajproWeeklyNewspaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/majpro")
public class MajproController {
    @Resource
    private IMajproWeeklyNewspaperService majproWeeklyNewspaperService;

    /**
     * 获取没在指定时间内填写周报的重点项目
     *
     * @param request
     * @return
     */
    @PostMapping("/getNoMajproWeeklyNewspaperList")
    public BaseResponse getNoMajproWeeklyNewspaperList(@RequestBody NoMajproWeeklyNewspaperRequest request,@RequestHeader String token) {
        log.info("getNoMajproWeeklyNewspaperList");
        return majproWeeklyNewspaperService.getNoMajproWeeklyNewspaperList(request,token);
    }
}
