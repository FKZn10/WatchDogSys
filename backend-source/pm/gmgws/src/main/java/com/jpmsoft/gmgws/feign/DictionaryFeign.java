package com.jpmsoft.gmgws.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.pojo.dto.SelectDto;
import com.jpmsoft.gmgws.pojo.vo.SysInterfaceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


/**
 * 数据字典
 */
@FeignClient(name = "user-producer", contextId = "dictionary")
public interface DictionaryFeign {

    @PostMapping("/v1/SystemManage/getDictionaryTreeByCodes")
    BaseResponse getDictionaryTreeByCodes(@RequestBody SelectDto selectDto);

    @PostMapping("/v1/SystemManage/getDictionaryTree")
    BaseResponse getDictionaryTree(@RequestBody SelectDto selectDto);

    @PostMapping("/system/v1/interface/createOrUpdate")
    BaseResponse createOrUpdate (@RequestBody SysInterfaceDTO request, @RequestHeader String token);

}
