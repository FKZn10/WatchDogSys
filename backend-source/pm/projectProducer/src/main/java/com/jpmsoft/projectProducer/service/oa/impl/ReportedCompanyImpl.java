package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.ReportedCompanyMapper;
import com.jpmsoft.projectProducer.mapper.other.SysParticipantUnitMapper;
import com.jpmsoft.projectProducer.pojo.dto.oa.ReportedCompanyDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.ReportedCompany;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.other.SysParticipantUnit;
import com.jpmsoft.projectProducer.pojo.vo.oa.ReportedCompanyVo;
import com.jpmsoft.projectProducer.service.oa.IReportedCompanyService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysCompanyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/21 14:41
 */
@Service
public class ReportedCompanyImpl extends BaseFileImpl<ReportedCompanyMapper, ReportedCompany, ReportedCompanyDto> implements IReportedCompanyService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysCompanyService sysCompanyService;
    @Resource
    private SysParticipantUnitMapper sysParticipantUnitMapper;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateReportedCompany(ReportedCompanyDto request) {
        if (ValidateUtil.isAnObjEmpty(request.getPId(), request.getCCreditcode(), request.getCType()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (sysParticipantUnitMapper.getAlreadyHaveCount(request.getPId(), request.getCCreditcode(), request.getCType()) > 0)
            return BaseResponse.fail(ResMsg.reportedCpyExists);
        ReportedCompany entity = new ReportedCompany();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getCBusinessLicense(), request.getCQualification(), request.getCCredentials()), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getCBusinessLicense(), request.getCQualification(), request.getCCredentials()),
                    Arrays.asList(request.getFileIds(), request.getCBusinessLicenseIds(), request.getCQualificationIds(), request.getCCredentialsIds()), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getReportedCompanyList(ReportedCompanyDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.NOT.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ReportedCompanyDto> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("pName").put("cName").put("pId")
                .put("state").put("createUname").put("cType");
        IPage<ReportedCompanyVo> page = new Page<>(request.getPage(), request.getSize());
        List<ReportedCompanyVo> datas = baseMapper.getReportedCompanyList(page, query.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getReportedCompanyById(String id) {
        return BaseResponse.ok(baseMapper.getReportedCompanyById(id));
    }

    @Override
    public BaseResponse getReportedCompanyGroupTypeByPid(String pId) {
        List<ReportedCompanyVo> data = baseMapper.getReportedCompanyGroupTypeByPid(pId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ReportedCompanyVo> res = new ArrayList<>();
            List<String> types = new ArrayList<>();
            for (ReportedCompanyVo d : data) {
                if (types.contains(d.getCType())) {
                    res.add(d);
                    types.add(d.getCType());
                }
            }
            return BaseResponse.ok(res);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approved2SyncCompanyInfo(String id) {
        ReportedCompany entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!JudgeUtil.isApprovedState(entity.getState())) return BaseResponse.fail(ResMsg.stateCannotAct);
        SysCompany cpy = sysCompanyService.getByCreditCode(entity.getCCreditcode());
        Date now = new Date();
        //region 不存在单位，先创建单位信息
        if (cpy == null) {//不存在单位
            cpy = new SysCompany();
            BeanUtils.copyProperties(entity, cpy);
            cpy.setcCreatetime(now);
            cpy.setcCreateuId(entity.getCreateUid());
            cpy.setcCreateuName(entity.getCreateUname());
            cpy.setcBusinessLicense(GenerateUUID.getUUID());
            cpy.setcQualification(GenerateUUID.getUUID());
            cpy.setcCredentials(GenerateUUID.getUUID());
            cpy.setcState("1");
            cpy.setcId(GenerateUUID.getUUID());
            String codeRule = null;
            if (apiUtilsService.sequenceRestEnable("SysCompany") == true) {
                cpy.setcCode(apiUtilsService.generateCodeByTCode("SysCompany", cpy));
                if (ValidateUtil.isEmpty(cpy.getcCode()))
                    return new BaseResponse("生成编码失败");
                codeRule = "SysCompany";
            }
            sysCompanyService.save(cpy);
            sysFileAnnexService.copyFileAnnex2NewRelation(Arrays.asList(entity.getCBusinessLicense(), entity.getCQualification(), entity.getCCredentials()),
                    Arrays.asList(cpy.getcBusinessLicense(), cpy.getcQualification(), cpy.getcCredentials()), cpy.getcId());
            if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, cpy);
        }
        //endregion

        //region 创建项目参建设单位
        SysParticipantUnit unit = new SysParticipantUnit();
        unit.setPuId(GenerateUUID.getUUID());
        unit.setPuCreateTime(now);
        unit.setPuCreateUid(entity.getCreateUid());
        unit.setPuCreateUname(entity.getCreateUname());
        unit.setPId(entity.getPId());
        unit.setCId(cpy.getcId());
        unit.setCType(entity.getCType());
        sysParticipantUnitMapper.insert(unit);
        //endregion

        return BaseResponse.ok();
    }
}
