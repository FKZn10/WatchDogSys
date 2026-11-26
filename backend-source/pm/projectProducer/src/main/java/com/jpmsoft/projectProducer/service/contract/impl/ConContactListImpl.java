package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Contract.ConContactListRequest;
import com.jpmsoft.common.model.response.Contract.ConContactListResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.DockFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConContactListMapper;
import com.jpmsoft.projectProducer.mapper.other.SysFileMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContactList;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataFileRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.ThirdUserRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.WorkFlowRequest;
import com.jpmsoft.projectProducer.service.contract.IConContactListService;
import com.jpmsoft.projectProducer.service.contract.IConEcService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ConContactListImpl extends ServiceImpl<ConContactListMapper, ConContactList> implements IConContactListService {

    @Resource
    private IConEcService conEcService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private SysFileMapper sysFileMapper;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private DockFeign dockFeign;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "clId", codeParam = "clCode", method = "setClCode", codeRule = "ConContactList", beanClass = ConContactListRequest.class)
    public BaseResponse createOrUpdateContactList(ConContactListRequest request) {
        ConContactList entity = new ConContactList();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getClCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        QueryWrapper<ConContactList> wrapper = new QueryWrapper<>();
        wrapper.eq("Cl_Code", request.getClCode());
        if (!ValidateUtil.isEmpty(entity.getClId()))
            wrapper.ne("Cl_Id", request.getClId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (ValidateUtil.isEmpty(request.getClId())) {
            entity.setClId(GenerateUUID.getUUID());
            entity.setClCreateTime(new Date());
            entity.setClState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setClStateTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getClId(), request.gettId(), entity.getClState() + "", entity.getClCreateUid(), entity.getClCreateUname()
                    , entity.getClCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getClId());
        } else {
            ConContactList model = baseMapper.selectById(request.getClId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setClCreateTime(model.getClCreateTime());
            entity.setClCreateUid(model.getClCreateUid());
            entity.setClCreateUname(model.getClCreateUname());
            entity.setClState(model.getClState());
            entity.setClStateTime(model.getClStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getClId(), request.gettId(), entity.getClState() + "", entity.getClCreateUid(), entity.getClCreateUname()
                    , entity.getClCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getClId());
        }
        return BaseResponse.ok(entity.getClId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteContactLisByClId(String clId) {
        ConContactList model = baseMapper.selectById(clId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (conEcService.contactListAssociateData(clId))
            return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(clId);
        sysFileService.deleteFileById(clId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getContactLisByClId(String clId) {
        ConContactListResponse data = baseMapper.getContactLisByClId(clId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getConContactList(ConContactListRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Contract_List", uId, "Cl_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Cl_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConContactListRequest> params = getQueryMap(request, authSql);
        IPage<ConContactListResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContactListResponse> datas = baseMapper.getConContactList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    @Override
    public BaseResponse getConContactListNoAuth(ConContactListRequest request) {
        QueryMap<ConContactListRequest> params = getQueryMap(request, null);
        IPage<ConContactListResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContactListResponse> datas = baseMapper.getConContactList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    private QueryMap<ConContactListRequest> getQueryMap(ConContactListRequest request, String authSql) {
        QueryMap<ConContactListRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("clNeedCost").put("clCode").put("clType")
                .put("clLocation").put("clDrawingNo").put("clTitle").put("pName")
                .put("conName").put("pId").put("clState").put("clCreateUname")
                .put(request.getClCreateTimes(), "clCreateTimeStart", "clCreateTimeEnd");
        return params;
    }


    @Override
    public ConContactList getConContactListByConId(String conId) {
        if (ValidateUtil.isEmpty(conId)) return null;
        QueryWrapper<ConContactList> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.ne("Cl_State", ApprovalStatusEnum.Invalid.getIndex());
        List<ConContactList> datas = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(datas)) return null;
        return datas.get(0);
    }

    @Override
    public void processSyn(String uuid) {
        SysFile sysFile = sysFileMapper.selectById(uuid);
        if (sysFile == null) {
            throw new JPMException("无效数据");
        }
        ConContactList conContactList = getById(uuid);
        if (conContactList == null) {
            throw new JPMException("无效数据");
        }
        String fileRelationId = sysFile.getFileRelationId();
        String fileflowstatus = sysFile.getFileflowstatus();
        if("7".equals(fileflowstatus)){
            //查询云上建工用户信息
            CommonResponse<ThirdUserRequest> thirdUserCommonResponse = dockFeign.getThirdUser(conContactList.getClCreateUid());
            if (thirdUserCommonResponse.isSuccess() && thirdUserCommonResponse.getData() != null) {
                ThirdUserRequest thirdUserRequest = thirdUserCommonResponse.getData();
                conContactList.setThirdUserId(thirdUserRequest.getUserId());
                WorkFlowRequest workFlowRequest = new WorkFlowRequest();
                workFlowRequest.setRequestName(conContactList.getClTitle());
                workFlowRequest.setWorkflowId(sysConfigService.getConfigByCode("YSJG_TECHNICAL_SEAL_WFID"));
                workFlowRequest.setBizId(conContactList.getClId());
                workFlowRequest.setBizType("ConContactList");
                workFlowRequest.setThirdUserId(thirdUserRequest.getUserId());

                if (org.apache.commons.lang3.StringUtils.isNotBlank(conContactList.getThirdRequestId())) {
                    workFlowRequest.setRequestId(conContactList.getThirdRequestId());
                }

                List<MainDataRequest> mainData = new ArrayList<>();
                MainDataRequest sqr = new MainDataRequest();
                sqr.setFieldName("sqr");
                sqr.setFieldValue(thirdUserRequest.getUserId());
                mainData.add(sqr);

                MainDataRequest sqbm = new MainDataRequest();
                sqbm.setFieldName("sqbm");
                sqbm.setFieldValue(thirdUserRequest.getDepartmentId());
                mainData.add(sqbm);

                MainDataRequest sqdw = new MainDataRequest();
                sqdw.setFieldName("sqdw");
                sqdw.setFieldValue(thirdUserRequest.getSubCompanyId());
                mainData.add(sqdw);

                MainDataRequest sqrq = new MainDataRequest();
                sqrq.setFieldName("sqrq");
                sqrq.setFieldValue(DateFormatUtil.dateToString(new Date(), DateFormatUtil.FORMAT_PATTERN_OF_DAY));
                mainData.add(sqrq);

                // 流程编号

                // 印章类别
                mainData.add(MainDataRequest.builder()
                        .fieldName("yzlb")
                        .fieldValue(conContactList.getSealCategory())
                        .build());
                // 是否盖章
                mainData.add(MainDataRequest.builder()
                        .fieldName("sfgz")
                        .fieldValue("0")
                        .build());

                // 专业审核
                mainData.add(MainDataRequest.builder()
                        .fieldName("zysh")
                        .fieldValue(conContactList.getProfessionalReview())
                        .build());

                // 项目类别
                mainData.add(MainDataRequest.builder()
                        .fieldName("xmlb")
                        .fieldValue(conContactList.getProjectCategory())
                        .build());

                // 事由
                mainData.add(MainDataRequest.builder()
                        .fieldName("sy")
                        .fieldValue(conContactList.getReason())
                        .build());

                // 公共附件
                if (org.apache.commons.lang3.StringUtils.isNotBlank(fileRelationId)) {
                    String rootDirectory = sysConfigService.getConfigByCode("rootDirectory");
                    String archiveDirectory = sysConfigService.getConfigByCode("archiveDirectory");
                    LambdaQueryWrapper<SysFileannex> fileQueryWrapper = new LambdaQueryWrapper<>();
                    fileQueryWrapper.eq(SysFileannex::getFileRelationId, fileRelationId);
                    fileQueryWrapper.eq(SysFileannex::getUploadStatus, 1);
                    List<SysFileannex> fileList = sysFileAnnexService.list(fileQueryWrapper);
                    if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(fileList)) {
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
                if (StrUtil.isNotBlank(conContactList.getThirdRequestId())){
                    //驳回后重新提交流程
                    workFLowCreateResponse = dockFeign.resubmitFLow(workFlowRequest);
                }else{
                    //创建流程
                    workFLowCreateResponse = dockFeign.createWorkFLow(workFlowRequest);
                }
                if (workFLowCreateResponse.isSuccess() && StrUtil.isNotBlank(workFLowCreateResponse.getData())) {
                    conContactList.setThirdRequestId(workFLowCreateResponse.getData());
                    //默认审批中
                    conContactList.setThirdStatus("1");
                }
                this.updateById(conContactList);
            }
        }
    }
}
