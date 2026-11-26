package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysPortalDesignMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPortalDesignDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalDesign;
import com.jpmsoft.userProducer.service.ISysPortalDesignService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPortalDesignImpl extends ServiceImpl<SysPortalDesignMapper, SysPortalDesign> implements ISysPortalDesignService {
    @Override
    public BaseResponse GetSysPortalDesignList(SysPortalDesignDto request) {
        QueryMap<SysPortalDesignDto> query = new QueryMap<>(request);
        query.put("pdName").put("pdType").put("pdState")
                .put("pdCreateNmae").put("pdCode").put("tId");
        IPage<SysPortalDesign> page = new Page<>(request.getPage(), request.getSize());
        List<SysPortalDesign> data = baseMapper.GetSysPortalDesignList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }


    @Override
    public BaseResponse CreateOrUpdateSysPortalDesign(SysPortalDesignDto request) {
        SysPortalDesign old = baseMapper.selectById(request.getId());
        SysPortalDesign entity = new SysPortalDesign();
        BeanUtils.copyProperties(request, entity);
        if (old != null) {
            baseMapper.updateById(entity);
        } else {
            //存在数据，修改
            entity.setId(GenerateUUID.getUUID());
            entity.setPdCreateTime(new Date());
            baseMapper.insert(entity);

        }
        return BaseResponse.ok("数据保存成功");
    }

    @Override
    public BaseResponse importSysPortalDesign(List<SysPortalDesignDto> request) {
        List<String> mas = new ArrayList<>();
        if (request.size() > 0) {
            List<String> keyIds = request.stream().map(n -> n.getPdCode()).collect(Collectors.toList());
            QueryWrapper<SysPortalDesign> wrapper = new QueryWrapper<>();
            wrapper.in("Pd_Code", keyIds);

            List<SysPortalDesign> oldList = baseMapper.selectList(wrapper);
            for (SysPortalDesignDto obj : request) {
                List<SysPortalDesign> ls = oldList.stream().filter(n -> n.getPdCode().equals(obj.getPdCode())).collect(Collectors.toList());
                if (ls.size() == 0) {
                    SysPortalDesign entity = new SysPortalDesign();
                    BeanUtils.copyProperties(obj, entity);
                    entity.setPdCreateTime(new Date());
                    baseMapper.insert(entity);
                } else {
                    mas.add(obj.getPdName());
                }
            }
        }
        return BaseResponse.ok(mas.size() > 0 ? mas : "保存成功");
    }
}
