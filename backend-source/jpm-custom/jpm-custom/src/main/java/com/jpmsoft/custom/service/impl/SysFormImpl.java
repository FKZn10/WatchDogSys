package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.custom.constants.CustomMsg;
import com.jpmsoft.custom.mapper.SysFormMapper;
import com.jpmsoft.custom.pojo.entity.SysForm;
import com.jpmsoft.custom.service.ISysFormService;
import org.springframework.stereotype.Service;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 9:08
 */
@Service
public class SysFormImpl extends ServiceImpl<SysFormMapper, SysForm> implements ISysFormService {


    @Override
    public BaseResponse getSysFormById(String id) {
        SysForm entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(CustomMsg.infoNotExist);
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse getSysFormByCode(String code, String teId) {
        QueryWrapper<SysForm> wrapper = new QueryWrapper<>();
        wrapper.eq("T_Code", code);
        wrapper.eq("Sy_State", "true");
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        return BaseResponse.ok(baseMapper.selectList(wrapper));
    }
}
