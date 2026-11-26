package com.jpmsoft.jputils.sms;

import com.jpmsoft.jputils.dto.GmSmsWebServiceDto;
import com.jpmsoft.jputils.utils.AZDG;
import com.jpmsoft.jputils.utils.Model2XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/12 9:14
 */
@Slf4j
@Repository
public class GmSms {

    private final static String xml = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservices.chinagdn.com\">\n" +
            "<soapenv:Header/>\n" +
            "<soapenv:Body>\n" +
            "<web:InsertDownSms soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
            "<username xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">填写帐号</username>\n" +
            "<password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">填写口令</password>\n" +
            "<batch xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"></batch>\n" +
            "<sendbody xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
            "<![CDATA[\n" +
            "<sendbody>\n" +
            "<message>\n" +
            "<orgaddr></orgaddr>\n" +
            "<mobile><![CDATA[填写手机号码]]]]><![CDATA[></mobile>\n" +
            "<content><![CDATA[填写短信内容]]]]><![CDATA[></content>\n" +
            "<sendtime></sendtime>\n" +
            "<needreport>1</needreport>\n" +
            "</message>\n" +
            "<publicContent></publicContent>\n" +
            "</sendbody>\n" +
            "]]>\n" +
            "</sendbody>\n" +
            "</web:InsertDownSms>\n" +
            "</soapenv:Body>\n" +
            "</soapenv:Envelope>";

    //光明项目短息发送批次号
    private static String batch = "";
    @Value("${gmProject.Sms.WebService.uname}")
    //光明项目短息发送批账号
    private String uname;
    @Value("${gmProject.Sms.WebService.password}")
    //光明项目短息发送密码
    private String password;
    ////光明项目短息发送内容主体
    //private final static String sendbody = "<sendbody>{0}</sendbody>";
    @Value("${gmProject.Sms.WebService.url}")
    private String url;
    @Value("${gmProject.Sms.WebService.namespaceurl}")
    private String naneSpace;
    @Value("${gmProject.Sms.WebService.AZDG}")
    private String azdgSecret;

    //    @Async
    public String InsertDownSms(String smsStr) {
        EndpointReference targetEPR = new EndpointReference(
                url);// 接口地址
        Options options = new Options();
        // 下面的一行代码可要可不要（自己测试的结果）
        // options.setAction("urn:BeiJingChaoYang");//调用接口方法
        options.setTo(targetEPR);
        ServiceClient sender = null;
        try {
            sender = new ServiceClient();
            sender.setOptions(options);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            // 设置命名空间，这里的命名空间是和wsdl中的命名空间一样的（从wsdl中获取）
            OMNamespace omNs = fac.createOMNamespace(naneSpace, "");
            // 设置方法名称，这个方法名称(BeiJingChaoYang)和服务里面的方法一致（是需要调用的方法名称）
            OMElement method = fac.createOMElement("InsertDownSms", omNs);
            // 设置入参名称,这个参数名称(dataXml)是和服务提供的方法里面的参数名称一致
            // （例如：要和方法BeiJingChaoYang中dataXml参数名称一致），否则服务方法获取不到该参数值
            OMElement name = fac.createOMElement("username", omNs);
            // 设置入参值
            name.setText(AZDG.encrypt(uname, azdgSecret));
            method.addChild(name);
            OMElement name2 = fac.createOMElement("password", omNs);
            // 设置入参值
            name2.setText(AZDG.encrypt(password, azdgSecret));
            method.addChild(name2);
            OMElement name3 = fac.createOMElement("batch", omNs);
            // 设置入参值
            name3.setText(batch);
            method.addChild(name3);
            OMElement name4 = fac.createOMElement("sendbody", omNs);
            // 设置入参值
            name4.setText(smsStr);
            method.addChild(name4);
            method.build();
            log.info("请求正文：" + method.toString());// print:<getUserById
            // xmlns="http://service.hsinghsu.com"><id>1</id></getUserById>
            OMElement response = sender.sendReceive(method); //获取处理结果
            log.info("_______________________________________________________________________");
            log.info("响应体：" + response);
            log.info("_______________________________________________________________________");
            log.info("响应正文：" + response.getText());
            return response.toString();
        } catch (AxisFault e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }


//        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient(url);
//        // 需要密码的情况需要加上用户名和密码
//        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
//        // 命名空间，方法名
//        QName name = new QName(naneSpace, "InsertDownSms");
//        HashMap<String, Object> map = new HashMap<>();
//        try {
//            // invoke("方法名",参数1,参数2,参数3....);
//            Object[] invoke = client.invoke("InsertDownSms", AZDG.encrypt("0000", uname), AZDG.encrypt("0000", password), batch, MessageFormat.format(sendbody, smsStr));
//            if (invoke != null && invoke.length >= 1) {
//                String result = (String) invoke[0];
//                log.info("InsertDownSms result: {}", result);
//                client.destroy();
//                return result;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("result", "接口调用异常");
//        }
//        client.destroy();
        return null;
    }

    public String doPostSoap(GmSmsWebServiceDto sms) {
        String soapXml = Model2XmlUtil.convertToXml(xml, sms);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000).build();
        httpPost.setConfig(requestConfig);
        // 设置Post请求报文头部
        httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
        httpPost.setHeader("SOAPAction", "Sms");
        // 添加报文内容
        soapXml = soapXml.replaceAll("填写帐号", AZDG.encrypt(uname, azdgSecret)).replaceAll("填写口令", AZDG.encrypt(password, azdgSecret));
        StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
        httpPost.setEntity(data);
        try {
            // 执行请求获取返回报文
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                String result = EntityUtils.toString(httpEntity, "GBK");
                result = result.replaceAll("&lt;", "<")
                        .replaceAll("&gt;", ">")
                        .replaceAll("&quot;", "\"");
                return result;
            }
            closeableHttpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
