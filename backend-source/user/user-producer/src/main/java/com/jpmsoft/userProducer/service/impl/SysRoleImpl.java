package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.SignAndVerifyDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysRoleMapper;
import com.jpmsoft.userProducer.pojo.dto.RoleDto;
import com.jpmsoft.userProducer.pojo.entity.SysRole;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysRoleImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private ISysRolePowerService sysRolePowerService;

    @Resource
    private ISysPositionRoleService sysPositionRoleService;

    @Resource
    private ISysPositionRolePService sysPositionRolePService;

    @Resource
    private ISysPositionPRoleService sysPositionPRoleService;

    @Resource
    private ISysTPositionRoleService sysTPositionRoleService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISysSequenceSetService sysSequenceSetService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private ISysFlowUserService sysFlowUserService;
    @Resource
    private IApiUtilsService apiUtilsService;


    @Override
    public BaseResponse GetRoleList(RoleDto request) {
        QueryMap<RoleDto> query = new QueryMap<>(request);
        query.put("roleType").put("roleDataauthority").put("roleCode").put("roleName").put("roleCpy");
        IPage<RoleDto> page = new Page<>(request.getPage(), request.getSize());
        List<RoleDto> datas = baseMapper.getRoleList(page, query.getQuery());
        if (!CollectionUtils.isEmpty(datas)) {
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            if (ied.getRoleListVerify()) {
                datas.forEach(d -> {
                    if (!roleSignVerify(ied, d.signContent(), d.getSign()))
                        throw new JPMException(BaseMsg.dataVerifyError);
                    d.setSign(null);
                });
            }
        }
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public List<SysRole> GetRoleList(String teId) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateRole(RoleDto request) {
        SysRole pojo;
        String codeRule = null;
        if (ValidateUtil.isEmpty(request.getRoleId())) {
            pojo = new SysRole();
            if (sysSequenceSetService.sequenceRestEnable("SysRole", request.getTeId()) == true) {
                request.setRoleCode(sysSequenceSetService.generateCodeByTCode("SysRole", request, request.getTeId()));
                if (ValidateUtil.isEmpty(request.getRoleCode()))
                    return BaseResponse.fail(BaseMsg.codeGenerateFail);
                codeRule = "SysRole";
            }
        } else {
            pojo = baseMapper.selectById(request.getRoleId());
            if (pojo == null)
                return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("1", 1);
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        wrapper.and(w -> w.eq("Role_Name", request.getRoleName()).or().
                eq("Role_Code", request.getRoleCode()));
        if (!ValidateUtil.isEmpty(request.getRoleId()))
            wrapper.ne("Role_ID", request.getRoleId());
        Integer num = baseMapper.selectCount(wrapper).intValue();
        if (num > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);
        if (ValidateUtil.isNotEmpty(request.getRoleCode())) pojo.setRoleCode(request.getRoleCode());
        if (ValidateUtil.isNotEmpty(request.getRoleSolution())) pojo.setRoleSolution(request.getRoleSolution());
        if (ValidateUtil.isNotEmpty(request.getRoleRemark())) pojo.setRoleRemark(request.getRoleRemark());
        if (ValidateUtil.isNotEmpty(request.getRoleName())) pojo.setRoleName(request.getRoleName());
        if (ValidateUtil.isNotEmpty(request.getRoleDataauthority()))
            pojo.setRoleDataauthority(request.getRoleDataauthority());
        if (ValidateUtil.isNotEmpty(request.getRoleCpy())) pojo.setRoleCpy(request.getRoleCpy());
        if (ValidateUtil.isNotEmpty(request.getRoleType())) pojo.setRoleType(request.getRoleType());
        pojo.setRoleSort(request.getRoleSort());
        pojo.setTeId(request.getTeId());
        String ids = request.getPowerIds();

        String powerTrue = redisUtils.tenantKey("powerAllStatusTrue", request.getTeId());
        String routeKey = redisUtils.tenantKey("VuePowerRoute", request.getTeId());
        if (ValidateUtil.isEmpty(request.getRoleId())) {
            pojo.setRoleCreatetime(new Date());
            pojo.setRoleId(GenerateUUID.getUUID());
            pojo.setRoleCreateuName(request.getRoleCreateuName());
            pojo.setRoleCreateuId(request.getRoleCreateuId());
            baseMapper.insert(roleSign(pojo));
            sysRolePowerService.CreateRolePower(pojo.getRoleId(), ids, request.getTeId());
            redisUtils.delete(powerTrue, routeKey);
        } else {
            sysRolePowerService.CreateRolePower(request.getRoleId(), ids, request.getTeId());
            baseMapper.updateById(roleSign(pojo));
            redisUtils.delete(powerTrue, routeKey);
        }
        if (ValidateUtil.isNotEmpty(codeRule))
            sysSequenceSetService.refershAutoNo(codeRule, request, request.getTeId());
        return BaseResponse.ok();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse DeleteRole(RoleDto request) {
        SysRole sysRole = baseMapper.selectById(request.getRoleId());
        if (sysRole == null)
            return new BaseResponse(HttpStatus.OK.value(), false, BaseMsg.notReqParam);
        if (sysFlowUserService.roleExist(request.getRoleId())) return BaseResponse.fail(BaseMsg.bpmNotDel);
        sysPositionRolePService.deletePositionRolePByRoleId(request.getRoleId());
        sysPositionRoleService.deletePositionRoleByRoleId(request.getRoleId());
        sysRolePowerService.deleteRolePower(request.getRoleId());
        sysPositionPRoleService.deletePositionPRoleByRoleId(request.getRoleId());
        //模板角色
        sysTPositionRoleService.deleteTPositionRoleByRole(request.getRoleId());
        baseMapper.deleteById(request.getRoleId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse GetRoleCheckBox(String powerId, String cId, String token) {
        List<RoleDto> models;
        Map<String, Object> params = new HashMap<>();
        params.put("teId", redisUtils.getTeId(token));
        if (ValidateUtil.isNotEmpty(cId))
            params.put("roleCpy", cId);
        models = baseMapper.getRoleList(null, params);
        List<Map<String, Object>> data = new ArrayList<>();
        List<SysRole> roles = null;
        if (ValidateUtil.isNotEmpty(powerId)) roles = getRoleByPower(powerId);
        for (RoleDto model : models) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", model.getRoleId());
            map.put("label", model.getRoleName());
            if (!CollectionUtils.isEmpty(roles)) {
                List<SysRole> currentNode = roles.stream().filter(n -> n.getRoleId().equals(model.getRoleId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(currentNode))
                    map.put("checked", true);
                else
                    map.put("checked", false);
            } else if (ValidateUtil.isNotEmpty(powerId))
                map.put("checked", false);
            data.add(map);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public List<SysRole> getRoleByPower(String poweId) {
        return baseMapper.getRoleByPower(poweId);
    }


    /**
     * 用户角色数据完整性签名
     *
     * @param role
     * @return
     */
    private SysRole roleSign(SysRole role) {
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        if (ied.getRoleSign()) {
            if (ValidateUtil.isNotEmpty(ied.getSignUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(role.signContent());
                BaseResponse res = apiUtilsService.sendPost(ied.getSignUrl(), sg, null);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    role.setSign(res.getData().toString());
                }
            }
        }
        return role;
    }

    /**
     * 用户角色验签 验证完整性
     *
     * @param content
     * @param sign
     * @return
     */
    private Boolean roleSignVerify(InterfaceEncDecDTO ied, String content, String sign) {
        if (ied.getRoleListVerify()) {
            if (ValidateUtil.isNotEmpty(ied.getVerifyUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(content);
                sg.setSign(sign);
                BaseResponse res = apiUtilsService.sendPost(ied.getVerifyUrl(), sg, null);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    return Boolean.parseBoolean(res.getData().toString());
                }
            }
        } else return true;
        return false;
    }
}
