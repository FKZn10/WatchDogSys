package com.jpmsoft.jputils.sms;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.*;
import com.google.gson.Gson;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jputils.constants.ApiMsg;
import com.jpmsoft.jputils.dto.AliyunBathSmsDto;
import com.jpmsoft.jputils.dto.AliyunSmsDto;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * 阿里云短信
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/11 9:28
 */
@Slf4j
public class AliyunSms {

    /**
     * 单条短信发送
     *
     * @param sms 短信
     */
    public static BaseResponse sendSms(AliyunSmsDto sms) {
        AsyncClient client = getAsyncClient(sms);
        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName(sms.getSignName())
                .templateCode(sms.getTmpCode())
                .phoneNumbers(sms.getPhoneNumber())
                .templateParam(sms.getContent())
                .build();
        BaseResponse res;
        try {
            CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
            SendSmsResponse resp = response.get();
            SendSmsResponseBody body = resp.getBody();
            if (body.getCode().equalsIgnoreCase("ok"))
                res = BaseResponse.ok(body);
            else
                res = BaseResponse.fail(ApiMsg.sendSmsError, body);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("阿里云短信发送失败,msg:" + ex.getMessage());
            res = BaseResponse.fail(ApiMsg.sendSmsError);
        }
        client.close();
        return res;
    }

    private static AsyncClient getAsyncClient(AliyunSmsDto sms) {
        // Configure the Client
        return AsyncClient.builder()
                .region(sms.getRegion()) // Region ID
                .credentialsProvider(getCredentialProvider(sms))
                .overrideConfiguration(
                        ClientOverrideConfiguration.create().setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();
    }

    private static StaticCredentialProvider getCredentialProvider(AliyunSmsDto sms) {
        return StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(sms.getAccessKey())
                .accessKeySecret(sms.getKeySecret())
                .build());
    }

    /**************************************************************************************************************/

    private final static String accessKeyId = "";
    private final static String accessKeySecret = "";
    private final static String regionId = "cn-shanghai";

    /**
     * 批量短信发送
     *
     * @param bathSms 短信
     */
    public static void sendBatchSms(AliyunBathSmsDto bathSms) {
        AsyncClient client = getAsyncClient();
        SendBatchSmsRequest sendBatchSmsRequest = SendBatchSmsRequest.builder()
                .phoneNumberJson(new Gson().toJson(bathSms.getPhoneNumbers()))
                .signNameJson(new Gson().toJson(bathSms.getSignNames()))
                .templateCode(bathSms.getTemplateCode())
                .templateParamJson(new Gson().toJson(bathSms.getTemplateParams()))
                .build();
        try {
            CompletableFuture<SendBatchSmsResponse> response = client.sendBatchSms(sendBatchSmsRequest);
            SendBatchSmsResponse resp = response.get();
            System.out.println(new Gson().toJson(resp));
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("阿里云批量短信发送失败,msg:" + ex.getMessage());
        }
        client.close();
    }

    private static StaticCredentialProvider getCredentialProvider() {
        return StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .build());
    }

    private static AsyncClient getAsyncClient() {
        // Configure the Client
        return AsyncClient.builder()
                .region(regionId) // Region ID
                .credentialsProvider(getCredentialProvider())
                .overrideConfiguration(
                        ClientOverrideConfiguration.create().setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();
    }


}
