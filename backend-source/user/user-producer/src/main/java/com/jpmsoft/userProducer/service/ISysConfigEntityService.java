package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.pojo.dto.*;
import com.jpmsoft.userProducer.pojo.dto.sms.SmsTimeConfDto;
import com.jpmsoft.userProducer.pojo.entity.AIInfo;
import com.jpmsoft.userProducer.pojo.entity.SysConfigEntity;

import java.util.List;
import java.util.Map;


public interface ISysConfigEntityService extends IService<SysConfigEntity> {


    BaseResponse selectByCode(String code,String token);

    DepartmentPCjConfDTO getDepartmentPCjConf();

    /**
     * 新增or修改配置项信息
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdateConfig(ConfigDto request,String token);

    /**
     * 删除配置项信息
     *
     * @param request
     * @return
     */
    BaseResponse DeleteConfig(ConfigDto request);


    /**
     * 配置列表
     *
     * @param request
     * @return
     */
    BaseResponse GetConfigList(ConfigDto request);

    /**
     * KEY获取Value
     *
     * @param code
     * @return
     */
    BaseResponse GetConfigValueByCode(String code, String token);

    /**
     * 批量
     *
     * @param codes
     * @return
     */
    BaseResponse GetConfigValuesByCodes(List<String> codes, String token);

    Map<String, String> getConfigByCodes(List<String> codes, String uid);

    String getConfigByCode(String code, String token);

    /**
     * 获取系统级别配置
     *
     * @param code
     * @return
     */
    String getSysConfigByCode(String code);

    /**
     * 获取用户密码使用天数限制
     *
     * @return
     */
    Integer getUserPasswordUsageLimit();


    BaseResponse executeConfigAction(ExecuteConfigDto request, String token);


    /**
     * 短信发送时效间隔配置
     *
     * @return
     */
    SmsTimeConfDto getSmsTimeConf();

    /**
     * 第三方登录入参超时时效
     * 无配置默认60L
     *
     * @return
     */
    Long getThirdLoginTimeOut();

    /**
     * 获取当前用户类型的
     * 用户密码期限校验配置（区分内外部用户）
     *
     * @param uType
     * @return
     */
    UserPwdExpirationVerificationDTO getUserUserPwdExpirationConf(String uType);


    LoginWayDTO getLoginWay();

    TenantModeConfigDTO getTenantConf();

    InterfaceEncDecDTO getInterfaceEncDec();

    DepartmentSelectConfDTO getDepartmentSelectConf();

    AIDockConfigInfoDTO getAIDockConfigInfo();
    /**
     * push_record表是否根据推送类型分表
     *
     * @return
     */
    boolean pushRecordTypeFenTable();

    /**
     * 用户所在部门为单位部门且授权模式为按主管部门授权则穿透平级部门以及平级的下级单位。
     * @return
     */
    Boolean enableCompanyDepPenetrate();

    /**
     * 查询ukey设备类型 缺省andang
     * @return
     */
    String getUkeyType();
}
