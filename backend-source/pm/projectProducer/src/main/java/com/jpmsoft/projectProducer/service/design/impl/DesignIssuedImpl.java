package com.jpmsoft.projectProducer.service.design.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedDetailModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignIssuedMapper;

import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.other.UserDto;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignIssued;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignIssuedDetail;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignResultsIssued;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;
import com.jpmsoft.projectProducer.service.design.IDesignIssuedDetailService;
import com.jpmsoft.projectProducer.service.design.IDesignIssuedService;
import com.jpmsoft.projectProducer.service.design.IDesignResultsIssuedService;
import com.jpmsoft.projectProducer.service.other.ISysCompanyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DesignIssuedImpl extends ServiceImpl<DesignIssuedMapper, DesignIssued> implements IDesignIssuedService {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private BpmFeign bpmFeign;

    @Resource
    private IDesignIssuedDetailService designIssuedDetailService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private UserProducerFeign userProducerFeign;

    @Resource
    private ISysCompanyService sysCompanyService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdateDesignIssued(DesignResultsIssuedModel request, String driId, String token) {

        DesignIssued designIssued = new DesignIssued();
        String uId = redisUtils.getUid(token);
        LogUserInfoVo logUserInfoVo = redisUtils.getUserInfoByToken(token);
        // 07 项目管理单位
        Set<String> uIds = new HashSet<>();
        if (ValidateUtil.isNotEmpty(request.getpId())) {
            UserDto userDto = new UserDto();
            userDto.setPdId(request.getpId());
            userDto.setPage(1);
            userDto.setSize(Integer.MAX_VALUE);
            BaseResponse baseResponse = userProducerFeign.GetUserListByDPId(userDto);

            if (baseResponse != null && baseResponse.getSuccess() && baseResponse.getData() != null) {
                // 将数据转换为 List<SysUserVo>
                List<SysUserVo> sysUserVos = (List<SysUserVo>) baseResponse.getData();

                // 判断列表是否为空
                if (CollectionUtil.isNotEmpty(sysUserVos)) {
                    uIds.addAll(sysUserVos.stream()
                            .map(SysUserVo::getuId)
                            .collect(Collectors.toList()));
                }
            }
        }
        // 查询当前操作人的单位类型
        if (uIds.contains(uId)) {
            designIssued.setDiCompanyType("02");
        } else {
            // 否则根据单位类型设置 cType
            SysUser sysUser = sysUserService.getById(uId);
            if (sysUser != null && ValidateUtil.isNotEmpty(sysUser.getCId())) {
                SysCompany sysCompany = sysCompanyService.getById(sysUser.getCId());
                if (sysCompany != null) {
                    if ("07".equals(sysCompany.getcType())) {
                        designIssued.setDiCompanyType("02");
                    } else {
                        designIssued.setDiCompanyType("01");
                    }
                }
            }
        }
        String tCode = "LssuanceAndReissue";
        designIssued.setId(GenerateUUID.getUUID());
        designIssued.setCreateTime(new Date());
        designIssued.setState(request.getState());
        designIssued.setCreateUid(uId);
        designIssued.setCreateUname(logUserInfoVo.getName());
        designIssued.setStateTime(new Date());
        designIssued.setDiEntityId(driId);
        designIssued.setDiModule(tCode);
        designIssued.setDiIsIssued(request.getDiIsIssued());
        designIssued.setDriFormType(request.getDriFormType());
        baseMapper.insert(designIssued);
        FileTemplateTree fileTemplateByCode = userProducerFeign.getFileTemplateByCode(tCode);
        if (fileTemplateByCode == null) {
            return;
        }
        String fileRelationId = GenerateUUID.getUUID();
        sysFileService.saveFile(designIssued.getId(), fileTemplateByCode.gettId(), designIssued.getState() + "", designIssued.getCreateUid(), designIssued.getCreateUname()
                , designIssued.getCreateTime(), request.getpId(), request.getFileTitle(), tCode, "create",
                fileRelationId, null, tCode, null);
        sysFileAnnexService.addFormRefreshRelationFile(Collections.singletonList(fileRelationId), designIssued.getId());
        List<DesignIssuedDetail> designIssuedDetails = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(request.getDesignIssuedDetails())) {
            for (DesignIssuedDetailModel designIssuedDetailModel : request.getDesignIssuedDetails()) {
                DesignIssuedDetail designIssuedDetail = new DesignIssuedDetail();
                BeanUtils.copyProperties(designIssuedDetailModel, designIssuedDetail);
                designIssuedDetail.setDidElectron(designIssuedDetailModel.getDidElectron());
                designIssuedDetail.setDiId(designIssued.getId());
                designIssuedDetail.setDidUname(designIssuedDetailModel.getDidUname());
                designIssuedDetails.add(designIssuedDetail);
            }
            Collection<DesignIssuedDetail> collection = designIssuedDetails;

            designIssuedDetailService.saveBatch(collection);
            if ("03".equals(request.getDiIsIssued())) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        bathSendDesignResultsIssuedDetail(request,designIssued,tCode,token);
                    }
                });
            }
        }
    }

    @Override
    public BaseResponse getDesignIssuedList(DesignIssuedModel request, String uid) {
        if (request == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isEmpty(request.getDiEntityId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<DesignIssuedModel> designIssuedModels = baseMapper.getDesignIssuedList(request);
        return BaseResponse.ok(designIssuedModels);
    }

    @Override
    public BaseResponse getDesignIssuedById(String id, String uid) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notId);
        }

        DesignResultsIssuedModel designIssuedModel = baseMapper.getDesignIssuedById(id);
        return BaseResponse.ok(designIssuedModel);
    }

    private void bathSendDesignResultsIssuedDetail(DesignResultsIssuedModel request, DesignIssued designIssued, String tCode, String token) {
        List<DesignIssuedDetailModel> designIssuedDetailModels = request.getDesignIssuedDetails();
        List<BatchhandlingTaskListDto> taskListDtos = new ArrayList<>();
        BatchhandlingTaskDto task = new BatchhandlingTaskDto();
        task.setCurrentNode("createNode");
        task.setOnlineConditions(String.valueOf(true));
        task.settCode(tCode);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        task.setTeId(ValidateUtil.isEmpty(user.getTeId()) ? "All" : user.getTeId());
        for (DesignIssuedDetailModel designIssuedDetailModel : designIssuedDetailModels) {
            BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();
            if (ValidateUtil.isEmpty(designIssuedDetailModel.getDidUid()) || !"03".equals(designIssuedDetailModel.getDidIsIssuedTask())) {
                continue;
            }
            tkl.setFileId(designIssued.getId())
                    .setUId(user.getId())
                    .setProcessed(designIssuedDetailModel.getDidUid())
                    .setOperator(user.getId());
            taskListDtos.add(tkl);
        }
        if(!taskListDtos.isEmpty()) {
            task.setSendList(taskListDtos);
            BaseResponse taskRes = bpmFeign.batchSendTask(task, token);
            log.info("taskRes:{}", taskRes);
            if (taskRes == null || !taskRes.getSuccess()) {
                throw new JPMException(ResMsg.taskCreateFail);
            }
        }
    }
}

