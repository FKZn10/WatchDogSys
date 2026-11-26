package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysRegionMapper;
import com.jpmsoft.userProducer.pojo.dto.CityJsonDTO;
import com.jpmsoft.userProducer.pojo.dto.SysRegionDTO;
import com.jpmsoft.userProducer.pojo.dto.SysRegionDTONew;
import com.jpmsoft.userProducer.pojo.entity.SysRegion;
import com.jpmsoft.userProducer.service.ISysRegionService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/27 9:41
 */
@Service
public class SysRegionImpl extends ServiceImpl<SysRegionMapper, SysRegion> implements ISysRegionService {

    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse createOrUpdateSysRegion(SysRegionDTO request) {
        if (codeExist(request.getSrCode(), request.getId()))
            return BaseResponse.ok(BaseMsg.infoIsExist);
        SysRegion entity = new SysRegion();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(request.getId())) {
            if (ValidateUtil.isNotEmpty(entity.getSrParentCode()) && ValidateUtil.isNull(request.getSrLevel()))
                entity.setSrLevel(level(request.getSrParentCode()));
            baseMapper.updateById(entity);
        } else {
            if (ValidateUtil.isNull(request.getSrLevel()))
                entity.setSrLevel(level(request.getSrParentCode()));
            entity.setId(GenerateUUID.getUUID());
            entity.setSrStatus(1);
            baseMapper.insert(entity);
        }
        redisUtils.delete("sysRegionTree", "sysRegionAllData");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSysRegionTree() {
        return BaseResponse.ok(getSysRegionTreeList());
    }

