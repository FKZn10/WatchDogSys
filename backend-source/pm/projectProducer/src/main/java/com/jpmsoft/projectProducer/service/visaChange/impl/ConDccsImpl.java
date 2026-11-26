package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConDccsMapper;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDccsDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDccs;
import com.jpmsoft.projectProducer.pojo.vo.visaChange.ConDccsVO;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConDccsCompanyService;
import com.jpmsoft.projectProducer.service.visaChange.IConDccsService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:53
 */

@Service
public class ConDccsImpl extends FileImpl<ConDccsMapper, ConDccs, ConDccsDTO> implements IConDccsService {

    @Resource
    private IConDccsCompanyService conDccsCompanyService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateConDccs(ConDccsDTO request) {
        ConDccs entity = new ConDccs();
        createOrUpdateFileEntity(entity, request);
        conDccsCompanyService.createOrUpdateBath(request.getConDccsCompanys(), entity.getDccsId());
        return BaseResponse.ok(entity.getDccsId());
    }

    @Override
    public BaseResponse getConDccsList(ConDccsDTO request, String uid) {
        String authSql = sysUserService.getAuthSql("Con_Dccs", uid, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "state");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConDccsDTO> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("state").put("createUname").put("pName")
                .put("conName").put("dccsMainUnitName").put("pId");
        IPage<ConDccsVO> page = new Page<>(request.getPage(), request.getSize());
        List<ConDccsVO> data = baseMapper.getConDccsList(page, query.getQuery());
        return BaseResponse.ok(data, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse getConDccsById(String id) {
        ConDccsVO data = baseMapper.getConDccsById(id);
        return BaseResponse.ok(data);
    }
}
