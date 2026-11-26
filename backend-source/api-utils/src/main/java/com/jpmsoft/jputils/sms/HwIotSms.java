package com.jpmsoft.jputils.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jputils.constants.ApiMsg;
import com.jpmsoft.jputils.dto.HwIotSmsBody;
import com.jpmsoft.jputils.dto.HwIotSmsResult;
import com.jpmsoft.jputils.dto.HwSmsDto;
import com.jpmsoft.jputils.dto.MtSmsMessage;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/19 10:03
 */
public class HwIotSms {

    /**
     * 设置不验证主机
     */


    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };


    public static BaseResponse send(HwSmsDto sms) throws Exception {
        //APP接入地址+接口访问URI， IP:Port表示IoT云通信的IP和端口号，IP和端口信息在申请开通接入完成后会自动分配，可在控制台-国内短信-开通接入页面进行查看。
        String url = sms.getUrl();
        //String[] msisdn = {"8612345678911", "8612345678912"}; //手机号，支持传入多个，需要list格式，若只需传入1个，格式为：{"8612345678911"}
        //String smsTemplateId = sms.getTmpCode(); //模板编号
        //当发送短信使用的是无变量模板时，不需要templateParas对象，请删除该对象。
        Map<String, String> templateParas = new HashMap<String, String>();
        //当发送短信使用的是带变量的模板时，需要配置本行代码。VarName表示模板中变量的参数名称，VarValue表示该变量实际要配置的参数值，请按照实际情况配置VarName和VarValue。若需要配置多个变量，请参考本行代码格式进行添加。
        templateParas.put("VarName", "VarValue");
        //String accout = sms.getAccessKey(); //实际账号
        //String passward = sms.getKeySecret(); //实际密码

        // If the request body does not contain the signature name, set signature to null.
        //Map<String, Object> body = buildRequestBody(msisdn, smsTemplateId, templateParas, accout, passward);
        //if (null == body || body.isEmpty()) {
        //    System.out.println("body is null.");
        //    return;
        //}
        HwIotSmsBody body = buildBody(sms);

        HttpsURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        trustAllHttpsCertificates();
        try {
            URL realUrl = new URL(url);
            connection = (HttpsURLConnection) realUrl.openConnection();
            connection.setHostnameVerifier(DO_NOT_VERIFY);
            connection.setDoInput(true); // 设置可输入
            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            // connection.connect();
            ObjectMapper objectMapper = new ObjectMapper();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            pw.write(objectMapper.writeValueAsString(body));
            pw.flush();
            pw.close();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            int status = connection.getResponseCode();
            if (200 == status) { // 200
                is = connection.getInputStream();
            } else { // 400/401
                is = connection.getErrorStream();
            }
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line + "\n");
            }
            connection.disconnect();
            HwIotSmsResult res = JacksonUtils.parseObject(result.toString(), HwIotSmsResult.class);
            if (res != null)
                if (res.getResultCode().equals("0"))
                    return BaseResponse.ok(result);
            return BaseResponse.fail(ApiMsg.sendSmsError, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BaseResponse.fail(ApiMsg.sendSmsError);
    }


    private static HwIotSmsBody buildBody(HwSmsDto sms) {
        HwIotSmsBody body = new HwIotSmsBody();
        //库内存储对应的是账号密码
        body.setAccount(sms.getAccessKey());
        body.setPassword(sms.getKeySecret());
        List<MtSmsMessage> requestLists = new ArrayList<>();
        MtSmsMessage mtSmsMessage = new MtSmsMessage();
        mtSmsMessage.setMobiles(Arrays.asList("86" + sms.getPhoneNumber()));
        mtSmsMessage.setTemplateId(sms.getTmpCode());
        //当发送短信使用的是无变量模板时，不需要给TemplateParas对象赋值。
        if (ValidateUtil.isNotEmpty(sms.getContent()))
            mtSmsMessage.setTemplateParas(JacksonUtils.parseObject(sms.getContent(), Map.class));
        mtSmsMessage.setSignature("【" + sms.getSignName() + "】"); //短信签名
        requestLists.add(mtSmsMessage);
        body.setRequestLists(requestLists);
        return body;
    }


    // msisdn, smsTemplateId, paramValues, countryID
    //public static Map<String, Object> buildRequestBody(String[] msisdn, String smsTemplateId,
    //                                                   Map<String, String> paramValues, String accout, String passward) {
    //    if (null == msisdn || null == smsTemplateId || null == accout || null == passward) {
    //        System.out.println(
    //                "buildRequestBody(): mobiles, templateId or templateParas or account or password is null.");
    //        return null;
    //    }
    //    List<String> mobiles = new ArrayList<String>();
    //    // 多个手机号参数
    //    for (String mobile : msisdn) {
    //        mobiles.add(mobile);
    //    }
    //    Map<String, Object> map = new HashMap<String, Object>();
    //    List<MtSmsMessage> requestLists = new ArrayList<MtSmsMessage>();
    //    MtSmsMessage mtSmsMessage = new MtSmsMessage();
    //    mtSmsMessage.setMobiles(mobiles);
    //    mtSmsMessage.setTemplateId(smsTemplateId);
    //    //当发送短信使用的是无变量模板时，不需要给TemplateParas对象赋值。
    //    mtSmsMessage.setTemplateParas(paramValues);
    //    mtSmsMessage.setSignature("【华为云】"); //短信签名
    //    requestLists.add(mtSmsMessage);
    //    map.put("account", accout);
    //    map.put("password", passward);
    //    map.put("requestLists", requestLists);
    //    return map;
    //}

    static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return;
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}
