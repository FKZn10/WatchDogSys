package com.jpmsoft.projectProducer.service.design.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedDetailModel;

import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignIssuedDetailMapper;

import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignIssued;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignIssuedDetail;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.design.IDesignIssuedDetailService;
import com.jpmsoft.projectProducer.service.design.IDesignIssuedService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DesignIssuedDetailImpl extends ServiceImpl<DesignIssuedDetailMapper, DesignIssuedDetail> implements IDesignIssuedDetailService {

    @Resource
    private IDesignIssuedService designIssuedService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private BpmFeign bpmFeign;

    @Resource
    private ISysUserService sysUserService;

    @Override
    public void createOrUpdateDesignIssueReissueDetail(DesignResultsIssuedModel request, String driId,String token) {

        List<DesignIssuedDetail> designIssuedDetails = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(request.getDesignIssuedDetails())) {
            for (DesignIssuedDetailModel designLssuedDetailModel : request.getDesignIssuedDetails()) {
                DesignIssuedDetail designLssued = new DesignIssuedDetail();
                BeanUtils.copyProperties(designLssuedDetailModel, designLssued);
                designIssuedDetails.add(designLssued);
            }
            // 将 List 转换为 Collection
            Collection<DesignIssuedDetail> collection = designIssuedDetails;
            saveBatch(collection);
            BathSendDesignResultsIssuedDetail(request, driId,token);
        }
    }

    private void BathSendDesignResultsIssuedDetail(DesignResultsIssuedModel request, String driId,String token) {
        List<DesignIssuedDetailModel> designIssuedDetailModels = request.getDesignIssuedDetails();
        List<BatchhandlingTaskListDto> taskListDtos = new ArrayList<>();
        BatchhandlingTaskDto task = new BatchhandlingTaskDto();
        task.setCurrentNode("createNode");
        task.setOnlineConditions("false");
        task.settCode(request.gettCode());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        task.setTeId(ValidateUtil.isEmpty(user.getTeId()) ? "All" : user.getTeId());
        for (DesignIssuedDetailModel designIssuedDetailModel : designIssuedDetailModels) {
            BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();
            tkl.setFileId(driId)
                    .setUId(user.getId())
                    .setProcessed(designIssuedDetailModel.getDidUid())
                    .setOperator(user.getId());
            taskListDtos.add(tkl);
        }
        task.setSendList(taskListDtos);
        BaseResponse taskRes = bpmFeign.batchSendTask(task, token);
        if (taskRes == null || !taskRes.getSuccess()) {
            throw new JPMException(ResMsg.taskCreateFail);
        }
    }

    @Override
    public List<DesignIssuedDetailModel> getDesignIssueReissueDetailById(String id) {
        LambdaQueryWrapper<DesignIssued> issuedLambdaQueryWrapper = new LambdaQueryWrapper<>();
        issuedLambdaQueryWrapper.eq(DesignIssued::getDiEntityId, id);
        List<DesignIssued> designIssueds = designIssuedService.list(issuedLambdaQueryWrapper);

        if (CollectionUtil.isEmpty(designIssueds)) {
            return Collections.emptyList();
        }

        List<String> diIds = designIssueds.stream()
                .map(DesignIssued::getId)
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtil.isEmpty(diIds)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<DesignIssuedDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper.in(DesignIssuedDetail::getDiId, diIds);
        List<DesignIssuedDetail> designIssuedDetails = list(detailLambdaQueryWrapper);

        List<String> uIds = designIssuedDetails.stream()
                .map(DesignIssuedDetail::getDidUid)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<String, String> userNames;
        if (!uIds.isEmpty()) {
            List<SysUser> users = sysUserService.listByIds(uIds);
            userNames = users.stream()
                    .collect(Collectors.toMap(SysUser::getUId, SysUser::getUName, (oldValue, newValue) -> oldValue));
        } else {
            userNames = new HashMap<>();
        }

        Map<String, Date> issuedCreateTimeMap = designIssueds.stream()
                .collect(Collectors.toMap(DesignIssued::getId, DesignIssued::getCreateTime));

        List<DesignIssuedDetailModel> models = designIssuedDetails.stream()
                .map(detail -> {
                    DesignIssuedDetailModel model = new DesignIssuedDetailModel();
                    BeanUtils.copyProperties(detail, model);
                    model.setDidUname(userNames.getOrDefault(detail.getDidUid(), detail.getDidUname()));
                    model.setCreateTime(issuedCreateTimeMap.get(detail.getDiId()));
                    return model;
                })
                .collect(Collectors.toList());

        Optional<DesignIssuedDetailModel> projectRetentionOpt = models.stream()
                .filter(m -> "发起部门留存".equals(m.getDidType()))
                .max(Comparator.<DesignIssuedDetailModel, Date>comparing(
                        DesignIssuedDetailModel::getCreateTime,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ));

        List<DesignIssuedDetailModel> others = models.stream()
                .filter(m -> !"发起部门留存".equals(m.getDidType()))
                .collect(Collectors.toList());

        others.sort(
                Comparator
                        .comparing(DesignIssuedDetailModel::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(DesignIssuedDetailModel::getDidNumber, Comparator.nullsLast(Integer::compareTo))
        );

        List<DesignIssuedDetailModel> result = new ArrayList<>();

        projectRetentionOpt.ifPresent(result::add);

        result.addAll(others);

        for (int i = 0; i < result.size(); i++) {
            result.get(i).setDidNumber(i + 1);
        }

        return result;
    }




}
