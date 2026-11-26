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
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConceptModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.DockFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignConceptMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConcept;
import com.jpmsoft.projectProducer.pojo.entity.other.SysConfig;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataFileRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.ThirdUserRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.WorkFlowRequest;
import com.jpmsoft.projectProducer.service.design.IDesignConceptService;
import com.jpmsoft.projectProducer.service.design.IDesignProjectInfoService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DesignConceptImpl extends FileImpl<DesignConceptMapper, DesignConcept, DesignConceptModel> implements IDesignConceptService {
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private DockFeign dockFeign;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private IDesignProjectInfoService designProjectInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignConcept(DesignConceptModel request) {
        DesignConcept entity = new DesignConcept();
        String entityId = createOrUpdateFileEntity(entity, request);
        DesignProjectInfoModel designProjectInfoModel = request.getDesignProjectInfoModel();
        if(null!=designProjectInfoModel){
            designProjectInfoModel.setBusinessId(entityId);
            designProjectInfoModel.setBusinessType(1);
            designProjectInfoModel.setpId(request.getpId());
            designProjectInfoModel.setCreateUid(request.getDcCreateUid());
            designProjectInfoModel.setCreateUname(request.getDcCreateUname());
            designProjectInfoService.createOrUpdateDesignProjectInfo(designProjectInfoModel);
        }
        return BaseResponse.ok(entityId);
    }

    @Override
    public BaseResponse getDesignConceptList(DesignConceptModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Design_Concept", uId, "Dc_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Dc_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<DesignConceptModel> params = getFilter(request);
        params.put("authSql", authSql);
        IPage<DesignConceptModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignConceptModel> datas = baseMapper.getDesignConceptList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignConceptListNoAuth(DesignConceptModel request) {
        QueryMap<DesignConceptModel> params = getFilter(request);
        IPage<DesignConceptModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignConceptModel> datas = baseMapper.getDesignConceptList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private QueryMap<DesignConceptModel> getFilter(DesignConceptModel request) {
        QueryMap<DesignConceptModel> params = new QueryMap<>(request);
        params.put("pNameHistory").put("dcFsr").put("dcAssignment").put("dcGovernment").put("pId").put("conName").put("conCode")
                .put("dcUser").put("dcExamine").put("dcDrawingVersion").put("dcDrawingSpecialty").put("dcName")
                .put("dcCreateUname").put("dcState").put("dcUnitName").put("dcUnitNameHistory").put("pName");
        return params;
    }


    @Override
    public BaseResponse getDesignConceptById(String dcId) {
        DesignConceptModel designConceptModel = baseMapper.getDesignConceptById(dcId);
        DesignProjectInfoModel designProjectInfoModel = designProjectInfoService.getDesignProjectInfoByBusinessId(designConceptModel.getDcId(), 1, designConceptModel.getpId());
        designConceptModel.setDesignProjectInfoModel(designProjectInfoModel);
        return BaseResponse.ok(designConceptModel);
    }

    @Override
    public BaseResponse updateDesignConceptApproveStatus(DesignConceptModel request) {
        String dcId = request.getDcId();
        SysFile sysFile = sysFileService.getById(dcId);
        String fileRelationId = sysFile.getFileRelationId();
        String fileflowstatus = sysFile.getFileflowstatus();

        DesignConcept designConcept = this.getById(dcId);

        if("7".equals(fileflowstatus) && null !=designConcept.getCompanySealFlag() && designConcept.getCompanySealFlag()){
            //查询云上建工用户信息
            CommonResponse<ThirdUserRequest> thirdUserCommonResponse = dockFeign.getThirdUser(designConcept.getDcCreateUid());
            if (thirdUserCommonResponse.isSuccess() && thirdUserCommonResponse.getData() != null) {
                ThirdUserRequest thirdUserRequest = thirdUserCommonResponse.getData();

                designConcept.setThirdUserId(thirdUserRequest.getUserId());
                //创建流程
                WorkFlowRequest workFlowRequest = new WorkFlowRequest();
                workFlowRequest.setRequestName(designConcept.getDcName());
                workFlowRequest.setWorkflowId(sysConfigService.getConfigByCode("YSJG_TECHNICAL_SEAL_WFID"));
                workFlowRequest.setThirdUserId(thirdUserRequest.getUserId());
                workFlowRequest.setBizId(dcId);
                workFlowRequest.setBizType("DesignConcept");


                if (StrUtil.isNotBlank(designConcept.getThirdRequestId())){
                    workFlowRequest.setRequestId(designConcept.getThirdRequestId());
                }

                List<MainDataRequest> mainData = new ArrayList<>();
                MainDataRequest yzlb = new MainDataRequest();
                yzlb.setFieldName("yzlb");//印章类别
                yzlb.setFieldValue(designConcept.getSealCategory());
                mainData.add(yzlb);

                MainDataRequest sfgz = new MainDataRequest();
                sfgz.setFieldName("sfgz");//是否盖章
                sfgz.setFieldValue("0");
                mainData.add(sfgz);

                MainDataRequest zysh = new MainDataRequest();
                zysh.setFieldName("zysh");//专业审核
                zysh.setFieldValue(designConcept.getProfessionalReview());
                mainData.add(zysh);

                MainDataRequest xmlb = new MainDataRequest();
                xmlb.setFieldName("xmlb");//项目类别
                xmlb.setFieldValue(designConcept.getProjectCategory());
                mainData.add(xmlb);

                MainDataRequest sy = new MainDataRequest();
                sy.setFieldName("sy");//事由
                sy.setFieldValue(designConcept.getReason());
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
                if (StrUtil.isNotBlank(designConcept.getThirdRequestId())){
                    //驳回后重新提交流程
                    workFLowCreateResponse = dockFeign.resubmitFLow(workFlowRequest);
                }else{
                    //创建流程
                    workFLowCreateResponse = dockFeign.createWorkFLow(workFlowRequest);
                }
                if (workFLowCreateResponse.isSuccess() && StrUtil.isNotBlank(workFLowCreateResponse.getData())) {
                    designConcept.setThirdRequestId(workFLowCreateResponse.getData());
                    //默认审批中
                    designConcept.setThirdStatus("1");
                }
                this.updateById(designConcept);
            }
        }
        return BaseResponse.ok();
    }
}
