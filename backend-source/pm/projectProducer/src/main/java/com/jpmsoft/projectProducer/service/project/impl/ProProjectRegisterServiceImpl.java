package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.project.ProProjectRegisterRequest;
import com.jpmsoft.common.model.request.project.ProProjectRegisterUnitRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProProjectRegisterMapper;
import com.jpmsoft.projectProducer.mapper.project.ProProjectRegisterUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.ProProjectRegister;
import com.jpmsoft.projectProducer.pojo.entity.project.ProProjectRegisterUnit;
import com.jpmsoft.projectProducer.pojo.vo.project.ProProjectRegisterVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IProProjectRegisterService;
import com.jpmsoft.projectProducer.service.project.ProjectRegisterDataPermission;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目登记服务，主表直接保存，参建单位先删后新增，查询SQL由Mapper XML维护。
 */
@Service
public class ProProjectRegisterServiceImpl extends ServiceImpl<ProProjectRegisterMapper, ProProjectRegister> implements IProProjectRegisterService {
    @Resource
    private ProProjectRegisterUnitMapper unitMapper;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ProjectRegisterDataPermission dataPermission;

    /**
     * 按登录用户单位校验后保存项目登记，同步附件并替换参建单位。
     *
     * @param request 项目登记表单
     * @param user    当前登录用户
     * @return 保存后的登记ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProProjectRegister(ProProjectRegisterRequest request, LogUserInfoVo user) {
        user = dataPermission.currentUser();
        if (request == null || user == null || ValidateUtil.isEmpty(user.getId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        boolean creating = ValidateUtil.isEmpty(request.getId());
        ProProjectRegister old = creating ? null : getById(request.getId());
        if (!creating && old == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (old != null) {
            dataPermission.checkCreator(old.getCreateUid(), user);
        }
        if (old != null && JudgeUtil.nonModifyState(old.getState())) {
            return BaseResponse.fail(ResMsg.stateNotModify);
        }
        ProProjectRegister entity = new ProProjectRegister();
        BeanUtils.copyProperties(request, entity);
        Date now = new Date();
        entity.setId(creating ? GenerateUUID.getUUID() : old.getId());
        entity.setState(creating ? ApprovalStatusEnum.Not_Started.getIndex() : old.getState());
        entity.setStateTime(creating ? now : old.getStateTime());
        entity.setCreateUid(creating ? user.getId() : old.getCreateUid());
        entity.setCreateUname(creating ? user.getName() : old.getCreateUname());
        entity.setCreateTime(creating ? now : old.getCreateTime());
        if (!creating) {
            entity.setUpdateUid(user.getId());
            entity.setUpdateUname(user.getName());
            entity.setUpdateTime(now);
            if (request.getApprovalFile() == null) {
                entity.setApprovalFile(old.getApprovalFile());
            }
            if (request.getRenderingFile() == null) {
                entity.setRenderingFile(old.getRenderingFile());
            }
        }
        // 新增未提供附件分组时生成，修改未提供时保留原分组。
        if (ValidateUtil.isEmpty(entity.getApprovalFile())) {
            entity.setApprovalFile(GenerateUUID.getUUID());
        }
        if (ValidateUtil.isEmpty(entity.getRenderingFile())) {
            entity.setRenderingFile(GenerateUUID.getUUID());
        }
        List<ProProjectRegisterUnit> units = new ArrayList<>();
        if (request.getUnitList() != null) {
            for (int i = 0; i < request.getUnitList().size(); i++) {
                ProProjectRegisterUnitRequest dto = request.getUnitList().get(i);
                if (dto == null) {
                    return BaseResponse.fail("参建单位明细不能为null");
                }
                ProProjectRegisterUnit unit = new ProProjectRegisterUnit();
                BeanUtils.copyProperties(dto, unit);
                unit.setId(GenerateUUID.getUUID());
                unit.setRegisterId(entity.getId());
                unit.setSortNo(dto.getSortNo() == null ? i + 1 : dto.getSortNo());
                unit.setCreateUid(user.getId());
                unit.setCreateUname(user.getName());
                unit.setCreateTime(now);
                units.add(unit);
            }
        }

        if (!(creating ? save(entity) : updateById(entity))) {
            throw new JPMException("项目登记保存失败");
        }
        if (request.getUnitList() != null) {
            unitMapper.deleteByRegisterId(entity.getId());
            for (ProProjectRegisterUnit unit : units) {
                if (unitMapper.insert(unit) != 1) {
                    throw new JPMException("参建单位保存失败");
                }
            }
        }
        SysFile file = creating ? null : sysFileService.getById(entity.getId());
        String relationId = file == null ? request.getFileRelationId() : file.getFileRelationId();
        if (ValidateUtil.isEmpty(relationId)) {
            relationId = GenerateUUID.getUUID();
        }
        sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(),
                ValidateUtil.isEmpty(request.getFileTitle()) ? entity.getProjectName() : request.getFileTitle(),
                ValidateUtil.isEmpty(request.getFileCode()) ? entity.getProjectCode() : request.getFileCode(),
                file == null ? "create" : "update", relationId, null, request.gettCode(), null);
        if (creating) {
            sysFileAnnexService.addFormRefreshRelationFile(
                    Arrays.asList(relationId, entity.getApprovalFile(), entity.getRenderingFile()), entity.getId());
        } else {
            List<String> groups = Arrays.asList(relationId, entity.getApprovalFile(), entity.getRenderingFile());
            List<String> ids = Arrays.asList(request.getFileIds(), request.getApprovalFileIds(), request.getRenderingFileIds());
            for (int i = 0; i < groups.size(); i++) {
                // 未提交文件ID列表时保留该组，提交空字符串时清空。
                if (ids.get(i) != null) {
                    sysFileAnnexService.refreshRelationFile(groups.get(i), ids.get(i), entity.getId());
                }
            }
        }
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 按登录用户单位及XML筛选SQL分页查询项目登记，管理员和白名单单位可查询全部。
     *
     * @param request 筛选及分页参数
     * @return 当前页记录与总条数
     */
    @Override
    public BaseResponse getProProjectRegisterList(ProProjectRegisterRequest request) {
        if (request == null) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<ProProjectRegisterRequest> params = new QueryMap<>(request);
        params.put("id").put("projectName").put("projectCode").put("projectType")
                .put("investorName").put("isGovernmentInvestment").put("state");
        dataPermission.applyScope(params.getQuery(), dataPermission.currentUser());
        Page<ProProjectRegister> page = new Page<>(request.getPage() > 0 ? request.getPage() : 1,
                request.getSize() > 0 ? request.getSize() : 20);
        List<ProProjectRegister> list = baseMapper.getProProjectRegisterList(page, params.getQuery());
        return BaseResponse.ok(list, page.getTotal());
    }

