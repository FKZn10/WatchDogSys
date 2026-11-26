package com.jpmsoft.jpbpm.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysUserMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysCompany;
import com.jpmsoft.jpbpm.pojo.entity.SysUser;
import com.jpmsoft.jpbpm.pojo.vo.UserInfoVo;
import com.jpmsoft.jpbpm.service.ISysCompanyService;
import com.jpmsoft.jpbpm.service.ISysConfigService;
import com.jpmsoft.jpbpm.service.SysUserService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysUserImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ISysCompanyService sysCompanyService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public List<SysUser> getUserAllByUids(List<String> uIds, String customerId) {
        if (uIds != null && uIds.size() > 0) {
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.in("U_Id", uIds);
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            wrapper.orderByAsc("U_Name");
            return baseMapper.selectList(wrapper);
        } else {
            List<SysUser> List = new ArrayList<>();
            return List;
        }
    }

    @Override
    public List<SysUser> getAllUser(String customerId) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        // wrapper.ne()
        return baseMapper.selectList(wrapper);
    }


    @Override
    public List<Map<String, Object>> getSomeFieldsUser(String uId) {
        return baseMapper.getSomeFieldsUser(uId);
    }

    @Override
    public List<String> getAllChildDepartment(String uId, String customerId, String token) {
        if (ValidateUtil.isNotEmpty(uId)) return new ArrayList<>();
        String url = "http://user-producer/v1/OrganizationManage/getAllChildDepartment?uId=" + uId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(headers);
        List<String> data = restTemplate.getForObject(url, List.class, formEntity);
        return data;
    }


    @Override
    public List<UserInfoVo> getPersonnelSettings(List<String> uIds) {
        List<UserInfoVo> personnelSettings = new ArrayList<>();
        //获取当前用户信息
        //去除uIds中的空格
        uIds=uIds.stream().filter(uId -> ValidateUtil.isNotEmpty(uId)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(uIds)){
            return personnelSettings;
        }
        List<SysUser> uList = baseMapper.selectBatchIds(uIds);
        List<Map> dInfos = baseMapper.getDInfo(uIds);
        List<Map> dPInfos = baseMapper.getPdInfo(uIds);
        if (!CollectionUtils.isEmpty(uList)) {
            List<String> cIds = uList.stream().map(n -> n.getcId()).distinct().collect(Collectors.toList());
            List<SysCompany> comList = sysCompanyService.listByIds(cIds);

            for (SysUser uObJ : uList) {
                UserInfoVo userInfo = new UserInfoVo();
                userInfo.setuAccount(uList.get(0).getuAccount());
                userInfo.setuCode(uList.get(0).getuCode());
                userInfo.setuName(uList.get(0).getuName());
                userInfo.setuId(uList.get(0).getuId());
                userInfo.setuType(uList.get(0).getuType());
                userInfo.setuSex(uList.get(0).getuSex());


                if (!CollectionUtils.isEmpty(comList)) {
                    List<SysCompany> sClist = comList.stream().filter(b -> b.getCId().equals(uObJ.getcId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(sClist)) {
                        userInfo.setcAbbreviation(sClist.get(0).getCAbbreviation());
                        userInfo.setcName(sClist.get(0).getCName());
                        userInfo.setcId(sClist.get(0).getCId());
                        userInfo.setcType(sClist.get(0).getCType());
                        userInfo.setcCreditCode(sClist.get(0).getCCreditcode());
                        userInfo.setcMDMNO(sClist.get(0).getCMdmNo());
                        userInfo.setcSolution(sClist.get(0).getCSolution());
                        userInfo.setcGroupCode(sClist.get(0).getCGroupCode());
                    }
                }

                if (!CollectionUtils.isEmpty(dInfos)) {
                    List<Map> dInfo = dInfos.stream().filter(n -> uObJ.getuId().equals(getKey(n, "U_Id"))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dInfo)) {
                        List<String> pCodes = new ArrayList<>();
                        List<String> PNames = new ArrayList<>();
                        List<String> PMDMNOs = new ArrayList<>();
                        List<String> DParentIds = new ArrayList<>();
                        List<String> DCodes = new ArrayList<>();
                        List<String> DNames = new ArrayList<>();
                        List<String> DIds = new ArrayList<>();
                        List<String> DSolutions = new ArrayList<>();
                        List<String> DCompanyIds = new ArrayList<>();
                        List<String> RoleCodes = new ArrayList<>();
                        List<String> RoleNames = new ArrayList<>();
                        List<String> RoleTypes = new ArrayList<>();
                        List<String> Rolesolutions = new ArrayList<>();
                        for (Map obj : dInfo) {
                            pCodes.add(getKey(obj, "P_Code") + "");
                            PNames.add(getKey(obj, "P_Name") + "");
                            PMDMNOs.add(getKey(obj, "P_MDM_NO") + "");
                            DParentIds.add(getKey(obj, "D_ParentId") + "");
                            DCodes.add(getKey(obj, "D_Code") + "");
                            DNames.add(getKey(obj, "D_Name") + "");
                            DIds.add(getKey(obj, "D_Id") + "");
                            DSolutions.add(getKey(obj, "D_Solution") + "");
                            DCompanyIds.add(getKey(obj, "D_Company_Id") + "");
                            RoleCodes.add(getKey(obj, "Role_Code") + "");
                            RoleNames.add(getKey(obj, "Role_Name") + "");
                            RoleTypes.add(getKey(obj, "Role_Type") + "");
                            Rolesolutions.add(getKey(obj, "Role_solution") + "");
                        }
                        userInfo.setPoPcode(String.join(",", pCodes));
                        userInfo.setPoPname(String.join(",", PNames));
                        userInfo.setPoPmdmno(String.join(",", PMDMNOs));
                        userInfo.setdDparentId(String.join(",", DParentIds));
                        userInfo.setdDcode(String.join(",", DCodes));
                        userInfo.setdDname(String.join(",", DNames));
                        userInfo.setdDid(String.join(",", DIds));
                        userInfo.setdDsolution(String.join(",", DSolutions));
                        userInfo.setdDcId(String.join(",", DCompanyIds));
                        userInfo.setRdRcode(String.join(",", RoleCodes));
                        userInfo.setRdRname(String.join(",", RoleNames));
                        userInfo.setRdRtype(String.join(",", RoleTypes));
                        userInfo.setRdRsolution(String.join(",", Rolesolutions));
                    }
                }
                if (CollectionUtils.isEmpty(dPInfos)) {
                    List<Map> dPInfo = dPInfos.stream().filter(n -> uObJ.getuId().equals(getKey(n, "U_Id"))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dPInfo)) {
                        List<String> RpRcodes = new ArrayList<>();
                        List<String> RpRnames = new ArrayList<>();
                        List<String> RpRtypes = new ArrayList<>();
                        List<String> RpRsolutions = new ArrayList<>();
                        List<String> DpPDCodes = new ArrayList<>();
                        List<String> DpPDNames = new ArrayList<>();
                        List<String> DpPDMDMNOs = new ArrayList<>();
                        List<String> DpPDTypes = new ArrayList<>();
                        List<String> DpPDIDs = new ArrayList<>();
                        List<String> DpPDSolutions = new ArrayList<>();
                        List<String> DpPDParentIDs = new ArrayList<>();
                        List<String> PopPPcodes = new ArrayList<>();
                        List<String> PopPPnames = new ArrayList<>();
                        List<String> PopPPmdmnos = new ArrayList<>();
                        for (Map obj : dPInfo) {
                            RpRcodes.add(getKey(obj, "Role_Code") + "");
                            RpRnames.add(getKey(obj, "Role_Name") + "");
                            RpRtypes.add(getKey(obj, "Role_Type") + "");
                            RpRsolutions.add(getKey(obj, "Role_solution") + "");
                            DpPDCodes.add(getKey(obj, "PD_Code") + "");
                            DpPDNames.add(getKey(obj, "PD_Name") + "");
                            DpPDMDMNOs.add(getKey(obj, "PD_MDM_NO") + "");
                            DpPDTypes.add(getKey(obj, "PD_Type") + "");
                            DpPDIDs.add(getKey(obj, "P_ID") + "");
                            DpPDSolutions.add(getKey(obj, "D_Solution") + "");
                            DpPDParentIDs.add(getKey(obj, "PD_ParentId") + "");
                            PopPPcodes.add(getKey(obj, "PP_Code") + "");
                            PopPPnames.add(getKey(obj, "PP_Name") + "");
                            PopPPmdmnos.add(getKey(obj, "PP_MDM_NO") + "");
                        }
                        userInfo.setRpRcode(String.join(",", RpRcodes));
                        userInfo.setRpRname(String.join(",", RpRnames));
                        userInfo.setRpRtype(String.join(",", RpRtypes));
                        userInfo.setRpRsolution(String.join(",", RpRsolutions));
                        userInfo.setDpPDCode(String.join(",", DpPDCodes));
                        userInfo.setDpPDName(String.join(",", DpPDNames));
                        userInfo.setDpPDMDMNO(String.join(",", DpPDMDMNOs));
                        userInfo.setDpPDType(String.join(",", DpPDTypes));
                        userInfo.setDpPDID(String.join(",", DpPDIDs));
                        userInfo.setDpPDSolution(String.join(",", DpPDSolutions));
                        userInfo.setDpPDParentID(String.join(",", DpPDParentIDs));
                        userInfo.setPopPPcode(String.join(",", PopPPcodes));
                        userInfo.setPopPPname(String.join(",", PopPPnames));
                        userInfo.setPopPPmdmno(String.join(",", PopPPmdmnos));
                    }
                }

                personnelSettings.add(userInfo);
            }
        }

        return personnelSettings;

    }


    @Override
    public   List<Map> getUidByUidInfo(Map<String, Object> params) {
        return baseMapper.getUidByUidInfo(params);
    }




    public Object getKey(Map<String, Object> obj, String key) {
        if (obj.get(key) != null) {
            return obj.get(key);
        } else if (obj.get(key.toLowerCase(Locale.ENGLISH)) != null) {
            String keyStr = key.toLowerCase(Locale.ENGLISH);
            return obj.get(keyStr);
        } else if (obj.get(key.toUpperCase(Locale.ENGLISH)) != null) {
            String keyStr = key.toUpperCase(Locale.ENGLISH);
            return obj.get(keyStr);
        } else {
            return "";
        }
    }

    /**
     * 检查当前用户是否具有显示所有数据的权限
     *
     * @param uId 当前用户ID
     * @return true表示具有权限，false表示不具有权限
     */
    @Override
    public boolean checkShowAllDataAuth(String uId) {
        if (ValidateUtil.isEmpty(uId)) {
            return false;
        }
        try {
            Object showCancelData = redisUtils.get("Config_showCancelDataAuth");
            String showCancelDataStr = showCancelData == null ? "" : showCancelData.toString();
            if (ValidateUtil.isEmpty(showCancelDataStr)) {
                showCancelDataStr = sysConfigService.getSysConfigByCode("showCancelDataAuth");
                if (ValidateUtil.isNotEmpty(showCancelDataStr)) {
                    redisUtils.set("Config_showCancelDataAuth", showCancelDataStr);
                } else {
                    // 避免频繁查询数据库，设置一个空值；后续如果有配置项了会自动删除该配置
                    redisUtils.set("Config_showCancelDataAuth", "{\"roles\":\"\",\"userIds\":\"\"}");
                }
            }
            if (ValidateUtil.isEmpty(showCancelDataStr)) {
                return false;
            }
            // 解析json
            Map<String, String> map = JSONUtil.toBean(showCancelDataStr, Map.class);
            if (map == null || map.isEmpty()) {
                return false;
            }
            Set<String> roleSet = toTrimmedSet(map.get("roles"));
            Set<String> userIdSet = toTrimmedSet(map.get("userIds"));
            // 先判断当前用户是否在特定用户列表中
            if (userIdSet.contains(uId)) {
                return true;
            }
            // 如果特定角色列表为空，则直接返回false
            if (roleSet.isEmpty()) {
                return false;
            }
            // 获取到uid对应的角色名称列表
            Set<String> uRoles = baseMapper.getUserRoleNames(uId);
            if (uRoles == null || uRoles.isEmpty()) {
                return false;
            }
            for (String role : uRoles) {
                if (ValidateUtil.isNotEmpty(role) && roleSet.contains(role.trim())) {
                    return true;
                }
            }
        } catch (Exception ex) {
            log.error("针对特定用户或角色显示所有数据授权处理失败！", ex);
        }
        return false;
    }

    /**
     * 将逗号分隔的字符串转换为一个修剪后的字符串集合
     *
     * @param csvObj
     * @return
     */
    private Set<String> toTrimmedSet(Object csvObj) {
        if (csvObj == null) {
            return Collections.emptySet();
        }
        String csv = csvObj.toString();
        if (ValidateUtil.isEmpty(csv)) {
            return Collections.emptySet();
        }
        Set<String> result = new HashSet<>();
        String[] arr = csv.split(",");
        for (String item : arr) {
            if (ValidateUtil.isNotEmpty(item)) {
                String v = item.trim();
                if (ValidateUtil.isNotEmpty(v)) {
                    result.add(v);
                }
            }
        }
        return result;
    }
}
