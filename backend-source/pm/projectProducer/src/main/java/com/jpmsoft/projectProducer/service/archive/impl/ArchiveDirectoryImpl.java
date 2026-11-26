package com.jpmsoft.projectProducer.service.archive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryRequest;
import com.jpmsoft.common.model.response.Archive.ArchiveDirectoryResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.archive.ArchiveDirectoryMapper;
import com.jpmsoft.projectProducer.pojo.dto.archive.DirectoryRoleDTO;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveDirectory;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.archive.IArchiveDirectoryGroupService;
import com.jpmsoft.projectProducer.service.archive.IArchiveDirectoryService;
import com.jpmsoft.projectProducer.service.archive.IArchiveFiletemplateService;
import com.jpmsoft.projectProducer.service.archive.IArchiveService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArchiveDirectoryImpl extends ServiceImpl<ArchiveDirectoryMapper, ArchiveDirectory> implements IArchiveDirectoryService {


    @Resource
    private IArchiveFiletemplateService archiveFiletemplateService;

    @Resource
    private IArchiveService archiveService;

    @Resource
    private IArchiveDirectoryGroupService archiveDirectoryGroupService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateArchiveDirectory(ArchiveDirectoryRequest request) {
        ArchiveDirectory entity = new ArchiveDirectory();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAdParentId()))
            entity.setAdParentId("0");
        QueryWrapper<ArchiveDirectory> wrapper = new QueryWrapper<>();
        wrapper.eq("Ad_Parent_Id", entity.getAdParentId());
        List<ArchiveDirectory> sameAlls = baseMapper.selectList(wrapper);
        if (sameAlls != null && sameAlls.size() > 0) {
            List<ArchiveDirectory> node = sameAlls.stream().filter(n -> n.getAdCode() != null && n.getAdCode().equals(request.getAdCode())).collect(Collectors.toList());
            if (node != null && node.size() > 0) {
                if (!ValidateUtil.isEmpty(request.getAdId()))
                    node = node.stream().filter(n -> !n.getAdId().equals(request.getAdId())).collect(Collectors.toList());
                if (node != null && node.size() > 0)
                    return BaseResponse.fail(ResMsg.recordExist);
            }
        }
        archiveFiletemplateService.checkAlreadyInDiDirectory(request.getArchiveFiletemplates(), entity.getAdId());
        if (ValidateUtil.isEmpty(entity.getAdId())) {
            entity.setAdCreateTime(new Date());
            entity.setAdId(GenerateUUID.getUUID());
            entity.setAdOrder(sameAlls.size() + 1);
            baseMapper.insert(entity);
        } else {
            ArchiveDirectory model = baseMapper.selectById(request.getAdId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setAdCreateTime(model.getAdCreateTime());
            entity.setAdCreateUid(model.getAdCreateUid());
            entity.setAdCreateUname(model.getAdCreateUname());
            entity.setAdOrder(model.getAdOrder());
            baseMapper.updateById(entity);
        }
        archiveFiletemplateService.createOrUpdateArchiveFiletemplate(request.getArchiveFiletemplates(), entity.getAdId());
        return BaseResponse.ok(entity.getAdId());
    }

    @Override
    public BaseResponse getArchiveDirectoryList(ArchiveDirectoryRequest request, LogUserInfoVo user) {
        List<ArchiveDirectoryResponse> alls = baseMapper.getAllArchiveDirectory(request);
        List<ArchiveDirectoryResponse> data = new ArrayList<>();
        if (!CollectionUtils.isEmpty(alls)) {
            List<DirectoryRoleDTO> drs = archiveDirectoryGroupService.getUserDirectoryPower(user);
            if (!CollectionUtils.isEmpty(drs)) {
                alls = alls.stream().peek(ad -> {
                    List<DirectoryRoleDTO> dr = drs.stream().filter(n -> n.getAdId().equals(ad.getAdId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dr)) ad.setPowerCodes(dr.stream().findFirst().get().getPowerCodes());
                }).collect(Collectors.toList());
            }
            data = alls.stream().filter(n -> n.getAdParentId().equals("0")).collect(Collectors.toList());
            setChildNodes(alls, data);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse upOrDownArchiveDirectory(String adId, String action, String teId) {
        ArchiveDirectory entity = baseMapper.selectById(adId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<ArchiveDirectory> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("Ad_Parent_Id", entity.getAdParentId());
        wrapper.orderByAsc("Ad_Order");
        List<ArchiveDirectory> sameLevels = baseMapper.selectList(wrapper);
        if (sameLevels != null && sameLevels.size() > 1) {
            ArchiveDirectory entity1;
            int index = sameLevels.indexOf(entity);
            if (action.equalsIgnoreCase("UP")) {
                if (index == 0)
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = sameLevels.get(index - 1);
            } else if (action.equalsIgnoreCase("DOWN")) {
                if (index == sameLevels.size())
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = sameLevels.get(index + 1);
            } else
                return BaseResponse.fail(ResMsg.actError);
            Integer sort = entity.getAdOrder();
            entity.setAdOrder(entity1.getAdOrder());
            entity1.setAdOrder(sort);
            updateBatchById(Arrays.asList(entity, entity1));
        } else
            return BaseResponse.fail(ResMsg.actError);
        return BaseResponse.ok();
    }

    /**
     * 查询指定目录的直接子级或根目录，并累计项目内各级归档及未归档数量。
     * 本级数量由 SQL 按目录批量聚合，子级累计及目录权限在内存中处理。
     *
     * @param adId    父目录 ID，为空时返回根目录
     * @param pId     统计项目 ID，为空时不统计文件数量
     * @param adType  目录类型，为空时默认项目目录
     * @param adState 目录状态
     * @param user    当前用户，用于补充目录权限
     * @return 当前层目录、子树文件数量及目录权限
     */
    @Override
    public BaseResponse getArchiveDirectoryByadIdOrpId(String adId, String pId, String adType,String adState, LogUserInfoVo user) {
        // 查询全部目录（含本级归档/未归档数量），供后续按“上级=所有下级+本级”递归累加
        List<ArchiveDirectoryResponse> alls = baseMapper.getArchiveDirectoryByadIdOrpId(null, pId, adType, adState);
        List<ArchiveDirectoryResponse> data = new ArrayList<>();
        if (!CollectionUtils.isEmpty(alls)) {
            // 按父目录ID分组，构建子树关系
            Map<String, List<ArchiveDirectoryResponse>> childrenMap = alls.stream()
                    .filter(n -> ValidateUtil.isNotEmpty(n.getAdParentId()))
                    .collect(Collectors.groupingBy(ArchiveDirectoryResponse::getAdParentId));
            // 结果集：指定 adId 的直接子级；adId 为空时取根级目录
            if (ValidateUtil.isNotEmpty(adId)) {
                data = alls.stream().filter(n -> adId.equals(n.getAdParentId())).collect(Collectors.toList());
            } else {
                data = alls.stream().filter(n -> ValidateUtil.isEmpty(n.getAdParentId()) || "0".equals(n.getAdParentId())).collect(Collectors.toList());
            }
            // 上级目录归档文件数量 = 所有下级目录归档文件数量 + 本级归档文件数量（仅按项目统计时有数量）
            if (ValidateUtil.isNotEmpty(pId)) {
                for (ArchiveDirectoryResponse node : data) {
                    sumArchiveCount(node, childrenMap);
                }
            }
        }
        if (!CollectionUtils.isEmpty(data)) {
            List<DirectoryRoleDTO> drs = archiveDirectoryGroupService.getUserDirectoryPower(user);
            if (!CollectionUtils.isEmpty(drs)) {
                Map<String, DirectoryRoleDTO> roleByDirectory = new HashMap<>();
                for (DirectoryRoleDTO role : drs) {
                    roleByDirectory.putIfAbsent(role.getAdId(), role);
                }
                for (ArchiveDirectoryResponse directory : data) {
                    DirectoryRoleDTO role = roleByDirectory.get(directory.getAdId());
                    if (role != null) {
                        directory.setPowerCodes(role.getPowerCodes());
                    }
                }
            }
        }
        return BaseResponse.ok(data);
    }

    /**
     * 递归累计：上级归档文件数量 = 所有下级归档文件数量 + 本级归档文件数量
     */
    private void sumArchiveCount(ArchiveDirectoryResponse node, Map<String, List<ArchiveDirectoryResponse>> childrenMap) {
        int archived = node.getArchived() == null ? 0 : node.getArchived();
        int notArchived = node.getNotArchived() == null ? 0 : node.getNotArchived();
        List<ArchiveDirectoryResponse> children = childrenMap.get(node.getAdId());
        if (!CollectionUtils.isEmpty(children)) {
            for (ArchiveDirectoryResponse child : children) {
                sumArchiveCount(child, childrenMap);
                archived += child.getArchived() == null ? 0 : child.getArchived();
                notArchived += child.getNotArchived() == null ? 0 : child.getNotArchived();
            }
        }
        node.setArchived(archived);
        node.setNotArchived(notArchived);
    }

    @Override
    public BaseResponse getArchiveDirectoryTreeByPId(String pId, String adType, LogUserInfoVo user) {
        List<ArchiveDirectoryResponse> data = baseMapper.getArchiveDirectoryByadIdOrpId(null, pId, adType,null);
        if (!CollectionUtils.isEmpty(data)) {
            List<DirectoryRoleDTO> drs = archiveDirectoryGroupService.getUserDirectoryPower(user);
            if (!CollectionUtils.isEmpty(drs)) {
                data = data.stream().peek(ad -> {
                    List<DirectoryRoleDTO> dr = drs.stream().filter(n -> n.getAdId().equals(ad.getAdId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dr)) ad.setPowerCodes(dr.stream().findFirst().get().getPowerCodes());
                }).collect(Collectors.toList());
            }
            //最上级
            List<ArchiveDirectoryResponse> roots = data.stream().filter(f -> ValidateUtil.isEmpty(f.getAdParentId()) || f.getAdParentId().equals("0")).collect(Collectors.toList());
            setChildNodes(data, roots);
            for (ArchiveDirectoryResponse root : roots)
                root.calculateAndSetTotalArchivedAndNotArchived();
            return BaseResponse.ok(roots);
        }
        return BaseResponse.ok(data);
    }


    /**
     * 查询目录树，按本级归档数量和名称筛选并补齐祖先目录。
     * 返回数量为筛选后目录树中本级与所有保留子级的归档数量之和。
     *
     * @param request 项目、租户、目录类型及筛选条件，filterUnArchive 必填
     * @return 按目录顺序排列的目录树；无匹配目录时返回空列表
     */
    @Override
    public BaseResponse getAllArchiveDirectoryByPid(ArchiveDirectoryRequest request) {
        if (request == null || request.getFilterUnArchive() == null) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<ArchiveDirectoryResponse> models = baseMapper.getAllArchiveDirectoryByPid(request);
        if (CollectionUtils.isEmpty(models)) {
            return BaseResponse.ok(new ArrayList<ArchiveDirectoryResponse>());
        }
        boolean filterArchived = Boolean.TRUE.equals(request.getFilterUnArchive());
        String adName = request.getAdName();
        boolean filterName = !ValidateUtil.isEmpty(adName);
        List<ArchiveDirectoryResponse> datas;
        if (!filterArchived && !filterName) {
            datas = models;
        } else {
            Map<String, ArchiveDirectoryResponse> directoryMap = new HashMap<>();
            for (ArchiveDirectoryResponse model : models) {
                directoryMap.putIfAbsent(model.getAdId(), model);
            }
            datas = new ArrayList<>();
            Set<String> selectedIds = new HashSet<>();
            for (ArchiveDirectoryResponse model : models) {
                if (filterArchived && (model.getArchived() == null || model.getArchived() <= 0)) {
                    continue;
                }
                if (filterName && (model.getAdName() == null || !model.getAdName().contains(adName))) {
                    continue;
                }
                addDirectoryAndParents(model, directoryMap, datas, selectedIds);
            }
        }
        List<ArchiveDirectoryResponse> res = buildArchiveDirectoryTree(datas);
        calculateArchivedSum(res);
        return BaseResponse.ok(res);
    }

    @Override
    public BaseResponse refershDirectoryLink(String teId) {
        QueryWrapper<ArchiveDirectory> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        List<ArchiveDirectory> allDirs = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(allDirs)) {
            List<ArchiveDirectory> ups = new ArrayList<>();
            for (ArchiveDirectory dir : allDirs) {
                String adLink = dir.getAdName();
                if (ValidateUtil.isNotEmpty(dir.getAdParentId()) && !dir.getAdParentId().equals("0"))
                    adLink = setLinkName(adLink, allDirs, dir.getAdParentId());
                dir.setAdLink(adLink);
                ups.add(dir);
            }
            if (!CollectionUtils.isEmpty(ups)) updateBatchById(ups);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse delDirectoryByAdId(String adId, String teId) {
        ArchiveDirectory entity = baseMapper.selectById(adId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<ArchiveDirectory> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.orderByAsc("Ad_Order");
        List<ArchiveDirectory> allDirs = baseMapper.selectList(wrapper);
        //涉及的目录ID（当前ID和下级ID）
        List<String> allIds = new ArrayList<>();
        allIds.add(adId);
        getAllChildId(allDirs, allIds, adId);
        //存在归档模板 归档信息 无法删除
        if (archiveFiletemplateService.directoryHavingTemplate(allIds) ||
                archiveService.directoryHavingArchiveFile(allIds))
            return BaseResponse.fail(ResMsg.relationUnToDelArchiveDir);
        //region 删除 组和用户、目录
        //删除组和用户
        archiveDirectoryGroupService.delArchiveDirectoryGroupByAdIds(allIds);
        //删除目录
        QueryWrapper<ArchiveDirectory> dirWrapper = new QueryWrapper<>();
        dirWrapper.in("Ad_Id", allIds);
        baseMapper.delete(dirWrapper);
        //endregion
        //region 删除的同级目录 序号进1
        //删除目录的同级目录（排序号大于当前要删除的目录）
        if (ValidateUtil.isNotNull(entity.getAdOrder()) && ValidateUtil.isNotEmpty(entity.getAdParentId())) {
            List<ArchiveDirectory> sameLevels = allDirs.stream().filter(n -> ValidateUtil.isNotEmpty(n.getAdParentId())
                            && ValidateUtil.isNotNull(n.getAdOrder()) && n.getAdParentId().equals(entity.getAdParentId()) && n.getAdOrder() > entity.getAdOrder())
                    .map(dir -> {
                        dir.setAdOrder(dir.getAdOrder() - 1);
                        return dir;
                    }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(sameLevels)) updateBatchById(sameLevels);
        }
        //endregion
        return BaseResponse.ok();
    }

    private void getAllChildId(List<ArchiveDirectory> allDirs, List<String> allIds, String parentId) {
        if (ValidateUtil.isNotEmpty(parentId)) {
            List<ArchiveDirectory> childs = allDirs.stream().filter(n -> ValidateUtil.isNotEmpty(n.getAdParentId()) && n.getAdParentId().equals(parentId)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(childs)) {
                for (ArchiveDirectory child : childs) {
                    allIds.add(child.getAdId());
                    getAllChildId(allDirs, allIds, child.getAdId());
                }
            }
        }
    }


    private String setLinkName(String adLink, List<ArchiveDirectory> allDirs, String id) {
        List<ArchiveDirectory> dirs = allDirs.stream().filter(n -> n.getAdId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(dirs)) {
            ArchiveDirectory dir = dirs.stream().findFirst().get();
            adLink = dir.getAdName() + "/" + adLink;
            if (ValidateUtil.isNotEmpty(dir.getAdParentId()) && !dir.getAdParentId().equals("0"))
                adLink = setLinkName(adLink, allDirs, dir.getAdParentId());
        }
        return adLink;
    }

    /**
     * 通过目录索引补齐当前目录及祖先，遇到已收集目录、根目录或缺失父目录时停止。
     *
     * @param node 命中的目录
     * @param directoryMap 全部目录的 ID 索引
     * @param datas 收集后的目录，保留首次加入顺序
     * @param selectedIds 已收集目录 ID，同时用于防止循环追溯
     */
    private void addDirectoryAndParents(ArchiveDirectoryResponse node,
                                        Map<String, ArchiveDirectoryResponse> directoryMap,
                                        List<ArchiveDirectoryResponse> datas, Set<String> selectedIds) {
        while (node != null && selectedIds.add(node.getAdId())) {
            datas.add(node);
            String parentId = node.getAdParentId();
            if (ValidateUtil.isEmpty(parentId) || "0".equals(parentId)) {
                break;
            }
            node = directoryMap.get(parentId);
        }
    }

    /**
     * 按父目录分组构树，避免为每个节点重复扫描全部目录。
     * 各层按 adOrder 升序排列，空序号排末尾，同序号保留输入顺序。
     *
     * @param directories 筛选并补齐祖先后的目录
     * @return 父目录 ID 为 0 的根目录列表
     */
    private List<ArchiveDirectoryResponse> buildArchiveDirectoryTree(List<ArchiveDirectoryResponse> directories) {
        Map<String, List<ArchiveDirectoryResponse>> childrenMap = new HashMap<>();
        for (ArchiveDirectoryResponse directory : directories) {
            childrenMap.computeIfAbsent(directory.getAdParentId(), key -> new ArrayList<>()).add(directory);
        }
        Comparator<ArchiveDirectoryResponse> orderComparator = Comparator.comparing(
                ArchiveDirectoryResponse::getAdOrder, Comparator.nullsLast(Comparator.naturalOrder()));
        for (List<ArchiveDirectoryResponse> siblings : childrenMap.values()) {
            siblings.sort(orderComparator);
        }
        for (ArchiveDirectoryResponse directory : directories) {
            List<ArchiveDirectoryResponse> children = childrenMap.get(directory.getAdId());
            directory.setChildNodes(children == null ? new ArrayList<>() : children);
        }
        List<ArchiveDirectoryResponse> roots = childrenMap.get("0");
        return roots == null ? new ArrayList<>() : roots;
    }


    private void setChildNodes(List<ArchiveDirectoryResponse> alls, List<ArchiveDirectoryResponse> data) {
        data.forEach(node -> {
            List<ArchiveDirectoryResponse> chils = alls.stream()
                    .filter(n -> n.getAdParentId().equals(node.getAdId()))
                    .sorted(Comparator.comparing(ArchiveDirectoryResponse::getAdOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
            setChildNodes(alls, chils);
            node.setChildNodes(chils);
        });
    }

    /**
     * 递归计算每个目录的归档文件数（包含所有子节点）
     */
    private int calculateArchivedSum(List<ArchiveDirectoryResponse> nodes) {
        if (ValidateUtil.isEmpty(nodes)) {
            return 0;
        }

        int total = 0;
        for (ArchiveDirectoryResponse node : nodes) {
            int childSum = 0;
            if (!ValidateUtil.isEmpty(node.getChildNodes())) {
                // 递归求子节点总数
                childSum = calculateArchivedSum(node.getChildNodes());
            }
            // 当前节点的总数 = 自身 + 子节点
            int selfArchived = node.getArchived() == null ? 0 : node.getArchived();
            int totalArchived = selfArchived + childSum;
            node.setArchived(totalArchived);
            total += totalArchived;
        }
        return total;
    }
}
