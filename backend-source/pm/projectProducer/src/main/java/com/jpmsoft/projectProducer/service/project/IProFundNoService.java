package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.ProFundNoDto;
import com.jpmsoft.common.model.response.Project.ProFundNoVo;
import com.jpmsoft.projectProducer.pojo.entity.project.ProFundNo;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/20 14:51
 */
public interface IProFundNoService extends IService<ProFundNo> {

    void createOrUpdateProFundNo(List<ProFundNoDto> datas, String pId);

    void delProFundNoByPid(String pId);

    List<ProFundNoVo> getProFundNoByPid(String pId);
}
