package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirImageDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirImage;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
public interface IMemoirImageService extends IService<MemoirImage> {

    /**
     * 删除记录后重建
     * 中间会重新刷新附件状态以及重新生成压缩图片
     *
     * @param data 图片信息数据
     * @param miId 大事记ID
     */
    void createOrUpdate(List<MemoirImageDTO> data, String miId);

    void deleteByMiId(String miId);

    List<MemoirImage> selectByMiId(String miId);

    List<MemoirImageDTO>  selectByMiIds(List<String> miIds);

}
