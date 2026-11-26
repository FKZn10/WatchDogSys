package com.jpmsoft.projectProducer.controller.v1.xz;

import com.jpmsoft.projectProducer.service.xz.FundApplicationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:56
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/xz/fundApplicationDetail")
public class FundApplicationDetailController {

    @Autowired
    private FundApplicationDetailService fundApplicationDetailService;
}
