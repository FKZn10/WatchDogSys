package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SelectDto;
import com.jpmsoft.userProducer.service.ISysDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/application")
public class ApplicationController {

    @Resource
    private ISysDictionaryService sysDictionaryService;

    /**
     * 数据字典列表
     * parent code筛选
     *
     * @return
     */
    @PostMapping("/getApplicationDictionary")
    public BaseResponse getApplicationDictionary(@RequestBody SelectDto request) {
        log.info("getApplicationDictionary");
        return sysDictionaryService.getApplicationDictionary(request);
    }


}
