package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.SignAndVerifyDTO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlan;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/8/8 14:43
 */
@Slf4j
@RestController
@RequestMapping("/v1/refreshSign")
public class RefreshSignController {

    @Resource
    private ISecBiddingPlanService secBiddingPlanService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @GetMapping("/refreshSecBp")
    public BaseResponse refreshSecBp() {
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        if (ied.getSecSign()) {
            List<SecBiddingPlan> users = secBiddingPlanService.list();
            List<SecBiddingPlan> nosign = users.stream().filter(f -> ValidateUtil.isEmpty(f.getBpSign())).collect(Collectors.toList());
            nosign.forEach(n -> {
                if (ValidateUtil.isNotEmpty(ied.getSignUrl())) {
                    SignAndVerifyDTO sg = new SignAndVerifyDTO();
                    sg.setContent(n.signContent());
                    BaseResponse res = apiUtilsService.sendPost(ied.getSignUrl(), sg);
                    if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                        n.setBpSign(res.getData().toString());
                    }
                }
            });
            secBiddingPlanService.updateBatchById(nosign);
        }
        return BaseResponse.ok();
    }

}
