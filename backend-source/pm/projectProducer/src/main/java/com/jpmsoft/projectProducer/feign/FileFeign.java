package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.RefershFileDto;
import com.jpmsoft.common.model.request.ImgCompressAndRefreshStateDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/jpm/backDelFileByIds")
    BaseResponse backDelFileByIds(@RequestParam String id, @RequestParam String fileIds, @RequestHeader String token);

    @PostMapping("/jpm/backDelFileByRelations")
    BaseResponse backDelFileByRelations(@RequestParam String id, @RequestHeader String token, @RequestBody List<String> relationIds);

    @PostMapping("/fileRecord/refreshImgAndCompress")
    BaseResponse refreshImgAndCompress(@RequestBody List<ImgCompressAndRefreshStateDTO> files,@RequestParam String entityId);

    @PostMapping("/fileRecord/validateFileExist")
    BaseResponse validateFileExist(@RequestBody List<SysFileannex> files, @RequestParam(defaultValue = "all") String fileType);
}
