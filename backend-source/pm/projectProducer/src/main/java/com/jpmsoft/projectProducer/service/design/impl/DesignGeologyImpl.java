package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignGeologyModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.design.DesignGeologyMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignGeology;
import com.jpmsoft.projectProducer.service.design.IDesignGeologyService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DesignGeologyImpl extends FileImpl<DesignGeologyMapper, DesignGeology, DesignGeologyModel> implements IDesignGeologyService {

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignGeology(DesignGeologyModel request) {
        DesignGeology entity = new DesignGeology();
        return BaseResponse.ok(createOrUpdateFileEntity(entity, request));
    }

    @Override
    public BaseResponse getDesignGeologyList(DesignGeologyModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Design_Geology", uId, "Dg_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<DesignGeologyModel> params = getQueryMap(request, authSql);
        IPage<DesignGeologyModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignGeologyModel> datas = baseMapper.getDesignGeologyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignGeologyListNoAuth(DesignGeologyModel request) {
        QueryMap<DesignGeologyModel> params = getQueryMap(request, null);
        IPage<DesignGeologyModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignGeologyModel> datas = baseMapper.getDesignGeologyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignGeologyById(String dgId) {
        return BaseResponse.ok(baseMapper.getDesignGeologyById(dgId));
    }

    private QueryMap<DesignGeologyModel> getQueryMap(DesignGeologyModel request, String authSql) {
        QueryMap<DesignGeologyModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conName").put("conCode").put("state")
                .put("dgCreateUname").put("dgName").put("dgCode").put("dgDesignOrder").put("dgExplorationUnitTel");
        return params;
    }
}
