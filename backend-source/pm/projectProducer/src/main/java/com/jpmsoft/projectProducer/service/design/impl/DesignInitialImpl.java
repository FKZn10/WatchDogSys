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
import com.jpmsoft.common.model.request.Design.DesignInitialRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;
import com.jpmsoft.common.model.response.Design.DesignInitialBudgetDtlResponse;
import com.jpmsoft.common.model.response.Design.DesignInitialResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.DockFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignInitialMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignInitial;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataFileRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.ThirdUserRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.WorkFlowRequest;
import com.jpmsoft.projectProducer.pojo.vo.design.DesignInitialMoneySumVo;
import com.jpmsoft.projectProducer.service.design.IDesignInitialBudgetDtlService;
import com.jpmsoft.projectProducer.service.design.IDesignInitialService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DesignInitialImpl extends FileImpl<DesignInitialMapper, DesignInitial, DesignInitialRequest> implements IDesignInitialService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private DockFeign dockFeign;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private IDesignProjectInfoService designProjectInfoService;

    @Resource
    private IDesignInitialBudgetDtlService designInitialBudgetDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignInitial(DesignInitialRequest request) {
        DesignInitial entity = new DesignInitial();
        String entityId = createOrUpdateFileEntity(entity, request);
        DesignProjectInfoModel designProjectInfoModel = request.getDesignProjectInfoModel();
        if(null!=designProjectInfoModel){
            designProjectInfoModel.setBusinessId(entityId);
            designProjectInfoModel.setBusinessType(2);
            designProjectInfoModel.setpId(request.getpId());
            designProjectInfoModel.setCreateUid(request.getDiCreateUid());
            designProjectInfoModel.setCreateUname(request.getDiCreateUname());
            designProjectInfoService.createOrUpdateDesignProjectInfo(designProjectInfoModel);
        }
        if(CollectionUtil.isNotEmpty(request.getDiBudgetDtls())){
            designInitialBudgetDtlService.createOrUpdateDesignInitialBudgetDtl(request.getDiBudgetDtls(), entityId);
        }
        return BaseResponse.ok(entityId);
    }

    @Override
    public BaseResponse getDesignInitialList(DesignInitialRequest request, String uId) {
        QueryMap<DesignInitialRequest> params = getFilter(request);
        params.put("authSql", getSelfAuthSql(uId));
        IPage<DesignInitialResponse> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignInitialStatisticsList(DesignInitialRequest request, String uId) {
        QueryMap<DesignInitialRequest> params = getFilter(request);
        params.put("authSql", getSelfAuthSql(uId));
        IPage<DesignInitialResponse> page = getList(request, params);
        DesignInitialMoneySumVo data = baseMapper.getDesignInitialMoneySum(params.getQuery());
        if (data == null) data = new DesignInitialMoneySumVo();
        data.setData(page.getRecords());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignInitialListNoAuth(DesignInitialRequest request) {
        QueryMap<DesignInitialRequest> params = getFilter(request);
        IPage<DesignInitialResponse> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }


    private QueryMap<DesignInitialRequest> getFilter(DesignInitialRequest request) {
        QueryMap<DesignInitialRequest> params = new QueryMap<>(request);
        params.put("pId").put("pName").put("diTitle").put("diDesignUnit").put("diDesignUnitName")
                .put("diState").put("diEfs").put("diDesignBrief").put("diGovernment")
                .put("diUserConfirm").put("diCreateUname").put("conPartyBPm").put("conName")
                .put(request.getDiEstimates(), "diEstimateStart", "diEstimateEnd")
                .put(request.getDiBudgets(), "diBudgetStart", "diBudgetEnd")
                .put(request.getDiCreateTimes(), "diCreateTimeStart", "diCreateTimeEnd");
        return params;
    }

    private IPage<DesignInitialResponse> getList(DesignInitialRequest request, QueryMap<DesignInitialRequest> params) {
        IPage<DesignInitialResponse> page = new Page<>(request.getPage(), request.getSize());
        List<DesignInitialResponse> datas = baseMapper.getDesignInitialList(page, params.getQuery());
        page.setRecords(datas);
        return page;
    }

    private String getSelfAuthSql(String uId) {
        String authSql = sysUserService.getAuthSql("Design_Initial", uId, "Di_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Di_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        return authSql;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteDesignInitialBydiId(String diId) {
        DesignInitial model = baseMapper.selectById(diId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getDiState()))
            return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(diId);
        baseMapper.deleteById(diId);
        //删除设计管理项目信息
        designProjectInfoService.delDesignProjectInfoByBusinessId(diId,2);
        //删除概算
        designInitialBudgetDtlService.deleteDesignInitialBudgetDtlByDiId(diId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getDesignInitialBydiId(String diId) {
        DesignInitialResponse designInitialResponse = baseMapper.getDesignInitialById(diId);
        DesignProjectInfoModel designProjectInfoModel = designProjectInfoService.getDesignProjectInfoByBusinessId(designInitialResponse.getDiId(), 2, designInitialResponse.getpId());
        designInitialResponse.setDesignProjectInfoModel(designProjectInfoModel);
        List<DesignInitialBudgetDtlResponse> budgetDtlResponseList = designInitialBudgetDtlService.getDesignInitialBudgetDtlListByDiId(diId);
        designInitialResponse.setDiBudgetDtls(budgetDtlResponseList);
        return BaseResponse.ok(designInitialResponse);
    }

    @Override
    public BaseResponse updateDesignInitialApproveStatus(DesignInitialRequest request) {
        String diId = request.getDiId();
        SysFile sysFile = sysFileService.getById(diId);
        String fileRelationId = sysFile.getFileRelationId();
        String fileflowstatus = sysFile.getFileflowstatus();

        DesignInitial designInitial = this.getById(diId);

        if("7".equals(fileflowstatus) && null !=designInitial.getCompanySealFlag() && designInitial.getCompanySealFlag()){
            //查询云上建工用户信息
            CommonResponse<ThirdUserRequest> thirdUserCommonResponse = dockFeign.getThirdUser(designInitial.getDiCreateUid());
            if (thirdUserCommonResponse.isSuccess() && thirdUserCommonResponse.getData() != null) {
                ThirdUserRequest thirdUserRequest = thirdUserCommonResponse.getData();

                designInitial.setThirdUserId(thirdUserRequest.getUserId());
                //创建流程
                WorkFlowRequest workFlowRequest = new WorkFlowRequest();
                workFlowRequest.setRequestName(designInitial.getDiTitle());
                workFlowRequest.setWorkflowId(sysConfigService.getConfigByCode("YSJG_TECHNICAL_SEAL_WFID"));
                workFlowRequest.setThirdUserId(thirdUserRequest.getUserId());
                workFlowRequest.setBizId(diId);
                workFlowRequest.setBizType("DesignInitial");

                if (StrUtil.isNotBlank(designInitial.getThirdRequestId())){
                    workFlowRequest.setRequestId(designInitial.getThirdRequestId());
                }

                List<MainDataRequest> mainData = new ArrayList<>();
                MainDataRequest yzlb = new MainDataRequest();
                yzlb.setFieldName("yzlb");//印章类别
                yzlb.setFieldValue(designInitial.getSealCategory());
                mainData.add(yzlb);

                MainDataRequest sfgz = new MainDataRequest();
                sfgz.setFieldName("sfgz");//是否盖章
                sfgz.setFieldValue("0");
                mainData.add(sfgz);

                MainDataRequest zysh = new MainDataRequest();
                zysh.setFieldName("zysh");//专业审核
                zysh.setFieldValue(designInitial.getProfessionalReview());
                mainData.add(zysh);

                MainDataRequest xmlb = new MainDataRequest();
                xmlb.setFieldName("xmlb");//项目类别
                xmlb.setFieldValue(designInitial.getProjectCategory());
                mainData.add(xmlb);

                MainDataRequest sy = new MainDataRequest();
                sy.setFieldName("sy");//事由
                sy.setFieldValue(designInitial.getReason());
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
                if (StrUtil.isNotBlank(designInitial.getThirdRequestId())){
                    //驳回后重新提交流程
                    workFLowCreateResponse = dockFeign.resubmitFLow(workFlowRequest);
                }else{
                    //创建流程
                    workFLowCreateResponse = dockFeign.createWorkFLow(workFlowRequest);
                }
                if (workFLowCreateResponse.isSuccess() && StrUtil.isNotBlank(workFLowCreateResponse.getData())) {
                    designInitial.setThirdRequestId(workFLowCreateResponse.getData());
                    //默认审批中
                    designInitial.setThirdStatus("1");
                }
                this.updateById(designInitial);
            }
        }
        return BaseResponse.ok();
    }
}
