package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.fileUpload.mapper.WatermarkMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.entity.Watermark;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.service.IWatermarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/10 13:41
 */
@Service
public class WatermarkImpl extends ServiceImpl<WatermarkMapper, Watermark> implements IWatermarkService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public String getWatermarkPath(String root, String id) {
        Watermark entity = baseMapper.selectById(id);
        if (entity == null) throw new JPMException("未检索到水印信息！【Watermark表，id:" + id + "】");
        SysFileAnnex file = sysFileAnnexService.getOneFileAnnexByRelationId(entity.getWFile());
        if (file == null) throw new JPMException("未检索到水印图片信息！【Watermark表，id:" + id + "】");
        return root + "/" + file.getFilePath();
    }

    @Override
    public Watermark getByWatermarkId(String root, String id) {
        return getByWatermarkId(root, id, false);
    }

    @Override
    public Watermark getByWatermarkId(String root, String id, Boolean isPdf) {
        Watermark entity = baseMapper.selectById(id);
        if (entity == null) throw new JPMException("未检索到水印信息！【Watermark表，id:" + id + "】");
        SysFileAnnex file = sysFileAnnexService.getOneFileAnnexByRelationId(entity.getWFile());
        if (file == null) throw new JPMException("未检索到水印图片信息！【Watermark表，id:" + id + "】");
        String imgPath = root + "/" + file.getFilePath();
        String opacityPath = imgPath.substring(0, imgPath.lastIndexOf("/") + 1) + GenerateUUID.getUUID() + ".png";
        if (!isPdf && Float.compare(entity.getWOpacity(), 1f) < 0) {//需要调整图片透明度 PDF添加水印不需要自己调整图片透明度
            try {
                setImageOpacity(imgPath, opacityPath, entity.getWOpacity());
                entity.setPath(opacityPath);
                entity.setDeletePath(Boolean.TRUE);
            } catch (Exception exception) {
                log.error("图片透明度调整失败！");
                entity.setPath(imgPath);
            }
        } else entity.setPath(imgPath);
        return entity;
    }

    /**
     * 设置图片透明度
     */
    public static void setImageOpacity(String inputPath, String outputPath, float opacity) throws Exception {
        BufferedImage srcImage = ImageIO.read(new File(inputPath));
        BufferedImage destImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = destImage.createGraphics();
        // 设置透明度（opacity 范围：0.0~1.0）
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(srcImage, 0, 0, null);
        g2d.dispose();
        ImageIO.write(destImage, "PNG", new File(outputPath)); // 输出为支持透明度的 PNG 格式
    }

    @Override
    public BaseResponse getWatermarkList() {
        QueryWrapper<Watermark> wrapper = new QueryWrapper<>();
        wrapper.eq("W_Is_Activate", "true");
        wrapper.ne("state", ApprovalStatusEnum.Invalid.getIndex());
        wrapper.orderByAsc("create_time");
        return BaseResponse.ok(baseMapper.selectList(wrapper));
    }
}
