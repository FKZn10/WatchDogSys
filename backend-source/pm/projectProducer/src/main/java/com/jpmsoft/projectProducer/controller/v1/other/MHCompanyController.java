package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.vo.other.MHCompanyResultVO;
import com.jpmsoft.projectProducer.service.other.IMHCompanyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/MHCompanyController")
public class MHCompanyController {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IMHCompanyService mhCompanyService;

    /**
     * 根据申请类型查询单位列表（按批次分组）
     *
     * @param applyType 申请类型
     * @param token     用户token
     * @return 批次列表（批次、单位类型、累计合同暂估价、剩余合同暂估价、单位数组）
     */
    @GetMapping("/getCompanyListByApplyType")
    public BaseResponse getCompanyListByApplyType(@RequestParam String applyType,
                                                  @RequestHeader("token") String token) {
        log.info("getCompanyListByApplyType, applyType: {}", applyType);
        List<MHCompanyResultVO> companyListByApplyType = mhCompanyService.getCompanyListByApplyType(applyType, redisUtils.getTeId(token));
        return BaseResponse.ok(companyListByApplyType, companyListByApplyType.size());
    }
}
