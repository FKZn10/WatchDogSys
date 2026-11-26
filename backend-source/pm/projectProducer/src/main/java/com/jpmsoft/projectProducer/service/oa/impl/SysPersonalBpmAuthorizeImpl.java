package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Oa.SysPersonalBpmAuthorizeModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.SysPersonalBpmAuthorizeMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.SysPersonalBpmAuthorize;
import com.jpmsoft.projectProducer.service.oa.ISysPersonalBpmAuthorizeService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysPersonalBpmAuthorizeImpl extends ServiceImpl<SysPersonalBpmAuthorizeMapper, SysPersonalBpmAuthorize> implements ISysPersonalBpmAuthorizeService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePersonalBpmAuthorize(SysPersonalBpmAuthorizeModel request, String uid) {
        if (ValidateUtil.isNotEmpty(request.getBaCreateUid()) && !request.getBaCreateUid().equals(uid))
            throw new JPMException("创建人变更，无权操作！");
        SysPersonalBpmAuthorize entity = new SysPersonalBpmAuthorize();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getBaId())) {
            entity.setBaId(GenerateUUID.getUUID());
            entity.setBaCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getBaId(), request.gettId(), entity.getBaState() + "", entity.getBaCreateUid(), entity.getBaCreateUname()
                    , entity.getBaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getBaId());

        } else {
            SysPersonalBpmAuthorize model = baseMapper.selectById(entity.getBaId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setBaCreateTime(model.getBaCreateTime());
            entity.setBaStartTime(model.getBaStartTime());
            entity.setBaState(model.getBaState());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getBaId(), request.gettId(), entity.getBaState() + "", entity.getBaCreateUid(), entity.getBaCreateUname()
                    , entity.getBaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getBaId());

        }
        return BaseResponse.ok(entity.getBaId());
    }

    @Override
    public BaseResponse getSelfPersonalBpmAuthorizeList(SysPersonalBpmAuthorizeModel request, String uId) {
        Map<String, Object> params = getQuery(request, uId);
        List<SysPersonalBpmAuthorizeModel> data = baseMapper.getPersonalBpmAuthorizeList(params);
        Integer total = baseMapper.getPersonalBpmAuthorizeListCount(params);
        return BaseResponse.ok(data, total);
    }

    @Override
    public BaseResponse getPersonalBpmAuthorizeList(SysPersonalBpmAuthorizeModel request) {
        Map<String, Object> params = getQuery(request, null);
        List<SysPersonalBpmAuthorizeModel> data = baseMapper.getPersonalBpmAuthorizeList(params);
        Integer total = baseMapper.getPersonalBpmAuthorizeListCount(params);
        return BaseResponse.ok(data, total);
    }


    private Map<String, Object> getQuery(SysPersonalBpmAuthorizeModel request, String uId) {
        QueryMap<SysPersonalBpmAuthorizeModel> query = new QueryMap<>(request);
        query.put("currentUid", uId).put("baAuthorizerUname").put("baPromoter")
                .put("baState").put("baTcode").put("baRemark").put("baCreateUname")
                .put("baCreateTime", DateUtil.Date2String(request.getBaCreateTime(), "yyyy-MM-dd HH:mm:ss"))
                .put("baStartTime", DateUtil.Date2String(request.getBaStartTime(), "yyyy-MM-dd HH:mm:ss"))
                .put("baEndTime", DateUtil.Date2String(request.getBaEndTime(), "yyyy-MM-dd HH:mm:ss"));
        return query.getQuery();
    }


    @Override
    public BaseResponse getPersonalBpmAuthorizeById(String baId) {
        SysPersonalBpmAuthorizeModel data = baseMapper.getPersonalBpmAuthorizeById(baId);
        return BaseResponse.ok(data);
    }
}
