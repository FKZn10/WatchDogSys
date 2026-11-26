package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/monthlyoutput")
public class MonthlyOutputController {

    @Resource
    private InvFeign invFeign;

//    @GetMapping("/changeMonthlyOutputByInvId")
//    public BaseResponse changeMonthlyOutputByInvId(@RequestParam("ipvId") String ipvId) {
//        return invFeign.changeMonthlyOutputByInvId(ipvId);
//    }

//    @PostMapping("/importMonthlyOutput")
//    public BaseResponse importMonthlyOutput(@RequestParam("file") MultipartFile file, @RequestParam("token") String token) {
//        return invFeign.importMonthlyOutput(file,token);
//    }



}
