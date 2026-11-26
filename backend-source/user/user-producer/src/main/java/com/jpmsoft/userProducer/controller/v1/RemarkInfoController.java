package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.RemarkInfoDto;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.userProducer.service.RemarkInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yb.Sun
 * @date 2024/10/25 09:21
 */
@Slf4j
@RestController
@RequestMapping("/v1/RemarkInfo")
public class RemarkInfoController {
    @Resource
    private RemarkInfoService remarkInfoService;

    //插入备注信息
    @PostMapping("/insertRemarkInfo")
    public BaseResponse insertRemarkInfo(@RequestBody RemarkInfoDto request) {
        log.info("insertRemarkInfo");
        log.info("request:{}", JacksonUtils.toJSONString(request));
        return remarkInfoService.insertRemarkInfo(request);
    }
    //查询备注信息
    @PostMapping("/getRemarkInfoList")
    public BaseResponse getRemarkInfoList(@RequestBody RemarkInfoDto request) {
        log.info("getRemarkInfoList");
        log.info("request:{}", JacksonUtils.toJSONString(request));
        return remarkInfoService.getRemarkInfoList(request);
    }

}
