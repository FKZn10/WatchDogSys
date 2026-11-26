package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.fileUpload.mapper.SysFileTemplatePrintMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplatePrint;
import com.jpmsoft.fileUpload.service.ISysFileTemplatePrintService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/12 9:32
 */
@Service
public class SysFileTemplatePrintImpl extends ServiceImpl<SysFileTemplatePrintMapper, SysFileTemplatePrint> implements ISysFileTemplatePrintService {
    @Override
    public List<SysFileTemplatePrint> getJiMuPrint(List<String> tIds) {
        QueryWrapper<SysFileTemplatePrint> wrapper = new QueryWrapper<>();
        wrapper.eq("Tp_State", 1);
        wrapper.eq("Tp_Type", "jimuReport");
        wrapper.in("T_Id", tIds);
        return baseMapper.selectList(wrapper);
    }
}
