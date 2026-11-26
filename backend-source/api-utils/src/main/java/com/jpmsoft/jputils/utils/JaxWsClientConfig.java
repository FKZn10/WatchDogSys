package com.jpmsoft.jputils.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;


@Slf4j
@Repository
public class JaxWsClientConfig {

    @Value("${gmProject.Sms.WebService.url}")
    private String url;

//    public Client client() {
//        try {
//            // 创建动态客户端
//            JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
//            //CommonConstant.PUBLIC_SECURITY_URL为连接的url,如http://10.189.200.170:9201/wharfWebService/services/WharfService?wsdl
//            log.info("publicsecurity webService url : {}", url);
//            //创建client
//            Client client = clientFactory.createClient(url);
//            HTTPConduit conduit = (HTTPConduit) client.getConduit();
//            HTTPClientPolicy policy = new HTTPClientPolicy();
//            policy.setAllowChunking(false);
//            // 连接服务器超时时间 10秒
//            policy.setConnectionTimeout(10000);
//            // 等待服务器响应超时时间 20秒
//            policy.setReceiveTimeout(20000);
//            conduit.setClient(policy);
//            return client;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            log.error("光明项目短信服务，获取webService Client失败！" + ex.getMessage());
//        }
//        return null;

//    }

    /**
     * 判断XML内是否存在指定节点
     *
     * @param xml  xml内容
     * @param node 节点名称
     * @return
     */
    public static Boolean havingXmlNode(String xml, String node) {
        log.info(xml);
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
            log.info("是否转XML成功！" + doc.asXML());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (doc != null) {
            List<Node> nodes = doc.selectNodes(node);
            if (nodes.size() > 0)
                return true;
            else
                return false;
        } else return false;
    }
}
