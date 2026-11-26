package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecQeReportRequest;
import com.jpmsoft.common.model.response.SecInfo.SecQeGroupDtlResponse;
import com.jpmsoft.common.model.response.SecInfo.SecQeReportResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecQeReportMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeReport;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQeGroupDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQeReportService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SecQeReportImpl extends ServiceImpl<SecQeReportMapper, SecQeReport> implements ISecQeReportService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISecQeGroupDtlService secQeGroupDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateQeReport(SecQeReportRequest request) {
        SecQeReport entity = new SecQeReport();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);

        if (ValidateUtil.isEmpty(request.getQerId())) {
            entity.setQerState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setQerCreateTime(new Date());
            entity.setQerId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getQerId(), request.gettId(), entity.getQerState() + "", entity.getQerCreateUid(), entity.getQerCreateUname()
                    , entity.getQerCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), entity.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), entity.getQerReport()), entity.getQerId());

            return BaseResponse.ok(entity.getQerId());
        } else {
            SecQeReport model = baseMapper.selectById(request.getQerId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getQerState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setQerCreateTime(model.getQerCreateTime());
            entity.setQerCreateUid(model.getQerCreateUid());
            entity.setQerCreateUname(model.getQerCreateUname());
            entity.setQerState(model.getQerState());
            entity.setQerStateTime(model.getQerStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getQerId(), request.gettId(), entity.getQerState() + "", entity.getQerCreateUid(), entity.getQerCreateUname()
                    , entity.getQerCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), entity.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), entity.getQerReport()),
                    Arrays.asList(request.getFileIds(), request.getQerReportIds()), entity.getQerId());

            return BaseResponse.ok(entity.getQerId());
        }
    }

    @Override
    public BaseResponse getQeReportList(SecQeReportRequest request) {
        QueryMap<SecQeReportRequest> params = new QueryMap<>(request);
        params.put("qerResult").put("qerCreateUname").put("qerRemark").put("qerState").put("secName")
                .put("qerBidderNum").put("qerBidderNumAdopt").put("qerDate").put("qerDate")
                .put(request.getQerCreateTimes(), "qerCreateTimeStart", "qerCreateTimeEnd");
        List<SecQeReportResponse> datas = baseMapper.getQeReportList(params.getQuery());
        Integer total = baseMapper.getQeReportListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteQeReportByQerId(String qerId) {
        SecQeReport model = baseMapper.selectById(qerId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getQerState()))
            return BaseResponse.fail(ResMsg.stateNotDel);
        baseMapper.deleteById(qerId);
        sysFileService.deleteFileById(qerId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getQeReportByQerId(String qerId) {
        SecQeReportResponse data = baseMapper.getQeReportByQerId(qerId);
        if (data != null) {
            List<SecQeGroupDtlResponse> dtls = secQeGroupDtlService.getSecQeGroupDtlBySecId(data.getSecId());
            data.setSqGroupDtls(dtls);
        }
        return BaseResponse.ok(data);
    }
}
