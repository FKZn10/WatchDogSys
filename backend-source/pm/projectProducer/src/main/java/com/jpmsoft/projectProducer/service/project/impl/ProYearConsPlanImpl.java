package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.project.ProConstructionSourcesFundsDto;
import com.jpmsoft.common.model.request.project.ProFundsUsageDetailsDto;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProYearConsPlanMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProYearConsPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProYearConsPlan;
import com.jpmsoft.projectProducer.pojo.vo.project.ProYearConsPlanVo;
import com.jpmsoft.projectProducer.pojo.vo.project.ProYearPlanSelectVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.project.IProConstructionSourcesFundsService;
import com.jpmsoft.projectProducer.service.project.IProFundsUsageDetailsService;
import com.jpmsoft.projectProducer.service.project.IProYearConsPlanService;
import com.jpmsoft.projectProducer.service.project.IProYearPlanSelectService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:13
 */
@Service
public class ProYearConsPlanImpl extends BaseFileImpl<ProYearConsPlanMapper, ProYearConsPlan, ProYearConsPlanDto>
        implements IProYearConsPlanService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IProYearPlanSelectService proYearPlanSelectService;
    @Resource
    private IProConstructionSourcesFundsService proConstructionSourcesFundsService;
    @Resource
    private IProFundsUsageDetailsService proFundsUsageDetailsService;

    @Override
    public BaseResponse createOrUpdateProYearConsPlan(ProYearConsPlanDto request) {
        ProYearConsPlan entity = new ProYearConsPlan();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        proYearPlanSelectService.createOrUpdateProYearPlanSelect(request.getProYearPlanSelects(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getProYearConsPlanList(ProYearConsPlanDto request) {
        QueryMap<ProYearConsPlanDto> params = new QueryMap<>(request);
        params.put("state").put("createUname")
                .put(request.getYcpStartTimes(), "ycpStartTimeStart", "ycpStartTimeEnd")
                .put(request.getYcpEndTimes(), "ycpEndTimeStart", "ycpEndTimeEnd");
        IPage<ProYearConsPlanVo> page = new Page<>(request.getPage(), request.getSize());
        List<ProYearConsPlanVo> datas = baseMapper.getProYearConsPlanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProYearConsPlanById(String id) {
        ProYearConsPlanVo data = baseMapper.getProYearConsPlanById(id);
        List<ProYearPlanSelectVo> models = data.getProYearPlanSelects();
        if (!CollectionUtils.isEmpty(models)) {
            List<ProYearPlanSelectVo> datas = new ArrayList<>();
            List<ProFundsUsageDetailsDto> fuds = proFundsUsageDetailsService.getProFundsUsageDetailsByYcpId(id);
            List<ProConstructionSourcesFundsDto> csfs = proConstructionSourcesFundsService.getProConstructionSourcesFundsByYcpId(id);
            for (ProYearPlanSelectVo model : models) {
                if (!CollectionUtils.isEmpty(csfs)) {
                    List<ProConstructionSourcesFundsDto> csf = csfs.stream().filter(n -> n.getCplId().equals(model.getCplId())).collect(Collectors.toList());
                    model.setProConstructionSourcesFunds(csf);
                }
                if (!CollectionUtils.isEmpty(fuds)) {
                    List<ProFundsUsageDetailsDto> fud = fuds.stream().filter(n -> n.getCplId().equals(model.getCplId())).collect(Collectors.toList());
                    model.setProFundsUsageDetails(fud);
                }
                datas.add(model);
            }
            data.setProYearPlanSelects(datas);
        }
        return BaseResponse.ok(data);
    }
}
