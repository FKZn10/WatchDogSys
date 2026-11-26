package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysParticipantUnitMapper;
import com.jpmsoft.userProducer.pojo.dto.SysParticipantUnitDto;
import com.jpmsoft.userProducer.pojo.entity.SysDictionary;
import com.jpmsoft.userProducer.pojo.entity.SysParticipantUnit;
import com.jpmsoft.userProducer.pojo.vo.VueTreeVo;
import com.jpmsoft.userProducer.service.ISysDictionaryService;
import com.jpmsoft.userProducer.service.ISysParticipantUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysParticipantUnitImpl extends ServiceImpl<SysParticipantUnitMapper, SysParticipantUnit> implements ISysParticipantUnitService {


    @Resource
    private ISysDictionaryService sysDictionaryService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createSysParticipantUnit(SysParticipantUnitDto request) {
        SysParticipantUnit entity = new SysParticipantUnit();
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<SysParticipantUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", request.getpId());
        wrapper.eq("C_Id", request.getcId());
        wrapper.eq("C_Type", request.getcType());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);
        entity.setPuId(GenerateUUID.getUUID());
        entity.setPuCreateTime(new Date());
        entity.setCId(request.getcId());
        entity.setCType(request.getcType());
        baseMapper.insert(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSysParticipantUnitList(SysParticipantUnitDto request,String token) {
        QueryMap<SysParticipantUnitDto> queryMap = new QueryMap<>(request);
        queryMap.put("pId").put("cId").put("cType").put("pName")
                .put("cName").put("puCreateUname").put("cCreditcode").put("cCode");
        List<SysParticipantUnitDto> datas = baseMapper.getSysParticipantUnitList(queryMap.getQuery());
        Integer total = baseMapper.getSysParticipantUnitListCount(queryMap.getQuery());
        List<VueTreeVo> dirs=sysDictionaryService.getDictionaryByParentCode("JPMsoft_DWLX",token);
        datas.forEach(d->{
            d.setcTypeName(getCTypeName(dirs,d.getcType()));
        });
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSysParticipantUnitDistinctList(SysParticipantUnitDto request,String token) {
        IPage<SysParticipantUnitDto> page = new Page<>(request.getPage(), request.getSize());
        List<SysParticipantUnitDto> data = baseMapper.getSysParticipantUnitDistinctList(page, request);
        List<VueTreeVo> dirs=sysDictionaryService.getDictionaryByParentCode("JPMsoft_DWLX",token);
        data.forEach(d->{
            d.setcTypeName(getCTypeName(dirs,d.getcType()));
        });
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }


    @Override
    public BaseResponse deleteSysParticipantUnitModelById(String puId) {
        SysParticipantUnit data = baseMapper.selectById(puId);
        if (data == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(puId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSysParticipantUnitByPidGroupType(String pId,String token) {
        List<SysParticipantUnitDto> data = baseMapper.getSysParticipantUnitByPidGroupType(pId);
        if (!CollectionUtils.isEmpty(data)) {
            List<VueTreeVo> dirs=sysDictionaryService.getDictionaryByParentCode("JPMsoft_DWLX",token);
            List<SysParticipantUnitDto> res = new ArrayList<>();
            List<String> types = new ArrayList<>();
            for (SysParticipantUnitDto d : data) {
                if (!types.contains(d.getcType())) {
                    d.setcTypeName(getCTypeName(dirs,d.getcType()));
                    res.add(d);
                    types.add(d.getcType());
                }
            }
            return BaseResponse.ok(res);
        }
        return BaseResponse.ok(data);
    }

    private String getCTypeName(List<VueTreeVo> dirs,String type)
    {
        if (ValidateUtil.isEmpty(type)) return null;
        if (CollectionUtils.isEmpty(dirs)) return null;
        List<VueTreeVo> dir=dirs.stream().filter(f->f.getValue().equals(type)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dir)) return null;
        return dir.stream().findFirst().get().getTitle();
    }
}
