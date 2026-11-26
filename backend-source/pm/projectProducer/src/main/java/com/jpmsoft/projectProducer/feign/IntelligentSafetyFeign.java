package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractEnterVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/12/9 10:45
 * @Description:
 * @Version: 1.0
 */
@FeignClient(name = "intelligent-safety-service", contextId = "intelligent-safety-service")
public interface IntelligentSafetyFeign {

    @PostMapping("/contractEnter/insert")
    CommonResponse insert(@RequestBody ContractEnterVO contractEnterVO);
}
