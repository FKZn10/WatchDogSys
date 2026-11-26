package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeDatasourceResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjecDatasourceResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineDelayDatasourceMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineDatasource;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineDelayDatasource;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectLib;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectManage;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.projectOutline.*;
import com.jpmsoft.projectProducer.utils.FreemarkerUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 延迟数据源实现类
 *
 * @author tianhelin
 * @date 2025/08/15
 */
@Service
public class OutlineDelayDatasourceImpl extends ServiceImpl<OutlineDelayDatasourceMapper, OutlineDelayDatasource> implements IOutlineDelayDatasourceService {
    @Resource
    private IOutlineProjectLibService outlineProjectLibService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private IOutlineNodeDatasourceService outlineNodeDatasourceService;
    @Resource
    private IOutlineProjecDatasourceService outlineProjecDatasourceService;
    @Resource
    private IOutlineProjectManageService outlineProjectManageService;

    /**
     * 处理延迟渲染
     *
     * @param result
     * @param pId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleDelayRender(List<OutlineProjectTempResponse> result, String pId) {
        List<OutlineDelayDatasource> delays = this.list(Wrappers.<OutlineDelayDatasource>lambdaQuery().eq(OutlineDelayDatasource::getPId, pId));
        if (ValidateUtil.isEmpty(result)) {
            // 避免空指针
            result = Collections.emptyList();
        }
        // 计算delays和result的差集，仅使用oplId和tCode进行匹配，oplId下的tCode可能有多个
        // 如果两边都存在，则认为是不需要后续处理的，要排除
        // 如果result中存在而delays中不存在，则认为是需要新增进表里的
        // 如果delays中存在而result中不存在，则认为是需要重新渲染的
        // 构建delays的key集合和result的key集合
        Set<String> delayKeys = delays.stream()
                .map(d -> d.getOplId() + "_" + d.getTCode())
                .collect(Collectors.toSet());
        Set<String> resultKeys = result.stream()
                .map(r -> r.getOplId() + "_" + r.getTCode())
                .collect(Collectors.toSet());
        // 需要新增进表里的
        List<OutlineProjectTempResponse> toBeAdded = result.stream()
                .filter(r -> !delayKeys.contains(r.getOplId() + "_" + r.getTCode()))
                .collect(Collectors.toList());
        // 需要重新渲染的
        List<OutlineDelayDatasource> toBeRemovedOrUpdate = delays.stream()
                .filter(d -> !resultKeys.contains(d.getOplId() + "_" + d.getTCode()))
                .collect(Collectors.toList());

        // 构建id → OutlineDelayDatasource
        Map<String, OutlineDelayDatasource> delayById = delays.stream()
                .collect(Collectors.toMap(OutlineDelayDatasource::getId, Function.identity(), (a, b) -> a));

        // 一次性查出项目下所有有效文件
        List<SysFile> allFiles = sysFileService.list(Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getFileproject, pId)
                .ne(SysFile::getFileflowstatus, String.valueOf(ApprovalStatusEnum.Invalid.getIndex()))
        );
        // 构建tCode → 最新SysFile
        Map<String, SysFile> latestSysFileByTCode = allFiles.stream()
                .filter(f -> f.gettCode() != null && f.getFilecreatedate() != null)
                .collect(Collectors.toMap(SysFile::gettCode, Function.identity(), BinaryOperator.maxBy(Comparator.comparing(SysFile::getFilecreatedate))));

        // 处理重新渲染或更新文件信息的记录
        if (ValidateUtil.isNotEmpty(toBeRemovedOrUpdate)) {
            // 构建sfId → SysFile
            Map<String, SysFile> latestSysFileBySfId = allFiles.stream()
                    .collect(Collectors.toMap(SysFile::getFileid, Function.identity(), (a, b) -> a));

            Set<String> oplIds = toBeRemovedOrUpdate.stream().map(OutlineDelayDatasource::getOplId).collect(Collectors.toSet());
            // 所有的目录信息
            List<OutlineProjectLib> projectLibs = outlineProjectLibService.listByIds(oplIds);
            // 构建 oplId → OutlineProjectLib
            Map<String, OutlineProjectLib> oplIdToLib = projectLibs.stream()
                    .collect(Collectors.toMap(OutlineProjectLib::getId, Function.identity(), (a, b) -> a));

            // 所有的数据源信息
            List<OutlineProjecDatasourceResponse> datasources = outlineProjecDatasourceService.getOutlineProjecDatasourceByOplIds(new ArrayList<>(oplIds));
            // 构建 oplId_odId → OutlineProjecDatasourceResponse
            Map<String, OutlineProjecDatasourceResponse> oplIdToDatasource = datasources.stream()
                    .collect(Collectors.toMap(
                            ds -> ds.getOplId() + "_" + ds.getOdId(),
                            Function.identity(),
                            (a, b) -> a
                    ));

            // 需要批量处理的记录
            List<String> removeIds = new ArrayList<>(toBeRemovedOrUpdate.size());
            List<OutlineDelayDatasource> delayToUpdate = new ArrayList<>(toBeRemovedOrUpdate.size());
            List<OutlineProjectLib> libsToUpdate = new ArrayList<>();

            Map<String, List<OutlineNodeDatasourceResponse>> dsByOplId = new HashMap<>();
            Map<String, List<String>> delayIdsByOplId = new HashMap<>();

            toBeRemovedOrUpdate.forEach(delay -> {
                String sfId = delay.getSfId();
                if (ValidateUtil.isEmpty(sfId)) {
                    SysFile file = latestSysFileByTCode.get(delay.getTCode());
                    if (ValidateUtil.isNull(file)) {
                        // 如果没有找到文件，则不处理
                        return;
                    }
                    // 判断文件是否已审批
                    if (Integer.valueOf(file.getFileflowstatus()).equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                        // 已审批，走渲染逻辑
                        OutlineProjecDatasourceResponse datasource = oplIdToDatasource.get(delay.getOplId() + "_" + delay.getOdId());
                        if (ValidateUtil.isNull(datasource)) {
                            return;
                        }
                        OutlineNodeDatasourceResponse response = new OutlineNodeDatasourceResponse();
                        BeanUtils.copyProperties(datasource, response);
                        dsByOplId.computeIfAbsent(delay.getOplId(), k -> new ArrayList<>()).add(response);
                        delayIdsByOplId.computeIfAbsent(delay.getOplId(), k -> new ArrayList<>()).add(delay.getId());
                        return;
                    }
                    delay.setSfId(file.getFileid());
                    delayToUpdate.add(delay);
                    return;
                }
                SysFile sysFile = latestSysFileBySfId.get(sfId);
                if (ValidateUtil.isNull(sysFile) || !Integer.valueOf(sysFile.getFileflowstatus()).equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                    // 如果不是已审批，则不处理
                    return;
                }
                // 数据源
                OutlineProjecDatasourceResponse datasource = oplIdToDatasource.get(delay.getOplId() + "_" + delay.getOdId());
                if (ValidateUtil.isNull(datasource)) {
                    return;
                }
                // 转换成模板的数据源格式
                OutlineNodeDatasourceResponse response = new OutlineNodeDatasourceResponse();
                BeanUtils.copyProperties(datasource, response);

                // 聚合到各自oplId的数据源列表
                dsByOplId.computeIfAbsent(delay.getOplId(), k -> new ArrayList<>()).add(response);
                // 记录该oplId下哪些delayId需要在渲染成功后删除
                delayIdsByOplId.computeIfAbsent(delay.getOplId(), k -> new ArrayList<>()).add(delay.getId());
            });

            // 处理过的lib和delay
            List<OutlineDelayDatasource> processedDelays = new ArrayList<>(toBeRemovedOrUpdate.size());
            // 批量渲染
            if (!dsByOplId.isEmpty()) {
                // 取出渲染目标 HTML
                List<String> htmls = new ArrayList<>(dsByOplId.size());
                List<String> oplOrder = new ArrayList<>(dsByOplId.size());

                for (String oplId : dsByOplId.keySet()) {
                    OutlineProjectLib lib = oplIdToLib.get(oplId);
                    if (lib == null) {
                        continue;
                    }
                    htmls.add(lib.getOplContent() == null ? "" : lib.getOplContent());
                    oplOrder.add(oplId);
                }

                // 合并所有数据源为一个列表
                Map<String, OutlineNodeDatasourceResponse> uniq = new LinkedHashMap<>();
                dsByOplId.values().forEach(list -> list.forEach(x -> uniq.put(x.getOdId(), x)));
                List<OutlineNodeDatasourceResponse> allDatasources = new ArrayList<>(uniq.values());

                // 批量渲染
                List<String> renderedList = outlineNodeDatasourceService.batchHandleDatasourceRender(htmls, allDatasources, pId);

                // 回填并收集需要更新/删除的对象
                for (int i = 0; i < oplOrder.size(); i++) {
                    String oplId = oplOrder.get(i);
                    OutlineProjectLib lib = oplIdToLib.get(oplId);
                    if (lib == null) {
                        continue;
                    }
                    // 回填渲染结果
                    lib.setOplContent(renderedList.get(i));
                    // 收集需要更新的lib
                    libsToUpdate.add(lib);
                    List<String> ids = delayIdsByOplId.get(oplId);
                    if (ValidateUtil.isNotEmpty(ids)) {
                        processedDelays.addAll(ids.stream().map(delayById::get).filter(Objects::nonNull).collect(Collectors.toList()));
                        removeIds.addAll(ids);
                    }
                }
            }

            // 批量更新大纲体系
            if (ValidateUtil.isNotEmpty(libsToUpdate)) {
                verifyAndClearTemplate(libsToUpdate, processedDelays);
                outlineProjectLibService.updateBatchById(libsToUpdate);
            }

            // 批量更新延迟渲染记录
            if (ValidateUtil.isNotEmpty(delayToUpdate)) {
                this.updateBatchById(delayToUpdate);
            }

            // 删除延迟渲染记录
            if (ValidateUtil.isNotEmpty(removeIds)) {
                this.removeBatchByIds(removeIds);
            }
        }

        // 处理新增的记录
        if (ValidateUtil.isNotEmpty(toBeAdded)) {
            Set<String> oplIds = toBeAdded.stream().map(OutlineProjectTempResponse::getOplId).collect(Collectors.toSet());
            // 所有的数据源信息
            List<OutlineProjecDatasourceResponse> datasources = outlineProjecDatasourceService.getOutlineProjecDatasourceByOplIds(new ArrayList<>(oplIds));
            // 构建 oplId → tCode → odId 列表
            Map<String, Map<String, List<String>>> oplIdToTCodeToOdIds = this.indexOdIdsByOplAndTCode(datasources);
            if (ValidateUtil.isEmpty(oplIdToTCodeToOdIds)) {
                return;
            }

            List<OutlineDelayDatasource> delsyDatasources = new ArrayList<>();
            toBeAdded.forEach(opt -> {
                List<String> odIds = Optional.ofNullable(oplIdToTCodeToOdIds.get(opt.getOplId()))
                        .map(m -> m.get(opt.getTCode()))
                        .orElse(Collections.emptyList());
                if (ValidateUtil.isEmpty(odIds)) {
                    return;
                }
                // 设置sfId，取最新的SysFile记录，如果没有也要加
                SysFile file = latestSysFileByTCode.get(opt.getTCode());
                // 多个odId，需要给每个odId创建一条记录
                odIds = odIds.stream().distinct().collect(Collectors.toList());
                odIds.forEach(odId -> {
                    OutlineDelayDatasource entity = new OutlineDelayDatasource();
                    entity.setId(GenerateUUID.getUUID());
                    entity.setOplId(opt.getOplId());
                    entity.setOdId(odId);
                    entity.setTCode(opt.getTCode());
                    entity.setPId(pId);
                    if (ValidateUtil.isNotNull(file)) {
                        entity.setSfId(file.getFileid());
                    }
                    delsyDatasources.add(entity);
                });
            });

            // 批量保存
            if (ValidateUtil.isNotEmpty(delsyDatasources)) {
                this.saveBatch(delsyDatasources);
            }
        }
    }

    /**
     * 根据opmId删除延迟渲染数据源
     *
     * @param opmIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByOpmIds(Collection<String> opmIds) {
        if (ValidateUtil.isEmpty(opmIds)) {
            return;
        }
        List<String> oplIds = baseMapper.getRemoveOplIdsByOpmIds(opmIds);
        if (ValidateUtil.isEmpty(oplIds)) {
            return;
        }
        remove(Wrappers.<OutlineDelayDatasource>lambdaQuery().in(OutlineDelayDatasource::getOplId, oplIds));
    }

    /**
     * 处理所有的延迟渲染
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAllDelayRender() {
        // 获取所有的pId
        List<OutlineProjectManage> projectManages = outlineProjectManageService.list(Wrappers.<OutlineProjectManage>lambdaQuery()
                .select(OutlineProjectManage::getId, OutlineProjectManage::getPId, OutlineProjectManage::getState)
        );
        if (ValidateUtil.isEmpty(projectManages)) {
            return;
        }
        List<String> remove = projectManages.stream().filter(pm -> pm.getState() != null && pm.getState().equals(ApprovalStatusEnum.Invalid.getIndex()))
                .map(OutlineProjectManage::getId)
                .distinct()
                .collect(Collectors.toList());
        // 删除无效的延迟渲染记录
        if (ValidateUtil.isNotEmpty(remove)) {
            this.deleteByOpmIds(remove);
        }

        List<String> pIds = projectManages.stream().filter(pm -> pm.getState() != null && !pm.getState().equals(ApprovalStatusEnum.Invalid.getIndex()))
                .map(OutlineProjectManage::getPId)
                .distinct()
                .collect(Collectors.toList());

        // 在延迟渲染表中的pId
        List<OutlineDelayDatasource> delayDatasources = this.list(Wrappers.<OutlineDelayDatasource>lambdaQuery()
                .in(OutlineDelayDatasource::getPId, pIds)
                .select(OutlineDelayDatasource::getPId)
        );
        if (ValidateUtil.isEmpty(delayDatasources)) {
            return;
        }
        // 获取所有的pId
        pIds = delayDatasources.stream().map(OutlineDelayDatasource::getPId).distinct().collect(Collectors.toList());
        for (String pId : pIds) {
            // 处理延迟渲染
            handleDelayRender(Collections.emptyList(), pId);
        }
    }

    /**
     * 建立 Map<oplId, Map<tCode, List<odId>>> 索引
     *
     * @param datasources
     * @return
     */
    private Map<String, Map<String, List<String>>> indexOdIdsByOplAndTCode(List<OutlineProjecDatasourceResponse> datasources) {
        Map<String, Map<String, List<String>>> index = new HashMap<>();

        if (datasources == null || datasources.isEmpty()) {
            return index;
        }

        for (OutlineProjecDatasourceResponse ds : datasources) {
            String oplId = ds.getOplId();
            String odId = ds.getOdId();
            List<String> tCodes = ds.getTCodeList();
            if (oplId == null || odId == null || tCodes == null || tCodes.isEmpty()) {
                continue;
            }
            Map<String, List<String>> tcodeMap =
                    index.computeIfAbsent(oplId, k -> new HashMap<>());
            for (String tCode : tCodes) {
                if (tCode == null) {
                    continue;
                }
                List<String> odList = tcodeMap.computeIfAbsent(tCode, k -> new ArrayList<>());
                // 去重
                if (!odList.contains(odId)) {
                    odList.add(odId);
                }
            }
        }
        return index;
    }

