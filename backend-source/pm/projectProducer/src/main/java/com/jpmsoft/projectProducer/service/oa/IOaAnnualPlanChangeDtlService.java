package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanChangeDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanChangeDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:05
 */
public interface IOaAnnualPlanChangeDtlService extends IService<OaAnnualPlanChangeDtl> {

    void createOrUpdateOaAnnualPlanChangeDtl(List<OaAnnualPlanChangeDtlDto> datas, String apcId);

    void delOaAnnualPlanChangeDtlByApcId(String apcId);

}
