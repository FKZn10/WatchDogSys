package com.jpmsoft.projectProducer.service.conSupNotice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNoticeDw;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/6 14:44
 */
public interface IConSupNoticeDwService extends IService<ConSupNoticeDw> {

    void createOrUpdate(List<ConSupNoticeDw> data, String snId);

    void deleteBySnId(String snId);

    List<ConSupNoticeDw> getBySnId(String snId);

    List<ConSupNoticeDw> getBySnId(List<String> snId);
}
