package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.construction.NightConstructionFilingMapper;
import com.jpmsoft.projectProducer.pojo.dto.construction.NightConstructionFilingDTO;
import com.jpmsoft.projectProducer.pojo.entity.construction.NightConstructionFiling;
import com.jpmsoft.projectProducer.service.construction.INightConstructionFilingService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/24 10:53
 */
@Service
public class NightConstructionFilingImpl extends FileImpl<NightConstructionFilingMapper, NightConstructionFiling, NightConstructionFilingDTO> implements INightConstructionFilingService {
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateNightConstructionFiling(NightConstructionFilingDTO request) {
        NightConstructionFiling entity = new NightConstructionFiling();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getNightConstructionFilingList(NightConstructionFilingDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("Night_Construction_Filing", uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<NightConstructionFilingDTO> params =new QueryMap<>(request);
        params.put("authSql",authSql).put("pId").put("conId").put("pName").put("pCode").put("conName").put("conCode")
                .put("state").put("createUname").put("ncfLeader").put("ncfLeaderPhone").put("ncfAreaSection")
                .put("ncfWorker").put("ncfWorkerPhone").put("ncfSide").put("ncfSidePhone");
        IPage<NightConstructionFilingDTO> page = new Page<>(request.getPage(), request.getSize());
        List<NightConstructionFilingDTO> datas = baseMapper.getNightConstructionFilingList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getNightConstructionFilingById(String id) {
        return BaseResponse.ok(baseMapper.getNightConstructionFilingById(id));
    }
}
