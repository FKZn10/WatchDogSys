package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.other.ApplyRoleMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.ImportTDepartmentDto;
import com.jpmsoft.projectProducer.pojo.entity.other.ApplyRole;
import com.jpmsoft.projectProducer.pojo.entity.other.SysPositionUserP;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ApplyRoleImpl extends ServiceImpl<ApplyRoleMapper, ApplyRole> implements IApplyRoleService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private UserProducerFeign userProducerFeign;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysPositionUserPService sysPositionUserPService;


    @Override
    public BaseResponse approve2SyncDepartmentP(String id, String token) {
        ApplyRole entity = baseMapper.selectById(id);
        if (entity == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isEmpty(entity.getState()) || !entity.getState().equals("7")) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        List<SysUser> users = sysUserService.getUserInfoByPhone(Arrays.asList(entity.getPhone()));
        String uid = null;
        if (CollectionUtils.isEmpty(users)) {
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            //用户不存在创建用户信息
            String codeRule = null;
            SysUser newUser = new SysUser();
            newUser.setUId(GenerateUUID.getUUID());
            newUser.setUMdmNo(entity.getUserid());
            newUser.setUMtel(entity.getPhone());
            newUser.setUName(entity.getName());
            newUser.setUAccount(entity.getPhone());
            newUser.setUSalt(UUID.randomUUID().toString());
            newUser.setCId(entity.getCId());
            newUser.setUType(UserTypeEnum.Out_User.getIndex().toString());
            newUser.setUState("1");
            newUser.setUEmail(entity.getEmail());
            newUser.setUFirstLogin(1);
            String pwd = "jgpt@2023";
            newUser.setUPassword(apiUtilsService.PasswrodEncryptionResult(pwd, newUser.getUSalt(), newUser.getUAccount()));
            if (apiUtilsService.sequenceRestEnable("SysUser")) {
                newUser.setUCode(apiUtilsService.generateCodeByTCode("SysUser", newUser));
                if (ValidateUtil.isEmpty(newUser.getUCode()))
                    throw new JPMException(ResMsg.codeGenerError);
                codeRule = "SysUser";
            }
            newUser.setUCreateuId(user.getId());
            newUser.setUCreateuName(user.getName());
            newUser.setUCreatetime(new Date());
            newUser.setUSignature(GenerateUUID.getUUID());
            newUser.setUQualification(GenerateUUID.getUUID());
            sysUserService.save(newUser);
            if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, newUser);
            uid = newUser.getUId();
        } else {
            SysUser user = users.get(0);
            uid = user.getUId();
        }
        //若无项目组织机构则导入模板
        Integer havingDep = baseMapper.havingDepartmentP(entity.getPId());
        if (ValidateUtil.isNull(havingDep) || havingDep == 0) {
            ImportTDepartmentDto req = new ImportTDepartmentDto();
            req.setPId(entity.getPId());
            req.setToId(sysConfigService.getConfigByCode("zjtExportOrganization"));
            userProducerFeign.ImportT_Department(req);
        }
        String ppId = baseMapper.getDepartmentPPosition(entity.getPId(), sysConfigService.getConfigByCode("zjtPositionUserPCode"));
        Integer havingPositionUserP = baseMapper.havingPositionUserP(ppId, uid);
        if (ValidateUtil.isNull(havingPositionUserP) || havingPositionUserP == 0) {
            SysPositionUserP p = new SysPositionUserP();
            p.setPupId(GenerateUUID.getUUID());
            p.setPpId(ppId);
            p.setuId(uid);
            p.setPupSort(1);
            sysPositionUserPService.save(p);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse syncAll(String token) {
        List<ApplyRole> list = list();
        if (!CollectionUtils.isEmpty(list)) {
            for (ApplyRole entity : list) {
                approve2SyncDepartmentP(entity.getId(), token);
            }
        }
        return BaseResponse.ok();
    }
}
