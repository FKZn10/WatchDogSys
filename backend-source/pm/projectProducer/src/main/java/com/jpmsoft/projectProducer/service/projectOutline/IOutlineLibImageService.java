package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineLibImage;

import java.util.List;

/**
 * 目录图片对应接口
 *
 * @author tianhelin
 * @date 2025/08/25
 */
public interface IOutlineLibImageService extends IService<OutlineLibImage> {
    /**
     * 初始化
     */
    void imageInitialization();

    /**
     * 删除图片
     * <p>
     * 删除指定文件
     *
     * @param libId
     * @param fileId
     * @return
     */
    BaseResponse deleteFile(String libId, String fileId);

    /**
     * 删除图片
     * <p>
     * 批量删除指定文件
     *
     * @param libId
     * @param fileIds
     * @return
     */
    BaseResponse deleteFiles(String libId, List<String> fileIds);

    /**
     * 处理图片
     * <p>
     * 按照fileIds与当前libId下的图片的id的差集进行删除和新增
     *
     * @param libId
     * @param fileIds
     * @return
     */
    BaseResponse handleOutlineFile(String libId, List<String> fileIds);
}
