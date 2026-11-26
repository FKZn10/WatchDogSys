package com.jpmsoft.custom.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.ContractAuthorityDTO;
import com.jpmsoft.common.utils.DataAuthSqlUtil;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.custom.constants.CustomMsg;
import com.jpmsoft.custom.mapper.SysUserMapper;
import com.jpmsoft.custom.pojo.entity.SysUser;
import com.jpmsoft.custom.service.IApiUtilsService;
import com.jpmsoft.custom.service.ISysConfigService;
import com.jpmsoft.custom.service.ISysContractAuthorityService;
import com.jpmsoft.custom.service.ISysUserService;
import com.jpmsoft.custom.utils.RedisUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysUserImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private ISysContractAuthorityService sysContractAuthorityService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 外部用户
     * constructionControl  true 是监理单位或全过程咨询单位
     * constructionUnit true 是其他类型单位用户
     * 内部用户
     * departments 可查看数据所属组织机构列表
     * departments  null 内部用户仅看组织机构
     * departments 空集合（非null） 则具有所有权限
     * <p>
     * param cId                 单位ID
     * param constructionControl true 是监理单位或全过程咨询单位
     * param constructionUnit    true 是其他类型单位用户
     * param departments         可查看数据所属组织机构列表
     *
     * @param uId           用户ID
     * @param dbCreateFiled 当前操作主表上的创建人数据库字段
     * @param userAuth      枚举类 OutUserAuthEnum
     * @return
     */
    @Override
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String conAlise) {
        return getAuthSql(tableName, null, uId, dbCreateFiled, userAuth, conAlise);
    }

    @Override
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String conAlise) {
        String authSql = getAuthSql(tableName, uId, dbCreateFiled, userAuth, conAlise);
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String sql = authShowDisable(dbCreateFiled, stateFile, uId, authSql);
        if (ValidateUtil.isEmpty(sql))
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, CustomMsg.noPermission);
        return sql;
    }

    @Override
    public String onlyInnerAndPartyB(String tableName, String uId, String dbCreateFiled, String stateFile) {
        SysUser user = sysUserService.getById(uId);
        if (user == null) return null;
        Object userLogInfo = redisUtils.get("userLoginInfo_" + user.getUId());
        if (userLogInfo == null)
            return null;
        Map<String, Object> info = JacksonUtils.parseObject(userLogInfo.toString(), Map.class);
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        String authSql = DataAuthSqlUtil.onlyInnerAndPartyB(user.getUType(), info, sysContractAuthorityService.getConIdByCidFromContractAuthority(cId, uId));
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String sql = authShowDisable(dbCreateFiled, stateFile, uId, authSql);
        if (ValidateUtil.isEmpty(sql))
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, CustomMsg.noPermission);
        return sql;
    }

    @Override
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String pKey, String conAlise) {
        String authSql = getAuthSql(tableName, pKey, uId, dbCreateFiled, userAuth, conAlise);
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        return authShowDisable(dbCreateFiled, stateFile, uId, authSql);
    }

    @Override
    public String onlyProOrganizationAuth(String tableName, String uId, String dbCreateFiled, String stateFile) {
        return DataAuthSqlUtil.onlyProOrganizationAuth(tableName, uId) + ShowCancelData(uId, dbCreateFiled, stateFile);
    }

    @Override
    public String onlyProOrganizationAuth(String tableName, String uId) {
        return DataAuthSqlUtil.onlyProOrganizationAuth(tableName, uId);
    }

    @Override
    public Boolean checkIsOutUser(String uId) {
        SysUser sysUserPojo = sysUserService.getById(uId);
        return DataAuthSqlUtil.checkIsOutUser(sysUserPojo.getUType());
    }

    @Override
    public String getAuthSqlOnlyCheckUserDepartment(String uId, String createUidFiled, String tCode, String primaryKey) {
        List<String> departments = apiUtilsService.getAllChildDepartment(uId);
        return DataAuthSqlUtil.getAuthSqlOnlyCheckUserDepartment(uId, createUidFiled, tCode, primaryKey, departments);
    }

    @Override
    public String getAuthSqlOnlyCheckUserDepartment(String uId, String dbCreateFiled, String tCode, String primaryKey, String stateFile) {
        List<String> departments = apiUtilsService.getAllChildDepartment(uId);
        String authSql = "";
        if (departments != null && departments.size() == 0) {
            //空集合表示所有机构权限
            authSql = " where 1=1 ";
        } else {
            String depSql = DataAuthSqlUtil.getAuthSqlOnlyCheckUserDepartment(uId, dbCreateFiled, tCode, primaryKey, departments);
            if (ValidateUtil.isNotEmpty(depSql))
                authSql = depSql;
        }
        return authSql + ShowCancelData(uId, dbCreateFiled, stateFile);
    }

    @Override
    public List<SysUser> getUserInfoByPhone(List<String> phones) {
        if (CollectionUtils.isEmpty(phones)) return null;
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.in("U_MTel", phones);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public String ShowCancelData(String uId, String dbCreateFiled, String stateFile) {
        if (!ValidateUtil.isAnObjEmpty(dbCreateFiled, stateFile)) {
            try {
                Object showCancelData = redisUtils.get("Config_showCancelData");
                if (!Objects.isNull(showCancelData)) {
                    Integer config = Integer.parseInt(showCancelData.toString());
                    String alias = DataAuthSqlUtil.getDbCreateFiledAlias(dbCreateFiled);
                    if (config == 0 || config == 2 || config == 3) {
                        if (config == 3 && "P_Create_User_ID".equalsIgnoreCase(dbCreateFiled)) {
                            return " and (" + DataAuthSqlUtil.dbCreateFiledAlias(dbCreateFiled) + "='" + uId + "' or (" + alias + "." + stateFile + "!=3 and ( " + alias + "." + stateFile + " !=0 or fpro.ppr_id is not null))) ";
                        } else if (config == 0 || config == 3) {
                            //排除非当前操作人创建的未发起和作废的数据
                            return " and (" + DataAuthSqlUtil.dbCreateFiledAlias(dbCreateFiled) + "='" + uId + "' or (" + alias + "." + stateFile + "!=3 and " + alias + "." + stateFile + "!=0)) ";
                        } else {
                            return " and (" + alias + "." + stateFile + "!=3 and (" + DataAuthSqlUtil.dbCreateFiledAlias(dbCreateFiled) + "='" + uId + "' or " + alias + "." + stateFile + "!=0)) ";
                        }
                    } else if (config == 4) //作废所有人不可见但未发起所有人可见
                    {
                        return " and " + alias + "." + stateFile + "!=3 ";
                    }
                }
            } catch (Exception ex) {
                log.error("排除当前操作人创建的未发起和作废的数据SQL处理失败！", ex);
                ex.printStackTrace();
            }
        }
        return "";
    }

    private String getAuthSql(String tableName, String pKey, String uId, String dbCreateFiled, Integer userAuth, String conAlise) {
        SysUser user = sysUserService.getById(uId);
        if (user == null) return null;
        Object userLogInfo = redisUtils.get("userLoginInfo_" + user.getUId());
        if (userLogInfo == null)
            return null;
        Map<String, Object> info = JacksonUtils.parseObject(userLogInfo.toString(), Map.class);
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        ContractAuthorityDTO auth = sysContractAuthorityService.getConIdByCidFromContractAuthority(cId, uId);
        return DataAuthSqlUtil.getAuthSql(tableName, uId, dbCreateFiled, userAuth, user.getUType(), info, auth.getDisables(), auth.getEnables(), pKey, conAlise);
    }

    private String authShowDisable(String dbCreateFiled, String stateFile, String uId, String authSql) {
        //后台基础配置 是否显示作废以及暂存的数据
        if (!ValidateUtil.isAnObjEmpty(dbCreateFiled, stateFile)) {
            String show = ShowCancelData(uId, dbCreateFiled, stateFile);
            // 后台配置授权给特定用户或角色
            return showAllDataAuth(uId, dbCreateFiled, authSql + show);
        } else if (ValidateUtil.isNotEmpty(dbCreateFiled)){
            return showAllDataAuth(uId, dbCreateFiled, authSql);
        } else {
            return authSql;
        }
    }

    /**
     * 针对特定用户或角色的授权配置
     * 因为鉴权sql中left join的部分在部分sql中会有引用，且部分sql在使用时会有直接替换sql的情况，所有鉴权sql的部分会保留，只是添加一个1=1 or的条件，来跳过鉴权逻辑
     *
     * @param uId
     * @param dbCreateFiled
     * @param authSql
     * @return
     */
    @Override
    public String showAllDataAuth(String uId, String dbCreateFiled, String authSql) {
        if (ValidateUtil.isAnObjEmpty(dbCreateFiled, authSql)) {
            return authSql;
        }
        if (!this.checkShowAllDataAuth(uId)) {
            return authSql;
        }
        if (authSql.matches("(?is).*\\bwhere\\b.*")) {
            return authSql.replaceFirst("(?i)\\bwhere\\b", "where ( 1=1 or ") + ")";
        }
        return authSql + " where 1=1 ";
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
                showCancelDataStr = sysConfigService.getConfigByCode("showCancelDataAuth");
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
