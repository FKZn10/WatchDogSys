package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.project.ProConstructionSourcesFundsDto;
import com.jpmsoft.common.model.request.project.ProFundsUsageDetailsDto;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanListModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProConstructionPlanMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionPlan;
import com.jpmsoft.projectProducer.pojo.vo.project.ProConstructionPlanStatisticsVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IProConstructionPlanListService;
import com.jpmsoft.projectProducer.service.project.IProConstructionPlanService;
import com.jpmsoft.projectProducer.service.project.IProConstructionSourcesFundsService;
import com.jpmsoft.projectProducer.service.project.IProFundsUsageDetailsService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProConstructionPlanImpl extends ServiceImpl<ProConstructionPlanMapper, ProConstructionPlan> implements IProConstructionPlanService {

    @Resource
    private IProConstructionPlanListService proConstructionPlanListService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IProConstructionSourcesFundsService proConstructionSourcesFundsService;
    @Resource
    private IProFundsUsageDetailsService proFundsUsageDetailsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProConstructionPlan(ProConstructionPlanModel request) {
        ProConstructionPlan entity = new ProConstructionPlan();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getPcpId())) {
            entity.setPcpCreateTime(new Date());
            entity.setPcpId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPcpId(), request.gettId(), entity.getPcpState() + "", entity.getPcpCreateUid(), entity.getPcpCreateUname()
                    , entity.getPcpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getPcpId());

        } else {
            ProConstructionPlan model = baseMapper.selectById(request.getPcpId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getPcpState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setPcpState(model.getPcpState());
            entity.setPcpStateTime(model.getPcpStateTime());
            entity.setPcpCreateTime(model.getPcpCreateTime());
            entity.setPcpCreateUid(model.getPcpCreateUid());
            entity.setPcpCreateUname(model.getPcpCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPcpId(), request.gettId(), entity.getPcpState() + "", entity.getPcpCreateUid(), entity.getPcpCreateUname()
                    , entity.getPcpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getPcpId());

        }
        if (!CollectionUtils.isEmpty(request.getProConstPlanListModels()))
            proConstructionPlanListService.createOrUpdateProConstructionPlanList(request.getProConstPlanListModels(), entity);
        return BaseResponse.ok(entity.getPcpId());
    }

    @Override
    public BaseResponse getProConstructionPlanList(ProConstructionPlanModel request) {
        QueryMap<ProConstructionPlanModel> queryMap = new QueryMap<>(request);
        queryMap.put("pcpFileName").put("pcpYear").put("pcpState").put("pcpCreateUname");
        IPage<ProConstructionPlanModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProConstructionPlanModel> datas = baseMapper.getProConstructionPlanList(page, queryMap.getQuery());
        ProConstructionPlanStatisticsVo data = new ProConstructionPlanStatisticsVo();
        data.setData(datas);
        if (!CollectionUtils.isEmpty(datas))
            data.setCplPlannedInvSum(baseMapper.getCplPlannedInvSum(queryMap.getQuery()));
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProConstructionPlanById(String pcpId) {
        ProConstructionPlanModel data = baseMapper.getProConstructionPlanById(pcpId);
        List<ProConstructionPlanListModel> models = data.getProConstPlanListModels();
        if (!CollectionUtils.isEmpty(models)) {
            List<ProConstructionPlanListModel> datas = new ArrayList<>();
            List<ProFundsUsageDetailsDto> fuds = proFundsUsageDetailsService.getProFundsUsageDetailsByPcpId(pcpId);
            List<ProConstructionSourcesFundsDto> csfs = proConstructionSourcesFundsService.getProConstructionSourcesFundsByPcpId(pcpId);
            for (ProConstructionPlanListModel model : models) {
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
            data.setProConstPlanListModels(datas);
        }
        return BaseResponse.ok(data);
    }
}
