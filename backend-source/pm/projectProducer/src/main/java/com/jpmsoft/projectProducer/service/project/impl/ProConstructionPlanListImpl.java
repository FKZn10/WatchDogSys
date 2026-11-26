package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.project.ProConstructionSourcesFundsDto;
import com.jpmsoft.common.model.request.project.ProFundsUsageDetailsDto;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanListModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.mapper.project.ProConstructionPlanListMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionPlan;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionPlanList;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.project.IProConstructionPlanListService;
import com.jpmsoft.projectProducer.service.project.IProConstructionSourcesFundsService;
import com.jpmsoft.projectProducer.service.project.IProFundsUsageDetailsService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProConstructionPlanListImpl extends FileImpl<ProConstructionPlanListMapper, ProConstructionPlanList, ProConstructionPlanListModel> implements IProConstructionPlanListService {

    @Resource
    private IProConstructionSourcesFundsService proConstructionSourcesFundsService;

    @Resource
    private IProFundsUsageDetailsService proFundsUsageDetailsService;

    @Resource
    private IApiUtilsService apiUtilsService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void createOrUpdateProConstructionPlanList(List<ProConstructionPlanListModel> datas, ProConstructionPlan pcp) {
        boolean flag = false;//标记是否开启编码生成
        List<ProConstructionPlanList> olds = null;
        if (apiUtilsService.sequenceRestEnable("ProConstructionPlanList")) {
            flag = true;
            olds = getByParent(pcp.getPcpId());
        }
        deleteByPcpId(pcp.getPcpId());
        List<ProConstructionPlanList> entitys = new ArrayList<>();
        ProConstructionPlanList entity;
        List<List<ProConstructionSourcesFundsDto>> cfsds = new ArrayList<>();
        List<List<ProFundsUsageDetailsDto>> fudds = new ArrayList<>();
        List<String> cplIds = new ArrayList<>();

        for (ProConstructionPlanListModel data : datas) {
            entity = new ProConstructionPlanList();
            data.setCplId(GenerateUUID.getUUID(data.getCplId()));
            data.setCplCreateTime(data.getCplCreateTime() == null ? new Date() : data.getCplCreateTime());
            data.setPcpId(pcp.getPcpId());
            data.setCplCreateUname(pcp.getPcpCreateUname());
            data.setCplCreateUid(pcp.getPcpCreateUid());
            BeanUtils.copyProperties(data, entity);
            if (flag) {//生成编码 如果是之前已有编码则不在生成
                List<ProConstructionPlanList> old = olds.stream().filter(n -> n.getCplId().equals(data.getCplId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(old)) {
                    entity.setPCode(old.stream().findFirst().get().getPCode());
                } else {
                    entity.setPCode(apiUtilsService.getAutoCode("ProConstructionPlanList", entity));
                    apiUtilsService.refershAutoNo("ProConstructionPlanList", entity);
                }
            }
            entity.setTeId(pcp.getTeId());
            entitys.add(entity);
            cfsds.add(CollectionUtils.isEmpty(data.getProConstructionSourcesFunds()) ? new ArrayList<>() : data.getProConstructionSourcesFunds());
            fudds.add(CollectionUtils.isEmpty(data.getProFundsUsageDetails()) ? new ArrayList<>() : data.getProFundsUsageDetails());
            cplIds.add(entity.getCplId());
            sysFileAnnexService.refreshRelationFile(data.getCplFile(), data.getCplFile(), entity.getCplId());
        }
        if (entitys.size() > 0) {
            saveBatch(entitys);
            proConstructionSourcesFundsService.createOrUpdateProConstructionSourcesFundsBath(cfsds, cplIds);
            proFundsUsageDetailsService.createOrUpdateProFundsUsageDetailsBath(fudds, cplIds);
        }
    }

    @Override
    @CodeGeneration(key = "cplId", codeParam = "pCode", method = "setpCode", codeRule = "ProConstructionPlanList", beanClass = ProConstructionPlanListModel.class)
    public BaseResponse createOrUpdateProConstructionPlanList(ProConstructionPlanListModel request) {
        String action = "create";
        if (ValidateUtil.isNotEmpty(request.getCplId())) action = "update";
        ProConstructionPlanList entity = new ProConstructionPlanList();
        createOrUpdateFileEntity(entity, request);
        if (action.equalsIgnoreCase("update")) {
            proConstructionSourcesFundsService.delProConstructionSourcesFundsByCplId(entity.getCplId());
            proFundsUsageDetailsService.delProFundsUsageDetailsByCplId(entity.getCplId());
        }
        proConstructionSourcesFundsService.createOrUpdateProConstructionSourcesFundsBath(Arrays.asList(request.getProConstructionSourcesFunds()), Arrays.asList(entity.getCplId()));
        proFundsUsageDetailsService.createOrUpdateProFundsUsageDetailsBath(Arrays.asList(request.getProFundsUsageDetails()), Arrays.asList(entity.getCplId()));
        return BaseResponse.ok(entity.getCplId());
    }

    @Override
    public BaseResponse getProConstructionPlanList(ProConstructionPlanListModel request) {
        QueryMap<ProConstructionPlanListModel> params = getFilter(request);
        IPage<ProConstructionPlanListModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProConstructionPlanListModel> datas = baseMapper.getProConstructionPlanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    private QueryMap<ProConstructionPlanListModel> getFilter(ProConstructionPlanListModel request) {
        QueryMap<ProConstructionPlanListModel> params = new QueryMap<>(request);
        params.put("pcpFileName").put("pcpYear").put("pcpState").put("pcpCreateUname").put("cplApproval")
                .put("pCode").put("pName").put("cplPType").put("cplYear").put("cplCategory")
                .put("state").put("cplSource").put("cplAppend").put("queryAppend");
        return params;
    }

    @Override
    public BaseResponse getProConstructionPlanSelectList(ProConstructionPlanListModel request) {
        QueryMap<ProConstructionPlanListModel> params = getFilter(request);
        IPage<ProConstructionPlanListModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProConstructionPlanListModel> datas = baseMapper.getProConstructionPlanList(page, params.getQuery());
        if (!CollectionUtils.isEmpty(datas)) {
            List<String> cplIds = datas.stream().map(ProConstructionPlanListModel::getCplId).collect(Collectors.toList());
            List<ProConstructionSourcesFundsDto> csfs = proConstructionSourcesFundsService.getProConstructionSourcesFundsByCplIds(cplIds);
            List<ProFundsUsageDetailsDto> fuds = proFundsUsageDetailsService.getProFundsUsageDetailsByCplIds(cplIds);
            return BaseResponse.ok(setChildTableInfo(csfs, fuds, datas), (int) page.getTotal());
        }
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProConstructionPlanListById(String cplId) {
        ProConstructionPlanListModel data = baseMapper.getProConstructionPlanListById(cplId);
        return BaseResponse.ok(data);
    }

    private List<ProConstructionPlanListModel> setChildTableInfo(List<ProConstructionSourcesFundsDto> csfs, List<ProFundsUsageDetailsDto> fuds, List<ProConstructionPlanListModel> data) {
        if (!CollectionUtils.isEmpty(data)) {
            List<ProConstructionPlanListModel> datas = new ArrayList<>();
            for (ProConstructionPlanListModel model : data) {
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
            return datas;
        }
        return data;
    }

    @Override
    public BaseResponse getProConstructionPlanListByPcpId(String pcpId) {
        List<ProConstructionPlanListModel> data = baseMapper.getProConstructionPlanListByPcpId(pcpId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ProConstructionSourcesFundsDto> csfs = proConstructionSourcesFundsService.getProConstructionSourcesFundsByPcpId(pcpId);
            List<ProFundsUsageDetailsDto> fuds = proFundsUsageDetailsService.getProFundsUsageDetailsByPcpId(pcpId);
            return BaseResponse.ok(setChildTableInfo(csfs, fuds, data));
        }
        return BaseResponse.ok(data);
    }

    private void deleteByPcpId(String pcpId) {
        proConstructionSourcesFundsService.delProConstructionSourcesFundsByPcpId(pcpId);
        proFundsUsageDetailsService.delProFundsUsageDetailsByPcpId(pcpId);
        QueryWrapper<ProConstructionPlanList> wrapper = new QueryWrapper<>();
        wrapper.eq("Pcp_Id", pcpId);
        baseMapper.delete(wrapper);
    }

    private List<ProConstructionPlanList> getByParent(String pcpId) {
        QueryWrapper<ProConstructionPlanList> wrapper = new QueryWrapper<>();
        wrapper.eq("Pcp_Id", pcpId);
        return baseMapper.selectList(wrapper);
    }
}
