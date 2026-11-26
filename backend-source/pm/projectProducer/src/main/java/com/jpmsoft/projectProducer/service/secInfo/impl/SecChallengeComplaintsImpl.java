package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecChallengeComplaintsRequest;
import com.jpmsoft.common.model.response.SecInfo.SecChallengeComplaintsResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecChallengeComplaintsMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecChallengeComplaints;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecChallengeComplaintsService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.LogicUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SecChallengeComplaintsImpl extends ServiceImpl<SecChallengeComplaintsMapper, SecChallengeComplaints> implements ISecChallengeComplaintsService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecChallengeComplaints(SecChallengeComplaintsRequest request) {
        SecChallengeComplaints sec = new SecChallengeComplaints();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);

        if (ValidateUtil.isEmpty(request.getpId()) && ValidateUtil.isNotEmpty(request.getSecProjects()) && request.getSecProjects().size() == 1) {
            request.setpId(request.getSecProjects().get(0).getpId());
        }
        if (ValidateUtil.isEmpty(request.getSccId())) {
            sec.setSccCreateTime(new Date());
            sec.setSccId(GenerateUUID.getUUID());
            sec.setSccState(ValidateUtil.isEmpty(request.getSccState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getSccState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getSccId(), request.gettId(), sec.getSccState() + "", sec.getSccCreateUid(), sec.getSccCreateUname()
                    , sec.getSccCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(),request.getSccHandleDoc()), sec.getSccId());

            return BaseResponse.ok(sec.getSccId());
        } else {
            SecChallengeComplaints entity = baseMapper.selectById(request.getSccId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getSccState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setSccCreateTime(entity.getSccCreateTime());
            sec.setSccCreateUid(entity.getSccCreateUid());
            sec.setSccCreateUname(entity.getSccCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getSccId(), request.gettId(), sec.getSccState() + "", sec.getSccCreateUid(), sec.getSccCreateUname()
                    , sec.getSccCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(),request.getSccHandleDoc()),Arrays.asList( request.getFileIds(),request.getSccHandleDocFileIds()), sec.getSccId());


            return BaseResponse.ok(sec.getSccId());
        }
    }

    @Override
    public BaseResponse getSecChallengeComplaintsList(SecChallengeComplaintsRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecChallengeComplaintsResponse> datas = baseMapper.getSecChallengeComplaintsList(params);
        Integer total = baseMapper.getSecChallengeComplaintsListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecChallengeComplaintsById(String beeId) {
        SecChallengeComplaintsResponse data = baseMapper.getSecChallengeComplaintsById(beeId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getSecChallengeComplaintsListOne2Many(SecChallengeComplaintsRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecChallengeComplaintsResponse> datas = baseMapper.getSecChallengeComplaintsListOne2Many(params);
        Integer total = baseMapper.getSecChallengeComplaintsListOne2ManyCount(params);
        return BaseResponse.ok(datas, total);
    }

    private Map<String, Object> getFilterMap(SecChallengeComplaintsRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "Scc_Create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);

        params.put("sccQuestion", ValidateUtil.isEmpty(request.getSccQuestion()) ? null : request.getSccQuestion());
        params.put("secId", ValidateUtil.isEmpty(request.getSecId()) ? null : request.getSecId());
        params.put("secName", ValidateUtil.isEmpty(request.getSecName()) ? null : request.getSecName());
        params.put("sccQuestionerTelephone", ValidateUtil.isEmpty(request.getSccQuestionerTelephone()) ? null : request.getSccQuestionerTelephone());
        params.put("sccQuestionQuestion", ValidateUtil.isEmpty(request.getSccQuestionQuestion()) ? null : request.getSccQuestionQuestion());
        params.put("sccRespondent", ValidateUtil.isEmpty(request.getSccRespondent()) ? null : request.getSccRespondent());
        params.put("sccReplyContent", ValidateUtil.isEmpty(request.getSccReplyContent()) ? null : request.getSccReplyContent());
        params.put("sccRemarks", ValidateUtil.isEmpty(request.getSccRemarks()) ? null : request.getSccRemarks());
        params.put("pName", ValidateUtil.isEmpty(request.getpName()) ? null : request.getpName());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("sccCreateUname", ValidateUtil.isEmpty(request.getSccCreateUname()) ? null : request.getSccCreateUname());
        params.put("secBiddingType", ValidateUtil.isEmpty(request.getSecBiddingType()) ? null : request.getSecBiddingType());
        LogicUtil.putParamToMap(params, request.getSccCreateTimes(), "sccCreateTimeStart", "sccCreateTimeEnd");
        return params;
    }
}
