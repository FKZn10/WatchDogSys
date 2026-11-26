package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGAgencySelectionRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGAgencyExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.JGExpertPrintDetailsDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.JGExpertPrintDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGAgencyExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGAgencySelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGAgencyExpertExtractVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.secInfo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class SecJGAgencySelectionRecordImpl extends ServiceImpl<SecJGAgencySelectionRecordMapper, SecJGAgencySelectionRecord> implements ISecJGAgencySelectionRecordService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecJGAgencySelectionRecordDtlService secJGAgencySelectionRecordDtlService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISecJGExpertShieldService secJGExpertShieldService;
    @Resource
    private ISecJGExpertSelectService secJGExpertSelectService;
    @Resource
    private ISecJGExpertService secJGExpertService;
    @Resource
    private ISysDictionaryService sysDictionaryService;
    @Resource
    private ISysUserService sysUserService;

    private SecJGAgencySelectionRecord createOrUpdateEntity(SecJGAgencySelectionRecordModel request, LogUserInfoVo user) {
        SecJGAgencySelectionRecord entity = new SecJGAgencySelectionRecord();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setId(GenerateUUID.getUUID());
            entity.setSrCreateTime(new Date());
            entity.setState(ValidateUtil.isNull(request.getState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            entity.setStateTime(new Date());
            entity.setSrCreateUid(user.getId());
            entity.setSrCreateUname(user.getName());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getSrCreateUid(), entity.getSrCreateUname()
                    , entity.getSrCreateTime(), null, request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            SecJGAgencySelectionRecord pojo = baseMapper.selectById(request.getId());
            if (pojo == null)
                throw new JPMException(ResMsg.infoNotExist);
            entity.setSrCreateTime(pojo.getSrCreateTime());
            entity.setSrCreateUid(pojo.getSrCreateUid());
            entity.setSrCreateUname(pojo.getSrCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getSrCreateUid(), entity.getSrCreateUname()
                    , entity.getSrCreateTime(), null, request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        secJGAgencySelectionRecordDtlService.createOrUpdateSecSelectionRecord(request.getSecJGSelectionRecordDtlModels(), entity.getId());
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecJGAgencySelectionRecord(SecJGAgencySelectionRecordModel request, LogUserInfoVo user) {
        SecJGAgencySelectionRecord entity = createOrUpdateEntity(request, user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecJGAgencySelectionRecordList(SecJGAgencySelectionRecordModel request, String uid) {
        String authSql = sysUserService.getAuthSql("Sec_JG_Agency_Selection_Record", uid, "Sr_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<SecJGAgencySelectionRecordModel> params = new QueryMap<>(request);
        params.put("excludeDiscard").put("srType").put("srContact").put("srPhone")
                .put(request.getSrAccessTimes(), "srAccessTimeStart", "srAccessTimeEnd")
                .put("pId").put("secId").put("state")
                .put("currentUid",uid).put("authSql", authSql);
        IPage<SecJGAgencySelectionRecordModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecJGAgencySelectionRecordModel> datas = baseMapper.getSecSelectionRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getJGAgencySectionDocumentCount(SecJGAgencySelectionRecordModel request) {

        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecJGAgencySelectionRecordById(String srId) {
        SecJGAgencySelectionRecordModel data = baseMapper.getSecSelectionRecordById(srId);
        Map<String, List<SecJGAgencySelectionRecordDtlModel>> extractMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(data.getSecJGSelectionRecordDtlModels())) {
            List<SecJGAgencySelectionRecordDtlModel> insideExperts = new ArrayList<>();
            List<SecJGAgencySelectionRecordDtlModel> outsideExperts = new ArrayList<>();

            List<SecJGAgencySelectionRecordDtlModel> dtlModels=data.getSecJGSelectionRecordDtlModels();
            dtlModels.forEach(d->{
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                if (d.getExInsideType() != null && d.getExInsideType() == 1){
                    insideExperts.add(d);
                } else {
                    outsideExperts.add(d);
                }
            });
            extractMap.put("insideExperts", insideExperts);
            extractMap.put("outsideExperts", outsideExperts);
            data.setExtractMap(extractMap);
            data.setSecJGSelectionRecordDtlModels(dtlModels);
        }
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse deleteSecJGAgencySelectionRecordById(String srId) {
        SecJGAgencySelectionRecord pojo = baseMapper.selectById(srId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        secJGAgencySelectionRecordDtlService.deleteAllBySrId(srId);
        secJGExpertShieldService.delSecExpertShieldBySrId(srId);
        baseMapper.deleteById(srId);
        sysFileService.deleteFileById(srId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse checkJGAgencyExPeople(ExtractJGAgencyExpertDto request, String teId) {
        return null;
    }

    @Override
    public BaseResponse createOrUpdateJGAgencyExpertExtract(SecJGAgencySelectionRecordModel request, LogUserInfoVo user) {
        SecJGAgencySelectionRecord entity = createOrUpdateEntity(request, user);
        secJGExpertShieldService.createOrUpdateSecExpertShield(request.getSecJGExpertShields(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecJGAgencyExpertExtractById(String srId) {
        return BaseResponse.ok(idNumberVo(baseMapper.getSecExpertExtractById(srId)));
    }

    private SecJGAgencyExpertExtractVo idNumberVo(SecJGAgencyExpertExtractVo data) {
        if (data != null) {
            if (!CollectionUtils.isEmpty(data.getSecJGSelectionRecordDtlModels())) {
                List<SecJGAgencySelectionRecordDtlModel> rds = data.getSecJGSelectionRecordDtlModels().stream().peek(e -> {
                    e.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(e.getExIdNumber())));
                }).collect(Collectors.toList());
                data.setSecJGSelectionRecordDtlModels(rds);
            }
        }
        return data;
    }


    @Override
    public BaseResponse getSecJGAgencyExpertExtractByEsId(String esId) {
        return null;
    }

    @Override
    public BaseResponse getSecJGAgencySelectionRecordDtlModelById(String srId) {
        SecJGAgencySelectionRecordModel data = baseMapper.getSecSelectionRecordById(srId);
        List<SecJGAgencySelectionRecordDtlModel> dtlModels= new ArrayList<>();
        if (!CollectionUtils.isEmpty(data.getSecJGSelectionRecordDtlModels())) {
            dtlModels=data.getSecJGSelectionRecordDtlModels();
            dtlModels.forEach(d->{
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            });
        }
        return BaseResponse.ok(dtlModels, 0);
    }

    @Override
    public BaseResponse getSecJGAgencyExpertExtractList(SecJGAgencyExpertSelectDto request, String uid) {
        return null;
    }

    @Override
    public BaseResponse cancelJGAgencyExtractCancelSelect(String srId) {
        return null;
    }

    @Override
    public BaseResponse getSecJGAgencyValueById(String srId) {
        List<JGExpertPrintDto> datas = new ArrayList<>();
        SecJGAgencySelectionRecordModel data = baseMapper.getSecSelectionRecordById(srId);
        List<SysDictionary>  dicList= sysDictionaryService.getSysDictionaryByCode("JPMsoft_JGZJSBZYA");
        if (data == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        String srOutsideExList= data.getSrOutsideExList();
        if (StringUtils.isNotBlank(srOutsideExList)) {
            List<JGExpertPrintDetailsDto> dtos=JacksonUtils.parseArray(srOutsideExList, JGExpertPrintDetailsDto.class);
            int i=1;
            for (JGExpertPrintDetailsDto dto : dtos) {
                JGExpertPrintDto printDto = new JGExpertPrintDto();
                printDto.setName("专家"+i);
                List<String> exTypeCodes = dto.getExTypeCodes();
                StringBuffer sb = new StringBuffer();
                for (String exTypeCode : exTypeCodes) {
                    //从字典编码中获取字典名称
                    String value ="";
                    if (dicList.stream().filter(e->e.getdCode().equals(exTypeCode)).collect(Collectors.toList()).size()>0){
                        value =dicList.stream().filter(e->e.getdCode().equals(exTypeCode)).collect(Collectors.toList()).get(0).getdName();
                    }
                    if (StrUtil.isNotBlank(value)) {
                        sb.append(value).append(",");
                    }
                }
                printDto.setValue(sb.deleteCharAt(sb.length()-1).toString());
                datas.add(printDto);
                i++;
            }
        }
        //打印获取
        return BaseResponse.ok(datas);
    }
}
