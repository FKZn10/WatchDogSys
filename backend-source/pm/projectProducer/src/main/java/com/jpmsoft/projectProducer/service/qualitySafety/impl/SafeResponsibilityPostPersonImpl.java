package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserFeign;
import com.jpmsoft.projectProducer.mapper.qualitySafety.SafeResponsibilityPostPersonMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ReportedPersonnel;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityPostPerson;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.oa.IReportedPersonnelService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityPostPersonService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 安全责任书岗位人员
 */
@Service
public class SafeResponsibilityPostPersonImpl extends ServiceImpl<SafeResponsibilityPostPersonMapper, SafeResponsibilityPostPerson> implements ISafeResponsibilityPostPersonService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IReportedPersonnelService reportedPersonnelService;
    @Resource
    private UserFeign userFeign;

    /**
     * 新增或修改安全责任书岗位人员
     *
     * @param request 岗位人员数据
     * @return 岗位人员ID
     */
    @Override
    public BaseResponse createOrUpdatePostPerson(SafeResponsibilityPostPersonDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SafeResponsibilityPostPerson entity = new SafeResponsibilityPostPerson();
        BeanUtils.copyProperties(request, entity);
        LogUserInfoVo userInfo = UserUtils.getUserInfo();
        if (ValidateUtil.isNotEmpty(entity.getId())) {
            entity.setUpdateTime(new Date());
            entity.setUpdateUid(userInfo.getId());
            entity.setUpdateUname(userInfo.getName());
            // sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), request.getSecId(), request.getTCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getIdentityCardFile(), request.getInductionCertificateFile(), request.getQualificationsFile(), request.getFileRelationId()),
                    Arrays.asList(request.getIdentityCardFileIds(), request.getInductionCertificateFileIds(), request.getQualificationsFileIds(), request.getFileIds()), entity.getId());
            this.updateById(entity);
        } else {
            entity.setCreateTime(new Date());
            entity.setCreateUid(userInfo.getId());
            entity.setCreateUname(userInfo.getName());
            // sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), request.getSecId(), request.getTCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getIdentityCardFile(), request.getInductionCertificateFile(), request.getQualificationsFile(), request.getFileRelationId()), entity.getId());
            this.save(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 获取安全责任书岗位人员列表
     * <p>
     * 支持按项目、合同、人员、单位、岗位、工种、联系方式、证书、流程状态和创建人筛选，
     * 查询结果包含项目名称与合同名称，并按照请求分页返回。
     *
     * @param request 筛选及分页参数
     * @return 岗位人员分页列表
     */
    @Override
    public BaseResponse getPostPersonList(SafeResponsibilityPostPersonDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        IPage<SafeResponsibilityPostPersonDTO> page = new Page<>(request.getPage(), request.getSize());
        List<SafeResponsibilityPostPersonDTO> data = baseMapper.getPostPersonList(page, request);
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取安全责任书岗位人员
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getPostPersonById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        return BaseResponse.ok(baseMapper.getPostPersonById(id));
    }

    /**
     * 删除安全责任书岗位人员
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse deletePostPersonById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        sysFileService.deleteFileById(id);
        removeById(id);
        return BaseResponse.ok();
    }

    /**
     * 同步上报人员
     *
     * @param rpId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncReportedPersonnel(String rpId) {
        if (ValidateUtil.isEmpty(rpId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ReportedPersonnel personnel = reportedPersonnelService.getById(rpId);
        if (ValidateUtil.isNull(personnel)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(personnel.getRpState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        // 判断是否已经同步
        SafeResponsibilityPostPerson person = getOne(Wrappers.<SafeResponsibilityPostPerson>lambdaQuery()
                .eq(SafeResponsibilityPostPerson::getPId, personnel.getPId())
                .eq(SafeResponsibilityPostPerson::getConId, personnel.getConId())
                .eq(SafeResponsibilityPostPerson::getName, personnel.getRpApplicant())
                .eq(SafeResponsibilityPostPerson::getCertificateNumber, personnel.getRpCertNumber())
        );
        LogUserInfoVo userInfo = UserUtils.getUserInfo();
        SafeResponsibilityPostPersonDTO request = new SafeResponsibilityPostPersonDTO();
        if (ValidateUtil.isNull(person)) {
            SafeResponsibilityPostPerson entity = new SafeResponsibilityPostPerson();
            entity.setId(GenerateUUID.getUUID());
            entity.setPId(personnel.getPId());
            entity.setConId(personnel.getConId());
            entity.setName(personnel.getRpApplicant());
            entity.setUnitId(personnel.getRpUnitId());
            entity.setPostId(personnel.getRpChangePosition());
            entity.setJobCategory(personnel.getRpJobType());
            entity.setPhone(personnel.getRpContact());
            entity.setCertificateNumber(personnel.getRpCertNumber());
            entity.setIdentityCardFile(GenerateUUID.getUUID());
            entity.setInductionCertificateFile(GenerateUUID.getUUID());
            entity.setQualificationsFile(GenerateUUID.getUUID());
            entity.setState(ApprovalStatusEnum.Already_Over.getIndex());
            entity.setStateTime(new Date());
            // 自动计算
            long count = count(Wrappers.<SafeResponsibilityPostPerson>lambdaQuery()
                    .eq(SafeResponsibilityPostPerson::getPId, personnel.getPId())
                    .eq(SafeResponsibilityPostPerson::getPostId, personnel.getRpChangePosition())
            );
            entity.setSortNo(Math.toIntExact(count + 1));
            List<String> oldIds = Arrays.asList(personnel.getRpCardFile(), personnel.getRpOriginalFile(), personnel.getRpChangeFile());
            List<String> newIds = Arrays.asList(entity.getIdentityCardFile(), entity.getInductionCertificateFile(), entity.getQualificationsFile());
            sysFileAnnexService.copyFileAnnex2NewRelation(oldIds, newIds, entity.getId(), true);
            entity.setCreateUid(userInfo.getId());
            entity.setCreateUname(userInfo.getName());
            entity.setCreateTime(new Date());
            save(entity);
            BeanUtils.copyProperties(entity, request);
        } else {
            person.setPId(personnel.getPId());
            person.setConId(personnel.getConId());
            person.setName(personnel.getRpApplicant());
            person.setUnitId(personnel.getRpUnitId());
            person.setPostId(personnel.getRpChangePosition());
            person.setJobCategory(personnel.getRpJobType());
            person.setPhone(personnel.getRpContact());
            person.setCertificateNumber(personnel.getRpCertNumber());
            person.setUpdateUid(userInfo.getId());
            person.setUpdateUname(userInfo.getName());
            person.setUpdateTime(new Date());
            List<String> oldIds = Arrays.asList(personnel.getRpCardFile(), personnel.getRpOriginalFile(), personnel.getRpChangeFile());
            List<String> newIds = Arrays.asList(person.getIdentityCardFile(), person.getInductionCertificateFile(), person.getQualificationsFile());
            sysFileAnnexService.copyFileAnnex2NewRelation(oldIds, newIds, person.getId(), true);
            updateById(person);
            BeanUtils.copyProperties(person, request);
        }
        return userFeign.syncSafeResponsibilityPostPerson(request);
    }
}
