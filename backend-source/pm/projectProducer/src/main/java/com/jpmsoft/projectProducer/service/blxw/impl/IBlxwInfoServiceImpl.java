package com.jpmsoft.projectProducer.service.blxw.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeCloseInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeInfoModel;
import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.blxw.BlxwInfoMapper;
import com.jpmsoft.projectProducer.mapper.evaluate.ZNPJEvaluateBasisInformationQuarterMapper;
import com.jpmsoft.projectProducer.mapper.evaluate.ZNPJIndicatorMapper;
import com.jpmsoft.projectProducer.mapper.other.NoticeInfoCloseMapper;
import com.jpmsoft.projectProducer.mapper.other.NoticeInfoMapper;
import com.jpmsoft.projectProducer.mapper.other.SysFileMapper;
import com.jpmsoft.projectProducer.pojo.dto.blxw.BlxwInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwInfo;
import com.jpmsoft.projectProducer.pojo.entity.evaluate.ZNPJEvaluateBasisInformationQuarter;
import com.jpmsoft.projectProducer.pojo.entity.evaluate.ZNPJIndicator;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfo;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfoClose;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.request.blxw.BlxwInfoRequest;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoNoticeCloseVo;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoNoticeVo;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoPrintVo;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoVo;
import com.jpmsoft.projectProducer.service.blxw.IBlxwInfoService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IBlxwInfoServiceImpl extends BaseFileImpl<BlxwInfoMapper, BlxwInfo, BlxwInfoDto> implements IBlxwInfoService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private NoticeInfoMapper noticeInfoMapper;
    @Autowired
    private NoticeInfoCloseMapper noticeInfoCloseMapper;
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private ZNPJEvaluateBasisInformationQuarterMapper znpjEvaluateBasisInformationQuarterMapper;
    @Autowired
    private ZNPJIndicatorMapper znpjIndicatorMapper;
    @Override
    public BaseResponse saveOrUpdateBlxwInfo(BlxwInfoDto dto) {
        String supportingDocument = dto.getSupportingDocument();
        String supportingDocumentFileIds = dto.getSupportingDocumentFileIds();
        if(StrUtil.isEmpty(supportingDocument)||StrUtil.isEmpty(supportingDocumentFileIds)){
            return BaseResponse.fail("请上传附件");
        }
        String primaryId = dto.getId();
        BlxwInfo entity ;
        if (primaryId == null || primaryId.isEmpty()) {
            entity = new BlxwInfo();
            BeanUtil.copyProperties(dto,entity);
            entity.setCreateTime(DateTime.now());
            primaryId=GenerateUUID.getUUID();
            entity.setId(primaryId);
            String seqCode = "BLXWCode";
            String code = generateCodeByTCode(seqCode,dto);
            if (ValidateUtil.isEmpty(code))
                 return new BaseResponse("编号生成失败");
            entity.setBlxwNumber(code);
            refreshAutoNo(seqCode,dto);
            entity.setBlxwState(0);
            entity.setCloseStatus("unclosed");
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            String blxwTypeIds=entity.getBlxwTypeIds();
            String typeNames = "";
            setTypeNames(entity,blxwTypeIds,typeNames);
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(dto.getFileRelationId(),supportingDocument), entity.getId());
            //创建流程
            if(sysFileService.FileHelper(entity,dto,"create",dto.getFileRelationId())){

            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        } else {
            entity = baseMapper.selectById(primaryId);
            String typeNames = "";
            String blxwTypeIds=entity.getBlxwTypeIds();
            setTypeNames(entity,blxwTypeIds,typeNames);
            BeanUtil.copyProperties(dto,entity);
            baseMapper.updateById(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(dto.getFileRelationId(),supportingDocument), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    private void setTypeNames(BlxwInfo entity,String blxwTypeIds,String typeNames) {
        if(ValidateUtil.isNotEmpty(blxwTypeIds)){
            String[] blxwTypeId = blxwTypeIds.split(",");
            //log.info("blxwTypeId:{}",JacksonUtils.toJSONString(blxwTypeId));
            for (String type : blxwTypeId) {
                String typeName = baseMapper.getBlxwTypeNameByTypeId(type);
                if (StrUtil.isNotEmpty(typeName)){
                    typeNames+=typeName+",";
                }
            }
            if (typeNames.length()>0){
                typeNames = typeNames.substring(0,typeNames.length()-1);
            }
            entity.setBlxwTypeNames(typeNames);
        }
    }

    @Override
    public BaseResponse approvalData(BlxwInfoRequest request) {
        BlxwInfo entity ;
        SysFile fileEntity;
        String id =request.getId();
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null)
                return new BaseResponse("信息不存在，请刷新重试！");
            fileEntity = sysFileService.getById(id);
            /**
             * 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
             * 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(new Date());
            if (fileFlowStatus.equals(ApprovalStatusEnum.Already_Over.getIndex().toString())){
                if ("01".equals(entity.getBlxwHandleType())){
                    BlxwInfoVo blxwInfoVo= baseMapper.getBlxwInfoById(id);
                    generateBlxwNoticeInfo(blxwInfoVo);
                    entity.setCloseStatus("closed");
                }else{
                    entity.setCloseStatus("unclosed");
                }
                QueryWrapper<ZNPJIndicator> znpjIndicatorQueryWrapper = new QueryWrapper<>();
                znpjIndicatorQueryWrapper.eq("del_flag", 1).eq("status", 1);
                List<ZNPJIndicator> znpjIndicatorList = znpjIndicatorMapper.selectList(znpjIndicatorQueryWrapper);
                ZNPJEvaluateBasisInformationQuarter znpjEvaluateBasisInformationQuarter =generateBlxwBasicInfo(znpjIndicatorList,
                        entity);
                log.info("znpjEvaluateBasisInformationQuarter:{}",JacksonUtils.toJSONString(znpjEvaluateBasisInformationQuarter));
                generate(znpjEvaluateBasisInformationQuarter);
            }
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getBlxwInfoList(BlxwInfoRequest request,String token) {
        log.info("request:{}",JacksonUtils.toJSONString(request));
        IPage<BlxwInfoVo> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<BlxwInfoRequest> params = new QueryMap<>(request);
        params.put("blxwNumber").put("blxwProjectId").put("blxwProjectName").put("blxwSectionId").put("blxwSectionName")
        .put("blxwContractId").put("blxwContractName").put("blxwResponsibleUnitId").put("blxwResponsibleUnitName")
        .put("blxwResponsibleUnitLeaderId").put("blxwResponsibleUnitLeaderName").put("blxwHandleType")
        .put("blxwState").put("state").put("blxwLevels").put("closeStatus");
        params.put("nowDate",new Date());
        log.info("params:{}",JacksonUtils.toJSONString(params));
        List<BlxwInfoVo> blxwInfoVos = baseMapper.getBlxwInfoList(params.getQuery(), page);
        for (BlxwInfoVo blxwInfoVo : blxwInfoVos){
            getTypeNames(blxwInfoVo);
        }
        log.info("blxwInfoVos:{}",JacksonUtils.toJSONString(blxwInfoVos));
        return BaseResponse.ok(blxwInfoVos,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getBlxwInfoDetail(String id) {
        BlxwInfoVo blxwInfoVo= baseMapper.getBlxwInfoById(id);
        if(blxwInfoVo==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        getTypeNames(blxwInfoVo);
        log.info("查询信息：{}",blxwInfoVo);
        return BaseResponse.ok(blxwInfoVo);
    }

    private void getTypeNames(BlxwInfoVo blxwInfoVo) {
        String typeNames = "";
        String blxwTypeIds=blxwInfoVo.getBlxwTypeIds();
        if(ValidateUtil.isNotEmpty(blxwTypeIds)){
            String[] blxwTypeId = blxwTypeIds.split(",");
            //log.info("blxwTypeId:{}",JacksonUtils.toJSONString(blxwTypeId));
            for (String type : blxwTypeId) {
                String typeName = baseMapper.getBlxwTypeNameByTypeId(type);
                if (StrUtil.isNotEmpty(typeName)){
                    typeNames+=typeName+",";
                }
            }
            if (!typeNames.isEmpty()){
                typeNames = typeNames.substring(0,typeNames.length()-1);
            }
            blxwInfoVo.setBlxwTypeNames(typeNames);
        }
    }

    private void getTypeNames(BlxwInfoPrintVo blxwInfoVo) {
        String typeNames = "";
        String blxwTypeIds=blxwInfoVo.getBlxwTypeIds();
        if(ValidateUtil.isNotEmpty(blxwTypeIds)){
            String[] blxwTypeId = blxwTypeIds.split(",");
            //log.info("blxwTypeId:{}",JacksonUtils.toJSONString(blxwTypeId));
            for (String type : blxwTypeId) {
                String typeName = baseMapper.getBlxwTypeNameByTypeId(type);
                if (StrUtil.isNotEmpty(typeName)){
                    typeNames+=typeName+",";
                }
            }
            if (!typeNames.isEmpty()){
                typeNames = typeNames.substring(0,typeNames.length()-1);
            }
            blxwInfoVo.setBlxwTypeNames(typeNames);
        }
    }

    @Override
    public boolean canToDeleteBlxwType(String id) {
        List<BlxwInfo> list = baseMapper.selectListByTypeId(id);
        if (!list.isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public BaseResponse getBlxwInfoNoticeDetail(String id) {
        BlxwInfoNoticeVo blxwInfoVo= baseMapper.getBlxwInfoNoticeById(id);
        if(blxwInfoVo==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        getTypeNames(blxwInfoVo);
        log.info("查询信息：{}",blxwInfoVo);
        return BaseResponse.ok(blxwInfoVo);
    }
    private QueryMap<BlxwInfoRequest> getFilter(BlxwInfoRequest request) {
        QueryMap<BlxwInfoRequest> params = new QueryMap<>(request);
        params.put("blxwNumber").put("blxwProjectId").put("blxwProjectName").put("blxwSectionId").put("blxwSectionName")
                .put("blxwContractId").put("blxwContractName").put("blxwResponsibleUnitId").put("blxwResponsibleUnitName")
                .put("blxwResponsibleUnitLeaderId").put("blxwResponsibleUnitLeaderName").put("blxwState").put("blxwState").put("noticeInfoCode");
        log.info("params:{}", JacksonUtils.toJSONString(params));
        return params;
    }
    @Override
    public BaseResponse getBlxwInfoNoticeList(BlxwInfoRequest request) {
        log.info("request:{}",JacksonUtils.toJSONString(request));
        IPage<BlxwInfoNoticeVo> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<BlxwInfoRequest> params=getFilter(request);
        List<BlxwInfoNoticeVo> blxwInfoVos = baseMapper.getBlxwInfoNoticeList(params.getQuery(), page);
        for (BlxwInfoNoticeVo blxwInfoVo : blxwInfoVos){
            getTypeNames(blxwInfoVo);
        }
        log.info("blxwInfoVos:{}",JacksonUtils.toJSONString(blxwInfoVos));
        return BaseResponse.ok(blxwInfoVos,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getBlxwInfoPrintDetail(String id) {
        BlxwInfoPrintVo blxwInfoVo= baseMapper.getBlxwInfoNoticePrintById(id);
        if(blxwInfoVo==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        getTypeNames(blxwInfoVo);
        log.info("查询信息：{}",blxwInfoVo);
        return BaseResponse.ok(blxwInfoVo);
    }

    @Override
    public BaseResponse approvalNoticeData(BlxwNoticeInfoModel request) {
        NoticeInfo entity ;
        SysFile fileEntity;
        String id =request.getId();
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("缺少请求参数");
        } else {
            entity = noticeInfoMapper.selectById(id);
            if (entity == null)
                return new BaseResponse("信息不存在，请刷新重试！");
            fileEntity = sysFileService.getById(id);
            /**
             * 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
             * 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(DateTime.now());
            noticeInfoMapper.updateById(entity);
            BlxwInfo blxwInfo =baseMapper.selectById(entity.getSourceId());
            if (StrUtil.isNotEmpty(request.getSupportingAnnex())){
                blxwInfo.setSupportingAnnex(request.getSupportingAnnex());
                sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(blxwInfo.getSupportingAnnex(),blxwInfo.getSupportingDocument()), blxwInfo.getId());
                baseMapper.updateById(blxwInfo);
            }
            if (fileFlowStatus.equals(ApprovalStatusEnum.Already_Over.getIndex().toString())){
                //更新不良行为处理状态
                if (blxwInfo != null){
                    blxwInfo.setBlxwState(1);
                }
                baseMapper.updateById(blxwInfo);
            }else if (fileFlowStatus.equals(ApprovalStatusEnum.Invalid.getIndex().toString())){
                //更新不良行为处理状态
                if (blxwInfo != null){
                    blxwInfo.setBlxwState(0);
                }
                baseMapper.updateById(blxwInfo);
            }else{
                //更新不良行为处理状态
                if (blxwInfo != null){
                    blxwInfo.setBlxwState(2);
                }
                baseMapper.updateById(blxwInfo);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public void generateBlxwNoticeInfo(BlxwInfoVo entity) {
        NoticeInfoModel dto = new NoticeInfoModel();
        dto.setId(IdWorker.getGenerateID());
        dto.setSourceId(entity.getId());
        dto.setSourceCode("blxwInfo");
        dto.setNoticeInfoContent("给予你单位"+entity.getBlxwLevelName()+"处理");
        dto.setState(ApprovalStatusEnum.Already_Over.getIndex());
        dto.setCreateUid(entity.getCreateUid());
        dto.setCreateUname(entity.getCreateUname());
        dto.setCreateTime(new Date());
        dto.setStateTime(new Date());
        dto.setIsHistory(true);
        dto.setNoticeInfoTime(entity.getBlxwStartTime());
        dto.setSupportingDocument(entity.getSupportingDocument());
        String seqCode = "BlxwNoticeCode";
        String code = generateCodeByTCode(seqCode,dto);
        if (ValidateUtil.isNotEmpty(code)){
            dto.setNoticeInfoCode(code);
            refreshAutoNo(seqCode,dto);
            NoticeInfo noticeInfo = new NoticeInfo();
            BeanUtils.copyProperties(dto,noticeInfo);
            noticeInfoMapper.insert(noticeInfo);
            SysFile sysFile = getSysFile(noticeInfo);
            sysFileMapper.insert(sysFile);
        }
    }
    private static SysFile getSysFile(NoticeInfo entity) {
        SysFile sysFile = new SysFile();
        sysFile.setFileid(entity.getId());
        sysFile.settCode("badBehaviorRecordNotice");
        sysFile.settId("34fedbfc49ec496daad4dd64666fe968");
        sysFile.setFilecode("badBehaviorRecordNotice");
        sysFile.setFiletitle("不良行为通知");
        sysFile.setFilecreateuser(entity.getCreateUid());
        sysFile.setFilecreateusername(entity.getCreateUname());
        sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        return sysFile;
    }
    @Override
    public BaseResponse getBlxwInfoNoticeCloseList(BlxwInfoRequest request) {
        log.info("request:{}",JacksonUtils.toJSONString(request));
        IPage<BlxwInfoNoticeCloseVo> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<BlxwInfoRequest> params=getFilter(request);
        List<BlxwInfoNoticeCloseVo> blxwInfoVos = baseMapper.getBlxwInfoNoticeCloseList(params.getQuery(), page);
        for (BlxwInfoNoticeCloseVo blxwInfoVo : blxwInfoVos){
            getTypeNames(blxwInfoVo);
        }
        log.info("blxwInfoVos:{}",JacksonUtils.toJSONString(blxwInfoVos));
        return BaseResponse.ok(blxwInfoVos,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getBlxwInfoNoticeCloseDetail(String id) {
        BlxwInfoNoticeVo blxwInfoVo= baseMapper.getBlxwInfoNoticeCloseDetail(id);
        if(blxwInfoVo==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        getTypeNames(blxwInfoVo);
        log.info("查询信息：{}",JacksonUtils.toJSONString(blxwInfoVo));
        return BaseResponse.ok(blxwInfoVo);
    }

    @Override
    public BaseResponse approvalNoticeCloseData(BlxwNoticeCloseInfoModel request) {
        NoticeInfoClose entity ;
        SysFile fileEntity;
        String id =request.getId();
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("缺少请求参数");
        } else {
            entity = noticeInfoCloseMapper.selectById(id);
            if (entity == null)
                return new BaseResponse("信息不存在，请刷新重试！");
            fileEntity = sysFileService.getById(id);
            /**
             * 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
             * 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(DateTime.now());
            noticeInfoCloseMapper.updateById(entity);
            BlxwInfo blxwInfo = baseMapper.selectById(entity.getSourceId());
            if (ApprovalStatusEnum.Invalid.getIndex().toString().equals(fileFlowStatus)){
                blxwInfo.setCloseStatus("unclosed");
            } else if (ApprovalStatusEnum.Already_Over.getIndex().toString().equals(fileFlowStatus)){
                blxwInfo.setCloseStatus("closed");
                BlxwInfoVo blxwInfoVo= baseMapper.getBlxwInfoById(entity.getSourceId());
                generateBlxwNoticeInfo(blxwInfoVo);
                setBasicInfoInvalid(blxwInfo);
                QueryWrapper<ZNPJIndicator> znpjIndicatorQueryWrapper = new QueryWrapper<>();
                znpjIndicatorQueryWrapper.eq("del_flag", 1).eq("status", 1);
                List<ZNPJIndicator> znpjIndicatorList = znpjIndicatorMapper.selectList(znpjIndicatorQueryWrapper);
                ZNPJEvaluateBasisInformationQuarter znpjEvaluateBasisInformationQuarter =generateBlxwBasicInfoClose(znpjIndicatorList,
                        blxwInfo);
                generate(znpjEvaluateBasisInformationQuarter);
            }else {
                blxwInfo.setCloseStatus("closing");
            }
            baseMapper.updateById(blxwInfo);
        }
        return BaseResponse.ok();
    }

    private void setBasicInfoInvalid(BlxwInfo blxwInfo) {
        ZNPJEvaluateBasisInformationQuarter znpjEvaluateBasisInformationQuarter
                =znpjEvaluateBasisInformationQuarterMapper.selectBySourceId(blxwInfo.getId());
        if (znpjEvaluateBasisInformationQuarter != null){
            znpjEvaluateBasisInformationQuarter.setStatus(2);
            znpjEvaluateBasisInformationQuarter.setInvalidReason("不良行为已闭环，该扣分自动失效");
            znpjEvaluateBasisInformationQuarter.setInvalidTime(new Date());
            znpjEvaluateBasisInformationQuarterMapper.updateById(znpjEvaluateBasisInformationQuarter);
        }
    }

    @Override
    public void generateNoticeInfo() {
        List<BlxwInfoVo> blxwInfoVos = baseMapper.getBlxwInfoListQX();
        List<BlxwInfoVo> blxwInfoVos2 = baseMapper.getBlxwInfoListWithoutNotice();
        List<BlxwInfoVo> blxwInfoVosGenerate = new ArrayList<>();
        List<BlxwInfoVo> blxwInfoVosGenerate2 = new ArrayList<>();
        QueryWrapper<NoticeInfo> noticeInfoQueryWrapper = new QueryWrapper<>();
        noticeInfoQueryWrapper.eq("state", ApprovalStatusEnum.Already_Over.getIndex())
                .eq("source_code", "blxwInfo");
        List<NoticeInfo> noticeInfoList =noticeInfoMapper.selectList(noticeInfoQueryWrapper);
        List<String> noticeInfoSourceIds = noticeInfoList.stream()
                .map(NoticeInfo::getSourceId).filter(StrUtil::isNotEmpty).collect(Collectors.toList());
        log.info("noticeInfoList:{}",JacksonUtils.toJSONString(noticeInfoList));
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        now = calendar.getTime();
        log.info("now:{}", DateFormatUtil.dateToString(now, DateFormatUtil.FORMAT_PATTERN_OF_SECOND));
        for (BlxwInfoVo blxwInfoVo : blxwInfoVos){
            if (!noticeInfoSourceIds.contains(blxwInfoVo.getId())){
                //当天之前生成通知
                log.info("blxwInfoVo.getBlxwRectifyTime:{}", DateFormatUtil.dateToString(blxwInfoVo.getBlxwRectifyTime(), DateFormatUtil.FORMAT_PATTERN_OF_DAY));
                if (blxwInfoVo.getBlxwRectifyTime().compareTo(now)<0){
                    blxwInfoVosGenerate.add(blxwInfoVo);
                }
            }
        }
        log.info("blxwInfoVos2:{}",JacksonUtils.toJSONString(blxwInfoVos2));
        for (BlxwInfoVo blxwInfoVo : blxwInfoVos2){
            if (!noticeInfoSourceIds.contains(blxwInfoVo.getId())){
                //当天之前生成通知
                blxwInfoVosGenerate2.add(blxwInfoVo);
            }
        }
        log.info("blxwInfoVosGenerate:{}",JacksonUtils.toJSONString(blxwInfoVosGenerate));
        log.info("blxwInfoVosGenerate2:{}",JacksonUtils.toJSONString(blxwInfoVosGenerate2));
        if (CollectionUtil.isNotEmpty(blxwInfoVosGenerate)){
            for (BlxwInfoVo blxwInfoVo : blxwInfoVosGenerate){
                generateBlxwNoticeInfo(blxwInfoVo);
                BlxwInfo blxwInfo = baseMapper.selectById(blxwInfoVo.getId());
                if ("02".equals(blxwInfoVo.getBlxwHandleType())){
                    blxwInfo.setCloseStatus("autoclosed");
                }else {
                    blxwInfo.setCloseStatus("closed");
                }
                baseMapper.updateById(blxwInfo);
            }
        }
        if (CollectionUtil.isNotEmpty(blxwInfoVosGenerate2)){
            for (BlxwInfoVo blxwInfoVo : blxwInfoVosGenerate2){
                generateBlxwNoticeInfo(blxwInfoVo);
                BlxwInfo blxwInfo = baseMapper.selectById(blxwInfoVo.getId());
                blxwInfo.setCloseStatus("closed");
                baseMapper.updateById(blxwInfo);
            }
        }
    }

    @Override
    public BaseResponse fresh(BlxwNoticeCloseInfoModel request) {
        List<NoticeInfo> noticeInfos = noticeInfoMapper.selectList2();
        List<String> sourceIds = noticeInfos.stream().map(NoticeInfo::getSourceId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(sourceIds)){
            return BaseResponse.ok();
        }
        List<BlxwInfo> blxwInfoBySources = baseMapper.getBlxwInfoBySourceIds(sourceIds);
        for (BlxwInfo blxwInfo : blxwInfoBySources){
            List<ZNPJEvaluateBasisInformationQuarter> znpjEvaluateBasisInformationQuarters
                    = znpjEvaluateBasisInformationQuarterMapper.selectList(new QueryWrapper<ZNPJEvaluateBasisInformationQuarter>().eq("source_id", blxwInfo.getId()));
            if (CollectionUtil.isNotEmpty(znpjEvaluateBasisInformationQuarters)){
                for (ZNPJEvaluateBasisInformationQuarter znpjEvaluateBasisInformationQuarter : znpjEvaluateBasisInformationQuarters){
                    znpjEvaluateBasisInformationQuarterMapper.deleteById(znpjEvaluateBasisInformationQuarter);
                }
            }
            NoticeInfo noticeInfo = noticeInfoMapper.selectBySourceId(blxwInfo.getId());
            if (noticeInfo!=null){
                noticeInfoMapper.deleteById(noticeInfo);
            }
            baseMapper.deleteById(blxwInfo);
        }
        return BaseResponse.ok();
    }

    private void getTypeNames(BlxwInfoNoticeVo blxwInfoVo) {
        String typeNames = "";
        String blxwTypeIds=blxwInfoVo.getBlxwTypeIds();
        if(ValidateUtil.isNotEmpty(blxwTypeIds)){
            String[] blxwTypeId = blxwTypeIds.split(",");
            //log.info("blxwTypeId:{}",JacksonUtils.toJSONString(blxwTypeId));
            for (String type : blxwTypeId) {
                String typeName = baseMapper.getBlxwTypeNameByTypeId(type);
                if (StrUtil.isNotEmpty(typeName)){
                    typeNames+=typeName+",";
                }
            }
            if (typeNames.length()>0){
                typeNames = typeNames.substring(0,typeNames.length()-1);
            }
            blxwInfoVo.setBlxwTypeNames(typeNames);
        }
    }
    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //log.info("服务调用时:{}",JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
    public String generateCodeByTCode(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/generateCodeByTCode?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }
    public String refreshAutoNo(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/refershAutoNo?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }
    private ZNPJEvaluateBasisInformationQuarter  generateBlxwBasicInfo(List<ZNPJIndicator> znpjIndicatorList,
                                       BlxwInfo blxwInfo) {
        ZNPJEvaluateBasisInformationQuarter znpjevaluateBasisInformationQuarter =new ZNPJEvaluateBasisInformationQuarter();
        znpjevaluateBasisInformationQuarter.setId(IdWorker.getGenerateID());
        znpjevaluateBasisInformationQuarter.setType("02");
        znpjevaluateBasisInformationQuarter.setProjectId(blxwInfo.getBlxwProjectId());
        znpjevaluateBasisInformationQuarter.setBasisInformationProjectType(blxwInfo.getBlxwProjectType());
        znpjevaluateBasisInformationQuarter.setContractId(blxwInfo.getBlxwContractId());
        znpjevaluateBasisInformationQuarter.setCompanyId(blxwInfo.getBlxwResponsibleUnitId());
        if (blxwInfo.getBlxwStartTime()== null||blxwInfo.getBlxwEndTime()== null){
            return null;
        }
        znpjevaluateBasisInformationQuarter.setEffectiveStartTime(blxwInfo.getBlxwStartTime());
        znpjevaluateBasisInformationQuarter.setEffectiveEndTime(blxwInfo.getBlxwEndTime());
        setEffective(znpjevaluateBasisInformationQuarter);
        znpjevaluateBasisInformationQuarter.setContractUnitType(blxwInfo.getBlxwContractUnitType());
        znpjevaluateBasisInformationQuarter.setIsContainsSg(1);
        znpjevaluateBasisInformationQuarter.setSum(1);
        znpjevaluateBasisInformationQuarter.setScore(BigDecimal.valueOf(blxwInfo.getBlxwDeductScore()).abs().doubleValue());
        znpjevaluateBasisInformationQuarter.setSourceCode("badBehaviorRecord");
        znpjevaluateBasisInformationQuarter.setSourceId(blxwInfo.getId());
        if (StrUtil.isEmpty(blxwInfo.getBlxwLevel())){
            return null;
        }
        ZNPJIndicator znpjIndicator =null;
        switch (blxwInfo.getBlxwLevel()){
            case "01":
                znpjevaluateBasisInformationQuarter.setInfoType("18");
                znpjIndicator = znpjIndicatorList.stream().
                        filter(n -> "【指标】-20240828-00000140".equals(n.getCode())).findFirst().orElse(null);
                break;
            case "02":
                znpjevaluateBasisInformationQuarter.setInfoType("19");
                znpjIndicator = znpjIndicatorList.stream().
                        filter(n -> "【指标】-20240828-00000141".equals(n.getCode())).findFirst().orElse(null);
                break;
            default:
                break;
        }
        if (znpjIndicator == null) {
            return null;
        }
        log.info("znpjIndicator:{}",JacksonUtils.toJSONString(znpjIndicator));
        znpjevaluateBasisInformationQuarter.setReason(blxwInfo.getBlxwContent());
        znpjevaluateBasisInformationQuarter.setIndicatorId(znpjIndicator.getId());
        znpjevaluateBasisInformationQuarter.setScoreType(String.valueOf(znpjIndicator.getScoreType()));
        znpjevaluateBasisInformationQuarter.setIndicatorVersionId(znpjIndicator.getIndicatorVersionId());
        znpjevaluateBasisInformationQuarter.setPlusMinusScoreType(znpjIndicator.getPlusMinusScoreType());
        znpjevaluateBasisInformationQuarter.setDelFlag(1);
        znpjevaluateBasisInformationQuarter.setStatus(1);
        znpjevaluateBasisInformationQuarter.setFlowStatus(ApprovalStatusEnum.Already_Over.getIndex());
        znpjevaluateBasisInformationQuarter.setReviewTime(blxwInfo.getBlxwStartTime());
        znpjevaluateBasisInformationQuarter.setCreateBy(blxwInfo.getCreateUid());
        znpjevaluateBasisInformationQuarter.setCreateName(blxwInfo.getCreateUname());
        znpjevaluateBasisInformationQuarter.setCreateTime(blxwInfo.getCreateTime());
        znpjevaluateBasisInformationQuarter.setUpdateBy(blxwInfo.getCreateUid());
        znpjevaluateBasisInformationQuarter.setUpdateTime(blxwInfo.getStateTime());
        return znpjevaluateBasisInformationQuarter;
    }
    private ZNPJEvaluateBasisInformationQuarter generateBlxwBasicInfoClose(List<ZNPJIndicator> znpjIndicatorList,BlxwInfo blxwInfo) {
        ZNPJEvaluateBasisInformationQuarter znpjevaluateBasisInformationQuarter = new ZNPJEvaluateBasisInformationQuarter();
        znpjevaluateBasisInformationQuarter.setId(IdWorker.getGenerateID());
        znpjevaluateBasisInformationQuarter.setType("02");
        znpjevaluateBasisInformationQuarter.setProjectId(blxwInfo.getBlxwProjectId());
        znpjevaluateBasisInformationQuarter.setBasisInformationProjectType(blxwInfo.getBlxwProjectType());
        znpjevaluateBasisInformationQuarter.setContractId(blxwInfo.getBlxwContractId());
        znpjevaluateBasisInformationQuarter.setCompanyId(blxwInfo.getBlxwResponsibleUnitId());
        if (blxwInfo.getBlxwStartTime()== null||blxwInfo.getBlxwEndTime()== null){
            return null;
        }
        znpjevaluateBasisInformationQuarter.setEffectiveStartTime(blxwInfo.getBlxwStartTime());
        znpjevaluateBasisInformationQuarter.setEffectiveEndTime(blxwInfo.getBlxwEndTime());
        setEffective(znpjevaluateBasisInformationQuarter);
        znpjevaluateBasisInformationQuarter.setContractUnitType(blxwInfo.getBlxwContractUnitType());
        znpjevaluateBasisInformationQuarter.setIsContainsSg(1);
        znpjevaluateBasisInformationQuarter.setSum(1);
        znpjevaluateBasisInformationQuarter.setSourceCode("badBehaviorRecord");
        znpjevaluateBasisInformationQuarter.setSourceId(blxwInfo.getId());
        ZNPJIndicator znpjIndicator = znpjIndicatorList.stream().
                filter(n -> "【指标】-20250821-00000253".equals(n.getCode())).findFirst().orElse(null);
        if (znpjIndicator == null) {
            return null;
        }
        znpjevaluateBasisInformationQuarter.setInfoType("17");
        znpjevaluateBasisInformationQuarter.setReason(blxwInfo.getBlxwContent());
        znpjevaluateBasisInformationQuarter.setIndicatorId(znpjIndicator.getId());
        znpjevaluateBasisInformationQuarter.setScoreType(String.valueOf(znpjIndicator.getScoreType()));
        znpjevaluateBasisInformationQuarter.setIndicatorVersionId(znpjIndicator.getIndicatorVersionId());
        znpjevaluateBasisInformationQuarter.setPlusMinusScoreType(znpjIndicator.getPlusMinusScoreType());
        znpjevaluateBasisInformationQuarter.setScore(Double.valueOf(znpjIndicator.getScore()));
        znpjevaluateBasisInformationQuarter.setDelFlag(1);
        znpjevaluateBasisInformationQuarter.setStatus(1);
        znpjevaluateBasisInformationQuarter.setFlowStatus(ApprovalStatusEnum.Already_Over.getIndex());
        znpjevaluateBasisInformationQuarter.setReviewTime(blxwInfo.getBlxwStartTime());
        znpjevaluateBasisInformationQuarter.setCreateBy(blxwInfo.getCreateUid());
        znpjevaluateBasisInformationQuarter.setCreateName(blxwInfo.getCreateUname());
        znpjevaluateBasisInformationQuarter.setCreateTime(blxwInfo.getCreateTime());
        znpjevaluateBasisInformationQuarter.setUpdateBy(blxwInfo.getCreateUid());
        znpjevaluateBasisInformationQuarter.setUpdateTime(blxwInfo.getStateTime());
        return znpjevaluateBasisInformationQuarter;
    }
    private void generate(ZNPJEvaluateBasisInformationQuarter znpjEvaluateBasisInformationQuarter) {
        if (znpjEvaluateBasisInformationQuarter != null){
            znpjEvaluateBasisInformationQuarterMapper.insert(znpjEvaluateBasisInformationQuarter);
            SysFile sysFile = getSysFileBasisInformationQuarter(znpjEvaluateBasisInformationQuarter);
            sysFileMapper.insert(sysFile);
        }
    }
    public static int getCurrentQuarter(int month) {
        return (month - 1) / 3 + 1;
    }
    private void setEffective(ZNPJEvaluateBasisInformationQuarter evaluateBasisInformation) {
        if (evaluateBasisInformation.getEffectiveStartTime() != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(evaluateBasisInformation.getEffectiveStartTime());
            evaluateBasisInformation.setEffectiveStartYear(calendar.get(Calendar.YEAR));
            evaluateBasisInformation.setEffectiveStartQuarter(getCurrentQuarter(calendar.get(Calendar.MONTH)+1));
        }
        if (evaluateBasisInformation.getEffectiveEndTime() != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(evaluateBasisInformation.getEffectiveEndTime());
            evaluateBasisInformation.setEffectiveEndYear(calendar.get(Calendar.YEAR));
            evaluateBasisInformation.setEffectiveEndQuarter(getCurrentQuarter(calendar.get(Calendar.MONTH)+1));
        }
    }
    private static SysFile getSysFileBasisInformationQuarter(ZNPJEvaluateBasisInformationQuarter basisInformationQuarter) {
        SysFile sysFile = new SysFile();
        sysFile.setFileid(basisInformationQuarter.getId());
        sysFile.settCode("OtherFunctions");
        sysFile.settId("895f26d0bead4b9c83c76cd9129bebba");
        sysFile.setFilecode("OtherFunctions");
        sysFile.setFiletitle("季度履约加减分");
        sysFile.setFileproject(basisInformationQuarter.getProjectId());
        sysFile.setFilecreateuser(basisInformationQuarter.getCreateBy());
        sysFile.setFilecreateusername(basisInformationQuarter.getCreateName());
        sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        return sysFile;
    }
}