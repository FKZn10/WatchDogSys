package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SysQuestionSumMapper;
import com.jpmsoft.fileUpload.pojo.dto.SysAnswerLibDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysQuestionSum;
import com.jpmsoft.fileUpload.service.ISysQuestionSumService;
import org.springframework.stereotype.Service;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 13:37
 */
@Service
public class SysQuestionSumImpl extends ServiceImpl<SysQuestionSumMapper, SysQuestionSum> implements ISysQuestionSumService {


    @Override
    public void createQuestionSum(SysAnswerLibDTO request, Boolean question) {
        if (ValidateUtil.isAllEmpty(request.getAlKeys(), request.getProblem())) return;
        SysQuestionSum entity = new SysQuestionSum();
        entity.setId(GenerateUUID.getUUID());
        entity.setCreateUname(request.getCreateUname());
        entity.setCreateUid(request.getCreateUid());
        entity.setCreateTime(request.getCreateTime());
        if (ValidateUtil.isNotEmpty(request.getAlKeys()))
            entity.setAsQuestion(request.getAlKeys());
        else if (ValidateUtil.isNotEmpty(request.getProblem()))
            entity.setAsQuestion(request.getProblem());
        entity.setAsAnswer(question);
        baseMapper.insert(entity);
    }

    @Override
    public BaseResponse getSysQuestionSumList(BaseModeRequest request) {
        QueryWrapper<SysQuestionSum> wrapper = new QueryWrapper<>();
        if (!ValidateUtil.isAllEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("Create_Time");
        IPage<SysQuestionSum> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }
}
