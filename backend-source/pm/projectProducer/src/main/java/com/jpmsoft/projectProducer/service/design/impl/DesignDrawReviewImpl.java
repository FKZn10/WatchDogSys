package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.projectProducer.mapper.design.DesignDrawReviewMapper;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDrawReviewDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawReview;
import com.jpmsoft.projectProducer.service.design.IDesignDrawReviewDtlService;
import com.jpmsoft.projectProducer.service.design.IDesignDrawReviewService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 11:30
 */
@Service
public class DesignDrawReviewImpl extends FileImpl<DesignDrawReviewMapper, DesignDrawReview, DesignDrawReviewDTO> implements IDesignDrawReviewService {

    @Resource
    private IDesignDrawReviewDtlService designDrawReviewDtlService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignDrawReview(DesignDrawReviewDTO request, String token) {
        DesignDrawReview entity = new DesignDrawReview();
        createOrUpdateFileEntity(entity, request);
        designDrawReviewDtlService.createOrUpdate(request.getDesignDrawReviewDtls(), entity.getId(), token);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getDesignDrawReviewList(DesignDrawReviewDTO request, String token) {
        String authSql = sysUserService.getAuthSql("Design_Draw_Review", redisUtils.getUid(token), "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<DesignDrawReviewDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pType").put("drTitle")
                .put("state").put("createUname");
        IPage<DesignDrawReviewDTO> page = new Page<>(request.getPage(), request.getSize());
        List<DesignDrawReviewDTO> datas = baseMapper.getDesignDrawReviewList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignDrawReviewById(String id) {
        return BaseResponse.ok(baseMapper.getDesignDrawReviewById(id));
    }
}
