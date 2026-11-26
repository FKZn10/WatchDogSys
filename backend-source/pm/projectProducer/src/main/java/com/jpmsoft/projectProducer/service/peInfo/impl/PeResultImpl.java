package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.PeInfo.PeResultRequest;
import com.jpmsoft.common.model.response.PeResultResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.DeliveryStatusEnum;
import com.jpmsoft.projectProducer.mapper.peInfo.PeResultMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeResult;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoDetailService;
import com.jpmsoft.projectProducer.service.peInfo.IPeResultService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeResultImpl extends ServiceImpl<PeResultMapper, PeResult> implements IPeResultService {


    @Resource
    private IPeInfoDetailService peInfoDetailService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    public void insertPeResult(String peId) {
        QueryWrapper<PeResult> wrapper = new QueryWrapper<>();
        wrapper.eq("Pe_Id", peId);
        if (baseMapper.selectCount(wrapper) > 0)
            return;
        PeResult entity = new PeResult();
        entity.setPeId(peId);
        entity.setrId(GenerateUUID.getUUID());
        entity.setrDeliveryStatus(DeliveryStatusEnum.Already.getIndex());
        entity.setrDeliveryTime(new Date());
        entity.setrSignState(ApprovalStatusEnum.Not_Started.getIndex());
        baseMapper.insert(entity);
    }

    @Override
    public BaseResponse signPeResult(PeResultRequest request) {
        PeResult entity = baseMapper.selectById(request.getrId());
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.nonModifyState(entity.getrSignState()))
            return BaseResponse.fail(ResMsg.stateNotModify);
        entity.setrSignState(request.getrSignState());
        entity.setrSignTime(new Date());
        entity.setrSignContent(request.getrSignContent());
        entity.setrSignUid(request.getrSignUid());
        entity.setrSignUname(request.getrSignUname());
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPeResultList(PeResultRequest request, String uId) {
        //根据考核评分做鉴权
        String authSql = sysUserService.getAuthSql("Pe_Result", uId, "pe.R_Sign_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", authSql);
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("pId", ValidateUtil.isEmpty(request.getpId()) ? null : request.getpId());
        params.put("conName", ValidateUtil.isEmpty(request.getConName()) ? null : request.getConName());
        params.put("rSignContent", ValidateUtil.isEmpty(request.getrSignContent()) ? null : request.getrSignContent());
        params.put("rDeliveryStatus", ValidateUtil.isEmpty(request.getrDeliveryStatus()) ? null : request.getrDeliveryStatus());
        params.put("rSignState", ValidateUtil.isEmpty(request.getrSignState()) ? null : request.getrSignState());
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? null : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? null : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        List<PeResultResponse> datas = baseMapper.getPeResultList(params);
        Integer total = baseMapper.getPeResultListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getPeResultByRid(PeResultRequest request) {
        if (ValidateUtil.isEmpty(request.getrId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PeResultResponse response = baseMapper.getPeResultByRid(request.getrId());
        if (response == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        response.setPeInfoDetails(peInfoDetailService.getPeInfoDetailByPeId(response.getPeId()));
        return BaseResponse.ok(response);
    }
}
