package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.FlowMapper;
import com.jpmsoft.userProducer.pojo.dto.FileTemplateDto;
import com.jpmsoft.userProducer.pojo.dto.FlowDto;
import com.jpmsoft.userProducer.pojo.entity.*;
import com.jpmsoft.userProducer.pojo.vo.*;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import dm.jdbc.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlowImpl extends ServiceImpl<FlowMapper, Flow> implements IFlowManageService {

    @Resource
    private RedisUtils redisUtils;

    private static String codestr = "role,";
    private static String pcodestr = "department_position,";
    private static String dcodestr = "department_position,";
    @Resource
    ISysCompanyService sysCompanyService;
    @Resource
    private IFlowActivityService flowActivityService;
    @Resource
    private IFlowUserService flowUserService;
    @Resource
    private IFlowRuleService flowRuleService;
    @Resource
    private ISysDepartmentService sysDepartmentService;
    @Resource
    private ISysPositionService sysPositionService;
    @Resource
    private ISysRoleService sysRoleService;
    @Resource
    private IFileTemplateService fileTemplateService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private FileService fileService;

    /**
     * 获取流程关键字信息列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getFlowtList(FlowDto request) {
        QueryWrapper<Flow> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (!CheckNullUtil.checkString(request.getFlowname()))
            wrapper.like("FlowName", request.getFlowname());
        if (!CheckNullUtil.checkString(request.gettCode()))
            wrapper.eq("T_Code", request.gettCode());
        if (!CheckNullUtil.checkString(request.gettId()))
            wrapper.eq("T_ID", request.gettId());

        if ((!CheckNullUtil.checkString(request.getSortFiled()))) {
            if (request.getSortType().equalsIgnoreCase("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        }
        List<Flow> list = baseMapper.selectList(wrapper);
        if (list.size() == 0) {
            return BaseResponse.ok(list, list.size());
        }
        List<String> pid = list.stream().map(itam -> itam.getFlowproject()).collect(Collectors.toList());
        List<String> tid = list.stream().map(itam -> itam.gettId()).collect(Collectors.toList());
        List<PreProject> prolist = preProjectService.getProjectList(pid);
        List<SysDepartment> dList = sysDepartmentService.getDepartmentList(request.getTeId());
        List<FileTemplateDto> templist = fileTemplateService.getFileTemplateList(tid, request.getTeId());
        List<SysFlowEntityVo> newlist = new ArrayList<>();
        //获取人员的所属项目组织机构信息

        if (ValidateUtil.isNotEmpty(request.getFlowcompany())) {
            List<String> dId = new ArrayList<>();
            List<SysDepartment> cDeps = dList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdCompanyId())
                    && n.getdCompanyId().equals(request.getFlowcompany())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cDeps)) {
                SysDepartment cDep = cDeps.stream().findFirst().get();
                dId = getDepartmentId(dList, cDep.getdId());
                dId.add(cDep.getdId());
            }
            List<String> dIdS = dId;
            list = list.stream().filter(n -> dIdS.contains(n.getFlowcompany()) || request.getpIds().contains(n.getFlowproject())).collect(Collectors.toList());
        }

        for (Flow flowpro : list) {
            SysFlowEntityVo objo = new SysFlowEntityVo();
            objo.setFlowxml(flowpro.getFlowxml());
            objo.setFlowversionnum(flowpro.getFlowversionnum());
            objo.setFlowsolution(flowpro.getFlowsolution());
            objo.setFlowproject(flowpro.getFlowproject());
            objo.setFlowname(flowpro.getFlowname());
            objo.setFlowlastedituName(flowpro.getFlowlastedituName());
            objo.setFlowlastedituId(flowpro.getFlowlastedituId());
            objo.setFlowlastedittime(flowpro.getFlowlastedittime());
            objo.setFlowlasteditreason(flowpro.getFlowlasteditreason());
            objo.setFlowid(flowpro.getFlowid());
            objo.setFlowenable(flowpro.getFlowenable());
            objo.setFlowcreateuName(flowpro.getFlowcreateuName());
            objo.setFlowcreateuId(flowpro.getFlowcreateuId());
            objo.setFlowcreatetime(flowpro.getFlowcreatetime());
            objo.setFlowcompany(flowpro.getFlowcompany());
            objo.setFlowclient(flowpro.getFlowclient());
            objo.settCode(flowpro.gettCode());
            objo.settId(flowpro.gettId());
            objo.setFlowRecordRule(flowpro.getFlowRecordRule());
            if (prolist != null) {
                List<PreProject> pobj = prolist.stream().filter(item -> item.getPId().equals(flowpro.getFlowproject())).collect(Collectors.toList());
                if (pobj.size() > 0) {
                    objo.setpName(pobj.get(0).getPName());
                } else {
                    if (flowpro.getFlowproject().equals("ALL")) {
                        objo.setpName("【所有项目】");
                    }

                }
            } else {
                if (flowpro.getFlowproject().equals("ALL")) {
                    objo.setpName("【所有项目】");
                }
            }
            if (dList != null) {
                List<SysDepartment> cobj = dList.stream().filter(item -> item.getdId().equals(flowpro.getFlowcompany())).collect(Collectors.toList());
                if (cobj.size() > 0) {
                    objo.setcName(cobj.get(0).getdName());
                } else {
                    if (flowpro.getFlowcompany().equals("ALL")) {
                        objo.setcName("【所有部门】");
                    }

                }
            } else {
                if (flowpro.getFlowcompany().equals("ALL")) {
                    objo.setcName("【所有部门】");
                }
            }
            if (templist != null) {
                List<FileTemplateDto> tobj = templist.stream().filter(item -> item.gettId().equals(flowpro.gettId())).collect(Collectors.toList());
                if (tobj.size() > 0) {
                    objo.settName(tobj.get(0).gettName());
                }
            }
            newlist.add(objo);
        }

        List<SysFlowEntityVo> data = newlist.stream().skip((request.getPage() - 1) * request.getSize()).limit(request.getSize()).collect(Collectors.toList());
        return BaseResponse.ok(data, list.size());
    }

    public List<String> getDepartmentId(List<SysDepartment> dList, String dPid) {
        List<String> newIds = new ArrayList<>();
        List<SysDepartment> notList = dList.stream().filter(n -> n.getdParentid().equals(dPid)).collect(Collectors.toList());
        if (notList.size() > 0) {
            notList.forEach(n -> {
                List<String> dIds = getDepartmentId(dList, n.getdId());
                newIds.add(n.getdId());
                newIds.addAll(dIds);
            });
        }
        return newIds;
    }

    /**
     * 新增或修改流程信息，先完成 XML 解析、节点转换和使用中节点校验，再替换旧数据。
     * 数据写入异常继续向外抛出，由事务统一回滚。
     *
     * @param request 流程及节点配置
     * @return 保存结果或校验失败原因
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateFlow(FlowDto request) {
        Flow pojo = new Flow();
        String message = "";
        boolean isSuccess = true;

        //声明xml解析过滤条件
        String urlStr = "http://www.omg.org/spec/BPMN/20100524/MODEL";
        List<String> conditionList = new ArrayList<>();
        conditionList.add("process");
        conditionList.add("startEvent");
        conditionList.add("endEvent");
        conditionList.add("userTask");
        conditionList.add("sendTask");
        conditionList.add("receiveTask");
        conditionList.add("sequenceFlow");
        conditionList.add("exclusiveGateway");
        conditionList.add("parallelGateway");
        conditionList.add("inclusiveGateway");
        List<String> sqlList = new ArrayList<>();
        if (request.getType().equals("2")) {
            request.setFlowxml(CopyFlowXML(request.getFlowxml(), urlStr, conditionList));
            request.setFlowid("");
        }
        List<ElementS> elementsList = xmlParsing(request.getFlowxml(), urlStr, conditionList);

        String FlowID = request.getFlowid() == null ? "" : request.getFlowid();
        String CreateType = "";
        if (CheckNullUtil.checkString(FlowID))      // 新增流程
        {
            CreateType = "1";
            List<ElementS> flowXele = elementsList.stream().filter(n -> n.getTypes().equals("process")).collect(Collectors.toList());
            Element objr = (Element) flowXele.stream().findFirst().orElse(null).getEl();
            FlowID = objr.attributeValue("id");
            request.setFlowversionnum(GetNewFlowVersionNum(request.gettId()) + 1);
            request.setFlowcreatetime(new Date());
            request.setFlowlastedittime(request.getFlowcreatetime());
            request.setFlowlastedituId(request.getFlowcreateuId());
            request.setFlowlastedituName(request.getFlowcreateuName());
            message = "新增成功";
        } else      // 修改流程
        {
            CreateType = "2";

            request.setFlowlastedittime(new Date());
            message = "修改成功";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (CheckNullUtil.checkString(request.getFlowcompany())) {
            request.setFlowcompany("ALL");
        }

        pojo.setFlowenable(request.getFlowenable());
        pojo.settCode(request.gettCode());
        pojo.setFlowlastedituId(request.getFlowlastedituId());
        pojo.setFlowlastedittime(request.getFlowlastedittime());
        pojo.settId(request.gettId());
        pojo.setFlowid(FlowID);
        pojo.setFlowname(request.getFlowname());
        pojo.setFlowxml(request.getFlowxml());
        pojo.setFlowclient(request.getFlowclient());
        pojo.setFlowproject(request.getFlowproject());
        pojo.setFlowsolution(request.getFlowsolution());
        pojo.setFlowcompany(request.getFlowcompany());
        pojo.setFlowcreateuId(request.getFlowcreateuId());
        pojo.setFlowcreateuName(request.getFlowcreateuName());
        pojo.setFlowlastedituName(request.getFlowlastedituName());
        pojo.setFlowversionnum(request.getFlowversionnum());
        pojo.setFlowcreatetime(request.getFlowcreatetime());
        pojo.setFlowlasteditreason(request.getFlowlasteditreason());
        pojo.setTeId(request.getTeId());
        pojo.setFlowRecordRule(request.getFlowRecordRule());

        List<String> NodesList = new ArrayList<>();
        NodesList.add("userTask");
        NodesList.add("sendTask");
        NodesList.add("receiveTask");
        List<ElementS> partNosStart = elementsList.stream().filter(n -> n.getTypes().equals("startEvent")).collect(Collectors.toList());
        List<ElementS> partNosEnd = elementsList.stream().filter(n -> n.getTypes().equals("endEvent")).collect(Collectors.toList());
        List<ElementS> partNosUserTask = elementsList.stream().filter(n -> NodesList.contains(n.getTypes())).collect(Collectors.toList());
        List<ElementS> partNosSequenceFlow = elementsList.stream().filter(n -> n.getTypes().equals("sequenceFlow")).collect(Collectors.toList());
        List<ElementS> partNosExclusiveGateway = elementsList.stream().filter(n -> n.getTypes().equals("exclusiveGateway")).collect(Collectors.toList());
        List<ElementS> partNosParallelGateway = elementsList.stream().filter(n -> n.getTypes().equals("parallelGateway")).collect(Collectors.toList());
        List<ElementS> partNosInclusiveGateway = elementsList.stream().filter(n -> n.getTypes().equals("inclusiveGateway")).collect(Collectors.toList());


        //检查是否有多个开始节点
        if (partNosStart.size() > 1) {
            message = "只允许设置一个开始节点。";
            isSuccess = false;
            return new BaseResponse(isSuccess, message);
        }
        //开始节点
        List<FlowActivity> ActivityList = new ArrayList<>();
        List<FlowUser> FlowUserList = new ArrayList<>();
        List<FlowRule> FlowRuleList = new ArrayList<>();

        for (ElementS lobj : partNosStart) {
            Element node = (Element) lobj.getEl();
            ActivityList.add(GetActivitySQL("startEvent", FlowID, node));
            //flowActivityService.CreateFlowActivity(GetActivitySQL("startEvent", FlowID, node));
            String ActID = node.attributeValue("id");

            String ActUserRole = node.attributeValue("ActUserRole") == null ? "" : node.attributeValue("ActUserRole");
            String[] userRoles = ActUserRole.split("\\|");
            for (int i = 0; i < userRoles.length; i++) {
                if (userRoles[i].trim() != "") {
                    FlowUserList.add(GetActivityRoleSQL(userRoles[i], FlowID, ActID));
                }
            }
        }

        //结束节点

        for (ElementS lobj : partNosEnd) {
            Element node = (Element) lobj.getEl();
            ActivityList.add(GetActivitySQL("endEvent", FlowID, node));
            String ActID = node.attributeValue("id");
            String ActUserRole = node.attributeValue("ActUserRole") == null ? "" : node.attributeValue("ActUserRole");
            String[] userRoles = ActUserRole.split("\\|");
            for (int i = 0; i < userRoles.length; i++) {
                if (userRoles[i].trim() != "") {
                    FlowUserList.add(GetActivityRoleSQL(userRoles[i], FlowID, ActID));
                }
            }
        }

        List<String> ActIdList = new ArrayList<>();
        //用户任务节点
        for (ElementS lobj : partNosUserTask) {
            Element node = (Element) lobj.getEl();
            ActivityList.add(GetActivitySQL(lobj.getTypes(), FlowID, node));
            String ActID = node.attributeValue("id");
            ActIdList.add(ActID);
            String ActUserRole = node.attributeValue("ActUserRole") == null ? "" : node.attributeValue("ActUserRole");
            String[] userRoles = ActUserRole.split("\\|");
            for (int i = 0; i < userRoles.length; i++) {
                if (userRoles[i].trim() != "") {
                    FlowUserList.add(GetActivityRoleSQL(userRoles[i], FlowID, ActID));
                }
            }
        }
        //判断在走流程的节点是否被删除
        if (CreateType.equals("2")) {
            QueryWrapper<FlowActivity> FlowActivityWrapper = new QueryWrapper<>();
            FlowActivityWrapper.eq("ActFlowID", FlowID);
            if (!ActIdList.isEmpty()) {
                FlowActivityWrapper.notIn("ActID", ActIdList);
            }
            FlowActivityWrapper.inSql("ActID", "select TaskCurrentActID from  SYS_Task");
            List<FlowActivity> FlowActivitylist = flowActivityService.list(FlowActivityWrapper);
            if (FlowActivitylist.size() > 0) {
                message = "流程保存失败，正在走流程的节点无法删除。";
                isSuccess = false;
                return new BaseResponse(isSuccess, message);
            }
        }

        //排他网关

        for (ElementS lobj : partNosExclusiveGateway) {
            Element node = (Element) lobj.getEl();
            ActivityList.add(GetActivitySQL("exclusiveGateway", FlowID, node));
        }


        //并行网关
        for (ElementS lobj : partNosParallelGateway) {
            Element node = (Element) lobj.getEl();
            ActivityList.add(GetActivitySQL("parallelGateway", FlowID, node));
        }

        //相容网关
        for (ElementS lobj : partNosInclusiveGateway) {
            Element node = (Element) lobj.getEl();
            ActivityList.add(GetActivitySQL("inclusiveGateway", FlowID, node));
        }


        for (ElementS lobj : partNosSequenceFlow) {
            Element node = (Element) lobj.getEl();
            FlowRuleList.add(GetRuleSQL(FlowID, node));
            //flowRuleService.CreateFlowRule(GetRuleSQL(FlowID, node));
        }
        // 全部解析和校验通过后再删除，避免校验失败或数字转换异常导致旧流程丢失。
        if (CreateType.equals("2")) {
            baseMapper.deleteById(FlowID);
            flowActivityService.DeleteFlowActivity(FlowID);
            flowUserService.DeleteFlowUser(FlowID);
            flowRuleService.DeleteFlowRule(FlowID);
        }
        if (ActivityList.size() > 0) {
            baseMapper.insertFlowActivity(ActivityList, request.getTeId());
        }
        if (FlowUserList.size() > 0) {
            baseMapper.insertFlowUser(FlowUserList, request.getTeId());
        }
        if (FlowRuleList.size() > 0) {
            baseMapper.insertFlowRule(FlowRuleList, request.getTeId());
        }
        //baseMapper.insertFlow(sqlList);
        //如果是主版本，则将同一个文档模板和项目下的其他流程的主版本设置为：否
        if ("1".equals(request.getFlowenable()))
            baseMapper.updateFlowenable(request.getFlowproject(), request.gettCode(), pojo.getFlowid(), request.getFlowcompany());

        baseMapper.insert(pojo);
        message = "数据保存成功。";
        isSuccess = true;
        return new BaseResponse(isSuccess, message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse setEnableDisable(String flowId, String enable, String token) {
        Flow flowObj = baseMapper.selectById(flowId);
        flowObj.setFlowenable(enable);
        flowObj.setFlowlastedittime(new Date());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        flowObj.setFlowcreateuId(user.getId());
        flowObj.setFlowlastedituName(user.getName());
        baseMapper.updateById(flowObj);
        if (flowObj.getFlowenable().equals("1"))
            baseMapper.updateFlowenable(flowObj.getFlowproject(), flowObj.gettCode(), flowObj.getFlowid(), flowObj.getFlowcompany());
        return new BaseResponse(true, "设置成功");
    }


    /**
     * XMlid更换方法
     *
     * @param xml
     * @param url
     * @param conditionList
     * @@return
     */
    public String CopyFlowXML(String xml, String url, List<String> conditionList) {
        String xmlCopy = xml;
        List<ElementS> elementsList = xmlParsing(xml, url, conditionList);

        //流程ID
        List<ElementS> flowXele = elementsList.stream().filter(n -> n.getTypes().equals("process")).collect(Collectors.toList());
        for (ElementS elmentObj : flowXele) {
            Element node = (Element) elmentObj.getEl();
            String oldId = node.attributeValue("id");
            String idP = "Process_" + StrRandom(7, true);
            xmlCopy = xmlCopy.replace(oldId, idP);
        }
        //开始节点
        List<ElementS> partNosStart = elementsList.stream().filter(n -> n.getTypes().equals("startEvent")).collect(Collectors.toList());
        for (ElementS elmentObj : partNosStart) {
            Element node = (Element) elmentObj.getEl();
            String oldId = node.attributeValue("id");
            String idP = "Event_" + StrRandom(7, true);
            xmlCopy = xmlCopy.replace(oldId, idP);
        }
        //结束节点
        List<ElementS> partNosEnd = elementsList.stream().filter(n -> n.getTypes().equals("endEvent")).collect(Collectors.toList());
        for (ElementS elmentObj : partNosEnd) {
            Element node = (Element) elmentObj.getEl();
            String oldId = node.attributeValue("id");
            String idP = "Event_" + StrRandom(7, true);
            xmlCopy = xmlCopy.replace(oldId, idP);
        }

        //用户任务节点
        List<ElementS> partNosUserTask = elementsList.stream().filter(n -> n.getTypes().equals("userTask")).collect(Collectors.toList());
        for (ElementS elmentObj : partNosUserTask) {
            Element node = (Element) elmentObj.getEl();
            String oldId = node.attributeValue("id");
            String idP = "Activity_" + StrRandom(7, true);
            xmlCopy = xmlCopy.replace(oldId, idP);
        }

        //连接线
        List<ElementS> partNosSequenceFlow = elementsList.stream().filter(n -> n.getTypes().equals("sequenceFlow")).collect(Collectors.toList());
        for (ElementS elmentObj : partNosSequenceFlow) {
            Element node = (Element) elmentObj.getEl();
            String oldId = node.attributeValue("id");
            String idP = "Flow_" + StrRandom(7, true);
            xmlCopy = xmlCopy.replace(oldId, idP);
        }

        //网关1
        List<ElementS> partNosExclusiveGateway = elementsList.stream().filter(n -> n.getTypes().equals("exclusiveGateway")).collect(Collectors.toList());
        for (ElementS elmentObj : partNosExclusiveGateway) {
            Element node = (Element) elmentObj.getEl();
            String oldId = node.attributeValue("id");
            String idP = "Gateway_" + StrRandom(7, true);
            xmlCopy = xmlCopy.replace(oldId, idP);
        }

        //网关2
        List<ElementS> partNosParallelGateway = elementsList.stream().filter(n -> n.getTypes().equals("parallelGateway")).collect(Collectors.toList());
        for (ElementS elmentObj : partNosParallelGateway) {
            Element node = (Element) elmentObj.getEl();
            String oldId = node.attributeValue("id");
            String idP = "Gateway_" + StrRandom(7, true);
            xmlCopy = xmlCopy.replace(oldId, idP);
        }

        //网关3
        List<ElementS> partNosInclusiveGateway = elementsList.stream().filter(n -> n.getTypes().equals("inclusiveGateway")).collect(Collectors.toList());
        for (ElementS elmentObj : partNosInclusiveGateway) {
            Element node = (Element) elmentObj.getEl();
            String oldId = node.attributeValue("id");
            String idP = "Gateway_" + StrRandom(7, true);
            xmlCopy = xmlCopy.replace(oldId, idP);
        }
        return xmlCopy;
    }


    /**
     * XMl字符串解析方法
     *
     * @param xmlString
     * @param url
     * @param conditionList
     * @@return
     */
    private List<ElementS> xmlParsing(String xmlString, String url, List<String> conditionList) {
        List<ElementS> list_node = new ArrayList<>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlString);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();// 指向根节点
        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();// 一个Item节点
            if (conditionList.contains(element.getName()) && element.getNamespaceURI() == url) {
                ElementS eobj = new ElementS();
                eobj.setTypes(element.getName());
                eobj.setEl(element);
                list_node.add(eobj);
            }
            list_node.addAll(nodelists(element, url, conditionList));
        }
        return list_node;
    }


    /**
     * XMl节点信息读取方法
     *
     * @param Elements
     * @param url
     * @param conditionList
     * @@return
     */
    private List<ElementS> nodelists(Element Elements, String url, List<String> conditionList) {
        List<ElementS> list_node = new ArrayList<>();
        List<Element> idff_ = Elements.elements();
        if (idff_.size() > 0) {
            for (Element el : idff_) {
                if (conditionList.contains(el.getName()) && el.getNamespaceURI() == url) {
                    ElementS eobj = new ElementS();
                    eobj.setTypes(el.getName());
                    eobj.setEl(el);
                    list_node.add(eobj);
                }
                nodelists(el, url, conditionList);
            }
            return list_node;
        } else {
            return list_node;
        }
    }


    /**
     * 将 XML 节点转换为流程节点，办理时限为空时按零处理，非法时限在写库前拒绝。
     *
     * @param type   节点类型
     * @param flowID 所属流程 ID
     * @param node   XML 节点
     * @return 待保存的流程节点
     * @throws IllegalArgumentException 办理时限不是有限数字时抛出
     */
    private FlowActivity GetActivitySQL(String type, String flowID, Element node) {
        String ActID = node.attributeValue("id");
        String ActName = node.attributeValue("name") == null ? "" : node.attributeValue("name");
        String ActCode = node.attributeValue("ActCode") == null ? "" : node.attributeValue("ActCode");
        String ActType = type;
        String ActOrder = ValidateUtil.isEmpty(node.attributeValue("ActOrder")) ? "0" : node.attributeValue("ActOrder");
        String ActBackType = node.attributeValue("ActBackType") == null ? "" : node.attributeValue("ActBackType");
        String ActFlowID = flowID;
        String ActUserType = node.attributeValue("ActUserType") == null ? "" : node.attributeValue("ActUserType");
        String ActCountersignTitle = node.attributeValue("ActCountersignTitle") == null ? "" : node.attributeValue("ActCountersignTitle");
        String ActRemark = node.attributeValue("ActRemark") == null ? "" : node.attributeValue("ActRemark");
        double ActHour = parseActivityHour(node);
        String ActCodeRole = node.attributeValue("ActCodeRole") == null ? "" : node.attributeValue("ActCodeRole");
        String ActAllowConcurrency = node.attributeValue("ActAllowConcurrency") == null ? "0" : node.attributeValue("ActAllowConcurrency");
        String ActConcurrentMerger = node.attributeValue("ActConcurrentMerger") == null ? "" : node.attributeValue("ActConcurrentMerger");
        String ActUserShowType = node.attributeValue("ActUserShowType") == null ? "" : node.attributeValue("ActUserShowType");
        String ActCanCarbonCopy = node.attributeValue("ActCanCarbonCopy") == null ? "FALSE" : node.attributeValue("ActCanCarbonCopy");
        String ActCanFinish = node.attributeValue("ActCanFinish") == null ? "FALSE" : node.attributeValue("ActCanFinish");
        String ActCanCoordination = node.attributeValue("ActCanCoordination") == null ? "FALSE" : node.attributeValue("ActCanCoordination");
        String ActTemplateCode = node.attributeValue("ActTemplateCode") == null ? "" : node.attributeValue("ActTemplateCode");
        String ActUserFilter = node.attributeValue("ActUserFilter") == null ? "" : node.attributeValue("ActUserFilter");
        String ActNoticeS = node.attributeValue("ActNotice_S") == null ? "" : node.attributeValue("ActNotice_S");
        String ActNoticeST = node.attributeValue("ActNotice_ST") == null ? "" : node.attributeValue("ActNotice_ST");
        String ActNoticeN = node.attributeValue("ActNotice_N") == null ? "" : node.attributeValue("ActNotice_N");
        String ActNoticeNT = node.attributeValue("ActNotice_NT") == null ? "" : node.attributeValue("ActNotice_NT");
        String ActNoticeH = node.attributeValue("ActNotice_H") == null ? "" : node.attributeValue("ActNotice_H");
        String ActNoticeHT = node.attributeValue("ActNotice_HT") == null ? "" : node.attributeValue("ActNotice_HT");
        String ActNoticeW = node.attributeValue("ActNotice_W") == null ? "" : node.attributeValue("ActNotice_W");
        String ActLabSend = node.attributeValue("ActLabSend") == null ? "" : node.attributeValue("ActLabSend");
        String ActLabSave = node.attributeValue("ActLabSave") == null ? "" : node.attributeValue("ActLabSave");
        String ActLabBack = node.attributeValue("ActLabBack") == null ? "" : node.attributeValue("ActLabBack");
        String ActLabFinish = node.attributeValue("ActLabFinish") == null ? "" : node.attributeValue("ActLabFinish");
        String ActLabCo = node.attributeValue("ActLabCo") == null ? "" : node.attributeValue("ActLabCo");
        String ActLabCc = node.attributeValue("ActLabCc") == null ? "" : node.attributeValue("ActLabCc");
        String ActCarbonCopy_WR = node.attributeValue("ActCarbonCopy_WR") == null ? "" : node.attributeValue("ActCarbonCopy_WR");
        String ActCarbonCopy_T = node.attributeValue("ActCarbonCopy_T") == null ? "" : node.attributeValue("ActCarbonCopy_T");
        String ActCarbonCopy_CT = node.attributeValue("ActCarbonCopy_CT") == null ? "" : node.attributeValue("ActCarbonCopy_CT");
        String ActCarbonCopy_Role = node.attributeValue("ActCarbonCopy_Role") == null ? "" : node.attributeValue("ActCarbonCopy_Role");
        String ActCarbonCopy_Post = node.attributeValue("ActCarbonCopy_Post") == null ? "" : node.attributeValue("ActCarbonCopy_Post");
        String ActCarbonCopy_Department = node.attributeValue("ActCarbonCopy_Department") == null ? "" : node.attributeValue("ActCarbonCopy_Department");
        String ActCarbonCopy_User = node.attributeValue("ActCarbonCopy_User") == null ? "" : node.attributeValue("ActCarbonCopy_User");
        String ActCarbonCopy_Sql = node.attributeValue("ActCarbonCopy_Sql") == null ? "" : node.attributeValue("ActCarbonCopy_Sql");
        String ActCarbonCopy_Front_Node = node.attributeValue("ActCarbonCopy_Front_Node") == null ? "" : node.attributeValue("ActCarbonCopy_Front_Node");
        Boolean Act_Allow_Multiple = node.attributeValue("Act_Allow_Multiple") == null ? false : StringUtil.isEmpty(node.attributeValue("Act_Allow_Multiple")) ? false : node.attributeValue("Act_Allow_Multiple").equals(1) ? false : true;
        Boolean Act_Opinion_Required = node.attributeValue("Act_Opinion_Required") == null ? false : StringUtil.isEmpty(node.attributeValue("Act_Opinion_Required")) ? false : node.attributeValue("Act_Opinion_Required").equals("1") ? true : false;
        String Act_View_Pre_Condition = node.attributeValue("Act_View_Pre_Condition") == null ? "" : node.attributeValue("Act_View_Pre_Condition");
        String Act_Business_Description = node.attributeValue("Act_Business_Description") == null ? "" : node.attributeValue("Act_Business_Description");
        //跨系统审批新增字段

        String Act_Sys_Url = node.attributeValue("Act_Sys_Url") == null ? "" : node.attributeValue("Act_Sys_Url");
        String Act_Sys_Iden = node.attributeValue("Act_Sys_Iden") == null ? "" : node.attributeValue("Act_Sys_Iden");
        String Act_Request_Type = node.attributeValue("Act_Request_Type") == null ? "" : node.attributeValue("Act_Request_Type");

        //业务办理提醒信息
        String Act_Business_Start_Node = node.attributeValue("Act_Business_Start_Node") == null ? "" : node.attributeValue("Act_Business_Start_Node");
        String Act_Business_End_Node = node.attributeValue("Act_Business_End_Node") == null ? "" : node.attributeValue("Act_Business_End_Node");
        String Act_Business_Handle_Tips = node.attributeValue("Act_Business_Handle_Tips") == null ? "" : node.attributeValue("Act_Business_Handle_Tips");
        String Act_Business_Menu_Code = node.attributeValue("Act_Business_Menu_Code") == null ? "" : node.attributeValue("Act_Business_Menu_Code");
        //多选并发
        String actConcurrencyType = node.attributeValue("actConcurrencyType") == null ? "" : node.attributeValue("actConcurrencyType");
        String actConcurrencyEnd = node.attributeValue("actConcurrencyEnd") == null ? "" : node.attributeValue("actConcurrencyEnd");
        //自动完成
        String actAutoCompletion = node.attributeValue("ActAutoCompletion") == null ? "" : node.attributeValue("ActAutoCompletion");
        String actTriggertTime = node.attributeValue("ActTriggertTime") == null ? "" : node.attributeValue("ActTriggertTime");
        String actAutoMultiple = node.attributeValue("ActAutoMultiple") == null ? "" : node.attributeValue("ActAutoMultiple");
        String actAutoCompletionK = node.attributeValue("ActAutoCompletionK") == null ? "" : node.attributeValue("ActAutoCompletionK");
        String actAutoCompletionT = node.attributeValue("ActAutoCompletionT") == null ? "" : node.attributeValue("ActAutoCompletionT");
        String actAutoDefaultOpinion = node.attributeValue("ActAutoDefaultOpinion") == null ? "" : node.attributeValue("ActAutoDefaultOpinion");
        String actAutoTriggerField = node.attributeValue("ActAutoTriggerField") == null ? "" : node.attributeValue("ActAutoTriggerField");
        String actFormItemSet = node.attributeValue("actFormItemSet") == null ? "" : node.attributeValue("actFormItemSet");
        String actAllowWithdrawal = node.attributeValue("ActAllowWithdrawal") == null ? "" : node.attributeValue("ActAllowWithdrawal");
        String actAllowVoid = node.attributeValue("ActAllowVoid") == null ? "" : node.attributeValue("ActAllowVoid");
        String actReplaceOperator = node.attributeValue("ActReplaceOperator") == null ? "" : node.attributeValue("ActReplaceOperator");
        String actDefaultOpinion = node.attributeValue("actDefaultOpinion") == null ? "" : node.attributeValue("actDefaultOpinion");
        String actSkipStatus = node.attributeValue("ActSkipStatus") == null ? "" : node.attributeValue("ActSkipStatus");
        String actApprovalHiddenType = node.attributeValue("actApprovalHiddenType") == null ? "" : node.attributeValue("actApprovalHiddenType");
        String actApprovalHiddenCondition = node.attributeValue("actApprovalHiddenCondition") == null ? "" : node.attributeValue("actApprovalHiddenCondition");
        String ActUserPost = node.attributeValue("ActUserPost") == null ? "" : node.attributeValue("ActUserPost");
        String actSelectAll = node.attributeValue("actSelectAll") == null ? "" : node.attributeValue("actSelectAll");

        //增加
        String actApprovalNodeNumber = node.attributeValue("ActApprovalNodeNumber") == null ? "" : node.attributeValue("ActApprovalNodeNumber");
        String actRequestMethod = node.attributeValue("ActRequestMethod") == null ? "" : node.attributeValue("ActRequestMethod");
        String actInterfaceUrl = node.attributeValue("ActInterfaceUrl") == null ? "" : node.attributeValue("ActInterfaceUrl");
        String actRequestParameter = node.attributeValue("ActRequestParameter") == null ? "" : node.attributeValue("ActRequestParameter");
        String ActFormField = node.attributeValue("ActFormField") == null ? "" : node.attributeValue("ActFormField");
        String ActUserAppoint = node.attributeValue("ActUserAppoint") == null ? "" : node.attributeValue("ActUserAppoint");
        String ActFilterFields = node.attributeValue("ActFilterFields") == null ? "" : node.attributeValue("ActFilterFields");
        String actApplyPreState = node.attributeValue("ActApplyPreState") == null ? "" : node.attributeValue("ActApplyPreState");

        String actSmsPost = node.attributeValue("ActSms_Post") == null ? "" : node.attributeValue("ActSms_Post");
        String actSmsDepartment = node.attributeValue("ActSms_Department") == null ? "" : node.attributeValue("ActSms_Department");
        String actSmsRole = node.attributeValue("ActSms_Role") == null ? "" : node.attributeValue("ActSms_Role");
        String actSmsUser = node.attributeValue("ActSms_User") == null ? "" : node.attributeValue("ActSms_User");
        String actSmsSql = node.attributeValue("ActSms_Sql") == null ? "" : node.attributeValue("ActSms_Sql");
        String actSmsRequestMethod = node.attributeValue("ActSmsRequestMethod") == null ? "" : node.attributeValue("ActSmsRequestMethod");
        String actSmsInterfaceUrl = node.attributeValue("ActSmsInterfaceUrl") == null ? "" : node.attributeValue("ActSmsInterfaceUrl");
        String actSmsRequestParameter = node.attributeValue("ActSmsRequestParameter") == null ? "" : node.attributeValue("ActSmsRequestParameter");
        String actSmsTem = node.attributeValue("ActSms_Tem") == null ? "" : node.attributeValue("ActSms_Tem");


        String actMassageType = node.attributeValue("actMassageType") == null ? "" : node.attributeValue("actMassageType");
        String actMassageS = node.attributeValue("actMassageS") == null ? "" : node.attributeValue("actMassageS");
        String actMassageST = node.attributeValue("actMassageST") == null ? "" : node.attributeValue("actMassageST");
        String actMassageN = node.attributeValue("actMassageN") == null ? "" : node.attributeValue("actMassageN");
        String actMassageNT = node.attributeValue("actMassageNT") == null ? "" : node.attributeValue("actMassageNT");
        String actMassageH = node.attributeValue("actMassageH") == null ? "" : node.attributeValue("actMassageH");
        String actMassageSql = node.attributeValue("actMassageSql") == null ? "" : node.attributeValue("actMassageSql");
        String actMassageRequestMethod = node.attributeValue("actMassageRequestMethod") == null ? "" : node.attributeValue("actMassageRequestMethod");
        String actMassageInterfaceUrl = node.attributeValue("actMassageInterfaceUrl") == null ? "" : node.attributeValue("actMassageInterfaceUrl");
        String actMassageRequestParameter = node.attributeValue("actMassageRequestParameter") == null ? "" : node.attributeValue("actMassageRequestParameter");
        String actMassageTem = node.attributeValue("actMassageTem") == null ? "" : node.attributeValue("actMassageTem");

        String actMassagePost = node.attributeValue("actMassagePost") == null ? "" : node.attributeValue("actMassagePost");
        String actMassageUser = node.attributeValue("actMassageUser") == null ? "" : node.attributeValue("actMassageUser");
        String actMassageRole = node.attributeValue("actMassageRole") == null ? "" : node.attributeValue("actMassageRole");
        String actMassageDepartment = node.attributeValue("actMassageDepartment") == null ? "" : node.attributeValue("actMassageDepartment");
        String actGatewayAccess = node.attributeValue("actGatewayAccess") == null ? "" : node.attributeValue("actGatewayAccess");
        String actApprovalDrop = node.attributeValue("actApprovalDrop") == null ? "" : node.attributeValue("actApprovalDrop");
        //自定以流程跳过提示语

        String actNotSkipPrompt = node.attributeValue("actNotSkipPrompt") == null ? "" : node.attributeValue("actNotSkipPrompt");
        String actCustomizeSkipPrompt = node.attributeValue("actCustomizeSkipPrompt") == null ? "" : node.attributeValue("actCustomizeSkipPrompt");

        //是否允许退回当前节点
        String actReturnCurrent= node.attributeValue("actReturnCurrent") == null ? "" : node.attributeValue("actReturnCurrent");
        //允许允许退回当前节点的节点
        String actReturnCurrentOk= node.attributeValue("actReturnCurrentOk") == null ? "" : node.attributeValue("actReturnCurrentOk");
        //节点状态权限
        String actNodeStatusPower= node.attributeValue("actNodeStatusPower") == null ? "" : node.attributeValue("actNodeStatusPower");
        //审批记录权限
        String actRecordPower= node.attributeValue("actRecordPower") == null ? "" : node.attributeValue("actRecordPower");
        //审批记录权限范围
        String actRecordPowerScope= node.attributeValue("actRecordPowerScope") == null ? "" : node.attributeValue("actRecordPowerScope");
        //指定节点
        String actRecordPowerNode= node.attributeValue("actRecordPowerNode") == null ? "" : node.attributeValue("actRecordPowerNode");

        FlowActivity flowActivityobj = new FlowActivity();
        flowActivityobj.setActAllowWithdrawal(actAllowWithdrawal);
        flowActivityobj.setActAllowVoid(actAllowVoid);
        flowActivityobj.setActReplaceOperator(actReplaceOperator);

        flowActivityobj.setActid(ActID);
        flowActivityobj.setActname(ActName);
        flowActivityobj.setActcode(ActCode);
        flowActivityobj.setActtype(ActType);
        flowActivityobj.setActorder(Integer.parseInt(ActOrder));
        flowActivityobj.setActbacktype(ActBackType);
        flowActivityobj.setActflowid(ActFlowID);
        flowActivityobj.setActusertype(ActUserType);
        flowActivityobj.setActcountersigntitle(ActCountersignTitle);
        flowActivityobj.setActremark(ActRemark);
        flowActivityobj.setActhour(ActHour);
        flowActivityobj.setActcoderole(ActCodeRole);
        flowActivityobj.setActallowconcurrency(ActAllowConcurrency);
        flowActivityobj.setActconcurrentmerger(ActConcurrentMerger);
        flowActivityobj.setActusershowtype(ActUserShowType);
        flowActivityobj.setActcancarboncopy(ActCanCarbonCopy);
        flowActivityobj.setActcanfinish(ActCanFinish);
        flowActivityobj.setActcancoordination(ActCanCoordination);
        flowActivityobj.setActtemplatecode(ActTemplateCode);
        flowActivityobj.setActuserfilter(ActUserFilter);
        flowActivityobj.setActnoticeS(ActNoticeS);
        flowActivityobj.setActnoticeSt(ActNoticeST);
        flowActivityobj.setActnoticeN(ActNoticeN);
        flowActivityobj.setActnoticeNt(ActNoticeNT);
        flowActivityobj.setActnoticeH(ActNoticeH);
        flowActivityobj.setActnoticeHt(ActNoticeHT);
        flowActivityobj.setActnoticeW(ActNoticeW);
        flowActivityobj.setActlabsend(ActLabSend);
        flowActivityobj.setActlabsave(ActLabSave);
        flowActivityobj.setActlabback(ActLabBack);
        flowActivityobj.setActlabfinish(ActLabFinish);
        flowActivityobj.setActlabco(ActLabCo);
        flowActivityobj.setActlabcc(ActLabCc);
        flowActivityobj.setActcarboncopyWr(ActCarbonCopy_WR);
        flowActivityobj.setActcarboncopyT(ActCarbonCopy_T);
        flowActivityobj.setActcarboncopyCt(ActCarbonCopy_CT);
        flowActivityobj.setActcarboncopyRole(ActCarbonCopy_Role);
        flowActivityobj.setActcarboncopyPost(ActCarbonCopy_Post);
        flowActivityobj.setActcarboncopyDepartment(ActCarbonCopy_Department);
        flowActivityobj.setActcarboncopyUser(ActCarbonCopy_User);
        flowActivityobj.setActcarboncopySql(ActCarbonCopy_Sql);
        flowActivityobj.setActSysUrl(Act_Sys_Url);
        flowActivityobj.setActSysIden(Act_Sys_Iden);
        flowActivityobj.setActRequestType(Act_Request_Type);
        flowActivityobj.setActcarboncopyFrontNode(ActCarbonCopy_Front_Node);
        flowActivityobj.setActAllowMultiple(Act_Allow_Multiple);
        flowActivityobj.setActOpinionRequired(Act_Opinion_Required);
        flowActivityobj.setActViewPreCondition(Act_View_Pre_Condition);


        flowActivityobj.setActBusinessStartNode(Act_Business_Start_Node);
        flowActivityobj.setActBusinessEndNode(Act_Business_End_Node);
        flowActivityobj.setActBusinessHandleTips(Act_Business_Handle_Tips);
        flowActivityobj.setActBusinessMenuCode(Act_Business_Menu_Code);

        flowActivityobj.setActConcurrencyType(actConcurrencyType);
        flowActivityobj.setActConcurrencyEnd(actConcurrencyEnd);
        flowActivityobj.setActAutoCompletion(actAutoCompletion);
        if (ValidateUtil.isNotEmpty(actTriggertTime)) {
            flowActivityobj.setActTriggertTime(Float.valueOf(actTriggertTime));
        }
        flowActivityobj.setActAutoMultiple(actAutoMultiple);
        flowActivityobj.setActAutoCompletionK(actAutoCompletionK);
        flowActivityobj.setActAutoCompletionT(actAutoCompletionT);
        flowActivityobj.setActAutoDefaultOpinion(actAutoDefaultOpinion);
        flowActivityobj.setActAutoTriggerField(actAutoTriggerField);
        flowActivityobj.setActFormItemSet(actFormItemSet);
        flowActivityobj.setActDefaultOpinion(actDefaultOpinion);
        flowActivityobj.setActSkipStatus(actSkipStatus);
        flowActivityobj.setActBusinessDescription(Act_Business_Description);

        flowActivityobj.setActApprovalHiddenType(actApprovalHiddenType);
        flowActivityobj.setActApprovalHiddenCondition(actApprovalHiddenCondition);
        flowActivityobj.setActUserPost(ActUserPost);
        flowActivityobj.setActSelectAll(actSelectAll);

        flowActivityobj.setActApprovalNodeNumber(actApprovalNodeNumber);
        flowActivityobj.setActRequestMethod(actRequestMethod);
        flowActivityobj.setActInterfaceUrl(actInterfaceUrl);
        flowActivityobj.setActRequestParameter(actRequestParameter);
        flowActivityobj.setActUserAppoint(ActUserAppoint);
        flowActivityobj.setActFilterFields(ActFilterFields);
        flowActivityobj.setActFormField(ActFormField);
        flowActivityobj.setActApplyPreState(actApplyPreState);
        flowActivityobj.setActSmsPost(actSmsPost);
        flowActivityobj.setActSmsDepartment(actSmsDepartment);
        flowActivityobj.setActSmsRole(actSmsRole);
        flowActivityobj.setActSmsUser(actSmsUser);
        flowActivityobj.setActSmsSql(actSmsSql);
        flowActivityobj.setActSmsRequestMethod(actSmsRequestMethod);
        flowActivityobj.setActSmsInterfaceUrl(actSmsInterfaceUrl);
        flowActivityobj.setActSmsRequestParameter(actSmsRequestParameter);
        flowActivityobj.setActSmsTem(actSmsTem);

        flowActivityobj.setActMassageType(actMassageType);
        flowActivityobj.setActMassageS(actMassageS);
        flowActivityobj.setActMassageSt(actMassageST);
        flowActivityobj.setActMassageN(actMassageN);
        flowActivityobj.setActMassageNt(actMassageNT);
        flowActivityobj.setActMassageH(actMassageH);
        flowActivityobj.setActMassageSql(actMassageSql);
        flowActivityobj.setActMassageRequestMethod(actMassageRequestMethod);
        flowActivityobj.setActMassageInterfaceUrl(actMassageInterfaceUrl);
        flowActivityobj.setActMassageRequestParameter(actMassageRequestParameter);
        flowActivityobj.setActMassageTem(actMassageTem);
        flowActivityobj.setActMassagePost(actMassagePost);
        flowActivityobj.setActMassageUser(actMassageUser);
        flowActivityobj.setActMassageRole(actMassageRole);
        flowActivityobj.setActMassageDepartment(actMassageDepartment);
        flowActivityobj.setActGatewayAccess(actGatewayAccess);
        flowActivityobj.setActApprovalDrop(actApprovalDrop);
        flowActivityobj.setActNotSkipPrompt(actNotSkipPrompt);
        flowActivityobj.setActCustomizeSkipPrompt(actCustomizeSkipPrompt);
        flowActivityobj.setActReturnCurrent(actReturnCurrent);
        flowActivityobj.setActReturnCurrentOk(actReturnCurrentOk);
        flowActivityobj.setActNodeStatusPower(actNodeStatusPower);
        flowActivityobj.setActRecordPower(actRecordPower);
        flowActivityobj.setActRecordPowerScope(actRecordPowerScope);
        flowActivityobj.setActRecordPowerNode(actRecordPowerNode);

        return flowActivityobj;
    }


    /**
     * 解析节点办理时限，缺失、空字符串和纯空白均按零处理。
     *
     * @param node 包含 ActHour 属性的 XML 节点
     * @return 有限的办理时限数值
     * @throws IllegalArgumentException 属性为非法数字、NaN 或无穷大时抛出，包含节点 ID
     */
    private double parseActivityHour(Element node) {
        String value = node.attributeValue("ActHour");
        if (value == null || value.trim().isEmpty()) {
            return 0D;
        }
        try {
            double hour = Double.parseDouble(value.trim());
            if (Double.isNaN(hour) || Double.isInfinite(hour)) {
                throw new NumberFormatException("办理时限必须为有限数字");
            }
            return hour;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("流程节点[" + node.attributeValue("id")
                    + "]的办理时限 ActHour 必须为有效数字，当前值：" + value, ex);
        }
    }

    private FlowUser GetActivityRoleSQL(String role, String FlowID, String ActID) {
        FlowUser FlowUser = new FlowUser();
        FlowUser.setFlowuserid(GenerateUUID.getUUID());
        FlowUser.setFlowuseract(ActID);
        FlowUser.setFlowuserflow(FlowID);
        FlowUser.setFlowuserrole(role);
        return FlowUser;
    }


    private FlowRule GetRuleSQL(String flowID, Element node) {
        String RuleID = node.attributeValue("id");
        String RuleName = node.attributeValue("name") == null ? "" : node.attributeValue("name");
        String RuleType = node.attributeValue("RuleType") == null ? "" : node.attributeValue("RuleType");
        String RuleCondition = node.attributeValue("RuleCondition") == null ? "" : node.attributeValue("RuleCondition");
        String RuleSysType = node.attributeValue("RuleType") == null ? "提交" : node.attributeValue("RuleSysType");
        String RuleFlowID = flowID;
        String RuleBeginActID = node.attributeValue("sourceRef") == null ? "" : node.attributeValue("sourceRef");
        String RuleEndActID = node.attributeValue("targetRef") == null ? "" : node.attributeValue("targetRef");

        FlowRule flowRuleobj = new FlowRule();
        flowRuleobj.setRuletype(RuleType);
        flowRuleobj.setRulesystype(RuleSysType);
        flowRuleobj.setRulename(RuleName);
        flowRuleobj.setRuleid(RuleID);
        flowRuleobj.setRuleflowid(RuleFlowID);
        flowRuleobj.setRuleendactid(RuleEndActID);
        flowRuleobj.setRulecondition(RuleCondition);
        flowRuleobj.setRulebeginactid(RuleBeginActID);
        return flowRuleobj;

    }


    public int GetNewFlowVersionNum(String tId) {
        QueryWrapper<Flow> wrapper = new QueryWrapper<>();
        wrapper.eq("T_ID", tId);
        return baseMapper.selectCount(wrapper).intValue();

    }


    /// <summary>
    /// 生成随机字母与数字
    /// </summary>
    /// <param name="Length">生成长度</param>
    /// <param name="Sleep">是否要在生成前将当前线程阻止以避免重复</param>
    /// <returns></returns>
    private String StrRandom(int Length, boolean Sleep) {

        return GenerateUUID.getUUID().substring(0, Length);
    }


    /**
     * 删除流程关键字信息
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse DeleteFlow(FlowDto request) {
        Flow pojo = baseMapper.selectById(request.getFlowid());
        if (CheckNullUtil.checkObject(pojo))
            return BaseResponse.fail(BaseMsg.infoNotExist);
        if (fileService.exists(request.getFlowid(), pojo.gettCode())) {
            return new BaseResponse(false, "当前流程已被使用，无法删除！");
        } else {
            //删除原有的流程步骤信息
            baseMapper.deleteById(request.getFlowid());
            //删除原有的任务节点信息
            flowActivityService.DeleteFlowActivity(request.getFlowid());
            //删除流程节点所有选择的角色信息
            flowRuleService.DeleteFlowRule(request.getFlowid());
            //删除流程节点所有选择的用户信息
            flowUserService.DeleteFlowUser(request.getFlowid());
            return BaseResponse.ok();
        }
    }


    @Override
    public BaseResponse getDepartmentResponseList(String token) {
        String teId = redisUtils.getTeId(token);
        List<SysDepartment> departmentList = sysDepartmentService.getDepartmentList(teId);
        List<SysPosition> position = sysPositionService.GetPositionList();
        List<DepartmentVo> list = new ArrayList<>();
        if (departmentList.size() > 0) {
            for (SysDepartment dmp : departmentList) {
                DepartmentVo entity = new DepartmentVo();
                entity.setCode(dmp.getdCode());
                entity.setdId(dmp.getdId());
                entity.setdName(dmp.getdName());
                entity.setdParentid(dmp.getdParentid());
                List<SysPosition> newList = position.stream().filter(item -> item.getdId().equals(dmp.getdId())).collect(Collectors.toList());
                List<PositionVo> plist = new ArrayList<>();

                for (SysPosition itm : newList) {
                    PositionVo p = new PositionVo();
                    p.setCode(dcodestr + itm.getpId());
                    p.setpId(itm.getpId());
                    p.setpName(itm.getpName());
                    plist.add(p);
                }
                entity.setPositions(plist);
                list.add(entity);
            }
        }

        List<PositionVo> Positionlist = new ArrayList<>();
        for (SysPosition iem : position) {
            PositionVo p = new PositionVo();
            p.setCode(pcodestr + iem.getpId());
            p.setpId(iem.getpId());
            p.setpName(iem.getpName());
            List<SysDepartment> dpobj = departmentList.stream().filter(item -> item.getdId().equals(iem.getdId())).collect(Collectors.toList());
            if (dpobj.size() > 0) {
                p.setdId(dpobj.get(0).getdId());
                p.setdName(dpobj.get(0).getdName());
            }
            Positionlist.add(p);
        }


        List<SysRole> SysRole = sysRoleService.GetRoleList(teId);
        List<RoleVo> RoleResponselist = new ArrayList<>();

        for (com.jpmsoft.userProducer.pojo.entity.SysRole itam : SysRole) {
            RoleVo entity = new RoleVo();
            entity.setCode(codestr + itam.getRoleId());
            entity.setRoleId(itam.getRoleId());
            entity.setRoleName(itam.getRoleName());
            entity.setRoleCpy(itam.getRoleCpy());
            RoleResponselist.add(entity);
        }
        FlowroledpVo data = new FlowroledpVo();
        data.setDepartment(list);
        data.setPosition(Positionlist);
        data.setRole(RoleResponselist);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getFlowXmlByFlowId(String flowId) {
        Flow obj = baseMapper.selectById(flowId);
        if (obj != null) {
            return BaseResponse.ok(obj.getFlowxml());
        } else {
            return BaseResponse.ok("");
        }
    }


    public class ElementS {
        public String types;
        public Object el;

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public Object getEl() {
            return el;
        }

        public void setEl(Object el) {
            this.el = el;
        }
    }

}
