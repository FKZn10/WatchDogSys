package com.jpmsoft.projectProducer.service.fund.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fund.GroupFundPlanMapper;
import com.jpmsoft.projectProducer.mapper.fund.GroupFundPlanSummaryMapper;
import com.jpmsoft.projectProducer.mapper.other.RemarkInfoMapper;
import com.jpmsoft.projectProducer.mapper.other.SysDepartmentMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanSummaryDetailDto;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanSummaryDto;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlan;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanSummary;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanSummaryDetail;
import com.jpmsoft.projectProducer.pojo.entity.other.RemarkInfo;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDepartment;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanSummaryDetailRequest;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanSummaryRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanSummaryDetailTotalVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanSummaryDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanSummaryVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanSummaryDetailService;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanSummaryService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IGroupFundPlanSummaryServiceImpl extends BaseFileImpl<GroupFundPlanSummaryMapper, GroupFundPlanSummary, GroupFundPlanSummaryDto> implements IGroupFundPlanSummaryService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    private GroupFundPlanMapper groupFundPlanMapper;
    @Resource
    private IGroupFundPlanSummaryDetailService groupFundPlanSummaryDetailService;
    @Autowired
    private RemarkInfoMapper remarkInfoMapper;
    @Autowired
    private PreProjectMapper preProjectMapper;
    @Resource
    private IConPayService conPayService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGroupFundPlanSummary(GroupFundPlanSummaryDto dto) {
        String primaryId = dto.getId();
        if(StrUtil.isEmpty(primaryId)){
            primaryId = GenerateUUID.getUUID();
            if (!"change".equals(dto.getOperationalType()) && existProjectSubmit(null, dto.getGfpsYear(), dto.getGfpsMonth(), dto.getGfpsType(), dto.getDepartmentIds())) {
                return BaseResponse.fail("该年月已存在已提交的集团资金计划，不能重复提交！");
            }
            GroupFundPlanSummary groupFundPlanSummary = new GroupFundPlanSummary();
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            dto.setState(flowStatus);
            BeanUtil.copyProperties(dto,groupFundPlanSummary);
            groupFundPlanSummary.setId(primaryId);
            groupFundPlanSummary.setDelFlag(0);
            groupFundPlanSummary.setCreateTime(DateTime.now());
            List<GroupFundPlanSummaryDetail> details = new ArrayList<>();
            // List<RemarkInfo> remarkInfos = new ArrayList<>();
            if (dto.getDetailDtos()!=null){
                List<GroupFundPlanSummaryDetailDto> dtos =dto.getDetailDtos();
                if (CollectionUtil.isNotEmpty(dtos)){
                    for (GroupFundPlanSummaryDetailDto detailsDtos:dtos) {
                        detailsDtos.setId(GenerateUUID.getUUID());
                        detailsDtos.setGfpsId(primaryId);
                        detailsDtos.setDelFlag(0);
                        detailsDtos.setStatus(0);
                        GroupFundPlanSummaryDetail detailsObj =new GroupFundPlanSummaryDetail();
                        BeanUtil.copyProperties(detailsDtos,detailsObj);
                        if ("change".equals(dto.getOperationalType())){
                            detailsObj.setGfpFundApplyAmountCwAudit(BigDecimal.ZERO);
                        }
                        details.add(detailsObj);
                        // if(dto.getGfpsType()==2 && StrUtil.isNotEmpty(detailsDtos.getRemarkContext())){
                        //     RemarkInfo remarkInfo =new RemarkInfo();
                        //     remarkInfo.setId(GenerateUUID.getUUID());
                        //     remarkInfo.setRemarkSourceId(detailsObj.getId());
                        //     remarkInfo.setRemarkFunctionCode("BatchSubmissionCJ");
                        //     remarkInfo.setRemarkContext(detailsDtos.getRemarkContext());
                        //     remarkInfo.setCreateTime(DateTime.now());
                        //     remarkInfo.setState(ApprovalStatusEnum.Already_Over.getIndex());
                        //     remarkInfo.setStateTime(DateTime.now());
                        //     remarkInfo.setCreateUid(dto.getCreateUid());
                        //     remarkInfo.setCreateUname(dto.getCreateUname());
                        //     remarkInfos.add(remarkInfo);
                        // }
                    }
                }
            }else {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            List<String> fileIds =new ArrayList<>();
            if (CollectionUtil.isNotEmpty(details)){
                groupFundPlanSummaryDetailService.saveBatch(details);
            }
            // if (CollectionUtil.isNotEmpty(remarkInfos)){
            //     remarkInfoMapper.addBatch(remarkInfos);
            // }
            baseMapper.insert(groupFundPlanSummary);
            fileIds.add(dto.getFileRelationId());
            sysFileAnnexService.addFormRefreshRelationFile(fileIds, groupFundPlanSummary.getId());
            //创建流程
            if(!sysFileService.FileHelper(groupFundPlanSummary,dto,"create",dto.getFileRelationId())){
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        }else{
            if (!"change".equals(dto.getOperationalType()) && existProjectSubmit(primaryId, dto.getGfpsYear(), dto.getGfpsMonth(), dto.getGfpsType(), dto.getDepartmentIds())) {
                return BaseResponse.fail("该年月已存在已提交的集团资金计划，不能重复提交！");
            }
            GroupFundPlanSummary groupFundPlanSummary = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(dto,groupFundPlanSummary);
            //禁用旧的填报值
            deleteDetailById(primaryId);
            // deleteRemarkById(primaryId);
            List<GroupFundPlanSummaryDetail> details = new ArrayList<>();
            // List<RemarkInfo> remarkInfos = new ArrayList<>();
            if (dto.getDetailDtos()!=null) {
                List<GroupFundPlanSummaryDetailDto> dtos = dto.getDetailDtos();
                if (CollectionUtil.isNotEmpty(dtos)) {
                    for (GroupFundPlanSummaryDetailDto detailsDtos : dtos) {
                        detailsDtos.setId(GenerateUUID.getUUID());
                        detailsDtos.setGfpsId(primaryId);
                        detailsDtos.setDelFlag(0);
                        detailsDtos.setStatus(0);
                        GroupFundPlanSummaryDetail detailsObj = new GroupFundPlanSummaryDetail();
                        BeanUtil.copyProperties(detailsDtos,detailsObj);
                        details.add(detailsObj);
                        // if(dto.getGfpsType()==2 && StrUtil.isNotEmpty(detailsDtos.getRemarkContext())){
                        //     RemarkInfo remarkInfo =new RemarkInfo();
                        //     remarkInfo.setId(GenerateUUID.getUUID());
                        //     remarkInfo.setRemarkSourceId(detailsObj.getId());
                        //     remarkInfo.setRemarkFunctionCode("BatchSubmissionCJ");
                        //     remarkInfo.setRemarkContext(detailsDtos.getRemarkContext());
                        //     remarkInfo.setCreateTime(DateTime.now());
                        //     remarkInfo.setState(ApprovalStatusEnum.Already_Over.getIndex());
                        //     remarkInfo.setStateTime(DateTime.now());
                        //     remarkInfo.setCreateUid(dto.getCreateUid());
                        //     remarkInfo.setCreateUname(dto.getCreateUname());
                        //     remarkInfos.add(remarkInfo);
                        // }
                    }
                }
            }else {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            List<String> fileIds =new ArrayList<>();
            if (CollectionUtil.isNotEmpty(details)){
                groupFundPlanSummaryDetailService.saveBatch(details);
            }
            // if (CollectionUtil.isNotEmpty(remarkInfos)){
            //     remarkInfoMapper.addBatch(remarkInfos);
            // }
            baseMapper.updateById(groupFundPlanSummary);
            fileIds.add(dto.getFileRelationId());
            sysFileAnnexService.addFormRefreshRelationFile(fileIds, groupFundPlanSummary.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    private void deleteRemarkById(String primaryId) {
        List<RemarkInfo> remarkInfos = remarkInfoMapper.getRemarkInfoByGroupFundPlanSummaryId(primaryId);
        if (CollectionUtil.isNotEmpty(remarkInfos)){
            for (RemarkInfo remarkInfo:remarkInfos) {
                remarkInfoMapper.deleteById(remarkInfo);
            }
        }
    }

    private void deleteDetailById(String primaryId) {
        List<GroupFundPlanSummaryDetail> details = groupFundPlanSummaryDetailService.getDetailByGfpsId(primaryId);
        if (CollectionUtil.isNotEmpty(details)){
            for (GroupFundPlanSummaryDetail detail:details) {
                detail.setDelFlag(1);
                detail.setStatus(1);
            }
            groupFundPlanSummaryDetailService.updateBatchById(details);
        }
    }

    @Override
    public BaseResponse getGroupFundPlanSummaryList(GroupFundPlanSummaryRequest request, String uid) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isEmpty(uid)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        String authSql = sysUserService.getAuthSql("Group_Fund_Plan_Summary", uid, "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        QueryMap<GroupFundPlanSummaryRequest> params = new QueryMap<>(request);
        params.put("gfpsType").put("gfpsYear").put("gfpsMonth").put("gfpsProjectCompetentDepartmentId")
                .put("groupId").put("groupMainType").put("gfpsTitle")
                .put("authSql", authSql);
        IPage<GroupFundPlanSummaryVo> page = new Page<>(request.getPage(), request.getSize());
        List<GroupFundPlanSummaryVo> groupFundPlanSummaryVos
                = baseMapper.getGroupFundPlanSummaryVosList(params.getQuery(),page);
        groupFundPlanSummaryVos.forEach(item -> item.setCanChange(!existProjectChange(item.getGroupId(), item.getGfpsType())));
        for (GroupFundPlanSummaryVo groupFundPlanSummaryVo:groupFundPlanSummaryVos) {
            getSummaryInfoDetails(groupFundPlanSummaryVo);
        }
        log.info("groupFundPlanSummaryVos:"+ JacksonUtils.toJSONString(groupFundPlanSummaryVos));
        return BaseResponse.ok(groupFundPlanSummaryVos,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getGroupFundPlanSummaryById(String id) {
        GroupFundPlanSummaryVo groupFundPlanSummaryVo= baseMapper.getGroupFundPlanSummaryById(id);
        log.info("groupFundPlanSummaryVo:{}", JacksonUtils.toJSONString(groupFundPlanSummaryVo));
        if(groupFundPlanSummaryVo==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }else{
            getSummaryInfoDetails(groupFundPlanSummaryVo);
        }
        return BaseResponse.ok(groupFundPlanSummaryVo);
    }

    @Override
    public BaseResponse getCompanyGroupFundPlanSummaryById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        GroupFundPlanSummaryVo detailVos = baseMapper.getCompanyGroupFundPlanSummaryById(id);
        if (ValidateUtil.isNull(detailVos)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<GroupFundPlanSummaryDetailVo> list = groupFundPlanSummaryDetailService.getCompanyDetailByGfpsId(detailVos.getId());
        if (CollectionUtil.isEmpty(list)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        Map<String, List<GroupFundPlanSummaryDetailVo>> competentDeptMap = list.stream()
                .filter(vo -> StrUtil.isNotBlank(vo.getGfpsProjectCompetentDepartmentId()))
                .collect(Collectors.groupingBy(GroupFundPlanSummaryDetailVo::getGfpsProjectCompetentDepartmentId));

        List<GroupFundPlanSummaryDetailVo> treeList = new ArrayList<>();

        for (Map.Entry<String, List<GroupFundPlanSummaryDetailVo>> competentEntry : competentDeptMap.entrySet()) {
            List<GroupFundPlanSummaryDetailVo> competentDeptList = competentEntry.getValue();
            GroupFundPlanSummaryDetailVo first = competentDeptList.get(0);

            // 事业部节点
            GroupFundPlanSummaryDetailVo competentNode = new GroupFundPlanSummaryDetailVo();
            competentNode.setId(GenerateUUID.getUUID());
            competentNode.setGfpMonth(first.getGfpMonth());
            competentNode.setGfpYear(first.getGfpYear());
            competentNode.setGfpsProjectCompetentDepartmentId(first.getGfpsProjectCompetentDepartmentId());
            competentNode.setGfpsProjectCompetentDepartmentName(first.getGfpsProjectCompetentDepartmentName());
            competentNode.setGfpsProjectDepartmentId(first.getGfpsProjectDepartmentId());
            competentNode.setGfpsProjectDepartmentName(first.getGfpsProjectDepartmentName());
            Map<String, List<GroupFundPlanSummaryDetailVo>> deptMap2 = competentDeptList.stream()
                    .filter(vo -> StrUtil.isNotBlank(vo.getGfpsProjectDepartmentId()))
                    .collect(Collectors.groupingBy(GroupFundPlanSummaryDetailVo::getGfpsProjectDepartmentId));

            List<GroupFundPlanSummaryDetailVo> deptNodes = new ArrayList<>();
            for (Map.Entry<String, List<GroupFundPlanSummaryDetailVo>> deptEntry : deptMap2.entrySet()) {
                List<GroupFundPlanSummaryDetailVo> deptList = deptEntry.getValue();
                GroupFundPlanSummaryDetailVo deptFirst = deptList.get(0);
                GroupFundPlanSummaryDetailVo deptNode = new GroupFundPlanSummaryDetailVo();
                deptNode.setId(GenerateUUID.getUUID());
                deptNode.setGfpsProjectCompetentDepartmentId(deptFirst.getGfpsProjectCompetentDepartmentId());
                deptNode.setGfpsProjectCompetentDepartmentName(deptFirst.getGfpsProjectCompetentDepartmentName());
                deptNode.setGfpsProjectDepartmentId(deptFirst.getGfpsProjectDepartmentId());
                deptNode.setGfpsProjectDepartmentName(deptFirst.getGfpsProjectDepartmentName());
                deptNode.setChildren(deptList);
                deptNodes.add(deptNode);
            }
            competentNode.setChildren(deptNodes);
            treeList.add(competentNode);
        }
        detailVos.setDetailVos(treeList);
        return BaseResponse.ok(detailVos);
    }

    @Override
    public BaseResponse getBlocFundPlanSummaryById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        GroupFundPlanSummaryVo detailVos = baseMapper.getCompanyGroupFundPlanSummaryById(id);
        if (ValidateUtil.isNull(detailVos)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<GroupFundPlanSummaryDetailVo> list = groupFundPlanSummaryDetailService.getCompanyDetailByGfpsId(detailVos.getId());
        if (CollectionUtil.isEmpty(list)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        Map<String, List<GroupFundPlanSummaryDetailVo>> competentDeptMap = list.stream()
                .filter(vo -> StrUtil.isNotBlank(vo.getGfpsProjectCompetentDepartmentId()))
                .collect(Collectors.groupingBy(GroupFundPlanSummaryDetailVo::getGfpsProjectCompetentDepartmentId));

        List<GroupFundPlanSummaryDetailVo> treeList = new ArrayList<>();
        for (Map.Entry<String, List<GroupFundPlanSummaryDetailVo>> competentEntry : competentDeptMap.entrySet()) {
            List<GroupFundPlanSummaryDetailVo> competentDeptList = competentEntry.getValue();
            GroupFundPlanSummaryDetailVo first = competentDeptList.get(0);

            // 事业部节点
            GroupFundPlanSummaryDetailVo competentNode = new GroupFundPlanSummaryDetailVo();
            competentNode.setId(GenerateUUID.getUUID());
            competentNode.setGfpMonth(first.getGfpMonth());
            competentNode.setGfpYear(first.getGfpYear());

            competentNode.setGfpsProjectCompetentDepartmentId(first.getGfpsProjectCompetentDepartmentId());
            competentNode.setGfpsProjectCompetentDepartmentName(first.getGfpsProjectCompetentDepartmentName());
            competentNode.setGfpsProjectDepartmentId(first.getGfpsProjectDepartmentId());
            competentNode.setGfpsProjectDepartmentName(first.getGfpsProjectDepartmentName());
            competentNode.setChildren(competentDeptList);
            treeList.add(competentNode);
        }
        detailVos.setDetailVos(treeList);
        return BaseResponse.ok(detailVos);
    }

    @Override
    public BaseResponse approvalData(GroupFundPlanSummaryRequest request) {
        String id = request.getId();
        GroupFundPlanSummary entity;
        SysFile fileEntity;
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("信息不存在，请刷新重试！");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null)
                return new BaseResponse("信息不存在，请刷新重试！");
            if (ValidateUtil.isNotNull(entity.getReturnType())) {
                if (entity.getReturnType() == 1) {
                    fileEntity = sysFileService.getById(id);
                    fileEntity.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
                    sysFileService.updateById(fileEntity);
                    // 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
                    entity.setState(ApprovalStatusEnum.Already_Over.getIndex());
                    entity.setStateTime(new Date());
                    baseMapper.updateById(entity);
                } else {
                    fileEntity = sysFileService.getById(id);
                    // 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
                    String fileFlowStatus = fileEntity.getFileflowstatus();
                    entity.setState(Integer.valueOf(fileFlowStatus));
                    entity.setStateTime(new Date());
                    baseMapper.updateById(entity);
                }
            }

            // 流程审批结束 设置工程财务审核金额
            List<GroupFundPlanSummaryDetailRequest> details = request.getGfpDetailDtos();
            log.info("details:{}", JacksonUtils.toJSONString(details));
            if (CollectionUtil.isNotEmpty(details)) {
                for (GroupFundPlanSummaryDetailRequest detail : details) {
                    GroupFundPlanSummaryDetail detailEntity = groupFundPlanSummaryDetailService.getById(detail.getId());
                    GroupFundPlan groupFundPlan = groupFundPlanMapper.selectById(detail.getGfpId());
                    if (groupFundPlan != null) {
                        groupFundPlan.setGfpFundApplyAmountCwAudit(detail.getGfpFundApplyAmountCwAudit());
                        groupFundPlan.setGfpFundApplyAmountGcAudit(detail.getGfpFundApplyAmountGcAudit());
                        groupFundPlanMapper.updateById(groupFundPlan);
                    }
                    if (detailEntity != null) {
                        detailEntity.setGfpFundApplyAmountCwAudit(detail.getGfpFundApplyAmountCwAudit());
                        detailEntity.setGfpFundApplyAmountGcAudit(detail.getGfpFundApplyAmountGcAudit());
                        groupFundPlanSummaryDetailService.updateById(detailEntity);
                    }
                }
            }
        }

        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getGroupFundPlanSummaryInfo(GroupFundPlanSummaryRequest request,String uid) {
        QueryMap<GroupFundPlanSummaryRequest> params = new QueryMap<>(request);
        GroupFundPlanSummaryVo groupFundPlanSummaryVo= new GroupFundPlanSummaryVo();
        groupFundPlanSummaryVo.setId(GenerateUUID.getUUID());
        groupFundPlanSummaryVo.setGfpsTitle("南京市城建集团公益性项目月度（"+request.getGfpsYear()+"年"+request.getGfpsMonth()+"月）资金计划安排审核表");
        groupFundPlanSummaryVo.setGfpsYear(request.getGfpsYear());
        groupFundPlanSummaryVo.setGfpsMonth(request.getGfpsMonth());
        params.put("gfpsMonth").put("gfpsYear");
        if (StrUtil.isNotEmpty(request.getGfpsProjectCompetentDepartmentId())){
            groupFundPlanSummaryVo.setGfpsType(1);
            groupFundPlanSummaryVo.setGfpsProjectCompetentDepartmentId(request.getGfpsProjectCompetentDepartmentId());
            SysDepartment department = sysDepartmentMapper.selectById(request.getGfpsProjectCompetentDepartmentId());
            List<String> departmentIds =new ArrayList<>();
            departmentIds.add(request.getGfpsProjectCompetentDepartmentId());
            if (department!=null){
                groupFundPlanSummaryVo.setGfpsProjectCompetentDepartmentName(department.getdName());
                getParentDepartment(department,departmentIds);
            }
            params.put("gfpsProjectCompetentDepartmentIds",departmentIds);
            List<GroupFundPlanSummaryDetailVo> groupFundPlanSummaryDetailVos
                = baseMapper.getGroupFundPlanSummaryInfo(params.getQuery());
            log.info("groupFundPlanSummaryDetailVos:{}", JacksonUtils.toJSONString(groupFundPlanSummaryDetailVos));
            for (GroupFundPlanSummaryDetailVo detailVo:groupFundPlanSummaryDetailVos){
                detailVo.setId(GenerateUUID.getUUID());
                detailVo.setGfpsId(groupFundPlanSummaryVo.getId());
                detailVo.setIsNew(0);
            }
             //同一项目可发起同一月份的多条资金计划，且最新一笔的资金计划需要字体标红
            setIsNew(groupFundPlanSummaryVo,groupFundPlanSummaryDetailVos);
        }else {
            groupFundPlanSummaryVo.setGfpsType(2);
            List<GroupFundPlanSummaryDetailVo> groupFundPlanSummaryDetailVos
                = baseMapper.getGroupFundPlanSummaryInfo(params.getQuery());
            log.info("groupFundPlanSummaryDetailVos2:{}", JacksonUtils.toJSONString(groupFundPlanSummaryDetailVos));
            for (GroupFundPlanSummaryDetailVo detailVo:groupFundPlanSummaryDetailVos){
                detailVo.setId(GenerateUUID.getUUID());
                detailVo.setGfpsId(groupFundPlanSummaryVo.getId());
                String summaryId =baseMapper.getGroupFundPlanSummaryByGroupFundPlanId(detailVo.getGfpId());
                detailVo.setGfpsSummaryId(summaryId);
                detailVo.setIsNew(0);
            }
            setIsNew(groupFundPlanSummaryVo,groupFundPlanSummaryDetailVos);
        }
        log.info("groupFundPlanSummaryVo:{}", JacksonUtils.toJSONString(groupFundPlanSummaryVo));
        getSummaryInfo(groupFundPlanSummaryVo);
        log.info("groupFundPlanSummaryVo:{}", JacksonUtils.toJSONString(groupFundPlanSummaryVo));
        return BaseResponse.ok(groupFundPlanSummaryVo);
    }

    private static void setIsNew(GroupFundPlanSummaryVo groupFundPlanSummaryVo,List<GroupFundPlanSummaryDetailVo> groupFundPlanSummaryDetailVos) {
        Map<String, List<GroupFundPlanSummaryDetailVo>> groupFundPlanSummaryDetailVoGroups =
                groupFundPlanSummaryDetailVos.stream()
                        .filter(vo -> StrUtil.isNotBlank(vo.getProjectId()))
                        .collect(Collectors.groupingBy(GroupFundPlanSummaryDetailVo::getProjectId));
        for (Map.Entry<String, List<GroupFundPlanSummaryDetailVo>> entry : groupFundPlanSummaryDetailVoGroups.entrySet()){
            List<GroupFundPlanSummaryDetailVo> list = entry.getValue();
            if (list.size()>1){
                Map<Integer, List<GroupFundPlanSummaryDetailVo>> groupedProducts= list.stream().filter(groupFundPlanVo->groupFundPlanVo.getGfpMonth()!=null).collect(Collectors.groupingBy(GroupFundPlanSummaryDetailVo::getGfpMonth));
                for (Map.Entry<Integer, List<GroupFundPlanSummaryDetailVo>> entry1 : groupedProducts.entrySet()){
                    List<GroupFundPlanSummaryDetailVo> list1 = entry1.getValue();
                    list1=list1.stream().filter(groupFundPlanVo->groupFundPlanVo.getCreateTime()!=null).collect(Collectors.toList());
                    if (list1.size()>1){
                        GroupFundPlanSummaryDetailVo groupFundPlanSummaryDetailVo =
                                list1.stream().sorted(Comparator.comparing(GroupFundPlanSummaryDetailVo::getCreateTime).reversed())
                                        .collect(Collectors.toList()).get(0);
                        groupFundPlanSummaryDetailVo.setIsNew(1);
                    }
                }
            }
        }
        groupFundPlanSummaryVo.setDetailVos(groupFundPlanSummaryDetailVos);
    }

    private void getParentDepartment(SysDepartment department,List<String> departmentIds) {
        if (StrUtil.isNotEmpty(department.getdParentid())){
             SysDepartment parentDepartment = sysDepartmentMapper.selectById(department.getdParentid());
             if (parentDepartment!=null){
                  if (StrUtil.isNotEmpty(parentDepartment.getdParentid())&& !"0".equals(parentDepartment.getdParentid())){//根节点不加
                     departmentIds.add(parentDepartment.getdId());
                  }
                  getParentDepartment(parentDepartment,departmentIds);
             }
        }
    }

    @Override
    public BaseResponse rollbackData(GroupFundPlanSummaryRequest request) {
        if (request.getGfpsType()==1){
            /// 成员
            List<GroupFundPlanSummaryDetail> details = groupFundPlanSummaryDetailService.getDetailById(request.getGfpDetailIds());
            if (CollectionUtil.isNotEmpty(details)){
                for (GroupFundPlanSummaryDetail detail:details) {
                    detail.setDelFlag(1);
                    detail.setStatus(1);
                    groupFundPlanSummaryDetailService.updateById(detail);
                    String gfpId = detail.getGfpId();
                    GroupFundPlan groupFundPlan = groupFundPlanMapper.selectById(gfpId);
                    if (groupFundPlan!=null){
                        SysFile fileEntity =  sysFileService.getById(gfpId);
                        if (fileEntity!=null){
                            fileEntity.setFileflowstatus(String.valueOf(ApprovalStatusEnum.Returned_Initiator.getIndex()));
                            sysFileService.updateById(fileEntity);
                        }
                        groupFundPlan.setState(ApprovalStatusEnum.Returned_Initiator.getIndex());
                        groupFundPlanMapper.updateById(groupFundPlan);
                    }
                }
            }
            else {
                List<GroupFundPlan> groupFundPlans = groupFundPlanSummaryDetailService.getGroupFundPlanById(request.getGfpDetailIds());
                if (CollectionUtil.isNotEmpty(groupFundPlans)){
                    for (GroupFundPlan groupFundPlan:groupFundPlans) {
                        if (groupFundPlan!=null){
                            SysFile fileEntity =  sysFileService.getById(groupFundPlan.getId());
                            if (fileEntity!=null){
                                fileEntity.setFileflowstatus(String.valueOf(ApprovalStatusEnum.Returned_Initiator.getIndex()));
                                sysFileService.updateById(fileEntity);
                            }
                            groupFundPlan.setState(ApprovalStatusEnum.Returned_Initiator.getIndex());
                            groupFundPlanMapper.updateById(groupFundPlan);
                        }
                    }
                }
            }
        }else {
            /// 集团
            List<GroupFundPlanSummary> groupFundPlanSummarys
                    = baseMapper.getGroupFundPlanSummaryByDetailId(request.getGfpDetailIds());
            if (CollectionUtil.isNotEmpty(groupFundPlanSummarys)){
                for (GroupFundPlanSummary groupFundPlanSummary:groupFundPlanSummarys) {
                    if (groupFundPlanSummary!=null){
                        SysFile fileEntity =  sysFileService.getById(groupFundPlanSummary.getId());
                        if (fileEntity!=null){
                            fileEntity.setFileflowstatus(String.valueOf(ApprovalStatusEnum.Returned_Initiator.getIndex()));
                            sysFileService.updateById(fileEntity);
                        }
                        groupFundPlanSummary.setReturnType(request.getReturnType());
                        groupFundPlanSummary.setState(ApprovalStatusEnum.Returned_Initiator.getIndex());
                        baseMapper.updateById(groupFundPlanSummary);
                    }
                }
            }
        }

        return BaseResponse.ok();
    }

    private  void getSummaryInfo(GroupFundPlanSummaryVo groupFundPlanSummaryVo) {
        List<GroupFundPlanSummaryDetailVo> detailVos= groupFundPlanSummaryVo.getDetailVos();
        List<GroupFundPlanSummaryDetailTotalVo> detailTotalVos =new ArrayList<>();
        if (CollectionUtil.isNotEmpty(detailVos)){
            for(GroupFundPlanSummaryDetailVo detailVo:detailVos){
                if (detailVo.getGfpFundApplyAmountGcAudit()==null){
                    detailVo.setGfpFundApplyAmountGcAudit(detailVo.getGfpFundApplyAmount());
                }
                getProjectTotal(detailVo);
            }
            //生成统计信息
            //小计
            List<String> gfpsProjectCompetentDepartmentIds = detailVos.stream().map(GroupFundPlanSummaryDetailVo::getGfpsProjectCompetentDepartmentId).distinct().collect(Collectors.toList());
            for (String gfpsProjectCompetentDepartmentId:gfpsProjectCompetentDepartmentIds){
                GroupFundPlanSummaryDetailTotalVo detailTotalVo =new GroupFundPlanSummaryDetailTotalVo();
                detailTotalVo.setId(GenerateUUID.getUUID());
                detailTotalVo.setGfpsId(groupFundPlanSummaryVo.getId());
                String name =detailVos.stream()
                        .filter(n->gfpsProjectCompetentDepartmentId.equals(n.getGfpsProjectCompetentDepartmentId()))
                        .findFirst().get().getGfpsProjectCompetentDepartmentName();
                String departmentName;
                //1、一分中心（建管）→南京城市建设管理集团有限公司
                //2、二分中心（隧桥）→南京城建城市运营集团有限公司
                //3、三分中心（市政）→南京市城建市政工程管理有限公司
                if ("一分中心（建管）".equals(name))  {
                    departmentName="南京城市建设管理集团有限公司";
                } else if ("二分中心（隧桥）".equals(name)) {
                    departmentName="南京城建城市运营集团有限公司";
                } else if ("三分中心（市政）".equals(name)) {
                    departmentName="南京市城建市政工程管理有限公司";
                } else {
                    departmentName=name;
                }
                detailTotalVo.setGfpsProjectCompetentDepartmentName(departmentName);
                detailTotalVo.setGfpsProjectCompetentDepartmentId(gfpsProjectCompetentDepartmentId);
                detailTotalVo.setProjectTotal(BigDecimal.ZERO);
                detailTotalVo.setGfpArrivedFunds(BigDecimal.ZERO);
                detailTotalVo.setGfpFundApplyAmount(BigDecimal.ZERO);
                detailTotalVo.setGfpFundDemandAmount(BigDecimal.ZERO);
                detailTotalVo.setGfpMemberBalanceFunds(BigDecimal.ZERO);
                detailTotalVo.setGfpBalanceFunds(BigDecimal.ZERO);
                detailTotalVo.setGfpFundApplyAmountGcAudit(BigDecimal.ZERO);
                detailTotalVo.setGfpFundApplyAmountCwAudit(BigDecimal.ZERO);
                detailTotalVos.add(detailTotalVo);
            }
            //总计
            GroupFundPlanSummaryDetailTotalVo detailTotalVo =new GroupFundPlanSummaryDetailTotalVo();
            detailTotalVo.setId(GenerateUUID.getUUID());
            detailTotalVo.setGfpsId(groupFundPlanSummaryVo.getId());
            detailTotalVo.setGfpsProjectCompetentDepartmentName("all");
            detailTotalVo.setGfpsProjectCompetentDepartmentId("all");
            detailTotalVo.setProjectTotal(BigDecimal.ZERO);
            detailTotalVo.setGfpArrivedFunds(BigDecimal.ZERO);
            detailTotalVo.setGfpFundApplyAmount(BigDecimal.ZERO);
            detailTotalVo.setGfpFundDemandAmount(BigDecimal.ZERO);
            detailTotalVo.setGfpMemberBalanceFunds(BigDecimal.ZERO);
            detailTotalVo.setGfpBalanceFunds(BigDecimal.ZERO);
            detailTotalVo.setGfpFundApplyAmountGcAudit(BigDecimal.ZERO);
            detailTotalVo.setGfpFundApplyAmountCwAudit(BigDecimal.ZERO);
            detailTotalVos.add(detailTotalVo);
            //生成总投资
            for (GroupFundPlanSummaryDetailVo detailVo:detailVos) {
               //找到和当前单位一致的
               List<GroupFundPlanSummaryDetailTotalVo> detailTotalVos1 = detailTotalVos.stream()
                       .filter(n->(n.getGfpsProjectCompetentDepartmentId().equals(detailVo.getGfpsProjectCompetentDepartmentId())||n.getGfpsProjectCompetentDepartmentId().equals("all"))).collect(Collectors.toList());
               if (CollectionUtil.isNotEmpty(detailTotalVos1)){
                   for (GroupFundPlanSummaryDetailTotalVo detailTotalVo2:detailTotalVos1){
                      if (detailVo.getProjectTotal()!=null) {
                          detailTotalVo2.setProjectTotal(detailTotalVo2.getProjectTotal().add(detailVo.getProjectTotal()));
                      }
                      if (detailVo.getGfpArrivedFunds()!=null){
                          detailTotalVo2.setGfpArrivedFunds(detailTotalVo2.getGfpArrivedFunds().add(detailVo.getGfpArrivedFunds()));
                      }
                      if (detailVo.getGfpBalanceFunds()!=null){
                          detailTotalVo2.setGfpBalanceFunds(detailTotalVo2.getGfpBalanceFunds().add(detailVo.getGfpBalanceFunds()));
                      }
                      if (detailVo.getGfpMemberBalanceFunds()!=null){
                          detailTotalVo2.setGfpMemberBalanceFunds(detailTotalVo2.getGfpMemberBalanceFunds().add(detailVo.getGfpMemberBalanceFunds()));
                      }
                      if (detailVo.getGfpFundDemandAmount()!=null){
                          detailTotalVo2.setGfpFundDemandAmount(detailTotalVo2.getGfpFundDemandAmount().add(detailVo.getGfpFundDemandAmount()));
                      }
                      if (detailVo.getGfpFundApplyAmount()!=null){
                          detailTotalVo2.setGfpFundApplyAmount(detailTotalVo2.getGfpFundApplyAmount().add(detailVo.getGfpFundApplyAmount()));
                      }
                      if (detailVo.getGfpFundApplyAmountGcAudit()!=null){
                          detailTotalVo2.setGfpFundApplyAmountGcAudit(detailTotalVo2.getGfpFundApplyAmountGcAudit().add(detailVo.getGfpFundApplyAmountGcAudit()));
                      }
                      if (detailVo.getGfpFundApplyAmountCwAudit()!=null){
                          detailTotalVo2.setGfpFundApplyAmountCwAudit(detailTotalVo2.getGfpFundApplyAmountCwAudit().add(detailVo.getGfpFundApplyAmountCwAudit()));
                      }
                   }
               }
            }

        }
        groupFundPlanSummaryVo.setDetailTotalVos(detailTotalVos);
    }


    private  void getSummaryInfoDetails(GroupFundPlanSummaryVo groupFundPlanSummaryVo) {
        List<GroupFundPlanSummaryDetailVo> detailVos= groupFundPlanSummaryVo.getDetailVos();
        setIsNew(groupFundPlanSummaryVo,detailVos);
        List<GroupFundPlanSummaryDetailTotalVo> detailTotalVos =new ArrayList<>();
        if (CollectionUtil.isNotEmpty(detailVos)){
            for(GroupFundPlanSummaryDetailVo detailVo:detailVos){
                getProjectTotal(detailVo);
            }
            //生成统计信息
            //小计
            List<String> gfpsProjectCompetentDepartmentIds = detailVos.stream().map(GroupFundPlanSummaryDetailVo::getGfpsProjectCompetentDepartmentId).distinct().collect(Collectors.toList());
            for (String gfpsProjectCompetentDepartmentId:gfpsProjectCompetentDepartmentIds){
                GroupFundPlanSummaryDetailTotalVo detailTotalVo =new GroupFundPlanSummaryDetailTotalVo();
                detailTotalVo.setId(GenerateUUID.getUUID());
                detailTotalVo.setGfpsId(groupFundPlanSummaryVo.getId());
                String name =detailVos.stream()
                        .filter(n->gfpsProjectCompetentDepartmentId.equals(n.getGfpsProjectCompetentDepartmentId()))
                        .findFirst().get().getGfpsProjectCompetentDepartmentName();
                String departmentName;
                //1、一分中心（建管）→南京城市建设管理集团有限公司
                //2、二分中心（隧桥）→南京城建城市运营集团有限公司
                //3、三分中心（市政）→南京市城建市政工程管理有限公司
                if ("一分中心（建管）".equals(name))  {
                    departmentName="南京城市建设管理集团有限公司";
                } else if ("二分中心（隧桥）".equals(name)) {
                    departmentName="南京城建城市运营集团有限公司";
                } else if ("三分中心（市政）".equals(name)) {
                    departmentName="南京市城建市政工程管理有限公司";
                } else {
                    departmentName=name;
                }
                detailTotalVo.setGfpsProjectCompetentDepartmentName(departmentName);
                detailTotalVo.setGfpsProjectCompetentDepartmentId(gfpsProjectCompetentDepartmentId);
                detailTotalVo.setProjectTotal(BigDecimal.ZERO);
                detailTotalVo.setGfpArrivedFunds(BigDecimal.ZERO);
                detailTotalVo.setGfpFundApplyAmount(BigDecimal.ZERO);
                detailTotalVo.setGfpFundDemandAmount(BigDecimal.ZERO);
                detailTotalVo.setGfpMemberBalanceFunds(BigDecimal.ZERO);
                detailTotalVo.setGfpBalanceFunds(BigDecimal.ZERO);
                detailTotalVo.setGfpFundApplyAmountGcAudit(BigDecimal.ZERO);
                detailTotalVo.setGfpFundApplyAmountCwAudit(BigDecimal.ZERO);
                detailTotalVos.add(detailTotalVo);
            }
            //总计
            GroupFundPlanSummaryDetailTotalVo detailTotalVo =new GroupFundPlanSummaryDetailTotalVo();
            detailTotalVo.setId(GenerateUUID.getUUID());
            detailTotalVo.setGfpsId(groupFundPlanSummaryVo.getId());
            detailTotalVo.setGfpsProjectCompetentDepartmentName("all");
            detailTotalVo.setGfpsProjectCompetentDepartmentId("all");
            detailTotalVo.setProjectTotal(BigDecimal.ZERO);
            detailTotalVo.setGfpArrivedFunds(BigDecimal.ZERO);
            detailTotalVo.setGfpFundApplyAmount(BigDecimal.ZERO);
            detailTotalVo.setGfpFundDemandAmount(BigDecimal.ZERO);
            detailTotalVo.setGfpMemberBalanceFunds(BigDecimal.ZERO);
            detailTotalVo.setGfpBalanceFunds(BigDecimal.ZERO);
            detailTotalVo.setGfpFundApplyAmountGcAudit(BigDecimal.ZERO);
            detailTotalVo.setGfpFundApplyAmountCwAudit(BigDecimal.ZERO);
            detailTotalVos.add(detailTotalVo);
            //生成总投资
            for (GroupFundPlanSummaryDetailVo detailVo:detailVos) {
               //找到和当前单位一致的
               List<GroupFundPlanSummaryDetailTotalVo> detailTotalVos1 = detailTotalVos.stream()
                       .filter(n->(n.getGfpsProjectCompetentDepartmentId().equals(detailVo.getGfpsProjectCompetentDepartmentId())||n.getGfpsProjectCompetentDepartmentId().equals("all"))).collect(Collectors.toList());
               if (CollectionUtil.isNotEmpty(detailTotalVos1)){
                   for (GroupFundPlanSummaryDetailTotalVo detailTotalVo2:detailTotalVos1){
                      if (detailVo.getProjectTotal()!=null) {
                          detailTotalVo2.setProjectTotal(detailTotalVo2.getProjectTotal().add(detailVo.getProjectTotal()));
                      }
                      if (detailVo.getGfpArrivedFunds()!=null){
                          detailTotalVo2.setGfpArrivedFunds(detailTotalVo2.getGfpArrivedFunds().add(detailVo.getGfpArrivedFunds()));
                      }
                      if (detailVo.getGfpBalanceFunds()!=null){
                          detailTotalVo2.setGfpBalanceFunds(detailTotalVo2.getGfpBalanceFunds().add(detailVo.getGfpBalanceFunds()));
                      }
                      if (detailVo.getGfpMemberBalanceFunds()!=null){
                          detailTotalVo2.setGfpMemberBalanceFunds(detailTotalVo2.getGfpMemberBalanceFunds().add(detailVo.getGfpMemberBalanceFunds()));
                      }
                      if (detailVo.getGfpFundDemandAmount()!=null){
                          detailTotalVo2.setGfpFundDemandAmount(detailTotalVo2.getGfpFundDemandAmount().add(detailVo.getGfpFundDemandAmount()));
                      }
                      if (detailVo.getGfpFundApplyAmount()!=null){
                          detailTotalVo2.setGfpFundApplyAmount(detailTotalVo2.getGfpFundApplyAmount().add(detailVo.getGfpFundApplyAmount()));
                      }
                      if (detailVo.getGfpFundApplyAmountGcAudit()!=null){
                          detailTotalVo2.setGfpFundApplyAmountGcAudit(detailTotalVo2.getGfpFundApplyAmountGcAudit().add(detailVo.getGfpFundApplyAmountGcAudit()));
                      }
                      if (detailVo.getGfpFundApplyAmountCwAudit()!=null){
                          detailTotalVo2.setGfpFundApplyAmountCwAudit(detailTotalVo2.getGfpFundApplyAmountCwAudit().add(detailVo.getGfpFundApplyAmountCwAudit()));
                      }
                   }
               }
            }

        }
        groupFundPlanSummaryVo.setDetailTotalVos(detailTotalVos);
    }

    private void getProjectTotal(GroupFundPlanSummaryDetailVo detailVo) {
        if(detailVo.getProjectTotal()==null){
            if (StrUtil.isNotEmpty(detailVo.getProjectId())){
                PreProject project = preProjectMapper.selectById(detailVo.getProjectId());
                if (project!=null){
                    if (project.getpApproveBudget()!=null){
                        detailVo.setProjectTotal(project.getpApproveBudget());
                    }else{
                        detailVo.setProjectTotal(BigDecimal.ZERO);
                    }
                }else{
                    detailVo.setProjectTotal(BigDecimal.ZERO);
                }
            }else{
                detailVo.setProjectTotal(BigDecimal.ZERO);
            }
        }
    }

    /**
     * 获取成员企业下各个事业部/分支机构的项目资金计划信息
     *
     * @param request
     * @param user
     * @return
     */
    @Override
    public BaseResponse getCompanyProjectFundPlan(GroupFundPlanSummaryRequest request, LogUserInfoVo user) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isNull(user) || ValidateUtil.isEmpty(user.getDepartmentIds())) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        List<SysDepartment> sysDepartments = sysDepartmentMapper.selectList(
                Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getdStatus, "1"));
        if (CollectionUtil.isEmpty(sysDepartments)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        Map<String, SysDepartment> deptMap = sysDepartments.stream()
                .collect(Collectors.toMap(SysDepartment::getdId, Function.identity()));

        List<String> userDeptIds = user.getDepartmentIds().stream()
                .map(deptId -> findNearestUnitDepartment(deptId, deptMap))
                .filter(Objects::nonNull)
                .map(SysDepartment::getdId)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(userDeptIds)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        request.setDepartmentIds(userDeptIds);
        request.setGroupType("01");

        List<GroupFundPlanSummaryDetailVo> dtos = baseMapper.getCompanyProjectFundPlan(request);
        if (CollectionUtil.isEmpty(dtos)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        Map<String, List<GroupFundPlanSummaryDetailVo>> competentDeptMap = dtos.stream()
                .filter(vo -> StrUtil.isNotBlank(vo.getGfpsProjectCompetentDepartmentId()))
                .collect(Collectors.groupingBy(GroupFundPlanSummaryDetailVo::getGfpsProjectCompetentDepartmentId));

        List<GroupFundPlanSummaryDetailVo> treeList = new ArrayList<>();

        for (Map.Entry<String, List<GroupFundPlanSummaryDetailVo>> competentEntry : competentDeptMap.entrySet()) {
            List<GroupFundPlanSummaryDetailVo> competentDeptList = competentEntry.getValue();
            GroupFundPlanSummaryDetailVo first = competentDeptList.get(0);

            // 事业部节点
            GroupFundPlanSummaryDetailVo competentNode = new GroupFundPlanSummaryDetailVo();
            competentNode.setId(GenerateUUID.getUUID());
            competentNode.setGfpMonth(first.getGfpMonth());
            competentNode.setGfpYear(first.getGfpYear());
            competentNode.setGfpsProjectCompetentDepartmentId(first.getGfpsProjectCompetentDepartmentId());
            competentNode.setGfpsProjectCompetentDepartmentName(first.getGfpsProjectCompetentDepartmentName());
            competentNode.setGfpsProjectDepartmentId(first.getGfpsProjectDepartmentId());
            competentNode.setGfpsProjectDepartmentName(first.getGfpsProjectDepartmentName());
            Map<String, List<GroupFundPlanSummaryDetailVo>> deptMap2 = competentDeptList.stream()
                    .filter(vo -> StrUtil.isNotBlank(vo.getGfpsProjectDepartmentId()))
                    .collect(Collectors.groupingBy(GroupFundPlanSummaryDetailVo::getGfpsProjectDepartmentId));

            List<GroupFundPlanSummaryDetailVo> deptNodes = new ArrayList<>();
            for (Map.Entry<String, List<GroupFundPlanSummaryDetailVo>> deptEntry : deptMap2.entrySet()) {
                List<GroupFundPlanSummaryDetailVo> deptList = deptEntry.getValue();
                GroupFundPlanSummaryDetailVo deptFirst = deptList.get(0);
                GroupFundPlanSummaryDetailVo deptNode = new GroupFundPlanSummaryDetailVo();
                deptNode.setId(GenerateUUID.getUUID());
                deptNode.setGfpsProjectCompetentDepartmentId(deptFirst.getGfpsProjectCompetentDepartmentId());
                deptNode.setGfpsProjectCompetentDepartmentName(deptFirst.getGfpsProjectCompetentDepartmentName());
                deptNode.setGfpsProjectDepartmentId(deptFirst.getGfpsProjectDepartmentId());
                deptNode.setGfpsProjectDepartmentName(deptFirst.getGfpsProjectDepartmentName());
                deptNode.setChildren(deptList);
                deptNodes.add(deptNode);
            }
            competentNode.setChildren(deptNodes);
            treeList.add(competentNode);
        }
        return BaseResponse.ok(treeList);
    }

    /**
     * 获取最近的单位部门
     *
     * @param departmentId
     * @param deptMap
     * @return
     */
    private SysDepartment findNearestUnitDepartment(String departmentId, Map<String, SysDepartment> deptMap) {
        SysDepartment current = deptMap.get(departmentId);
        while (current != null && !"0".equals(current.getdParentid())) {
            if ("1".equals(current.getdType())) {
                return current;
            }
            SysDepartment parent = deptMap.get(current.getdParentid());
            if (parent != null && "1".equals(parent.getdType())) {
                return parent;
            }
            current = parent;
        }
        return current != null && "1".equals(current.getdType()) ? current : null;
    }

    /**
     * 获取集团下各个成员企业的项目资金计划信息
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getGroupProjectFundPlan(GroupFundPlanSummaryRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<SysDepartment> sysDepartments = sysDepartmentMapper.selectList(
                Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getdStatus, "1")
                        .eq(SysDepartment::getdType, "1")
        );
        if (CollectionUtil.isEmpty(sysDepartments)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        List<String> userDeptIds = sysDepartments.stream()
                .map(SysDepartment::getdId)
                .distinct()
                .collect(Collectors.toList());
        request.setDepartmentIds(userDeptIds);
        request.setGfpsType(1);
        request.setGroupType("01");

        List<GroupFundPlanSummaryDetailVo> dtos = baseMapper.getCompanyProjectFundPlan(request);
        if (CollectionUtil.isEmpty(dtos)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        Map<String, List<GroupFundPlanSummaryDetailVo>> competentDeptMap = dtos.stream()
                .filter(vo -> StrUtil.isNotBlank(vo.getGfpsProjectCompetentDepartmentId()))
                .collect(Collectors.groupingBy(GroupFundPlanSummaryDetailVo::getGfpsProjectCompetentDepartmentId));

        List<GroupFundPlanSummaryDetailVo> treeList = new ArrayList<>();
        for (Map.Entry<String, List<GroupFundPlanSummaryDetailVo>> competentEntry : competentDeptMap.entrySet()) {
            List<GroupFundPlanSummaryDetailVo> competentDeptList = competentEntry.getValue();
            GroupFundPlanSummaryDetailVo first = competentDeptList.get(0);

            // 事业部节点
            GroupFundPlanSummaryDetailVo competentNode = new GroupFundPlanSummaryDetailVo();
            competentNode.setId(GenerateUUID.getUUID());
            competentNode.setGfpMonth(first.getGfpMonth());
            competentNode.setGfpYear(first.getGfpYear());

            competentNode.setGfpsProjectCompetentDepartmentId(first.getGfpsProjectCompetentDepartmentId());
            competentNode.setGfpsProjectCompetentDepartmentName(first.getGfpsProjectCompetentDepartmentName());
            competentNode.setGfpsProjectDepartmentId(first.getGfpsProjectDepartmentId());
            competentNode.setGfpsProjectDepartmentName(first.getGfpsProjectDepartmentName());
            competentNode.setChildren(competentDeptList);
            treeList.add(competentNode);
        }
        return BaseResponse.ok(treeList);
    }

    /**
     * 根据pid获取资金计划汇总详情
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getGroupFundPlanSummaryDetailByPid(GroupFundPlanSummaryDetailDto request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getPId()) || ValidateUtil.isNull(request.getGfpsType())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<GroupFundPlanSummaryDetailDto> page = new Page<>(request.getPage(), request.getSize());
        List<GroupFundPlanSummaryDetailVo> detailVos = baseMapper.getGroupFundPlanSummaryDetailByPid(page, request);
        return BaseResponse.ok(detailVos, Math.toIntExact(page.getTotal()));
    }

    /**
     * 刷新资金计划历史数据
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse refreshHistoryData(GroupFundPlanSummaryDto request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 当前最新的版本的id
        String mainId = request.getId();
        GroupFundPlanSummary plan = this.getById(mainId);
        if (ValidateUtil.isNull(plan)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!ApprovalStatusEnum.Already_Over.getIndex().equals(plan.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        // 历史组id
        String historyGroupId = request.getGroupId();
        this.update(Wrappers.<GroupFundPlanSummary>lambdaUpdate()
                .eq(GroupFundPlanSummary::getGroupId, historyGroupId)
                .set(GroupFundPlanSummary::getGroupMainType, "old")
                .ne(GroupFundPlanSummary::getId, mainId)
        );
        this.update(Wrappers.<GroupFundPlanSummary>lambdaUpdate()
                .set(GroupFundPlanSummary::getGroupMainType, "new")
                .set(GroupFundPlanSummary::getGroupId, historyGroupId)
                .eq(GroupFundPlanSummary::getId, mainId)
        );
        return BaseResponse.ok();
    }

    /**
     * 根据id删除资金计划汇总信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteGroupFundPlanSummaryById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        long count = conPayService.count(Wrappers.<ConPay>lambdaQuery()
                .eq(ConPay::getGfpdId, id)
                .ne(ConPay::getPayState, ApprovalStatusEnum.Invalid.getIndex())
        );
        if (count > 0) {
            return BaseResponse.fail("该资金计划已被使用，无法删除");
        }
        GroupFundPlanSummary summary = this.getById(id);
        if (ValidateUtil.isNull(summary)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!ApprovalStatusEnum.Invalid.getIndex().equals(summary.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        this.removeById(id);
        if ("new".equals(summary.getGroupMainType())) {
            // 查询同一组的最新版本
            List<GroupFundPlanSummary> list = this.list(Wrappers.<GroupFundPlanSummary>lambdaQuery()
                    .eq(GroupFundPlanSummary::getGroupId, summary.getGroupId())
                    .orderByDesc(BaseEntity::getStateTime)
            );
            if (ValidateUtil.isNotEmpty(list)) {
                GroupFundPlanSummary latest = list.get(0);
                latest.setGroupMainType("new");
                this.updateById(latest);
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 根据项目资金计划id判断是否存在关联的资金计划汇总数据
     *
     * @param gfpId
     * @return
     */
    @Override
    public boolean existUseByGfpId(String gfpId) {
        long count = baseMapper.existUseByGfpId(gfpId);
        return count > 0;
    }

    /**
     * 判断是否能够提交
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse existProject(GroupFundPlanSummaryRequest request) {
        return BaseResponse.ok(!existProjectSubmit(request.getId(), request.getGfpsYear(), request.getGfpsMonth(), request.getGfpsType(), request.getDepartmentIds()));
    }

    /**
     * 判断是否存在审批中的资金计划
     *
     * @param groupId
     * @param gfpsType
     * @return
     */
    private boolean existProjectChange(String groupId, Integer gfpsType) {
        return baseMapper.exists(Wrappers.<GroupFundPlanSummary>lambdaQuery()
                .eq(GroupFundPlanSummary::getGroupId, groupId)
                .eq(GroupFundPlanSummary::getGfpsType, gfpsType)
                .ne(BaseEntity::getState, ApprovalStatusEnum.Invalid.getIndex())
                .ne(BaseEntity::getState, ApprovalStatusEnum.Already_Over.getIndex()));
    }

    /**
     * 判断当前年月是否存在已审批结束的资金计划
     *
     * @param gfpsYear
     * @param gfpsMonth
     * @return
     */
    private boolean existProjectSubmit(String id, Integer gfpsYear, Integer gfpsMonth, Integer gfpsType, List<String> departmentIds) {
        List<String> dIds = baseMapper.existProjectSubmit(id, gfpsYear, gfpsMonth, gfpsType);
        if (ValidateUtil.isEmpty(dIds)) {
            return false;
        } else if (ValidateUtil.isNotEmpty(departmentIds)) {
            List<SysDepartment> sysDepartments = sysDepartmentMapper.selectList(
                    Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getdStatus, "1"));
            if (ValidateUtil.isEmpty(sysDepartments)) {
                return false;
            }

            Map<String, SysDepartment> deptMap = sysDepartments.stream()
                    .collect(Collectors.toMap(SysDepartment::getdId, Function.identity()));

            List<String> deptIds = dIds.stream()
                    .map(deptId -> findNearestUnitDepartment(deptId, deptMap))
                    .filter(Objects::nonNull)
                    .map(SysDepartment::getdId)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> nearestUnitDepartments = departmentIds.stream()
                    .map(deptId -> findNearestUnitDepartment(deptId, deptMap))
                    .filter(Objects::nonNull)
                    .map(SysDepartment::getdId)
                    .distinct()
                    .collect(Collectors.toList());

            if (ValidateUtil.isEmpty(deptIds) || ValidateUtil.isEmpty(nearestUnitDepartments)) {
                return false;
            }
            // 判断传入的这个部门id是否在已存在的部门id列表中，如果在，则表示存在已审批结束的资金计划
            return deptIds.stream().anyMatch(nearestUnitDepartments::contains);
        } else {
            return true;
        }
    }
}
