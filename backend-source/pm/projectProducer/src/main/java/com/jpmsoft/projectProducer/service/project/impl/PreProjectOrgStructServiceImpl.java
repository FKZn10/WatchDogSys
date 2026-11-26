package com.jpmsoft.projectProducer.service.project.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.project.PreProjectOrgStructRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectOrgStructModel;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUnitResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectOrgStructMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStruct;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUnit;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUser;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlan;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingProject;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfo;
import com.jpmsoft.projectProducer.pojo.response.PreProjectOrgStructnResponse;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.other.ISysCompanyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.*;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingPlanService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingProjectService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目组织机构(PreProjectOrgStruct)表服务实现类
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
@Service
public class PreProjectOrgStructServiceImpl extends ServiceImpl<PreProjectOrgStructMapper, PreProjectOrgStruct> implements IPreProjectOrgStructService {

    @Resource
    private PreProjectMapper preProjectMapper;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private IPreProjectOrgStructUnitService preProjectOrgStructUnitService;

    @Autowired
    private ISecBiddingProjectService iSecBiddingProjectService;

    @Autowired
    private ISecBiddingPlanService iSecBiddingPlanService;

    @Autowired
    private ISecInfoService iSecInfoService;

    @Resource
    private IPreProjectOrgStructUnitChangeService preProjectOrgStructUnitChangeService;

    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Autowired
    private IPreProjectOrgStructChangeService preProjectOrgStructChangeService;

    @Autowired
    private IConContractService iConContractService;

    @Autowired
    private IPreProjectOrgStructUserService iPreProjectOrgStructUserService;


    @Autowired
    private ISysCompanyService iSysCompanyService;

    @Autowired
    private ISysUserService iSysUserService;

    @Override
    public BaseResponse syncPreProjectOrgStruct(PreProjectOrgStructModel request, @RequestHeader String  uId) {
        if(StrUtil.isBlank(request.getProjectId())){
            return BaseResponse.fail(ResMsg.notReqParam);
        }
         SysUser sysUser = iSysUserService.getById(uId);
        SysFile fileEntity = sysFileService.getById(request.getProjectId());
        String fileFlowStatus = fileEntity.getFileflowstatus();
        PreProject project = preProjectMapper.selectById(request.getProjectId());
        String projectFormType = project.getpProjectFormType();

        if(ApprovalStatusEnum.Already_Over.getIndex().toString().equals(fileFlowStatus)){
            Date date=new Date();
            String createUid=project.getpCreateUserId();
            String createUname=project.getpCreateUserName();
            PreProjectOrgStruct preProjectOrgStruct=new PreProjectOrgStruct();
            String orgStructId=GenerateUUID.getUUID();
            preProjectOrgStruct.setId(orgStructId);
            preProjectOrgStruct.setPId(request.getProjectId());
            preProjectOrgStruct.setState(0);
            preProjectOrgStruct.setCreateUid(createUid);
            preProjectOrgStruct.setCreateUname(createUname);
            preProjectOrgStruct.setCreateTime(date);

            PreProjectOrgStructUnit preProjectOrgStructUnit=new PreProjectOrgStructUnit();
            List<PreProjectOrgStructUnit> unitList=new ArrayList<>();
            //立项相关单位01
            //立项形式=可研批复，取可研申报单位、可研审批单位、使用单位（仅限集中建设项目）；
            int sort=0;
            switch (projectFormType){
                case "01":
                    preProjectOrgStructUnit = generateOrgStructUnit("01","可研申报单位",orgStructId,project);
                    preProjectOrgStructUnit.setSort(sort++);
                    preProjectOrgStructUnit.setCreateTime(date);
                    unitList.add(preProjectOrgStructUnit);

                    preProjectOrgStructUnit = generateOrgStructUnit("01","可研审批单位",orgStructId,project);
                    preProjectOrgStructUnit.setSort(sort++);
                    preProjectOrgStructUnit.setCreateTime(date);
                    unitList.add(preProjectOrgStructUnit);

                    String pBuildModel = project.getpBuildModel();
                    if(StrUtil.isNotBlank(pBuildModel) && "001".equals(pBuildModel)){//集中建设
                        preProjectOrgStructUnit = generateOrgStructUnit("01","使用单位",orgStructId,project);
                        preProjectOrgStructUnit.setSort(sort++);
                        preProjectOrgStructUnit.setCreateTime(date);
                        unitList.add(preProjectOrgStructUnit);
                    }
                    break;
                case "02":
                    preProjectOrgStructUnit = generateOrgStructUnit("01","项目法人单位",orgStructId,project);
                    preProjectOrgStructUnit.setSort(sort++);
                    preProjectOrgStructUnit.setCreateTime(date);
                    unitList.add(preProjectOrgStructUnit);

                    preProjectOrgStructUnit = generateOrgStructUnit("01","备案证核发单位",orgStructId,project);
                    preProjectOrgStructUnit.setSort(sort++);
                    preProjectOrgStructUnit.setCreateTime(date);
                    unitList.add(preProjectOrgStructUnit);
                    break;
            }

            preProjectOrgStructUnit = generateOrgStructUnit("01","建设单位",orgStructId,project);
            preProjectOrgStructUnit.setSort(sort++);
            preProjectOrgStructUnit.setCreateTime(date);
            unitList.add(preProjectOrgStructUnit);

            baseMapper.insert(preProjectOrgStruct);

            //增加五方责任主体    01项目管理单位  02勘察单位  03设计单位  04施工单位  05监理单位
            int sortUn=0;
            List<String> list  = new ArrayList<>(Arrays.asList("01","02","03","04","05"));
            for (String s : list) {
                preProjectOrgStructUnit = new PreProjectOrgStructUnit();
                preProjectOrgStructUnit.setId(GenerateUUID.getUUID());
                preProjectOrgStructUnit.setUnitType("02");
                preProjectOrgStructUnit.setOrgStructType(s);
                preProjectOrgStructUnit.setOrgStructId(orgStructId);
                preProjectOrgStructUnit.setHaveUnit(1);
                preProjectOrgStructUnit.setUnitId("");  //单位id
                preProjectOrgStructUnit.setCreateUid(project.getpCreateUserId());
                preProjectOrgStructUnit.setCreateUname(project.getpCreateUserName());
                preProjectOrgStructUnit.setSort(sortUn++);
                preProjectOrgStructUnit.setCreateTime(date);
                unitList.add(preProjectOrgStructUnit);
            }

            if(CollectionUtil.isNotEmpty(unitList)){
                preProjectOrgStructUnitService.saveBatch(unitList);

                 List<PreProjectOrgStructUnit> strUnitList = unitList.stream().filter(e -> e.getOrgStructType().equals("01") && e.getUnitType().equals("02")).collect(Collectors.toList());
                 if(!CollectionUtils.isEmpty(strUnitList)){
                     List<PreProjectOrgStructUser> userList = new ArrayList<>();
                     PreProjectOrgStructUnit preUnit = strUnitList.get(0);
                     // 项目经理
                     userList.add(createUnitUser(project.getpProjectManager(), "项目经理", preUnit, sysUser));

                     // 项目分管领导
                     userList.add(createUnitUser(project.getpProjectLeader(), "项目分管领导", preUnit, sysUser));

                     // 工程部经办人
                     userList.add(createUnitUser(project.getpEngineer(), "工程部经办人", preUnit, sysUser));

                     // 工程部分管部长
                     userList.add(createUnitUser(project.getpEngineerLeader(), "工程部分管部长", preUnit, sysUser));

                     userList = userList.stream().filter(Objects::nonNull).collect(Collectors.toList());
                     iPreProjectOrgStructUserService.saveBatch(userList);

                 }
            }

        }

        return BaseResponse.ok();
    }

