package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertAuditGroupMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditGroupDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditGroupDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpert;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditGroup;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditGroupDetail;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAuditGroupDetailService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAuditGroupService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertChangeService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 集团专家评审
 */
@Service
public class SecExpertAuditGroupImpl extends FileImpl<SecExpertAuditGroupMapper, SecExpertAuditGroup, SecExpertAuditGroupDTO> implements ISecExpertAuditGroupService {
    @Resource
    private ISecExpertAuditGroupDetailService secExpertAuditGroupDetailService;
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISecExpertChangeService secExpertChangeService;

    @Override
    public BaseResponse createOrUpdateSecExpertAuditGroup(SecExpertAuditGroupDTO request, String token) {
        if (ValidateUtil.isNull(request.getYear())) {
            request.setYear(DateUtil.getTimeYear(new Date()));
        }
        SecExpertAuditGroup entity = new SecExpertAuditGroup();
        // LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        // entity.setSeaCid(user.getCId());
        createOrUpdateFileEntity(entity, request);
        secExpertAuditGroupDetailService.delete2Create(request.getSecExpertAuditDetails(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse createOrUpdateSecExpertAuditGroupDz(SecExpertAuditGroupDTO request, String token) {
        if (ValidateUtil.isNull(request.getCreateTime())) {
            request.setCreateTime(new Date());
        }
        Integer year = DateUtil.getTimeYear(request.getCreateTime());
        if (ValidateUtil.isEmpty(request.getId())) {
            request.setSeaName(year.toString() + "年评委入库审核表");
        }
        if (this.count(Wrappers.<SecExpertAuditGroup>lambdaQuery().eq(SecExpertAuditGroup::getSeaName, request.getSeaName())
                .ne(ValidateUtil.isNotEmpty(request.getId()), SecExpertAuditGroup::getId, request.getId())
                .ne(SecExpertAuditGroup::getState, ApprovalStatusEnum.Invalid.getIndex())) > 0) {
            return BaseResponse.fail("当前已存在入库申请，请勿重复创建！");
        }
        List<SecExpertAuditGroupDetailDTO> secExpertAuditDetails = request.getSecExpertAuditDetails();
        if (CollectionUtil.isNotEmpty(secExpertAuditDetails)) {
            List<String> exIdList = secExpertAuditDetails.stream().filter(s -> s.getExId() != null).map(e -> e.getExId()).collect(Collectors.toList());
            secExpertService.checkExpertWorkTime(exIdList);
        }

        return createOrUpdateSecExpertAuditGroup(request, token);
    }

    @Override
    public BaseResponse getSecExpertAuditGroupList(SecExpertAuditGroupDTO request) {
        QueryMap<SecExpertAuditGroupDTO> params = new QueryMap<>(request);
        params.put("seaName").put("createUname").put("state").put("year");
        IPage<SecExpertAuditGroupDTO> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertAuditGroupDTO> data = baseMapper.getSecExpertAuditGroupList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSecExpertAuditGroupById(String id) {
        SecExpertAuditGroup sea = baseMapper.selectById(id);
        if (sea == null) return BaseResponse.fail(ResMsg.infoNotExist);
        SecExpertAuditGroupDTO data = new SecExpertAuditGroupDTO();
        BeanUtils.copyProperties(sea, data);
        data.setSecExpertAuditDetails(secExpertAuditGroupDetailService.getDetailList(id));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecExpertAuditGroupByYear(Integer year) {
        if (ValidateUtil.isNull(year)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertAuditGroup sea = this.getOne(Wrappers.<SecExpertAuditGroup>lambdaQuery()
                .eq(SecExpertAuditGroup::getYear, year)
                .ne(SecExpertAuditGroup::getState, ApprovalStatusEnum.Invalid.getIndex())
                .orderByDesc(SecExpertAuditGroup::getCreateTime)
                .last("limit 1"));
        if (sea == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        SecExpertAuditGroupDTO data = new SecExpertAuditGroupDTO();
        BeanUtils.copyProperties(sea, data);
        data.setSecExpertAuditDetails(secExpertAuditGroupDetailService.getDetailList(sea.getId()));
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncSecExpert(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertAuditGroup group = this.getById(id);
        if (ValidateUtil.isNull(group)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(group.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        List<SecExpertAuditGroupDetail> list = secExpertAuditGroupDetailService.list(Wrappers.<SecExpertAuditGroupDetail>lambdaQuery()
                .eq(SecExpertAuditGroupDetail::getSeaId, id)
        );
        // 更新专家表的是否是重点专家字段
        List<SecExpert> expertList = new ArrayList<>();
        // 需要同步变更的专家id列表
        List<String> exIds = new ArrayList<>();

        List<String> allExId = list.stream().map(SecExpertAuditGroupDetail::getExId).collect(Collectors.toList());
        for (SecExpertAuditGroupDetail detail : list) {
            // 不处理未发起的
            if (ValidateUtil.isNotEmpty(detail.getExChangeStatus()) && "04".equals(detail.getExChangeStatus())) {
                continue;
            }
            SecExpert secExpert = new SecExpert();
            secExpert.setExId(detail.getExId());
            secExpert.setKeyExpert(detail.getKeyExpert());
            if (ValidateUtil.isNotEmpty(detail.getExChangeStatus())) {
                switch (detail.getExChangeStatus()) {
                    case "01":
                        secExpert.setExStatus(1);
                        exIds.add(detail.getExId());
                        break;
                    case "03":
                        secExpert.setExStatus(0);
                        break;
                    case "02":
                        exIds.add(detail.getExId());
                        break;
                }
            }
            expertList.add(secExpert);
        }
        if (ValidateUtil.isNotEmpty(expertList)) {
            secExpertService.updateBatchById(expertList);
        }
        if (ValidateUtil.isNotEmpty(exIds)) {
            // 调用批量同步更改的方法
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            secExpertChangeService.batchSyncChange(exIds);
                        }
                    }
            );
        }
        if (ValidateUtil.isNotEmpty(allExId)) {
            secExpertService.update(Wrappers.<SecExpert>lambdaUpdate()
                    .notIn(SecExpert::getExId, allExId)
                    .eq(SecExpert::getYear, group.getYear())
                    .set(SecExpert::getExStatus, 0)
            );
        }
        return BaseResponse.ok();
    }
}