    public List<SysRegionDTO> getSysRegionTreeList() {
        Object regionJson = redisUtils.get("sysRegionTree");
        List<SysRegionDTO> data;
        if (regionJson != null) {
            data = JacksonUtils.parseObject(regionJson.toString(), new TypeReference<List<SysRegionDTO>>() {
            });
        } else {
            data = new ArrayList<>();
            List<SysRegionDTO> allData = getAll();
            if (CollectionUtils.isEmpty(allData)) return null;
            //排除作废数据
            List<SysRegionDTO> all = allData.stream().filter(f -> ValidateUtil.isNotNull(f.getSrStatus()) && f.getSrStatus() == 1).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(all)) return null;
            List<SysRegionDTO> roots = all.stream().filter(n -> ValidateUtil.isNotNull(n.getSrLevel()) && n.getSrLevel() == 1).collect(Collectors.toList());
            roots.forEach(vueTree -> {
                List<SysRegionDTO> vueTrees = new ArrayList<>();
                List<SysRegionDTO> newList = all.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSrParentCode()) && n.getSrParentCode().equals(vueTree.getSrCode())).collect(Collectors.toList());
                if (newList.size() > 0) getChildNodes(vueTrees, newList, all);
                vueTree.setChildren(vueTrees);
                data.add(vueTree);
            });
            redisUtils.set("sysRegionTree", JacksonUtils.toJSONString(data));
        }
        return data;
    }


    @Override
    public BaseResponse getSysRegionTreeNew() {
        return BaseResponse.ok(getSysRegionTreeListNew());
    }

    public List<SysRegionDTONew> getSysRegionTreeListNew() {
        Object regionJson = redisUtils.get("sysRegionTreeNew");
        List<SysRegionDTONew> data;
        if (regionJson != null) {
            data = JacksonUtils.parseObject(regionJson.toString(), new TypeReference<List<SysRegionDTONew>>() {
            });
        } else {
            data = new ArrayList<>();
            List<SysRegionDTONew> allData = getAllNew();
            if (CollectionUtils.isEmpty(allData)) {
                return null;
            }
            //排除作废数据
            List<SysRegionDTONew> all = allData.stream().filter(f -> ValidateUtil.isNotNull(f.getSrStatus()) && f.getSrStatus() == 1).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(all)) {
                return null;
            }
            List<SysRegionDTONew> roots = all.stream().filter(n -> ValidateUtil.isNotNull(n.getSrLevel()) && n.getSrLevel() == 1).collect(Collectors.toList());
            roots.forEach(vueTree -> {
                List<SysRegionDTONew> vueTrees = new ArrayList<>();
                List<SysRegionDTONew> newList = all.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSrParentCode()) && n.getSrParentCode().equals(vueTree.getSrCode())).collect(Collectors.toList());
                if (newList.size() > 0) {
                    getChildNodesNew(vueTrees, newList, all);
                }
                vueTree.setChildren(vueTrees);
                data.add(vueTree);
            });
            redisUtils.set("sysRegionTreeNew", JacksonUtils.toJSONString(data));
        }
        return data;
    }

    @Override
    public BaseResponse getSysRegionByParentCode(String parentCode, Boolean exclude) {
        List<SysRegionDTO> data = getAll();
        if (CollectionUtils.isEmpty(data)) return BaseResponse.ok();
        if (exclude)//排除作废数据
            data = data.stream().filter(f -> ValidateUtil.isNotNull(f.getSrStatus()) && f.getSrStatus() == 1).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(data)) {
            List<SysRegionDTO> kd = new ArrayList<>();
            return BaseResponse.ok(data.stream().filter(f -> f.getSrParentCode().equals(parentCode)).peek(d -> d.setChildren(kd)).collect(Collectors.toList()));
        } else return BaseResponse.ok();
    }

    private void getChildNodes(List<SysRegionDTO> treeList, List<SysRegionDTO> filterList, List<SysRegionDTO> list) {
        filterList.forEach(vueTree -> {
            List<SysRegionDTO> vueTrees = new ArrayList<>();
            List<SysRegionDTO> newList = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSrParentCode()) && n.getSrParentCode().equals(vueTree.getSrCode())).collect(Collectors.toList());
            if (newList.size() > 0) getChildNodes(vueTrees, newList, list);
            vueTree.setChildren(vueTrees);
            treeList.add(vueTree);
        });
    }
    private void getChildNodesNew(List<SysRegionDTONew> treeList, List<SysRegionDTONew> filterList, List<SysRegionDTONew> list) {
        filterList.forEach(vueTree -> {
            List<SysRegionDTONew> vueTrees = new ArrayList<>();
            List<SysRegionDTONew> newList = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSrParentCode()) && n.getSrParentCode().equals(vueTree.getSrCode())).collect(Collectors.toList());
            if (newList.size() > 0) {
                getChildNodesNew(vueTrees, newList, list);
            }
            vueTree.setChildren(vueTrees);
            treeList.add(vueTree);
        });
    }

    @Override
    public BaseResponse getSysRegionById(String id) {
        SysRegion d = baseMapper.selectById(id);
        if (d == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        SysRegionDTO r = new SysRegionDTO();
        BeanUtils.copyProperties(d, r);
        return BaseResponse.ok(r);
    }

    @Override
    public BaseResponse deleteRegionById(String id) {
        SysRegion d = baseMapper.selectById(id);
        if (d == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
        List<SysRegion> res = baseMapper.selectList(wrapper);
        List<SysRegion> delete = new ArrayList<>();
        delete.add(d);
        List<SysRegion> cs = res.stream().filter(f -> ValidateUtil.isNotEmpty(f.getSrParentCode()) && f.getSrParentCode().equalsIgnoreCase(d.getSrCode())).collect(Collectors.toList());
        delete.addAll(cs);
        deleteChild(res, delete, cs);
        baseMapper.deleteBatchIds(delete);
        redisUtils.delete("sysRegionTree", "sysRegionAllData");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse importRegion(MultipartFile file) {
        if (!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase(Locale.ENGLISH).endsWith(".json"))
            return BaseResponse.fail("仅仅支持JSON文件上传!");
        try {
            String str = new String(file.getBytes());
            List<CityJsonDTO> json = JacksonUtils.parseArray(str, CityJsonDTO.class);
            List<String> codes = baseMapper.codes();
            List<SysRegion> entities = new ArrayList<>();
            json.forEach(d -> {
                if (!codes.contains(d.getCode())) {
                    SysRegion sr = new SysRegion();
                    sr.setId(GenerateUUID.getUUID());
                    sr.setSrCity(d.getCity());
                    sr.setSrCode(d.getCode());
                    sr.setSrJp(d.getJianpin());
                    sr.setSrLevel(d.getLevel());
                    sr.setSrDistrict(d.getDistrict());
                    sr.setSrLat(d.getLat());
                    sr.setSrLng(d.getLng());
                    sr.setSrZip(d.getZip());
                    sr.setSrTown(d.getTown());
                    sr.setSrTel(d.getTel());
                    sr.setSrQp(d.getPinyin());
                    sr.setSrProvince(d.getProvince());
                    sr.setSrParentCode(d.getParentcode());
                    sr.setSrName(d.getName());
                    sr.setSrFirstChar(d.getFirstchar());
                    sr.setSrStatus(1);
                    entities.add(sr);
                    codes.add(d.getCode());
                }
            });
            if (!CollectionUtils.isEmpty(entities)) {
                //saveBath效率不如自写批量sql
                if (entities.size() <= 1000)
                    baseMapper.bathInsert(entities);
                else {
                    boolean refresh = true;
                    List<SysRegion> adds = null;
                    for (int i = 0; i < entities.size(); i++) {
                        if (refresh) {
                            adds = new ArrayList<>();
                            refresh = false;
                        }
                        adds.add(entities.get(i));
                        if (adds.size() == 1000) {
                            baseMapper.bathInsert(adds);
                            refresh = true;
                        }
                    }
                    if (!CollectionUtils.isEmpty(adds))
                        baseMapper.bathInsert(adds);
                }
            }
            redisUtils.delete("sysRegionTree", "sysRegionAllData");
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("JSON导入地区数据失败:" + ex.getMessage(),ex);
            return BaseResponse.fail("导入失败!");
        }
        return BaseResponse.ok();
    }

    private boolean codeExist(String code, String id) {
        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(id))
            wrapper.ne("id", id);
        wrapper.eq("Sr_Code", code);
        return baseMapper.exists(wrapper);
    }


    private int level(String parentCode) {
        if (ValidateUtil.isEmpty(parentCode)) return 1;
        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
        wrapper.eq("Sr_Code", parentCode);
        SysRegion sr = baseMapper.selectOne(wrapper);
        if (sr == null) throw new JPMException(BaseMsg.selectSuperiorError);
        return sr.getSrLevel() + 1;
    }


    private void deleteChild(List<SysRegion> res, List<SysRegion> delete, List<SysRegion> cs) {
        if (!CollectionUtils.isEmpty(cs)) {
            cs.forEach(d -> {
                List<SysRegion> rms = res.stream().filter(f -> ValidateUtil.isNotEmpty(f.getSrParentCode()) && f.getSrParentCode().equalsIgnoreCase(d.getSrCode())).collect(Collectors.toList());
                delete.addAll(cs);
                deleteChild(res, delete, rms);
            });
        }
    }

    private List<SysRegionDTO> getAll() {
        Object regionJson = redisUtils.get("sysRegionAllData");
        if (regionJson != null) {
            return JacksonUtils.parseObject(regionJson.toString(), new TypeReference<List<SysRegionDTO>>() {
            });
        } else {
            QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
            List<SysRegion> res = baseMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(res)) return null;
            List<SysRegionDTO> all = res.stream().map(d -> {
                SysRegionDTO r = new SysRegionDTO();
                BeanUtils.copyProperties(d, r);
                r.setKey(d.getSrCode());
                r.setTitle(d.getSrName());
                r.setValue(d.getSrCode());
                return r;
            }).collect(Collectors.toList());
            redisUtils.set("sysRegionAllData", JacksonUtils.toJSONString(all));
            return all;
        }
    }

    private List<SysRegionDTONew> getAllNew() {
        Object regionJson = redisUtils.get("sysRegionAllDataNew");
        if (regionJson != null) {
            return JacksonUtils.parseObject(regionJson.toString(), new TypeReference<List<SysRegionDTONew>>() {
            });
        } else {
            QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
            List<SysRegion> res = baseMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(res)) {
                return null;
            }
            List<SysRegionDTONew> all = res.stream().map(d -> {
                SysRegionDTONew r = new SysRegionDTONew();
                BeanUtils.copyProperties(d, r);
                return r;
            }).collect(Collectors.toList());
            redisUtils.set("sysRegionAllDataNew", JacksonUtils.toJSONString(all));
            return all;
        }
    }
}
