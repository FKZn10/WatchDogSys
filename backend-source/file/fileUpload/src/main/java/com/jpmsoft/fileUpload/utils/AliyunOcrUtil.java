package com.jpmsoft.fileUpload.utils;

import com.aliyun.ocr_api20210707.models.RecognizeAllTextResponse;
import com.aliyun.ocr_api20210707.models.RecognizeGeneralStructureResponse;
import com.aliyun.tea.TeaException;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.fileUpload.constants.FileMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/14 9:04
 */
@Slf4j
public class AliyunOcrUtil {

    /**
     * <b>description</b> :
     * <p>使用AK&amp;SK初始化账号Client</p>
     *
     * @return Client
     * @throws Exception
     */
    private static com.aliyun.ocr_api20210707.Client createClient(String accessId, String accessSecret) throws Exception {
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考。
        // 建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html。
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId(accessId)
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(accessSecret);
        // Endpoint 请参考 https://api.aliyun.com/product/ocr-api
        config.endpoint = "ocr-api.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.ocr_api20210707.Client(config);
    }

    /**
     * ocr表单票据识别
     *
     * @param filePath 图片地址
     * @param keys     需要识别的字段key 文本
     * @throws Exception
     */
    public static Map<String, String> ocrForm(String accessId, String accessSecret, String filePath, List<String> keys) throws Exception {
        com.aliyun.ocr_api20210707.Client client = AliyunOcrUtil.createClient(accessId, accessSecret);
        // 需要安装额外的依赖库，直接点击下载完整工程即可看到所有依赖。
        java.io.InputStream bodyStream = com.aliyun.darabonba.stream.Client.readFromFilePath(filePath);
        com.aliyun.ocr_api20210707.models.RecognizeGeneralStructureRequest recognizeGeneralStructureRequest = new com.aliyun.ocr_api20210707.models.RecognizeGeneralStructureRequest()
                .setKeys(keys)
                .setBody(bodyStream);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            RecognizeGeneralStructureResponse response = client.recognizeGeneralStructureWithOptions(recognizeGeneralStructureRequest, runtime);
            log.info("请求响应内容body:"+JacksonUtils.toJSONString(response));
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                Object body = response.getBody().getData().subImages.get(0).kvInfo.getData();
                Map<String, String> map = JacksonUtils.parseObject(JacksonUtils.toJSONString(body), Map.class);
                return map;
            } else throw new JPMException(FileMsg.ocrRequestError);
        } catch (Exception _error) {
            log.error(_error.getMessage());
            throw new JPMException(FileMsg.ocrRequestError);
        }
    }


    /**
     * OCR统一识别
     */
    public static void ocr(String accessId, String accessSecret, String filePath) throws Exception {
        com.aliyun.ocr_api20210707.Client client = AliyunOcrUtil.createClient(accessId, accessSecret);
        java.io.InputStream bodyStream = com.aliyun.darabonba.stream.Client.readFromFilePath(filePath);
        com.aliyun.ocr_api20210707.models.RecognizeAllTextRequest recognizeAllTextRequest = new com.aliyun.ocr_api20210707.models.RecognizeAllTextRequest()
                .setOutputBarCode(false)
                .setOutputQrcode(false)
                .setOutputFigure(false)
                .setType("Advanced")
                .setBody(bodyStream);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            RecognizeAllTextResponse response = client.recognizeAllTextWithOptions(recognizeAllTextRequest, runtime);
            log.info(JacksonUtils.toJSONString(response));
        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}
