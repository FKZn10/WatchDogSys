package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPriceResultsfCompanyModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPriceResultsfModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecPriceResultsfMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPriceResultsf;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecPriceResultsfCompanyService;
import com.jpmsoft.projectProducer.service.secInfo.ISecPriceResultsfService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SecPriceResultsfImpl extends ServiceImpl<SecPriceResultsfMapper, SecPriceResultsf> implements ISecPriceResultsfService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecPriceResultsfCompanyService secPriceResultsfCompanyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecPriceResultsf(SecPriceResultsfModel request) {
        SecPriceResultsf sec = new SecPriceResultsf();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        List<SecPriceResultsfCompanyModel> models = request.getPrCompanyModels();
        if (ValidateUtil.isEmpty(request.getSprId())) {
            sec.setSprCreateTime(new Date());
            sec.setSprId(GenerateUUID.getUUID());
            sec.setState(request.getState() == null ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            baseMapper.insert(sec);
            secPriceResultsfCompanyService.createOrUpdatePriceResultsfCompany(models, sec.getSprId());
            sysFileService.saveFile(sec.getSprId(), request.gettId(), sec.getState() + "", sec.getSprCreateUid(), sec.getSprCreateUname()
                    , sec.getSprCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getSprId());

            return BaseResponse.ok(sec.getSprId());
        } else {
            SecPriceResultsf entity = baseMapper.selectById(request.getSprId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setSprCreateTime(entity.getSprCreateTime());
            sec.setSprCreateUid(entity.getSprCreateUid());
            sec.setSprCreateUname(entity.getSprCreateUname());
            sec.setState(entity.getState());
            sec.setStateTime(entity.getStateTime());
            baseMapper.updateById(sec);
            secPriceResultsfCompanyService.createOrUpdatePriceResultsfCompany(models, sec.getSprId());
            sysFileService.saveFile(sec.getSprId(), request.gettId(), sec.getState() + "", sec.getSprCreateUid(), sec.getSprCreateUname()
                    , sec.getSprCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getSprId());

            return BaseResponse.ok(sec.getSprId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecPriceResultsfBySprId(String sprId) {
        SecPriceResultsf entity = baseMapper.selectById(sprId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (secPriceResultsfCompanyService.havingPriceResultsfCompanyBySprId(sprId))
            return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(sprId);
        sysFileService.deleteFileById(sprId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecPriceResultsfBySprId(String sprId) {
        SecPriceResultsfModel data = baseMapper.getSecPriceResultsfBySprId(sprId);
        if (data != null) {
            List<SecPriceResultsfCompanyModel> dtls = secPriceResultsfCompanyService.getPriceResultsfCompanyBySprId(sprId);
            data.setPrCompanyModels(dtls);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecPriceResultsfList(SecPriceResultsfModel request) {
        QueryMap<SecPriceResultsfModel> params = new QueryMap<>(request);
        params.put("secName").put("secType").put("secOrganizeForm").put("secBiddingType").put("secTendereeName")
                .put("secTradingPlace").put("secPlace").put("sprPrerequisites").put("sprCompanyName").put("state")
                .put(request.getSprAmts(), "sprAmtStart", "sprAmtEnd").put("sprCreateUname").put("pName")
                .put(request.getSprCreateTimes(), "sprCreateTimeStart", "sprCreateTimeEnd")
                .put(request.getSprPriceCeilings(), "sprPriceCeilingStart", "sprPriceCeilingEnd")
                .put(request.getSprConMoneys(), "sprConMoneyStart", "sprConMoneyEnd")
                .put(request.getSecStartTimes(), "secStartTimeStart", "secStartTimeEnd")
                .put(request.getSecBiddingCycles(), "secBiddingCycleStart", "secBiddingCycleEnd")
                .put("pId").put("secId");
        IPage<SecPriceResultsfModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecPriceResultsfModel> datas = baseMapper.getSecPriceResultsfList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }
}
