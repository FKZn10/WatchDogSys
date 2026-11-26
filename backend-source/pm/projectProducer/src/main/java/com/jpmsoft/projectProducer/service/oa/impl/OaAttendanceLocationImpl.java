package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLocationModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceLocationMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceLocation;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceLocationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OaAttendanceLocationImpl extends ServiceImpl<OaAttendanceLocationMapper, OaAttendanceLocation> implements IOaAttendanceLocationService {

    @Override
    public BaseResponse createOrUpdateOaAttendanceLocation(OaAttendanceLocationModel request) {
        OaAttendanceLocation entity = new OaAttendanceLocation();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAlId())) {
            entity.setAlId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            OaAttendanceLocation model = baseMapper.selectById(entity.getAlId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getAlId());
    }

    @Override
    public BaseResponse deleteOaAttendanceLocationById(String alId) {
        OaAttendanceLocation model = baseMapper.selectById(alId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(alId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse getOaAttendanceLocationList(OaAttendanceLocationModel request) {
        QueryMap<OaAttendanceLocationModel> params = new QueryMap<>(request);
        params.put("agName").put("agId").put("alName").put("alCreateUname");
        List<OaAttendanceLocationModel> datas = baseMapper.getOaAttendanceLocationList(params.getQuery());
        Integer total = baseMapper.getOaAttendanceLocationListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }
}
