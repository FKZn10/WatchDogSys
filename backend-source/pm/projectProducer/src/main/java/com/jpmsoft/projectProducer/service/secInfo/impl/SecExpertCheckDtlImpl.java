package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertCheckDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertCheckDtlDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertLogicalConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheckDtl;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertCheckDtlVO;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertCheckDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordDtlService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 13:32
 */
@Service
public class SecExpertCheckDtlImpl extends ServiceImpl<SecExpertCheckDtlMapper, SecExpertCheckDtl> implements ISecExpertCheckDtlService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private UserProducerFeign userProducerFeign;


    @Override
    public void createOrUpdateSecExpertCheckDtl(List<SecExpertCheckDtlDTO> data, String ecId) {
        deleteByEcId(ecId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecExpertCheckDtl> entities = data.stream().map(d -> {
                SecExpertCheckDtl e = new SecExpertCheckDtl();
                BeanUtils.copyProperties(d, e);
                e.setEcId(ecId);
                e.setId(GenerateUUID.getUUID(d.getId()));
                return e;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(data)) saveBatch(entities);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrupdateSecExpertCheckDetailAndDtl(List<SecExpertCheckDtlModel> rawResult, String scdId) {
        delteByScdId(scdId);

        if (!CollectionUtils.isEmpty(rawResult)) {
            rawResult.forEach(d ->{
                if (ValidateUtil.isNotNull(d.getSelected())){
                    if (d.getSelected() == 0){
                        d.setSelected(2);
                    }
                }
                    }
            );
            List<SecExpertCheckDtl> entities = new ArrayList<>();

            for (SecExpertCheckDtlModel model : rawResult) {
                saveWithSubItems(model, model.getSecdSort(), null, entities, scdId);
            }

            saveBatch(entities);
        }
    }


    private int saveWithSubItems(SecExpertCheckDtlModel model, int index, String parentId, List<SecExpertCheckDtl> entities,String scdId) {
        SecExpertCheckDtl entity = new SecExpertCheckDtl();
        BeanUtils.copyProperties(model, entity);
        entity.setId(GenerateUUID.getUUID(model.getId()));
        entity.setScdId(scdId);
        entity.setParentId(parentId);

        // 外层使用传入的 secdSort，子项由程序生成
        if (parentId != null) {
            entity.setSecdSort(index++);
        }

        entities.add(entity);

        if (!CollectionUtils.isEmpty(model.getSubItems())) {
            int childIndex = 0;
            for (SecExpertCheckDtlModel sub : model.getSubItems()) {
                index = saveWithSubItems(sub, childIndex++, entity.getId(), entities,scdId);
            }
        }

        return index;
    }



    private void delteByScdId(String scdId) {
        QueryWrapper<SecExpertCheckDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Scd_id", scdId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecExpertCheckDtlModel> getSecExpertCheckDtlByScdId(String scdId) {
        // 1. 查询数据
        QueryWrapper<SecExpertCheckDtl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Scd_Id", scdId);
        List<SecExpertCheckDtl> records = baseMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(records)) {
            return new ArrayList<>();
        }

        // 2. 全局按 secdSort 排序
        records.sort(Comparator.comparingInt(SecExpertCheckDtl::getSecdSort));

        // 3. 转换为 Model 并建立 id -> model 映射
        Map<String, SecExpertCheckDtlModel> modelMap = records.stream()
                .map(record -> {
                    SecExpertCheckDtlModel model = new SecExpertCheckDtlModel();
                    BeanUtils.copyProperties(record, model);
                    model.setSubItems(new ArrayList<>()); // 初始化子项列表
                    return model;
                })
                .collect(Collectors.toMap(SecExpertCheckDtlModel::getId, model -> model));

        List<SecExpertCheckDtlModel> rootModels = new ArrayList<>();
        for (SecExpertCheckDtlModel model : modelMap.values()) {
            String parentId = model.getParentId();
            if (parentId == null) {
                rootModels.add(model);
            } else {
                SecExpertCheckDtlModel parentModel = modelMap.get(parentId);
                if (parentModel != null) {
                    parentModel.getSubItems().add(model);
                }
            }
        }

        // 对根节点排序
        rootModels.sort(Comparator.comparingInt(SecExpertCheckDtlModel::getSecdSort));

        // 5. 对每个父节点下的子节点再次排序
        for (SecExpertCheckDtlModel root : rootModels) {
            sortSubItemsRecursively(root);
        }

        return rootModels;
    }

    /**
     * 递归对每个节点下的 subItems 排序
     */
    private void sortSubItemsRecursively(SecExpertCheckDtlModel node) {
        if (!CollectionUtils.isEmpty(node.getSubItems())) {
            node.getSubItems().sort(Comparator.comparingInt(SecExpertCheckDtlModel::getSecdSort));
            for (SecExpertCheckDtlModel child : node.getSubItems()) {
                sortSubItemsRecursively(child);
            }
        }
    }

    @Override
    public void deleteByEcId(String ecId) {
        QueryWrapper<SecExpertCheckDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ec_Id", ecId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void updateResultByIds(List<String> ids, Integer result) {
        if (!CollectionUtils.isEmpty(ids))
            baseMapper.updateResultByIds(ids, result);
    }

    @Override
    public BaseResponse getExpertCheckDtlList(String secId, String uId) {
        SysUser user = sysUserService.getById(uId);
        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        BaseResponse res = userProducerFeign.getBeforeWorkDay(conf.getAppealDay());
        if (res.getSuccess() == null || !res.getSuccess()) return res;
        String date = res.getData().toString();
        List<SecExpertCheckDtlVO> data = baseMapper.getExpertCheckDtlList(secId, user.getUPlaintextNum(), date);
        if (CollectionUtils.isEmpty(data))
            return BaseResponse.fail(ResMsg.canNotExpertCheck);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecExpertCheckDtlRecord(SecSelectionRecordDtlModel request) {
        return null;
    }


}
