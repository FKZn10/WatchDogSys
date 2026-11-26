package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.ChatCompletionModel;
import com.jpmsoft.userProducer.pojo.dto.AIDockConfigInfoDTO;
import com.jpmsoft.userProducer.pojo.dto.AnswerDTO;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionRequest;
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionResponse;
import io.github.pigmesh.ai.deepseek.core.chat.ResponseFormatType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.*;

/**
 * deepseek
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/13 9:56
 */
@Slf4j
@RestController
@Api(tags = "deepseek-ai", value = "deepseek-ai")
@RequestMapping("/v1/ai")
public class DeepSeekController {

    @Autowired
    private DeepSeekClient deepSeekClient;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource(name = "restTemplateApi")
    private RestTemplate restTemplateApi;



    @ApiOperation(value = "JSON问答")
    @PostMapping(value = "/chatJson")
    public BaseResponse chatJson(@RequestBody AnswerDTO data) {
        AIDockConfigInfoDTO conf = sysConfigEntityService.getAIDockConfigInfo();
        DeepSeekClient dsc = DeepSeekClient.builder().
                baseUrl(conf.getUrl()).openAiApiKey(conf.getApiKey())
                .model(conf.getModel()).build();
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(conf.getModel())
                .addSystemMessage(data.getSystemPrompt())
                .addUserMessage(data.getUserPromp())
                .responseFormat(ResponseFormatType.JSON_OBJECT)
                .build();
        ChatCompletionResponse completionResponse = dsc.chatCompletion(request).execute();
        if (!completionResponse.choices().isEmpty())
            return BaseResponse.ok(completionResponse.choices().get(0).message().content());
        else return BaseResponse.fail("Ai模型响应超时或失败！");
    }


    @ApiOperation(value = "普通问答")
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatCompletionResponse> chat(String prompt) {
        AIDockConfigInfoDTO conf = sysConfigEntityService.getAIDockConfigInfo();
        DeepSeekClient dsc = DeepSeekClient.builder().
                baseUrl(conf.getUrl()).openAiApiKey(conf.getApiKey())
                .model(conf.getModel()).build();
        return dsc.chatFluxCompletion(prompt);
    }

    @ApiOperation(value = "普通问答")
    @GetMapping(value = "/bdchat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatCompletionResponse> bdchat(String prompt) {
        return deepSeekClient.chatFluxCompletion(prompt);
    }

    @ApiOperation(value = "问答，可开启深度思考")
    @GetMapping(value = "/chat/advanced", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatCompletionResponse> chatAdvanced(String prompt, @RequestParam(required = false) Boolean dk) {

        ChatCompletionRequest.Builder builder = ChatCompletionRequest.builder();
        builder.addUserMessage(prompt);
        if (ValidateUtil.isNotNull(dk) && dk) builder.model(ChatCompletionModel.DEEPSEEK_REASONER);
        else builder.model(ChatCompletionModel.DEEPSEEK_CHAT);
        return deepSeekClient.chatFluxCompletion(builder.build());
    }
}
