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
import com.jpmsoft.common.model.requestAndResponse.Design.DesignFeasibilityModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.DockFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignFeasibilityMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConcept;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignFeasibility;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataFileRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.ThirdUserRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.WorkFlowRequest;
import com.jpmsoft.projectProducer.service.design.IDesignFeasibilityService;
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
public class DesignFeasibilityImpl extends FileImpl<DesignFeasibilityMapper, DesignFeasibility, DesignFeasibilityModel> implements IDesignFeasibilityService {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private DockFeign dockFeign;

    @Resource
    private ISysConfigService sysConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignFeasibility(DesignFeasibilityModel request) {
        DesignFeasibility entity = new DesignFeasibility();
        return BaseResponse.ok(createOrUpdateFileEntity(entity, request));
    }


    @Override
    public BaseResponse getDesignFeasibilityList(DesignFeasibilityModel request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Df_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Df_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<DesignFeasibilityModel> params = getQueryMap(request, authSql);
        IPage<DesignFeasibilityModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignFeasibilityModel> datas = baseMapper.getDesignFeasibilityList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignFeasibilityListNoAuth(DesignFeasibilityModel request) {
        QueryMap<DesignFeasibilityModel> params = getQueryMap(request, null);
        IPage<DesignFeasibilityModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignFeasibilityModel> datas = baseMapper.getDesignFeasibilityList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private QueryMap<DesignFeasibilityModel> getQueryMap(DesignFeasibilityModel request, String authSql) {
        QueryMap<DesignFeasibilityModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pNameHistory").put("conName")
                .put("dfName").put("dfRemark").put("dfCreateUname").put("dfState").put("conCode")
                .put(request.getDfCreateTimes(), "dfCreateTimeStart", "dfCreateTimeEnd");
        return params;
    }

    @Override
    public BaseResponse getDesignFeasibilityById(String dfId) {
        return BaseResponse.ok(baseMapper.getDesignFeasibilityById(dfId));
    }

    @Override
    public BaseResponse updateDesignFeasibilityApproveStatus(DesignFeasibilityModel request) {
        String dfId = request.getDfId();
        SysFile sysFile = sysFileService.getById(dfId);
        String fileRelationId = sysFile.getFileRelationId();
        String fileflowstatus = sysFile.getFileflowstatus();

        DesignFeasibility designFeasibility = this.getById(dfId);

        if("7".equals(fileflowstatus) && null !=designFeasibility.getCompanySealFlag() && designFeasibility.getCompanySealFlag()){
            //查询云上建工用户信息
            CommonResponse<ThirdUserRequest> thirdUserCommonResponse = dockFeign.getThirdUser(designFeasibility.getDfCreateUid());
            if (thirdUserCommonResponse.isSuccess() && thirdUserCommonResponse.getData() != null) {
                ThirdUserRequest thirdUserRequest = thirdUserCommonResponse.getData();

                designFeasibility.setThirdUserId(thirdUserRequest.getUserId());
                //创建流程
                WorkFlowRequest workFlowRequest = new WorkFlowRequest();
                workFlowRequest.setRequestName(designFeasibility.getDfName());
                workFlowRequest.setWorkflowId(sysConfigService.getConfigByCode("YSJG_TECHNICAL_SEAL_WFID"));
                workFlowRequest.setThirdUserId(thirdUserRequest.getUserId());
                workFlowRequest.setBizId(dfId);
                workFlowRequest.setBizType("DesignFeasibility");

                if (StrUtil.isNotBlank(designFeasibility.getThirdRequestId())){
                    workFlowRequest.setRequestId(designFeasibility.getThirdRequestId());
                }

                List<MainDataRequest> mainData = new ArrayList<>();
                MainDataRequest yzlb = new MainDataRequest();
                yzlb.setFieldName("yzlb");//印章类别
                yzlb.setFieldValue(designFeasibility.getSealCategory());
                mainData.add(yzlb);

                MainDataRequest sfgz = new MainDataRequest();
                sfgz.setFieldName("sfgz");//是否盖章
                sfgz.setFieldValue("0");
                mainData.add(sfgz);

                MainDataRequest zysh = new MainDataRequest();
                zysh.setFieldName("zysh");//专业审核
                zysh.setFieldValue(designFeasibility.getProfessionalReview());
                mainData.add(zysh);

                MainDataRequest xmlb = new MainDataRequest();
                xmlb.setFieldName("xmlb");//项目类别
                xmlb.setFieldValue(designFeasibility.getProjectCategory());
                mainData.add(xmlb);

                MainDataRequest sy = new MainDataRequest();
                sy.setFieldName("sy");//事由
                sy.setFieldValue(designFeasibility.getReason());
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
                if (StrUtil.isNotBlank(designFeasibility.getThirdRequestId())){
                    //驳回后重新提交流程
                    workFLowCreateResponse = dockFeign.resubmitFLow(workFlowRequest);
                }else{
                    //创建流程
                    workFLowCreateResponse = dockFeign.createWorkFLow(workFlowRequest);
                }
                if (workFLowCreateResponse.isSuccess() && StrUtil.isNotBlank(workFLowCreateResponse.getData())) {
                    designFeasibility.setThirdRequestId(workFLowCreateResponse.getData());
                    //默认审批中
                    designFeasibility.setThirdStatus("1");
                }
                this.updateById(designFeasibility);
            }
        }
        return BaseResponse.ok();
    }
}
