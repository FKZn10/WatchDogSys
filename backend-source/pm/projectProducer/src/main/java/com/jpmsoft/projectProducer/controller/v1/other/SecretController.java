package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegister;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.project.IPreProjectRegisterService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 密改刷新数据
 */
@Slf4j
@RestController
@RequestMapping("/v1/secret")
public class SecretController {

    @Resource
    private ISecBiddingPlanService secBiddingPlanService;
    @Resource
    private IPreProjectRegisterService preProjectRegisterService;

    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigService sysConfigService;

    @GetMapping("/refreshSecBiddingPlanSign")
    public BaseResponse refreshSecBiddingPlanSign() {
        log.info("refreshSecBiddingPlanSign");
        return secBiddingPlanService.refreshSecBiddingPlanSign();
    }

    @GetMapping("/refreshPreProjectRegisterSign")
    public BaseResponse refreshPreProjectRegisterSign() {
        log.info("refreshPreProjectRegisterSign");
        List<PreProjectRegister> prs = preProjectRegisterService.list();
        if (!CollectionUtils.isEmpty(prs)) {
            InterfaceEncDecDTO conf = sysConfigService.getInterfaceEncDec();
            prs.forEach(p -> {
                p.setSign(apiUtilsService.sign(conf, conf.getPrSign(), p));
            });
            preProjectRegisterService.updateBatchById(prs, 200);
        }
        return BaseResponse.ok();
    }


    @GetMapping("/refreshPreProjectSign")
    public BaseResponse refreshPreProjectSign() {
        log.info("refreshPreProjectSign");
        List<PreProject> prs = preProjectService.list();
        if (!CollectionUtils.isEmpty(prs)) {
            InterfaceEncDecDTO conf = sysConfigService.getInterfaceEncDec();
            prs.forEach(p -> {
                p.setSign(apiUtilsService.sign(conf, conf.getProjectSign(), p));
            });
            preProjectService.updateBatchById(prs, 200);
        }
        return BaseResponse.ok();
    }


}