    /**
     * 验证并清除模板
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyAndClearTemplateByIds(List<String> ids) {
        if (ValidateUtil.isEmpty(ids)) {
            return;
        }
        // 传入的是即将删除的延迟渲染记录ID列表，所以说明这些记录所对应的目录下的指定的数据源已经渲染完毕
        // 所以需要验证数据源对应的指定别名的模板是否还存在于该目录下，如果还存在模板但是渲染已经结束，则需要清除模板

        // 查出所有的延迟渲染记录
        List<OutlineDelayDatasource> list = lambdaQuery()
                .in(OutlineDelayDatasource::getId, ids)
                .select(OutlineDelayDatasource::getOplId, OutlineDelayDatasource::getOdId)
                .list();
        if (ValidateUtil.isEmpty(list)) {
            return;
        }
        Set<String> allOplIds = list.stream().map(OutlineDelayDatasource::getOplId).collect(Collectors.toSet());
        List<OutlineProjectLib> projectLibs = outlineProjectLibService.list(Wrappers.<OutlineProjectLib>lambdaQuery()
                .in(OutlineProjectLib::getId, allOplIds)
                .isNotNull(OutlineProjectLib::getOplContent)
                .select(OutlineProjectLib::getId, OutlineProjectLib::getOplContent)
        );
        verifyAndClearTemplate(projectLibs, list);
        if (ValidateUtil.isNotEmpty(projectLibs)) {
            outlineProjectLibService.updateBatchById(projectLibs);
        }
    }

    /**
     * 验证并清除模板
     *
     * @param libsToUpdate
     * @param processedDelays
     */
    public void verifyAndClearTemplate(List<OutlineProjectLib> libsToUpdate, List<OutlineDelayDatasource> processedDelays) {
        if (ValidateUtil.isEmpty(libsToUpdate) || ValidateUtil.isEmpty(processedDelays)) {
            return;
        }
        Map<String, List<String>> oplIdToOdIds = new HashMap<>();
        processedDelays.forEach(d -> oplIdToOdIds.computeIfAbsent(d.getOplId(), k -> new ArrayList<>()).add(d.getOdId()));
        Set<String> allOdIds = processedDelays.stream().map(OutlineDelayDatasource::getOdId).collect(Collectors.toSet());
        List<OutlineDatasource> odIdToAliasByOdIds = baseMapper.getOdIdToAliasByOdIds(allOdIds);
        // 构建 odId → alias
        Map<String, String> odIdToAlias = odIdToAliasByOdIds.stream()
                .filter(od -> od.getId() != null && od.getOdAlias() != null)
                .collect(Collectors.toMap(OutlineDatasource::getId, OutlineDatasource::getOdAlias, (a, b) -> a));

        // 构建 oplId → OutlineProjectLib
        Map<String, OutlineProjectLib> oplIdToLib = libsToUpdate.stream()
                .collect(Collectors.toMap(OutlineProjectLib::getId, Function.identity(), (a, b) -> a));
        // 每个目录分别处理
        oplIdToOdIds.forEach((oplId, odIds) -> {
            // 获取目录信息
            OutlineProjectLib lib = oplIdToLib.get(oplId);
            if (ValidateUtil.isNull(lib) || ValidateUtil.isEmpty(lib.getOplContent())) {
                return;
            }
            String content = lib.getOplContent();
            // 获取别名列表
            List<String> aliases = odIds.stream()
                    .map(odIdToAlias::get)
                    .filter(Objects::nonNull)
                    .distinct().collect(Collectors.toList());
            if (ValidateUtil.isEmpty(aliases)) {
                return;
            }
            // 清除模板
            content = FreemarkerUtils.cleanByAliases(content, aliases);
            lib.setOplContent(content);
        });
    }
}
