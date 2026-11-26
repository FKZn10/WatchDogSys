package com.jpmsoft.jputils.utils;


import com.aliyun.core.utils.StringUtils;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.jputils.request.InvoiceInfoRequest;
import com.jpmsoft.jputils.response.InvoiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yb.Sun
 * @date 2025/1/16 21:54
 */
@Slf4j
public class InvoiceUtils {


    public static BaseResponse getInvoiceInfo(MultipartFile file, String appCode, String type) {
        if (file==null){
            return BaseResponse.fail("文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return BaseResponse.fail("文件名不能为空");
        }
        String fileType = null;
        fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String host;
        String path;
        String method = "POST";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        host = "https://jmcwpjocr.market.alicloudapi.com";
        path = "/cwpj/ocr/finance-bill";
        log.info("Base64: {}",multipartFileToBase64(file));
        if ("pdf".equalsIgnoreCase(fileType)) {
            bodys.put("pdf", multipartFileToBase64(file));
            bodys.put("pdfNum", "1");
        }else if("odf".equalsIgnoreCase(fileType)){
            bodys.put("ofd", multipartFileToBase64(file));
            bodys.put("ofdNum", "1");
        }else if("png".equalsIgnoreCase(fileType)||"jpg".equalsIgnoreCase(fileType)||"jpeg".equalsIgnoreCase(fileType)){
            bodys.put("image", multipartFileToBase64(file));
        }else {
            return BaseResponse.fail("文件类型无法识别发票信息");
        }
        try {
            /*
              重要提示如下:
              HttpUtils请从
              https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
              下载

              相应的依赖请参照
              https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            log.info(response.toString());
            if (response.getEntity()!=null){
                //获取response的body
                String body = EntityUtils.toString(response.getEntity());
                log.info(body);
                InvoiceResponse invoiceResponse = JacksonUtils.parseObject(body, InvoiceResponse.class);
                log.info(JacksonUtils.toJSONString(invoiceResponse));
                if (invoiceResponse != null ) {
                    if (!invoiceResponse.getSuccess()) {
                        return BaseResponse.fail(invoiceResponse.getMsg());
                    }
                    return BaseResponse.ok(invoiceResponse);
                }else {
                    return BaseResponse.fail("发票识别接口未响应");
                }
            }else {
                return BaseResponse.fail("发票识别接口未响应");
            }
        } catch (Exception e) {
            return BaseResponse.fail(e.getMessage());
        }
    }

    /**
     * MultipartFile转base64
     * @param file
     * @return
     */
    public static String multipartFileToBase64(MultipartFile file) {
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(fileBytes);
    }


    public static MultipartFile getMultipartFile(String s) throws IOException {
        File file = new File(s);
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("copy"+file.getName(),file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),fileInputStream);
        log.info(multipartFile.getName());
        return multipartFile;
    }

    public static BaseResponse checkInvoiceInfo(InvoiceInfoRequest request, String appCode, String normal) {
        if (request==null){
            return BaseResponse.fail("请求参数为空");
        }
        if (StringUtils.isBlank(request.getFphm())){
            return BaseResponse.fail("发票号码为空");
        }
        if (request.getKprq()==null){
            return BaseResponse.fail("开票日期不能为空");
        }

        String host = "https://fapiao.market.alicloudapi.com";
        String path = "/v2/invoice/query";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("fphm", request.getFphm());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getKprq());
        bodys.put("kprq", DateFormatUtil.dateToString(calendar.getTime(), "yyyyMMdd"));
        bodys.put("noTaxAmount", request.getNoTaxAmount()!=null?request.getNoTaxAmount().toString():"");
        bodys.put("checkCode", request.getCheckCode());
        bodys.put("fpdm", request.getFpdm());
        bodys.put("jshj", request.getJshj()!=null?request.getJshj().toString():"");
        log.info("bodys:{}",JacksonUtils.toJSONString(bodys));
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            log.info("response,{}",response.toString());
            //获取response的body
            String body = EntityUtils.toString(response.getEntity());
            //获取response的body
            log.info("获取response的body,{}",body);
            if (StringUtils.isBlank(body)){
                return BaseResponse.fail("发票识别接口未响应");
            }
            InvoiceResponse invoiceResponse = JacksonUtils.parseObject(body, InvoiceResponse.class);
            log.info(JacksonUtils.toJSONString(invoiceResponse));
            if (invoiceResponse != null ) {
                if (!invoiceResponse.getSuccess()) {
                    return BaseResponse.fail(invoiceResponse.getMsg());
                }else {
                    return BaseResponse.ok(invoiceResponse.getData());
                }
            }else {
                return BaseResponse.fail("发票识别接口未响应");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.fail(e.getMessage());
        }
    }
}
