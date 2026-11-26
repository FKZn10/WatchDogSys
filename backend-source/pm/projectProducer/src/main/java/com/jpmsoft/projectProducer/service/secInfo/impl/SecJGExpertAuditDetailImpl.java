package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGExpertAuditDetailMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertAuditDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditDetail;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertAuditDetail;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGExpertAuditDetailVO;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertAuditDetailService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecJGExpertAuditDetailImpl extends ServiceImpl<SecJGExpertAuditDetailMapper, SecJGExpertAuditDetail> implements ISecJGExpertAuditDetailService {

    @Resource
    private ISecJGExpertService secJGExpertService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    public void deleteCreate(List<SecJGExpertAuditDetailDTO> data, String seaId) {
        deleteBySeaId(seaId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecJGExpertAuditDetail> entities = new ArrayList<>();
            for (SecJGExpertAuditDetailDTO d : data) {
                if (ValidateUtil.isNotEmpty(d.getExId())) {
                    SecJGExpertAuditDetail entity = new SecJGExpertAuditDetail();
                    BeanUtils.copyProperties(d, entity);
                    entity.setId(GenerateUUID.getUUID());
                    entity.setSeaId(seaId);
                    entities.add(entity);
                }
            }
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteBySeaId(String seaId) {
        QueryWrapper<SecJGExpertAuditDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Sea_Id", seaId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecJGExpertAuditDetailVO> getDetailList(String seaId) {
        List<SecJGExpertAuditDetail> data = selectBySeaId(seaId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecJGExpertModel> exms = secJGExpertService.getExpertListByIds(data.stream().map(SecJGExpertAuditDetail::getExId).collect(Collectors.toList()));
            return data.stream().map(d -> {
                SecJGExpertAuditDetailVO vo = new SecJGExpertAuditDetailVO();
                BeanUtils.copyProperties(d, vo);
                List<SecJGExpertModel> exm = exms.stream().filter(f -> f.getId().equals(d.getExId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(exm))
                    BeanUtils.copyProperties(exm.stream().findFirst().get(), vo);
                String idCard = apiUtilsService.SelfSm2Decrypt(vo.getExIdNumber());
                vo.setExIdNumber(VoInfoUtil.handleUserIdCard(idCard));
                if (ValidateUtil.isNotEmpty(idCard)) {
                    vo.setExAge(IdcardUtil.getAgeByIdCard(idCard));
                }
                return vo;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<String> getExIdList(String seaId) {
        List<SecJGExpertAuditDetail> data = selectBySeaId(seaId);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(SecJGExpertAuditDetail::getExId).collect(Collectors.toList());
        return null;
    }

    private List<SecJGExpertAuditDetail> selectBySeaId(String seaId) {
        QueryWrapper<SecJGExpertAuditDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Sea_Id", seaId);
        return baseMapper.selectList(wrapper);
    }

}
