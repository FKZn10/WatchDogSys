package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ReportedPersonnelDtl;

import java.util.List;

public interface IReportedPersonnelDtlService extends IService<ReportedPersonnelDtl> {

    void createOrUpdateReportedPersonnelDtl(List<ReportedPersonnelDtlModel> datas, String rpId);

    List<ReportedPersonnelDtlModel> getReportedPersonnelDtlByRpId(String rpId);

    /**
     * 判断当前项目是否可以报备
     * 根据配置项 reportedPersonnel（岗位名称，配置几个，则这几个岗位名称必须配备有人才能保存）
     *
     * @param datas 当前即将报备的人员岗位
     * @param pId   项目Id
     * @return 缺少的岗位字符串，null则表示通过
     */
    String can2Filing(List<ReportedPersonnelDtlModel> datas, String pId);
}
