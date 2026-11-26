package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecEntrustMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecEntrustDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecEntrust;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecEntrustVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecEntrustService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/31 11:02
 */
@Service
public class SecEntrustImpl extends BaseFileImpl<SecEntrustMapper, SecEntrust, SecEntrustDto> implements ISecEntrustService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateSecEntrust(SecEntrustDto request) {
        SecEntrust entity = new SecEntrust();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecEntrustById(String id) {
        SecEntrustVo data = baseMapper.getSecEntrustById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecEntrustList(SecEntrustDto request) {
        QueryMap<SecEntrustDto> params = new QueryMap<>(request);
        params.put("secName").put("secType").put("secOrganizeForm").put("secBiddingType")
                .put("tendereeName").put("state").put("createUname").put("sePm").put("pName")
                .put("secTradingPlace").put("secPlace").put("seCpyName").put("seResult")
                .put("seQualification").put("seAchievement").put("pCompetentDepartmentName")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd")
                .put(request.getSeAmts(), "seAmtStart", "seAmtEnd")
                .put(request.getSecStartTimes(), "secStartTimeStart", "secStartTimeEnd")
                .put(request.getSecBiddingCycles(), "secBiddingCycleStart", "secBiddingCycleEnd");
        IPage<SecEntrustVo> page = new Page<>(request.getPage(), request.getSize());
        List<SecEntrustVo> datas = baseMapper.getSecEntrustList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }
}
