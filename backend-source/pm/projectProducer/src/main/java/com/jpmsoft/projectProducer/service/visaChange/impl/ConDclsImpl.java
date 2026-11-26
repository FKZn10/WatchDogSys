package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConDclsMapper;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDclsDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDcls;
import com.jpmsoft.projectProducer.pojo.vo.visaChange.ConDclsVO;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConDclsService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/7 8:40
 */
@Service
public class ConDclsImpl extends FileImpl<ConDclsMapper, ConDcls, ConDclsDTO> implements IConDclsService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateConDcls(ConDclsDTO request) {
        ConDcls entity = new ConDcls();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getDclsId());
    }

    @Override
    public BaseResponse getConDclsList(ConDclsDTO request, String uid) {
        String authSql = sysUserService.getAuthSql("Con_Dcls", uid, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "state");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConDclsDTO> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("state").put("createUname").put("pName")
                .put("conName").put("pId");
        IPage<ConDclsVO> page = new Page<>(request.getPage(), request.getSize());
        List<ConDclsVO> data = baseMapper.getConDclsList(page, query.getQuery());
        return BaseResponse.ok(data, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse getConDclsById(String id) {
        ConDclsVO data = baseMapper.getConDclsById(id);
        return BaseResponse.ok(data);
    }
}
