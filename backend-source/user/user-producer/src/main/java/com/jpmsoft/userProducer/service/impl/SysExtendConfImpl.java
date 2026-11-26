package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysExtendConfDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysExtendConfMapper;
import com.jpmsoft.userProducer.pojo.entity.SysExtendConf;
import com.jpmsoft.userProducer.service.IFileTemplateService;
import com.jpmsoft.userProducer.service.ISysExtendConfService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/10 11:12
 */
@Service
public class SysExtendConfImpl extends ServiceImpl<SysExtendConfMapper, SysExtendConf> implements ISysExtendConfService {

    @Resource
    private IFileTemplateService fileTemplateService;


    @Override
    public void createOrUpdateSysExtendConf(List<SysExtendConfDTO> extendConfs, String tId) {
        deleteSysExtendConfByTid(tId);
        if (!CollectionUtils.isEmpty(extendConfs)) {
            List<SysExtendConf> entities = extendConfs.stream().map(data -> {
                SysExtendConf entity = new SysExtendConf();
                BeanUtils.copyProperties(data, entity);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                entity.setTId(tId);
                return entity;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public BaseResponse getSysExtendConfByTid(String tId) {
        if (ValidateUtil.isNotEmpty(tId)) {
            QueryWrapper<SysExtendConf> wrapper = new QueryWrapper<>();
            wrapper.eq("T_ID", tId);
            wrapper.orderByAsc("Ec_Sort");
            List<SysExtendConf> data = baseMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(data)) {
                List<SysExtendConfDTO> res = data.stream().map(d -> {
                    SysExtendConfDTO f = new SysExtendConfDTO();
                    BeanUtils.copyProperties(d, f);
                    return f;
                }).collect(Collectors.toList());
                return BaseResponse.ok(res);
            }
        }
        return BaseResponse.ok(null);
    }

    @Override
    public BaseResponse getSysExtendConfByTCode(String code,String token) {
        return getSysExtendConfByTid(fileTemplateService.getFileTmpIdByCode(code,token));
    }

    @Override
    public BaseResponse getSysExtendConfByTCodes(List<String> codes) {
        List<SysExtendConfDTO> data = baseMapper.getSysExtendConfByTCodes(codes);
        Map<String, List<SysExtendConfDTO>> map = new HashMap<>();
        for (String code : codes)
            map.put(code, data.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTCode()) && n.getTCode().equals(code)).collect(Collectors.toList()));
        return BaseResponse.ok(map);
    }

    @Override
    public List<SysExtendConfDTO> getAllSysExtendConf() {
        QueryWrapper<SysExtendConf> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("Ec_Sort");
        List<SysExtendConf> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            return data.stream().map(d -> {
                SysExtendConfDTO f = new SysExtendConfDTO();
                BeanUtils.copyProperties(d, f);
                return f;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void deleteSysExtendConfByTid(String tId) {
        QueryWrapper<SysExtendConf> wrapper = new QueryWrapper<>();
        wrapper.eq("T_ID", tId);
        baseMapper.delete(wrapper);
    }
}
