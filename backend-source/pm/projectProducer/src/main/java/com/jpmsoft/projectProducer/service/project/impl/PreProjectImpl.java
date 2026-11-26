package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.FilterObject;
import com.jpmsoft.common.model.export.response.InvMonthlyScheduleModel;
import com.jpmsoft.common.model.export.response.MonthlyReportExportModel;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.project.*;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEcAndVisFeeModel;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.common.model.requestAndResponse.Project.*;
import com.jpmsoft.common.model.requestAndResponse.Workbench.WorkbenchOverviewModel;
import com.jpmsoft.common.model.response.Project.*;
import com.jpmsoft.common.utils.*;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.other.CarbonCopyMapper;
import com.jpmsoft.projectProducer.mapper.other.SysCompanyMapper;
import com.jpmsoft.projectProducer.mapper.other.SysPositionUserPMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ProjectSelectFilterDto;
import com.jpmsoft.projectProducer.pojo.dto.other.ImportTDepartmentDto;
import com.jpmsoft.projectProducer.pojo.dto.other.UserPositionPDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.ConfigRelationDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.ProMonthlyReportDto;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectFundStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYear;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanDtl;
import com.jpmsoft.projectProducer.pojo.entity.other.CarbonCopy;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.pojo.entity.project.PpOwnerUnit;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.ProInvDetail;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFunds;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConTypeStatisticsVO;
import com.jpmsoft.projectProducer.pojo.vo.project.*;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;
import com.jpmsoft.projectProducer.rabbitMQ.Sender;
import com.jpmsoft.projectProducer.service.investment.IInvPlanYearService;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanDtlService;
import com.jpmsoft.projectProducer.service.oa.IOaProjectReportService;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.*;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class PreProjectImpl
        extends ServiceImpl<PreProjectMapper, PreProject> implements IPreProjectService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private IProConstructionAreaDtlService proConstructionAreaDtlService;
    @Resource
    private IProSourcesFundsService proSourcesFundsService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IProHouseDtlService proHouseDtlService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private IInvPlanYearService invPlanYearService;
    @Resource
    private IOaProjectReportService oaProjectReportService;
    @Resource
    private IProInvDetailService proInvDetailService;

    @Resource
    private IProFundNoService proFundNoService;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IOaAnnualPlanDtlService oaAnnualPlanDtlService;

    @Resource
    private IPreProjectDtlService preProjectDtlService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private ISysPositionUserPService sysPositionUserPService;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    private CarbonCopyMapper carbonCopyMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private Sender sender;
    @Autowired
    private SysCompanyMapper sysCompanyMapper;
    //    @Resource
