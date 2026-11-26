package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConMeasureMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlMeasureDTO;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasure;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureApplyAmtVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureVo;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlService;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordCheckService;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordService;
import com.jpmsoft.projectProducer.service.contract.IConMeasureService;
import com.jpmsoft.projectProducer.service.contract.IConUnitProjectSummaryService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
@Service
public class ConMeasureImpl extends ServiceImpl<ConMeasureMapper, ConMeasure> implements IConMeasureService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IConMeasureRecordService conMeasureRecordService;
    @Resource
    private IConMeasureRecordCheckService conMeasureRecordCheckService;
    @Resource
    private IConUnitProjectSummaryService conUnitProjectSummaryService;
    @Resource
    private IConBoqDtlService conBoqDtlService;

    @Override
    public BaseResponse createOrUpdateConMeasure(ConMeasureDto request) {
        if (ValidateUtil.isNotEmpty(request.getId()))
            return updateConMeasure(request);
        else
            return createConMeasure(request);
    }


    private boolean havingConBoq(String conId){
        ConBoqDtlMeasureDTO req=new ConBoqDtlMeasureDTO();
        req.setConId(conId);
        req.setFilterMeasureType(0);
        BaseResponse res=conBoqDtlService.getConBoqDtlAndMeasureTree(req);
        return ValidateUtil.isNotNull(res.getData());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "meaCode", method = "setMeaCode", codeRule = "ConMeasure", beanClass = ConMeasureDto.class)
    public BaseResponse createConMeasure(ConMeasureDto request) {
        if (haveProcessConMea(request.getConId())) return BaseResponse.fail(ResMsg.unClosedConMea);
        if (!havingConBoq(request.getConId())) return BaseResponse.fail(ResMsg.contractNoBoq);
        ConMeasure entity = new ConMeasure();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        Date now = new Date();
        entity.setCreateTime(now);
        entity.setStateTime(now);
        entity.setState(ValidateUtil.isNull(request.getState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
        baseMapper.insert(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
        sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMeaBook()), entity.getId());
        conMeasureRecordService.createOrUpdateConMeasureRecord(request.getConMeasureRecords(), entity.getId());
        conUnitProjectSummaryService.createOrUpdateConUnitProjectSummary(request.getConUnitProjectSummarys(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateConMeasure(ConMeasureDto request) {
        ConMeasure model = baseMapper.selectById(request.getId());
        if (ValidateUtil.isNull(model)) return BaseResponse.fail(ResMsg.infoNotExist);
        ConMeasure entity = new ConMeasure();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateUid(model.getCreateUid());
        entity.setCreateTime(model.getCreateTime());
        entity.setCreateUname(model.getCreateUname());
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMeaBook()), Arrays.asList(request.getFileIds(), request.getMeaBookIds()), entity.getId());
        conMeasureRecordService.createOrUpdateConMeasureRecord(request.getConMeasureRecords(), entity.getId());
        conUnitProjectSummaryService.createOrUpdateConUnitProjectSummary(request.getConUnitProjectSummarys(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse updateConMeasureOnly(ConMeasureDto request) {
        ConMeasure model = baseMapper.selectById(request.getId());
        if (ValidateUtil.isNull(model)) return BaseResponse.fail(ResMsg.infoNotExist);
        ConMeasure entity = new ConMeasure();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateUid(model.getCreateUid());
        entity.setCreateTime(model.getCreateTime());
        entity.setCreateUname(model.getCreateUname());
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMeaBook()), Arrays.asList(request.getFileIds(), request.getMeaBookIds()), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getConMeasureList(ConMeasureDto request,String uId) {
        QueryMap<ConMeasureDto> params = new QueryMap<>(request);
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        params.put("meaType").put("meaCode").put("meaTitle").put("createUname").put("mearDataSeparate")
                .put("state").put("pName").put("pId").put("conName").put("conId").put("authSql",authSql)
                .put("pNameList").put("conNameList").put("ecChangeTypeList").put("createUnameList").put("stateList")
                .put("ecConstructionNameList");
        IPage<ConMeasureVo> page = new Page<>(request.getPage(), request.getSize());
        List<ConMeasureVo> datas = baseMapper.getConMeasureList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private String getAuthSql(Integer OutUserAuthEnum, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum, "State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        String code = "projectDateLimit";
        String str = sysConfigService.getConfigByCode(code);
        if (StringUtils.isNotEmpty(str)) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return authSql;
    }

    @Override
    public BaseResponse getConMeasureById(String id) {
        ConMeasureVo data = baseMapper.getConMeasureById(id);
        if (data != null) {
            List<ConMeasureRecordVo> records = data.getConMeasureRecords();
            if (!CollectionUtils.isEmpty(records)) {
                List<ConBoqDtlDto> boqds = conBoqDtlService.getConBoqDtlByConId(data.getConId());
                if (!CollectionUtils.isEmpty(boqds)) {
                    Set<ConMeasureRecordVo> allRecords = new HashSet<>();
                    for (ConMeasureRecordVo record : records) {
                        allRecords.add(record);
                        getParentBoqd(boqds, records, allRecords, record.getBoqdParentId());
                    }
                    List<ConMeasureRecordVo> allRds = new ArrayList<>(allRecords);
                    List<ConMeasureRecordVo> roots = allRecords.stream().filter(n -> n.getBoqdParentId().equals("0")).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(roots)) {
                        List<ConMeasureRecordVo> trees = new ArrayList<>();
                        for (ConMeasureRecordVo root : roots) {
                            List<ConMeasureRecordVo> childs = new ArrayList<>();
                            setBoqdTree(allRds, childs, root.getBoqdId());
                            root.setChilds(childs);
                            trees.add(root);
                        }
                        data.setConMeasureRecords(trees);
                    }
                }
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    public ConMeasure getLastApproveMeasureByConId(String conId) {
        QueryWrapper<ConMeasure> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.eq("state", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.orderByDesc("State_Time");
        wrapper.last(" limit 1");
        return baseMapper.selectOne(wrapper);
    }

    private void getParentBoqd(List<ConBoqDtlDto> boqds, List<ConMeasureRecordVo> records, Set<ConMeasureRecordVo> allRecords, String parentId) {
        if (ValidateUtil.isEmpty(parentId) || parentId.equals("0")) return;
        List<ConMeasureRecordVo> rds = records.stream().filter(n -> n.getBoqdId().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(rds)) {
            for (ConMeasureRecordVo rd : rds) {
                getParentBoqd(boqds, records, allRecords, rd.getBoqdParentId());
                allRecords.add(rd);
            }
        } else {
            List<ConBoqDtlDto> boqdParents = boqds.stream().filter(n -> n.getBoqdId().equals(parentId)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(boqdParents)) {
                for (ConBoqDtlDto d : boqdParents) {
                    ConMeasureRecordVo vo = new ConMeasureRecordVo();
                    BeanUtils.copyProperties(d, vo);
                    vo.setId(d.getBoqdId());
                    vo.setBoqdId(d.getBoqdId());
                    vo.setBoqdParentId(d.getBoqdParentId());
                    vo.setMearCode(d.getBoqdCode());
                    vo.setMearBoqdName(d.getBoqdName());
                    allRecords.add(vo);
                    getParentBoqd(boqds, records, allRecords, d.getBoqdParentId());
                }
            }
        }
    }

    private void setBoqdTree(List<ConMeasureRecordVo> allRecords, List<ConMeasureRecordVo> childs, String parentId) {
        List<ConMeasureRecordVo> roots = allRecords.stream().filter(n -> n.getBoqdParentId().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(roots)) {
            for (ConMeasureRecordVo root : roots) {
                List<ConMeasureRecordVo> cds = new ArrayList<>();
                setBoqdTree(allRecords, cds, root.getBoqdId());
                root.setChilds(cds);
                childs.add(root);
            }
        }
    }

    @Override
    public BaseResponse return2Initiator(String id) {
        ConMeasure model = baseMapper.selectById(id);
        if (ValidateUtil.isNull(model)) return BaseResponse.fail(ResMsg.infoNotExist);
        if (ValidateUtil.isNull(model.getState()) || !Objects.equals(model.getState(), ApprovalStatusEnum.Returned_Initiator.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        conMeasureRecordService.return2InitiatorRestoreAmount(id);
        conMeasureRecordCheckService.deleteConMeasureRecordCheckByMeaId(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse statisticsApplyAndAmt(String id,String conId) {
        ConMeasureApplyAmtVO data = baseMapper.statisticsApplyAndAmt(id);
        if (data == null) data = new ConMeasureApplyAmtVO();
        if (ValidateUtil.isNotEmpty(conId))
        {
            ConBoqDtlMeasureDTO req=new ConBoqDtlMeasureDTO();
            req.setConId(conId);
            req.setMeaId(id);
            req.setFilterMeasureType(0);
            req.setJoinMeasure(true);
            req.setFilterOut(true);
            req.setExcludeUnApproval(true);
            data.setOutConNum(conBoqDtlService.getConBoqDtlAndMeasureOutNum(req));
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse statisticsAmtByConId(String conId) {
        ConMeasureApplyAmtVO data = baseMapper.statisticsAmtByConId(conId);
        if (data == null) data = new ConMeasureApplyAmtVO();
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getConMeasureClassInfo(ConMeasureDto request) {
        List<ClassInfoAnnotation> classInfoAnnotationList;
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(ConMeasureDto.class.getName());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
        List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, request);
        classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getConMeasureInfo(ConMeasureDto request) {
        Page<ConMeasureDto> page = new Page<>(request.getPage(), request.getSize());
        // todo 待确认 这个 con_es 的实施单位 是 id 或 name
        List<ConMeasureVo> data = baseMapper.getConMeasureInfoList(page, request);
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    /**
     * 判断合同下是否存在未关闭的单据
     *
     * @param conId
     * @return
     */
    private Boolean haveProcessConMea(String conId) {
        if (ValidateUtil.isEmpty(conId)) return true;
        QueryWrapper<ConMeasure> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.ne("State", 3);
        wrapper.ne("State", 7);
        return baseMapper.selectCount(wrapper) > 0;
    }
}




