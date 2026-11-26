package com.jpmsoft.fileUpload.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.AiPromptGenerateDTO;
import com.jpmsoft.fileUpload.pojo.dto.AnswerDTO;
import com.jpmsoft.fileUpload.pojo.dto.SelectDto;
import com.jpmsoft.fileUpload.pojo.entity.SysInterface;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/28 9:58
 */
@FeignClient(name = "user-producer", contextId = "user-producer")
public interface UserFeign {

    @PostMapping(value = "/v1/ai/chatJson")
    BaseResponse chatJson(@RequestBody AnswerDTO request);

    @PostMapping(value = "/v1/aiInfo/getAiPromptXmlByBody")
    BaseResponse getAiPromptXmlByBody(@RequestBody AiPromptGenerateDTO apg);

    /**
     * AI提示词XML生成
     *
     * @param type
     * @return
     */
    @GetMapping(value = "/v1/aiInfo/getAiPromptXml")
    BaseResponse getAiPromptXml(@RequestParam String type);

    @PostMapping("/v1/seqSet/generateCodeByTCode")
    String generateCodeByTCode(@RequestParam String tCode, @RequestBody Object request, @RequestHeader(required = false) String token);

    @PostMapping("/v1/seqSet/refershAutoNo")
    BaseResponse refershAutoNo(@RequestParam String tCode, @RequestBody Object request, @RequestHeader(required = false) String token);

    @GetMapping("/v1/seqSet/sequenceRestEnable")
    Boolean sequenceRestEnable(@RequestParam String tCode, @RequestHeader(required = false) String token);

    @GetMapping("/v1/SystemManage/getPowerTreeList")
    BaseResponse getPowerTreeList(@RequestHeader String token);

    @GetMapping("/system/v1/interface/getAll")
    List<SysInterface> getAll();

    @PostMapping("/v1/SystemManage/getDictionaryTreeByCodes")
    BaseResponse getDictionaryTreeByCodes(@RequestBody SelectDto selectDto);
}
