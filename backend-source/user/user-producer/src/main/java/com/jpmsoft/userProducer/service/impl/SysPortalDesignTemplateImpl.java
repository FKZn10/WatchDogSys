package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysPortalDesignTemplateMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPortalDesignTemplateDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalDesignTemplate;
import com.jpmsoft.userProducer.service.ISysPortalDesignTemplateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPortalDesignTemplateImpl  extends ServiceImpl<SysPortalDesignTemplateMapper, SysPortalDesignTemplate> implements ISysPortalDesignTemplateService {
    @Override
    public BaseResponse   GetSysPortalDesignTemplateList(SysPortalDesignTemplateDto request){
        QueryMap<SysPortalDesignTemplateDto> query = new QueryMap<>(request);
        query.put("pdName").put("pdType").put("pdState")
                .put("pdCreateNmae").put("pdCode").put("tId");
        IPage<SysPortalDesignTemplate> page = new Page<>(request.getPage(), request.getSize());
        List<SysPortalDesignTemplate> data = baseMapper.GetSysPortalDesignTemplateList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }


    @Override
    public BaseResponse   CreateOrUpdateSysPortalDesignTemplate(SysPortalDesignTemplateDto request){
        SysPortalDesignTemplate old = baseMapper.selectById(request.getId());
        SysPortalDesignTemplate entity = new SysPortalDesignTemplate();
        BeanUtils.copyProperties(request, entity);
        if (old != null) {
            baseMapper.updateById(entity);
        } else {
            //存在数据，修改
            entity.setId(GenerateUUID.getUUID());
            entity.setPdtCreateTime(new Date());
            baseMapper.insert(entity);

        }
        return BaseResponse.ok("数据保存成功");
    }



    @Override
    public BaseResponse   importSysPortalDesignTemplate(List<SysPortalDesignTemplateDto> request){
        List<String> mas = new ArrayList<>();
        if (request.size() > 0) {
            List<String> keyIds = request.stream().map(n -> n.getPdtCode()).collect(Collectors.toList());
            QueryWrapper<SysPortalDesignTemplate> wrapper = new QueryWrapper<>();
            wrapper.in("Pdt_Code", keyIds);

            List<SysPortalDesignTemplate> oldList = baseMapper.selectList(wrapper);
            for (SysPortalDesignTemplateDto obj : request) {
                List<SysPortalDesignTemplate> ls = oldList.stream().filter(n -> n.getPdtCode().equals(obj.getPdtCode())).collect(Collectors.toList());
                if (ls.size() == 0) {
                    SysPortalDesignTemplate entity = new SysPortalDesignTemplate();
                    BeanUtils.copyProperties(obj, entity);
                    entity.setPdtCreateTime(new Date());
                    baseMapper.insert(entity);
                } else {
                    mas.add(obj.getPdtName());
                }
            }
        }
        return BaseResponse.ok(mas.size() > 0 ? mas : "保存成功");
    }
}
