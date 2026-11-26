package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.constants.ResMsg;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.ContractAuthorityDTO;
import com.jpmsoft.common.utils.DataAuthSqlUtil;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.mapper.ISysUserMapper;
import com.jpmsoft.gmgws.pojo.entity.SysUser;
import com.jpmsoft.gmgws.service.IApiUtilsService;
import com.jpmsoft.gmgws.service.ISysConfigService;
import com.jpmsoft.gmgws.service.ISysContractAuthorityService;
import com.jpmsoft.gmgws.service.ISysUserService;
import com.jpmsoft.gmgws.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ISysUserServiceImpl extends ServiceImpl<ISysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IApiUtilsService apiUtilsService;

    @Resource
    private ISysContractAuthorityService sysContractAuthorityService;
    @Resource
    private ISysUserService sysUserService;
    @Override
    public List<SysUser> getUser(List<String> collect) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysUser::getuName,collect);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SysUser getUserCreator(String uid) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getuId,uid);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public String onlyCreateorAndApprover(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateField, String primaryKey) {
        String conf = sysConfigService.getConfigByCode("specifyInterfaceOnlyCreateorAndApprover");
        if (ValidateUtil.isNotEmpty(conf) && conf.equals("1")) {
            StringBuilder sb = new StringBuilder();
            sb.append(" where (mn.").append(dbCreateFiled).append("='").append(uId).append("' or ");
            sb.append(" (select count(*) from Sys_Task_Processin st1p where st1p.tp_entityid=mn.").append(primaryKey)
                    .append(" and st1p.tp_user='").append(uId).append("')>0)");
            return sb.toString();
        } else {
            String authSql=getAuthSql(tableName, uId, dbCreateFiled, userAuth, stateField);
            String str = sysConfigService.getConfigByCode("projectDateLimit");
            if(StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")){
                StringBuffer sb = new StringBuffer();
                sb.append(authSql);
                sb.append("and fpro.P_Create_Time >='"+str+"'");
                authSql=sb.toString();
            }
            return authSql;
        }
    }

    @Override
    public String onlyInnerAndPartyB(String tableName, String uId, String dbCreateFiled, String stateFile) {
        SysUser user = sysUserService.getById(uId);
        if (user == null) return null;
        Object userLogInfo = redisUtils.get("userLoginInfo_" + user.getuId());
        if (userLogInfo == null) return null;
        Map<String, Object> info = JacksonUtils.parseObject(userLogInfo.toString(), Map.class);
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        String authSql = DataAuthSqlUtil.onlyInnerAndPartyB(user.getuType(), info, sysContractAuthorityService.getConIdByCidFromContractAuthority(cId,uId));
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if(StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")){
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='"+str+"'");
            authSql=sb.toString();
        }
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String sql = authShowDisable(dbCreateFiled, stateFile, uId, authSql);
        if (ValidateUtil.isEmpty(sql)) throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.noPermission);
        return sql;
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



    private String authShowDisable(String dbCreateFiled, String stateFile, String uId, String authSql) {
        //后台基础配置 是否显示作废以及暂存的数据
        if (!ValidateUtil.isAnObjEmpty(dbCreateFiled, stateFile)) {
            String show = ShowCancelData(uId, dbCreateFiled, stateFile);
            return authSql + show;
        } else return authSql;
    }

    @Override
    public String ShowCancelData(String uId, String dbCreateFiled, String stateFile) {
        if (!ValidateUtil.isAnObjEmpty(dbCreateFiled, stateFile)) {
            try {
                Object showCancelData = redisUtils.get("Config_showCancelData");
                if (!Objects.isNull(showCancelData)) {
                    Integer config = Integer.parseInt(showCancelData.toString());
                    if (config == 0 || config == 2) {
                        String alias = DataAuthSqlUtil.getDbCreateFiledAlias(dbCreateFiled);
                        //排除非当前操作人创建的未发起和作废的数据
                        if (config == 0)
                            return " and (" + DataAuthSqlUtil.dbCreateFiledAlias(dbCreateFiled) + "='" + uId + "' or (" + alias + "." + stateFile + "!=3 and " + alias + "." + stateFile + "!=0)) ";
                        else
                            return " and (" + alias + "." + stateFile + "!=3 and (" + DataAuthSqlUtil.dbCreateFiledAlias(dbCreateFiled) + "='" + uId + "' or " + alias + "." + stateFile + "!=0)) ";
                    }
                }
            } catch (Exception ex) {
                log.error("排除当前操作人创建的未发起和作废的数据SQL处理失败！",ex);
                ex.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth) {
        String authSql = getAuthSql(tableName, null, uId, dbCreateFiled, userAuth);
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if(StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")){
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='"+str+"'");
            authSql=sb.toString();
        }
        return authSql;
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

    @Override
    public String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String pKey) {
        String authSql = getAuthSql(tableName, pKey, uId, dbCreateFiled, userAuth);
        //authSql为null则无权操作
        if (ValidateUtil.isEmpty(authSql)) return null;
        String str = sysConfigService.getConfigByCode("projectDateLimit");
        if(StringUtils.isNotEmpty(str) && authSql.trim().contains("fpro")){
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='"+str+"'");
            authSql=sb.toString();
        }
        return authShowDisable(dbCreateFiled, stateFile, uId, authSql);
    }

    private String getAuthSql(String tableName, String pKey, String uId, String dbCreateFiled, Integer userAuth) {
        SysUser user = sysUserService.getById(uId);
        log.info("ID查询到的用户信息:" + user == null ? null : JacksonUtils.toJSONString(user));
        if (user == null) return null;
        Object userLogInfo = redisUtils.get("userLoginInfo_" + user.getuId());
        log.info("redis内的用户信息:" + userLogInfo);
        if (userLogInfo == null) return null;
        Map<String, Object> info = JacksonUtils.parseObject(userLogInfo.toString(), Map.class);
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        ContractAuthorityDTO auth = sysContractAuthorityService.getConIdByCidFromContractAuthority(cId,uId);
        return DataAuthSqlUtil.getAuthSql(tableName, uId, dbCreateFiled, userAuth, user.getuType(), info, auth.getDisables(),auth.getEnables(), pKey);
    }
}
