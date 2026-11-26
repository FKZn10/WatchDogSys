package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysSplitTableMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSplitTableDTO;
import com.jpmsoft.userProducer.pojo.entity.SysSplitTable;
import com.jpmsoft.userProducer.service.ISysSplitTableService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/3 14:19
 */
@Service
public class SysSplitTableImpl extends ServiceImpl<SysSplitTableMapper, SysSplitTable> implements ISysSplitTableService {

    @Resource
    private RedisUtils redisUtils;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createSysSplitTable(SysSplitTableDTO request) {
        if (ValidateUtil.isEmpty(request.getStCode())) return BaseResponse.fail(BaseMsg.reqCodeIsEmpty);
        QueryWrapper<SysSplitTable> wrapper = new QueryWrapper<>();
        wrapper.eq("st_code", request.getStCode());
        if (baseMapper.exists(wrapper)) return BaseResponse.fail(BaseMsg.codeAlreadyExists);
        SysSplitTable entity = new SysSplitTable();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        baseMapper.copyTable("sys_file", entity.getStCode());
        baseMapper.copyTable("Sys_Task_Processin", entity.getStCode());
        baseMapper.copyTable("Sys_Task_Process_User", entity.getStCode());
        baseMapper.copyTable("Sys_Task_Processin_Time", entity.getStCode());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteSysSplitTableById(String id) {
        SysSplitTable entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSysSplitTableList(SysSplitTableDTO request) {
        QueryWrapper<SysSplitTable> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTCode()))
            wrapper.eq("t_code", request.getTCode());
        if (ValidateUtil.isNotEmpty(request.getStTable()))
            wrapper.eq("st_table", request.getStTable());
        if (ValidateUtil.isNotEmpty(request.getStCode()))
            wrapper.eq("st_code", request.getStCode());
        if (ValidateUtil.isNotEmpty(request.getCreateUname()))
            wrapper.like("create_uname", request.getCreateUname());
        if (ValidateUtil.isNotEmpty(request.getStName()))
            wrapper.like("st_name", request.getStName());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("create_time");

        IPage<SysSplitTable> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        List<SysSplitTable> data = iPage.getRecords();
        return BaseResponse.ok(data, total.intValue());
    }

    @Override
    public BaseResponse getSysSplitTableById(String id) {
        SysSplitTable entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        SysSplitTableDTO dto = new SysSplitTableDTO();
        BeanUtils.copyProperties(entity, dto);
        return BaseResponse.ok(dto);
    }

}
