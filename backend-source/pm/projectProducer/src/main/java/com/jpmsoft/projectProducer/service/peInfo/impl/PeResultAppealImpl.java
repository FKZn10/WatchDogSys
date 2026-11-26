package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.PeInfo.PeResultAppealRequest;
import com.jpmsoft.common.model.response.PeResultAppealResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeResultAppealMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeResultAppeal;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.peInfo.IPeResultAppealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PeResultAppealImpl extends ServiceImpl<PeResultAppealMapper, PeResultAppeal> implements IPeResultAppealService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateResultAppeal(PeResultAppealRequest request) {
        if (ValidateUtil.isEmpty(request.getrId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PeResultAppeal entity = new PeResultAppeal();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getRaId())) {
            entity.setRaCreateTime(new Date());
            entity.setRaId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            return BaseResponse.ok(entity.getRaId());
        } else {
            PeResultAppeal pojo = baseMapper.selectById(request.getRaId());
            if (pojo == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setRaCreateUid(pojo.getRaCreateUid());
            entity.setRaCreateUname(pojo.getRaCreateUname());
            entity.setRaCreateTime(pojo.getRaCreateTime());
            baseMapper.updateById(entity);
            return BaseResponse.ok(entity.getRaId());
        }
    }

    @Override
    public BaseResponse getResultAppealList(PeResultAppealRequest request, String uId) {
        if (sysUserService.checkIsOutUser(uId))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<PeResultAppealRequest> params = new QueryMap<>(request);
        params.put("pId");
        List<PeResultAppealResponse> datas = baseMapper.getResultAppealList(params.getQuery());
        Integer total = baseMapper.getResultAppealListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse deleteResultAppealById(PeResultAppealRequest request) {
        if (ValidateUtil.isEmpty(request.getRaId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PeResultAppeal pojo = baseMapper.selectById(request.getRaId());
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(request.getRaId());
        return BaseResponse.ok();
    }
}
