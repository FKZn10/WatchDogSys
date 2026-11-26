package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.system.SelectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 数据字典
 */
@FeignClient(name = "user-producer", contextId = "dictionary")
public interface DictionaryFeign {

    @PostMapping("/v1/SystemManage/getDictionaryTreeByCodes")
    BaseResponse getDictionaryTreeByCodes(@RequestBody SelectDto selectDto);

    @PostMapping("/v1/SystemManage/getDictionaryTree")
    BaseResponse getDictionaryTree(@RequestBody SelectDto selectDto);

}
