package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendance;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OaAttendanceImpl extends ServiceImpl<OaAttendanceMapper, OaAttendance> implements IOaAttendanceService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAttendance(OaAttendanceModel request) {
        OaAttendance entity = new OaAttendance();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAId())) {
            entity.setAId(GenerateUUID.getUUID());
            entity.setACreateTime(new Date());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(request.getaPic(), entity.getAId());

        } else {
            OaAttendance model = baseMapper.selectById(entity.getAId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setACreateTime(model.getACreateTime());
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(request.getaPic(), request.getaPicIds(), entity.getAId());

        }
        return BaseResponse.ok(entity.getAId());
    }

    @Override
    public BaseResponse getAttendanceList(OaAttendanceModel request) {
        QueryMap<OaAttendanceModel> params = new QueryMap<>(request);
        params.put("agName").put("aUname").put("cId").put("aResult").put("aWay")
                .put("aWeek").put("aType").put("aUid").put("aCreateTime");
        List<OaAttendanceModel> datas = baseMapper.getAttendanceList(params.getQuery());
        Integer total = baseMapper.getAttendanceListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }
}
