package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.PeInfo.PeMeetingConfirmBackRequest;
import com.jpmsoft.common.model.response.PeMeetingConfirmBackResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeMeetingConfirmBackMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMeetingConfirmBack;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoCollectDtlService;
import com.jpmsoft.projectProducer.service.peInfo.IPeMeetingConfirmBackService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeMeetingConfirmBackImpl extends ServiceImpl<PeMeetingConfirmBackMapper, PeMeetingConfirmBack> implements IPeMeetingConfirmBackService {

    @Resource
    private IPeInfoCollectDtlService peInfoCollectDtlService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse meetingConfirmBack(PeMeetingConfirmBackRequest request) {
        if (ValidateUtil.isAnObjEmpty(request.getPeId(), request.getBackUid()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PeMeetingConfirmBack entity = new PeMeetingConfirmBack();
        BeanUtils.copyProperties(request, entity);
        entity.setBackId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        peInfoCollectDtlService.deleteByPeId(entity.getPeId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getMeetingConfirmBackList(PeMeetingConfirmBackRequest request, String uId) {
        //根据考核评分做鉴权
        String authSql = sysUserService.getAuthSql("Pe_MeetingConfirm_Back", uId, "b.Back_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", authSql);
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("backType", ValidateUtil.isEmpty(request.getBackType()) ? null : request.getBackType());
        params.put("pId", ValidateUtil.isEmpty(request.getpId()) ? null : request.getpId());
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? null : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? null : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        List<PeMeetingConfirmBackResponse> data = baseMapper.getMeetingConfirmBackList(params);
        Integer total = baseMapper.getMeetingConfirmBackListCount(params);
        return BaseResponse.ok(data, total);
    }

}
