package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.*;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.other.SysDictionaryMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGSelectionRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingNegotiation;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInvitationNegotiation;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGSelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGExpertExtractVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SecJGSelectionRecordImpl extends ServiceImpl<SecJGSelectionRecordMapper, SecJGSelectionRecord> implements ISecJGSelectionRecordService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecJGSelectionRecordDtlService secJGSelectionRecordDtlService;
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
    private RedisUtils redisUtils;
    @Resource
    private SysDictionaryMapper sysDictionaryMapper;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISecInvitationNegotiationService secInvitationNegotiationService;
    @Resource
    private ISecBiddingNegotiationService secBiddingNegotiationService;

    public SecJGSelectionRecord createOrUpdateEntity(SecJGSelectionRecordModel request, LogUserInfoVo user) {
        SecJGSelectionRecord entity = new SecJGSelectionRecord();
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
            SecJGSelectionRecord pojo = baseMapper.selectById(request.getId());
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
        secJGSelectionRecordDtlService.createOrUpdateSecSelectionRecord(request.getSecJGSelectionRecordDtlModels(), entity.getId());
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecJGSelectionRecord(SecJGSelectionRecordModel request, LogUserInfoVo user) {
        SecJGSelectionRecord entity = createOrUpdateEntity(request, user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecJGSelectionRecordList(SecJGSelectionRecordModel request, String uid) {
        String authSql = sysUserService.getAuthSql("Sec_JG_Selection_Record", uid, "Sr_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<SecJGSelectionRecordModel> params = new QueryMap<>(request);
        params.put("excludeDiscard").put("srType").put("srContact").put("srPhone")
                .put(request.getSrAccessTimes(), "srAccessTimeStart", "srAccessTimeEnd")
                .put("pName").put("pId").put("secName").put("state")
                .put("currentUid", uid).put("srIsJudge").put("authSql", authSql);
        IPage<SecJGSelectionRecordModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecJGSelectionRecordModel> datas = baseMapper.getSecSelectionRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getJGSectionDocumentCount(SecJGSelectionRecordModel request) {
        String fileTemplateCode = sysConfigService.getConfigByCode("SecExpertRecordEnabledFT");
        String fileTemplateCodeNew = sysConfigService.getConfigByCode("SecExpertRecordEnabledFTNew");
        if (StringUtils.isBlank(fileTemplateCode) && StringUtils.isBlank(fileTemplateCodeNew)) {
            return BaseResponse.ok(1);
        }
        long count = this.baseMapper.querySecDoumentCount(request.getSecId(), fileTemplateCode, fileTemplateCodeNew);
        long secInvitationNegotiationCount = secInvitationNegotiationService.count(Wrappers.<SecInvitationNegotiation>lambdaQuery()
                .eq(SecInvitationNegotiation::getSecId, request.getSecId())
                .eq(SecInvitationNegotiation::getState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        long secBiddingNegotiationCount = secBiddingNegotiationService.count(Wrappers.<SecBiddingNegotiation>lambdaQuery()
                .eq(SecBiddingNegotiation::getSecId, request.getSecId())
                .eq(SecBiddingNegotiation::getState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        return BaseResponse.ok(count + secInvitationNegotiationCount + secBiddingNegotiationCount);
    }

    @Override
    public BaseResponse getSecJGSelectionRecordById(String srId) {
        SecJGSelectionRecordModel data = baseMapper.getSecSelectionRecordById(srId);
        if (data != null && !CollectionUtils.isEmpty(data.getSecJGSelectionRecordDtlModels())) {
            List<SecJGSelectionRecordDtlModel> dtlModels = data.getSecJGSelectionRecordDtlModels();
            dtlModels.forEach(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            });
            data.setSecJGSelectionRecordDtlModels(dtlModels);
        }
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse deleteSecJGSelectionRecordById(String srId) {
        SecJGSelectionRecord pojo = baseMapper.selectById(srId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        secJGSelectionRecordDtlService.deleteAllBySrId(srId);
        secJGExpertShieldService.delSecExpertShieldBySrId(srId);
        baseMapper.deleteById(srId);
        sysFileService.deleteFileById(srId);
        return BaseResponse.ok();
    }

    /****************************************专家抽取***********************************************/

    @Override
    public BaseResponse createOrUpdateJGExpertExtract(SecJGSelectionRecordModel request, LogUserInfoVo user) {
        SecJGSelectionRecord entity = createOrUpdateEntity(request, user);
        secJGExpertShieldService.createOrUpdateSecExpertShield(request.getSecJGExpertShields(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecJGExpertExtractById(String srId) {
        return BaseResponse.ok(idNumberVo(baseMapper.getSecExpertExtractById(srId)));
    }

    private SecJGExpertExtractVo idNumberVo(SecJGExpertExtractVo data) {
        if (data != null) {
            if (!CollectionUtils.isEmpty(data.getSecJGSelectionRecordDtlModels())) {
                List<SecJGSelectionRecordDtlModel> rds = data.getSecJGSelectionRecordDtlModels().stream().peek(e -> {
                    e.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(e.getExIdNumber())));
                }).collect(Collectors.toList());
                data.setSecJGSelectionRecordDtlModels(rds);
            }
        }
        return data;
    }

    @Override
    public BaseResponse getSecJGExpertExtractByEsId(String esId) {
        return BaseResponse.ok(idNumberVo(baseMapper.getSecExpertExtractByEsId(esId)));
    }

    @Override
    public BaseResponse getSecJGSelectionRecordDtlModelById(String srId) {
        List<SecJGSelectionRecordDtlModel> data = baseMapper.getSecSelectionRecordDtlModelById(srId);
        if (data != null) {
            if (!CollectionUtils.isEmpty(data)) {
                data.stream().peek(e -> {
                    e.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(e.getExIdNumber())));
                }).collect(Collectors.toList());
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecJGExpertExtractList(SecJGExpertSelectDto request, String uid) {
        QueryMap<SecJGExpertSelectDto> queryMap = new QueryMap<>(request);
        queryMap.put("pName").put("pId").put("secName").put("createUname").put("state");
        IPage<SecJGExpertExtractVo> page = new Page<>(request.getPage(), request.getSize());
        List<SecJGExpertExtractVo> datas = baseMapper.getSecExpertExtractList(page, queryMap.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse cancelJGExtractCancelSelect(String srId) {
        SecJGSelectionRecord data = baseMapper.selectById(srId);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (Objects.equals(data.getState(), ApprovalStatusEnum.Invalid.getIndex())) {
            secJGExpertSelectService.updateState(data.getEsId(), ApprovalStatusEnum.Invalid.getIndex());
            sysFileService.onlyUpdateState(ApprovalStatusEnum.Invalid.getIndex(), data.getEsId());
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse checkJGExPeople(ExtractJGExpertDto request, String uId) {
        BaseResponse baseResponse = secJGExpertService.extractJGExpert(request, uId);
        if (baseResponse.getSuccess() && baseResponse.getStatusCode() == HttpStatus.SC_OK)
            baseResponse.setData(Boolean.TRUE);
        else baseResponse.setData(Boolean.FALSE);
        return baseResponse;
    }

    @Override
    public void updateStateById(Integer state, String srId) {
        if (StrUtil.isNotBlank(srId)) {
            baseMapper.updateStateById(state, srId);
        }
    }

    @Override
    public BaseResponse getSecJGSelectionRecordPrintById(String srId) {
        SecJGSelectionRecordPrintVo data = baseMapper.getSecJGSelectionRecordPrintVoById(srId);
        List<SecJGSelectionRecordPrintVo> list = new ArrayList<>();
        list.add(data);
        return BaseResponse.ok(list);
    }

    @Override
    public BaseResponse getSecJGSelectionRecordPrintInsecDtlModelsById(String srId, String token) {
        List<SecJGSelectionRecordDtlModelPrintVo> data = baseMapper.getSecJGSelectionRecordPrintInsecDtlModelsById(srId);
        return setDtlInfo(token, data);
    }

    private BaseResponse setDtlInfo(String token, List<SecJGSelectionRecordDtlModelPrintVo> data) {
        String teId = redisUtils.getTeId(token);
        List<SysDictionary> allDictionaryList = getAllDictionaryList(teId);
        SysDictionary dictionaryJPMsoft_ZJLB = allDictionaryList.stream().filter(dictionary1 -> "JPMsoft_ZJLB".equals(dictionary1.getdCode())).findFirst().orElse(null);
        allDictionaryList = allDictionaryList.stream().filter(n -> n.getdParentid().equals(dictionaryJPMsoft_ZJLB.getdId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(data)) {
            List<SysDictionary> finalAllDictionaryList = allDictionaryList;
            data.forEach(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                if (d.getSrdAttend() == 1) {
                    d.setSrdAttendName("按时");
                } else {
                    d.setSrdAttendName("不按时");
                }
                if (StrUtil.isNotEmpty(d.getExCategory())) {
                    if (d.getExCategory().contains(",")) {
                        String[] split = d.getExCategory().split(",");
                        StringBuffer sb = new StringBuffer();
                        for (String s : split) {
                            SysDictionary dictionary = finalAllDictionaryList.stream().filter(dictionary1 -> s.equals(dictionary1.getdCode())).findFirst().orElse(null);
                            if (dictionary != null) {
                                sb.append(dictionary.getdName());
                                sb.append(",");
                            }
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        d.setExCategoryName(sb.toString());
                    } else {
                        StringBuffer sb = new StringBuffer();
                        SysDictionary dictionary = finalAllDictionaryList.stream().filter(dictionary1 -> d.getExCategory().equals(dictionary1.getdCode())).findFirst().orElse(null);
                        if (dictionary != null) {
                            sb.append(dictionary.getdName());
                            sb.append(",");
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        d.setExCategoryName(sb.toString());
                    }
                }
            });
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecJGSelectionRecordPrintOutsecDtlModelsById(String srId, String token) {
        List<SecJGSelectionRecordDtlModelPrintVo> data = baseMapper.getSecJGSelectionRecordPrintOutsecDtlModelsById(srId);
        return setDtlInfo(token, data);
    }

    @Override
    public BaseResponse getSecJGSelectionRecordPrintSrInsideExListById(String srId, String token) {
        String teId = redisUtils.getTeId(token);
        SecJGSelectionRecordModel data = baseMapper.getSecSelectionRecordById(srId);
        List<SysDictionary> allDictionaryList = getAllDictionaryList(teId);
        SysDictionary dictionaryJPMsoft_SBZYYI = allDictionaryList.stream().filter(dictionary1 -> "JPMsoft_SBZYYI".equals(dictionary1.getdCode())).findFirst().orElse(null);
        allDictionaryList = allDictionaryList.stream().filter(n -> n.getdParentid().equals(dictionaryJPMsoft_SBZYYI.getdId())).collect(Collectors.toList());
        String s = data.getSrInsideExList();
        List<ExNamePrintVo> list = new ArrayList<>();
        List<ExNameInsideDto> exNameDtos = JacksonUtils.parseArray(s, ExNameInsideDto.class);
        if (exNameDtos != null) {
            for (ExNameInsideDto exNameDto : exNameDtos) {
                List<String> exName1 = exNameDto.getExName1();
                List<String> exName2 = exNameDto.getExName2();
                List<String> exName3 = exNameDto.getExName3();
                List<String> exName4 = exNameDto.getExName4();
                List<String> exName5 = exNameDto.getExName5();
                getExNamePrintVo(allDictionaryList, list, exName1, exName2, exName3, exName4, exName5);
            }
        }
        return BaseResponse.ok(list);
    }

    private void getExNamePrintVo(List<SysDictionary> allDictionaryList, List<ExNamePrintVo> list, List<String> exName1, List<String> exName2, List<String> exName3, List<String> exName4, List<String> exName5) {
        if (exName1 != null && exName1.size() > 0) {
            ExNamePrintVo exNamePrintVo = new ExNamePrintVo();
            exNamePrintVo.setName("专家1");
            StringBuffer sb = new StringBuffer();
            for (String exName : exName1) {
                SysDictionary dictionary = allDictionaryList.stream().filter(dictionary1 -> exName.equals(dictionary1.getdCode())).findFirst().orElse(null);
                if (dictionary != null) {
                    sb.append(dictionary.getdName());
                    sb.append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            exNamePrintVo.setCodeName(sb.toString());
            list.add(exNamePrintVo);
        }
        if (exName2 != null && exName2.size() > 0) {
            ExNamePrintVo exNamePrintVo = new ExNamePrintVo();
            exNamePrintVo.setName("专家2");
            StringBuffer sb = new StringBuffer();
            for (String exName : exName2) {
                SysDictionary dictionary = allDictionaryList.stream().filter(dictionary1 -> exName.equals(dictionary1.getdCode())).findFirst().orElse(null);
                if (dictionary != null) {
                    sb.append(dictionary.getdName());
                    sb.append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            exNamePrintVo.setCodeName(sb.toString());
            list.add(exNamePrintVo);
        }
        if (exName3 != null && exName3.size() > 0) {
            ExNamePrintVo exNamePrintVo = new ExNamePrintVo();
            exNamePrintVo.setName("专家3");
            StringBuffer sb = new StringBuffer();
            for (String exName : exName3) {
                SysDictionary dictionary = allDictionaryList.stream().filter(dictionary1 -> exName.equals(dictionary1.getdCode())).findFirst().orElse(null);
                if (dictionary != null) {
                    sb.append(dictionary.getdName());
                    sb.append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            exNamePrintVo.setCodeName(sb.toString());
            list.add(exNamePrintVo);
        }
        if (exName4 != null && exName4.size() > 0) {
            ExNamePrintVo exNamePrintVo = new ExNamePrintVo();
            exNamePrintVo.setName("专家4");
            StringBuffer sb = new StringBuffer();
            for (String exName : exName4) {
                SysDictionary dictionary = allDictionaryList.stream().filter(dictionary1 -> exName.equals(dictionary1.getdCode())).findFirst().orElse(null);
                if (dictionary != null) {
                    sb.append(dictionary.getdName());
                    sb.append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            exNamePrintVo.setCodeName(sb.toString());
            list.add(exNamePrintVo);
        }
        if (exName5 != null && exName5.size() > 0) {
            ExNamePrintVo exNamePrintVo = new ExNamePrintVo();
            exNamePrintVo.setName("专家5");
            StringBuffer sb = new StringBuffer();
            for (String exName : exName5) {
                SysDictionary dictionary = allDictionaryList.stream().filter(dictionary1 -> exName.equals(dictionary1.getdCode())).findFirst().orElse(null);
                if (dictionary != null) {
                    sb.append(dictionary.getdName());
                    sb.append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            exNamePrintVo.setCodeName(sb.toString());
            list.add(exNamePrintVo);
        }
    }

    @Override
    public BaseResponse getSecJGSelectionRecordPrintSrOutsideExListById(String srId, String token) {
        String teId = redisUtils.getTeId(token);
        List<SysDictionary> allDictionaryList = getAllDictionaryList(teId);
        SysDictionary dictionaryJPMsoft_SBZYYI = allDictionaryList.stream().filter(dictionary1 -> "JPMsoft_SBZYER".equals(dictionary1.getdCode())).findFirst().orElse(null);
        allDictionaryList = allDictionaryList.stream().filter(n -> n.getdParentid().equals(dictionaryJPMsoft_SBZYYI.getdId())).collect(Collectors.toList());
        SecJGSelectionRecordModel data = baseMapper.getSecSelectionRecordById(srId);
        String s = data.getSrOutsideExList();
        List<ExNamePrintVo> list = new ArrayList<>();
        List<ExNameOutsideDto> exNameDtos = JacksonUtils.parseArray(s, ExNameOutsideDto.class);
        if (exNameDtos != null) {
            for (ExNameOutsideDto exNameDto : exNameDtos) {
                List<String> exName6 = exNameDto.getExName6();
                List<String> exName7 = exNameDto.getExName7();
                List<String> exName8 = exNameDto.getExName8();
                List<String> exName9 = exNameDto.getExName9();
                List<String> exName10 = exNameDto.getExName10();
                getExNamePrintVo(allDictionaryList, list, exName6, exName7, exName8, exName9, exName10);
            }
        }
        return BaseResponse.ok(list);
    }

    private List<SysDictionary> getAllDictionaryList(String teId) {
        String allKey = redisUtils.tenantKey("allDictionary", teId);
        Object dictionaryTreeList = redisUtils.get(allKey);
        List<SysDictionary> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysDictionary>>() {
            });
        } else {
            list = getAll(teId);
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        return list;
    }

    private List<SysDictionary> getAll(String teId) {
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) queryWrapper.eq("Te_Id", teId).or().eq("D_Mold", 1);
        queryWrapper.orderByAsc("D_Sort");
        return sysDictionaryMapper.selectList(queryWrapper);
    }
}
