package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.SysFileTemplateApiFieldDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.jpbpm.mapper.SysFileTemplateApiFieldMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysFileTemplateApiField;
import com.jpmsoft.jpbpm.service.SysFileTemplateApiFieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysFileTemplateApiFieldImpl extends ServiceImpl<SysFileTemplateApiFieldMapper, SysFileTemplateApiField> implements SysFileTemplateApiFieldService {
    @Override
    public BaseResponse createOrUpdateFileTemplateApiField(List<SysFileTemplateApiFieldDto> fields, String tpId) {
        deleteFileTemplateApiFieldBytpId(tpId);
        if (fields != null && fields.size() > 0) {
            List<SysFileTemplateApiField> entitys = new ArrayList<>();
            fields.forEach(model -> {
                SysFileTemplateApiField entity = new SysFileTemplateApiField();
                BeanUtils.copyProperties(model, entity);
                entity.setTpId(tpId);
                entity.setFieldId(GenerateUUID.getUUID());
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
        return null;
    }

    @Override
    public Boolean can2DelFileTemplatePrint(String tpId) {
        QueryWrapper<SysFileTemplateApiField> wrapper = new QueryWrapper<>();
        wrapper.eq("TP_ID", tpId);
        if (baseMapper.selectCount(wrapper) > 0)
            return false;
        else
            return true;
    }


    private void deleteFileTemplateApiFieldBytpId(String tpId) {
        QueryWrapper<SysFileTemplateApiField> wrapper = new QueryWrapper<>();
        wrapper.eq("TP_ID", tpId);
        baseMapper.delete(wrapper);
    }
}
