package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceMemberModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceMemberMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceMember;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OaAttendanceMemberImpl extends ServiceImpl<OaAttendanceMemberMapper, OaAttendanceMember> implements IOaAttendanceMemberService {

    @Override
    public BaseResponse createOrUpdateAttendanceMember(OaAttendanceMemberModel request) {
        OaAttendanceMember entity = new OaAttendanceMember();
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<OaAttendanceMember> wrapper = new QueryWrapper<>();
        wrapper.eq("Am_Uid", request.getAmUid());
        if (!ValidateUtil.isEmpty(entity.getAmId()))
            wrapper.ne("Am_Id", request.getAmId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.oaAmUserExists);

        if (ValidateUtil.isEmpty(entity.getAmId())) {
            entity.setAmId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            OaAttendanceMember model = baseMapper.selectById(entity.getAmId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getAmId());
    }

    @Override
    public BaseResponse createOrUpdateAttendanceMemberByBath(List<OaAttendanceMemberModel> request,String teId) {
        if (request.size() < 1) return BaseResponse.ok();
        List<String> agIds = request.stream().map(OaAttendanceMemberModel::getAgId).distinct().collect(Collectors.toList());
        delAttendanceMemberByAgIds(agIds);
        List<OaAttendanceMember> entitys = request.stream().map(data -> {
            OaAttendanceMember entity = new OaAttendanceMember();
            BeanUtils.copyProperties(data, entity);
            entity.setAmId(ValidateUtil.isEmpty(data.getAmId()) ? GenerateUUID.getUUID() : data.getAmId());
            entity.setTeId(teId);
            return entity;
        }).collect(Collectors.toList());
        saveBatch(entitys);
        return BaseResponse.ok();
    }


    private List<OaAttendanceMember> getAllByUids(List<String> userIds) {
        if (userIds == null)
            return new ArrayList<>();
        if (userIds.size() < 1)
            return new ArrayList<>();
        QueryWrapper<OaAttendanceMember> wrapper = new QueryWrapper<>();
        wrapper.eq("Am_Uid", userIds.get(0));
        if (userIds.size() > 1)
            userIds.forEach(id -> {
                wrapper.or().eq("Am_Uid", id);
            });
        List<OaAttendanceMember> datas = baseMapper.selectList(wrapper);
        return datas;
    }

    @Override
    public BaseResponse getAttendanceMemberList(OaAttendanceMemberModel request) {
        QueryMap<OaAttendanceMemberModel> params = new QueryMap<>(request);
        params.put("agName").put("amUname").put("agId");
        List<OaAttendanceMemberModel> datas = baseMapper.getAttendanceMemberList(params.getQuery());
        Integer total = baseMapper.getAttendanceMemberListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public void delAttendanceMemberByAgIds(List<String> agIds) {
        QueryWrapper<OaAttendanceMember> wrapper = new QueryWrapper<>();
        wrapper.in("Ag_Id", agIds);
        baseMapper.delete(wrapper);
    }
}
