package com.jpmsoft.fileUpload.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.fileUpload.pojo.entity.AIInfo;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 千问模型调用 - 使用OkHttp基于OpenAI协议
 * 兼容阿里云百炼、Ollama本地部署及其他支持OpenAI协议的模型平台
 */
@Component
public class AiClient {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    
    // Jackson ObjectMapper（线程安全，单例）
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    // 创建OkHttpClient实例（单例）
    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    /**
     * 多模态对话调用（支持图片+文本）
     * @param conf AI配置信息
     * @param base64List 图片Base64列表
     * @param text 文本提示词
     * @return 模型返回的文本结果
     */
    public String simpleMultiModalConversationCall(AIInfo conf, List<String> base64List, String text) {
        return simpleMultiModalConversationCall(conf, base64List, text, false);
    }
    
    /**
     * 多模态对话调用（支持图片+文本 + 深度思考开关）
     * @param conf AI配置信息
     * @param base64List 图片Base64列表
     * @param text 文本提示词
     * @param enableThinking 是否启用深度思考（针对支持该功能的模型，如Qwen3等）
     * @return 模型返回的文本结果
     */
    public String simpleMultiModalConversationCall(AIInfo conf, List<String> base64List, String text, boolean enableThinking) {
        if (conf == null || StringUtils.isEmpty(conf.getUrl())) {
            throw new JPMException("AI配置信息不完整！");
        }
        
        try {
            // 构建请求URL
            String apiUrl = buildApiUrl(conf.getUrl());
            
            // 构建请求体
            String requestBodyJson = buildRequestBody(conf, base64List, text, enableThinking);
            
            // 发送HTTP请求
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(RequestBody.create(requestBodyJson, JSON_MEDIA_TYPE))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + conf.getApiKey())
                    .build();
            
            // 执行请求并获取响应
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "";
                    throw new JPMException("AI服务调用失败: " + response.code() + " " + errorBody);
                }
                
                String responseBody = response.body() != null ? response.body().string() : "";
                return parseResponse(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new JPMException("AI服务调用异常: " + e.getMessage());
        }
    }
    
    /**
     * 构建API URL
     */
    private String buildApiUrl(String baseUrl) {
        // 如果URL已经包含/chat/completions，直接使用
        if (baseUrl.contains("/chat/completions")) {
            return baseUrl;
        }
        // 否则拼接标准路径
        if (baseUrl.endsWith("/")) {
            return baseUrl + "v1/chat/completions";
        }
        return baseUrl + "/v1/chat/completions";
    }
    
    /**
     * 构建请求体（OpenAI格式）
     */
    private String buildRequestBody(AIInfo conf, List<String> base64List, String text) {
        return buildRequestBody(conf, base64List, text, false);
    }
    
    /**
     * 构建请求体（OpenAI格式 + 深度思考支持）
     * @param conf AI配置信息
     * @param base64List 图片Base64列表
     * @param text 文本提示词
     * @param enableThinking 是否启用深度思考
     */
    private String buildRequestBody(AIInfo conf, List<String> base64List, String text, boolean enableThinking) {
        try {
            ObjectNode requestBody = objectMapper.createObjectNode();
            
            // 设置模型名称
            requestBody.put("model", conf.getAiModel());
            
            // 构建messages数组
            ArrayNode messages = objectMapper.createArrayNode();
            ObjectNode userMessage = objectMapper.createObjectNode();
            userMessage.put("role", "user");
            
            // 构建content数组（支持多模态）
            ArrayNode contentArray = objectMapper.createArrayNode();
            
            // 添加图片内容
            if (!CollectionUtils.isEmpty(base64List)) {
                for (String base64Image : base64List) {
                    ObjectNode imageContent = objectMapper.createObjectNode();
                    imageContent.put("type", "image_url");
                    
                    ObjectNode imageUrl = objectMapper.createObjectNode();
                    // 确保Base64格式正确
                    String imageData = formatBase64Image(base64Image);
                    imageUrl.put("url", imageData);
                    
                    imageContent.set("image_url", imageUrl);
                    contentArray.add(imageContent);
                }
            }
            
            // 添加文本内容
            ObjectNode textContent = objectMapper.createObjectNode();
            textContent.put("type", "text");
            textContent.put("text", text);
            contentArray.add(textContent);
            
            userMessage.set("content", contentArray);
            messages.add(userMessage);
            
            requestBody.set("messages", messages);
            
            // 深度思考配置（针对支持该功能的模型，如Qwen3、DeepSeek等）
            if (enableThinking) {
                // 方式1: OpenAI兼容协议的thinking参数
                ObjectNode thinking = objectMapper.createObjectNode();
                thinking.put("type", "enabled");
                requestBody.set("thinking", thinking);
                
                // 方式2: 某些平台使用extra_body
                ObjectNode extraBody = objectMapper.createObjectNode();
                ObjectNode thinkingConfig = objectMapper.createObjectNode();
                thinkingConfig.put("type", "enabled");
                extraBody.set("thinking", thinkingConfig);
                requestBody.set("extra_body", extraBody);
            }
            
            // 可选参数：温度、最大token数等
            if (!StringUtils.isEmpty(conf.getRequestBody())) {
                try {
                    JsonNode extraParams = objectMapper.readTree(conf.getRequestBody());
                    if (extraParams.has("temperature")) {
                        requestBody.put("temperature", extraParams.get("temperature").asDouble());
                    }
                    if (extraParams.has("max_tokens")) {
                        requestBody.put("max_tokens", extraParams.get("max_tokens").asInt());
                    }
                } catch (Exception e) {
                    // 忽略解析错误，使用默认值
                }
            }
            
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new JPMException("构建请求体失败: " + e.getMessage());
        }
    }
    
    /**
     * 格式化Base64图片数据
     */
    private String formatBase64Image(String base64Image) {
        // 如果已经包含data:image前缀，直接返回
        if (base64Image.startsWith("data:image")) {
            return base64Image;
        }
        // 否则添加标准前缀
        return "data:image/png;base64," + base64Image;
    }
    
    /**
     * 解析响应结果
     */
    private String parseResponse(String responseBody) {
        try {
            JsonNode response = objectMapper.readTree(responseBody);
            
            // OpenAI标准响应格式
            if (response.has("choices")) {
                JsonNode choices = response.get("choices");
                if (choices.isArray() && choices.size() > 0) {
                    JsonNode firstChoice = choices.get(0);
                    if (firstChoice.has("message")) {
                        JsonNode message = firstChoice.get("message");
                        if (message.has("content")) {
                            return message.get("content").asText();
                        }
                    }
                }
            }
            
            // 兼容其他可能的响应格式
            if (response.has("output")) {
                JsonNode output = response.get("output");
                if (output.has("text")) {
                    return output.get("text").asText();
                }
            }
            
            throw new JPMException("无法解析AI响应结果");
        } catch (Exception e) {
            e.printStackTrace();
            throw new JPMException("解析AI响应失败: " + e.getMessage());
        }
    }
}
