package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConEnisRequest;
import com.jpmsoft.common.model.response.DesignChange.ConEnisResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConEnisMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnis;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConEnisService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ConEnisImpl extends ServiceImpl<ConEnisMapper, ConEnis> implements IConEnisService {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "enisId", codeParam = "enisCode", method = "setEnisCode", codeRule = "ConEnis", beanClass = ConEnisRequest.class)
    public BaseResponse createOrUpdateConEnis(ConEnisRequest request) {
        ConEnis entity = new ConEnis();
        BeanUtils.copyProperties(request, entity);
        //if (ValidateUtil.isEmpty(request.getEnisCode()))
        //    return BaseResponse.fail(ResMsg.codeIsEmpty);
        QueryWrapper<ConEnis> wrapper = new QueryWrapper<>();
        wrapper.eq("Enis_Code", request.getEnisCode());
        if (!ValidateUtil.isEmpty(entity.getEnisId()))
            wrapper.ne("Enis_Id", request.getEnisId());
        wrapper.ne("Enis_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getEnisId())) {
            entity.setEnisId(GenerateUUID.getUUID());
            entity.setEnisCreateTime(new Date());
            entity.setEnisStateTime(null);
            entity.setEnisState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEnisId(), request.gettId(), entity.getEnisState() + "", entity.getEnisCreateUid(), entity.getEnisCreateUname()
                    , entity.getEnisCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getEnisId());

        } else {
            ConEnis model = baseMapper.selectById(request.getEnisId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEnisState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEnisCreateTime(model.getEnisCreateTime());
            entity.setEnisCreateUid(model.getEnisCreateUid());
            entity.setEnisCreateUname(model.getEnisCreateUname());
            entity.setEnisState(model.getEnisState());
            entity.setEnisStateTime(model.getEnisStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEnisId(), request.gettId(), entity.getEnisState() + "", entity.getEnisCreateUid(), entity.getEnisCreateUname()
                    , entity.getEnisCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getEnisId());

        }
        return BaseResponse.ok(entity.getEnisId());

    }

    @Override
    public BaseResponse getConEnisList(ConEnisRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Enis", uId, "Enis_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Enis_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        return queryList(request, authSql);
    }

    @Override
    public BaseResponse getConEnisListGm(ConEnisRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Enis", uId, "Enis_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Enis_State");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        SysUser user = sysUserService.getById(uId);
        if ("1".equals(user.getUType())) {
            log.info("内部用户，无需权限校验");
            log.info("uId:" + uId + "  uType:" + user.getUType());
            request.setFilterEnisAuth(null);
        } else {
            log.info("外部用户，权限校验，筛选同单位");
            log.info("uId:" + uId + "  cId:" + user.getCId());
            request.setCurrentCompanyId(user.getCId());
        }
        return queryList(request, authSql);
    }

    @Override
    public BaseResponse getConEnisListNoAuth(ConEnisRequest request) {
        return queryList(request, null);
    }

    private BaseResponse queryList(ConEnisRequest request, String authSql) {
        log.info("请求参数 filterEnisAuth {} currentCompanyId {}", request.getFilterEnisAuth(), request.getCurrentCompanyId());
        QueryMap<ConEnisRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("enisCode").put("enisContent").put("filterEnBudget")
                .put("enisProjectName").put("enisProposedId").put("enisMajor").put("enisDep").put("enisName").put("enisMainCode")
                .put("enisDrawingNo").put("enisType").put("pId").put("conId").put("enisState").put("enisSubCode")
                .put("enisDepName").put("enisCreateUname").put("enisProposedName").put("enisLocation").put("enisLocation")
                .put(request.getEnisCreateTimes(), "enisCreateTimeStart", "enisCreateTimeEnd").put("pId")
                .put(request.getEnisDates(), "enisDateStart", "enisDateEnd").put("enisCategory")
                .put(request.getEnisFinishDates(), "enisFinishDateStart", "enisFinishDateEnd").put("filterEnChangeApply")
                .put("filterConDco").put("filterEnisAuth").put("currentCompanyId");
        log.info("params:" + params);
        log.info("queryMap{}", JacksonUtils.toJSONString(params));
        IPage<ConEnisResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEnisResponse> datas = baseMapper.getConEnisList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEnisById(String enisId) {
        ConEnisResponse data = baseMapper.getConEnisById(enisId);
        return BaseResponse.ok(data);
    }
}