    private PreProjectOrgStructUser createUnitUser(String userIdField, String postName, PreProjectOrgStructUnit preUnit, SysUser sysUser) {
        PreProjectOrgStructUser user = new PreProjectOrgStructUser();
        user.setId(GenerateUUID.getUUID());
        user.setOrgStructUnitId(preUnit.getId());
        user.setCreateTime(new Date());
        user.setCreateUid(sysUser.getUId());
        user.setCreateUname(sysUser.getUName());
        if (ValidateUtil.isNotEmpty(userIdField)) {
            SysUser managerUser = iSysUserService.getById(userIdField);
            if (ValidateUtil.isNotNull(managerUser)) {
                user.setUserId(managerUser.getUId());
                user.setUserName(managerUser.getUName());
                user.setOrgContact(managerUser.getUMtel());
                user.setOrgPost(postName);
            }
        }
        return user;
    }


    @Override
    public BaseResponse getPreProjectOrgStructList(PreProjectOrgStructRequest request,String uId) {
        QueryMap<PreProjectOrgStructRequest> params = new QueryMap<>(request);
        params.put("pId").put("pName").put("pCompetentDepartment").put("pCompetentDepartmentName");
        IPage<PreProjectOrgStructResponse> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectOrgStructResponse> datas = baseMapper.getPreProjectOrgStructList(params.getQuery(),page);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getPreProjectOrgStructById(String id) {
        PreProjectOrgStructResponse preProjectOrgStructResponse=baseMapper.getPreProjectOrgStructById(id);
        List<PreProjectOrgStructUnitResponse> unitList = preProjectOrgStructUnitService.getByOrgStructId(id);
        preProjectOrgStructResponse.setPreProjectOrgStructUnitList(unitList);
        return BaseResponse.ok(preProjectOrgStructResponse);
    }

    private PreProjectOrgStructUnit generateOrgStructUnit(String unitType,String orgStructType,String orgStructId,PreProject project){
        String unitId="";
        switch (orgStructType){
            case "可研申报单位":
                unitId=project.getpCanYanApplyUnit();
            break;
            case "可研审批单位":
                unitId=project.getpCanYanApprovalUnit();
            break;
            case "使用单位":
                unitId=project.getpUseUnit();
            break;
            case "建设单位":
                unitId=project.getpConstructionUnit();
            break;
            case "备案证核发单位":
                unitId=project.getpRecordIssueUnit();
            break;
//            case "项目管理单位":
//                unitId=project.getpCompetentDepartment();
//                break;
        }

        PreProjectOrgStructUnit  preProjectOrgStructUnit=new PreProjectOrgStructUnit();
        preProjectOrgStructUnit.setId(GenerateUUID.getUUID());
        preProjectOrgStructUnit.setUnitType(unitType);
        preProjectOrgStructUnit.setOrgStructType(orgStructType);
        preProjectOrgStructUnit.setOrgStructId(orgStructId);
        preProjectOrgStructUnit.setHaveUnit(1);
        preProjectOrgStructUnit.setUnitId(unitId);
        preProjectOrgStructUnit.setCreateUid(project.getpCreateUserId());
        preProjectOrgStructUnit.setCreateUname(project.getpCreateUserName());
        return preProjectOrgStructUnit;
    }

    //保存并同步项目组织机构信息
    @Override
    public BaseResponse saveAndsynchronouSorganization(PreProjectOrgStructRequest request,String uId) {
        //保存
        if(ValidateUtil.isEmpty(request.getId())){
            return BaseResponse.fail("缺少必填参数");
        }
        PreProjectOrgStructResponse pre=baseMapper.getPreProjectOrgStructById(request.getId());
        if (ValidateUtil.isEmpty(pre)){
            return BaseResponse.fail("未查询到该项目");
        }
        return preProjectOrgStructUnitService.saveSorganization(request,uId);
    }

    @Override
    public BaseResponse changesynchronouSorganization(PreProjectOrgStructRequest request, String uId) {
        if(ValidateUtil.isEmpty(request.getId())){
            return BaseResponse.fail("缺少必填参数");
        }
        PreProjectOrgStructResponse pre=baseMapper.getPreProjectOrgStructById(request.getId());
        if (ValidateUtil.isEmpty(pre)){
            return BaseResponse.fail("未查询到该项目");
        }
        return preProjectOrgStructUnitService.changeSorganization(request,uId);
    }

    @Override
    public BaseResponse getchangeRecords(PreProjectOrgStructRequest request) {
        return  preProjectOrgStructChangeService.getDatapId(request.getpId(),request.getPage(),request.getSize());
    }


    @Override
    public BaseResponse getPreProjectOrgStructChangeById(String id) {
        PreProjectOrgStructResponse preProjectOrgStructResponse=preProjectOrgStructChangeService.getPreProjectOrgStructChangeById(id);
        List<PreProjectOrgStructUnitResponse> unitList = preProjectOrgStructUnitChangeService.getByOrgStructId(id);
        preProjectOrgStructResponse.setPreProjectOrgStructUnitList(unitList);
        return BaseResponse.ok(preProjectOrgStructResponse);
    }

    //保存人员列表到项目组织机构
    @Override
    public BaseResponse saveProjectOrganization(String id) {
         BaseResponse preProjectOrgStructById = getPreProjectOrgStructById(id);
        PreProjectOrgStructResponse pre = (PreProjectOrgStructResponse) preProjectOrgStructById.getData();
        if(ValidateUtil.isEmpty(pre))  return BaseResponse.fail("未查询到数据");
        if(!pre.getState().equals(7)){
             return BaseResponse.fail("流程审批未结束,不能同步到项目组织机构");
         }
        preProjectOrgStructUnitService.saveProjectOrganization(pre);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse queryInspectionContract(String pId) {
        //查询是否有监理合同，如果有并拿去对应的监理单位
        List<SecInfo> secInfoList = new ArrayList<>();
        Boolean b = false;
        List<SecBiddingProject>  isecPreList =  iSecBiddingProjectService.getSecBiddingPidData(pId);
        if(!CollectionUtils.isEmpty(isecPreList)){
            List<String> bpIds = isecPreList.stream().map(e -> e.getBpId()).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(bpIds)){
                SecBiddingPlan   sec =  iSecBiddingPlanService.getSecBiddingPlanByPIdList(bpIds);
                if(ValidateUtil.isNotNull(sec)){
                    secInfoList = iSecInfoService.getSecInfoByBpId(sec.getBpId());
                }
            }
        }
        List<ConContract> conlist = new ArrayList<>();
        if(!CollectionUtils.isEmpty(secInfoList)){
            //查询是否有监理合同
             long count = secInfoList.stream().filter(e -> e.getSecType().equals("05")).count();
             if(count >0 ){
              conlist =  iConContractService.getConContractpId(pId);
             }
        }
        return  BaseResponse.ok(conlist);
    }

    @Override
    public BaseResponse getProjectOrganizationById(String pId) {
        //pProjectFormType  01 可研批复  02投资备案证  03其他
        PreProject project = preProjectMapper.selectById(pId);
        if(ValidateUtil.isNull(project)) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!project.getState().equals(7)) return BaseResponse.fail(ResMsg.stateCannotAct);
        PreProjectOrgStructnResponse response = new PreProjectOrgStructnResponse();
        response.setPId(project.getpId());
        response.setPName(project.getpName());


        return null;
    }
}
