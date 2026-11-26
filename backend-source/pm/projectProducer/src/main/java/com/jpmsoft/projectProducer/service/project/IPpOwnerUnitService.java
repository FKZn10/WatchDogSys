package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PpOwnerUnit;

import java.util.ArrayList;
import java.util.List;

public interface IPpOwnerUnitService extends IService<PpOwnerUnit> {
    /**
     * 根据项目id查询项目所有业主单位人员详情
     *
     * @param pId
     * @return
     */
    List<PpOwnerUnitDTO> getPpOwnerUnitByPId(String pId);

    List<PpOwnerUnitDTO> listPpOwnerUnitByPIds(ArrayList<String> strings);
}
