package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceGroupModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceGroupMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceGroup;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceGroupService;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceWeekdayService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OaAttendanceGroupImpl extends ServiceImpl<OaAttendanceGroupMapper, OaAttendanceGroup> implements IOaAttendanceGroupService {

    @Resource
    private IOaAttendanceWeekdayService oaAttendanceWeekdayService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAttendanceGroup(OaAttendanceGroupModel request) {
        OaAttendanceGroup entity = new OaAttendanceGroup();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAgId())) {
            entity.setAgId(GenerateUUID.getUUID());
            entity.setAgCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            OaAttendanceGroup model = baseMapper.selectById(entity.getAgId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setAgCreateTime(model.getAgCreateTime());
            entity.setAgCreateUid(model.getAgCreateUid());
            entity.setAgCreateUname(model.getAgCreateUname());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getAgId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAttendanceGroupAndWeekday(OaAttendanceGroupModel request) {
        OaAttendanceGroup entity = new OaAttendanceGroup();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAgId())) {
            entity.setAgId(GenerateUUID.getUUID());
            entity.setAgCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            OaAttendanceGroup model = baseMapper.selectById(entity.getAgId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setAgCreateTime(model.getAgCreateTime());
            entity.setAgCreateUid(model.getAgCreateUid());
            entity.setAgCreateUname(model.getAgCreateUname());
            baseMapper.updateById(entity);
        }
        oaAttendanceWeekdayService.deleteThenToCreateWeekday(request.getAttendanceWeekdays(), entity.getAgId(), entity.getTeId());
        return BaseResponse.ok(entity.getAgId());
    }

    @Override
    public BaseResponse getAttendanceGroupList(OaAttendanceGroupModel request) {
        QueryMap<OaAttendanceGroupModel> params = new QueryMap<>(request);
        params.put("pName").put("agName").put("agScope").put("agState").put("pId");
        IPage<OaAttendanceGroupModel> page = new Page<>(request.getPage(), request.getSize());
        List<OaAttendanceGroupModel> datas = baseMapper.getAttendanceGroupList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getAttendanceGroupById(String agId) {
        OaAttendanceGroupModel datas = baseMapper.getAttendanceGroupById(agId);
        return BaseResponse.ok(datas);
    }
}
