package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPositionRoleMapper;
import com.jpmsoft.userProducer.pojo.dto.BtnPowerRoleDto;
import com.jpmsoft.userProducer.pojo.dto.PositionDto;
import com.jpmsoft.userProducer.pojo.dto.UserPDPDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionRole;
import com.jpmsoft.userProducer.pojo.entity.SysRole;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.AuthorityBtnVo;
import com.jpmsoft.userProducer.pojo.vo.LogInfoVo;
import com.jpmsoft.userProducer.pojo.vo.SysRoleSourceVo;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysPositionRoleService;
import com.jpmsoft.userProducer.service.ISysUserService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysPositionRoleImpl extends ServiceImpl<SysPositionRoleMapper, SysPositionRole> implements ISysPositionRoleService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public List<SysPositionRole> getPositionRoleByPId(String pId) {
        QueryWrapper<SysPositionRole> wrapper = new QueryWrapper<>();
        wrapper.eq("P_ID", pId);
        List<SysPositionRole> models = baseMapper.selectList(wrapper);
        return models;
    }

    @Override
    public BaseResponse getRoleByPosition(PositionDto request) {
        List<String> list = new ArrayList<>();
        if (!ValidateUtil.isEmpty(request.getpId())) {
            for (SysPositionRole model : getPositionRoleByPId(request.getpId())) {
                list.add(model.getRoleId());
            }
        }
        return BaseResponse.ok(list);
    }

    @Override
    public void CreatePositionRoles(String roleIds, String positionId) {
        deletePositionRoles(positionId);
        if (!ValidateUtil.isEmpty(roleIds)) {
            String[] ids = roleIds.split(",");
            for (String id : ids) {
                SysPositionRole pojo = new SysPositionRole();
                pojo.setpId(positionId);
                pojo.setRoleId(id);
                pojo.setPrId(GenerateUUID.getUUID());
                baseMapper.insert(pojo);
            }
        }

    }

    @Override
    public void deletePositionRoles(String pId) {
        QueryWrapper<SysPositionRole> wrapper = new QueryWrapper<>();
        wrapper.eq("P_ID", pId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<String> loginToGetUserRoleName(String uId, InterfaceEncDecDTO ied) {
        if (ied == null) {
            ied = sysConfigEntityService.getInterfaceEncDec();
        }
        List<SysRole> roles = baseMapper.loginToGetUserRoleName(uId);
        if (ied.getLoginVerify()) {
            for (SysRole role : roles) {
                log.info("校验角色完整性：" + role.getRoleName() + role.getRoleCode());
                if (!sysUserService.userSignVerify(ied, ied.getLoginVerify(), role.signContent(), role.getSign()))
                    throw new JPMException(BaseMsg.dataVerifyError);
            }
        }
        return roles.stream().map(SysRole::getRoleName).distinct().collect(Collectors.toList());
    }

    @Override
    public LogInfoVo setUserRoleNameAndBtnRole(LogInfoVo logInfo, SysUser user) {
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        if (ied.getLoginVerify()) {
            //用户信息
            log.info("校验用户信息完整性");
            if (!sysUserService.userSignVerify(ied, ied.getLoginVerify(), user.signContent(), user.getSign()))
                throw new JPMException(BaseMsg.userVerifyError);
        }
        List<String> userRoles = loginToGetUserRoleName(user.getuId(), ied);
        if (!CollectionUtils.isEmpty(userRoles)) {
            List<AuthorityBtnVo> auths = new ArrayList<>();
            List<BtnPowerRoleDto> btns = baseMapper.getAllBtnPowerRole(userRoles);
            for (String userRole : userRoles) {
                AuthorityBtnVo auth = new AuthorityBtnVo();
                auth.setId(userRole);
                List<String> roleBtns = btns.stream().filter(n -> n.getRoleName().equals(userRole)).map(BtnPowerRoleDto::getPowerCode).collect(Collectors.toList());
                auth.setOperation(CollectionUtils.isEmpty(roleBtns) ? new ArrayList<>() : roleBtns);
                auths.add(auth);
            }
            logInfo.setBtnRoles(CollectionUtils.isEmpty(auths) ? new ArrayList<>() : auths);
        } else {
            logInfo.setBtnRoles(new ArrayList<>());
        }
        logInfo.setRoles(userRoles);
        return logInfo;
    }

    @Override
    public void deletePositionRoleByRoleId(String roleId) {
        QueryWrapper<SysPositionRole> wrapper = new QueryWrapper<>();
        wrapper.eq("Role_Id", roleId);
        baseMapper.delete(wrapper);
    }

    /**
     * 获取用户角色的来源
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse getUserRoleSource(UserPDPDto request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(BaseMsg.notReqParam);
        }
        if (ValidateUtil.isEmpty(request.getUId())) {
            request.setUId(redisUtils.getUid(token));
        }
        Page<SysRoleSourceVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysRoleSourceVo> list = baseMapper.getUserRoleSource(page, request);
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }
}
