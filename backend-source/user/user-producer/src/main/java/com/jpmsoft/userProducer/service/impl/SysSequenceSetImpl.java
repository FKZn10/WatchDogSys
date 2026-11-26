package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysSequenceSetMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceSet;
import com.jpmsoft.userProducer.service.ISysSequenceFactory;
import com.jpmsoft.userProducer.service.ISysSequenceLogService;
import com.jpmsoft.userProducer.service.ISysSequenceSetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysSequenceSetImpl extends ServiceImpl<SysSequenceSetMapper, SysSequenceSet> implements ISysSequenceSetService {

    @Resource
    private ISysSequenceFactory sysSequenceFactory;
    @Resource
    private ISysSequenceLogService sysSequenceLogService;

    @Override
    public BaseResponse createOrUpdateSysSequenceSet(SysSequenceSetDto request) {
        QueryWrapper<SysSequenceSet> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        wrapper.eq("T_Code", request.gettCode());
        if (!ValidateUtil.isEmpty(request.getsId()))
            wrapper.ne("S_Id", request.getsId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);
        SysSequenceSet entity = new SysSequenceSet();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getsId())) {
            entity.setSId(GenerateUUID.getUUID());
            entity.setSCreateTime(new Date());
            entity.setSUpdateTime(null);
            entity.setSUpdateUid(null);
            entity.setSUpdateUname(null);
            baseMapper.insert(entity);
        } else {
            SysSequenceSet model = baseMapper.selectById(request.getsId());
            if (model == null)
                return BaseResponse.fail(BaseMsg.infoNotExist);
            entity.setSCreateTime(model.getSCreateTime());
            entity.setSCreateUid(model.getSCreateUid());
            entity.setSCreateUname(model.getSCreateUname());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getSId());
    }

    @Override
    public BaseResponse getSysSequenceSetList(SysSequenceSetDto request) {
        QueryMap<SysSequenceSetDto> queryMap = new QueryMap<>(request);
        queryMap.put("tCode").put("sName");
        List<SysSequenceSetDto> datas = baseMapper.getSysSequenceSetList(queryMap.getQuery());
        Integer total = baseMapper.getSysSequenceSetListCount(queryMap.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSysSequenceSetByTcode(String tCode, String teId) {
        SysSequenceSetDto data = baseMapper.getSysSequenceSetByTcode(tCode, teId);
        return BaseResponse.ok(data);
    }


    @Override
    public String generateCodeByTCode(String tCode, String pId, String conId, String secId, Object request, String teId) {
        SysSequenceSetDto data = baseMapper.getSysSequenceSetByTcode(tCode, teId);
        if (data == null)
            return null;
        else
            return sysSequenceFactory.getCode(data, pId, conId, secId, request, false);
    }

    @Override
    public BaseResponse refershAutoNo(String tCode, Object request, String teId) {
        if (request == null)
            return null;
        Map<String, Object> map = JacksonUtils.parseObject(JacksonUtils.toJSONString(request), Map.class);
        SysSequenceSetDto data = baseMapper.getSysSequenceSetByTcode(tCode, teId);
        if (data == null)
            return null;
        else {
            String code = sysSequenceFactory.getCode(data, map.get("pId") == null ? null : map.get("pId").toString(),
                    map.get("conId") == null ? null : map.get("conId").toString(),
                    map.get("secId") == null ? null : map.get("secId").toString(),
                    request, true);
            sysSequenceLogService.createSequenceLog(code, tCode, request);
        }
        //Map<String, Object> map = JacksonUtils.parseObject(JacksonUtils.toJSONString(request), Map.class);
        //SysSequenceSetDto data = baseMapper.getSysSequenceSetByTcode(tCode);
        //if (data == null)
        //    return null;
        //else {
        //    List<SysSequenceSetDtlDto> model = data.getSysSequenceSetDtls();
        //    sysSequenceFactory.sequenceRest(data, map.get("pId") == null ? null : map.get("pId").toString(),
        //            map.get("conId") == null ? null : map.get("conId").toString(),
        //            map.get("secId") == null ? null : map.get("secId").toString(), model, request, true);
        //}
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse refershAutoNoByCustomized(String tCode, String pId, String conId, String secId, Object request, String teId) {
        SysSequenceSetDto data = baseMapper.getSysSequenceSetByTcode(tCode, teId);
        if (data == null)
            return null;
        else {
            String code = sysSequenceFactory.getCode(data, pId, conId, secId, request, true);
            sysSequenceLogService.createSequenceLog(code, tCode, request);
        }
        return BaseResponse.ok();
    }

    @Override
    public String generateCodeByTCode(String tCode, Object request, String teId) {
        if (request == null)
            return null;
        Map<String, Object> map = JacksonUtils.parseObject(JacksonUtils.toJSONString(request), Map.class);
        SysSequenceSetDto data = baseMapper.getSysSequenceSetByTcode(tCode, teId);
        if (data == null)
            return null;
        else
            return sysSequenceFactory.getCode(data, map.get("pId") == null ? null : map.get("pId").toString(),
                    map.get("conId") == null ? null : map.get("conId").toString(),
                    map.get("secId") == null ? null : map.get("secId").toString(),
                    request, false);
    }


    @Override
    public Boolean sequenceRestEnable(String tCode, String teId) {
        SysSequenceSetDto data = baseMapper.getSysSequenceSetByTcode(tCode, teId);
        if (data != null)
            return data.getsEnable();
        else
            return false;
    }

    @Override
    public int updateEntity(SysSequenceSet entity) {
        return baseMapper.updateById(entity);
    }


}
