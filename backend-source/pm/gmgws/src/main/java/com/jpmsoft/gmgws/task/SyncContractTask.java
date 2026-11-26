package com.jpmsoft.gmgws.task;


import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.gmgws.pojo.entity.ContractStatistics;
import com.jpmsoft.gmgws.pojo.vo.ContractVo;
import com.jpmsoft.gmgws.service.ContractStatisticsService;
import com.jpmsoft.gmgws.service.InfrastrExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SyncContractTask {
        @Autowired
        private InfrastrExcelService infrastrExcelService;

        @Autowired
        private ContractStatisticsService contractStatisticsService;


        //光明合同统计定时任务
    @Scheduled(cron = "${gmgws.codeTask:-}")
    public void saveCon(){
        log.info("合同统计start");
        List<ContractVo> contractAdopt = infrastrExcelService.getContract();
        if(!CollectionUtils.isEmpty(contractAdopt)){
            List<ContractStatistics> list = new ArrayList<>();
            for (ContractVo contractVo : contractAdopt) {
                ContractStatistics statistics = new ContractStatistics();
                BeanUtils.copyProperties(contractVo,statistics);
                statistics.setCsMoney(contractVo.getCsMoney());
                statistics.setId(GenerateUUID.getUUID());
                list.add(statistics);
            }
            if(!CollectionUtils.isEmpty(list)){
                try {
                    contractStatisticsService.saveBatch(list);
                } catch (DataIntegrityViolationException e) {
                    throw new JPMException("saveCon合同统计数据保存失败:" + e.getMessage());
                }
            }
        }

    }

}
