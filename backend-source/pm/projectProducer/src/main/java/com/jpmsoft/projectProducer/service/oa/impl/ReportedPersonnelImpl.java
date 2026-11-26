package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.AuditTypeEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelModel;
import com.jpmsoft.common.rabbit.AuditSender;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.OaReportedEnum;
import com.jpmsoft.projectProducer.mapper.oa.ReportedPersonnelMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.project.ReportedPersonnel;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.oa.IReportedPersonnelDtlService;
import com.jpmsoft.projectProducer.service.oa.IReportedPersonnelService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportedPersonnelImpl extends ServiceImpl<ReportedPersonnelMapper, ReportedPersonnel> implements IReportedPersonnelService {
    @Resource
    private IReportedPersonnelDtlService reportedPersonnelDtlService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private AuditSender auditSender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateReportedPersonnel(ReportedPersonnelModel request) {
        ReportedPersonnel entity = new ReportedPersonnel();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getRpId())) {
            entity.setRpCreateTime(new Date());
            entity.setRpId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getRpId(), request.gettId(), entity.getRpState() + "", entity.getRpCreateUid(), entity.getRpCreateUname()
                    , entity.getRpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getRpId());

        } else {
            ReportedPersonnel model = baseMapper.selectById(request.getRpId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getRpState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setRpState(model.getRpState());
            entity.setRpStateTime(model.getRpStateTime());
            entity.setRpCreateTime(model.getRpCreateTime());
            entity.setRpCreateUid(model.getRpCreateUid());
            entity.setRpCreateUname(model.getRpCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getRpId(), request.gettId(), entity.getRpState() + "", entity.getRpCreateUid(), entity.getRpCreateUname()
                    , entity.getRpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getRpId());

        }
        reportedPersonnelDtlService.createOrUpdateReportedPersonnelDtl(request.getRepPersonnelDtls(), entity.getRpId());
        return BaseResponse.ok(entity.getRpId());
    }

    @Override
    public BaseResponse createOrUpdateReportedPersonnelCj(ReportedPersonnelModel request) {
        preCheck(request);
        return createOrUpdateReportedPersonnel(request);
    }

    @Override
    public BaseResponse getReportedPersonnelList(ReportedPersonnelModel request, String uId) {
        String authSql = null;
        if (request.getAuth()) {
            authSql = sysUserService.getAuthSql("Reported_Personnel", uId, "Rp_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Rp_State");
            if (ValidateUtil.isEmpty(authSql))
                return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<ReportedPersonnelModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pCode")
                .put("rpType").put("rpCreateUname").put("rpState").put("rpChangeType");
        IPage<ReportedPersonnelModel> page = new Page<>(request.getPage(), request.getSize());
        List<ReportedPersonnelModel> models = baseMapper.getReportedPersonnelList(page, params.getQuery());
        List<ReportedPersonnelModel> datas = new ArrayList<>();
        models.forEach(model -> {
            List<ReportedPersonnelDtlModel> repPersonnelDtls = model.getRepPersonnelDtls();
            if (ValidateUtil.isNotEmpty(repPersonnelDtls)) {
                List<ReportedPersonnelDtlModel> dtls = repPersonnelDtls.stream().map(n -> {
                    n.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(n.getuIdcard())));
                    return n;
                }).collect(Collectors.toList());
                model.setRepPersonnelDtls(dtls);
            }
            datas.add(model);
        });
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getReportedPersonnelById(String rpId) {
        ReportedPersonnelModel data = baseMapper.getReportedPersonnelById(rpId);
        List<ReportedPersonnelDtlModel> repPersonnelDtls = data.getRepPersonnelDtls();
        if (ValidateUtil.isNotEmpty(repPersonnelDtls)) {
            List<ReportedPersonnelDtlModel> dtls = repPersonnelDtls.stream().map(n -> {
                n.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(n.getuIdcard())));
                return n;
            }).collect(Collectors.toList());
            data.setRepPersonnelDtls(dtls);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approveReportedPersonnelById(String rpId) {
        ReportedPersonnel entity = baseMapper.selectById(rpId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (entity.getRpState() != ApprovalStatusEnum.Already_Over.getIndex())
            return BaseResponse.fail(ResMsg.stateCannotAct);
        List<ReportedPersonnelDtlModel> dtls = reportedPersonnelDtlService.getReportedPersonnelDtlByRpId(rpId);
        if (!CollectionUtils.isEmpty(dtls)) {
            //region 找到电话号一致的用户 赋值uid
            List<SysUser> users = sysUserService.getUserInfoByPhone(dtls.stream().filter(n -> ValidateUtil.isNotEmpty(n.getRpdTel()))
                    .map(ReportedPersonnelDtlModel::getRpdTel).collect(Collectors.toList()));

            dtls = dtls.stream().peek(dtl -> {
                if (!CollectionUtils.isEmpty(users)) {
                    List<SysUser> user = users.stream().filter(n -> n.getUMtel().equals(dtl.getRpdTel())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(user))
                        dtl.setRpdUid(user.stream().findFirst().get().getUId());
                    else {
                        dtl.setRpdUid(setNewUser(dtl, entity));
                    }
                } else {
                    dtl.setRpdUid(setNewUser(dtl, entity));
                }
            }).collect(Collectors.toList());
            //endregion
            //同步项目组织机构
            BaseResponse baseResponse = apiUtilsService.createOrDeletePUPByBath(dtls);
            if (baseResponse.getStatusCode() != 200 || !baseResponse.getSuccess())
                throw new JPMException(ResMsg.syncProDepError);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse approve2SyncDepartmentP(String rpId, LogUserInfoVo user) {
        ReportedPersonnel entity = baseMapper.selectById(rpId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(entity.getRpState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        if (ValidateUtil.isNotEmpty(entity.getRpMeetingAgree()) && entity.getRpMeetingAgree().equals("true")) {
            //会议通过同意变更
            if (ValidateUtil.isAnObjEmpty(entity.getRpOriginalUser(), entity.getRpOriginalPosition(), entity.getRpChangeUser(), entity.getRpChangePosition()))
                return BaseResponse.fail(ResMsg.noMustParam);
            baseMapper.updatePositionUserP(entity.getRpOriginalUser(), entity.getRpOriginalPosition(), entity.getRpChangeUser(), entity.getRpChangePosition());
            auditSender.sendAudit("项目组织机构人员变更", user.getId(), user.getName(), entity.getRpId(), AuditTypeEnum.upd.getkey());
        }
        return BaseResponse.ok();
    }

    private String setNewUser(ReportedPersonnelDtlModel dtl, ReportedPersonnel entity) {
        if (dtl.getRpdType().equals("移除") || ValidateUtil.isNotEmpty(dtl.getRpdUid())) return dtl.getRpdUid();
        String codeRule = null;
        SysUser newUser = new SysUser();
        newUser.setUId(GenerateUUID.getUUID());
        newUser.setUMtel(dtl.getRpdTel());
        newUser.setUName(dtl.getRpdName());
        newUser.setUAccount(dtl.getRpdTel());
        newUser.setUSalt(UUID.randomUUID().toString());
        newUser.setCId(dtl.getRpdCpy());
        newUser.setUType(UserTypeEnum.Out_User.getIndex().toString());
        newUser.setUState("1");
        newUser.setUFirstLogin(1);
        newUser.setUIdcard(dtl.getRpdNumber());
        String idCard = apiUtilsService.SelfSm2Decrypt(dtl.getRpdNumber());
        String pwd = "jpm" + dtl.getRpdTel();
        if (ValidateUtil.isNotEmpty(idCard) && idCard.length() > 6)
            pwd = idCard.substring(idCard.length() - 6);
        newUser.setUPassword(apiUtilsService.PasswrodEncryptionResult(pwd, newUser.getUSalt(), newUser.getUAccount()));
        if (apiUtilsService.sequenceRestEnable("SysUser")) {
            newUser.setUCode(apiUtilsService.generateCodeByTCode("SysUser", newUser));
            if (ValidateUtil.isEmpty(newUser.getUCode()))
                throw new JPMException(ResMsg.codeGenerError);
            codeRule = "SysUser";
        }
        newUser.setUCreateuId(entity.getRpCreateUid());
        newUser.setUCreateuName(entity.getRpCreateUname());
        newUser.setUCreatetime(new Date());
        newUser.setUSignature(GenerateUUID.getUUID());
        newUser.setUQualification(GenerateUUID.getUUID());
        sysUserService.save(newUser);
        sysFileAnnexService.copyFileAnnex2NewRelation(Arrays.asList(dtl.getRpdQualification()), Arrays.asList(newUser.getUQualification()), newUser.getUId());
        if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, newUser);
        return newUser.getUId();
    }


    private void preCheck(ReportedPersonnelModel request) {
        if (Objects.equals(request.getRpType(), OaReportedEnum.Filing.getkey())) {//报备类型
            //单项目不允许同时多个报备进行
            QueryWrapper<ReportedPersonnel> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", request.getpId());
            wrapper.ne("Rp_State", ApprovalStatusEnum.Invalid.getIndex());
            wrapper.ne("Rp_State", ApprovalStatusEnum.Already_Over.getIndex());
            if (ValidateUtil.isNotEmpty(request.getRpId()))
                wrapper.ne("Rp_Id", request.getRpId());
            if (baseMapper.exists(wrapper)) throw new JPMException(ResMsg.recordUnFinish);
            String surplus = reportedPersonnelDtlService.can2Filing(request.getRepPersonnelDtls(), request.getpId());
            if (ValidateUtil.isNotEmpty(surplus))
                throw new JPMException(String.format(ResMsg.lackMustPPUser, surplus));
        }
    }
}
