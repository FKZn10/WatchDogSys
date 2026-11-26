package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInvitationSchemaDTO;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInvitationSchemaMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInvitationSchema;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInvitationSchemaService;
import com.jpmsoft.projectProducer.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 邀标工作方案
 */
@Service
public class SecInvitationSchemaImpl extends ServiceImpl<SecInvitationSchemaMapper, SecInvitationSchema> implements ISecInvitationSchemaService {
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 获取邀请招标列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getSecInvitationSchemaList(SecInvitationSchemaDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 创建人、流程处理人、抄送人、协同人；在中标通知书审批通过后集团用户可以查看所有中标通知书已结束的，同单位的可以查看同单位的中标通知书已结束的
        String authSql = getSecInvitationSchemaAuthSql(request, UserUtils.getUserInfo());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<SecInvitationSchemaDTO> map = new QueryMap<>(request);
        map.put("authSql", authSql).put("createUid").put("createUname").put("createTime").put("state")
                .put("stateTime").put("pId").put("pName").put("secId").put("secName").put("pCategory")
                .put("pNames").put("secNames").put("pCategorys").put("createUnames").put("states")
                .put("isNew")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd");
        Page<SecInvitationSchemaDTO> page = new Page<>(request.getPage(), request.getSize());
        List<SecInvitationSchemaDTO> list = baseMapper.getSecInvitationSchemaList(page, map.getQuery());
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取邀请招标多条件筛选
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getSecInvitationSchemaClassInfo(SecInvitationSchemaDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 创建人、流程处理人、抄送人、协同人；在中标通知书审批通过后集团用户可以查看所有中标通知书已结束的，同单位的可以查看同单位的中标通知书已结束的
        String authSql = getSecInvitationSchemaAuthSql(request, UserUtils.getUserInfo());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<SecInvitationSchemaDTO> map = new QueryMap<>(request);
        map.put("authSql", authSql).put("createUid").put("createUname").put("createTime").put("state")
                .put("stateTime").put("pId").put("pName").put("secId").put("secName").put("pCategory")
                .put("pNames").put("secNames").put("pCategorys").put("createUnames").put("states")
                .put("isNew")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd");
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecInvitationSchemaDTO.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
            List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, map.getQuery());
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getSecInvitationSchemaClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    private String getSecInvitationSchemaAuthSql(SecInvitationSchemaDTO request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("Sec_Invitation_Schema", user.getId(), "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        if (ValidateUtil.isEmpty(authSql)) {
            return null;
        }
        boolean groupUser = ValidateUtil.isNotEmpty(njcj) && Objects.equals(njcj, user.getCId());
        boolean myParticipation = ValidateUtil.isNotEmpty(request.getParticipationOrFinished()) && request.getParticipationOrFinished().contains("01");
        boolean finished = ValidateUtil.isNotEmpty(request.getParticipationOrFinished()) && request.getParticipationOrFinished().contains("02");
        String finishedSql = "exists (select 1 from Sec_Win_Notice swn where swn.Sec_Id = mn.Sec_Id and swn.Wn_State = 7)";
        String sameCompanyFinishedSql = getSameCompanyFinishedSql(user.getCId(), finishedSql);
        if (finished) {
            if (groupUser) {
                authSql = " where " + finishedSql + " ";
            } else {
                authSql = appendAndAuthSql(appendOrAuthSql(authSql, sameCompanyFinishedSql), finishedSql);
            }
        } else if (groupUser && !myParticipation) {
            authSql = appendOrAuthSql(authSql, finishedSql);
        } else if (!myParticipation) {
            authSql = appendOrAuthSql(authSql, sameCompanyFinishedSql);
        }
        return authSql;
    }

    private String appendOrAuthSql(String authSql, String orSql) {
        if (ValidateUtil.isEmpty(orSql)) {
            return authSql;
        }
        int whereIndex = authSql.toLowerCase().indexOf("where");
        if (whereIndex < 0) {
            return authSql;
        }
        return authSql.substring(0, whereIndex) + "where ((" + authSql.substring(whereIndex + "where".length()) + ") or (" + orSql + ")) ";
    }

    private String appendAndAuthSql(String authSql, String andSql) {
        if (ValidateUtil.isEmpty(andSql)) {
            return authSql;
        }
        return authSql + " and (" + andSql + ") ";
    }

    private String getSameCompanyFinishedSql(String cId, String finishedSql) {
        if (ValidateUtil.isEmpty(cId)) {
            return null;
        }
        return finishedSql + " and exists (select 1 from Sys_User su where su.U_Id = mn.Create_Uid and su.C_Id = '" + cId.replace("'", "''") + "')";
    }
}
