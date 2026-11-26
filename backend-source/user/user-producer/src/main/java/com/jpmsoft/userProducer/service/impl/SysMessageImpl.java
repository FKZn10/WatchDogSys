package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysMessageMapper;
import com.jpmsoft.userProducer.pojo.dto.SysMessageDto;
import com.jpmsoft.userProducer.pojo.entity.SysMessage;
import com.jpmsoft.userProducer.pojo.vo.SysMessageVo;
import com.jpmsoft.userProducer.service.ISysMessageReceiveService;
import com.jpmsoft.userProducer.service.ISysMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:29
 */
@Service
public class SysMessageImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {
    @Resource
    private ISysMessageReceiveService sysMessageReceiveService;

    @Override
    public BaseResponse createSysMessage(SysMessageDto request) {
        SysMessage entity = new SysMessage();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        entity.setId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        sysMessageReceiveService.createMessageReceive(request.getReceiveUserIds(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSysMessageList(SysMessageDto request) {
        QueryMap<SysMessageDto> query = new QueryMap<>(request);
        query.put("createUname").put("smTitle").put("smType").put("smSendUserName")
                .put("smContent").put("smtCode").put("smSendType");
        IPage<SysMessageVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysMessageVo> datas = baseMapper.getSysMessageList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSysMessageById(String id) {
        return BaseResponse.ok(baseMapper.getSysMessageById(id));
    }

}
