package com.jpmsoft.fileUpload.utils;


import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.Plan.GanttLinksResponse;
import com.jpmsoft.common.model.response.Plan.GanttPlanResponse;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.ConBoqDtlXmlTreeDto;
import com.jpmsoft.fileUpload.pojo.dto.VueTreeDto;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dom4jXml {

    public static GanttPlanResponse xml2Object(File file) throws Exception {
        //3.获取根节点
        Element rootElement = getRootElement(file);
        Element tasksElement = rootElement.element("Tasks");
        if (tasksElement == null)
            throw new JPMException(FileMsg.fileFormatError);
        List<Element> taskElement = tasksElement.elements("Task");
        if (taskElement == null)
            throw new JPMException(FileMsg.fileFormatError);
        GanttPlanResponse plan = new GanttPlanResponse();
        plan.setPlanName(rootElement.element("Title").getStringValue());
        plan.setPlanStart(DateUtil.String2Date(rootElement.element("StartDate").getStringValue().replace('T', ' '), "yyyy-MM-dd HH:mm:ss"));
        plan.setPlanEnd(DateUtil.String2Date(rootElement.element("FinishDate").getStringValue().replace('T', ' '), "yyyy-MM-dd HH:mm:ss"));
        List<Object> ganttTasks = new ArrayList<>();
        Map<String, Element> predecessorLinks = new HashMap<>();
        Map<String, String> taskIds = new HashMap<>();
        Map<String, String> outlineNumbers = new HashMap<>();
        for (Element e : taskElement) {
            GanttTasksResponse tasksResponse = new GanttTasksResponse();
            //任务Id
            String taskId = GenerateUUID.getUUID();
            tasksResponse.setTaskId(taskId);
            taskIds.put(e.element("UID").getStringValue(), taskId);
            //任务名称
            tasksResponse.setTaskText(e.element("Name").getStringValue());
            //计划开始时间
            tasksResponse.setTaskStartDate(DateUtil.String2Date(e.element("Start").getStringValue().replace('T', ' '), "yyyy-MM-dd HH:mm:ss"));
            //计划完成时间
            tasksResponse.setTaskEndDate(DateUtil.String2Date(e.element("Finish").getStringValue().replace('T', ' '), "yyyy-MM-dd HH:mm:ss"));
            //百分比
            tasksResponse.setTaskProgress(Float.valueOf(e.element("PercentComplete").getStringValue()));
            //节点编码
//            tasksResponse.put("outlineNumber", e.element("OutlineNumber").getStringValue());
//            task.put("predecessorLink",e.element("PredecessorLink").getStringValue());
//            task.put("outlineNumber",e.element("OutlineNumber").getStringValue());
            //一级根节点
            if (e.element("OutlineLevel").getStringValue().equals("1")) {
                tasksResponse.setTaskParent("0");
                outlineNumbers.put(e.element("OutlineNumber").getStringValue(), tasksResponse.getTaskId());
            } else if (!e.element("OutlineLevel").getStringValue().equals("0")) {
                String outlineNumber = e.element("OutlineNumber").getStringValue();
                tasksResponse.setTaskParent(outlineNumbers.get(outlineNumber.substring(0, outlineNumber.length() - 2)));
                outlineNumbers.put(outlineNumber, tasksResponse.getTaskId());
            }
            ganttTasks.add(tasksResponse);
            if (e.element("PredecessorLink") != null)
                predecessorLinks.put(e.element("UID").getStringValue(), e.element("PredecessorLink"));
        }
        plan.setGanttTasks(ganttTasks);

        List<Object> ganttLinks = new ArrayList<>();

        for (Map.Entry<String, Element> entry : predecessorLinks.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            GanttLinksResponse glr = new GanttLinksResponse();
            glr.setLinkTarget(taskIds.get(entry.getKey()));
            glr.setLinkSource(taskIds.get(entry.getValue().element("PredecessorUID").getStringValue()));
            glr.setLinkType(0);
            ganttLinks.add(glr);
        }
        plan.setGanttLinks(ganttLinks);

        return plan;
    }


    /**
     * 首字母转小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] += 32;
            return String.valueOf(chars);
        } else {
            return str;
        }
    }


    public static Element getRootElement(File file) throws Exception {
        //1.创建Reader对象
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = reader.read(file);
        //3.获取根节点
        Element rootElement = document.getRootElement();
        if (rootElement == null)
            throw new JPMException(FileMsg.fileFormatError);
        return rootElement;
    }

//    private static void getNode(String nodeName, Iterator iterator) {
//        while (iterator.hasNext()) {
//            Element stu = (Element) iterator.next();
//            if (stu.getName().equals("Task")) {
//                System.out.println("======遍历子节点======");
//                Iterator iterator1 = stu.elementIterator();
//                while (iterator1.hasNext()) {
//                    Element stuChild = (Element) iterator1.next();
//                    System.out.println("节点名：" + stuChild.getName() + "---节点值：" + stuChild.getStringValue());
//                }
//            } else {
//                getNode(nodeName, stu.elementIterator());
//            }
//        }
//    }
}
