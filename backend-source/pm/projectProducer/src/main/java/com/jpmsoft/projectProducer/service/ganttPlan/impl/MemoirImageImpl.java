package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.ImgCompressAndRefreshStateDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.FileFeign;
import com.jpmsoft.projectProducer.mapper.ganttPlan.MemoirImageMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirImageDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirImage;
import com.jpmsoft.projectProducer.service.ganttPlan.IMemoirImageService;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
@Service
public class MemoirImageImpl extends ServiceImpl<MemoirImageMapper, MemoirImage> implements IMemoirImageService {

    @Resource
    private FileFeign fileFeign;

    @Override
    public void createOrUpdate(List<MemoirImageDTO> data, String miId) {
        List<MemoirImage> images = selectByMiId(miId);
        deleteByMiId(miId);
        if (!CollectionUtils.isEmpty(data)) {
            List<ImgCompressAndRefreshStateDTO> files = new ArrayList<>();
            List<String> imgs = new ArrayList<>();
            data.forEach(d -> {
                ImgCompressAndRefreshStateDTO iar = new ImgCompressAndRefreshStateDTO();
                iar.setImgRelationId(d.getMiImg());
                iar.setImgIds(d.getMiImgIds());
                imgs.add(d.getMiImg());
                files.add(iar);
            });
            images.forEach(d -> {
                if (!imgs.contains(d.getMiImg())) { //新保存数据不包含的文件 需要直接删除
                    ImgCompressAndRefreshStateDTO iar = new ImgCompressAndRefreshStateDTO();
                    iar.setImgRelationId(d.getMiImg()); //原图
                    iar.setCompressRelationId(d.getMiCompressImg()); //压缩图
                    files.add(iar);
                } else {
                    //原来的压缩图片需要删除
                    int index = IntStream.range(0, files.size())
                            .filter(i -> files.get(i).getImgRelationId().equals(d.getMiImg()))
                            .findFirst()
                            .orElse(-1);
                    ImgCompressAndRefreshStateDTO iar = files.get(index);
                    iar.setCompressRelationId(d.getMiCompressImg());
                    files.set(index, iar);
                }
            });
            BaseResponse res = fileFeign.refreshImgAndCompress(files, miId);
            if (res.getSuccess() && res.getStatusCode() == HttpStatus.SC_OK) {
                List<ImgCompressAndRefreshStateDTO> returns = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), ImgCompressAndRefreshStateDTO.class);
                if (CollectionUtils.isEmpty(returns)) throw new JPMException(ResMsg.fileSaveRefershError);
                List<MemoirImage> entities = new ArrayList<>();
                data.forEach(d -> {
                    MemoirImage entity = new MemoirImage();
                    entity.setId(GenerateUUID.getUUID());
                    entity.setMiId(miId);
                    entity.setMiCarousel(d.getMiCarousel());
                    entity.setMiSort(d.getMiSort());
                    ImgCompressAndRefreshStateDTO iar = returns.stream().filter(f -> f.getImgRelationId().equals(d.getMiImg())).findFirst().get();
                    entity.setMiImg(iar.getImgRelationId());
                    entity.setMiCompressImg(iar.getCompressRelationId());
                    entity.setMiCompressUrl(iar.getCompressUrl());
                    entity.setMiUrl(iar.getImgUrl());
                    entities.add(entity);
                });
                saveBatch(entities);
            } else
                throw new JPMException(ResMsg.fileSaveRefershError);
        }
    }

    @Override
    public void deleteByMiId(String miId) {
        QueryWrapper<MemoirImage> wrapper = new QueryWrapper<>();
        wrapper.eq("mi_id", miId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<MemoirImage> selectByMiId(String miId) {
        QueryWrapper<MemoirImage> wrapper = new QueryWrapper<>();
        wrapper.eq("mi_id", miId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<MemoirImageDTO> selectByMiIds(List<String> miIds) {
        if (CollectionUtils.isEmpty(miIds)) return new ArrayList<>();
        QueryWrapper<MemoirImage> wrapper = new QueryWrapper<>();
        wrapper.in("mi_id", miIds);
        List<MemoirImage> data = baseMapper.selectList(wrapper);
        return data.stream().map(d -> {
            MemoirImageDTO e = new MemoirImageDTO();
            BeanUtils.copyProperties(d, e);
            return e;
        }).collect(Collectors.toList());
    }


}
