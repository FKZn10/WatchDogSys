package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.enums.SmsTypeEnum;
import com.jpmsoft.userProducer.mapper.SysSmsConfMapper;
import com.jpmsoft.userProducer.pojo.dto.sms.SysSmsConfDto;
import com.jpmsoft.userProducer.pojo.entity.SysSmsConf;
import com.jpmsoft.userProducer.pojo.vo.SysSmsConfVo;
import com.jpmsoft.userProducer.service.ISysSmsConfService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/6 9:53
 */
@Service
public class SysSmsConfImpl extends ServiceImpl<SysSmsConfMapper, SysSmsConf> implements ISysSmsConfService {
    @Override
    public BaseResponse createOrUpdateSmsConf(SysSmsConfDto request) {
        SysSmsConf entity = new SysSmsConf();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        } else
            baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSmsConfList(SysSmsConfDto request) {
        QueryWrapper<SysSmsConf> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getSscType()))
            wrapper.eq("Ssc_Type", request.getSscType());
        if (ValidateUtil.isNotEmpty(request.getSscPlatform()))
            wrapper.eq("Ssc_Platform", request.getSscPlatform());
        if (ValidateUtil.isNotNull(request.getSscStatus()))
            wrapper.eq("Ssc_Status", request.getSscStatus());
        if (ValidateUtil.isNotEmpty(request.getSscTempCode()))
            wrapper.like("Ssc_Temp_Code", request.getSscTempCode());
        wrapper.orderByDesc("Create_Time");
        IPage<SysSmsConf> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        List<SysSmsConfVo> datas = new ArrayList<>();
        for (SysSmsConf record : iPage.getRecords()) {
            SysSmsConfVo data = new SysSmsConfVo();
            BeanUtils.copyProperties(record, data);
            datas.add(data);
        }
        return BaseResponse.ok(datas, total.intValue());
    }

    @Override
    public BaseResponse getSmsConfById(String id) {
        SysSmsConf conf = baseMapper.selectById(id);
        if (conf != null) {
            SysSmsConfVo data = new SysSmsConfVo();
            BeanUtils.copyProperties(conf, data);
            return BaseResponse.ok(data);
        }
        return BaseResponse.ok(conf);
    }

    @Override
    public SysSmsConf getCodeSms(String tempCode) {
        QueryWrapper<SysSmsConf> wrapper = new QueryWrapper<>();
        wrapper.eq("Ssc_Status", 1);
        wrapper.eq("Ssc_Type", SmsTypeEnum.Code.getkey());
        if (!ValidateUtil.isEmpty(tempCode)) {
            wrapper.eq("Ssc_Temp_Code", tempCode);
        }
        wrapper.orderByDesc("Create_Time");
        List<SysSmsConf> datas = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(datas)) return datas.get(0);
        return null;
    }
}
