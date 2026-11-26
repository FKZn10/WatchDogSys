package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.ArchiveFileNameRuleMapper;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveFileNameRuleDTO;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveFileNameRule;
import com.jpmsoft.fileUpload.service.IArchiveFileNameRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:09
 */
@Service
@Slf4j
public class ArchiveFileNameRuleImpl extends ServiceImpl<ArchiveFileNameRuleMapper, ArchiveFileNameRule> implements IArchiveFileNameRuleService {
    @Override
    public BaseResponse createOrUpdate(ArchiveFileNameRuleDTO request) {
        ArchiveFileNameRule entity = new ArchiveFileNameRule();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            ArchiveFileNameRule old = baseMapper.selectById(request.getId());
            if (old == null) return BaseResponse.fail(FileMsg.infoNotExist);
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getArchiveFileNameRuleList(ArchiveFileNameRuleDTO request) {
        QueryWrapper<ArchiveFileNameRule> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getAfnId()))
            wrapper.eq("afn_id", request.getAfnId());
        wrapper.orderByAsc("Afnr_Sort");
        IPage<ArchiveFileNameRule> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        List<ArchiveFileNameRule> data = iPage.getRecords();
        if (!CollectionUtils.isEmpty(data))
            return BaseResponse.ok(data.stream().map(d ->
                    {
                        ArchiveFileNameRuleDTO s = new ArchiveFileNameRuleDTO();
                        BeanUtils.copyProperties(d, s);
                        return s;
                    }
            ).collect(Collectors.toList()), total.intValue());
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse deleteById(String id) {
        ArchiveFileNameRule old = baseMapper.selectById(id);
        if (old == null) return BaseResponse.fail(FileMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public List<ArchiveFileNameRule> selectRules(String afnId) {
        QueryWrapper<ArchiveFileNameRule> wrapper = new QueryWrapper<>();
        wrapper.eq("Afn_Id", afnId);
        wrapper.orderByAsc("Afnr_Sort");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void deleteByAfnId(String id) {
        QueryWrapper<ArchiveFileNameRule> wrapper = new QueryWrapper<>();
        wrapper.eq("Afn_Id", id);
        baseMapper.delete(wrapper);
    }
}
