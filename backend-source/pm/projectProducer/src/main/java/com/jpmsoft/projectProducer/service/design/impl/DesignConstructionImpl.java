package com.jpmsoft.projectProducer.service.design.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Design.DesignConstructionRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;
import com.jpmsoft.common.model.response.Design.DesignConstructionResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.DockFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignConstructionMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConstruction;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataFileRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.ThirdUserRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.WorkFlowRequest;
import com.jpmsoft.projectProducer.service.design.IDesignConstructionDtlService;
import com.jpmsoft.projectProducer.service.design.IDesignConstructionService;
import com.jpmsoft.projectProducer.service.design.IDesignProjectInfoService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Service
public class DesignConstructionImpl extends FileImpl<DesignConstructionMapper, DesignConstruction, DesignConstructionRequest> implements IDesignConstructionService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IDesignConstructionDtlService designConstructionDtlService;
    @Resource
    private DockFeign dockFeign;
    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private IDesignProjectInfoService designProjectInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignConstruction(DesignConstructionRequest request, String token) {
        DesignConstruction entity = new DesignConstruction();
        String id = createOrUpdateFileEntity(entity, request);
        designConstructionDtlService.createOrUpdateConstructionDtls(request.getDesignConstructionDtls(), id, token);
        DesignProjectInfoModel designProjectInfoModel = request.getDesignProjectInfoModel();
        if(null!=designProjectInfoModel){
            designProjectInfoModel.setBusinessId(id);
            designProjectInfoModel.setBusinessType(3);
            designProjectInfoModel.setpId(request.getpId());
            designProjectInfoModel.setCreateUid(request.getDcCreateUid());
            designProjectInfoModel.setCreateUname(request.getDcCreateUname());
            designProjectInfoService.createOrUpdateDesignProjectInfo(designProjectInfoModel);
        }
        return BaseResponse.ok(id);
    }

    @Override
    public BaseResponse getDesignConstructionList(DesignConstructionRequest request, String uId) {
        QueryMap<DesignConstructionRequest> params = getFilter(request);
        params.put("authSql", getSelfAuthSql(uId));
        IPage<DesignConstructionResponse> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignConstructionStatisticsList(DesignConstructionRequest request, String uId) {
        QueryMap<DesignConstructionRequest> params = getFilter(request);
        params.put("authSql", getSelfAuthSql(uId));
        IPage<DesignConstructionResponse> page = getList(request, params);
        BigDecimal dcBudgetSum = baseMapper.getDcBudgetSum(params.getQuery());
        Map<String, Object> data = new HashMap<>();
        data.put("dcBudgetSum", dcBudgetSum);
        data.put("data", page.getRecords());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignConstructionListNoAuth(DesignConstructionRequest request) {
        QueryMap<DesignConstructionRequest> params = getFilter(request);
        IPage<DesignConstructionResponse> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    private String getSelfAuthSql(String uId) {
        String authSql = sysUserService.getAuthSql("Design_Construction", uId, "Dc_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Dc_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        return authSql;
    }

    private QueryMap<DesignConstructionRequest> getFilter(DesignConstructionRequest request) {
        QueryMap<DesignConstructionRequest> params = new QueryMap<>(request);
        params.put("pId").put("dcName").put("pName").put("dcState").put("dcSunshine").put("dcSpecialty").put("dcDesignUnitName")
                .put("dcFps").put("dcGovernment").put("dcUserConfirm").put("dcCreateUname").put("secCurrentName")
                .put(request.getDcBudgets(), "dcBudgetStart", "dcBudgetEnd")
                .put(request.getDcPlanStarts(), "dcPlanStartStart", "dcPlanStartEnd")
                .put(request.getDcPlanEnds(), "dcPlanEndStart", "dcPlanEndEnd")
                .put(request.getDcCreateTimes(), "dcCreateStart", "dcCreateEnd");
        return params;
    }

    private IPage<DesignConstructionResponse> getList(DesignConstructionRequest request, QueryMap<DesignConstructionRequest> params) {
        IPage<DesignConstructionResponse> page = new Page<>(request.getPage(), request.getSize());
        List<DesignConstructionResponse> datas = baseMapper.getDesignConstructionList(page, params.getQuery());
        page.setRecords(datas);
        return page;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteDesignConstructionBydcId(String dcId, String token) {
        DesignConstruction model = baseMapper.selectById(dcId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getDcState()))
            return BaseResponse.fail(ResMsg.stateNotDel);
        designConstructionDtlService.deleteByDcId(dcId, token);
        sysFileService.deleteFileById(dcId);
        baseMapper.deleteById(dcId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getDesignConstructionBydcId(String dcId) {
        DesignConstructionResponse data = baseMapper.getDesignConstructionById(dcId);
        if (data != null) data.setDesignConstructionDtls(designConstructionDtlService.getDesConstDtlListByDcId(dcId));
        DesignProjectInfoModel designProjectInfoModel = designProjectInfoService.getDesignProjectInfoByBusinessId(data.getDcId(), 3, data.getpId());
        data.setDesignProjectInfoModel(designProjectInfoModel);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse updateDesignConstructionApproveStatus(DesignConstructionRequest request) {
        String diId = request.getDcId();
        SysFile sysFile = sysFileService.getById(diId);
        String fileRelationId = sysFile.getFileRelationId();
        String fileflowstatus = sysFile.getFileflowstatus();

        DesignConstruction designConstruction = this.getById(diId);

        if("7".equals(fileflowstatus) && null !=designConstruction.getCompanySealFlag() && designConstruction.getCompanySealFlag()){
            //查询云上建工用户信息
            CommonResponse<ThirdUserRequest> thirdUserCommonResponse = dockFeign.getThirdUser(designConstruction.getDcCreateUid());
            if (thirdUserCommonResponse.isSuccess() && thirdUserCommonResponse.getData() != null) {
                ThirdUserRequest thirdUserRequest = thirdUserCommonResponse.getData();

                designConstruction.setThirdUserId(thirdUserRequest.getUserId());
                //创建流程
                WorkFlowRequest workFlowRequest = new WorkFlowRequest();
                workFlowRequest.setRequestName(designConstruction.getDcName());
                workFlowRequest.setWorkflowId(sysConfigService.getConfigByCode("YSJG_TECHNICAL_SEAL_WFID"));
                workFlowRequest.setThirdUserId(thirdUserRequest.getUserId());
                workFlowRequest.setBizId(diId);
                workFlowRequest.setBizType("DesignInitial");

                if (StrUtil.isNotBlank(designConstruction.getThirdRequestId())){
                    workFlowRequest.setRequestId(designConstruction.getThirdRequestId());
                }

                List<MainDataRequest> mainData = new ArrayList<>();
                MainDataRequest yzlb = new MainDataRequest();
                yzlb.setFieldName("yzlb");//印章类别
                yzlb.setFieldValue(designConstruction.getSealCategory());
                mainData.add(yzlb);

                MainDataRequest sfgz = new MainDataRequest();
                sfgz.setFieldName("sfgz");//是否盖章
                sfgz.setFieldValue("0");
                mainData.add(sfgz);

                MainDataRequest zysh = new MainDataRequest();
                zysh.setFieldName("zysh");//专业审核
                zysh.setFieldValue(designConstruction.getProfessionalReview());
                mainData.add(zysh);

                MainDataRequest xmlb = new MainDataRequest();
                xmlb.setFieldName("xmlb");//项目类别
                xmlb.setFieldValue(designConstruction.getProjectCategory());
                mainData.add(xmlb);

                MainDataRequest sy = new MainDataRequest();
                sy.setFieldName("sy");//事由
                sy.setFieldValue(designConstruction.getReason());
                mainData.add(sy);

                MainDataRequest sqr = new MainDataRequest();
                sqr.setFieldName("sqr");//申请人
                sqr.setFieldValue(thirdUserRequest.getUserId());
                mainData.add(sqr);

                MainDataRequest sqbm = new MainDataRequest();
                sqbm.setFieldName("sqbm");//申请部门
                sqbm.setFieldValue(thirdUserRequest.getDepartmentId());
                mainData.add(sqbm);

                MainDataRequest sqdw = new MainDataRequest();
                sqdw.setFieldName("sqdw");//申请单位
                sqdw.setFieldValue(thirdUserRequest.getSubCompanyId());
                mainData.add(sqdw);

                MainDataRequest sqrq = new MainDataRequest();
                sqrq.setFieldName("sqrq");//申请日期
                sqrq.setFieldValue(DateFormatUtil.dateToString(new Date(), DateFormatUtil.FORMAT_PATTERN_OF_DAY));
                mainData.add(sqrq);

                //附件
                if (StrUtil.isNotBlank(fileRelationId)) {
                    String rootDirectory = sysConfigService.getConfigByCode("rootDirectory");
                    String archiveDirectory = sysConfigService.getConfigByCode("archiveDirectory");
                    LambdaQueryWrapper<SysFileannex> fileQueryWrapper = new LambdaQueryWrapper<>();
                    fileQueryWrapper.eq(SysFileannex::getFileRelationId, fileRelationId);
                    fileQueryWrapper.eq(SysFileannex::getUploadStatus, 1);
                    List<SysFileannex> fileList = sysFileAnnexService.list(fileQueryWrapper);
                    if (CollectionUtil.isNotEmpty(fileList)){
                        List<MainDataFileRequest> fileMainDatas = new ArrayList<>();
                        for (SysFileannex sysFileannex : fileList) {
                            String fileDir;
                            if (sysFileannex.getFileLocation() == 1) {
                                fileDir = archiveDirectory;
                            } else {
                                fileDir = rootDirectory;
                            }
                            MainDataFileRequest fileData = new MainDataFileRequest();
                            fileData.setFileName(sysFileannex.getFileName());
                            fileData.setFilePath("base64:" + Base64.encode(new File(fileDir + sysFileannex.getFilePath())));
                            fileMainDatas.add(fileData);
                        }

                        MainDataRequest fj = new MainDataRequest();
                        fj.setFieldName("fj");
                        fj.setFieldValue(fileMainDatas);
                        mainData.add(fj);
                    }

                }

                workFlowRequest.setMainData(mainData);
                CommonResponse<String> workFLowCreateResponse;
                if (StrUtil.isNotBlank(designConstruction.getThirdRequestId())){
                    //驳回后重新提交流程
                    workFLowCreateResponse = dockFeign.resubmitFLow(workFlowRequest);
                }else{
                    //创建流程
                    workFLowCreateResponse = dockFeign.createWorkFLow(workFlowRequest);
                }
                if (workFLowCreateResponse.isSuccess() && StrUtil.isNotBlank(workFLowCreateResponse.getData())) {
                    designConstruction.setThirdRequestId(workFLowCreateResponse.getData());
                    //默认审批中
                    designConstruction.setThirdStatus("1");
                }
                this.updateById(designConstruction);
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 下发状态切换
     *
     * @param dcId
     * @param issued
     * @return
     */
    @Override
    public BaseResponse updateDesignConstructionIssued(String dcId, String issued) {
        if (ValidateUtil.isEmpty(dcId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        DesignConstruction entity = getById(dcId);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isEmpty(issued)) {
            // 切换状态
            if ("01".equals(entity.getIssued())) {
                entity.setIssued("02");
            } else if ("02".equals(entity.getIssued())) {
                entity.setIssued("01");
            } else {
                return BaseResponse.fail(ResMsg.notReqParam);
            }
        } else {
            entity.setIssued(issued);
        }
        this.updateById(entity);
        return BaseResponse.ok();
    }
}
