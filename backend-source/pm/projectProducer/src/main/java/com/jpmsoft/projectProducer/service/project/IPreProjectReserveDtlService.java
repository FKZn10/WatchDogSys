package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserveDtl;

public interface IPreProjectReserveDtlService extends IService<PreProjectReserveDtl> {
    PreProjectReserveDtl getpreProjectReservePprById(String id);

    PreProjectReserveChangeDTO getpreProjectReservePprByIdData(String id);
}
