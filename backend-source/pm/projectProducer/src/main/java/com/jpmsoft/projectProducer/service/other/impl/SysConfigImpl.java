package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.other.SysConfigMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvPlanYearCjAuthDTO;
import com.jpmsoft.projectProducer.pojo.dto.other.OuterRegisterActionUserDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.CjSafetyJdyMessageDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsExpertLogicalConfDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertLogicalConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysConfig;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/28 11:35
 */
@Service
public class SysConfigImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Resource
    private RedisUtils redisUtils;

    @Override
    public String getConfigByCode(String code) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        LogUserInfoVo user = redisUtils.userInfo();
        if (user != null && user.isEnableTenant()) {
            wrapper.and(e -> e.eq("Con_Mold", 1).or().eq("Te_Id", user.getTeId()));
        }
        List<SysConfig> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.get(0).getConValue();
        else return null;
    }


    @Override
    public String selectConfigByCodeNoToken(String code) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        List<SysConfig> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.get(0).getConValue();
        else return null;
    }

    @Override
    public boolean endProcessGenerateCode() {
        String value = getConfigByCode("endProcessGenerateCode");
        if (ValidateUtil.isNotEmpty(value) && value.equals("1")) return true;
        return false;
    }

    @Override
    public OuterRegisterActionUserDTO getOuterRegisterActionUser() {
        String str = selectConfigByCodeNoToken("outerRegisterActionUserInfo");
        OuterRegisterActionUserDTO c = null;
        if (ValidateUtil.isNotEmpty(str))
            c = JacksonUtils.parseObject(str, OuterRegisterActionUserDTO.class);
        return c;
    }

    @Override
    public SecExpertLogicalConfDTO getSecExpertLogicalConf() {
        String str = getConfigByCode("secExpertLogicalConf");
        SecExpertLogicalConfDTO c = new SecExpertLogicalConfDTO();
        if (ValidateUtil.isNotEmpty(str))
            c = JacksonUtils.parseObject(str, SecExpertLogicalConfDTO.class);
        return c == null ? new SecExpertLogicalConfDTO() : c;
    }

    @Override
    public SecExpertLogicalConfDTO getSecJGAgencyExpertLogicalConf() {
        String str = getConfigByCode("secJGAgencyExpertLogicalConf");
        SecExpertLogicalConfDTO c = new SecExpertLogicalConfDTO();
        if (ValidateUtil.isNotEmpty(str))
            c = JacksonUtils.parseObject(str, SecExpertLogicalConfDTO.class);
        return c == null ? new SecExpertLogicalConfDTO() : c;
    }

    @Override
    public CjSafetyJdyMessageDTO getCjSafetyJdyMessage() {
        String str = getConfigByCode("cjSafetyJdyMessage");
        CjSafetyJdyMessageDTO c = new CjSafetyJdyMessageDTO();
        if (ValidateUtil.isNotEmpty(str))
            c = JacksonUtils.parseObject(str, CjSafetyJdyMessageDTO.class);
        return c == null ? new CjSafetyJdyMessageDTO() : c;
    }

    @Override
    public SecExpertLogicalConfDTO getSecJGExpertLogicalConf() {
        String str = getConfigByCode("secJGExpertLogicalConf");
        SecExpertLogicalConfDTO c = new SecExpertLogicalConfDTO();
        if (ValidateUtil.isNotEmpty(str))
            c = JacksonUtils.parseObject(str, SecExpertLogicalConfDTO.class);
        return c == null ? new SecExpertLogicalConfDTO() : c;
    }

    @Override
    public InterfaceEncDecDTO getInterfaceEncDec() {
        String conf = getConfigByCode("interfaceEncDecConfig");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, InterfaceEncDecDTO.class);
        return new InterfaceEncDecDTO();
    }

    @Override
    public DepartmentPCjConfDTO getDepartmentPCjConf() {
        String conf = getConfigByCode("departmentPCjConf");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, DepartmentPCjConfDTO.class);
        return new DepartmentPCjConfDTO();
    }

    @Override
    public List<DepartmentPCjConfDTO> departmentPCjConfList() {
        String conf = getConfigByCode("departmentPCjConfList");
        if (ValidateUtil.isNotEmpty(conf)) {
            return JacksonUtils.parseArray(conf, DepartmentPCjConfDTO.class);
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, Object> getConfigByCodes(List<String> codeList) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.in("Con_Code", codeList);
        wrapper.eq("Con_Status", 1);
        LogUserInfoVo user = redisUtils.userInfo();
        if (user != null && user.isEnableTenant()) {
            wrapper.and(e -> e.eq("Con_Mold", 1).or().eq("Te_Id", user.getTeId()));
        }
        List<SysConfig> entities = baseMapper.selectList(wrapper);
        Map<String, Object> map = entities.stream().collect(Collectors.toMap(SysConfig::getConCode, SysConfig::getConValue));
        return map;
    }

    @Override
    public InvPlanYearCjAuthDTO getInvPlanYearCjAuth() {
        String conf = getConfigByCode("invPlanYearCjAuthConf");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, InvPlanYearCjAuthDTO.class);
        return null;
    }

    @Override
    public QsExpertLogicalConfDTO getQsExpertLogicalConf() {
        String str = getConfigByCode("qsExpertLogicalConfDTO");
        QsExpertLogicalConfDTO c = new QsExpertLogicalConfDTO();
        if (ValidateUtil.isNotEmpty(str))
            c = JacksonUtils.parseObject(str, QsExpertLogicalConfDTO.class);
        return c == null ? new QsExpertLogicalConfDTO() : c;
    }
}
