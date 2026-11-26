package com.jpmsoft.projectProducer.service.other.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.ContractAuthorityDTO;
import com.jpmsoft.common.utils.DataAuthSqlUtil;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.other.SysUserMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;
import com.jpmsoft.projectProducer.service.contract.ISysContractAuthorityService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class SysUserImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private ISysContractAuthorityService sysContractAuthorityService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private UserProducerFeign userProducerFeign;

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    public boolean existsAccount(String account) {
        return baseMapper.exists(new QueryWrapper<SysUser>().eq("u_account", account));
    }

    @Override
    public void checkTenantRuleByUid(String id, String rule) {
        LogUserInfoVo userInfo = redisUtils.getUserInfoByUid(id);
        checkTenantRule(userInfo, rule);
    }

    @Override
    public void checkTenantRuleByToken(String token, String rule) {
        LogUserInfoVo userInfo = redisUtils.getUserInfoByToken(token);
        checkTenantRule(userInfo, rule);
    }

    @Override
    public String currentDepAuth(String uId, String currentDep, String dbCreateFiled, String stateFile) {
        List<String> departments = apiUtilsService.getAllChildDepartment(uId);
        if (CollectionUtils.isEmpty(departments))
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.noPermission);
        StringBuilder sb = new StringBuilder();
        sb.append(" where mn.").append(currentDep).append(" in (");
        for (int i = 0; i < departments.size(); i++) {
            if (i == 0) sb.append("'").append(departments.get(i)).append("'");
            else sb.append(",'").append(departments.get(i)).append("'");
        }
        sb.append(" ) ");
        String authSql = sb.toString();
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String sql = authShowDisable(dbCreateFiled, stateFile, uId, authSql);
        if (ValidateUtil.isEmpty(sql)) throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.noPermission);
        return sql;
    }

    public void checkTenantRule(LogUserInfoVo userInfo, String rule) {
        if (userInfo.isEnableTenant()) {//开启租户模式则有效
            if (rule.equalsIgnoreCase("date")) {
                if (DateUtil.dayCompare(new Date(), userInfo.getTrDate()))
                    throw new JPMException(ResMsg.tenantExpire);
            }
            if (rule.equalsIgnoreCase("user")) {
                Integer userCount = baseMapper.getTenantUserCount(userInfo.getTeId());
                if (userCount != null && userCount >= userInfo.getTrMaxUser())
                    throw new JPMException(ResMsg.tenantUserMax);
            }
            if (rule.equalsIgnoreCase("project")) {
                Integer projectCount = baseMapper.getTenantProjectCount(userInfo.getTeId());
                if (projectCount != null && projectCount >= userInfo.getTrMaxProject())
                    throw new JPMException(ResMsg.tenantProjectMax);
            }
        }
    }


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
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth) {
        String authSql = getAuthSql(tableName, null, uId, dbCreateFiled, userAuth);
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if (StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return showAllDataAuth(uId, dbCreateFiled,authSql);
    }

    @Override
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile) {
        String authSql = getAuthSql(tableName, uId, dbCreateFiled, userAuth);
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String sql = authShowDisable(dbCreateFiled, stateFile, uId, authSql);
        if (ValidateUtil.isEmpty(sql)) throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.noPermission);
        return sql;
    }

    public String getAuthSqlSelect(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile) {
        String authSql = getAuthSqlUserSelect(tableName, null, uId, dbCreateFiled, userAuth);
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if (StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String sql = authShowDisable(dbCreateFiled, stateFile, uId, authSql);
        if (ValidateUtil.isEmpty(sql)) throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.noPermission);
        return sql;
    }

    @Override
    public String onlyInnerAndPartyB(String tableName, String uId, String dbCreateFiled, String stateFile) {
        SysUser user = sysUserService.getById(uId);
        if (user == null) return null;
        Object userLogInfo = redisUtils.get("userLoginInfo_" + user.getUId());
        if (userLogInfo == null) return null;
        Map<String, Object> info = JacksonUtils.parseObject(userLogInfo.toString(), Map.class);
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        String authSql = DataAuthSqlUtil.onlyInnerAndPartyB(user.getUType(), info, sysContractAuthorityService.getConIdByCidFromContractAuthority(cId, uId));
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if (StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String sql = authShowDisable(dbCreateFiled, stateFile, uId, authSql);
        if (ValidateUtil.isEmpty(sql)) throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.noPermission);
        return sql;
    }

    @Override
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String pKey) {
        return getAuthSql(tableName, uId, dbCreateFiled, userAuth, stateFile, pKey, null);
    }

    @Override
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String pKey, String userType) {
        String authSql;
        if (Objects.equals(OutUserAuthEnum.Approver_Creator.getIndex(), userAuth)) {
            authSql = DataAuthSqlUtil.getAuthSql(uId, dbCreateFiled, pKey, userType);
        } else {
            authSql = getAuthSql(tableName, pKey, uId, dbCreateFiled, userAuth);
        }
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if (StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return authShowDisable(dbCreateFiled, stateFile, uId, authSql);
    }

    @Override
    public String onlyProOrganizationAuth(String tableName, String uId, String dbCreateFiled, String stateFile) {
        String authSql = DataAuthSqlUtil.onlyProOrganizationAuth(tableName, uId) + ShowCancelData(uId, dbCreateFiled, stateFile);
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if (StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return authSql;
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
            if (ValidateUtil.isNotEmpty(depSql)) authSql = depSql;
        }
        return authSql + ShowCancelData(uId, dbCreateFiled, stateFile);
    }

    @Override
    public String getAuthSqlInnerUserDepAndOutOrg(String uId, String tCode, String tableName, String primaryKey, String createUidFiled, String stateFile) {
        LogUserInfoVo user = redisUtils.getUserInfoByUid(uId);
        List<String> departments;
        if (ValidateUtil.isEmpty(user.getDepartments()))
            departments = null;
        else if (user.getDepartments().toString().equals("all"))
            return " where 1=1 ";
        else
            departments = JacksonUtils.parseArray(JacksonUtils.toJSONString(user.getDepartments()), String.class);
        if (departments == null || departments.size() < 1) {
            if (user.getUType().equals("1"))//内部用户无部门
                throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.noPermission);
            else //外部用户按组织机构鉴权
                return getAuthSql(tableName, uId, createUidFiled, OutUserAuthEnum.Organization.getIndex(), stateFile);
        }
        return DataAuthSqlUtil.getAuthSqlOnlyCheckUserDepartment(uId, createUidFiled, tCode, primaryKey, departments, DataAuthSqlUtil.getFiledAlias(createUidFiled)) + ShowCancelData(uId, createUidFiled, stateFile);
    }

    @Override
    public String getAuthSqlInnerUserDepAndOutOrg(String uId, String tCode, String tableName) {
        return getAuthSqlInnerUserDepAndOutOrg(uId, tCode, tableName, "id", "create_uid", "state");
    }

    @Override
    public List<SysUser> getUserInfoByPhone(List<String> phones) {
        if (CollectionUtils.isEmpty(phones)) return null;
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.in("U_MTel", phones);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public String onlyCreateorAndApprover(String tableName, String uId, String dbCreateFiled, Integer userAuth, String primaryKey) {
        String conf = sysConfigService.getConfigByCode("specifyInterfaceOnlyCreateorAndApprover");
        if (ValidateUtil.isNotEmpty(conf) && conf.equals("1")) {
            StringBuilder sb = new StringBuilder();
            sb.append(" where (mn.").append(dbCreateFiled).append("='").append(uId).append("' or ");
            sb.append(" (select count(*) from Sys_Task_Processin st1p where st1p.tp_entityid=mn.").append(primaryKey)
                    .append(" and  st1p.tp_user='").append(uId).append("')>0)");
            return sb.toString();
        } else {
            String authSql = getAuthSql(tableName, uId, dbCreateFiled, userAuth);
            String str = sysConfigService.getConfigByCode("projectDateLimit");
            if (StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")) {
                StringBuffer sb = new StringBuffer();
                sb.append(authSql);
                sb.append("and fpro.P_Create_Time >='" + str + "'");
                authSql = sb.toString();
            }
            return authSql;
        }

    }

    @Override
    public String onlyCreateorAndApprover(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateField, String primaryKey) {
        String conf = sysConfigService.getConfigByCode("specifyInterfaceOnlyCreateorAndApprover");
        if (ValidateUtil.isNotEmpty(conf) && "1".equals(conf)) {
            return getAuthSql(tableName, uId, dbCreateFiled, OutUserAuthEnum.Approver_Creator.getIndex(), stateField, primaryKey);
        } else {
            String authSql = getAuthSql(tableName, uId, dbCreateFiled, userAuth, stateField);
            String str = sysConfigService.getConfigByCode("projectDateLimit");
            if (StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")) {
                StringBuffer sb = new StringBuffer();
                sb.append(authSql);
                sb.append("and fpro.P_Create_Time >='" + str + "'");
                authSql = sb.toString();
            }
            return authSql;
        }
    }

    @Override
    public String onlyCreateorAndApproverSQL(String uId, String dbCreateFiled, String primaryKey, String stateField) {
        String conf = sysConfigService.getConfigByCode("specifyInterfaceOnlyCreateorAndApprover");
        if (ValidateUtil.isNotEmpty(conf) && "1".equals(conf)) {
            return getAuthSql(null, uId, dbCreateFiled, OutUserAuthEnum.Approver_Creator.getIndex(), stateField, primaryKey);
        }
        return " where 1=1 ";
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

    /**
     * 仅判断是否显示作废与未发起数据
     *
     * @param uId
     * @param dbCreateFiled
     * @param stateFile
     * @return
     */
    @Override
    public String onlyShowCancel(String uId, String dbCreateFiled, String stateFile) {
        return " where 1=1 " + ShowCancelData(uId, dbCreateFiled, stateFile);
    }

    private String getAuthSqlUserSelect(String tableName, String pKey, String uId, String dbCreateFiled, Integer userAuth) {
        SysUser user = sysUserService.getById(uId);
        log.info("ID查询到的用户信息:" + user == null ? null : JacksonUtils.toJSONString(user));
        if (user == null) return null;
        Object userLogInfo = userProducerFeign.getLogUserInfoById(uId);
        log.info("redis内的用户信息:" + userLogInfo);
        if (userLogInfo == null) return null;
        Map<String, Object> info = JacksonUtils.parseObject(userLogInfo.toString(), Map.class);
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        ContractAuthorityDTO auth = sysContractAuthorityService.getConIdByCidFromContractAuthority(cId, uId);
        return DataAuthSqlUtil.getAuthSql(tableName, uId, dbCreateFiled, userAuth, user.getUType(), info, auth.getDisables(), auth.getEnables(), pKey);
    }

    private String getAuthSql(String tableName, String pKey, String uId, String dbCreateFiled, Integer userAuth) {
        SysUser user = sysUserService.getById(uId);
        log.info("ID查询到的用户信息:" + user == null ? null : JacksonUtils.toJSONString(user));
        if (user == null) return null;
        Object userLogInfo = redisUtils.get("userLoginInfo_" + user.getUId());
        log.info("redis内的用户信息:" + userLogInfo);
        if (userLogInfo == null) return null;
        Map<String, Object> info = JacksonUtils.parseObject(userLogInfo.toString(), Map.class);
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        ContractAuthorityDTO auth = sysContractAuthorityService.getConIdByCidFromContractAuthority(cId, uId);
        return DataAuthSqlUtil.getAuthSql(tableName, uId, dbCreateFiled, userAuth, user.getUType(), info, auth.getDisables(), auth.getEnables(), pKey);
    }

    private String authShowDisable(String dbCreateFiled, String stateFile, String uId, String authSql) {
        if (!ValidateUtil.isAnObjEmpty(dbCreateFiled, stateFile)) {
            //后台基础配置 是否显示作废以及暂存的数据
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

    @Override
    public List<SysUserVo> getComPanyUser(List<String> dName, List<String> pName) {
        List<SysUserVo> data = baseMapper.getComPanyUser(dName, pName);
        return data;
    }

/*    @Override
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        String authSql = DataAuthSqlUtil.onlyInnerAndPartyB(user.getUType(), info, sysContractAuthorityService.getConIdByCidFromContractAuthority(cId, uId));
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if (StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String sql = authShowDisable(dbCreateFiled, stateFile, uId, authSql);
        if (ValidateUtil.isEmpty(sql)) throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.noPermission);
        return sql;
    }*/

    @Override
    public String getMonthAuthSql(String tableName, String uId, String icmCreateUid, List<String> outhList, String transferStart) {
        SysUser user = sysUserService.getById(uId);
        if (user == null) return null;
        Object userLogInfo = redisUtils.get("userLoginInfo_" + user.getUId());
        if (userLogInfo == null) return null;
        Map<String, Object> info = JacksonUtils.parseObject(userLogInfo.toString(), Map.class);
        return null;
    }

    /**
     * 上海对外经贸的质量检查使用
     *
     * @param uId
     * @return
     */
    @Override
    public boolean checkShowAllDataAuthSH(String uId) {
        if (ValidateUtil.isEmpty(uId)) {
            return false;
        }
        try {
            String showCancelDataStr = sysConfigService.getConfigByCode("showCancelDataAuthSH");
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
}
