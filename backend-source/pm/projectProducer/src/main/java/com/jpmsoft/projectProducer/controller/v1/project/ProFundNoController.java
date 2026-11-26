package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.Project.ProFundNoVo;
import com.jpmsoft.projectProducer.service.project.IProFundNoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/20 15:02
 */
@Slf4j
@RestController
@RequestMapping("/v1/proFundNo")
public class ProFundNoController {

    @Resource
    private IProFundNoService proFundNoService;


    @GetMapping("/getProFundNoByPid")
    public BaseResponse getProFundNoByPid(@RequestParam String pId) {
        List<ProFundNoVo> datas = proFundNoService.getProFundNoByPid(pId);
        return BaseResponse.ok(datas);
    }
}
