package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysConfigEntityMapper;
import com.jpmsoft.userProducer.pojo.dto.*;
import com.jpmsoft.userProducer.pojo.dto.sms.SmsTimeConfDto;
import com.jpmsoft.userProducer.pojo.entity.SysConfigEntity;
import com.jpmsoft.userProducer.pojo.vo.ConfigVo;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysFileAnnexService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysConfigEntityImpl extends ServiceImpl<SysConfigEntityMapper, SysConfigEntity> implements ISysConfigEntityService {


    /**
     * 同步到redis的config配置
     */
    private static final List<String> redisConfig = Arrays.asList("showCancelData", "showCancelDataAuth", "graphicCodeAgeing", "cjAllRoleDep", "cjPeerRoleDep", "noTimeoutAccount", "fanoutMsg2Rabbit");
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse selectByCode(String code, String token) {
        QueryWrapper<SysConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        List<SysConfigEntity> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities)) {
            if (ValidateUtil.isNotEmpty(token)) {
                String teId = redisUtils.getTeId(token);
                if (ValidateUtil.isNotEmpty(teId)) {
                    List<SysConfigEntity> tes = entities.stream().filter(f -> ValidateUtil.isNotEmpty(f.getTeId()) && f.getTeId().equals(teId)).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(tes)) entities = tes;
                }
            }
            SysConfigEntity entity = entities.stream().findFirst().get();
            ConfigVo vo = new ConfigVo();
            BeanUtils.copyProperties(entity, vo);
            return BaseResponse.ok(vo);
        }
        return BaseResponse.ok();
    }

    @Override
    public DepartmentPCjConfDTO getDepartmentPCjConf() {
        String conf = getSysConfigByCode("departmentPCjConf");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, DepartmentPCjConfDTO.class);
        return new DepartmentPCjConfDTO();
    }

    @Override
    public BaseResponse CreateOrUpdateConfig(ConfigDto request, String token) {
        SysConfigEntity pojo;
        if (ValidateUtil.isEmpty(request.getConId())) pojo = new SysConfigEntity();
        else {
            pojo = baseMapper.selectById(request.getConId());
            if (pojo == null) pojo = new SysConfigEntity();
        }
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        pojo.setConCode(request.getConCode());
        pojo.setConDescription(request.getConDescription());
        pojo.setConValue(request.getConValue());
        pojo.setConName(request.getConName());
        pojo.setConType(request.getConType());
        pojo.setConCategory(request.getConCategory());
        pojo.setConStatus(request.getConStatus());
        pojo.setTeId(request.getTeId());
        pojo.setConMold(request.getConMold());
        pojo.setUpdateTime(new Date());
        pojo.setUpdateUname(user.getName());
        pojo.setUpdateUid(user.getId());
        QueryWrapper<SysConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", request.getConCode());
        //非系统类型配置 不同租户下可以重复
        if (ValidateUtil.isNotEmpty(request.getTeId()) && request.getConMold() != 1)
            wrapper.eq("Te_Id", request.getTeId());
        if (!ValidateUtil.isEmpty(request.getConId())) wrapper.ne("Con_ID", request.getConId());
        if (baseMapper.selectCount(wrapper) > 0) return BaseResponse.fail(BaseMsg.codeAlreadyExists);

        List<Map<String, String>> params = new ArrayList<>();
        Map<String, String> file = new HashMap<>();
        file.put("fileRelationId", request.getConValue());
        file.put("fileIds", request.getFileIds());
        params.add(file);
        if (ValidateUtil.isEmpty(request.getConId())) {
            pojo.setConCreateuName(request.getConCreateuName());
            pojo.setConCreateuId(request.getConCreateuId());
            pojo.setConCreatetime(new Date());
            pojo.setConId(GenerateUUID.getUUID());
            pojo.setConStatus(ValidateUtil.isNull(request.getConStatus()) ? 1 : request.getConStatus());
            baseMapper.insert(pojo);
            if (request.getConType() == 1) //是文件
                sysFileAnnexService.refreshRelationFile(params, pojo.getConId());
            refershRedisConfig(pojo.getConCode(), pojo.getConValue());
        } else {
            baseMapper.updateById(pojo);
            if (request.getConType() == 1)//是文件
                sysFileAnnexService.refreshRelationFile(params, pojo.getConId());
            refershRedisConfig(pojo.getConCode(), pojo.getConValue());
        }
        return BaseResponse.ok();
    }

    private void refershRedisConfig(String conCode, String value) {
        //排除非当前操作人创建的未发起和作废的数据
        if (redisConfig.contains(conCode)) redisUtils.set("Config_" + conCode, value);
    }

    @Override
    public BaseResponse DeleteConfig(ConfigDto request) {
        SysConfigEntity pojo = baseMapper.selectById(request.getConId());
        if (pojo == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(request.getConId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse GetConfigList(ConfigDto request) {
        QueryWrapper<SysConfigEntity> queryWrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getTeId())) queryWrapper.eq("Te_Id", request.getTeId());
        if (!ValidateUtil.isEmpty(request.getConStatus())) queryWrapper.eq("Con_Status", request.getConStatus());
        if (!ValidateUtil.isEmpty(request.getConCategory())) queryWrapper.eq("Con_Category", request.getConCategory());
        if (!ValidateUtil.isEmpty(request.getConCode())) queryWrapper.like("Con_Code", request.getConCode());
        if (!ValidateUtil.isEmpty(request.getConName())) queryWrapper.like("Con_Name", request.getConName());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) queryWrapper.orderByAsc(request.getSortFiled());
            else queryWrapper.orderByDesc(request.getSortFiled());
        } else queryWrapper.orderByDesc("Con_CreateTime");

        IPage<SysConfigEntity> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse GetConfigValueByCode(String code, String token) {
        if (ValidateUtil.isNotEmpty(token))
            return BaseResponse.ok(getConfigByCode(code, token));
        else
            return BaseResponse.ok(getSysConfigByCode(code));
    }

    @Override
    public BaseResponse GetConfigValuesByCodes(List<String> codes, String token) {
        if (CollectionUtils.isEmpty(codes)) return new BaseResponse(BaseMsg.notReqParam);
        List<String> realCodes = new ArrayList<>();
        codes.forEach(code -> {
            if (!CheckNullUtil.checkStringConSpecialChar(code)) realCodes.add(code);
        });
        if (realCodes.size() > 0)
            return BaseResponse.ok(getConfigByCodes(realCodes, ValidateUtil.isEmpty(token) ? null : redisUtils.getUid(token)));
        else return BaseResponse.ok();
    }

    @Override
    public Map<String, String> getConfigByCodes(List<String> codes, String uid) {
        Map<String, String> data = new HashMap<>();
        if (CollectionUtils.isEmpty(codes)) return data;
        QueryWrapper<SysConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Status", 1);
        wrapper.in("Con_Code", codes);
        //入参没有uid时仅支持查询系统配置 不支持查看指定租户的配置
        //if (ValidateUtil.isEmpty(uid)) wrapper.eq("Con_Mold", 1);
        List<SysConfigEntity> entitys = baseMapper.selectList(wrapper);
        if (entitys != null) {
            String teId = ValidateUtil.isEmpty(uid) ? null : redisUtils.getTeIdByUid(uid);
            for (String code : codes) {
                List<SysConfigEntity> entity = entitys.stream().filter(n -> n.getConCode().equals(code)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(entity)) {
                    SysConfigEntity conf = entity.get(0);
                    if (ValidateUtil.isEmpty(teId) || (conf.getConMold() != null && conf.getConMold() == 1))
                        data.put(code, conf.getConValue());
                    else {
                        List<SysConfigEntity> confs = entity.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTeId()) && n.getTeId().equals(teId)).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(confs)) data.put(code, confs.get(0).getConValue());
                    }
                }
            }
        }
        return data;
    }

    @Override
    public String getConfigByCode(String code, String token) {
        QueryWrapper<SysConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        List<SysConfigEntity> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities)) {
            SysConfigEntity entity = entities.get(0);
            String teId = redisUtils.getTeId(token);
            if (ValidateUtil.isEmpty(teId) || (entity.getConMold() != null && entity.getConMold() == 1))
                return entity.getConValue();
            else {
                List<SysConfigEntity> tenantConfs = entities.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTeId()) && n.getTeId().equals(teId)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(tenantConfs)) return tenantConfs.get(0).getConValue();
            }
        }
        return null;
    }
    /**
     * 获取系统级配置值
     * @param code 配置键
     * @return 配置值
     */
    @Override
    public String getSysConfigByCode(String code) {
        QueryWrapper<SysConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        //系统级默认为仅只有一个配置存在
        //wrapper.eq("Con_Mold", 1);
        List<SysConfigEntity> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities)) return entities.get(0).getConValue();
        return null;
    }

    @Override
    public Integer getUserPasswordUsageLimit() {
        try {
            String str = getSysConfigByCode("userPasswordUsageLimit");
            if (ValidateUtil.isNotEmpty(str)) return Integer.parseInt(str);
            else return 0;
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public BaseResponse executeConfigAction(ExecuteConfigDto request, String token) {
        String sql = getConfigByCode(request.getCode(), token);
        if (ValidateUtil.isEmpty(sql))
            return BaseResponse.fail(String.format(BaseMsg.configIsError, request.getCode()));
        try {
            if (ValidateUtil.isNotNull(request.getParams())) {
                for (Map.Entry<String, Object> entry : request.getParams().entrySet()) {
                    if (ValidateUtil.isNotEmpty(entry.getKey()) && ValidateUtil.isNotNull(entry.getValue()))
                        sql = sql.replaceAll("\\{" + entry.getKey().trim() + "\\}", entry.getValue() + "");
                }
            }
            Boolean bool = baseMapper.executeConfigAction(sql);
            return BaseResponse.ok(bool);
        } catch (Exception ex) {
            return BaseResponse.fail(String.format(BaseMsg.configIsError, request.getCode()));
        }
    }


    @Override
    public SmsTimeConfDto getSmsTimeConf() {
        SmsTimeConfDto conf = new SmsTimeConfDto();
        String confStr = getSysConfigByCode("smsCodePrescriptionInterval");
        if (ValidateUtil.isNotEmpty(confStr)) {
            try {
                conf = JacksonUtils.parseObject(confStr, SmsTimeConfDto.class);
                return conf;
            } catch (Exception ex) {
                log.error("短信时效间隔配置(smsCodePrescriptionInterval)格式错误：" + ex.getMessage(), ex);
            }
        }
        return conf;
    }

    @Override
    public Long getThirdLoginTimeOut() {
        Long time = 60L;
        String data = getSysConfigByCode("thirdLoginTimeOut");
        try {
            if (ValidateUtil.isNotEmpty(data)) time = Long.valueOf(data);
        } catch (Exception ex) {
            log.error("基础配置thirdLoginTimeOut格式错误！", ex);
            return 60L;
        }
        return time;
    }

    @Override
    public UserPwdExpirationVerificationDTO getUserUserPwdExpirationConf(String uType) {
        if (ValidateUtil.isEmpty(uType)) throw new JPMException(BaseMsg.unknownUType);
        String confStr = getSysConfigByCode("userPwdExpirationVerification");
        if (ValidateUtil.isNotEmpty(confStr)) {
            Map map = JacksonUtils.parseObject(confStr, Map.class);
            if (map != null && map.containsKey(uType)) {
                UserPwdExpirationVerificationDTO pev = JacksonUtils.parseObject(JacksonUtils.toJSONString(map.get(uType)), UserPwdExpirationVerificationDTO.class);
                return pev;
            }
        }
        UserPwdExpirationVerificationDTO pev = new UserPwdExpirationVerificationDTO();
        pev.setOutTimeDisabledSwitch(false);
        pev.setOutTimeDisabledDays(0);
        return pev;
    }

    @Override
    public LoginWayDTO getLoginWay() {
        String confStr = getSysConfigByCode("loginWay");
        if (ValidateUtil.isNotEmpty(confStr)) {
            LoginWayDTO way = JacksonUtils.parseObject(confStr, LoginWayDTO.class);
            if (way != null) return way;
        }
        return new LoginWayDTO();
    }

    @Override
    public TenantModeConfigDTO getTenantConf() {
        String conf = getSysConfigByCode("tenantModeConfig");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, TenantModeConfigDTO.class);
        return null;
    }

    @Override
    public InterfaceEncDecDTO getInterfaceEncDec() {
        String conf = getSysConfigByCode("interfaceEncDecConfig");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, InterfaceEncDecDTO.class);
        else
            return new InterfaceEncDecDTO();
    }

    @Override
    public DepartmentSelectConfDTO getDepartmentSelectConf() {
        String conf = getSysConfigByCode("departmentSelectConf");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, DepartmentSelectConfDTO.class);
        else
            return new DepartmentSelectConfDTO();
    }

    @Override
    public AIDockConfigInfoDTO getAIDockConfigInfo() {
        String conf = getSysConfigByCode("AIDockConfigInfo");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, AIDockConfigInfoDTO.class);
        else
            return new AIDockConfigInfoDTO();
    }

    @Override
    public boolean pushRecordTypeFenTable() {
        String conf = getSysConfigByCode("pushRecordTypeFenTable");
        if (ValidateUtil.isNotEmpty(conf) && conf.equals("1"))
            return true;
        return false;
    }

    @Override
    public Boolean enableCompanyDepPenetrate() {
        String enable = getSysConfigByCode("mainManageCompanyDepPenetrate");
        return ValidateUtil.isNotEmpty(enable) && enable.equals("1");
    }

    @Override
    public String getUkeyType() {
        String type = getSysConfigByCode("usbKeyType");
        if (ValidateUtil.isNotEmpty(type)) return type;
        return "andang";
    }

}
