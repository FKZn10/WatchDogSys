package com.jpmsoft.gmgws.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.mapper.InfrastrExcelMapper;
import com.jpmsoft.gmgws.pojo.entity.ConPayApplyFlow;
import com.jpmsoft.gmgws.pojo.entity.InvProjectPlan;
import com.jpmsoft.gmgws.pojo.entity.Task;
import com.jpmsoft.gmgws.pojo.vo.ContractTotlaVo;
import com.jpmsoft.gmgws.pojo.vo.ContractVo;
import com.jpmsoft.gmgws.pojo.vo.PreProjectVo;
import com.jpmsoft.gmgws.pojo.vo.StatisticsVo;
import com.jpmsoft.gmgws.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class InfrastrExcelServiceImpl  implements InfrastrExcelService {


    @Autowired
    private InfrastrExcelMapper  infrastrExcelMapper;


    @Autowired
    private ConPayApplyFlowService conPayApplyFlowService;


    @Autowired
    private PreProjectService preProjectService;

    @Autowired
    private ConPayService conPayService;

    @Autowired
    private ConContractService conContractService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private TaskService taskService;


    @Autowired
    private InvProjectPlanService invProjectPlanService;
    //查询合同支付
    @Override
    public BaseResponse getContractList(StatisticsVo statisticsVo) {
        if(statisticsVo.getContractAmts() != null && statisticsVo.getContractAmts().size() > 0 ){
            statisticsVo.setContractAmtStr(statisticsVo.getContractAmts().get(0));
            statisticsVo.setContractAmtEnd(statisticsVo.getContractAmts().get(1));
        }

        if(statisticsVo.getPaymentAmounts() != null && statisticsVo.getPaymentAmounts().size() > 0  ){
            statisticsVo.setPaymentAmountStr(statisticsVo.getPaymentAmounts().get(0));
            statisticsVo.setPaymentAmountEnd(statisticsVo.getPaymentAmounts().get(1));
        }

        if(statisticsVo.getPaymentSpeeds() != null && statisticsVo.getPaymentSpeeds().size() > 0  ){
            if( statisticsVo.getPaymentSpeeds().get(0) != null && !statisticsVo.getPaymentSpeeds().get(0).equals("")){
                statisticsVo.setPaymentSpeedStr(new BigDecimal(statisticsVo.getPaymentSpeeds().get(0) ));
            }
            if(statisticsVo.getPaymentSpeeds().get(1) != null  && !statisticsVo.getPaymentSpeeds().get(1).equals("")){
                statisticsVo.setPaymentSpeedEnd(new BigDecimal(statisticsVo.getPaymentSpeeds().get(1)));
            }
        }

        if(statisticsVo.getSettlements() != null && statisticsVo.getSettlements().size() > 0  ){
            statisticsVo.setSettlementStr(statisticsVo.getSettlements().get(0));
            statisticsVo.setSettlementEnd(statisticsVo.getSettlements().get(1));
        }

        IPage<ContractTotlaVo> list = infrastrExcelMapper.getContractList(new Page<>(Integer.parseInt(statisticsVo.getPage()),Integer.parseInt(statisticsVo.getSize())),statisticsVo);

        if(ValidateUtil.isEmpty(list.getRecords())){
            return BaseResponse.ok(list.getRecords(),(int)list.getTotal());
        }else{
            List<String> collect = list.getRecords().stream().map(ContractTotlaVo::getConId).collect(Collectors.toList());

            //获取详细支付数据
            List<ConPayApplyFlow> idDataList = conPayApplyFlowService.getProjectDataList(collect);


            // 创建一个新的列表用于存储更新后的 ContractTotlaVo 对象
            List<ContractTotlaVo> updatedList = new ArrayList<>();

            for (ContractTotlaVo vo : list.getRecords()) {
                List<Map<String, Object>> matchingConPays = idDataList.stream()
                        .filter(conPay -> conPay.getConId().equals(vo.getConId()))
                        .map(conPay -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("payApproval", conPay.getCpafApplication());
                            map.put("payStateTime",  conPay.getCpafApplyTime());
                            map.put("conId", conPay.getConId());
                            return map;
                        })

                        .collect(Collectors.toList());
                vo.setList(matchingConPays);
                updatedList.add(vo);
            }
            return BaseResponse.ok(updatedList,(int)list.getTotal());
        }
    }

    //查询项目支付
    @Override
    public BaseResponse getProjectList(StatisticsVo statisticsVo, String uId) {
        if(statisticsVo.getContractAmts() != null && statisticsVo.getContractAmts().size() > 0 ){
            statisticsVo.setContractAmtStr(statisticsVo.getContractAmts().get(0));
            statisticsVo.setContractAmtEnd(statisticsVo.getContractAmts().get(1));
        }

        if(statisticsVo.getPaymentAmounts() != null && statisticsVo.getPaymentAmounts().size() > 0  ){
            statisticsVo.setPaymentAmountStr(statisticsVo.getPaymentAmounts().get(0));
            statisticsVo.setPaymentAmountEnd(statisticsVo.getPaymentAmounts().get(1));
        }

        if(statisticsVo.getPaymentSpeeds() != null && statisticsVo.getPaymentSpeeds().size() > 0  ){
            if( statisticsVo.getPaymentSpeeds().get(0) != null && !statisticsVo.getPaymentSpeeds().get(0).equals("")   ){
                statisticsVo.setPaymentSpeedStr(new BigDecimal(statisticsVo.getPaymentSpeeds().get(0) ));
            }
            if( statisticsVo.getPaymentSpeeds().get(1) != null && !statisticsVo.getPaymentSpeeds().get(1).equals("")   ){
                statisticsVo.setPaymentSpeedEnd(new BigDecimal(statisticsVo.getPaymentSpeeds().get(1)));
            }
        }

        if(statisticsVo.getSettlements() != null && statisticsVo.getSettlements().size() > 0  ){
            statisticsVo.setSettlementStr(statisticsVo.getSettlements().get(0));
            statisticsVo.setSettlementEnd(statisticsVo.getSettlements().get(1));
        }
        IPage<ContractTotlaVo> list = infrastrExcelMapper.getProjectList(new Page<>(Integer.parseInt(statisticsVo.getPage()),Integer.parseInt(statisticsVo.getSize())),statisticsVo);


        if(ValidateUtil.isEmpty(list.getRecords())) {
            return BaseResponse.ok(list.getRecords(), (int)list.getTotal());
        }else{
            List<String> collect = list.getRecords().stream().map(ContractTotlaVo::getConId).collect(Collectors.toList());

            //获取详细支付数据
            List<ConPayApplyFlow> idDataList =  conPayApplyFlowService.getProjectDataList(collect);

            // 创建一个新的列表用于存储更新后的 ContractTotlaVo 对象
            List<ContractTotlaVo> updatedList = new ArrayList<>();

            for (ContractTotlaVo vo : list.getRecords()) {
                List<Map<String, Object>> matchingConPays = idDataList.stream()
                        .filter(conPay -> conPay.getConId().equals(vo.getConId()))
                        .map(conPay -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("payApproval", conPay.getCpafApplication());
                            map.put("payStateTime",  conPay.getCpafApplyTime());
                            map.put("pId", conPay.getPId());
                            return map;
                        })

                        .collect(Collectors.toList());
                vo.setList(matchingConPays);
                updatedList.add(vo);
            }

            return BaseResponse.ok(updatedList,(int)list.getTotal());
        }
    }

    //查询合同审批状态
    @Override
    public BaseResponse getContractLedger(StatisticsVo statisticsVo) {
        IPage<ContractTotlaVo> list = infrastrExcelMapper.getContractLedgerList(new Page<>(Integer.parseInt(statisticsVo.getPage()),Integer.parseInt(statisticsVo.getSize())),statisticsVo);
        if(ValidateUtil.isEmpty(list.getRecords())) {
            return BaseResponse.ok(list.getRecords(), (int)list.getTotal());
        }else{
            List<String> collect = list.getRecords().stream().map(ContractTotlaVo::getConId).collect(Collectors.toList());

            //获取详细支付数据
            List<ConPayApplyFlow> idDataList =  conPayApplyFlowService.getProjectDataList(collect);

            // 创建一个新的列表用于存储更新后的 ContractTotlaVo 对象
            List<ContractTotlaVo> updatedList = new ArrayList<>();

            for (ContractTotlaVo vo : list.getRecords()) {
                List<Map<String, Object>> matchingConPays = idDataList.stream()
                        .filter(conPay -> conPay.getConId().equals(vo.getConId()))
                        .map(conPay -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("payApproval", conPay.getCpafApplication());
                            map.put("payStateTime",  conPay.getCpafApplyTime());
                            map.put("conId", conPay.getConId());
                            return map;
                        })

                        .collect(Collectors.toList());
                vo.setList(matchingConPays);
                updatedList.add(vo);
            }

            return BaseResponse.ok(updatedList,(int)list.getTotal());
        }
    }

    @Override
    public BaseResponse getProjectAuthenticationList(StatisticsVo statisticsVo, String uId) {
        if(statisticsVo.getContractAmts() != null && statisticsVo.getContractAmts().size() > 0 ){
            statisticsVo.setContractAmtStr(statisticsVo.getContractAmts().get(0));
            statisticsVo.setContractAmtEnd(statisticsVo.getContractAmts().get(1));
        }

        if(statisticsVo.getPaymentAmounts() != null && statisticsVo.getPaymentAmounts().size() > 0  ){
            statisticsVo.setPaymentAmountStr(statisticsVo.getPaymentAmounts().get(0));
            statisticsVo.setPaymentAmountEnd(statisticsVo.getPaymentAmounts().get(1));
        }

        if(statisticsVo.getPaymentSpeeds() != null && statisticsVo.getPaymentSpeeds().size() > 0  ){
            if( statisticsVo.getPaymentSpeeds().get(0) != null && !statisticsVo.getPaymentSpeeds().get(0).equals("")   ){
                statisticsVo.setPaymentSpeedStr(new BigDecimal(statisticsVo.getPaymentSpeeds().get(0) ));
            }
            if( statisticsVo.getPaymentSpeeds().get(1) != null && !statisticsVo.getPaymentSpeeds().get(1).equals("")   ){
                statisticsVo.setPaymentSpeedEnd(new BigDecimal(statisticsVo.getPaymentSpeeds().get(1)));
            }
        }

        if(statisticsVo.getSettlements() != null && statisticsVo.getSettlements().size() > 0  ){
            statisticsVo.setSettlementStr(statisticsVo.getSettlements().get(0));
            statisticsVo.setSettlementEnd(statisticsVo.getSettlements().get(1));
        }
        String authSql = getAuthSql(uId,null);
        IPage<ContractTotlaVo> list = infrastrExcelMapper.getProjectAuthenticationList(new Page<>(Integer.parseInt(statisticsVo.getPage()),Integer.parseInt(statisticsVo.getSize())),statisticsVo,authSql);


        if(ValidateUtil.isEmpty(list.getRecords())) {
            return BaseResponse.ok(list.getRecords(), (int)list.getTotal());
        }else{
            List<String> collect = list.getRecords().stream().map(ContractTotlaVo::getConId).collect(Collectors.toList());

            //获取详细支付数据
             List<ConPayApplyFlow> idDataList =  conPayApplyFlowService.getProjectDataList(collect);
             List<String> flowIds = idDataList.stream().map(e -> e.getCpafId()).collect(Collectors.toList());
             List<Task> taskList  =  taskService.queryNodes(flowIds);


            // 创建一个新的列表用于存储更新后的 ContractTotlaVo 对象
            List<ContractTotlaVo> updatedList = new ArrayList<>();

            for (ContractTotlaVo vo : list.getRecords()) {
                List<Map<String, Object>> matchingConPays = idDataList.stream()
                        .filter(conPay -> conPay.getConId().equals(vo.getConId()))
                        .map(conPay -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("payApproval", conPay.getCpafApplication());
                            map.put("payStateTime",  conPay.getCpafApplyTime());
                            map.put("pId", conPay.getPId());
                            map.put("state", conPay.getState());
                            map.put("cpafId", conPay.getCpafId());
                            List<String> taskNames = taskList.stream()
                                    .filter(task -> Objects.equals(task.getTaskentityid(), conPay.getCpafId()))
                                    .map(Task::getTaskname) //taskName
                                    .collect(Collectors.toList());
                            map.put("currentPerson",String.join(",",taskNames));
                            return map;
                        })

                        .collect(Collectors.toList());
                vo.setList(matchingConPays);
                updatedList.add(vo);
            }

            return BaseResponse.ok(updatedList,(int)list.getTotal());
        }
    }
    /**
     * 根据合同金额来查询数据
     * @param statisticsVo
     * @return
     */
    public BaseResponse getContractStatisticsList(StatisticsVo statisticsVo, String uId) {
        // 处理合同金额范围
        if(statisticsVo.getContractAmts() != null && statisticsVo.getContractAmts().size() > 0 ){
            statisticsVo.setContractAmtStr(statisticsVo.getContractAmts().get(0));
            statisticsVo.setContractAmtEnd(statisticsVo.getContractAmts().get(1));
        }
        // 处理付款合计范围
        if(statisticsVo.getPaymentAmounts() != null && statisticsVo.getPaymentAmounts().size() > 0  ){
            statisticsVo.setPaymentAmountStr(statisticsVo.getPaymentAmounts().get(0));
            statisticsVo.setPaymentAmountEnd(statisticsVo.getPaymentAmounts().get(1));
        }
        // 处理付款进度范围
        if(statisticsVo.getPaymentSpeeds() != null && statisticsVo.getPaymentSpeeds().size() > 0  ){
            if( statisticsVo.getPaymentSpeeds().get(0) != null && !statisticsVo.getPaymentSpeeds().get(0).equals("")){
                statisticsVo.setPaymentSpeedStr(new BigDecimal(statisticsVo.getPaymentSpeeds().get(0) ));
            }
            if(statisticsVo.getPaymentSpeeds().get(1) != null  && !statisticsVo.getPaymentSpeeds().get(1).equals("")){
                statisticsVo.setPaymentSpeedEnd(new BigDecimal(statisticsVo.getPaymentSpeeds().get(1)));
            }
        }
        // 处理结算价范围
        if(statisticsVo.getSettlements() != null && statisticsVo.getSettlements().size() > 0  ){
            statisticsVo.setSettlementStr(statisticsVo.getSettlements().get(0));
            statisticsVo.setSettlementEnd(statisticsVo.getSettlements().get(1));
        }

        // 分页参数安全处理
        long page = 1;
        long size = 10;
        try {
            page = Long.parseLong(statisticsVo.getPage());
            size = Long.parseLong(statisticsVo.getSize());
        } catch (Exception e) {}

        // 获取权限SQL
        String authSql = getAuthSql(uId, null);

        // 调用专用统计查询
        IPage<ContractTotlaVo> list = infrastrExcelMapper.getContractStatisticsList(new Page<>(page, size), statisticsVo, authSql);

        // ====== 修复：空判断逻辑写反了 ======
        if (ValidateUtil.isNotEmpty(list.getRecords())) {
            List<String> collect = list.getRecords().stream()
                    .map(ContractTotlaVo::getConId)
                    .collect(Collectors.toList());

            //获取详细支付数据
            List<ConPayApplyFlow> idDataList = conPayApplyFlowService.getProjectDataList(collect);

            // 组装数据
            for (ContractTotlaVo vo : list.getRecords()) {
                List<Map<String, Object>> matchingConPays = idDataList.stream()
                        .filter(conPay -> conPay.getConId().equals(vo.getConId()))
                        .map(conPay -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("payApproval", conPay.getCpafApplication());
                            map.put("payStateTime", conPay.getCpafApplyTime());
                            map.put("conId", conPay.getConId());
                            return map;
                        })
                        .collect(Collectors.toList());
                vo.setList(matchingConPays);
            }
        }

        return BaseResponse.ok(list.getRecords(), (int) list.getTotal());
    }

    //资金支付统计
    @Override
    public BaseResponse getCapitalPayment(StatisticsVo statisticsVo) {
        IPage<ContractTotlaVo> list =  infrastrExcelMapper.getCapitalPaymentList(new Page<>(Integer.parseInt(statisticsVo.getPage()),Integer.parseInt(statisticsVo.getSize())),statisticsVo);
//        LambdaQueryWrapper<PreProject> queryWrapper1 = new LambdaQueryWrapper<>();
//        if(ValidateUtil.isNotNull(statisticsVo.getProjectName())){
//            queryWrapper1.like(PreProject::getPName,statisticsVo.getProjectName());
//        }
//        queryWrapper1.eq(PreProject::getState,7);
//         Page<PreProject> preProjectPage = preProjectService.getBaseMapper().selectPage(new Page<>(Integer.parseInt(statisticsVo.getPage()), Integer.parseInt(statisticsVo.getSize())), queryWrapper1);
        List<String> ids =  list.getRecords().stream().map(ContractTotlaVo::getPId).collect(Collectors.toList());
        List<InvProjectPlan> invProjectPlans;
        if (ValidateUtil.isEmpty(ids)) {
            // 处理空结果情况
            invProjectPlans = Collections.emptyList();
            return BaseResponse.ok(Collections.emptyList());
        } else {
            invProjectPlans = invProjectPlanService.getDataIdS(ids);
        }
         List<ConPayApplyFlow> projectPidList = conPayApplyFlowService.getProjectPidList(ids);

        List<ContractTotlaVo>  conList =  conContractService.getPayList(ids);

        List<PreProjectVo> preList = list.getRecords().stream().map( p ->{
            PreProjectVo vo = new PreProjectVo();
            BeanUtils.copyProperties(p,vo);
            if(ValidateUtil.isNotNull(projectPidList)){
                 List<ConPayApplyFlow> flow = projectPidList.stream().filter(e -> e.getPId().equals(p.getPId())).collect(Collectors.toList());
                vo.setConPayApplyFlows(flow);
            }
            if (ValidateUtil.isNotNull(invProjectPlans)) {
                List<InvProjectPlan> plans = invProjectPlans.stream().filter(e -> e.getPId().equals(p.getPId())).collect(Collectors.toList());
                vo.setInvProjectPlans(plans);
            }
            if(ValidateUtil.isNotNull(conList)){
                 List<ContractTotlaVo> collect = conList.stream().filter(e -> e.getConProjectId().equals(p.getPId())).collect(Collectors.toList());
                 vo.setContractTotlas(collect);
            }

            return vo;

        }).collect(Collectors.toList());

//        conPayService.getIdDataList();


//       List<String> ids =  list.stream().map(ContractTotlaVo::getPId).collect(Collectors.toList());
//        Map<String,Object> map = new HashMap<>();
//        map.put("list",list);
//        map.put("invProjectPlans",invProjectPlans);
        return BaseResponse.ok(preList,(int)list.getTotal());
    }

    public static List<Map<String, Object>> parseJsonToList(String jsonString) {
        // 将 JSON 字符串转换为 JSONArray
        JSONArray jsonArray = JSONUtil.parseArray(jsonString);

        // 将 JSONArray 转换为 List<Map<String, Object>>
        List<Map<String, Object>> resultList = CollUtil.newArrayList();
        for (Object obj : jsonArray) {
            Map<String, Object> map = JSONUtil.toBean(obj.toString(), new TypeReference<Map<String, Object>>() {}, false);
            resultList.add(map);
        }
        return resultList;
    }

    //查询审批通过的合同
    @Override
    public List<ContractVo> getContractAdopt() {
        List<ContractVo> list =  infrastrExcelMapper.getContractAdopt();
        return list;
    }

    @Override
    public List<ContractVo> getContract() {
        List<ContractVo> list =  infrastrExcelMapper.getContract();
        return list;
    }


    @Override
    public BaseResponse getPaymentDetails(String conId) {
         List<ConPayApplyFlow> projectData = conPayApplyFlowService.getProjectData(conId);
         List<Map<String,Object>> data = new ArrayList<>();
         Map<String,Object> dataMap = new HashMap<>();
         if(ValidateUtil.isNotEmpty(projectData)){
            for (ConPayApplyFlow projectDatum : projectData) {
                Map<String,Object> map = new HashMap<>();
                map.put("payApproval",projectDatum.getCpafApplication());
                map.put("payStateTime",projectDatum.getCpafApplyTime());
                map.put("cpafId",projectDatum.getCpafId());
                map.put("cpaId",projectDatum.getCpaId());
                map.put("pId",projectDatum.getPId());
                map.put("conId",projectDatum.getConId());
                data.add(map);
            }

        }
        dataMap.put("state",data);
        return BaseResponse.ok(dataMap);
    }

    private String getAuthSql(String uId, String conCategory) {
        if (ValidateUtil.isNotEmpty(conCategory) && conCategory.equals("6")) {
            //年度合同
            return sysUserService.onlyInnerAndPartyB("Con_Contract", uId, "Con_Create_User_Id", "Con_Approval_State");
        } else if (ValidateUtil.isNotEmpty(conCategory) && conCategory.equals("7")) {
            //经费合同 没有项目的数据按合同部门鉴权 有项目的数据按正常项目鉴权
            String authsql = sysUserService.currentDepAuth(uId, "Con_Department", "Con_Create_User_Id", "Con_Approval_State");
            authsql = authsql.replaceFirst(" where ", " where  (( mn.Con_Project_Id is null and ") + ")  or ( mn.Con_Project_Id is not null and ";
            String projectAuth = sysUserService.getAuthSql("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State");
            projectAuth = projectAuth.replaceFirst(" where ", authsql) + ") )";
            return projectAuth;
            // return sysUserService.currentDepAuth(uId, "Con_Department", "Con_Create_User_Id", "Con_Approval_State");
        } else {//其他合同
            String authSql = sysUserService.onlyCreateorAndApprover("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State", "Con_Id");
            if (ValidateUtil.isEmpty(authSql)) throw new JPMException("无权操作！");
            return authSql;
        }
    }
}
