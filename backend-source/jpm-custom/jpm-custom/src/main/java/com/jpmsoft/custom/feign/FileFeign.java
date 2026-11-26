package com.jpmsoft.custom.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.RefershFileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 文件服务
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/8 9:28
 */
@FeignClient(name = "file-server", contextId = "file-server")
public interface FileFeign {

    @PostMapping("/fileRecord/refreshRelationFileBath")
    BaseResponse refreshRelationFileBath(@RequestBody List<RefershFileDto> request);
}
