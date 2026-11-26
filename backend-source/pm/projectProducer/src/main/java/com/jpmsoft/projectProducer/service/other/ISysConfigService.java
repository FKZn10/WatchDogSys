package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvPlanYearCjAuthDTO;
import com.jpmsoft.projectProducer.pojo.dto.other.OuterRegisterActionUserDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.CjSafetyJdyMessageDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsExpertLogicalConfDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertLogicalConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysConfig;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/28 11:35
 */
public interface ISysConfigService extends IService<SysConfig> {

    String getConfigByCode(String code);

    String selectConfigByCodeNoToken(String code);


    /**
     * 是否流程审批结束才执行编码生成逻辑
     * 此逻辑临时使用
     *
     * @return
     */
    boolean endProcessGenerateCode();

    OuterRegisterActionUserDTO getOuterRegisterActionUser();

    SecExpertLogicalConfDTO getSecExpertLogicalConf();

    //招标代理
    SecExpertLogicalConfDTO getSecJGAgencyExpertLogicalConf();

    /**
     * 城建质量安全定制逻辑配置项
     *
     * @return
     */
    CjSafetyJdyMessageDTO getCjSafetyJdyMessage();

    /**
     * 建管专家库
     *
     * @return
     */
    SecExpertLogicalConfDTO getSecJGExpertLogicalConf();

    InterfaceEncDecDTO getInterfaceEncDec();

    DepartmentPCjConfDTO getDepartmentPCjConf();

    List<DepartmentPCjConfDTO> departmentPCjConfList();

    Map<String, Object> getConfigByCodes(List<String> codeList);

    /**
     * 城建年度计划鉴权配置
     *
     * @return
     */
    InvPlanYearCjAuthDTO getInvPlanYearCjAuth();

    QsExpertLogicalConfDTO getQsExpertLogicalConf();
}
