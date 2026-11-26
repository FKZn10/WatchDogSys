package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignCompletedModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.design.DesignCompletedMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignCompleted;
import com.jpmsoft.projectProducer.service.design.IDesignCompletedService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DesignCompletedImpl extends FileImpl<DesignCompletedMapper, DesignCompleted, DesignCompletedModel> implements IDesignCompletedService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignCompleted(DesignCompletedModel request) {
        DesignCompleted entity = new DesignCompleted();
        return BaseResponse.ok(createOrUpdateFileEntity(entity, request));
    }

    @Override
    public BaseResponse getDesignCompletedList(DesignCompletedModel request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Dc_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<DesignCompletedModel> params = setFilter(request, authSql);
        IPage<DesignCompletedModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignCompletedModel> datas = baseMapper.getDesignCompletedList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignCompletedListNoAuth(DesignCompletedModel request) {
        QueryMap<DesignCompletedModel> params = setFilter(request, null);
        IPage<DesignCompletedModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignCompletedModel> datas = baseMapper.getDesignCompletedList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignCompletedById(String dcId) {
        return BaseResponse.ok(baseMapper.getDesignCompletedById(dcId));
    }

    private QueryMap<DesignCompletedModel> setFilter(DesignCompletedModel request, String authSql) {
        QueryMap<DesignCompletedModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("secName").put("state")
                .put("dcCreateUname").put("pId");
        return params;
    }
}
