package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecNoticeRequest;
import com.jpmsoft.common.model.response.SecInfo.SecNoticeResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecNoticeMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecNotice;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecNoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SecNoticeImpl extends ServiceImpl<SecNoticeMapper, SecNotice> implements ISecNoticeService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecNotice(SecNoticeRequest request) {
        SecNotice sec = new SecNotice();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);

        if (ValidateUtil.isEmpty(request.getnId())) {
            sec.setNCreateTime(new Date());
            sec.setNId(GenerateUUID.getUUID());
            sec.setNState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getNId(), request.gettId(), sec.getNState() + "", sec.getNCreateUid(), sec.getNCreateUname()
                    , sec.getNCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getNId());

            return BaseResponse.ok(sec.getNId());
        } else {
            SecNotice entity = baseMapper.selectById(request.getnId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (Objects.equals(entity.getNState(), ApprovalStatusEnum.Already_Over.getIndex())) {
                //审批已完成
                BeanUtils.copyProperties(entity, sec);
                sec.setNDate(request.getnDate());
                sec.setNEnddate(request.getnEnddate());
                baseMapper.updateById(sec);
                return BaseResponse.ok(sec.getNId());
            }
            sec.setNCreateTime(entity.getNCreateTime());
            sec.setNCreateUid(entity.getNCreateUid());
            sec.setNCreateUname(entity.getNCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getNId(), request.gettId(), sec.getNState() + "", sec.getNCreateUid(), sec.getNCreateUname()
                    , sec.getNCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getNId());

            return BaseResponse.ok(sec.getNId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecNoticeByNid(String nId) {
        SecNotice entity = baseMapper.selectById(nId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(nId);
        sysFileService.deleteFileById(nId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecNoticeList(SecNoticeRequest request) {
        QueryMap<SecNoticeRequest> params = new QueryMap<>(request);
        params.put("nBiddingType").put("nQeType").put("nContact").put("secName").put("nState")
                .put("nBiddingAgencyName").put("nScope").put("nContactTel").put("nCreateUname")
                .put("nReceiveJointBidd").put("nDeterminType").put("nFunction")
                .put(request.getnAmounts(), "nAmountStart", "nAmountEnd")
                .put(request.getnPlandates(), "nPlandateStart", "nPlandateEnd")
                .put(request.getnPlanenddates(), "nPlanenddateStart", "nPlanenddateEnd")
                .put(request.getnCreateTimes(), "nCreateTimeStart", "nCreateTimeEnd");
        IPage<SecNoticeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SecNoticeResponse> datas = baseMapper.getSecNoticeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSecNoticeByNid(String nId) {
        SecNoticeResponse data = baseMapper.getSecNoticeByNid(nId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getSecNoticeLastApprovedBySecId(String secId) {
        SecNoticeResponse data = baseMapper.getSecNoticeLastApprovedBySecId(secId);
        return BaseResponse.ok(data, 0);
    }
}
