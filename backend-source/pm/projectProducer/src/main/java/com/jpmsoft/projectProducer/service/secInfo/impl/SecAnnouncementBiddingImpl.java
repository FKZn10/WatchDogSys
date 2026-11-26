package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnnouncementBiddingModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecAnnouncementBiddingMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecAnnouncementBidding;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecAnnouncementBiddingService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SecAnnouncementBiddingImpl extends ServiceImpl<SecAnnouncementBiddingMapper, SecAnnouncementBidding>
        implements ISecAnnouncementBiddingService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecAnnouncementBidding(SecAnnouncementBiddingModel request) {
        SecAnnouncementBidding sec = new SecAnnouncementBidding();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSabId())) {
            sec.setSabCreateTime(new Date());
            sec.setSabId(GenerateUUID.getUUID());
            sec.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getSabId(), request.gettId(), sec.getState() + "", sec.getSabCreateUid(), sec.getSabCreateUname()
                    , sec.getSabCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getSabId());

            return BaseResponse.ok(sec.getSabId());
        } else {
            SecAnnouncementBidding entity = baseMapper.selectById(request.getSabId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setSabCreateTime(entity.getSabCreateTime());
            sec.setSabCreateUid(entity.getSabCreateUid());
            sec.setSabCreateUname(entity.getSabCreateUname());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getSabId(), request.gettId(), sec.getState() + "", sec.getSabCreateUid(), sec.getSabCreateUname()
                    , sec.getSabCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getSabId());

            return BaseResponse.ok(sec.getSabId());
        }
    }

    @Override
    public BaseResponse getSecAnnouncementBiddingList(SecAnnouncementBiddingModel request) {
        QueryMap<SecAnnouncementBiddingModel> params = new QueryMap<>(request);
        params.put("secId").put("secName").put("secCode").put("sabCreateUname").put("state")
                .put(request.getSabAnnouncementTimes(), "sabAnnouncementTimeStart", "sabAnnouncementTimeEnd")
                .put(request.getSabClosBidTimes(), "sabClosBidTimeStart", "sabClosBidTimeEnd");
        List<SecAnnouncementBiddingModel> datas = baseMapper.getSecAnnouncementBiddingList(params.getQuery());
        Integer total = baseMapper.getSecAnnouncementBiddingListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecAnnouncementBiddingById(String sabId) {
        SecAnnouncementBiddingModel data = baseMapper.getSecAnnouncementBiddingById(sabId);
        return BaseResponse.ok(data, 0);
    }
}
