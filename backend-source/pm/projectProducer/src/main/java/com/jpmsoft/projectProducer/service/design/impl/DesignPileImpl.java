package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.design.DesignPileMapper;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignPileDto;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignPile;
import com.jpmsoft.projectProducer.pojo.vo.design.DesignPileVo;
import com.jpmsoft.projectProducer.service.design.IDesignPileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 13:58
 */
@Service
public class DesignPileImpl extends FileImpl<DesignPileMapper, DesignPile, DesignPileDto> implements IDesignPileService {

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignPile(DesignPileDto request) {
        DesignPile entity = new DesignPile();
        return BaseResponse.ok(createOrUpdateFileEntity(entity, request));
    }

    @Override
    public BaseResponse getDesignPileList(DesignPileDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Dp_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<DesignPileDto> params = getQueryMap(request, authSql);
        IPage<DesignPileVo> page = new Page<>(request.getPage(), request.getSize());
        List<DesignPileVo> datas = baseMapper.getDesignPileList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignPileListNoAuth(DesignPileDto request) {
        QueryMap<DesignPileDto> params = getQueryMap(request, null);
        IPage<DesignPileVo> page = new Page<>(request.getPage(), request.getSize());
        List<DesignPileVo> datas = baseMapper.getDesignPileList(page,params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignPileById(String dpId) {
        DesignPileVo vo = baseMapper.getDesignPileById(dpId);
        return BaseResponse.ok(vo);
    }

    private QueryMap<DesignPileDto> getQueryMap(DesignPileDto request, String authSql) {
        QueryMap<DesignPileDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("state").put("pName").put("pId").put("pCode")
                .put("conName").put("conCode").put("dpCreateUname").put("dpSecName");
        return params;
    }
}
