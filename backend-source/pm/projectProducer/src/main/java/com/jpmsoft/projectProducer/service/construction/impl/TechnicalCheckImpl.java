package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.projectProducer.mapper.construction.TechnicalCheckMapper;
import com.jpmsoft.projectProducer.pojo.dto.construction.TechnicalCheckDTO;
import com.jpmsoft.projectProducer.pojo.entity.construction.TechnicalCheck;
import com.jpmsoft.projectProducer.service.construction.ITechnicalCheckService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 16:24
 */
@Service
public class TechnicalCheckImpl extends FileImpl<TechnicalCheckMapper, TechnicalCheck, TechnicalCheckDTO> implements ITechnicalCheckService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdate(TechnicalCheckDTO request) {
        TechnicalCheck entity = new TechnicalCheck();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getTechnicalCheckList(TechnicalCheckDTO request, String uid) {
        String authSql = sysUserService.getAuthSql(null, uid, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<TechnicalCheckDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pCode").put("state")
                .put("createUname").put("tcBuildName").put("tcDesignName").put("tcConstructionName").put("tcTitle");;
        IPage<TechnicalCheckDTO> page = new Page<>(request.getPage(), request.getSize());
        List<TechnicalCheckDTO> datas = baseMapper.getTechnicalCheckList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getTechnicalCheckById(String id) {
        return BaseResponse.ok(baseMapper.getTechnicalCheckById(id));
    }
}
