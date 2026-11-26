package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceWeekdayModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceWeekdayMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceWeekday;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceWeekdayService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OaAttendanceWeekdayImpl extends ServiceImpl<OaAttendanceWeekdayMapper, OaAttendanceWeekday> implements IOaAttendanceWeekdayService {
    @Override
    public BaseResponse createOrUpdateAttendanceWeekday(OaAttendanceWeekdayModel request) {
        OaAttendanceWeekday entity = new OaAttendanceWeekday();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAwId())) {
            entity.setAwId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            OaAttendanceWeekday model = baseMapper.selectById(entity.getAwId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getAwId());
    }

    @Override
    public BaseResponse getAttendanceWeekdayList(OaAttendanceWeekdayModel request) {
        QueryMap<OaAttendanceWeekdayModel> params = new QueryMap<>(request);
        params.put("agName").put("awWeek");
        List<OaAttendanceWeekdayModel> datas = baseMapper.getAttendanceWeekdayList(params.getQuery());
        Integer total = baseMapper.getAttendanceWeekdayListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public void deleteThenToCreateWeekday(List<OaAttendanceWeekdayModel> models, String agId, String teId) {
        QueryWrapper<OaAttendanceWeekday> wrapper = new QueryWrapper<>();
        wrapper.eq("Ag_Id", agId);
        baseMapper.delete(wrapper);
        List<OaAttendanceWeekday> entitys = new ArrayList<>();
        models.forEach(model -> {
            OaAttendanceWeekday entity = new OaAttendanceWeekday();
            BeanUtils.copyProperties(model, entity);
            entity.setAwId(GenerateUUID.getUUID());
            entity.setAgId(agId);
            entity.setTeId(teId);
            entitys.add(entity);
        });
        if (entitys.size() > 0)
            saveBatch(entitys);
    }
}
