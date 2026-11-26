package com.jpmsoft.jputils.utils;

import com.jpmsoft.jputils.dto.GmSmsWebServiceDto;

public class Model2XmlUtil {

    /**
     * 　　* 将对象直接转换成String类型的 XML输出
     * 　　*
     * 　　* @param obj
     * 　　* @return
     */
    public static String convertToXml(String xml, GmSmsWebServiceDto obj) {
        // 创建输出流
//        StringWriter sw = new StringWriter();
//        try {
//            // 利用jdk中自带的转换类实现
//            JAXBContext context = JAXBContext.newInstance(obj.getClass());
//            Marshaller marshaller = context.createMarshaller();
//            // 格式化xml输出的格式
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            // 将对象转换成输出流形式的xml
//            marshaller.marshal(obj, sw);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
        String soapXml = xml.replaceAll("填写短信内容", obj.getContent()).replaceAll("填写手机号码", obj.getMobile());
        return soapXml;
//        StringBuilder sb = new StringBuilder();
//        sb.append("<message>");
//        sb.append("<content>" + obj.getContent() + "</content>");
//        sb.append("<mobile>" + obj.getMobile() + "</mobile>");
//        sb.append("<needreport>" + obj.getNeedreport() + "</needreport>");
//        sb.append("</message>");
//        return sb.toString();
    }
}
