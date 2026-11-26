package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysMessageReceiveMapper;
import com.jpmsoft.userProducer.pojo.dto.MessageReceiveBathDto;
import com.jpmsoft.userProducer.pojo.dto.SysMessageReceiveDto;
import com.jpmsoft.userProducer.pojo.entity.SysMessageReceive;
import com.jpmsoft.userProducer.pojo.vo.SysMessageReceiveVo;
import com.jpmsoft.userProducer.service.ISysMessageReceiveService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:28
 */
@Service
public class SysMessageReceiveImpl extends ServiceImpl<SysMessageReceiveMapper, SysMessageReceive> implements ISysMessageReceiveService {
    @Override
    public void createMessageReceive(List<String> uIds, String smId) {
        if (!CollectionUtils.isEmpty(uIds)) {
            List<SysMessageReceive> entitys = new ArrayList<>();
            Date now = new Date();
            for (String uId : uIds) {
                SysMessageReceive entity = new SysMessageReceive();
                entity.setSmrUser(uId);
                entity.setSmId(smId);
                entity.setId(GenerateUUID.getUUID());
                entity.setSmrSendStatus("0");
                entity.setSmrSendTime(now);
                entity.setSmrRead(false);
                entitys.add(entity);
            }
            if (!CollectionUtils.isEmpty(entitys)) saveBatch(entitys);
        }
    }

    @Override
    public BaseResponse createMessageReceiveBath(MessageReceiveBathDto request) {
        createMessageReceive(request.getReceiveUserIds(), request.getSmId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse createMessageReceive(SysMessageReceiveDto request) {
        SysMessageReceive entity = new SysMessageReceive();
        entity.setSmrUser(request.getSmrUser());
        entity.setSmId(request.getSmId());
        entity.setId(GenerateUUID.getUUID());
        entity.setSmrSendStatus("0");
        entity.setSmrRead(false);
        baseMapper.insert(entity);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getMessageReceiveList(SysMessageReceiveDto request) {
        QueryMap<SysMessageReceiveDto> query = new QueryMap<>(request);
        query.put("smrUserName").put("smrUser").put("smId").put("smrSendStatus")
                .put("smrRead").put("smTitle");
        IPage<SysMessageReceiveVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysMessageReceiveVo> datas = baseMapper.getSysMessageReceiveList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse updateMessageRead(String id) {
        SysMessageReceive entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        Date now = new Date();
        entity.setSmrRead(true);
        entity.setSmrSendStatus("1");
        entity.setSmrSendTime(entity.getSmrSendTime() == null ? now : entity.getSmrSendTime());
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

}