    /**
     * 校验创建人单位后查询主表、参建单位及附件详情。
     *
     * @param id 登记ID
     * @return 登记详情
     */
    @Override
    public BaseResponse getProProjectRegisterById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ProProjectRegisterVO data = baseMapper.getProProjectRegisterById(id);
        if (data == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        dataPermission.checkCreator(data.getCreateUid(), dataPermission.currentUser());
        data.setUnitList(unitMapper.getByRegisterId(id));
        data.setFiles(sysFileAnnexService.getFileByFileRelationId(data.getFileRelationId()));
        data.setApprovalFiles(sysFileAnnexService.getFileByFileRelationId(data.getApprovalFile()));
        data.setRenderingFiles(sysFileAnnexService.getFileByFileRelationId(data.getRenderingFile()));
        return BaseResponse.ok(data);
    }

    /**
     * 校验创建人单位后，在同一事务中删除允许修改的登记、参建单位和单据。
     *
     * @param id 登记ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProProjectRegister(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ProProjectRegister entity = getById(id);
        if (entity == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        dataPermission.checkCreator(entity.getCreateUid(), dataPermission.currentUser());
        if (JudgeUtil.nonModifyState(entity.getState())) {
            return BaseResponse.fail(ResMsg.stateNotModify);
        }
        unitMapper.deleteByRegisterId(id);
        sysFileService.deleteFileById(id);
        if (!removeById(id)) {
            throw new JPMException("项目登记删除失败");
        }
        return BaseResponse.ok();
    }

    /**
     * 统计当前用户可见的全部年度项目登记。
     *
     * @return 总数、审核中和已审核数量
     */
    @Override
    public BaseResponse getRegistrationStatistics() {
        Map<String, Object> params = new HashMap<>();
        dataPermission.applyScope(params, dataPermission.currentUser());
        return BaseResponse.ok(baseMapper.countRegistrationStatistics(params));
    }
}
