package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Construction.ConConstructionOrgDesignRequest;
import com.jpmsoft.common.model.response.Construction.ConConstructionOrgDesignResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.construction.ConConstructionOrgDesignMapper;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConConstructionOrgDesign;
import com.jpmsoft.projectProducer.service.construction.IConConstructionOrgDesignService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConConstructionOrgDesignImpl extends FileImpl<ConConstructionOrgDesignMapper, ConConstructionOrgDesign, ConConstructionOrgDesignRequest> implements IConConstructionOrgDesignService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConConstructionOrgDesign(ConConstructionOrgDesignRequest request) {
        ConConstructionOrgDesign entity = new ConConstructionOrgDesign();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getCodId());
    }

    @Override
    public BaseResponse getConConstructionOrgDesignList(ConConstructionOrgDesignRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Construction_Org_Design", uId, "Cod_Create_Uid",
                OutUserAuthEnum.Unit_Auth.getIndex(), "Cod_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConConstructionOrgDesignRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conName")
                .put("secId").put("secName").put("codTitle");
        IPage<ConConstructionOrgDesignResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConConstructionOrgDesignResponse> datas = baseMapper.getConConstructionOrgDesignList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConConstructionOrgDesignById(String codId) {
        ConConstructionOrgDesignResponse data = baseMapper.getConConstructionOrgDesignById(codId);
        return BaseResponse.ok(data);
    }
}
