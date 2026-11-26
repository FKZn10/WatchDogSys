package com.jpmsoft.projectProducer.service.acpm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmDJTBSubjectTenderDto;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmDJTBSubjectTender;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmDJTBSubjectTenderVo;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:59
 */
public interface IAcpmDJTBSubjectTenderService extends IService<AcpmDJTBSubjectTender> {

    void createOrUpdate(List<AcpmDJTBSubjectTenderDto> subjectTenders, String daId);

    /**
     * 根据代建投标文件审批ID删除
     *
     * @param daId
     */
    void deleteByDaId(String daId);

    /**
     * 根据代建投标文件审批ID查询
     *
     * @param daId
     * @return
     */
    List<AcpmDJTBSubjectTenderVo> getByDaId(String daId);

    /**
     * 根据代建投标文件审批ID查询 批量
     *
     * @param daId
     * @return
     */
    List<AcpmDJTBSubjectTenderVo> getBathByDaIds(List<String> daIds);
}
