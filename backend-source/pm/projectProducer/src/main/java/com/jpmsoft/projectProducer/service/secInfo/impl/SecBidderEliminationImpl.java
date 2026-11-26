package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidderEliminationModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBidderEliminationMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidderElimination;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBidderEliminationService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SecBidderEliminationImpl extends ServiceImpl<SecBidderEliminationMapper, SecBidderElimination> implements ISecBidderEliminationService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecBidderElimination(SecBidderEliminationModel request) {
        SecBidderElimination sec = new SecBidderElimination();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getSbeId())) {
            sec.setSbeCreateTime(new Date());
            sec.setSbeId(GenerateUUID.getUUID());
            sec.setSbeState(ValidateUtil.isEmpty(request.getSbeState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getSbeState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getSbeId(), request.gettId(), sec.getSbeState() + "", sec.getSbeCreateUid(), sec.getSbeCreateUname()
                    , sec.getSbeCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSbeBidSummaryFile(), request.getSbeNumberFinalists()), sec.getSbeId());

            return BaseResponse.ok(sec.getSbeId());
        } else {
            SecBidderElimination entity = baseMapper.selectById(request.getSbeId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getSbeState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setSbeCreateTime(entity.getSbeCreateTime());
            sec.setSbeCreateUid(entity.getSbeCreateUid());
            sec.setSbeCreateUname(entity.getSbeCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getSbeId(), request.gettId(), sec.getSbeState() + "", sec.getSbeCreateUid(), sec.getSbeCreateUname()
                    , sec.getSbeCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSbeBidSummaryFile(), request.getSbeNumberFinalists())
                    , Arrays.asList(request.getFileIds(), request.getSbeBidSummaryFileIds(), request.getSbeNumberFinalistsIds()), sec.getSbeId());


            return BaseResponse.ok(sec.getSbeId());
        }
    }

    @Override
    public BaseResponse getSecBidderEliminationList(SecBidderEliminationModel request) {
        QueryMap<SecBidderEliminationModel> params = new QueryMap<>(request);
        params.put("secId").put("secName").put("sbeCreateUname").put("sbeState")
                .put(request.getSbeCreateTimes(), "sbeCreateTimeStart", "sbeCreateTimeEnd")
                .put(request.getSecStartTimes(), "secStartTimeStart", "secStartTimeEnd")
                .put(request.getnAmounts(), "nAmountStart", "nAmountEnd")
                .put(request.getSbeBidders(), "sbeBiddersStart", "sbeBiddersEnd")
                .put(request.getSbeBidLinks(), "sbeBidLinksStart", "sbeBidLinksEnd")
                .put(request.getSbeQualifieds(), "sbeQualifiedStart", "sbeQualifiedEnd");
        List<SecBidderEliminationModel> datas = baseMapper.getSecBidderEliminationList(params.getQuery());
        Integer total = baseMapper.getSecBidderEliminationListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecBidderEliminationById(String sbeId) {
        SecBidderEliminationModel data = baseMapper.getSecBidderEliminationById(sbeId);
        return BaseResponse.ok(data, 0);
    }
}