//    private ISysCompanyService sysCompanyService;
//
//    @Resource
//    private ISysDepartmentService sysDepartmentService;
//
//    @Resource
//    private Sender sender;
    @Resource
    private IPpOwnerUnitService ppOwnerUnitService;
    @Resource
    private SysPositionUserPMapper sysPositionUserPMapper;
    @Resource
    private IPreProjectReserveService preProjectReserveService;

    /**
     * 按项目筛选及权限条件汇总项目和已审批合同金额，不受分页影响。
     *
     * @param request    项目筛选条件
     * @param uId        当前用户 ID
     * @param exclude    是否排除未发起及作废项目
     * @param conAuthSql 合同数据权限 SQL
     * @return 项目及合同汇总结果
     */
    @Override
    public BaseResponse getPreProjectAmtSum(PreProjectRequest request, String uId, boolean exclude, String conAuthSql) {
        Map<String, Object> params = listFilter(request, getAuthSql(OutUserAuthEnum.NOT.getIndex(), uId));
        params.put("conAuthSql", conAuthSql);
        return getPreProjectAmtSum(params, exclude);
    }

    /**
     * 使用已构造的筛选及权限参数查询汇总，供统计列表复用参数。
     *
     * @param params  项目筛选和合同权限参数
     * @param exclude 是否排除未发起及作废项目
     * @return 项目及合同汇总结果
     */
    private BaseResponse getPreProjectAmtSum(Map<String, Object> params, boolean exclude) {
        ProAmtSumModel data = baseMapper.getPreProjectAmtSum(params, exclude);
        if (data == null) {
            data = new ProAmtSumModel();
        } else {
            ProAmtSumModel con = baseMapper.getPreProjectConSum(params, exclude);
            if (con != null) {
                data.setContractSum(con.getContractSum());
                data.setConMoneyTotalSum(con.getConMoneyTotalSum());
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    public Boolean updatePreState(String pid, String preState) {
        PreProject pre = baseMapper.selectById(pid);
        pre.setpProjectStage(preState);
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        pre.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), pre));
        int i = baseMapper.updateById(pre);
        return i == 1 ? true : false;
    }

    @Override
    public BaseResponse updateProjectInfo(PreProjectRequest request, String uid) {
        PreProject project = baseMapper.selectById(request.getpId());
        project.setpEngineer(request.getpEngineer());
        project.setpEngineerLeader(request.getpEngineerLeader());
        project.setpCategory(request.getpCategory());
        project.setpScaleDescribe(request.getpScaleDescribe());
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        project.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), project));
        baseMapper.updateById(project);
        return BaseResponse.ok();
    }

    @Override
    public void projectCreate2ImpTempDepartment(String pId) {
        String conf = sysConfigService.getConfigByCode("projectAutoImportProDepartment");
        if (ValidateUtil.isNotEmpty(conf) && conf.equals("1")) {
            String toId = baseMapper.getOneTOrganizationId();
            if (ValidateUtil.isNotEmpty(toId)) {
                ImportTDepartmentDto req = new ImportTDepartmentDto();
                req.setPId(pId);
                req.setToId(toId);
                userProducerFeign.ImportT_Department(req);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void projectApproved2ImpTempDepartment(String pId) {
        this.projectApproved2ImpTempDepartment(pId, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void projectApproved2ImpTempDepartment(String pId, Boolean isReserve) {
        PreProject project = baseMapper.selectById(pId);
        if (project == null) throw new JPMException(ResMsg.infoNotExist);
        if (!Objects.equals(project.getState(), ApprovalStatusEnum.Already_Over.getIndex()) && !isReserve)
            throw new JPMException(ResMsg.stateCannotAct);
        //城建项目审批通过同步指定项目组织机构模板数据 并将项目上指定字段人员绑定到对应部门岗位
        //region 导入同步项目组织机构信息
        String toId = null;

        // 首先，判断项目的pBuildModel是否有值
        if (ValidateUtil.isNotEmpty(project.getpBuildModel())) {
            // 有，就查询
            List<ConfigRelationDTO> configRelations = baseMapper.getConfigRelationListByPBuildModel(project.getpBuildModel());
            // 再判断查询结果是否有值
            if (ValidateUtil.isNotEmpty(configRelations)) {
                // 如果有值，就根据项目的pCompetentDepartment取对应的toId
                if (ValidateUtil.isNotEmpty(project.getpCompetentDepartment())) {
                    Optional<ConfigRelationDTO> configRelationOptional = configRelations.stream().filter(c -> StrUtil.isNotEmpty(c.getDId()) && c.getDId().equals(project.getpCompetentDepartment())).findFirst();
                    if (configRelationOptional.isPresent()) {
                        toId = configRelationOptional.get().getDTId();
                    }
                }
            }
            if (ValidateUtil.isEmpty(toId)) {
                // 如果没有值，就取列表里pBuildModel对应的pCompetentDepartment为空的配置项的toId
                if (ValidateUtil.isNotEmpty(project.getpCompetentDepartment())) {
                    toId = baseMapper.selectConfigRelation(project.getpCompetentDepartment());
                }
            }
        } else if (ValidateUtil.isNotEmpty(project.getpCompetentDepartment())) {
            // 回退方案：如果pBuildModel没有值，就根据项目的pCompetentDepartment取没有pBuildModel的配置对应的toId
            toId = baseMapper.selectConfigRelation(project.getpCompetentDepartment());
        }

        List<DepartmentPCjConfDTO> confs = sysConfigService.departmentPCjConfList();
        // 读取project的pBuildModel，如果pBuildModel在confs中有对应的配置项，则取对应的toId，没有就取列表里的pBuildModel为空的
        DepartmentPCjConfDTO conf = null;
        if (CollectionUtil.isNotEmpty(confs)) {
            Optional<DepartmentPCjConfDTO> confOptional = confs.stream().filter(c -> StrUtil.isNotEmpty(c.getPBuildModel()) && c.getPBuildModel().equals(project.getpBuildModel())).findFirst();
            if (confOptional.isPresent()) {
                conf = confOptional.get();
            } else {
                Optional<DepartmentPCjConfDTO> defaultConfOptional = confs.stream().filter(c -> StrUtil.isEmpty(c.getPBuildModel())).findFirst();
                if (defaultConfOptional.isPresent()) {
                    conf = defaultConfOptional.get();
                }
            }
        }
        if (conf == null) {
            throw new JPMException(ResMsg.confError);
        }
        //缺省根据配置取指定名称的组织机构模板
        if (ValidateUtil.isEmpty(toId)) {
            toId = baseMapper.getOneTOrganizationIdByName(conf.getDepTemp());
        }
        if (ValidateUtil.isNotEmpty(toId)) {
            ImportTDepartmentDto req = new ImportTDepartmentDto();
            req.setPId(pId);
            req.setToId(toId);
            BaseResponse baseResponse = userProducerFeign.syncTDepartment(req);
        }
        //endregion
        //region 将项目信息上的用户信息绑定到对应部门对应岗位
        conf.setProjectId(project.getpId());
        sender.sendSyncNJCJDep(JacksonUtils.toJSONString(conf));
        //endregion
    }

    @Override
    public BaseResponse approvalData(ProjectRequest request, String token) {
        String id = request.getId();
        PreProject entity;
        SysFile fileEntity;
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("信息不存在，请刷新重试！");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null)
                return new BaseResponse("信息不存在，请刷新重试！");
            fileEntity = sysFileService.getById(id);
            //流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);
            if (ApprovalStatusEnum.Already_Over.getIndex().toString().equals(fileFlowStatus)) {
                String customerId = redisUtils.getCustomerId(token);
                //项目新增（新）审批流程结束后，流程自动抄送给“工程部经办人”、 “工程部分管部长”这两个岗位的人员。
                List<String> ccUser = new ArrayList<>();
                ccUser.add(entity.getpEngineer());
                ccUser.add(entity.getpEngineerLeader());
                List<CarbonCopy> pojos = new ArrayList<>();
                ccUser.forEach(u -> {
                    CarbonCopy pojo = new CarbonCopy();
                    pojo.setCcId(GenerateUUID.getUUID());
                    pojo.setCcActid(request.getActid());
                    pojo.setCcUser(u);
                    pojo.setCcTitle("项目信息新增");
                    pojo.setCcFlowid(request.getActflowid());
                    pojo.setCcTime(new Date());
                    pojo.setCcCreateuser(redisUtils.getUid(token));
                    pojo.setCcRead("未阅");
                    pojo.setCcReadtime(null);
                    pojo.setCcFileid(id);
                    pojo.setCcExplain(null);
                    if (!"All".equals(customerId)) {
                        pojo.setTeId(customerId);
                    }
                    pojos.add(pojo);
                });
                if (pojos.size() > 0) {
                    for (CarbonCopy carbonCopy : pojos) {
                        carbonCopyMapper.insert(carbonCopy);
                    }
                }
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectList(PreProjectRequest request, String uId) {
        log.info("项目列表查询");
        return getProjectList(request, uId, "gw", sysUserService.onlyCreateorAndApproverSQL(uId, "conf.Con_Create_User_Id", "conf.Con_Id", "Con_Approval_State"));
    }

    @Override
    public List<PreProjectResponse> getProjectListExcel(PreProjectRequest request, String uId) {
        log.info("项目列表查询");
        return getProjectListExcel(request, uId, "gw", sysUserService.onlyCreateorAndApproverSQL(uId, "conf.Con_Create_User_Id", "conf.Con_Id", "Con_Approval_State"));
    }

    /**
     * 查询项目统计及分页列表，在本次请求内复用项目权限和筛选参数。
     *
     * @param request 项目筛选与分页条件
     * @param uId     当前用户 ID
     * @return 全部匹配项目的汇总及当前页项目明细
     */
    @Override
    public BaseResponse getProjectStatisticsList(PreProjectRequest request, String uId) {
        String conAuthSql = sysUserService.onlyCreateorAndApproverSQL(uId, "conf.Con_Create_User_Id", "conf.Con_Id", "Con_Approval_State");
        String authSql = getAuthSql(OutUserAuthEnum.NOT.getIndex(), uId);
        Map<String, Object> params = listFilter(request, authSql);
        params.put("conAuthSql", conAuthSql);
        BaseResponse res = getPreProjectAmtSum(params, false);
        ProAmtSumModel data = res.getData() == null ? new ProAmtSumModel() : (ProAmtSumModel) res.getData();
        BaseResponse pros = getProjectList(request, "gw", authSql, params);
        data.setData(pros.getData());
        pros.setData(data);
        return pros;
    }

    @Override
    public BaseResponse getProjectTZList(PreProjectTZRequest request, String uid) {
        String conAuthSql = sysUserService.onlyCreateorAndApproverSQL(uid, "conf.Con_Create_User_Id", "conf.Con_Id", "Con_Approval_State");
        BaseResponse res = getPreProjectAmtSumTZ(request, uid, false, conAuthSql);
        ProAmtSumModel data;
        if (ValidateUtil.isNull(res.getData()))
            data = new ProAmtSumModel();
        else
            data = (ProAmtSumModel) res.getData();
        BaseResponse pros = getProjectTZListByUID(request, uid);
        data.setData(pros.getData());
        pros.setData(data);
        return pros;
        //return BaseResponse.ok(preProjectResponses, total);
    }

    private BaseResponse getProjectTZListByUID(PreProjectTZRequest request, String uid) {
        QueryMap<PreProjectTZRequest> params = new QueryMap<>(request);
        List<ClassInfoAnnotation> projectClassInfoAnnotations = getClassInfoAnnotations();
        for (ClassInfoAnnotation classInfoAnnotation : projectClassInfoAnnotations) {
            params.put(classInfoAnnotation.getValue());
        }
        List<PreProjectResponse> preProjectResponses = new ArrayList<>();
        List<PreProjectResponse> datas = baseMapper.getProjectTZList(params.getQuery());
        for (PreProjectResponse preProjectResponse : datas) {
            List<String> uids = new ArrayList<>();
            uids.add(preProjectResponse.getpCreateUserId());
            QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
            wrapper.eq("tp_entityid", preProjectResponse.getpId());
            wrapper.orderByDesc("tp_dotime");
            List<TaskProcessin> taskProcessins = taskProcessinService.list(wrapper);
            if (CollectionUtil.isNotEmpty(taskProcessins)) {
                uids.addAll(taskProcessins.stream().filter(taskProcessin -> StrUtil.isNotEmpty(taskProcessin.getTpUser())).map(TaskProcessin::getTpUser).distinct().collect(Collectors.toList()));
            }
            QueryWrapper<CarbonCopy> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("CC_FileID", preProjectResponse.getpId());
            List<CarbonCopy> carbonCopies = carbonCopyMapper.selectList(wrapper2);
            if (CollectionUtil.isNotEmpty(carbonCopies)) {
                uids.addAll(carbonCopies.stream().filter(carbonCopy -> StrUtil.isNotEmpty(carbonCopy.getCcUser())).map(CarbonCopy::getCcUser).distinct().collect(Collectors.toList()));
            }
            uids = uids.stream().distinct().collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(uids) && uids.contains(uid)) {
                preProjectResponses.add(preProjectResponse);
            }
        }
        return BaseResponse.ok(preProjectResponses);
    }

    public BaseResponse getPreProjectAmtSumTZ(PreProjectTZRequest request, String uId, boolean exclude, String conAuthSql) {
        String authSql = getAuthSql(OutUserAuthEnum.NOT.getIndex(), uId);
        QueryMap<PreProjectTZRequest> params = new QueryMap<>(request);
        List<ClassInfoAnnotation> projectClassInfoAnnotations = getClassInfoAnnotations();
        for (ClassInfoAnnotation classInfoAnnotation : projectClassInfoAnnotations) {
            params.put(classInfoAnnotation.getValue());
        }
        params.put("conAuthSql", conAuthSql).put("authSql", authSql);
        ProAmtSumModel data = baseMapper.getPreProjectAmtSumTZ(params.getQuery(), exclude);
        if (ValidateUtil.isNull(data)) data = new ProAmtSumModel();
        else {
            ProAmtSumModel con = baseMapper.getPreProjectConSumTZ(params.getQuery(), exclude);
            data.setContractSum(con.getContractSum());
            data.setConMoneyTotalSum(con.getConMoneyTotalSum());
        }
        return BaseResponse.ok(data);
    }

    @Override
    public List<PreProjectResponse> getAllProjectList(PreProjectRequest request, String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.NOT.getIndex(), uId);
        Map<String, Object> params = listFilter(request, authSql);
        return baseMapper.getAllProjectList(params);
    }

    @Override
    public BaseResponse getProjectStreamlineList(PreProjectRequest request, String uId) {
        log.info("项目列表查询");
        log.info("request:{}", JacksonUtils.toJSONString(request));
        List<String> userInfoDepartmentIds = getUserDepartmentSelectByUid(uId);
        log.info("userInfo:{}", JacksonUtils.toJSONString(userInfoDepartmentIds));
        boolean isPassAuth = true;
        if ("QsTaskQuality".equals(request.gettCode())
                && CollectionUtil.isNotEmpty(userInfoDepartmentIds)
                && userInfoDepartmentIds.contains("921e29bf-9928-4e66-83cb-2096ae247898")) {
            isPassAuth = false;
        }
        String authSql = "";
        if (isPassAuth) {
            authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        }
        log.info("authSql:{}", authSql);
        if (StrUtil.isNotEmpty(request.getpName())) {
            request.setpName(request.getpName().trim());
        }
        if (StrUtil.isNotEmpty(request.getpCode())) {
            request.setpCode(request.getpCode().trim());
        }
        if (StrUtil.isNotEmpty(request.getpConstructionUnitName())) {
            request.setpConstructionUnitName(request.getpConstructionUnitName().trim());
        }
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql);
        params.put("planFilter", (!Objects.isNull(request.getFilterByPlanState()) && request.getFilterByPlanState().equals("01")) ? "true" : null);
        params.put("filterByPlanState", (!Objects.isNull(request.getFilterByPlanState()) && request.getFilterByPlanState().equals("true")) ? "true" : null);
        log.info("filterByPlanState:" + params.getQuery().get("filterByPlanState"));
        params.put("outlineProjectFilter").put("ganttPlanFilter").put("excludePIds").put("excludePNames").put("pprIdFilter")
                .put("biddingPlanNew").put("biddingPlanOld");
        return getStreamlineList(params, request);
    }

    public List<String> getUserDepartmentSelectByUid(String uId) {
        return baseMapper.getUserDepartment(uId);
    }

    @Override
    public BaseResponse getProjectListForInvPlan(PreProjectRequest request, String uId) {
        if (CollectionUtils.isEmpty(request.getIpvTypeList())) {
            return BaseResponse.ok(new ArrayList<>(), 0);
        }
        if (CollectionUtils.isEmpty(request.getpCompetentDepartmentList())) {
            request.setpCompetentDepartmentList(null);
        }
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql);
        params.put("pCompDep", ValidateUtil.checkEmpty(request.getpCompetentDepartment())).put("pConstructionUnit")
                .put("pConstructionUnitName").put("pName").put("pCode").put("filterParentCollection")
                .put("pAssignmentNo").put("pParentId").put("pStatus").put("state").put("pSuperviseType")
                .put("pGroupType").put("excludePa").put("containDiscard").put("containsAnnual").put("apYear")
                .put("pAdvanceFund").put("pCorporateRisk").put("pProjectStage").put("pId").put("icmReportingMonth")
                .put("icmReportingYear").put("invPlanFlag").put("noAuth").put("ipvTypeList").put("pCompetentDepartmentList").put("planCompleteResultList").put("completeResultList").put("monthCumulativeInvProportion").put("transferStatusList");
        List<PreProjectStreamlineResponse> models = baseMapper.getProjectListForInvPlan(params.getQuery());
        Integer total = baseMapper.getProjectListForInvPlanCount(params.getQuery());
        List<PreProjectWhenGetAllResponse> all = baseMapper.getAllPreProject(Boolean.FALSE, params.getQuery().get("authSql").toString(), new QueryMap<>(request).getQuery());
        List<PreProjectStreamlineResponse> datas = new ArrayList<>();
        models.forEach(model -> {
            List<PreProjectWhenGetAllResponse> child = all.stream().filter(n -> n.getpParentId() != null && n.getpParentId().equals(model.getpId())).collect(Collectors.toList());
            model.setHavingChild(!CollectionUtils.isEmpty(child));
            model.setCanAction(true);
            datas.add(model);
        });
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getDepartmentListForInvPlan(PreProjectRequest request, String uId) {
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("icmReportingYear");
        List<PreProjectStreamlineResponse> models = baseMapper.getDepartmentListForInvPlan(params.getQuery());
        return BaseResponse.ok(models);
    }

    public BaseResponse getPreProposalIsNullList(PreProjectRequest request, String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pCategorys").put("ppProName");
        List<PreProjectStreamlineResponse> models = baseMapper.getProjectStreamlineList(params.getQuery());
        Integer total = baseMapper.getProjectStreamlineListCount(params.getQuery());
        return new BaseResponse(true, "操作成功！", null);
    }


    @Override
    public BaseResponse getProjectCjBiddingPlanList(PreProjectRequest request, String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql);
        params.put("conTypes", null);
        params.put("cpdCodes", null);
        String selectFilter = sysConfigService.getConfigByCode("cjProjectSelectFilter");
        if (ValidateUtil.isNotEmpty(selectFilter)) {
            try {
                ProjectSelectFilterDto filter = JacksonUtils.parseObject(selectFilter, ProjectSelectFilterDto.class);
                params.put("conTypes", ValidateUtil.isNotEmpty(filter.getConType()) ? filter.getConType() : null);
                params.put("cpdCodes", ValidateUtil.isNotEmpty(filter.getCpdCode()) ? filter.getCpdCode() : null);
            } catch (Exception ex) {
                log.error("基础配置cjProjectSelectFilter格式异常！", ex);
            }
        }
        return getStreamlineList(params, request);
    }

    @Override
    public BaseResponse getProjectByProOrganization(PreProjectRequest request, String uId) {
        String authSql = sysUserService.onlyProOrganizationAuth(null, uId, "P_Create_User_ID", "State");
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql);
        return getStreamlineList(params, request);
    }

    @Override
    public BaseResponse getProjectListNoAuth(PreProjectRequest request) {
        QueryMap<PreProjectRequest> params = getNoAuthFilter(request);
        List<PreProjectStreamlineResponse> datas = baseMapper.getProjectListNoAuth(params.getQuery());
        Integer total = baseMapper.getProjectListNoAuthCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getProjectListBackstage(PreProjectRequest request, LogUserInfoVo user) {
        QueryMap<PreProjectRequest> params = getNoAuthFilter(request);
        if (!user.getCode().equalsIgnoreCase("admin") &&
                !user.getCode().equalsIgnoreCase("jpmadmin")) {
            List<String> depIds = apiUtilsService.getAllChildDepartment(user.getId());
            if (CollectionUtils.isEmpty(depIds))
                return BaseResponse.fail(ResMsg.noPermission);
            params.put("depIds", depIds);
        }
        IPage<PreProjectStreamlineResponse> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectStreamlineResponse> datas = baseMapper.getProjectListBackstage(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }


    @Override
    public List<PreProject> getProjectList(List<String> idlist) {
        if (CollectionUtils.isEmpty(idlist)) return null;
        QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", Boolean.FALSE);
        wrapper.in("P_Id", idlist);
        return baseMapper.selectList(wrapper);
    }


    @Override
    public void insertPreProject(PreProject entity) {
        entity.setDeleted(Boolean.FALSE);
        baseMapper.insert(entity);
        projectCreate2ImpTempDepartment(entity.getpId());
    }

    @Override
    public void insertPreProjectDtl(List<PreProjectDtlModel> entity, String pId) {
        preProjectDtlService.createOrUpdatePreProjectDtl(entity, pId);
    }

    @Override
    public List<PreProjectDtlModel> getPreProjectDtlByPId(String pId) {
        return preProjectDtlService.getPreProjectDtlByPId(pId);
    }


    @Override
    public BaseResponse statisticsProject(String teId) {
        Map<String, Integer> datas = new HashMap<>();
        QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("P_Status", 1);
        wrapper.eq("deleted", Boolean.FALSE);
        Long count = baseMapper.selectCount(wrapper);
        datas.put("all", count == null ? 0 : count.intValue());
        List<Map<String, Object>> stages = baseMapper.statisticsProject();
        for (Map<String, Object> stage : stages) {
            datas.put(stage.get("projectStage").toString(), Integer.parseInt(stage.get("stageCount").toString()));
        }
        return BaseResponse.ok(datas);
    }

    @Override
    public WorkbenchOverviewModel getWorkbenchOverviewModel(String teId) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //质量检查今日新增数量，默认是按创建时间统计。 1为按检查日期统计
        String qsAddTodayCode = sysConfigService.getConfigByCode("qsAddToday");
        String qsAddToday = "false";
        if (StrUtil.isNotBlank(qsAddTodayCode) && "1".equals(qsAddTodayCode)) {
            qsAddToday = "true";
        }
        return baseMapper.getWorkbenchOverviewModel(qsAddToday, year, teId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateProjectCodeById(String pId, String pCode, String uId) {
        PreProject pojo = baseMapper.selectById(pId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        pojo.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), pojo));
        PreProject entity = pojo;
        pojo.setpCode(pCode);
        baseMapper.updateById(pojo);
        apiUtilsService.insertFiledChangeRecord(entity, pojo, "Pre_Project", uId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getInvestmentDate(String teId) {
        return BaseResponse.ok(baseMapper.getInvestmentDate(teId));
    }

    @Override
    public BaseResponse getProInvInfoByPid(String pId) {
        ProInvInfoModel model = baseMapper.getProInvInfoByPid(pId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        Integer start = DateUtil.getTimeYear(model.getpActualStartTime());
        Integer end = DateUtil.getTimeYear(model.getpActualCompleteDate());
        ProInvInfoModel invModel = invPlanYearService.getInvInfoByPidAndTimeYear(pId, start, end);
        model.setIpvScheduledPlan(invModel.getIpvScheduledPlan() == null ? BigDecimal.ZERO : invModel.getIpvScheduledPlan());
        model.setIpvScheduledSum(invModel.getIpvScheduledSum() == null ? BigDecimal.ZERO : invModel.getIpvScheduledSum());
        model.setIpvPlannedInvestment(invModel.getIpvPlannedInvestment() == null ? BigDecimal.ZERO : invModel.getIpvPlannedInvestment());
        model.setIpvPlannedPay(invModel.getIpvPlannedPay() == null ? BigDecimal.ZERO : invModel.getIpvPlannedPay());
        return BaseResponse.ok(model);
    }

    @Override
    public BaseResponse getProMonthlyReport(ProMonthlyReportDto request) {
        //Integer start = null;
        List<MonthlyReportExportModel> models = baseMapper.getProMonthlyReport(request.getPageStart(), request.getSize(), request.getSortFiled(), request.getSortType(), request.getYear());
        if (models != null && models.size() > 0) {
            List<String> pIds = models.stream().map(MonthlyReportExportModel::getpId).collect(Collectors.toList());
            //项目资金
            //List<ProSourcesFundsPojo> funds = proSourcesFundsService.getProSourcesFundsPojoByPids(pIds);
            List<ProInvDetail> funds = proInvDetailService.getProInvDetailPojoByPids(pIds);
            //年投资计划
            //List<InvPlanYearPojo> ipys = invPlanYearService.getInvPlanYearByYearAndPids(pIds, request.getYear());
            //月进度计划
            //List<InvMonthlyScheduleModel> monInvs = invMonthlyScheduleService.getInvMonthlyScheduleByPidsAndYearMon(pIds, year, month);
            //工程报告
            List<OaProjectReportModel> proReports = oaProjectReportService.getOaProjectReportByPidsAndYearMon(pIds, request.getYear(), request.getMonth());
            //工程报告-季报
            List<OaProjectReportModel> quarterlies = oaProjectReportService.getOaProjectQuarterlReportByPidsAndYear(pIds, request.getYear());
            //年度计划详情
            List<OaAnnualPlanDtl> apds = oaAnnualPlanDtlService.getOaAnnualPlanDtlByYearAndPids(pIds, request.getYear());
            List<MonthlyReportExportModel> allModels = new ArrayList<>();
            //赋值项目月报项目资金计划信息  年资计划信息 月进度计划 工程报告
            setMonthlyReportOther(allModels, models, funds, null, null, proReports, quarterlies, apds);
            QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(request.getTeId()))
                wrapper.eq("Te_Id", request.getTeId());
            wrapper.eq("deleted", Boolean.FALSE);
            wrapper.eq("State", ApprovalStatusEnum.Already_Over.getIndex());
            wrapper.last(" and P_Id in (select P_Id from Oa_Annual_Plan_Dtl apd left join Oa_Annual_Plan ap on ap.id=apd.Ap_Id  where ap.Ap_Year='" + request.getYear() + "')");
            Integer total = baseMapper.selectCount(wrapper).intValue();
            return BaseResponse.ok(allModels, total);
        }
        return BaseResponse.ok(models);
    }

    @Override
    public Boolean isParentProject(String pId) {
        String parentId = baseMapper.getProjectParentId(pId);
        return ValidateUtil.isEmpty(parentId) || parentId.equals("0");
    }

    @Override
    public List<String> getAllChildPids(String pId) {
        return baseMapper.getAllChildPids(pId);
    }

    @Override
    public BaseResponse getProjectInvStatistics(ProMonthlyReportDto request) {
        ProjectInvStatisticsVo oaData = oaProjectReportService.getOaProjectReportInvStatistics(request);
        //ProjectInvStatisticsVo ipvData = invPlanYearService.getInvPlanYearInvStatistics(request.getYear());
        if (ValidateUtil.isNull(oaData)) oaData = new ProjectInvStatisticsVo();
        oaData.setPInvTotal(proInvDetailService.getPInvTotal(request));
        //oaData.setIpvPlannedInvTotal(ipvData.getIpvPlannedInvTotal());
        //oaData.setIpvDoneLastYearTotal(ipvData.getIpvDoneLastYearTotal());
        return BaseResponse.ok(oaData);
    }

    @Override
    public BaseResponse getProApprovalInfo(String pId) {
        return BaseResponse.ok(baseMapper.getProApprovalInfo(pId));
    }

    @Override
    public Boolean registerProjectExist(String prId) {
        QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
        wrapper.eq("Pr_Id", prId);
        return baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public BaseResponse getUserAllPid(LogUserInfoVo user) {
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), user.getId());
        List<String> pids = baseMapper.getUserAllPid(authSql, user.isEnableTenant() ? user.getTeId() : null);
        return BaseResponse.ok(pids);
    }

    @Override
    public BaseResponse getAllProjectByOrganization(String id) {
        return BaseResponse.ok(baseMapper.getAllProjectByOrganization(id));
    }

    @Override
    public BaseResponse getProjectFundStatistics(ProjectFundStatisticsDTO request, String uId) {
        QueryMap<ProjectFundStatisticsDTO> queryMap = new QueryMap<>(request);
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        queryMap.put("authSql", authSql).put("pName").put("pId").put("pParentId").put("pCode").put("year").put("parentProject")
                .put("pUseUnit").put("pConstructionUnitName").put("pProjectStage").put("pProjectStatus");
        IPage<ProjectFundStatisticsVO> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectFundStatisticsVO> data = baseMapper.getProjectFundStatistics(page, queryMap.getQuery());
        ProjectFundStatisticsSumVO sum;
        if (CollectionUtils.isEmpty(data))
            sum = new ProjectFundStatisticsSumVO();
        else {
            sum = baseMapper.getProjectFundStatisticsSum(queryMap.getQuery());
            if (sum == null) sum = new ProjectFundStatisticsSumVO();
        }
        sum.setData(data);
        return BaseResponse.ok(sum, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getApproveBudgetAndRate(String pId) {
        ProjectApproveBudgetRateDTO data = baseMapper.getApproveBudgetAndRate(pId);
        if (data == null) data = new ProjectApproveBudgetRateDTO();
        if (data.getPApproveBudget() == null) {
            data.setPApproveBudget(BigDecimal.ZERO);
            data.setPApproveBudgetRate(new BigDecimal(1));
        } else {
            data.setPApproveBudgetRate(MathUtil.decimalDivide(data.getConMoneyTotalSum(), data.getPApproveBudget()));
        }
        return BaseResponse.ok(data);
    }

    @Override
    public void updateProjectStage(String pId, String stage) {
        if (!ValidateUtil.isAnObjEmpty(pId, stage)) {
            PreProject project = baseMapper.selectById(pId);
            project.setpProjectStage(stage);
            //项目数据完整性验证
            InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
            project.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), project));
            baseMapper.updateById(project);
        }
    }

    @Override
    public List<String> getAllAuthProjectId(String uid) {
        String authSql = sysUserService.getAuthSql(null, uid, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex(), "State");
        return baseMapper.getAllAuthProjectId(authSql);
    }

    @Override
    public BaseResponse getPreProjectDtlByIds(List<String> pIds) {
        if (pIds == null || pIds.size() < 1) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
        wrapper.eq("P_ID", pIds.get(0));
        for (int i = 1; i < pIds.size(); i++) {
            wrapper.or().eq("P_ID", pIds.get(i));
        }
        List<PreProject> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0) {
            List<String> pId = new ArrayList<>();
            datas.forEach(data -> {
                pId.add(data.getpId());
            });
            Map<String, List<PreProjectDtlModel>> map = preProjectDtlService.getPreProjectDtlByIds(pId);
            return BaseResponse.ok(map);
        }
        return BaseResponse.ok(null);
    }

    @Override
    public BaseResponse getProjectCompetentDepartmentByPid(PreProjectRequest request) {
        PreProjectResponse pojo;
        ProjectCompetentDepartmentVo data = new ProjectCompetentDepartmentVo();
        if (ValidateUtil.isEmpty(request.getpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        else {
            pojo = baseMapper.getPreProjectById(request.getpId());
            if (ValidateUtil.isNull(pojo)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            } else {
                data.setPCompetentDepartment(pojo.getpCompetentDepartment());
                data.setPCompetentDepartmentName(pojo.getpCompetentDepartmentName());
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse updateProjectAuthType(String id, Integer type) {
        PreProject project = baseMapper.selectById(id);
        if (project == null) return BaseResponse.fail(ResMsg.infoNotExist);
        project.setpAuthType(type);
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        project.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), project));
        baseMapper.updateById(project);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectClassInfo(List<String> projectIds, String uid) {
        List<ClassInfoAnnotation> projectClassInfoAnnotations = getClassInfoAnnotations(projectIds);
        log.info("projectClassInfoAnnotations:{}", JacksonUtils.toJSONString(projectClassInfoAnnotations));
        return BaseResponse.ok(projectClassInfoAnnotations);
    }

    /**
     * 获取项目信息类注解
     *
     * @return
     */
    private List<ClassInfoAnnotation> getClassInfoAnnotations() {
        List<ClassInfoAnnotation> projectClassInfoAnnotations;
        try {
            projectClassInfoAnnotations = ClassInfoUtils.getClassInfo(PreProjectTZRequest.class.getName());
            log.info("projectClassInfoAnnotations:{}", JacksonUtils.toJSONString(projectClassInfoAnnotations));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        projectClassInfoAnnotations = projectClassInfoAnnotations.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        return projectClassInfoAnnotations;
    }

    /**
     * 获取项目信息类注解
     *
     * @param projectIds
     * @return
     */
    private List<ClassInfoAnnotation> getClassInfoAnnotations(List<String> projectIds) {
        List<ClassInfoAnnotation> projectClassInfoAnnotations;
        try {
            projectClassInfoAnnotations = ClassInfoUtils.getClassInfo(PreProjectTZRequest.class.getName());
            for (ClassInfoAnnotation classInfoAnnotation : projectClassInfoAnnotations) {
                if (!classInfoAnnotation.getType().equals("date")) {
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                        if (classInfoAnnotation.getType().equals("company")) {
                            List<FilterObject> dictionaryValues = baseMapper.getCompanyValueByCodeWithPid(classInfoAnnotation.getDbValue(), projectIds);
                            dictionaryValues = ClassInfoUtils.processGenericDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryFilterValues(dictionaryValues);
                        } else if (classInfoAnnotation.getType().equals("person")) {
                            List<FilterObject> dictionaryValues = baseMapper.getPersonValueByCodeWithPid(classInfoAnnotation.getDbValue(), projectIds);
                            dictionaryValues = ClassInfoUtils.processGenericDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryFilterValues(dictionaryValues);
                        } else {
                            List<Object> dictionaryValues = baseMapper.getValueByCodeWithPid(classInfoAnnotation.getDbValue(), projectIds);
                            dictionaryValues = ClassInfoUtils.processGenericDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        }

                    }
                }
            }
            log.info("projectClassInfoAnnotations:{}", JacksonUtils.toJSONString(projectClassInfoAnnotations));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //no正序排列
        projectClassInfoAnnotations = projectClassInfoAnnotations.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        return projectClassInfoAnnotations;
    }


    /**
     * 赋值项目月报
     * 项目资金计划信息
     * 年资计划信息
     * 月进度计划
     * 工程报告
     */
    private void setMonthlyReportOther(List<MonthlyReportExportModel> allModels, List<MonthlyReportExportModel> models, List<ProInvDetail> funds, List<InvPlanYear> ipys
            , List<InvMonthlyScheduleModel> monInvs, List<OaProjectReportModel> proReports, List<OaProjectReportModel> quarterlies, List<OaAnnualPlanDtl> apds) {
        for (MonthlyReportExportModel model : models) {
            //region 项目资金
            List<ProInvDetail> proFunds = funds.stream().filter(n -> n.getpId().equals(model.getpId())).collect(Collectors.toList());
            List<ProInvDetail> fsCodes = proFunds.stream().filter(n -> !ValidateUtil.isEmpty(n.getFsCode()) && n.getFsCode().equals("01")).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(fsCodes))
                model.setFsOne(fsCodes.stream().map(ProInvDetail::getFsMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            fsCodes = proFunds.stream().filter(n -> !ValidateUtil.isEmpty(n.getFsCode()) && n.getFsCode().equals("02")).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(fsCodes))
                model.setFsTwo(fsCodes.stream().map(ProInvDetail::getFsMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            fsCodes = proFunds.stream().filter(n -> !ValidateUtil.isEmpty(n.getFsCode()) && n.getFsCode().equals("03")).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(fsCodes))
                model.setFsThree(fsCodes.stream().map(ProInvDetail::getFsMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            fsCodes = proFunds.stream().filter(n -> !ValidateUtil.isEmpty(n.getFsCode()) && n.getFsCode().equals("04")).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(fsCodes))
                model.setFsFour(fsCodes.stream().map(ProInvDetail::getFsMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            fsCodes = proFunds.stream().filter(n -> !ValidateUtil.isEmpty(n.getFsCode()) && n.getFsCode().equals("05")).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(fsCodes))
                model.setFsFive(fsCodes.stream().map(ProInvDetail::getFsMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            fsCodes = proFunds.stream().filter(n -> !ValidateUtil.isEmpty(n.getFsCode()) && n.getFsCode().equals("06")).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(fsCodes))
                model.setFsSix(fsCodes.stream().map(ProInvDetail::getFsMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            fsCodes = proFunds.stream().filter(n -> !ValidateUtil.isEmpty(n.getFsCode()) && n.getFsCode().equals("07")).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(fsCodes))
                model.setFsSeven(fsCodes.stream().map(ProInvDetail::getFsMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            model.setFsOne(MathUtil.DecimalDivisionInt(model.getFsOne(), 10000));
            model.setFsTwo(MathUtil.DecimalDivisionInt(model.getFsTwo(), 10000));
            model.setFsThree(MathUtil.DecimalDivisionInt(model.getFsThree(), 10000));
            model.setFsFour(MathUtil.DecimalDivisionInt(model.getFsFour(), 10000));
            model.setFsFive(MathUtil.DecimalDivisionInt(model.getFsFive(), 10000));
            model.setFsSix(MathUtil.DecimalDivisionInt(model.getFsSix(), 10000));
            model.setFsSeven(MathUtil.DecimalDivisionInt(model.getFsSeven(), 10000));
            model.setFsAmtTotal(model.getFsOne().add(model.getFsTwo()).add(model.getFsThree()).add(model.getFsFour())
                    .add(model.getFsFive()).add(model.getFsSix()).add(model.getFsSeven()));
            //endregion

            //region 年投资计划信息
//            if (ValidateUtil.isNotEmpty(ipys)) {
//                List<InvPlanYearPojo> ipy = ipys.stream().filter(n ->ValidateUtil.isNotEmpty(n.getPId())&&n.getPId().equals(model.getpId())).collect(Collectors.toList());
//                if (ValidateUtil.isNotEmpty(ipy)) {
//                    InvPlanYearPojo ipyPojo = ipy.stream().findFirst().get();
//                    model.setIpvDoneLastYear(MathUtil.DecimalDivisionInt(
//                            ipy.stream().map(InvPlanYearPojo::getIpvDoneLastYear).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add), 10000));
//                    model.setIpvPlannedInvestment(MathUtil.DecimalDivisionInt(
//                            ipy.stream().map(InvPlanYearPojo::getIpvPlannedInvestment).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add), 10000));
//                    model.setIpvTImgProgress(ipyPojo.getIpvTImgProgress());
//                }
//            } else {
//                model.setIpvDoneLastYear(BigDecimal.ZERO);
//                model.setIpvPlannedInvestment(BigDecimal.ZERO);
//                model.setIpvTImgProgress("");
//            }
            //endregion

            //region 月投资计划
//            List<InvMonthlyScheduleModel> proInvs = monInvs.stream().filter(n -> n.getpId().equals(model.getpId())).collect(Collectors.toList());
//            if (proInvs != null && proInvs.size() > 0) {
//                proInvs = proInvs.stream().sorted(Comparator.comparing(InvMonthlyScheduleModel::getImsMonth)).collect(Collectors.toList());
//                for (InvMonthlyScheduleModel proInv : proInvs) {
//                    if (proInv.getImsMonth() <= 3) {
//                        //季度1
//                        model.setInvQuarterOne(proInv.getImsCumulativeInv());
//                        model.setProgressQuarterOne(proInv.getImsImgProgress());
//                    } else if (proInv.getImsMonth() <= 6) {
//                        //季度2
//                        model.setInvQuarterTwo(proInv.getImsCumulativeInv());
//                        model.setProgressQuarterTwo(proInv.getImsImgProgress());
//                    } else if (proInv.getImsMonth() <= 9) {
//                        //季度3
//                        model.setInvQuarterThree(proInv.getImsCumulativeInv());
//                        model.setProgressQuarterThree(proInv.getImsImgProgress());
//                    } else {
//                        //季度4
//                        model.setInvQuarterFour(proInv.getImsCumulativeInv());
//                        model.setProgressQuarterFour(proInv.getImsImgProgress());
//                    }
//                }
//            }
//            model.setInvQuarterOne(MathUtil.DecimalDivisionInt(model.getInvQuarterOne(), 10000));
//            model.setProgressQuarterOne(ValidateUtil.isEmpty(model.getProgressQuarterOne()) ? "" : model.getProgressQuarterOne());
//            model.setInvQuarterTwo(MathUtil.DecimalDivisionInt(model.getInvQuarterTwo(), 10000));
//            model.setProgressQuarterTwo(ValidateUtil.isEmpty(model.getProgressQuarterTwo()) ? "" : model.getProgressQuarterTwo());
//            model.setInvQuarterThree(MathUtil.DecimalDivisionInt(model.getInvQuarterThree(), 10000));
//            model.setProgressQuarterThree(ValidateUtil.isEmpty(model.getProgressQuarterThree()) ? "" : model.getProgressQuarterThree());
//            model.setInvQuarterFour(MathUtil.DecimalDivisionInt(model.getInvQuarterFour(), 10000));
//            model.setProgressQuarterFour(ValidateUtil.isEmpty(model.getProgressQuarterFour()) ? "" : model.getProgressQuarterFour());
            //endregion

            //region 工程报告
            List<OaProjectReportModel> reports = proReports.stream().filter(n -> n.getpId().equals(model.getpId())).collect(Collectors.toList());
            model.setPrInvRate(BigDecimal.ZERO);
            model.setPrMonthInvRate(BigDecimal.ZERO);
            if (ValidateUtil.isNotEmpty(reports)) {
                OaProjectReportModel report = reports.stream().findFirst().get();
                model.setPrProUnder(report.getPrProUnder());
                model.setPrProcedures(report.getPrProcedures());
                model.setPrImgProgress(report.getPrImgProgress());
                model.setPrCompleteInv(report.getPrCompleteInv());
                model.setPrYearInv(report.getPrYearInv());
                model.setPrInvRate(report.getPrInvRate());
                model.setPrMonthInv(report.getPrMonthInv());
                model.setPrMonthInvRate(report.getPrMonthInvRate());
                model.setPrWhetherLag(report.getPrWhetherLag());
                model.setPrReasonLag(report.getPrReasonLag());
                model.setPrSuggest(report.getPrSuggest());
                model.setPrStartWork(report.getPrStartWork());
                model.setPrNumberPresent(report.getPrNumberPresent() == null ? 0 : report.getPrNumberPresent());

                model.setIpvPlannedInvestment(reports.stream().map(OaProjectReportModel::getPrPlannedInvestment).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
                model.setIpvDoneLastYear(reports.stream().map(OaProjectReportModel::getPrDoneLastYear).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
                model.setInvQuarterOne(report.getPrInvQuarterOne());
                model.setInvQuarterTwo(report.getPrInvQuarterTwo());
                model.setInvQuarterThree(report.getPrInvQuarterThree());
                model.setInvQuarterFour(report.getPrInvQuarterFour());
                model.setIpvTImgProgress(report.getPrTImgProgress());
                model.setProgressQuarterOne(report.getPrProgressQuarterOne());
                model.setProgressQuarterTwo(report.getPrProgressQuarterTwo());
                model.setProgressQuarterThree(report.getPrProgressQuarterThree());
                model.setProgressQuarterFour(report.getPrProgressQuarterFour());
                model.setWrDate(report.getWrDate());
                model.setPrUrbanPlan(report.getPrUrbanPlan());
                model.setPrAdjustInv(MathUtil.DecimalDivisionInt(report.getPrAdjustInv(), 10000));
                model.setPrDutyMain(report.getPrDutyMain());
                model.setPrInitiationPlan(report.getPrInitiationPlan());
                model.setPrResponsibleDep(report.getPrResponsibleDep());
                model.setPrResponsibleLeader(report.getPrResponsibleLeader());
                model.setPrProjectInvestment(MathUtil.DecimalDivisionInt(report.getPrProjectInvestment(), 10000));
                model.setPrInvestmentType(JudgeUtil.StrIsEmptyRtStr(report.getPrInvestmentType()));
                model.setPrIsBudget(JudgeUtil.StrIsEmptyRtStr(report.getPrIsBudget()));
                model.setPrBudgetInvestment(MathUtil.DecimalDivisionInt(report.getPrBudgetInvestment(), 10000));
                model.setPrMainContent(JudgeUtil.StrIsEmptyRtStr(report.getPrMainContent()));
                model.setPrAdjustmentType(JudgeUtil.StrIsEmptyRtStr(report.getPrAdjustmentType()));
                model.setPrAdjustmentTime(report.getPrAdjustmentTime());
                model.setPrLastYearRate(report.getPrLastYearRate());
                model.setPrIsStartWork(JudgeUtil.StrIsEmptyRtStr(report.getPrIsStartWork()));
                model.setPrIsFinish(JudgeUtil.StrIsEmptyRtStr(report.getPrIsFinish()));
                model.setPrPlannedLastInv(MathUtil.DecimalDivisionInt(report.getPrPlannedLastInv(), 10000));
                model.setPrDoneLastYear(MathUtil.DecimalDivisionInt(report.getPrDoneLastYear(), 10000));
                model.setPrPlannedInvestment(MathUtil.DecimalDivisionInt(report.getPrPlannedInvestment(), 10000));
                model.setPrRefine(JudgeUtil.StrIsEmptyRtStr(report.getPrRefine()));
                model.setPrSocialProject(JudgeUtil.StrIsEmptyRtStr(report.getPrSocialProject()));
                model.setPrHandleAffairs(JudgeUtil.StrIsEmptyRtStr(report.getPrHandleAffairs()));
                model.setPrOther(JudgeUtil.StrIsEmptyRtStr(report.getPrOther()));
                model.setPrImpState(JudgeUtil.StrIsEmptyRtStr(report.getPrImpState()));

            }
            model.setPrProUnder(JudgeUtil.StrIsEmptyRtStr(model.getPrProUnder()));
            model.setPrProcedures(JudgeUtil.StrIsEmptyRtStr(model.getPrProcedures()));
            model.setPrImgProgress(JudgeUtil.StrIsEmptyRtStr(model.getPrImgProgress()));
            model.setPrCompleteInv(MathUtil.DecimalDivisionInt(model.getPrCompleteInv(), 10000));
            model.setPrYearInv(MathUtil.DecimalDivisionInt(model.getPrYearInv(), 10000));
            model.setPrMonthInv(MathUtil.DecimalDivisionInt(model.getPrMonthInv(), 10000));
            model.setpInvTotal(MathUtil.DecimalDivisionInt(model.getpInvTotal(), 10000));
            model.setPrReasonLag(JudgeUtil.StrIsEmptyRtStr(model.getPrReasonLag()));
            model.setPrSuggest(JudgeUtil.StrIsEmptyRtStr(model.getPrSuggest()));
            model.setPrStartWork(JudgeUtil.StrIsEmptyRtStr(model.getPrStartWork()));

            model.setIpvPlannedInvestment(MathUtil.DecimalDivisionInt(model.getIpvPlannedInvestment(), 10000));
            model.setIpvDoneLastYear(MathUtil.DecimalDivisionInt(model.getIpvDoneLastYear(), 10000));
            model.setInvQuarterOne(MathUtil.DecimalDivisionInt(model.getInvQuarterOne(), 10000));
            model.setInvQuarterTwo(MathUtil.DecimalDivisionInt(model.getInvQuarterTwo(), 10000));
            model.setInvQuarterThree(MathUtil.DecimalDivisionInt(model.getInvQuarterThree(), 10000));
            model.setInvQuarterFour(MathUtil.DecimalDivisionInt(model.getInvQuarterFour(), 10000));
            model.setIpvTImgProgress(JudgeUtil.StrIsEmptyRtStr(model.getIpvTImgProgress()));
            model.setProgressQuarterOne(JudgeUtil.StrIsEmptyRtStr(model.getProgressQuarterOne()));
            model.setProgressQuarterTwo(JudgeUtil.StrIsEmptyRtStr(model.getProgressQuarterTwo()));
            model.setProgressQuarterThree(JudgeUtil.StrIsEmptyRtStr(model.getProgressQuarterThree()));
            model.setProgressQuarterFour(JudgeUtil.StrIsEmptyRtStr(model.getProgressQuarterFour()));
            model.setpConsPeriod(!ValidateUtil.isEmpty(model.getpConsPeriod()) && model.getpConsPeriod().equals("~") ? model.getpConsPeriod().replaceAll("~", "")
                    : model.getpConsPeriod());
            //endregion
            //region 季报
            List<OaProjectReportModel> proQuarter = quarterlies.stream().filter(n -> n.getpId().equals(model.getpId())).collect(Collectors.toList());
            BigDecimal cs = BigDecimal.ZERO;
            for (int i = 1; i <= 4; i++) {
                int finalI = i;
                List<OaProjectReportModel> quarter = proQuarter.stream().filter(n -> n.getWrQuarter() == finalI).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(quarter)) {
                    OaProjectReportModel q = quarter.stream().findFirst().get();
                    if (i == 1) {
                        model.setQuarterImg1(q.getPrQuarterlyImageSchedule());
                        model.setQuarterComInv1(MathUtil.DecimalDivisionInt(q.getPrQuarterlyCompleteInvestment(), 10000));
                        cs = model.getQuarterComInv1();
                        model.setQuarterCumulativeInv1(cs);
                        model.setQuarterRate1(q.getPrQuarterlyFinishRate());
                    }
                    if (i == 2) {
                        model.setQuarterImg2(q.getPrQuarterlyImageSchedule());
                        model.setQuarterComInv2(MathUtil.DecimalDivisionInt(q.getPrQuarterlyCompleteInvestment(), 10000));
                        cs = cs.add(model.getQuarterComInv2());
                        model.setQuarterCumulativeInv2(cs);
                        model.setQuarterRate2(q.getPrQuarterlyFinishRate());
                    }
                    if (i == 3) {
                        model.setQuarterImg3(q.getPrQuarterlyImageSchedule());
                        model.setQuarterComInv3(MathUtil.DecimalDivisionInt(q.getPrQuarterlyCompleteInvestment(), 10000));
                        cs = cs.add(model.getQuarterComInv3());
                        model.setQuarterCumulativeInv3(cs);
                        model.setQuarterRate3(q.getPrQuarterlyFinishRate());
                    }
                    if (i == 4) {
                        model.setQuarterImg4(q.getPrQuarterlyImageSchedule());
                        model.setQuarterComInv4(MathUtil.DecimalDivisionInt(q.getPrQuarterlyCompleteInvestment(), 10000));
                        cs = cs.add(model.getQuarterComInv4());
                        model.setQuarterCumulativeInv4(cs);
                        model.setQuarterRate4(q.getPrQuarterlyFinishRate());
                    }
                }
            }
            //endregion
            //region 年度计划
            List<OaAnnualPlanDtl> apd = apds.stream().filter(n -> n.getPId().equals(model.getpId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(apd)) {
                OaAnnualPlanDtl oaApd = apd.stream().findFirst().get();
                model.setApdCheckType(oaApd.getApdCheckType());
            }
            //endregion
            allModels.add(model);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePreProject(PreProjectRequest request, String uId, boolean skipCodeGeneration) {
        return createOrUpdatePreProject(request, uId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "pId", codeParam = "pCode", codeRule = "Project", method = "setpCode", beanClass = PreProjectRequest.class)
    public BaseResponse createOrUpdatePreProject(PreProjectRequest request, String uId) {
        log.info("PreProjectRequest:{}", JacksonUtils.toJSONString(request));
        PreProject pojo = new PreProject();
        //部分项目编码非必填
        String codeRequired = sysConfigService.getConfigByCode("projectCodeRequired");
        codeRequired = ValidateUtil.isNotEmpty(codeRequired) ? codeRequired : "1";
        if (ValidateUtil.isEmpty(request.getpCode()) && codeRequired.equals("1"))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, pojo);
        if (codeRequired.equals("1") || ValidateUtil.isNotEmpty(request.getpCode())) {
            QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(request.getTeId()))
                wrapper.eq("Te_Id", request.getTeId());
            wrapper.eq("P_Code", request.getpCode());
            if (!ValidateUtil.isEmpty(request.getpId()))
                wrapper.ne("P_Id", request.getpId());
            wrapper.eq("deleted", Boolean.FALSE);
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(ResMsg.codeIsRepeat);
        }
        if (ValidateUtil.isEmpty(request.getpId())) {
            sysUserService.checkTenantRuleByUid(uId, "project");
            pojo.setpNameHistory(pojo.getpName());
            pojo.setpCreateTime(new Date());
            pojo.setpUpdateTime(new Date());
            pojo.setpId(GenerateUUID.getUUID());
            if (ValidateUtil.isEmpty(request.getpParentId()))
                pojo.setpParentId("0");
            pojo.setDeleted(Boolean.FALSE);
            //走流程版本不传状态
            pojo.setpStatus(ValidateUtil.isEmpty(request.getpStatus()) ? "1" : request.getpStatus());
            pojo.setState(ValidateUtil.isNotNull(request.getState()) ? request.getState() : ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(pojo);
            String buildModel = pojo.getpBuildModel();
            if ("007".equals(buildModel)) {
                List<PpOwnerUnitDTO> ppOwnerUnitDtos = request.getPpOwnerUnits();
                if (ValidateUtil.isNotEmpty(ppOwnerUnitDtos)) {
                    List<PpOwnerUnit> ppOwnerUnits = new ArrayList<>();
                    for (PpOwnerUnitDTO ppOwnerUnit : ppOwnerUnitDtos) {
                        PpOwnerUnit po = new PpOwnerUnit();
                        BeanUtils.copyProperties(ppOwnerUnit, po);
                        po.setPId(pojo.getpId());
                        ppOwnerUnits.add(po);
                    }
                    ppOwnerUnitService.saveBatch(ppOwnerUnits);
                }
            }
            proConstructionAreaDtlService.insertProConstructionAreaDtls(request.getProConstructionAreaDtls(), pojo.getpId(), "create");
            proSourcesFundsService.insertProSourcesFunds(request.getProSourcesFunds(), pojo.getpId(), "create");
            proInvDetailService.insertProSourcesFunds(request.getProInvDetails(), pojo.getpId(), "create");
            proHouseDtlService.insertProHouseDtls(request.getProHouseDtls(), pojo.getpId(), "create");
            sysFileService.saveFile(pojo.getpId(), request.gettId(), pojo.getState() + "", pojo.getpCreateUserId(), pojo.getpCreateUserName()
                    , pojo.getpCreateTime(), pojo.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getpAssignmentFile()
                    , request.getpCommitteeApprovaDocument(), request.getpCityLeaderMeetingMinutes(), request.getpCityLeaderInstructions(), request.getpOther(),
                    request.getpApprovaDocument(), request.getpLeaderMeetingMinutes(), request.getpLeaderInstructions(), request.getpEstimateFile(), request.getpInvFile()
                    , request.getpPassVideo(), request.getpEconomicFile(), request.getpProposalFile(), request.getpReportFile(), request.getpOpinionFile(), request.getpPlanFile(),
                    request.getpFeasibilityFile(), request.getpApproveFile(), request.getpConstructionFile(), request.getpSbgjgzfFile(), request.getpGcjsqtFile(), request.getpYbFile(), request.getPrCityLeaderMeetingMinutes(), request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(), request.getPrOther()), pojo.getpId());
            projectCreate2ImpTempDepartment(pojo.getpId());
        } else {
            PreProject entity = baseMapper.selectById(request.getpId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            pojo.setpCreateUserId(entity.getpCreateUserId());
            pojo.setpCreateUserName(entity.getpCreateUserName());
            pojo.setpCreateTime(entity.getpCreateTime());
            pojo.setpUpdateTime(new Date());
            //pojo.setpCode(entity.getpCode());
            pojo.setState(entity.getState());
            if (ValidateUtil.isNotEmpty(pojo.getpName())) {
                if (!pojo.getpName().equals(entity.getpName())) {
                    pojo.setpNameHistory(pojo.getpNameHistory() + "；" + entity.getpName());
                }
            }
            pojo.setStateTime(entity.getStateTime());
            pojo.setDeleted(entity.getDeleted());
            baseMapper.updateById(pojo);
            String buildModel = pojo.getpBuildModel();
            if ("007".equals(buildModel)) {
                List<PpOwnerUnitDTO> ppOwnerUnitDtos = request.getPpOwnerUnits();
                if (ValidateUtil.isNotEmpty(ppOwnerUnitDtos)) {
                    List<PpOwnerUnit> ppOwnerUnits = new ArrayList<>();
                    for (PpOwnerUnitDTO ppOwnerUnit : ppOwnerUnitDtos) {
                        PpOwnerUnit po = new PpOwnerUnit();
                        BeanUtils.copyProperties(ppOwnerUnit, po);
                        po.setPId(pojo.getpId());
                        ppOwnerUnits.add(po);
                    }
                    ppOwnerUnitService.updateBatchById(ppOwnerUnits);
                }
            }
            if (Objects.equals(pojo.getState(), ApprovalStatusEnum.Returned_Initiator.getIndex()))
                apiUtilsService.insertFiledChangeRecord(entity, pojo, "Pre_Project", uId);
            proConstructionAreaDtlService.insertProConstructionAreaDtls(request.getProConstructionAreaDtls(), pojo.getpId(), "update");
            proSourcesFundsService.insertProSourcesFunds(request.getProSourcesFunds(), pojo.getpId(), "update");
            proInvDetailService.insertProSourcesFunds(request.getProInvDetails(), pojo.getpId(), "update");
            proHouseDtlService.insertProHouseDtls(request.getProHouseDtls(), pojo.getpId(), "update");
            sysFileService.saveFile(pojo.getpId(), request.gettId(), pojo.getState() + "", pojo.getpCreateUserId(), pojo.getpCreateUserName()
                    , pojo.getpCreateTime(), pojo.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(
                    Arrays.asList(
                            request.getFileRelationId(), request.getpAssignmentFile(),
                            request.getpCommitteeApprovaDocument(), request.getpCityLeaderMeetingMinutes(),
                            request.getpCityLeaderInstructions(), request.getpOther(),
                            request.getpApprovaDocument(), request.getpLeaderMeetingMinutes(),
                            request.getpLeaderInstructions(), request.getpEstimateFile(),
                            request.getpInvFile(), request.getpPassVideo(),
                            request.getpEconomicFile(), request.getpProposalFile(),
                            request.getpReportFile(), request.getpOpinionFile(),
                            request.getpPlanFile(), request.getpFeasibilityFile(),
                            request.getpApproveFile(), request.getpConstructionFile(),
                            request.getpSbgjgzfFile(), request.getpGcjsqtFile(),
                            request.getpYbFile(), request.getPrCityLeaderMeetingMinutes(),
                            request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(),
                            request.getPrOther()
                    ),
                    Arrays.asList(
                            request.getFileIds(), request.getpAssignmentFileIds(),
                            request.getpCommitteeApprovaDocumentIds(), request.getpCityLeaderMeetingMinutesIds(),
                            request.getpCityLeaderInstructionsIds(), request.getpOtherIds(),
                            request.getpApprovaDocumentIds(), request.getpLeaderMeetingMinutesIds(),
                            request.getpLeaderInstructionsIds(), request.getpEstimateFileIds(),
                            request.getpInvFileIds(), request.getpPassVideoIds(),
                            request.getpEconomicFileIds(), request.getpProposalFileIds(),
                            request.getpReportFileIds(), request.getpOpinionFileIds(),
                            request.getpPlanFileIds(), request.getpFeasibilityFileIds(),
                            request.getpApproveFileIds(), request.getpConstructionFileIds(),
                            request.getpSbgjgzfFileIds(), request.getpGcjsqtFileIds(),
                            request.getpYbFileIds(), request.getPrCityLeaderMeetingMinutesFileIds(),
                            request.getPrCommitteeApprovaDocumentFileIds(), request.getPrCityLeaderInstructionsFileIds(),
                            request.getPrOtherFileIds()
                    ), pojo.getpId()
            );
        }
        proFundNoService.createOrUpdateProFundNo(request.getProFundNos(), pojo.getpId());

        preProjectDtlService.createOrUpdatePreProjectDtl(request.getPreProjectDtl(), pojo.getpId());
        return BaseResponse.ok(pojo.getpId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "pId", codeParam = "pCode", codeRule = "Project", method = "setpCode", beanClass = PreProjectRequest.class)
    public BaseResponse createOrUpdatePreProjectCopy(PreProjectRequest request, String uId) {
        log.info("PreProjectRequest:{}", JacksonUtils.toJSONString(request));
        PreProject pojo = new PreProject();
        //部分项目编码非必填
        String codeRequired = sysConfigService.getConfigByCode("projectCodeRequired");
        codeRequired = ValidateUtil.isNotEmpty(codeRequired) ? codeRequired : "1";
        if (ValidateUtil.isEmpty(request.getpCode()) && codeRequired.equals("1"))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, pojo);
        if (codeRequired.equals("1") || ValidateUtil.isNotEmpty(request.getpCode())) {
            QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(request.getTeId()))
                wrapper.eq("Te_Id", request.getTeId());
            wrapper.eq("P_Code", request.getpCode());
            if (!ValidateUtil.isEmpty(request.getpId()))
                wrapper.ne("P_Id", request.getpId());
            wrapper.eq("deleted", Boolean.FALSE);
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(ResMsg.codeIsRepeat);
        }
        if (ValidateUtil.isEmpty(request.getpId())) {
            sysUserService.checkTenantRuleByUid(uId, "project");
            pojo.setpNameHistory(pojo.getpName());
            pojo.setpCreateTime(new Date());
            pojo.setpUpdateTime(new Date());
            pojo.setpId(GenerateUUID.getUUID());
            if (ValidateUtil.isEmpty(request.getpParentId()))
                pojo.setpParentId("0");
            pojo.setDeleted(Boolean.FALSE);
            //走流程版本不传状态
            pojo.setpStatus(ValidateUtil.isEmpty(request.getpStatus()) ? "1" : request.getpStatus());
            pojo.setState(ValidateUtil.isNotNull(request.getState()) ? request.getState() : ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(pojo);
            proConstructionAreaDtlService.insertProConstructionAreaDtls(request.getProConstructionAreaDtls(), pojo.getpId(), "create");
            proSourcesFundsService.insertProSourcesFunds(request.getProSourcesFunds(), pojo.getpId(), "create");
            proInvDetailService.insertProSourcesFunds(request.getProInvDetails(), pojo.getpId(), "create");
            proHouseDtlService.insertProHouseDtls(request.getProHouseDtls(), pojo.getpId(), "create");
            sysFileService.saveFile(pojo.getpId(), request.gettId(), pojo.getState() + "", pojo.getpCreateUserId(), pojo.getpCreateUserName()
                    , pojo.getpCreateTime(), pojo.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getpAssignmentFile()
                    , request.getpCommitteeApprovaDocument(), request.getpCityLeaderMeetingMinutes(), request.getpCityLeaderInstructions(), request.getpOther(),
                    request.getpApprovaDocument(), request.getpLeaderMeetingMinutes(), request.getpLeaderInstructions(), request.getpEstimateFile(), request.getpInvFile()
                    , request.getpPassVideo(), request.getpEconomicFile(), request.getpProposalFile(), request.getpReportFile(), request.getpOpinionFile(), request.getpPlanFile(),
                    request.getpFeasibilityFile(), request.getpApproveFile(), request.getpConstructionFile(), request.getpSbgjgzfFile(), request.getpGcjsqtFile(), request.getpYbFile(), request.getPrCityLeaderMeetingMinutes(), request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(), request.getPrOther()), pojo.getpId());
            projectCreate2ImpTempDepartment(pojo.getpId());
        } else {
            PreProject entity = baseMapper.selectById(request.getpId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            pojo.setpCreateUserId(entity.getpCreateUserId());
            pojo.setpCreateUserName(entity.getpCreateUserName());
            pojo.setpCreateTime(entity.getpCreateTime());
            pojo.setpUpdateTime(new Date());
            //pojo.setpCode(entity.getpCode());
            pojo.setState(entity.getState());
            if (ValidateUtil.isNotEmpty(pojo.getpName())) {
                if (!pojo.getpName().equals(entity.getpName())) {
                    pojo.setpNameHistory(pojo.getpNameHistory() + "；" + entity.getpName());
                }
            }
            pojo.setStateTime(entity.getStateTime());
            pojo.setDeleted(entity.getDeleted());
            baseMapper.updateById(pojo);
            if (Objects.equals(pojo.getState(), ApprovalStatusEnum.Returned_Initiator.getIndex()))
                apiUtilsService.insertFiledChangeRecord(entity, pojo, "Pre_Project", uId);
            proConstructionAreaDtlService.insertProConstructionAreaDtls(request.getProConstructionAreaDtls(), pojo.getpId(), "update");
            proSourcesFundsService.insertProSourcesFunds(request.getProSourcesFunds(), pojo.getpId(), "update");
            proInvDetailService.insertProSourcesFunds(request.getProInvDetails(), pojo.getpId(), "update");
            proHouseDtlService.insertProHouseDtls(request.getProHouseDtls(), pojo.getpId(), "update");
            sysFileService.saveFile(pojo.getpId(), request.gettId(), pojo.getState() + "", pojo.getpCreateUserId(), pojo.getpCreateUserName()
                    , pojo.getpCreateTime(), pojo.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(
                    Arrays.asList(
                            request.getFileRelationId(), request.getpAssignmentFile(),
                            request.getpCommitteeApprovaDocument(), request.getpCityLeaderMeetingMinutes(),
                            request.getpCityLeaderInstructions(), request.getpOther(),
                            request.getpApprovaDocument(), request.getpLeaderMeetingMinutes(),
                            request.getpLeaderInstructions(), request.getpEstimateFile(),
                            request.getpInvFile(), request.getpPassVideo(),
                            request.getpEconomicFile(), request.getpProposalFile(),
                            request.getpReportFile(), request.getpOpinionFile(),
                            request.getpPlanFile(), request.getpFeasibilityFile(),
                            request.getpApproveFile(), request.getpConstructionFile(),
                            request.getpSbgjgzfFile(), request.getpGcjsqtFile(),
                            request.getpYbFile(), request.getPrCityLeaderMeetingMinutes(),
                            request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(),
                            request.getPrOther()
                    ),
                    Arrays.asList(
                            request.getFileIds(), request.getpAssignmentFileIds(),
                            request.getpCommitteeApprovaDocumentIds(), request.getpCityLeaderMeetingMinutesIds(),
                            request.getpCityLeaderInstructionsIds(), request.getpOtherIds(),
                            request.getpApprovaDocumentIds(), request.getpLeaderMeetingMinutesIds(),
                            request.getpLeaderInstructionsIds(), request.getpEstimateFileIds(),
                            request.getpInvFileIds(), request.getpPassVideoIds(),
                            request.getpEconomicFileIds(), request.getpProposalFileIds(),
                            request.getpReportFileIds(), request.getpOpinionFileIds(),
                            request.getpPlanFileIds(), request.getpFeasibilityFileIds(),
                            request.getpApproveFileIds(), request.getpConstructionFileIds(),
                            request.getpSbgjgzfFileIds(), request.getpGcjsqtFileIds(),
                            request.getpYbFileIds(), request.getPrCityLeaderMeetingMinutesFileIds(),
                            request.getPrCommitteeApprovaDocumentFileIds(), request.getPrCityLeaderInstructionsFileIds(),
                            request.getPrOtherFileIds()
                    ), pojo.getpId()
            );
        }
        proFundNoService.createOrUpdateProFundNo(request.getProFundNos(), pojo.getpId());

        preProjectDtlService.createOrUpdatePreProjectDtl(request.getPreProjectDtl(), pojo.getpId());
        return BaseResponse.ok(pojo.getpId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse saveForGZ(PreProjectRequestDto request) {
        PreProject saveEntity = new PreProject();
        PreProject queryEntity = baseMapper.selectById(request.getpId());
        BeanUtils.copyProperties(request, saveEntity);
        if (null == queryEntity) {
            //新增
            saveEntity.setpNameHistory(saveEntity.getpName());
            saveEntity.setpParentId("0");
            saveEntity.setpStatus("1");
            saveEntity.setState(ApprovalStatusEnum.Already_Over.getIndex());
            saveEntity.setStateTime(new Date());
            saveEntity.setpCreateUserId("68D319413F904195A859973BD4C5702D");
            saveEntity.setpCreateUserName("admin");
            saveEntity.setpCreateTime(new Date());
            saveEntity.setpUpdateTime(new Date());
            saveEntity.setDeleted(Boolean.FALSE);
            this.save(saveEntity);
        } else {
            //更新
            if (!saveEntity.getpName().equals(queryEntity.getpName())) {
                saveEntity.setpNameHistory(queryEntity.getpNameHistory() + "；" + queryEntity.getpName());
            }
            saveEntity.setpUpdateTime(new Date());
            this.updateById(saveEntity);
        }

        //附件
        sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getpEstimateFile(), request.getpEconomicFile(), request.getpProposalFile(), request.getpReportFile()), saveEntity.getpId());

        //资金来源
        proSourcesFundsService.insertProSourcesFunds(request.getProSourcesFunds(), saveEntity.getpId(), "update");

        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse BjCreateOrUpdatePreProject(PreProjectRequest request, String uId) {
        PreProject pojo = new PreProject();
        //区分大小项目 生成编码规则
        String codeRule = null;
        if (ValidateUtil.isEmpty(request.getpParentId())) {
            if (ValidateUtil.isEmpty(request.getpId()) && apiUtilsService.sequenceRestEnable("Project")) {
                request.setpCode(apiUtilsService.generateCodeByTCode("Project", request));
                if (ValidateUtil.isEmpty(request.getpCode()))
                    return BaseResponse.fail(ResMsg.codeGenerError);
                codeRule = "Project";
            } else if (ValidateUtil.isEmpty(request.getpId()) && ValidateUtil.isEmpty(request.getpCode())) {
                return BaseResponse.fail(ResMsg.codeIsEmpty);
            }
        } else {
            if (ValidateUtil.isEmpty(request.getpId()) && apiUtilsService.sequenceRestEnable("SubProject")) {
                request.setpCode(apiUtilsService.generateCodeByCustomized("SubProject", request.getpParentId(), null, null, request));
                if (ValidateUtil.isEmpty(request.getpCode()))
                    return BaseResponse.fail(ResMsg.codeGenerError);
                codeRule = "SubProject";
            } else if (ValidateUtil.isEmpty(request.getpId()) && ValidateUtil.isEmpty(request.getpCode())) {
                return BaseResponse.fail(ResMsg.codeIsEmpty);
            }
        }
        BeanUtils.copyProperties(request, pojo);
        QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        wrapper.eq("P_Code", request.getpCode());
        if (!ValidateUtil.isEmpty(request.getpId()))
            wrapper.ne("P_Id", request.getpId());
        wrapper.eq("deleted", Boolean.FALSE);
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (ValidateUtil.isEmpty(request.getpId())) {
            sysUserService.checkTenantRuleByUid(uId, "project");
            pojo.setpCreateTime(new Date());
            pojo.setpUpdateTime(new Date());
            pojo.setpId(GenerateUUID.getUUID());
            pojo.setpNameHistory(pojo.getpName());
            if (ValidateUtil.isEmpty(request.getpParentId()))
                pojo.setpParentId("0");
            pojo.setDeleted(Boolean.FALSE);
            pojo.setpStatus(ValidateUtil.isEmpty(request.getpStatus()) ? "1" : request.getpStatus());
            if (ValidateUtil.isEmpty(pojo.getpProjectStage()))
                //无值则设定为预立项（项目创建不填值，标段创建必填）
                pojo.setpProjectStage("00");
            pojo.setState(ValidateUtil.isEmpty(request.getState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            baseMapper.insert(pojo);
            proConstructionAreaDtlService.insertProConstructionAreaDtls(request.getProConstructionAreaDtls(), pojo.getpId(), "create");
            proSourcesFundsService.insertProSourcesFunds(request.getProSourcesFunds(), pojo.getpId(), "create");
            proHouseDtlService.insertProHouseDtls(request.getProHouseDtls(), pojo.getpId(), "create");
            sysFileService.saveFile(pojo.getpId(), request.gettId(), pojo.getState() + "", pojo.getpCreateUserId(), pojo.getpCreateUserName()
                    , pojo.getpCreateTime(), pojo.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getpAssignmentFile()
                    , request.getpCommitteeApprovaDocument(), request.getpCityLeaderMeetingMinutes(), request.getpCityLeaderInstructions(), request.getpOther(),
                    request.getpApprovaDocument(), request.getpLeaderMeetingMinutes(), request.getpLeaderInstructions(), request.getpEstimateFile(), request.getpInvFile(),
                    request.getpPassVideo(), request.getpEconomicFile(), request.getpProposalFile(), request.getpReportFile(), request.getpOpinionFile(), request.getpPlanFile(),
                    request.getpFeasibilityFile(), request.getpApproveFile(), request.getpConstructionFile(), request.getpSbgjgzfFile(), request.getpGcjsqtFile(), request.getpYbFile()), pojo.getpId());
        } else {
            PreProject entity = baseMapper.selectById(request.getpId());
            if (JudgeUtil.checkObject(entity))
                return BaseResponse.fail(ResMsg.infoNotExist);
            pojo.setpCreateUserId(entity.getpCreateUserId());
            pojo.setpCreateUserName(entity.getpCreateUserName());
            pojo.setpCreateTime(entity.getpCreateTime());
            pojo.setpUpdateTime(new Date());
            if (!pojo.getpName().equals(entity.getpName())) {
                pojo.setpNameHistory(pojo.getpNameHistory() + "；" + entity.getpName());
            }
            baseMapper.updateById(pojo);
            if (Objects.equals(pojo.getState(), ApprovalStatusEnum.Returned_Initiator.getIndex()))
                apiUtilsService.insertFiledChangeRecord(entity, pojo, "Pre_Project", uId);
            proConstructionAreaDtlService.insertProConstructionAreaDtls(request.getProConstructionAreaDtls(), pojo.getpId(), "update");
            proSourcesFundsService.insertProSourcesFunds(request.getProSourcesFunds(), pojo.getpId(), "update");
            proHouseDtlService.insertProHouseDtls(request.getProHouseDtls(), pojo.getpId(), "update");
            sysFileService.saveFile(pojo.getpId(), request.gettId(), pojo.getState() + "", pojo.getpCreateUserId(), pojo.getpCreateUserName()
                    , pojo.getpCreateTime(), pojo.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getpAssignmentFile(), request.getpCommitteeApprovaDocument(), request.getpCityLeaderMeetingMinutes(), request.getpCityLeaderInstructions(), request.getpOther(), request.getpApprovaDocument(), request.getpLeaderMeetingMinutes(), request.getpLeaderInstructions(), request.getpEstimateFile(), request.getpInvFile(), request.getpPassVideo(), request.getpEconomicFile(), request.getpProposalFile(), request.getpReportFile(), request.getpOpinionFile(), request.getpPlanFile(), request.getpFeasibilityFile(), request.getpApproveFile(), request.getpConstructionFile(), request.getpSbgjgzfFile(), request.getpGcjsqtFile(), request.getpYbFile()),
                    Arrays.asList(request.getFileIds(), request.getpAssignmentFileIds(), request.getpCommitteeApprovaDocumentIds(), request.getpCityLeaderMeetingMinutesIds(), request.getpCityLeaderInstructionsIds(), request.getpOtherIds(), request.getpApprovaDocumentIds(), request.getpLeaderMeetingMinutesIds(), request.getpLeaderInstructionsIds(), request.getpEstimateFileIds(), request.getpInvFileIds(), request.getpPassVideoIds(), request.getpEconomicFileIds(), request.getpProposalFileIds(), request.getpReportFileIds(), request.getpOpinionFileIds(), request.getpPlanFileIds(), request.getpFeasibilityFileIds(), request.getpApproveFileIds(), request.getpConstructionFileIds(), request.getpSbgjgzfFileIds(), request.getpGcjsqtFileIds(), request.getpYbFileIds()), pojo.getpId());
        }
        if (ValidateUtil.isNotEmpty(codeRule)) {
            if (codeRule.equals("SubProject"))
                apiUtilsService.refershAutoNoByCustomized(codeRule, request.getpParentId(), null, null, request);
            else
                apiUtilsService.refershAutoNo(codeRule, request);
        }
        //推送数据
        try {
            if (ValidateUtil.isEmpty(pojo.getpParentId()) || pojo.getpParentId().equals("0")) {
                //大项目
//                PreProjectInfo info = new PreProjectInfo();
//                info.setProjectNo(pojo.getpCode());
//                info.setProjectBeginDate(DateUtils.DateFormat(pojo.getpPlanStartDate(), "yyyy-MM-dd"));
//                info.setProjectEndDate(DateUtils.DateFormat(pojo.getpPlanCompleteDate(), "yyyy-MM-dd"));
//                info.setProjectName(pojo.getpName());
//                info.setProjectAmt(MathUtil.DecimalMultiplicationInt(pojo.getpApproveBudget(), 100).longValue());
//                if (!ValidateUtil.isEmpty(pojo.getpConstructionUnit())) {
//                    SysCompany com = sysCompanyService.getById(pojo.getpConstructionUnit());
//                    info.setCorporationCode(com.getcCode());
//                }
//
//                if (!ValidateUtil.isEmpty(pojo.getpCompetentDepartment())) {
//                    SysDepartment sdp = sysDepartmentService.getById(pojo.getpCompetentDepartment());
//                    info.setAgentCode(sdp.getdCode());
//                }
//                PushRequest pushRequest = new PushRequest();
//
//                pushRequest.setMsgId(GenerateUUID.createMsgId());
//                pushRequest.setTransCode("CR_PROJ");
//                pushRequest.setAppId("JPM");
//                pushRequest.setTimestamp(DateUtils.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
//                Map<String, Object> map = new HashMap<>();
//                map.put("detailCount", 1);
//                map.put("rdList", Arrays.asList(info));
//                pushRequest.setBusiContent(JacksonUtils.toJSONString(map));
//                Map<String, Object> sendData = new HashMap<>();
//                sendData.put("action", "pushProjectData");
//                sendData.put("data", JacksonUtils.toJSONString(pushRequest));
//                sendData.put("pId", pojo.getpId());
//                sender.send(JacksonUtils.toJSONString(sendData));
            }
        } catch (Exception ex) {
            log.error("推送项目信息异常！");
            log.error(ex.getMessage(), ex);
        }
        return BaseResponse.ok(pojo.getpId());
    }


    @Override
    public BaseResponse getProjectSelect(PreProjectRequest request, String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        List<PreProjectWhenGetAllResponse> models = baseMapper.getAllPreProject(Boolean.FALSE, authSql, new QueryMap<>(request).getQuery());
        List<Map<String, String>> vueSelect = new ArrayList<>();
        models.forEach(model -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", model.getpId());
            map.put("name", model.getpName());
            vueSelect.add(map);
        });
        return BaseResponse.ok(vueSelect);
    }

    /**
     * 查询当前用户可见的项目，支持按细分类别包含匹配。
     *
     * @param request 项目筛选条件
     * @param uId     当前用户 ID
     * @return 匹配的项目列表
     */
    @Override
    public BaseResponse getAllPreProject(PreProjectRequest request, String uId) {
        log.info("request{}", JacksonUtils.toJSONString(request));
        String authSql = getAuthSql(OutUserAuthEnum.NOT.getIndex(), uId);
        QueryMap<PreProjectRequest> queryMap = new QueryMap<>(request);
        if (request != null)
            queryMap.put("pName").put("pType").put("state")
                    .put("pStatus").put("pRegion").put("pProjectStage")
                    .put("pConstructionUnit").put("pCompetentDepartment")
                    .put("dataSourceType").put("projectCategoryDetails");
        log.info("queryMap{}", JacksonUtils.toJSONString(queryMap));
        List<PreProjectWhenGetAllResponse> list = baseMapper.getAllPreProject(Boolean.FALSE, authSql, queryMap.getQuery());
        return BaseResponse.ok(list);
    }

    @Override
    public BaseResponse getAllOutOrgPreProject(PreProjectRequest request, String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        QueryMap<PreProjectRequest> queryMap = new QueryMap<>(request);
        if (request != null)
            queryMap.put("pName").put("pType").put("state").put("pCategory")
                    .put("pStatus").put("pRegion").put("pProjectStage")
                    .put("pConstructionUnit").put("pCompetentDepartment");
        return BaseResponse.ok(baseMapper.getAllPreProject(Boolean.FALSE, authSql, queryMap.getQuery()));
    }


    @Override
    public BaseResponse getProjectByPid(PreProjectRequest request) {
        return getProjectByPid(request, "gw");
    }

    @Override
    public BaseResponse getProjectByPidCopy(PreProjectRequest request) {
        return getProjectByPidCopy(request, "gw");
    }

    @Override
    public BaseResponse getProjectWYList(PreProjectRequest request, String uId) {
        return getProjectList(request, uId, "wy", sysUserService.onlyCreateorAndApproverSQL(uId, "conf.Con_Create_User_Id", "conf.Con_Id", "Con_Approval_State"));
    }

    @Override
    public BaseResponse getProjectWYByPid(PreProjectRequest request) {
        return getProjectByPid(request, "wy");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProjectByPid(String pId) {
        PreProject pojo;
        if (ValidateUtil.isEmpty(pId))
            return BaseResponse.fail(ResMsg.notReqParam);
        else {
            pojo = baseMapper.selectById(pId);
            if (ValidateUtil.isNull(pojo) || (!ValidateUtil.isNull(pojo) && pojo.getDeleted() == Boolean.TRUE))
                return BaseResponse.fail(ResMsg.infoNotExist);
        }
        pojo.setDeleted(Boolean.TRUE);
        baseMapper.updateById(pojo);
        return BaseResponse.ok();
    }


    private BaseResponse getStreamlineList(QueryMap<PreProjectRequest> params, PreProjectRequest request) {
        params.put("pCompDep", ValidateUtil.checkEmpty(request.getpCompetentDepartment())).put("pConstructionUnit").put("pCompetentDepartmentName")
                .put("pConstructionUnitName").put("pName").put("pCode").put("filterParentCollection").put("haveGantPlan")
                .put("pAssignmentNo").put("pParentId").put("pStatus").put("state").put("pSuperviseType").put("tenderPlanFilter")
                .put("pGroupType").put("excludePa").put("containDiscard").put("containsAnnual").put("apYear").put("formalProject")
                .put("pAdvanceFund").put("pCorporateRisk").put("pProjectStage").put("pId").put("icmReportingMonth")
                .put("icmReportingYear").put("invPlanFlag").put("pSecType").put("pEngineeringspecialty").put("projectIds")
                .put("pCmMode").put("planFilter").put("pType").put("pCreateUserName").put("pInvCategory").put("pCategory");
        log.info((String) params.getQuery().get("filterByPlanState"));
        if (params.getQuery().get("authSql") == null) {
            params.put("noAuth", true);
        } else {
            params.put("noAuth", null);
        }
        log.info("params:{}", JacksonUtils.toJSONString(params));
        List<PreProjectStreamlineResponse> models = baseMapper.getProjectStreamlineList(params.getQuery());
        log.info("models:{}", JacksonUtils.toJSONString(models));
        Integer total = baseMapper.getProjectStreamlineListCount(params.getQuery());
        log.info("total:{}", total);
        List<PreProjectWhenGetAllResponse> all = baseMapper.getAllPreProject(Boolean.FALSE,
                (params.getQuery().get("authSql") != null ? params.getQuery().get("authSql").toString() : ""),
                params.getQuery());
        List<PreProjectStreamlineResponse> datas = new ArrayList<>();
        models.forEach(model -> {
            List<PreProjectWhenGetAllResponse> child = all.stream()
                    .filter(n -> n.getpParentId() != null && n.getpParentId().equals(model.getpId())).collect(Collectors.toList());
            model.setHavingChild(!CollectionUtils.isEmpty(child));
            model.setCanAction(true);
            datas.add(model);
        });
        checkSign(models.stream().map(PreProjectStreamlineResponse::getpId).collect(Collectors.toList()));
        return BaseResponse.ok(datas, total);
    }


    /**
     * 构造项目查询参数并查询分页列表。
     *
     * @param request 项目筛选及分页条件
     * @param uId 当前用户 ID
     * @param unit 金额显示单位
     * @param conAuthSql 合同权限 SQL
     * @return 项目分页结果
     */
    private BaseResponse getProjectList(PreProjectRequest request, String uId, String unit, String conAuthSql) {
        String authSql = getAuthSql(OutUserAuthEnum.NOT.getIndex(), uId);
        Map<String, Object> params = listFilter(request, authSql);
        params.put("conAuthSql", conAuthSql);
        return getProjectList(request, unit, authSql, params);
    }

    /**
     * 查询当前页项目，并批量补充直接子项目、合同及辅助明细。
     * 子项目计数沿用未删除条件，havingChild 单独沿用项目可见性条件。
     *
     * @param request 项目查询条件
     * @param unit    金额显示单位
     * @param authSql 项目权限 SQL
     * @param params  已构造的项目筛选及合同权限参数
     * @return 项目分页结果
     */
    private BaseResponse getProjectList(PreProjectRequest request, String unit, String authSql, Map<String, Object> params) {
        String conAuthSql = (String) params.get("conAuthSql");
        log.info("params{}", JacksonUtils.toJSONString(params));
        List<PreProjectResponse> datas = baseMapper.getPreProjectList(params);
        //log.info("datas{}", JacksonUtils.toJSONString(datas));
        Integer total = baseMapper.getPreProjectListCount(params);
        if (CollectionUtils.isEmpty(datas)) {
            return BaseResponse.ok(new ArrayList<PreProjectResponse>(), total);
        }
        if (!CollectionUtils.isEmpty(datas)) {
            List<String> projectIds = datas.stream().map(PreProjectResponse::getpId).collect(Collectors.toList());
            List<PreProjectWhenGetAllResponse> children = baseMapper.getProjectChildrenByParentIds(projectIds);
            Map<String, List<PreProjectWhenGetAllResponse>> childrenByParent = children.stream()
                    .collect(Collectors.groupingBy(PreProjectWhenGetAllResponse::getpParentId));
            List<ConTypeStatisticsVO> types = baseMapper.getConTypeStatistics(conAuthSql, projectIds);
            types = types.stream().filter(f -> StringUtils.isNotEmpty(f.getConType())).collect(Collectors.toList());
            List<ConTypeStatisticsVO> finalTypes = types;
            datas.forEach(d -> {
                if (!CollectionUtils.isEmpty(finalTypes)) {
                    List<ConTypeStatisticsVO> type = finalTypes.stream().filter(f -> f.getPId().equals(d.getpId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(type)) {
                        List<String> conTypes = type.stream().map(ConTypeStatisticsVO::getConType).distinct().collect(Collectors.toList());
                        List<ConTypeStatisticsVO> ctv = new ArrayList<>();
                        conTypes.forEach(t -> {
                            ConTypeStatisticsVO ct = new ConTypeStatisticsVO();
                            ct.setConType(t);
                            ct.setTotal(type.stream().filter(f -> f.getConType().equals(t)).collect(Collectors.toList()).size());
                            ctv.add(ct);
                        });
                        d.setConTypeStatistics(JacksonUtils.parseArray(JacksonUtils.toJSONString(ctv), Map.class));
                    } else d.setConTypeStatistics(new ArrayList<>());
                } else d.setConTypeStatistics(new ArrayList<>());
                d.setChildProjectSum(childrenByParent.getOrDefault(d.getpId(), Collections.emptyList()).size());
            });
            if (request.isStatisticsConNum()) {
                Set<String> contractProjectIds = new LinkedHashSet<>(projectIds);
                children.forEach(child -> contractProjectIds.add(child.getpId()));
                Map<String, PreProjectResponse> amountsByProject = baseMapper.getProjectListConSum(
                                params, new ArrayList<>(contractProjectIds)).stream()
                        .collect(Collectors.toMap(PreProjectResponse::getpId, amount -> amount));
                for (PreProjectResponse project : datas) {
                    List<PreProjectWhenGetAllResponse> directChildren = childrenByParent.getOrDefault(
                            project.getpId(), Collections.emptyList());
                    PreProjectResponse ownAmount = amountsByProject.get(project.getpId());
                    if (directChildren.isEmpty()) {
                        if (ownAmount != null) {
                            project.setConMoneyTotalSum(ownAmount.getConMoneyTotalSum());
                            project.setConNumTotal(ownAmount.getConNumTotal());
                        }
                        continue;
                    }
                    BigDecimal money = ownAmount == null || ownAmount.getConMoneyTotalSum() == null
                            ? BigDecimal.ZERO : ownAmount.getConMoneyTotalSum();
                    int count = ownAmount == null ? 0 : ownAmount.getConNumTotal();
                    for (PreProjectWhenGetAllResponse child : directChildren) {
                        PreProjectResponse amount = amountsByProject.get(child.getpId());
                        if (amount != null) {
                            money = money.add(amount.getConMoneyTotalSum() == null
                                    ? BigDecimal.ZERO : amount.getConMoneyTotalSum());
                            count += amount.getConNumTotal();
                        }
                    }
                    project.setConMoneyTotalSum(money);
                    project.setConNumTotal(count);
                }
            }
        }

        if (datas != null && datas.size() > 0) {
            List<String> pIds = new ArrayList<>();
            datas.forEach(data -> pIds.add(data.getpId()));
            Set<String> visibleChildParents = new HashSet<>(baseMapper.getVisibleChildParentIds(
                    Boolean.FALSE, authSql, new QueryMap<>(request).getQuery(), pIds));
            List<ProConstructionAreaDtlModel> areaDtlModels = proConstructionAreaDtlService.getProConstructionAreaDtlByPids(pIds);
            List<ProSourcesFundsModel> sourcesfunds = new ArrayList<>();
            List<ProSourcesFundsModel> funds = proSourcesFundsService.getProSourcesFundsByPids(pIds);
            List<ProHouseDtlModel> houseDtlModels = proHouseDtlService.getProHouseDtlsByPids(pIds);
            List<PreProjectResponse> newData = new ArrayList<>();
            if (unit.equals("wy")) {
                for (ProSourcesFundsModel fund : funds) {
                    fund.setFsMoney(MathUtil.DecimalDivisionInt(fund.getFsMoney(), 10000));
                    sourcesfunds.add(fund);
                }
            } else
                sourcesfunds = funds;
            for (PreProjectResponse data : datas) {
                if (data != null && StrUtil.isNotEmpty(data.getpId())) {
                    data.setHavingChild(visibleChildParents.contains(data.getpId()));
                    data.setProConstructionAreaDtls(areaDtlModels.stream().filter(n -> data.getpId().equals(n.getpId())).collect(Collectors.toList()));
                    data.setProSourcesFunds(sourcesfunds.stream().filter(n -> data.getpId().equals(n.getpId())).collect(Collectors.toList()));
                    data.setProHouseDtls(houseDtlModels.stream().filter(n -> data.getpId().equals(n.getpId())).collect(Collectors.toList()));
                    newData.add(data);
                }

            }
            datas = newData;
        }
        checkSign(datas.stream().map(PreProjectResponse::getpId).collect(Collectors.toList()));
        return BaseResponse.ok(datas, total);
    }

    private void checkSign(List<String> pids) {
        if (!CollectionUtils.isEmpty(pids)) {
            InterfaceEncDecDTO encDec = sysConfigService.getInterfaceEncDec();
            if (encDec.getPrListVerify()) {
                List<PreProject> list = baseMapper.selectBatchIds(pids);
                list.forEach(d -> {
                    apiUtilsService.signVerify(encDec, encDec.getProjectListVerify(), d, d.getSign());
                });
            }
        }
    }


    private List<PreProjectResponse> getProjectListExcel(PreProjectRequest request, String uId, String unit, String conAuthSql) {
        String authSql = getAuthSql(OutUserAuthEnum.NOT.getIndex(), uId);
        Map<String, Object> params = listFilter(request, authSql);
        params.put("conAuthSql", conAuthSql);
        List<PreProjectResponse> datas = baseMapper.getProjectListExcel(params);
        Integer total = baseMapper.getPreProjectListCount(params);
        if (!CollectionUtils.isEmpty(datas)) {
            List<String> projectIds = datas.stream().map(PreProjectResponse::getpId).collect(Collectors.toList());
            List<ConTypeStatisticsVO> types = baseMapper.getConTypeStatistics(conAuthSql, projectIds);
            types = types.stream().filter(f -> StringUtils.isNotEmpty(f.getConType())).collect(Collectors.toList());
            List<ConTypeStatisticsVO> finalTypes = types;
            datas.forEach(d -> {
                if (!CollectionUtils.isEmpty(finalTypes)) {
                    List<ConTypeStatisticsVO> type = finalTypes.stream().filter(f -> f.getPId().equals(d.getpId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(type)) {
                        List<String> conTypes = type.stream().map(ConTypeStatisticsVO::getConType).distinct().collect(Collectors.toList());
                        List<ConTypeStatisticsVO> ctv = new ArrayList<>();
                        conTypes.forEach(t -> {
                            ConTypeStatisticsVO ct = new ConTypeStatisticsVO();
                            ct.setConType(t);
                            ct.setTotal(type.stream().filter(f -> f.getConType().equals(t)).collect(Collectors.toList()).size());
                            ctv.add(ct);
                        });
                        d.setConTypeStatistics(JacksonUtils.parseArray(JacksonUtils.toJSONString(ctv), Map.class));
                    } else d.setConTypeStatistics(new ArrayList<>());
                } else d.setConTypeStatistics(new ArrayList<>());
            });
            if (request.isStatisticsConNum()) {
                List<PreProjectResponse> cons = baseMapper.getProjectListConSum(params, projectIds);
                datas.forEach(d -> {
                    List<PreProjectResponse> con = cons.stream().filter(f -> f.getpId().equals(d.getpId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(con)) {
                        PreProjectResponse c = con.stream().findFirst().get();
                        d.setConMoneyTotalSum(c.getConMoneyTotalSum());
                        d.setConNumTotal(c.getConNumTotal());
                    }
                });
            }
        }
        List<PreProjectWhenGetAllResponse> models = baseMapper.getAllPreProject(Boolean.FALSE, authSql, new QueryMap<>(request).getQuery());
        if (datas != null && datas.size() > 0) {
            List<String> pIds = new ArrayList<>();
            datas.forEach(data -> pIds.add(data.getpId()));
            List<ProConstructionAreaDtlModel> areaDtlModels = proConstructionAreaDtlService.getProConstructionAreaDtlByPids(pIds);
            List<ProSourcesFundsModel> sourcesfunds = new ArrayList<>();
            List<ProSourcesFundsModel> funds = proSourcesFundsService.getProSourcesFundsByPids(pIds);
            List<ProHouseDtlModel> houseDtlModels = proHouseDtlService.getProHouseDtlsByPids(pIds);
            List<PreProjectResponse> newData = new ArrayList<>();
            if (unit.equals("wy")) {
                for (ProSourcesFundsModel fund : funds) {
                    fund.setFsMoney(MathUtil.DecimalDivisionInt(fund.getFsMoney(), 10000));
                    sourcesfunds.add(fund);
                }
            } else
                sourcesfunds = funds;
            for (PreProjectResponse data : datas) {
                if (data != null && StrUtil.isNotEmpty(data.getpId())) {
                    List<PreProjectWhenGetAllResponse> child = models.stream().filter(n -> data.getpId().equals(n.getpParentId())).collect(Collectors.toList());
                    data.setHavingChild(!CollectionUtils.isEmpty(child));
                    data.setProConstructionAreaDtls(areaDtlModels.stream().filter(n -> data.getpId().equals(n.getpId())).collect(Collectors.toList()));
                    data.setProSourcesFunds(sourcesfunds.stream().filter(n -> data.getpId().equals(n.getpId())).collect(Collectors.toList()));
                    data.setProHouseDtls(houseDtlModels.stream().filter(n -> data.getpId().equals(n.getpId())).collect(Collectors.toList()));
                    newData.add(data);
                }

            }
            datas = newData;
        }
        return datas;
    }

    private BaseResponse getProjectByPid(PreProjectRequest request, String unit) {
        PreProjectResponse pojo;
        if (ValidateUtil.isEmpty(request.getpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        else {
            pojo = baseMapper.getPreProjectById(request.getpId());
            if (ValidateUtil.isNull(pojo))
                return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<ProConstructionAreaDtlModel> areaDtlModels = proConstructionAreaDtlService.getProConstructionAreaDtlByPids(Collections.singletonList(request.getpId()));
        List<ProSourcesFundsModel> sourcesfunds = proSourcesFundsService.getProSourcesFundsByPids(Collections.singletonList(request.getpId()));
        List<ProSourcesFundsModel> proInvDetails = proInvDetailService.getProSourcesFundsByPids(Collections.singletonList(request.getpId()));
        List<ProHouseDtlModel> houseDtlModels = proHouseDtlService.getProHouseDtlsByPids(Collections.singletonList(request.getpId()));
        List<ProFundNoVo> proFundNos = proFundNoService.getProFundNoByPid(request.getpId());
        pojo.setProFundNos(proFundNos);
        pojo.setProConstructionAreaDtls(areaDtlModels);
        pojo.setProHouseDtls(houseDtlModels);
        pojo.setProInvDetails(proInvDetails);
        if (unit.equals("wy")) {
            List<ProSourcesFundsModel> newFds = new ArrayList<>();
            sourcesfunds.forEach(fund -> {
                fund.setFsMoney(MathUtil.DecimalDivisionInt(fund.getFsMoney(), 10000));
                newFds.add(fund);
            });
            pojo.setProSourcesFunds(newFds);
        } else
            pojo.setProSourcesFunds(sourcesfunds);

        List<PreProjectDtlModel> child = preProjectDtlService.getPreProjectDtlByPrId(pojo.getpId(), request.getpId());
        pojo.setPreProjectDtl(child);
        PreProject preProjects = getById(request.getpId());
        String projectManger = preProjects.getProjectManger();
        String projectLeader = preProjects.getProjectLeader();
        if (ValidateUtil.isNotEmpty(projectManger)) {
            pojo.setProjectManger(projectManger);
            SysUser userInfo = sysUserService.getById(projectManger);
            pojo.setProjectMangerName(userInfo.getUName());
        }
        if (ValidateUtil.isNotEmpty(projectLeader)) {
            pojo.setProjectLeader(projectLeader);
            SysUser userInfo = sysUserService.getById(projectLeader);
            pojo.setProjectLeaderName(userInfo.getUName());
        }

        List<PpOwnerUnitDTO> ppOwnerUnits = ppOwnerUnitService.getPpOwnerUnitByPId(request.getpId());
        pojo.setPpOwnerUnits(ppOwnerUnits);

        return BaseResponse.ok(pojo);
    }

    private BaseResponse getProjectByPidCopy(PreProjectRequest request, String unit) {
        PreProjectResponse pojo;
        if (ValidateUtil.isEmpty(request.getpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        else {
            pojo = baseMapper.getPreProjectById(request.getpId());
            if (ValidateUtil.isNull(pojo))
                return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<ProConstructionAreaDtlModel> areaDtlModels = proConstructionAreaDtlService.getProConstructionAreaDtlByPids(Collections.singletonList(request.getpId()));
        List<ProSourcesFundsModel> sourcesfunds = proSourcesFundsService.getProSourcesFundsByPids(Collections.singletonList(request.getpId()));
        List<ProSourcesFundsModel> proInvDetails = proInvDetailService.getProSourcesFundsByPids(Collections.singletonList(request.getpId()));
        List<ProHouseDtlModel> houseDtlModels = proHouseDtlService.getProHouseDtlsByPids(Collections.singletonList(request.getpId()));
        List<ProFundNoVo> proFundNos = proFundNoService.getProFundNoByPid(request.getpId());
        pojo.setProFundNos(proFundNos);
        pojo.setProConstructionAreaDtls(areaDtlModels);
        pojo.setProHouseDtls(houseDtlModels);
        pojo.setProInvDetails(proInvDetails);
        if (unit.equals("wy")) {
            List<ProSourcesFundsModel> newFds = new ArrayList<>();
            sourcesfunds.forEach(fund -> {
                fund.setFsMoney(MathUtil.DecimalDivisionInt(fund.getFsMoney(), 10000));
                newFds.add(fund);
            });
            pojo.setProSourcesFunds(newFds);
        } else
            pojo.setProSourcesFunds(sourcesfunds);

        List<PreProjectDtlModel> child = preProjectDtlService.getPreProjectDtlByPrId(pojo.getpId(), request.getpId());
        pojo.setPreProjectDtl(child);
        PreProject preProjects = getById(request.getpId());
        String projectManger = preProjects.getProjectManger();
        String projectLeader = preProjects.getProjectLeader();
        if (ValidateUtil.isNotEmpty(projectManger)) {
            pojo.setProjectManger(projectManger);
            SysUser userInfo = sysUserService.getById(projectManger);
            pojo.setProjectMangerName(userInfo.getUName());
        }
        if (ValidateUtil.isNotEmpty(projectLeader)) {
            pojo.setProjectLeader(projectLeader);
            SysUser userInfo = sysUserService.getById(projectLeader);
            pojo.setProjectLeaderName(userInfo.getUName());
        }

        List<PpOwnerUnitDTO> ppOwnerUnits = ppOwnerUnitService.getPpOwnerUnitByPId(request.getpId());
        pojo.setPpOwnerUnits(ppOwnerUnits);

        return BaseResponse.ok(pojo);
    }


    private String getAuthSql(Integer OutUserAuthEnum, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum, "State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        String code = "projectDateLimit";
        String str = sysConfigService.getConfigByCode(code);
        if (StringUtils.isNotEmpty(str)) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return authSql;
    }


    /**
     * getProjectList,getPreProjectAmtSum
     * 组装筛选条件
     */
    private Map<String, Object> listFilter(PreProjectRequest request, String authSql) {
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pProjectAbbreviation").put("pOriginalName").put("pNameInitials").put("conType").put("pId")
                .put("pUseUnit").put("pAddress").put("pIntroduction").put("pProjectDirector").put("pProjectStage").put("pApplyName")
                .put("pConstructionUnit").put("pConstructionUnitName").put("pType").put("pProjectCode").put("projectName").put("pName")
                .put("pCompetentDepartmentName").put("pDepartmentName").put("pCompetentDepartment").put("pCode").put("filterParentCollection").put("pCreateUserName")
                .put("pAssignmentNo").put("pParentId").put("pIsProjectApproval").put("pStatus").put("state").put("pAdvanceFund")
                .put("pSuperviseType").put("pGroupType").put("pMajorType").put("pProjectStatus").put("pNature").put("pCorporateRisk")
                .put("pOverview").put("pBuildModel").put("pImpCompany").put("pImpState").put("pApplyTilte").put("containsContract")
                .put("pApproveBudgetCode").put("pInvCaliber").put("pInvTilte").put("pInvCode").put("pApprovalUnit").put("pSafetyFiling")
                .put("constructionFilter").put("pCheckType").put("containDiscard").put("pRegion").put("statisticsConNum").put("pCategory")
                .put(request.getpConstructionAreas(), "constructionAreaStart", "constructionAreaEnd").put("conCategory")
                .put(request.getpHandoverDates(), "handoverStart", "handoverEnd").put("statisticsArchive").put("pInvCategory")
                .put(request.getpInterventionDates(), "interventionStart", "interventionEnd")
                .put(request.getpAcceptanceRecordDates(), "acceptanceRecordStart", "acceptanceRecordEnd")
                .put(request.getpActualCompleteDates(), "actualCompleteStart", "actualCompleteEnd")
                .put(request.getpApproveBudgetDates(), "approveBudgetStart", "approveBudgetEnd")
                .put(request.getpPlanCompleteDates(), "planCompleteStart", "planCompleteEnd")
                .put("pPlanStartDate").put("pPlanCompleteDate").put("pActualStartTime").put("pActualCompleteDate").put("pTotalDuration")
                .put(request.getpPlanStartDates(), "planStartStart", "planStartEnd")
                .put(request.getpFinalAccountsReportDates(), "pFinalAccountsReportStart", "pFinalAccountsReportEnd")
                .put(request.getpCreateTimes(), "createStart", "createEnd")
                .put(request.getpItemTimes(), "itemStart", "itemEnd")
                .put(request.getpReceiptTimes(), "receiptStart", "receiptEnd")
                .put(request.getpUpdateTimes(), "updateStart", "updateEnd")
                .put(request.getpActualStartTimes(), "actualStartStart", "actualStartEnd")
                .put(request.getpPreItemTimes(), "preItemStart", "preItemEnd")
                .put(request.getpLatitudes(), "latitudeStart", "latitudeEnd")
                .put(request.getpLongitudes(), "longitudeStart", "longitudeEnd")
                .put(request.getpBuildingScales(), "pBuildingScaleStart", "pBuildingScaleEnd")
                .put(request.getpInterventionYears(), "pInterventionYearStart", "pInterventionYearEnd")
                .put(request.getpInterventionEndYears(), "pInterventionEndYearStart", "pInterventionEndYearEnd")
                .put(request.getpInvTotals(), "pInvTotalStart", "pInvTotalEnd")
                .put(request.getpInvDates(), "pInvDateStart", "pInvDateEnd")
                .put(request.getpCalculateRoughlyAmounts(), "pCalculateRoughlyAmountStart", "pCalculateRoughlyAmountEnd")
                .put(request.getpEstimateAmounts(), "pEstimateAmountStart", "pEstimateAmountEnd")
                .put(request.getpApproveBudgets(), "pApproveBudgetStart", "pApproveBudgetEnd")
                .put(request.getpTotalDurations(), "pTotalDurationStart", "pTotalDurationEnd")
                .put(request.getpActualTotalDurations(), "pActualTotalDurationStart", "pActualTotalDurationEnd")
                .put(request.getpConstructionStarts(), "pConstructionStartStart", "pConstructionStartEnd")
                .put(request.getpConstructionEnds(), "pConstructionEndStart", "pConstructionEndEnd")
                .put(request.getpConstructionCosts(), "pConstructionCostStart", "pConstructionCostEnd")
                .put("pCampus").put("fsCostCode").put("pBuildingScale").put("pInterventionDate").put("projectCategoryDetails");
        return params.getQuery();
    }

    private QueryMap<PreProjectRequest> getNoAuthFilter(PreProjectRequest request) {
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        //region filterParams
        params.put("pConstructionUnit").put("pConstructionUnitName").put("pName").put("pCode").put("pCorporateRisk")
                .put("filterParentCollection").put("pAssignmentNo").put("pParentId").put("pStatus").put("pAdvanceFund")
                .put("state").put("pSuperviseType").put("pGroupType").put("pIsProjectApproval").put("pCreateUserName")
                .put("pOverview").put("pBuildModel").put("pImpCompany").put("pImpState").put("pApplyTilte").put("pApplyName")
                .put("pApproveBudgetCode").put("pInvCaliber").put("pInvTilte").put("pInvCode").put("pSafetyFiling")
                .put(request.getpBuildingScales(), "pBuildingScaleStart", "pBuildingScaleEnd")
                .put(request.getpActualCompleteDates(), "actualCompleteStart", "actualCompleteEnd")
                .put(request.getpActualStartTimes(), "actualStartStart", "actualStartEnd")
                .put(request.getpApproveBudgetDates(), "approveBudgetStart", "approveBudgetEnd")
                .put(request.getpInterventionYears(), "pInterventionYearStart", "pInterventionYearEnd")
                .put(request.getpInterventionEndYears(), "pInterventionEndYearStart", "pInterventionEndYearEnd")
                .put(request.getpInvTotals(), "pInvTotalStart", "pInvTotalEnd")
                .put(request.getpInvDates(), "pInvDateStart", "pInvDateEnd")
                .put(request.getpCalculateRoughlyAmounts(), "pCalculateRoughlyAmountStart", "pCalculateRoughlyAmountEnd")
                .put(request.getpEstimateAmounts(), "pEstimateAmountStart", "pEstimateAmountEnd")
                .put(request.getpApproveBudgets(), "pApproveBudgetStart", "pApproveBudgetEnd")
                .put(request.getpTotalDurations(), "pTotalDurationStart", "pTotalDurationEnd")
                .put(request.getpActualTotalDurations(), "pActualTotalDurationStart", "pActualTotalDurationEnd")
                .put(request.getpConstructionStarts(), "pConstructionStartStart", "pConstructionStartEnd")
                .put(request.getpConstructionEnds(), "pConstructionEndStart", "pConstructionEndEnd")
                .put("pInterventionYear").put("pType");
        //endregion
        return params;
    }

    @Override
    public BaseResponse queryDockptPreList(String dId, String pName, Integer page, Integer size) {
        if (ValidateUtil.isEmpty(dId)) return BaseResponse.fail("缺少必填参数");
        List<SysUserVo> voList = baseMapper.queryDockptPreList(Arrays.asList(dId.split(",")));
        if (CollectionUtils.isEmpty(voList)) {
            return BaseResponse.ok();
        }

        List<String> pdIds = voList.stream().filter(e -> ValidateUtil.isNotNull(e.getPdId())).map(SysUserVo::getPdId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pdIds)) return BaseResponse.ok();
        System.out.println(pdIds);
        List<PreProjectResponse> pList = baseMapper.getSYSDepartmentPList(pdIds);
        if (CollectionUtils.isEmpty(pList)) return BaseResponse.ok();
        System.out.println(JSONUtil.formatJsonStr(JSONUtil.toJsonPrettyStr(pList)));
        pList = pList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> collect = pList.stream().filter(e -> StrUtil.isNotEmpty(e.getpId())).map(PreProjectResponse::getpId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) return BaseResponse.ok();

        LambdaQueryWrapper<PreProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PreProject::getpId, collect);
        if (ValidateUtil.isNotEmpty(pName)) {
            queryWrapper.like(PreProject::getpName, pName);
        }
        Page<PreProject> preProjectPage = baseMapper.selectPage(new Page<>(page, size), queryWrapper);


        return BaseResponse.ok(preProjectPage.getRecords(), (int) preProjectPage.getTotal());
    }

    @Override
    public BaseResponse getProjectCjGsGsFenJie(String pid) {
        return BaseResponse.ok(baseMapper.getProjectCjGsGsFenJie(pid));
    }


    @Override
    public BaseResponse getProjecListNJCJ(PreProjectTZRequest request, String token) {
        LogUserInfoVo userInfoByToken = redisUtils.getUserInfoByToken(token);
        String sql = DataAuthSqlUtil.getAuthSqlOnlyCheckUserCJ(userInfoByToken.getId(), "P_Create_User_ID", "P_ID", userInfoByToken.getCId(), "State", true);
        sql = sysUserService.showAllDataAuth(userInfoByToken.getId(), "P_Create_User_ID", sql);
        return getProjecListNJCJ(request, userInfoByToken.getId(), sql);
    }

    @Override
    public BaseResponse getProjecListNJCJAuth(PreProjectTZRequest request, String uid) {
        String authSql = sysUserService.getAuthSql(null, uid, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex(), "State");
        return getProjecListNJCJ(request, uid, authSql);
    }

    public BaseResponse getProjecListNJCJ(PreProjectTZRequest request, String uid, String auth) {
        QueryMap<PreProjectTZRequest> params = new QueryMap<>(request);
        List<ClassInfoAnnotation> projectClassInfoAnnotations = getClassInfoAnnotations();
        for (ClassInfoAnnotation classInfoAnnotation : projectClassInfoAnnotations) {
            params.put(classInfoAnnotation.getValue());
        }
        params.put("authSql", auth);
        params.put("state");
        params.put("sortCJDZ", "mn.P_Create_Time desc, mn.state asc");
        params.put("pStatus").put("pProjectStageOrganization").put("pProjectStageOrganizations").put("noReserves");
        List<PreProjectResponse> datas = baseMapper.getProjectTZList(params.getQuery());
        Integer total = baseMapper.getProjectTZListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getPreprojectStaList(PreProjectApplicationStaRequest request, String uId) {
        if (!CollectionUtils.isEmpty(request.getRangeTimes())) {
            if (request.getRangeTimes() != null && request.getRangeTimes().size() > 0) {
                if (request.getRangeTimes().get(0) != null && !request.getRangeTimes().get(0).equals("")) {
                    request.setRangeTimeStart(request.getRangeTimes().get(0));
                }
                if (request.getRangeTimes().get(1) != null && !request.getRangeTimes().get(1).equals("")) {
                    request.setRangeTimeEnd(request.getRangeTimes().get(1));
                }
            }
        } else {
            getDateTIme(request, request.getRange());
        }
        IPage<PreProjectApplicationStaResponse> dataList = baseMapper.getPreprojectStaList(new Page<>(request.getPage(), request.getSize()), request);

        return BaseResponse.ok(dataList.getRecords(), (int) dataList.getTotal());
    }

    @Override
    public BaseResponse getPreprojectStaByPId(PreProjectApplicationStaRequest request) {
        if (!CollectionUtils.isEmpty(request.getRangeTimes())) {
            if (request.getRangeTimes() != null && request.getRangeTimes().size() > 0) {
                if (request.getRangeTimes().get(0) != null && !request.getRangeTimes().get(0).equals("")) {
                    request.setRangeTimeStart(request.getRangeTimes().get(0));
                }
                if (request.getRangeTimes().get(1) != null && !request.getRangeTimes().get(1).equals("")) {
                    request.setRangeTimeEnd(request.getRangeTimes().get(1));
                }
            }
        } else {
            getDateTIme(request, request.getRange());
        }
        IPage<PreProjectApplicationStaResponse> dataList = baseMapper.getPreprojectStaByPId(new Page<>(request.getPage(), request.getSize()), request);
        return BaseResponse.ok(dataList.getRecords(), (int) dataList.getTotal());
    }

    @Override
    public BaseResponse getApplicationStaList(PreProjectApplicationStaRequest request, String uid) {
        if (!CollectionUtils.isEmpty(request.getRangeTimes())) {
            if (request.getRangeTimes() != null && request.getRangeTimes().size() > 0) {
                if (request.getRangeTimes().get(0) != null && !request.getRangeTimes().get(0).equals("")) {
                    request.setRangeTimeStart(request.getRangeTimes().get(0));
                }
                if (request.getRangeTimes().get(1) != null && !request.getRangeTimes().get(1).equals("")) {
                    request.setRangeTimeEnd(request.getRangeTimes().get(1));
                }
            }
        } else {
            getDateTIme(request, request.getRange());
        }
        IPage<PreProjectApplicationStaResponse> dataList = baseMapper.getApplicationStaList(new Page<>(request.getPage(), request.getSize()), request);

        return BaseResponse.ok(dataList.getRecords(), (int) dataList.getTotal());
    }

    @Override
    public BaseResponse getApplicationStaByPId(PreProjectApplicationStaRequest request) {
        if (!CollectionUtils.isEmpty(request.getRangeTimes())) {
            if (request.getRangeTimes() != null && request.getRangeTimes().size() > 0) {
                if (request.getRangeTimes().get(0) != null && !request.getRangeTimes().get(0).equals("")) {
                    request.setRangeTimeStart(request.getRangeTimes().get(0));
                }
                if (request.getRangeTimes().get(1) != null && !request.getRangeTimes().get(1).equals("")) {
                    request.setRangeTimeEnd(request.getRangeTimes().get(1));
                }
            }
        } else {
            getDateTIme(request, request.getRange());
        }
        IPage<PreProjectApplicationStaResponse> dataList = baseMapper.getApplicationStaByPId(new Page<>(request.getPage(), request.getSize()), request);

        return BaseResponse.ok(dataList.getRecords(), (int) dataList.getTotal());
    }

    @Override
    public BaseResponse getProjecListGDGY(PreProjectRequest request) {
        request.setPage(request.getPage() <= 0 ? 1 : request.getPage());
        request.setSize(request.getSize() <= 0 ? 10 : request.getSize());
        LambdaQueryWrapper<PreProject> queryWrapper = new LambdaQueryWrapper<>();
        if (request != null) {
            if (StringUtils.isNotBlank(request.getpName())) {
                queryWrapper.like(PreProject::getpName, request.getpName());
            }
            if (StringUtils.isNotBlank(request.getpType())) {
                queryWrapper.eq(PreProject::getpType, request.getpType());
            }
            if (StringUtils.isNotBlank(request.getpCampus())) {
                queryWrapper.eq(PreProject::getpCampus, request.getpCampus());
            }
            if (request.getState() != null) {
                queryWrapper.eq(PreProject::getState, request.getState());
            }
        }

        // 分页查询
        Page<PreProject> page = new Page<>(request.getPage(), request.getSize());
        IPage<PreProject> projectPage = page(page, queryWrapper);
        List<PreProject> projectList = projectPage.getRecords();

        if (CollectionUtils.isEmpty(projectList)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        // 2. 提取所有 pId，用于后续查询
        List<String> pIds = projectList.stream()
                .map(PreProject::getpId)
                .distinct()
                .collect(Collectors.toList());
        // 3. 调用 proSourcesFundsService 查询资金信息
        List<ProSourcesFunds> fundsList = proSourcesFundsService.getByProjectIds(pIds);
        Map<String, List<ProSourcesFundsModel>> fundsMap = fundsList.stream()
                .map(this::convertToModel)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(ProSourcesFundsModel::getpId));
        List<PreProjectResponse> responseList = projectList.stream().map(project -> {
            PreProjectResponse response = new PreProjectResponse();
            BeanUtils.copyProperties(project, response);
            response.setProSourcesFunds(fundsMap.getOrDefault(project.getpId(), Collections.emptyList()));
            return response;
        }).collect(Collectors.toList());
        GDGYProjectVo gdgyProjectVo = new GDGYProjectVo();
        gdgyProjectVo.setData(responseList);
        BigDecimal totalAmount = projectList.stream()
                .map(PreProject::getpCalculateRoughlyAmount)
                .filter(Objects::nonNull) // 过滤 null 值
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        gdgyProjectVo.setTotalAmount(totalAmount);
        return BaseResponse.ok(gdgyProjectVo, (int) projectPage.getTotal());
    }

    private ProSourcesFundsModel convertToModel(ProSourcesFunds entity) {
        if (entity == null) return null;
        ProSourcesFundsModel model = new ProSourcesFundsModel();
        model.setpId(entity.getPId());
        model.setFsCode(entity.getFsCode());
        model.setFsId(entity.getFsId());
        model.setFsCreateTime(entity.getFsCreateTime());
        model.setFsMoney(entity.getFsMoney());
        model.setFsCostCode(entity.getFsCostCode());
        model.setFsRemark(entity.getFsRemark());
        return model;
    }

    @Override
    public BaseResponse getProjectGDGY(PreProjectRequest request) {
        request.setPage(request.getPage() <= 0 ? 1 : request.getPage());
        request.setSize(request.getSize() <= 0 ? 10 : request.getSize());
        Page<PreProject> page = new Page<>(request.getPage(), request.getSize());
        // 调用 Mapper 查询数据
//        IPage<SecInfoResponse> projectPage = baseMapper.getProjectListGDGY(page, request);
        return null;
    }


    public static void getDateTIme(PreProjectApplicationStaRequest request, String range) {

        if (ValidateUtil.isNull(request.getRange()) || !request.getRange().equals("0")) {//为空或不为0
            // 当月
//            DateTime firstDayOfMonth = cn.hutool.core.date.DateUtil.beginOfMonth(cn.hutool.core.date.DateUtil.date());
//            DateTime lastDayOfMonth = cn.hutool.core.date.DateUtil.endOfMonth(cn.hutool.core.date.DateUtil.date());
//            request.setRangeTimeStart(cn.hutool.core.date.DateUtil.format(firstDayOfMonth, DatePattern.NORM_DATE_PATTERN));
//            request.setRangeTimeEnd(cn.hutool.core.date.DateUtil.format(lastDayOfMonth, DatePattern.NORM_DATE_PATTERN));

            //今天起到三十天前
            DateTime today = cn.hutool.core.date.DateUtil.date();
            DateTime thirtyDaysAgo = cn.hutool.core.date.DateUtil.offsetDay(today, -30);
            request.setRangeTimeStart(cn.hutool.core.date.DateUtil.format(thirtyDaysAgo, DatePattern.NORM_DATE_PATTERN));
            request.setRangeTimeEnd(cn.hutool.core.date.DateUtil.format(today, DatePattern.NORM_DATE_PATTERN));
        } else {  //当前周
            DateTime firstDayOfMonth = cn.hutool.core.date.DateUtil.beginOfWeek(cn.hutool.core.date.DateUtil.date());
            DateTime lastDayOfMonth = cn.hutool.core.date.DateUtil.endOfWeek(cn.hutool.core.date.DateUtil.date());
            request.setRangeTimeStart(cn.hutool.core.date.DateUtil.format(firstDayOfMonth, DatePattern.NORM_DATE_PATTERN));
            request.setRangeTimeEnd(cn.hutool.core.date.DateUtil.format(lastDayOfMonth, DatePattern.NORM_DATE_PATTERN));
        }

    }

    @Override
    public BaseResponse getPIdByPprId(String pprId) {
        if (ValidateUtil.isEmpty(pprId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        PreProject preProject = lambdaQuery().eq(PreProject::getPprId, pprId).one();
        if (ValidateUtil.isNull(preProject)) {
            return BaseResponse.ok();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pId", preProject.getpId());
        map.put("state", preProject.getState());
        return BaseResponse.ok(map);
    }

    @Override
    public void updateProjectPersonnel(String pId, String rpCreateUid) {
        if (ValidateUtil.isEmpty(pId)) {
            return;
        }
        PreProject project = getById(pId);
        if (ValidateUtil.isNull(project)) {
            return;
        }
        // PreProjectReserve projectReserve = ValidateUtil.isNotEmpty(project.getPprId())
        //         ? preProjectReserveService.getById(project.getPprId())
        //         : null;
        DepartmentPCjConfDTO conf = sysConfigService.getDepartmentPCjConf();
        if (ValidateUtil.isNull(conf)) {
            return;
        }
        List<UserPositionPDTO> positionUsers = sysPositionUserPMapper.selectPositionPUser(
                project.getpId(), conf.getJtLd(), conf.getJtJbr(), conf.getGldwXmjl(), conf.getGldwFgld(),
                conf.getXmfzoCode(), conf.getXmfgoCode(), conf.getXmfztCode(), conf.getXmfgtCode()
        );
        if (ValidateUtil.isEmpty(positionUsers)) {
            return;
        }
        boolean xtndj = "007".equalsIgnoreCase(project.getpBuildModel());
        List<PpOwnerUnit> ownerUnits = xtndj
                ? ppOwnerUnitService.list(Wrappers.<PpOwnerUnit>lambdaQuery().eq(PpOwnerUnit::getPId, pId))
                : Collections.emptyList();
        Map<String, PpOwnerUnit> ownerUnitMap = ownerUnits.stream()
                .filter(u -> ValidateUtil.isNotEmpty(u.getOwnerUnit()))
                .collect(Collectors.toMap(PpOwnerUnit::getOwnerUnit, Function.identity(), (a, b) -> a));
        for (UserPositionPDTO model : positionUsers) {
            String ppCode = model.getPpCode();
            String uid = model.getUid();
            if (Objects.equals(ppCode, conf.getJtLd())) {
                // 工程部分管部长
                project.setpEngineerLeader(uid);
                // if (ValidateUtil.isNotNull(projectReserve)) {
                //     projectReserve.setPprEngineerLeader(uid);
                // }
            } else if (Objects.equals(ppCode, conf.getJtJbr())) {
                // 工程部经办人
                project.setpEngineer(uid);
                // if (ValidateUtil.isNotNull(projectReserve)) {
                //     projectReserve.setPprEngineer(uid);
                // }
            } else if (Objects.equals(ppCode, conf.getGldwXmjl())) {
                // 项目经理
                project.setpProjectManager(uid);
                // if (ValidateUtil.isNotNull(projectReserve)) {
                //     projectReserve.setPprProjectManager(uid);
                // }
            } else if (Objects.equals(ppCode, conf.getGldwFgld())) {
                // 项目分管领导
                project.setpProjectLeader(uid);
                // if (ValidateUtil.isNotNull(projectReserve)) {
                //     projectReserve.setPprProjectLeader(uid);
                // }
            }
            // 业主单位处理
            if (xtndj) {
                String unit = model.getOwnerUnit();
                if (ValidateUtil.isEmpty(unit)) {
                    continue;
                }
                PpOwnerUnit ownerUnit = ownerUnitMap.get(unit);
                if (ValidateUtil.isNull(ownerUnit)) {
                    continue;
                }
                if (Objects.equals(ppCode, conf.getXmfzoCode()) || Objects.equals(ppCode, conf.getXmfztCode())) {
                    // 项目负责人
                    ownerUnit.setProjectManger(uid);
                } else if (Objects.equals(ppCode, conf.getXmfgoCode()) || Objects.equals(ppCode, conf.getXmfgtCode())) {
                    // 项目分管领导
                    ownerUnit.setProjectLeader(uid);
                }
            }
        }
        project.setpUpdateTime(new Date());
        project.setpUpdateUserId(rpCreateUid);
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        project.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), project));
        updateById(project);
        // 更新业主单位信息
        if (ValidateUtil.isNotEmpty(ownerUnits)) {
            ppOwnerUnitService.updateBatchById(ownerUnits);
        }
        // if (ValidateUtil.isNotNull(projectReserve)) {
        //     preProjectReserveService.updateById(projectReserve);
        // }
    }

    @Override
    public List<PreProjectResponse> getProjectListByPCompetentDepartmentName(Set<String> pIds) {
        return baseMapper.getProjectListByPCompetentDepartmentName(pIds);
    }

    @Override
    public Boolean existProjectName(String pName, String pId) {
        LambdaQueryWrapper<PreProject> query = Wrappers.lambdaQuery();
        query.eq(PreProject::getpName, pName);
        query.ne(PreProject::getState, ApprovalStatusEnum.Invalid.getIndex());
        if (ValidateUtil.isNotEmpty(pId)) {
            query.ne(PreProject::getpId, pId);
        }
        return baseMapper.exists(query);
    }

    @Override
    public BaseResponse getProjectContractChangeList(PreProjectRequest request, String uid) {

        QueryMap<PreProjectRequest> query = new QueryMap<>(request);
        query.put("pName").put("pCode");
        IPage<PreProjectResponse> page = new Page<>(request.getPage(), request.getSize());

        List<PreProjectResponse> projectList = baseMapper.getProjectContractChangeList(query.getQuery(), page);

        return BaseResponse.ok(projectList, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProjectContractChangeListBypId(ConEcAndVisFeeModel request, String uId) {

        QueryMap<ConEcAndVisFeeModel> query = new QueryMap<>(request);

        query.put("pId").put("pName").put("conId").put("type")
                .put("approvalId").put("approvedFlag").put("budgetAmount")
                .put("pConName").put("dataSeparateType");

        IPage<ConEcAndVisFeeModel> page = new Page<>(request.getPage(), request.getSize());

        List<ConEcAndVisFeeModel> list = baseMapper.getProjectContractChangeListBypId(query.getQuery(), page);

        BigDecimal approvedTotal = BigDecimal.ZERO;
        BigDecimal unapprovedTotal = BigDecimal.ZERO;

        for (ConEcAndVisFeeModel model : list) {
            if (model.getState() != null && model.getState() == 7) {
                model.setApprovedFlag("01");
                approvedTotal = approvedTotal.add(model.getBudgetAmount() == null ? BigDecimal.ZERO : model.getBudgetAmount());
            } else {
                model.setApprovedFlag("02");
                unapprovedTotal = unapprovedTotal.add(model.getBudgetAmount() == null ? BigDecimal.ZERO : model.getBudgetAmount());
            }
        }

        BigDecimal total = approvedTotal.add(unapprovedTotal);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("approvedTotal", approvedTotal);
        result.put("unapprovedTotal", unapprovedTotal);
        result.put("total", total);

        return BaseResponse.ok(result, (int) page.getTotal());
    }


    @Override
    public BaseResponse getProjectDivision(String province, String city) {
        return BaseResponse.ok(baseMapper.getProjectDivision(ValidateUtil.isEmpty(province) ? null : province,
                ValidateUtil.isEmpty(city) ? null : city));
    }

    @Override
    public BaseResponse getProjectHHTZList(PreProjectRequest request, String uid) {

        List<PreProject> preProjectList = this.lambdaQuery()
                .select(PreProject::getpId, PreProject::getpName, PreProject::getpInterventionYear, PreProject::getpType)
                .like(StringUtils.isNotBlank(request.getpName()), PreProject::getpName, request.getpName())
                .eq(StringUtils.isNotBlank(request.getpType()), PreProject::getpType, request.getpType())
                .isNotNull(PreProject::getpInterventionYear).isNotNull(PreProject::getpType)
                .list();

        if (preProjectList == null || preProjectList.isEmpty()) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }

        Map<Integer, Map<String, Integer>> yearStatsMap = new TreeMap<>();

        for (PreProject p : preProjectList) {
            if (p == null) continue;

            Integer yearKey = (p.getpInterventionYear() == null) ? -1 : p.getpInterventionYear();
            String typeVal = (StringUtils.isBlank(p.getpType())) ? "未知类型" : p.getpType();

            Map<String, Integer> typeCounts = yearStatsMap.computeIfAbsent(yearKey, k -> new HashMap<>());

            typeCounts.put(typeVal, typeCounts.getOrDefault(typeVal, 0) + 1);
        }

        List<Map<String, Object>> finalResult = new ArrayList<>();

        Comparator<String> typeComparator = (s1, s2) -> {
            if ("未知类型".equals(s1)) return -1;
            if ("未知类型".equals(s2)) return 1;
            return s1.compareTo(s2);
        };

        for (Map.Entry<Integer, Map<String, Integer>> entry : yearStatsMap.entrySet()) {
            Map<String, Object> yearData = new HashMap<>(6);
            Integer year = entry.getKey();
            Map<String, Integer> typeMap = entry.getValue();


            int yearTotalCount = typeMap.values().stream().mapToInt(Integer::intValue).sum();

            // 构建 type 列表：[{type: "01", count: 10}]
            List<String> sortedTypes = new ArrayList<>(typeMap.keySet());
            sortedTypes.sort(typeComparator);

            List<Map<String, Object>> typeDetailList = new ArrayList<>();
            for (String typeName : sortedTypes) {
                Map<String, Object> typeDetail = new HashMap<>(2);
                typeDetail.put("type", typeName);
                typeDetail.put("count", typeMap.get(typeName));
                typeDetailList.add(typeDetail);
            }

            // 组装单条年份数据
            yearData.put("year", year == -1 ? null : year);
            yearData.put("count", yearTotalCount);
            yearData.put("type", typeDetailList);

            finalResult.add(yearData);
        }

        return BaseResponse.ok(finalResult, finalResult.size());
    }

    @Override
    public BaseResponse getProjectFundStatisticsTree(ProjectFundStatisticsDTO request, String uId) {
        QueryMap<ProjectFundStatisticsDTO> queryMap = new QueryMap<>(request);
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        queryMap.put("authSql", authSql).put("pName").put("pId").put("pParentId").put("pCode").put("year").put("parentProject")
                .put("pUseUnit").put("pConstructionUnitName").put("pProjectStage").put("pProjectStatus");
        IPage<ProjectFundStatisticsVO> page = new Page<>(1, Integer.MAX_VALUE);
        List<ProjectFundStatisticsVO> data = baseMapper.getProjectFundStatistics(page, queryMap.getQuery());
        Map<String, ProjectFundStatisticsVO> idToNodeMap = data.stream()
                .collect(Collectors.toMap(ProjectFundStatisticsVO::getPId, node -> node));
        List<ProjectFundStatisticsVO> rootNodes = new ArrayList<>();
        for (ProjectFundStatisticsVO node : data) {
            String parentId = node.getPParentId();
            if (StrUtil.isBlank(parentId) || "0".equals(parentId) || !idToNodeMap.containsKey(parentId)) {
                rootNodes.add(node);
            } else {
                ProjectFundStatisticsVO parentNode = idToNodeMap.get(parentId);
                if (parentNode.getChildren() == null) {
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(node);
            }
        }
        rootNodes.forEach(this::calculateSumRecursively);

        ProjectFundStatisticsSumVO sum;
        if (CollectionUtils.isEmpty(data)) {
            sum = new ProjectFundStatisticsSumVO();
        } else {
            sum = baseMapper.getProjectFundStatisticsSum(queryMap.getQuery());
            if (sum == null) {
                sum = new ProjectFundStatisticsSumVO();
            }
        }
        sum.setData(rootNodes);
        return BaseResponse.ok(sum, Math.toIntExact(page.getTotal()));
    }

    private void calculateSumRecursively(ProjectFundStatisticsVO node) {
        List<ProjectFundStatisticsVO> children = node.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        BigDecimal pCalculateRoughlyAmount = ValidateUtil.isNull(node.getPCalculateRoughlyAmount()) ? BigDecimal.ZERO : node.getPCalculateRoughlyAmount();
        BigDecimal pEstimateAmount = ValidateUtil.isNull(node.getPEstimateAmount()) ? BigDecimal.ZERO : node.getPEstimateAmount();
        BigDecimal pApproveBudget = ValidateUtil.isNull(node.getPApproveBudget()) ? BigDecimal.ZERO : node.getPApproveBudget();
        BigDecimal secMoneyTotal = ValidateUtil.isNull(node.getSecMoneyTotal()) ? BigDecimal.ZERO : node.getSecMoneyTotal();
        BigDecimal pFinalAccounts = ValidateUtil.isNull(node.getPFinalAccounts()) ? BigDecimal.ZERO : node.getPFinalAccounts();
        BigDecimal conMoneyTotal = ValidateUtil.isNull(node.getConMoneyTotal()) ? BigDecimal.ZERO : node.getConMoneyTotal();
        BigDecimal conPayApprovalTotal = ValidateUtil.isNull(node.getConPayApprovalTotal()) ? BigDecimal.ZERO : node.getConPayApprovalTotal();
        BigDecimal conPayYearTotal = ValidateUtil.isNull(node.getConPayYearTotal()) ? BigDecimal.ZERO : node.getConPayYearTotal();
        BigDecimal unPayAmtTotal = ValidateUtil.isNull(node.getUnPayAmtTotal()) ? BigDecimal.ZERO : node.getUnPayAmtTotal();
        BigDecimal saApprovedAmtTotal = ValidateUtil.isNull(node.getSaApprovedAmtTotal()) ? BigDecimal.ZERO : node.getSaApprovedAmtTotal();
        BigDecimal proPayApprovalTotal = ValidateUtil.isNull(node.getProPayApprovalTotal()) ? BigDecimal.ZERO : node.getProPayApprovalTotal();
        for (ProjectFundStatisticsVO child : children) {
            // 递归调用
            calculateSumRecursively(child);
            // 累加子节点的值
            pCalculateRoughlyAmount = pCalculateRoughlyAmount.add(ValidateUtil.isNull(child.getPCalculateRoughlyAmount()) ? BigDecimal.ZERO : child.getPCalculateRoughlyAmount());
            pEstimateAmount = pEstimateAmount.add(ValidateUtil.isNull(child.getPEstimateAmount()) ? BigDecimal.ZERO : child.getPEstimateAmount());
            pApproveBudget = pApproveBudget.add(ValidateUtil.isNull(child.getPApproveBudget()) ? BigDecimal.ZERO : child.getPApproveBudget());
            secMoneyTotal = secMoneyTotal.add(ValidateUtil.isNull(child.getSecMoneyTotal()) ? BigDecimal.ZERO : child.getSecMoneyTotal());
            pFinalAccounts = pFinalAccounts.add(ValidateUtil.isNull(child.getPFinalAccounts()) ? BigDecimal.ZERO : child.getPFinalAccounts());
            conMoneyTotal = conMoneyTotal.add(ValidateUtil.isNull(child.getConMoneyTotal()) ? BigDecimal.ZERO : child.getConMoneyTotal());
            conPayApprovalTotal = conPayApprovalTotal.add(ValidateUtil.isNull(child.getConPayApprovalTotal()) ? BigDecimal.ZERO : child.getConPayApprovalTotal());
            conPayYearTotal = conPayYearTotal.add(ValidateUtil.isNull(child.getConPayYearTotal()) ? BigDecimal.ZERO : child.getConPayYearTotal());
            unPayAmtTotal = unPayAmtTotal.add(ValidateUtil.isNull(child.getUnPayAmtTotal()) ? BigDecimal.ZERO : child.getUnPayAmtTotal());
            saApprovedAmtTotal = saApprovedAmtTotal.add(ValidateUtil.isNull(child.getSaApprovedAmtTotal()) ? BigDecimal.ZERO : child.getSaApprovedAmtTotal());
            proPayApprovalTotal = proPayApprovalTotal.add(ValidateUtil.isNull(child.getProPayApprovalTotal()) ? BigDecimal.ZERO : child.getProPayApprovalTotal());
        }
        // 设置回当前节点
        node.setPCalculateRoughlyAmount(pCalculateRoughlyAmount);
        node.setPEstimateAmount(pEstimateAmount);
        node.setPApproveBudget(pApproveBudget);
        node.setSecMoneyTotal(secMoneyTotal);
        node.setPFinalAccounts(pFinalAccounts);
        node.setConMoneyTotal(conMoneyTotal);
        node.setConPayApprovalTotal(conPayApprovalTotal);
        node.setConPayYearTotal(conPayYearTotal);
        node.setUnPayAmtTotal(unPayAmtTotal);
        node.setSaApprovedAmtTotal(saApprovedAmtTotal);
        node.setProPayApprovalTotal(proPayApprovalTotal);
    }

    @Override
    public BaseResponse refreshProjectCode(String pId, String oldPBuildModel) {
        if (ValidateUtil.isAnObjEmpty(pId, oldPBuildModel)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        PreProject project = getById(pId);
        if (ValidateUtil.isNull(project)) {
            return BaseResponse.fail(ResMsg.noData);
        }
        // 储备项目的编码不用刷新
        if (project.getpCode().startsWith("CB-")) {
            return BaseResponse.ok();
        }
        // 建设模式相同，不刷新
        if (project.getpBuildModel().equalsIgnoreCase(oldPBuildModel)) {
            return BaseResponse.ok();
        }
        String codeRule = "Project";
        if (apiUtilsService.sequenceRestEnable(codeRule)) {
            String code = apiUtilsService.generateCodeByCustomized(codeRule, project.getpId(), null, null, project);
            if (ValidateUtil.isEmpty(code)) {
                return BaseResponse.fail("编号生成失败");
            }

            QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Code", code);
            if (!ValidateUtil.isEmpty(pId)) {
                wrapper.ne("P_Id", pId);
            }
            wrapper.eq("deleted", Boolean.FALSE);
            if (this.count(wrapper) > 0) {
                return BaseResponse.fail(ResMsg.codeIsRepeat);
            }

            apiUtilsService.refershAutoNoByCustomized(codeRule, project.getpId(), null, null, project);
            project.setpCode(code);
            this.updateById(project);
        }
        return BaseResponse.ok();
    }

    /**
     * 获取项目进度
     *
     * @param request 项目分页查询条件
     * @param uId 当前用户 ID
     * @return 带合同金额、支付占比和最新全景文件的项目分页结果
     */
    @Override
    public BaseResponse getProjectProgress(PreProjectRequest request, String uId) {
        String conAuthSql = sysUserService.onlyCreateorAndApproverSQL(uId, "conf.Con_Create_User_Id", "conf.Con_Id", "Con_Approval_State");
        BaseResponse response = getProjectList(request, uId, "gw", conAuthSql);
        List<PreProjectResponse> projects = (List<PreProjectResponse>) response.getData();
        if (CollectionUtils.isEmpty(projects)) {
            return response;
        }
        List<String> projectIds = projects.stream().map(PreProjectResponse::getpId).collect(Collectors.toList());
        Map<String, PreProjectResponse> amounts = baseMapper.getProjectProgressAmounts(conAuthSql, projectIds).stream()
                .collect(Collectors.toMap(PreProjectResponse::getpId, item -> item));
        Map<String, String> panoramaFiles = baseMapper.getLatestPanoramaFiles(projectIds).stream()
                .filter(item -> item.getPpoFile() != null)
                .collect(Collectors.toMap(PreProjectResponse::getpId, PreProjectResponse::getPpoFile));
        projects.forEach(project -> {
            project.setPpoFile(panoramaFiles.get(project.getpId()));
            PreProjectResponse amount = amounts.get(project.getpId());
            if (amount == null) {
                project.setConMoneyTotalSum(BigDecimal.ZERO);
                project.setConPayAmount(BigDecimal.ZERO);
                project.setConPayRatio(BigDecimal.ZERO);
                return;
            }
            BigDecimal contractTotal = amount.getConMoneyTotalSum();
            BigDecimal paidTotal = amount.getConPayAmount();
            project.setConMoneyTotalSum(contractTotal);
            project.setConPayAmount(paidTotal);
            project.setConPayRatio(contractTotal == null || contractTotal.signum() == 0
                    ? BigDecimal.ZERO
                    : paidTotal.multiply(new BigDecimal("100")).divide(contractTotal, 2, java.math.RoundingMode.HALF_UP));
        });
        return response;
    }

    @Override
    public PreProjectResponse selectById(String pId) {
        if (ValidateUtil.isEmpty(pId)) {
            return null;
        }
        return baseMapper.getPreProjectById(pId);
    }
}
