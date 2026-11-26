package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysDictionaryMapper;
import com.jpmsoft.userProducer.pojo.dto.DictionaryDto;
import com.jpmsoft.userProducer.pojo.dto.SelectDto;
import com.jpmsoft.userProducer.pojo.entity.SysDictionary;
import com.jpmsoft.userProducer.pojo.vo.VueTreeSimpleVo;
import com.jpmsoft.userProducer.pojo.vo.VueTreeVo;
import com.jpmsoft.userProducer.service.ISysDictionaryService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysDictionaryImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements ISysDictionaryService {

    @Resource
    private RedisUtils redisUtils;


    @Override
    public BaseResponse getDictionaryTreeList(String dName, String dCode, String token) {
        String teId = redisUtils.getTeId(token);
        String treeKey = redisUtils.tenantKey("dictionaryTreeList", teId);
        Object dictionaryTreeList = redisUtils.get(treeKey);
        if (dictionaryTreeList != null) {
            List<VueTreeVo> returnData = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<VueTreeVo>>() {
            });
            // 筛选
            List<VueTreeVo> vueTreeVos = this.dictionaryTreeListFilter(returnData, dName, dCode);
            return BaseResponse.ok(vueTreeVos);
        }
        List<SysDictionary> list = getAll(teId);
        redisUtils.set(redisUtils.tenantKey("allDictionary", teId), JacksonUtils.toJSONString(list));
        List<VueTreeVo> treeList = new ArrayList<>();
        List<SysDictionary> roots = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && "0".equals(n.getdParentid())).collect(Collectors.toList());
        this.GetDictionaryTreeChildren(treeList, roots, list);
        redisUtils.set(treeKey, JacksonUtils.toJSONString(treeList));
        // 筛选
        List<VueTreeVo> vueTreeVos = this.dictionaryTreeListFilter(treeList, dName, dCode);
        return BaseResponse.ok(vueTreeVos);
    }

    /**
     * 过滤树形结构
     * <p>
     * 根据传入的dName和dCode进行过滤，返回包含匹配节点及其全部父级节点的树形结构
     * @param treeList
     * @param dName
     * @param dCode
     * @return
     */
    public List<VueTreeVo> dictionaryTreeListFilter(List<VueTreeVo> treeList, String dName, String dCode) {
        if (CollectionUtils.isEmpty(treeList)) {
            return new ArrayList<>();
        }
        if (ValidateUtil.isEmpty(dName) && ValidateUtil.isEmpty(dCode)) {
            return treeList;
        }

        String searchDName = CheckNullUtil.checkString(dName) ? null : dName.toLowerCase(Locale.ROOT);
        String searchDCode = CheckNullUtil.checkString(dCode) ? null : dCode.toLowerCase(Locale.ROOT);
        List<VueTreeVo> result = new ArrayList<>();
        for (VueTreeVo node : treeList) {
            boolean nameMatches = searchDName == null || (node.getTitle() != null && node.getTitle().toLowerCase(Locale.ROOT).contains(searchDName));
            boolean codeMatches = searchDCode == null || (node.getdCode() != null && node.getdCode().toLowerCase(Locale.ROOT).contains(searchDCode));
            boolean isSelfMatch = nameMatches && codeMatches;

            if (isSelfMatch) {
                result.add(node);
                continue;
            }

            List<VueTreeVo> filteredChildren = dictionaryTreeListFilter(node.getChildren(), dName, dCode);
            if (!CollectionUtils.isEmpty(filteredChildren)) {
                VueTreeVo copy = cloneShallow(node);
                copy.setChildren(filteredChildren);
                result.add(copy);
            }
        }
        return result;
    }

    private VueTreeVo cloneShallow(VueTreeVo src) {
        VueTreeVo vueTree = new VueTreeVo();
        vueTree.setKey(src.getKey());
        vueTree.setTitle(src.getTitle());
        vueTree.setValue(src.getValue());
        vueTree.setPid(src.getPid());
        vueTree.setdCode(src.getdCode());
        vueTree.setdSort(src.getdSort());
        vueTree.setdRemark(src.getdRemark());
        vueTree.setdStatus(src.getdStatus());
        vueTree.setdMold(src.getdMold());
        return vueTree;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CUDDictionary(DictionaryDto request) {
        String requestType = request.getType().toUpperCase(Locale.ENGLISH);
        if (requestType.equals("CREATE") || requestType.equals("CREATE2") || requestType.equals("UPDATE")) {
            //修改删除优先验证code是否重复
            QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
            wrapper.eq("d_parentid", request.getdParentid());
            if (ValidateUtil.isNotEmpty(request.getTeId())) wrapper.eq("Te_Id", request.getTeId());
            if (requestType.equals("UPDATE")) wrapper.ne("D_ID", request.getdId());
            wrapper.and(wrp -> wrp.eq("D_Code", request.getdCode()).or().eq("D_Name", request.getdName()));
            if (baseMapper.selectCount(wrapper) > 0) return BaseResponse.fail(BaseMsg.infoIsExist);
        }
        List<SysDictionary> datas = getAllDictionaryList(request.getTeId());
        String treeKey = redisUtils.tenantKey("dictionaryTreeList", request.getTeId());
        String allKey = redisUtils.tenantKey("allDictionary", request.getTeId());

        if (requestType.equals("CREATE") || requestType.equals("CREATE2")) {
            SysDictionary pojo = new SysDictionary();
            pojo.setdId(request.getdId());
            pojo.setdCode(request.getdCode());
            pojo.setdName(request.getdName());
            pojo.setdParentid(request.getdParentid());
            pojo.setdRemark(request.getdRemark());
            pojo.setdSort(request.getdSort() == null ? 0 : request.getdSort());
            pojo.setdStatus(request.getdStatus());
            pojo.setTeId(request.getTeId());
            pojo.setdMold(request.getdMold());
            List<SysDictionary> sameData = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals(pojo.getdParentid())).collect(Collectors.toList());
            pojo.setdSort(sameData.size() + 1);
            pojo.setdMold(request.getdMold());
            baseMapper.insert(pojo);
            redisUtils.delete(treeKey, allKey);
            return BaseResponse.ok();
        } else if (requestType.equals("UPDATE")) {
            SysDictionary dictionary = baseMapper.selectById(request.getdId());
            if (dictionary != null) {
                if (dictionary.getdId().equals(request.getdParentid()))
                    return BaseResponse.fail(BaseMsg.selectSuperiorError);
                dictionary.setdCode(request.getdCode());
                dictionary.setdName(request.getdName());
                dictionary.setdRemark(request.getdRemark());
//                sysDictionaryPojo.setdSort(request.getdSort() == null ? 0 : request.getdSort());
                dictionary.setdStatus(request.getdStatus());
                dictionary.setTeId(request.getTeId());
                dictionary.setdMold(request.getdMold());
                if (request.getdParentid() != null && !dictionary.getdParentid().equals(request.getdParentid())) {
                    //修改父级
                    //原同级节点下后续排序全部前进一级  新节点下当前节点排序为最后一级
                    List<SysDictionary> oldNodes = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals(dictionary.getdParentid()) && n.getdSort() > dictionary.getdSort()).collect(Collectors.toList());
                    List<SysDictionary> pNodes = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals(request.getdParentid())).collect(Collectors.toList());
                    dictionary.setdSort(pNodes.size() + 1);
                    dictionary.setdParentid(request.getdParentid());
                    List<SysDictionary> data = new ArrayList<>();
                    data.add(dictionary);
                    oldNodes.forEach(model -> {
                        model.setdSort(model.getdSort() - 1);
                        data.add(model);
                    });
                    updateBatchById(data);
                } else baseMapper.updateById(dictionary);
                redisUtils.delete(treeKey, allKey);
                return BaseResponse.ok();
            }
        } else if (requestType.equals("DELETE")) {
            SysDictionary pojo = baseMapper.selectById(request.getdId());
            if (pojo != null) {
                List<SysDictionary> childs = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals(pojo.getdId())).collect(Collectors.toList());
                Integer chilCount = childs.size();
                if (pojo.getdParentid().equals("0")) {
                    List<SysDictionary> roots = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals("0") && !n.getdId().equals(pojo.getdId())).collect(Collectors.toList());
                    Integer rootCount = roots.size();
                    if (rootCount == 0) return BaseResponse.fail(BaseMsg.onlyRootNode);
                    else if (chilCount > 0) return BaseResponse.fail(BaseMsg.delSubFirst);
                    else {
                        delteToChangeOtherSort(pojo, datas);
                        baseMapper.deleteById(request.getdId());
                        redisUtils.delete(treeKey, allKey);
                        return BaseResponse.ok();
                    }
                } else {
                    if (chilCount > 0) {
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    } else {
                        delteToChangeOtherSort(pojo, datas);
                        baseMapper.deleteById(request.getdId());
                        redisUtils.delete(treeKey, allKey);
                        return BaseResponse.ok();
                    }
                }
            }
        } else return BaseResponse.fail(BaseMsg.noOperationTypeSelected);
        return BaseResponse.fail();
    }

    @Override
    public BaseResponse getSelctList(SelectDto request) {
        List<SysDictionary> list = getAll(request.getTeId());
        List<Map<String, String>> data = new ArrayList<>();
        if (list != null && !ValidateUtil.isEmpty(request.getCode())) {
            List<SysDictionary> entities = list.stream().filter(d -> d.getdCode().equals(request.getCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) {
                SysDictionary entity = entities.stream().findFirst().get();
                List<SysDictionary> models = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals(entity.getdId())).collect(Collectors.toList());
                models.forEach(model -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", model.getdCode());
                    map.put("name", model.getdName());
                    data.add(map);
                });
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getDictionaryTree(SelectDto request) {
        return BaseResponse.ok(getDictionaryTreeMethod(request));
    }

    @Override
    public BaseResponse getDictionaryTreeByCodes(SelectDto request) {
        List<String> codes = request.getCodes();
        if (codes == null || codes.size() < 1) return new BaseResponse(BaseMsg.notReqParam);
        String allKey = redisUtils.tenantKey("allDictionary", request.getTeId());
        Object dictionaryTreeList = redisUtils.get(allKey);
        List<SysDictionary> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysDictionary>>() {
            });
        } else {
            list = getAll(request.getTeId());
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        Map<String, Object> data = new HashMap<>();
        for (String code : codes) {
            List<VueTreeVo> treeList = new ArrayList<>();
            list.forEach(model -> {
                if (code.equals(model.getdCode())) {
                    if (ValidateUtil.isEmpty(request.getdStatus())) {
                        list.forEach(child -> {
                            if (ValidateUtil.isNotEmpty(child.getdParentid()) && child.getdParentid().equals(model.getdId())) {
                                VueTreeVo vueTree = new VueTreeVo();
                                vueTree.setKey(child.getdId());
                                vueTree.setTitle(child.getdName());
                                vueTree.setValue(child.getdCode());
                                vueTree.setdStatus(child.getdStatus());
                                vueTree.setdRemark(child.getdRemark());
                                List<VueTreeVo> vueTrees = new ArrayList<>();
                                getDictionaryTreeChildrenIgnoreStatus(vueTrees, child.getdId(), list);
                                vueTree.setChildren(vueTrees);
                                treeList.add(vueTree);
                            }
                        });
                    } else {
                        list.forEach(child -> {
                            if (ValidateUtil.isNotEmpty(child.getdStatus()) && child.getdStatus().equals("1") && ValidateUtil.isNotEmpty(child.getdParentid()) && child.getdParentid().equals(model.getdId())) {
                                VueTreeVo vueTree = new VueTreeVo();
                                vueTree.setKey(child.getdId());
                                vueTree.setTitle(child.getdName());
                                vueTree.setValue(child.getdCode());
                                vueTree.setdStatus(child.getdStatus());
                                vueTree.setdRemark(child.getdRemark());
                                List<VueTreeVo> vueTrees = new ArrayList<>();
                                getDictionaryTreeChildren(vueTrees, child.getdId(), list);
                                vueTree.setChildren(vueTrees);
                                treeList.add(vueTree);
                            }
                        });
                    }
                }
            });
            data.put(code, treeList);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse upOrDownDictionarySort(String dId, String action, String token) {
        String teId = redisUtils.getTeId(token);
        List<SysDictionary> datas = getAllDictionaryList(teId);
        List<SysDictionary> data = datas.stream().filter(n -> n.getdId().equals(dId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(data)) {
            SysDictionary entity = data.get(0);
            List<SysDictionary> sameLevels = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals(entity.getdParentid())).collect(Collectors.toList());
            Integer index = sameLevels.indexOf(entity);
            Integer changeIndex;
            if (action.equals("up") && index > 0) {
                changeIndex = index - 1;
            } else if (action.equals("down") && index < sameLevels.size() - 1) {
                changeIndex = index + 1;
            } else
                return BaseResponse.fail(BaseMsg.notMove);
            SysDictionary temp = sameLevels.get(index);
            sameLevels.set(index, sameLevels.get(changeIndex));
            sameLevels.set(changeIndex, temp);
            int sort = 1;
            List<SysDictionary> updateData = new ArrayList<>();
            for (SysDictionary s : sameLevels) {
                s.setdSort(sort);
                sort++;
                updateData.add(s);
            }
            updateBatchById(updateData);
            redisUtils.delete(redisUtils.tenantKey("dictionaryTreeList", teId), redisUtils.tenantKey("allDictionary", teId));
            return BaseResponse.ok();
        } else return BaseResponse.fail(BaseMsg.infoNotExist);
    }

    @Override
    public List<VueTreeVo> getDictionaryByParentCode(String code, String token) {
        SelectDto data = new SelectDto();
        data.setCode(code);
        data.setTeId(redisUtils.getTeId(token));
        return getDictionaryTreeMethod(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse getApplicationDictionary(SelectDto request) {
        List<VueTreeSimpleVo> treeList = new ArrayList<>();
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("D_Sort");
        List<SysDictionary> list = baseMapper.selectList(queryWrapper);
        SysDictionary model = list.stream().filter(n -> "JPMsoft_SSFATB".equals(n.getdCode())).findFirst().orElse(null);
        if (model != null) {
            list.forEach(child -> {
                if (ValidateUtil.isNotEmpty(child.getdParentid()) && child.getdParentid().equals(model.getdId())) {
                    list.forEach(child2 -> {
                        if (ValidateUtil.isNotEmpty(child2.getdParentid()) && child2.getdParentid().equals(child.getdId())) {
                            VueTreeSimpleVo vueTree = new VueTreeSimpleVo();
                            vueTree.setKey(child2.getdId());
                            vueTree.setTitle(child2.getdName());
                            treeList.add(vueTree);
                        }
                    });
                }
            });
        }
        return BaseResponse.ok(treeList);
    }


    public List<VueTreeVo> getDictionaryTreeMethod(SelectDto request) {
        String allKey = redisUtils.tenantKey("allDictionary", request.getTeId());
        Object dictionaryTreeList = redisUtils.get(allKey);
        List<SysDictionary> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysDictionary>>() {
            });
        } else {
            list = getAll(request.getTeId());
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        List<VueTreeVo> treeList = new ArrayList<>();
        list.forEach(model -> {
            if (request.getCode().equals(model.getdCode())) {
                list.forEach(child -> {
                    if (ValidateUtil.isNotEmpty(child.getdParentid()) && child.getdParentid().equals(model.getdId())) {
                        VueTreeVo vueTree = new VueTreeVo();
                        vueTree.setKey(child.getdId());
                        vueTree.setTitle(child.getdName());
                        vueTree.setValue(child.getdCode());
                        vueTree.setdStatus(child.getdStatus());
                        vueTree.setdSort(child.getdSort());
                        List<VueTreeVo> vueTrees = new ArrayList<>();
                        getDictionaryTreeChildren(vueTrees, child.getdId(), list);
                        vueTree.setChildren(vueTrees);
                        treeList.add(vueTree);
                    }
                });
            }
        });
        return treeList;
    }


    private Integer getSysDictionaryCount(String parentId) {
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("D_Parentid", parentId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }


    private Integer getRootDictionaryCount(String dId) {
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("D_Parentid", '0');
        queryWrapper.ne("D_Id", dId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    private void getDictionaryTreeChildren(List<VueTreeVo> treeList, String parentId, List<SysDictionary> list) {
        list.forEach(model -> {
            if (ValidateUtil.isNotEmpty(model.getdStatus()) && model.getdStatus().equals("1") && ValidateUtil.isNotEmpty(model.getdParentid()) && model.getdParentid().equals(parentId)) {
                VueTreeVo vueTree = new VueTreeVo();
                vueTree.setKey(model.getdId());
                vueTree.setTitle(model.getdName());
                vueTree.setValue(model.getdCode());
                vueTree.setdStatus(model.getdStatus());
                List<VueTreeVo> vueTrees = new ArrayList<>();
                getDictionaryTreeChildren(vueTrees, model.getdId(), list);
                vueTree.setChildren(vueTrees);
                treeList.add(vueTree);
            }
        });
    }

    private void getDictionaryTreeChildrenIgnoreStatus(List<VueTreeVo> treeList, String parentId, List<SysDictionary> list) {
        list.forEach(child -> {
            if (ValidateUtil.isNotEmpty(child.getdParentid()) && child.getdParentid().equals(parentId)) {
                VueTreeVo vueTree = new VueTreeVo();
                vueTree.setKey(child.getdId());
                vueTree.setTitle(child.getdName());
                vueTree.setValue(child.getdCode());
                vueTree.setdStatus(child.getdStatus());
                vueTree.setdRemark(child.getdRemark());
                List<VueTreeVo> vueTrees = new ArrayList<>();
                getDictionaryTreeChildrenIgnoreStatus(vueTrees, child.getdId(), list);
                vueTree.setChildren(vueTrees);
                treeList.add(vueTree);
            }
        });
    }


    private void GetDictionaryTreeChildren(List<VueTreeVo> treeList, List<SysDictionary> filterList, List<SysDictionary> list) {
        filterList.forEach(model -> {
            VueTreeVo vueTree = new VueTreeVo();
            vueTree.setKey(model.getdId());
            vueTree.setTitle(model.getdName());
            vueTree.setValue(model.getdId());
            vueTree.setPid(model.getdParentid());
            vueTree.setdCode(model.getdCode());
            vueTree.setdSort(model.getdSort());
            vueTree.setdRemark(model.getdRemark());
            vueTree.setdStatus(model.getdStatus());
            vueTree.setdMold(model.getdMold());
            List<VueTreeVo> vueTrees = new ArrayList<>();
            List<SysDictionary> newList = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals(model.getdId())).collect(Collectors.toList());
            if (newList.size() > 0) GetDictionaryTreeChildren(vueTrees, newList, list);
            vueTree.setChildren(vueTrees);
            treeList.add(vueTree);
        });
    }

    private List<SysDictionary> getAllDictionaryList(String teId) {
        String allKey = redisUtils.tenantKey("allDictionary", teId);
        Object dictionaryTreeList = redisUtils.get(allKey);
        List<SysDictionary> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysDictionary>>() {
            });
        } else {
            list = getAll(teId);
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        return list;
    }

    private List<SysDictionary> getAll(String teId) {
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) queryWrapper.eq("Te_Id", teId).or().eq("D_Mold", 1);
        queryWrapper.orderByAsc("D_Sort");
        return baseMapper.selectList(queryWrapper);
    }


    /**
     * 删除当前节点后 同级后续排序进1
     *
     * @param pojo
     */
    private void delteToChangeOtherSort(SysDictionary pojo, List<SysDictionary> datas) {
        List<SysDictionary> sameLevels = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdParentid()) && n.getdParentid().equals(pojo.getdParentid()) && n.getdSort() > pojo.getdSort()).collect(Collectors.toList());
        List<SysDictionary> changes = new ArrayList<>();
        sameLevels.forEach(model -> {
            model.setdSort(model.getdSort() - 1);
            changes.add(model);
        });
        if (!CollectionUtils.isEmpty(changes)) updateBatchById(changes);
    }

    @Override
    public BaseResponse addDictionaryByCodeCj(DictionaryDto request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(BaseMsg.notReqParam);
        }
        // 查询
        LambdaQueryWrapper<SysDictionary> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysDictionary::getdCode, request.getdCode())
                .eq(SysDictionary::getdStatus, "1")
                .last(" limit 1 ");
        SysDictionary dictionary = this.getOne(queryWrapper);
        if (ValidateUtil.isNull(dictionary)) {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }

        // 传入的单位处理
        String united = unitStandardHandle(request.getdName());
        // 重复校验
        long count = count(Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getdCode, united).eq(SysDictionary::getdParentid, dictionary.getdId()));
        if (count > 0) {
            return BaseResponse.fail("该单位已存在");
        }

        List<SysDictionary> datas = list(Wrappers.<SysDictionary>lambdaQuery().eq(SysDictionary::getdParentid, dictionary.getdId()));
        SysDictionary pojo = new SysDictionary();
        pojo.setdId(GenerateUUID.getUUID());
        pojo.setdCode(united);
        pojo.setdName(united);
        pojo.setdParentid(dictionary.getdId());
        pojo.setdRemark(request.getdRemark());
        pojo.setdStatus("1");
        pojo.setTeId(request.getTeId());
        pojo.setdMold(request.getdMold());
        pojo.setdSort(datas.size() + 1);
        String treeKey = redisUtils.tenantKey("dictionaryTreeList", request.getTeId());
        String allKey = redisUtils.tenantKey("allDictionary", request.getTeId());
        this.save(pojo);
        redisUtils.delete(treeKey, allKey);
        return BaseResponse.ok();
    }

    private static String unitStandardHandle(String unit) {
        if (unit == null || unit.isEmpty()) {
            return unit;
        }
        String lowerUnit = unit.toLowerCase(Locale.ROOT)
                        .replace("\u200C", "")
                        .replace("\u200B", "")
                        .trim();

        if (unit.contains("千米") || unit.contains("公里") || lowerUnit.contains("km")) {
            return "km";
        } else if (unit.contains("平方米") || unit.contains("平米") || lowerUnit.contains("㎡") || lowerUnit.contains("m2") || lowerUnit.contains("m²")) {
            return "m²";
        } else if (unit.contains("立方米") || unit.contains("方") || lowerUnit.contains("m³") || lowerUnit.contains("m3")) {
            return "m³";
        } else if ((unit.contains("米") || lowerUnit.matches("(^|.*/)m$")) && !unit.contains("千米") && !lowerUnit.contains("km") && !unit.contains("平方米") && !unit.contains("平米") && !lowerUnit.contains("m2") && !unit.contains("立方米") && !lowerUnit.contains("m3")) {
            return "m";
        } else if (unit.contains("项")) {
            return "项";
        } else if (unit.contains("部")) {
            return "部";
        } else if (lowerUnit.contains("kva")) {
            return "KVA";
        } else if (unit.contains("个")) {
            return "个";
        } else if (unit.contains("张")) {
            return "张";
        } else if (unit.contains("株")) {
            return "株";
        } else if (unit.contains("丛")) {
            return "丛";
        } else if (unit.contains("处")) {
            return "处";
        } else if (unit.contains("千克") || unit.contains("公斤") || lowerUnit.contains("kg")) {
            return "kg";
        } else if (unit.contains("套")) {
            return "套";
        } else if (unit.contains("组")) {
            return "组";
        } else if (unit.contains("台")) {
            return "台";
        } else if (unit.contains("系统")) {
            return "系统";
        } else if (unit.contains("吨") || lowerUnit.contains("t")) {
            return "吨";
        } else {
            return unit;
        }
    }
}
