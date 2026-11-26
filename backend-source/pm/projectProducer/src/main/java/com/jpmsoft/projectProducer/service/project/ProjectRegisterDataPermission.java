package com.jpmsoft.projectProducer.service.project;

import cn.hutool.core.util.StrUtil;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.projectProducer.mapper.other.SysUserMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * 项目登记、项目申报和项目论证共用的数据权限，按创建人当前所属单位授权。
 */
@Component
public class ProjectRegisterDataPermission {
    public static final String COMPANY_WHITELIST_CONFIG = "projectRegister.companyWhitelist";

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 从当前请求令牌取得真实登录用户，不接受表单中的用户或单位信息。
     *
     * @return 当前登录用户
     * @throws JPMException 未登录时拒绝访问
     */
    public LogUserInfoVo currentUser() {
        LogUserInfoVo user = redisUtils.userInfo();
        if (user == null || StrUtil.isBlank(user.getId())) {
            throw new JPMException(401, "请先登录");
        }
        if (!unrestricted(user)) {
            requireCompany(user);
        }
        return user;
    }

    /**
     * 白名单单位用于授权业务管理方跨单位访问。
     *
     * @param user 已认证的登录用户
     * @return 是否可访问所有单位的数据
     */
    public boolean unrestricted(LogUserInfoVo user) {
        String config = sysConfigService.getConfigByCode(COMPANY_WHITELIST_CONFIG);
        return StrUtil.isNotBlank(user.getCId()) && StrUtil.isNotBlank(config)
                && Arrays.stream(config.split("[,，]"))
                .map(String::trim).anyMatch(user.getCId()::equals);
    }

    /**
     * 将可信的单位范围写入分页查询参数；无单位的普通用户不允许查询。
     *
     * @param params 后端组装的查询参数
     * @param user   已认证的登录用户
     */
    public void applyScope(Map<String, Object> params, LogUserInfoVo user) {
        params.remove("permissionCompanyId");
        if (!unrestricted(user)) {
            requireCompany(user);
            params.put("permissionCompanyId", user.getCId());
        }
    }

    /**
     * 检查已有记录的创建人单位，缺失创建人、单位或跨单位访问均拒绝。
     *
     * @param createUid 数据库中保存的创建人ID
     * @param user      已认证的登录用户
     */
    public void checkCreator(String createUid, LogUserInfoVo user) {
        if (unrestricted(user)) {
            return;
        }
        requireCompany(user);
        SysUser creator = StrUtil.isBlank(createUid) ? null : sysUserMapper.selectById(createUid);
        if (creator == null || !user.getCId().equals(creator.getCId())) {
            throw new JPMException(403, "无权访问其他单位或归属不明的项目数据");
        }
    }

    /**
     * 验证普通用户存在单位，避免空单位之间被误判为同单位。
     *
     * @param user 已认证的登录用户
     */
    private void requireCompany(LogUserInfoVo user) {
        if (StrUtil.isBlank(user.getCId())) {
            throw new JPMException(403, "当前登录用户未关联单位");
        }
    }
}
