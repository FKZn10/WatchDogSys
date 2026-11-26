package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.projectProducer.mapper.design.DesignDocReviewMapper;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDocReviewDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDocReview;
import com.jpmsoft.projectProducer.service.design.IDesignDocReviewService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/19 9:29
 */
@Service
public class DesignDocReviewImpl extends FileImpl<DesignDocReviewMapper, DesignDocReview, DesignDocReviewDTO> implements IDesignDocReviewService {

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDocReview(DesignDocReviewDTO request) {
        DesignDocReview entity = new DesignDocReview();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getDesignDocReviewList(DesignDocReviewDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Delay", uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<DesignDocReviewDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("state").put("createUname")
                .put("pCode").put("conName").put("conCode").put("drName").put("drUnitName")
                .put("drType").put("drStage");
        IPage<DesignDocReviewDTO> page = new Page<>(request.getPage(), request.getSize());
        List<DesignDocReviewDTO> datas = baseMapper.getDesignDocReviewList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignDocReviewById(String id) {
        return BaseResponse.ok(baseMapper.getDesignDocReviewById(id));
    }
}
