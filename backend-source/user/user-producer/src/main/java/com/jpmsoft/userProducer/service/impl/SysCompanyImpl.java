package com.jpmsoft.userProducer.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.SysCompanyMapper;
import com.jpmsoft.userProducer.pojo.dto.CommonUnitsDTO;
import com.jpmsoft.userProducer.pojo.dto.CompanyDto;
import com.jpmsoft.userProducer.pojo.entity.SysCompany;
import com.jpmsoft.userProducer.pojo.entity.SysDepartmentP;
import com.jpmsoft.userProducer.pojo.vo.SysCompanyVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysCompanyImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements ISysCompanyService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISysSequenceSetService sysSequenceSetService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private ISysDepartmentPService sysDepartmentPService;


    @Override
    public BaseResponse GetCompanySelect(String token) {
        String selectKey = redisUtils.tenantKey("allCompanySelect", redisUtils.getTeId(token));
        String allKey = redisUtils.tenantKey("allCompany", redisUtils.getTeId(token));
        Object allCompanySelect = redisUtils.get(selectKey);
        if (allCompanySelect != null) {
            List<Map<String, String>> returnData = JacksonUtils.parseObject(allCompanySelect.toString(), new TypeReference<List<Map<String, String>>>() {
            });
            return BaseResponse.ok(returnData);
        }

        Object allCompany = redisUtils.get(allKey);
        List<SysCompany> list;
        if (allCompany != null) {
            list = JacksonUtils.parseObject(allCompany.toString(), new TypeReference<List<SysCompany>>() {
            });
        } else {
            QueryWrapper<SysCompany> queryWrapper = new QueryWrapper<>();
            list = baseMapper.selectList(queryWrapper);
            queryWrapper.orderByAsc("C_CreateTime");
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        List<Map<String, String>> data = new ArrayList<>();
        list.forEach(model -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", model.getcId());
            map.put("name", model.getcName());
            data.add(map);
        });
        redisUtils.set(selectKey, JacksonUtils.toJSONString(data));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse GetCompanySelectByType(String cType, String token) {
        String allKey = redisUtils.tenantKey("allCompany", redisUtils.getTeId(token));
        Object allCompany = redisUtils.get(allKey);
        List<SysCompany> list;
        if (allCompany != null) {
            list = JacksonUtils.parseObject(allCompany.toString(), new TypeReference<List<SysCompany>>() {
            });
        } else {
            QueryWrapper<SysCompany> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("C_CreateTime");
            list = baseMapper.selectList(queryWrapper);
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        if (list != null && list.size() > 0)
            list = list.stream().filter(n -> !ValidateUtil.isEmpty(n.getcType()) && n.getcType().equals(cType)).collect(Collectors.toList());
        List<Map<String, String>> data = new ArrayList<>();
        list.forEach(model -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", model.getcId());
            map.put("name", model.getcName());
            data.add(map);
        });
        return BaseResponse.ok(data);
    }

    @Override
    public List<SysCompany> GetCompanySelect(List<String> idlist, String token) {
        String allKey = redisUtils.tenantKey("allCompany", redisUtils.getTeId(token));
        Object allCompany = redisUtils.get(allKey);
        List<SysCompany> list;
        if (allCompany != null) {
            list = JacksonUtils.parseObject(allCompany.toString(), new TypeReference<List<SysCompany>>() {
            });
        } else {
            QueryWrapper<SysCompany> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("C_CreateTime");
            list = baseMapper.selectList(queryWrapper);
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        if (list != null && list.size() > 0) {
            return list.stream().filter(item -> idlist.contains(item.getcId())).collect(Collectors.toList());
        } else return new ArrayList<>();
    }

    @Override
    public BaseResponse GetCompanyList(CompanyDto data) {
        if (!ValidateUtil.isEmpty(data.getcId()) && ValidateUtil.isEmpty(data.getIncludeCIdStr())) {
            List<SysCompany> datas = baseMapper.selectList(Wrappers.<SysCompany>lambdaQuery().eq(SysCompany::getcId, data.getcId()));
            return new BaseResponse("获取成功", datas, datas == null ? 0 : datas.size());
        }
        QueryMap<CompanyDto> map = new QueryMap<>(data);
        map.put("cId").put("teId").put("cState").put("cType").put("cName").put("cCode")
                .put("cCreditcode").put("payNames").put("cContactpersontel").put("cContactperson")
                .put("excludeType").put("containType").put("filterDepCpy");

        if (ValidateUtil.isNotEmpty(data.getIncludeCIdStr())) {
            String[] split = data.getIncludeCIdStr().split("；");
            map.put("includeCIds", Arrays.stream(split)
                    .map(String::trim)
                    .filter(StrUtil::isNotBlank)
                    .collect(Collectors.toList()));
        }

        //筛选合同乙方单位
        if (ValidateUtil.isNotEmpty(data.getConId())) {
            List<String> cIds = baseMapper.getContractPartyB(data.getConId());
            if (!CollectionUtils.isEmpty(cIds)) {
                cIds = cIds.stream().filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(cIds)) map.put("cIds", cIds);
                else return BaseResponse.fail(BaseMsg.contractNotPartyB);
            } else return BaseResponse.fail(BaseMsg.contractNotPartyB);
        }

        if (ValidateUtil.isNotEmpty(data.getTopModule())) {
            String commonUnits = sysConfigEntityService.getSysConfigByCode("CommonUnits");
            if (ValidateUtil.isNotEmpty(commonUnits)) {
                List<CommonUnitsDTO> unitsList = JSONUtil.toList(commonUnits, CommonUnitsDTO.class);
                if (ValidateUtil.isNotEmpty(unitsList)) {
                    List<String> modelCIds = unitsList.stream()
                            .filter(unit -> unit.getModule().equals(data.getTopModule()))
                            .sorted(Comparator.comparing(CommonUnitsDTO::getOrderNumber))
                            .map(CommonUnitsDTO::getCId)
                            .filter(ValidateUtil::isNotEmpty)
                            .collect(Collectors.toList());
                    if (ValidateUtil.isNotEmpty(modelCIds)) {
                        map.put("modelCIds", modelCIds);
                    }
                }
            }
        }

        Page<SysCompany> page = new Page<>(data.getPage(), data.getSize());
        List<SysCompany> datas = baseMapper.getCompanyList(page, map.getQuery());
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse GetAllCompanyList(CompanyDto data) {
        QueryWrapper<SysCompany> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("C_State", "1");
        if (ValidateUtil.isNotEmpty(data.getTeId())) queryWrapper.eq("Te_Id", data.getTeId());
        if (!ValidateUtil.isEmpty(data.getcType())) queryWrapper.eq("C_Type", data.getcType());
        if (!ValidateUtil.isEmpty(data.getcName())) queryWrapper.like("C_Name", data.getcName());
        if (!ValidateUtil.isEmpty(data.getcCode())) queryWrapper.like("C_Code", data.getcCode());
        if (!ValidateUtil.isEmpty(data.getcCreditcode())) queryWrapper.like("C_CreditCode", data.getcCreditcode());
        if (!ValidateUtil.isEmpty(data.getcContactpersontel()))
            queryWrapper.like("C_ContactPersonTel", data.getcContactpersontel());
        if (!ValidateUtil.isEmpty(data.getcContactperson()))
            queryWrapper.like("C_ContactPerson", data.getcContactperson());
        //筛选合同乙方单位
        if (ValidateUtil.isNotEmpty(data.getConId())) {
            List<String> cIds = baseMapper.getContractPartyB(data.getConId());
            if (!CollectionUtils.isEmpty(cIds)) {
                cIds = cIds.stream().filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(cIds)) queryWrapper.in("C_Id", cIds);
                else return BaseResponse.fail(BaseMsg.contractNotPartyB);
            } else return BaseResponse.fail(BaseMsg.contractNotPartyB);
        }

        if (!CheckNullUtil.checkString(data.getSortType(), data.getSortFiled())) {
            if (data.getSortType().equals("ASC")) {
                queryWrapper.orderByAsc(data.getSortFiled());
            } else {
                queryWrapper.orderByDesc(data.getSortFiled());
            }
        }
        List<SysCompany> datas = baseMapper.selectList(queryWrapper);
        return BaseResponse.ok(datas, datas == null ? 0 : datas.size());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateCompany(CompanyDto data) {
        SysCompany pojo;
        String codeRule = null;
        if (ValidateUtil.isEmpty(data.getcId())) {
            pojo = new SysCompany();
            if (sysSequenceSetService.sequenceRestEnable("SysCompany", data.getTeId()) == true) {
                data.setcCode(sysSequenceSetService.generateCodeByTCode("SysCompany", data, data.getTeId()));
                if (ValidateUtil.isEmpty(data.getcCode())) return BaseResponse.fail(BaseMsg.codeGenerateFail);
                codeRule = "SysCompany";
            }
        } else {
            pojo = baseMapper.selectById(data.getcId());
        }
        pojo.setcName(data.getcName());
        pojo.setcAbbreviation(data.getcAbbreviation());
        pojo.setcCode(data.getcCode());
        pojo.setcContactperson(data.getcContactperson());
        pojo.setcContactpersontel(data.getcContactpersontel());
        pojo.setcCreditcode(data.getcCreditcode());
        pojo.setcMdmNo(data.getcMdmNo());
        pojo.setcRemark(data.getcRemark());
        pojo.setcSolution(data.getcSolution());
        pojo.setcType(data.getcType());
        pojo.setcUnitlegalperson(data.getcUnitlegalperson());
        pojo.setcWebsite(data.getcWebsite());
        pojo.setcState(data.getcState());
        pojo.setTeId(data.getTeId());
        QueryWrapper<SysCompany> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(data.getTeId())) wrapper.eq("Te_Id", data.getTeId());
        if (!ValidateUtil.isEmpty(data.getcId())) wrapper.ne("C_ID", data.getcId());
        wrapper.eq("C_Name", data.getcName());
        if (baseMapper.selectCount(wrapper) > 0) return new BaseResponse("单位名称重复！");
        wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(data.getTeId())) wrapper.eq("Te_Id", data.getTeId());
        if (!ValidateUtil.isEmpty(data.getcId())) wrapper.ne("C_ID", data.getcId());
        wrapper.eq("C_CreditCode", data.getcCreditcode());
        if (baseMapper.selectCount(wrapper) > 0) return new BaseResponse("统一社会信用代码重复！");
        String allSelect = redisUtils.tenantKey("allCompanySelect", data.getTeId());
        String allKey = redisUtils.tenantKey("allCompany", data.getTeId());
        if (ValidateUtil.isEmpty(data.getcId())) {
            pojo.setcId(UUID.randomUUID().toString().replaceAll("-", ""));
            pojo.setcCreatetime(new Date());
            pojo.setcCreateuName(data.getcCreateuName());
            pojo.setcCreateuId(data.getcCreateuId());
            pojo.setcBusinessLicense(data.getcBusinessLicense());
            pojo.setcQualification(data.getcQualification());
            pojo.setcCredentials(data.getcCredentials());
            baseMapper.insert(pojo);
            List<String> ids = new ArrayList<>();
            ids.add(pojo.getcBusinessLicense());
            ids.add(pojo.getcQualification());
            ids.add(pojo.getcCredentials());
            sysFileAnnexService.addFormRefreshRelationFile(ids, pojo.getcId());
            redisUtils.delete(allSelect, allKey);
        } else {
            List<Map<String, String>> params = new ArrayList<>();
            Map<String, String> cBusinessLicense = new HashMap<>();
            Map<String, String> cQualification = new HashMap<>();
            Map<String, String> cCredentials = new HashMap<>();
            cBusinessLicense.put("fileRelationId", pojo.getcBusinessLicense());
            cBusinessLicense.put("fileIds", data.getcBusinessLicense());
            cQualification.put("fileRelationId", pojo.getcQualification());
            cQualification.put("fileIds", data.getcQualification());
            cCredentials.put("fileRelationId", pojo.getcCredentials());
            cCredentials.put("fileIds", data.getcCredentials());
            params.add(cBusinessLicense);
            params.add(cQualification);
            params.add(cCredentials);
            baseMapper.updateById(pojo);
            sysFileAnnexService.refreshRelationFile(params, pojo.getcId());
            redisUtils.delete(allSelect, allKey);
        }
        if (ValidateUtil.isNotEmpty(codeRule)) sysSequenceSetService.refershAutoNo(codeRule, data, data.getTeId());
        return BaseResponse.ok(pojo.getcId());
    }

    @Override
    public BaseResponse saveForGZ(CompanyDto data) {
        SysCompany saveEntity = new SysCompany();
        SysCompany queryEntity = this.baseMapper.selectById(data.getcId());
        BeanUtils.copyProperties(data, saveEntity);
        if (null == queryEntity) {
            //新增
            saveEntity.setcCreateuId("68D319413F904195A859973BD4C5702D");
            saveEntity.setcCreateuName("admin");
            saveEntity.setcCreatetime(new Date());
            this.save(saveEntity);
        } else {
            this.updateById(saveEntity);
        }
        return BaseResponse.ok();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse DeleteCompany(CompanyDto data) {
        SysCompany sysCompany = baseMapper.selectById(data.getcId());
        if (sysCompany == null) return new BaseResponse(HttpStatus.OK.value(), false, "数据不存在，请重试！");
        else {
            baseMapper.deleteById(sysCompany.getcId());
            redisUtils.delete("allCompanySelect", "allCompany");
            return new BaseResponse(HttpStatus.OK.value(), true, "删除成功");
        }
    }

    @Override
    public BaseResponse getCompanyById(String id) {
        SysCompany entity = baseMapper.selectById(id);
        if (entity == null) return new BaseResponse("数据信息异常,请刷新重试");
        SysCompanyVo company = new SysCompanyVo();
        company.setBusinessLicenseList(sysFileAnnexService.getFileByFileRelationId(entity.getcBusinessLicense()));
        company.setQualificationList(sysFileAnnexService.getFileByFileRelationId(entity.getcQualification()));
        company.setCredentialsList(sysFileAnnexService.getFileByFileRelationId(entity.getcCredentials()));
        company.setcQualification(entity.getcQualification());
        company.setcCredentials(entity.getcCredentials());
        company.setcBusinessLicense(entity.getcBusinessLicense());
        company.setcWebsite(entity.getcWebsite());
        company.setcUnitlegalperson(entity.getcUnitlegalperson());
        company.setcType(entity.getcType());
        company.setcState(entity.getcState());
        company.setcSolution(entity.getcSolution());
        company.setcRemark(entity.getcRemark());
        company.setcName(entity.getcName());
        company.setcMdmNo(entity.getcMdmNo());
        company.setcId(entity.getcId());
        company.setcCreditcode(entity.getcCreditcode());
        company.setcCreateuName(entity.getcCreateuName());
        company.setcCreateuId(entity.getcCreateuId());
        company.setcCreatetime(entity.getcCreatetime());
        company.setcContactpersontel(entity.getcContactpersontel());
        company.setcCode(entity.getcCode());
        company.setcAbbreviation(entity.getcAbbreviation());
        return BaseResponse.ok(company);
    }

    @Override
    public BaseResponse getCompanyByCreditCode(String code, String token) {
        String teId = redisUtils.getTeId(token);
        QueryWrapper<SysCompany> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        wrapper.eq("C_Creditcode", code);
        List<SysCompany> datas = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(datas)) return BaseResponse.ok(datas.stream().findFirst().get());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse GetCompanyNameByIds(String ids) {
        if (ValidateUtil.isEmpty(ids)) {
            return BaseResponse.fail("未传入任何招标单位ID");
        }

        // 分割字符串并去重
        Set<String> idSet = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());

        if (idSet.isEmpty()) {
            return BaseResponse.fail("未找到有效招标单位ID");
        }

        // 查询数据库
        List<SysCompany> companies = listByIds(new ArrayList<>(idSet));

        if (CollectionUtils.isEmpty(companies)) {
            return BaseResponse.fail("未查询到任何匹配的招标单位");
        }
        // 提取所有单位名称到列表中
        List<String> tendereeNames = companies.stream()
                .map(SysCompany::getcName)
                .distinct()
                .collect(Collectors.toList());
        return BaseResponse.ok(tendereeNames);
    }

    /**
     * 根据pdCode和pId查询项目组织机构单位信息
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getCompanyByPId(CompanyDto request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getpId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<CompanyDto> map = new QueryMap<>(request);
        if (ValidateUtil.isNotEmpty(request.getExcludeCIdStr())) {
            String excludeCIdStr = request.getExcludeCIdStr();
            String[] split = excludeCIdStr.split("；");
            if (split.length > 0) {
                map.put("excludeCIds", Arrays.stream(split)
                        .map(String::trim)
                        .filter(StrUtil::isNotBlank)
                        .collect(Collectors.toList()));
            }
        }
        if (ValidateUtil.isNotEmpty(request.getPdCode())) {
            List<String> pdCodes = getChildPdCodes(request.getpId(), request.getPdCode());
            if (CollectionUtils.isEmpty(pdCodes)) {
                return BaseResponse.ok(Collections.emptyList(), 0);
            }
            map.put("pdCodes", pdCodes);
        }
        map.put("cId").put("cName").put("pId").put("pdType");
        Page<SysCompanyVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysCompanyVo> list = baseMapper.getCompanyByPId(page, map.getQuery());
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    private List<String> getChildPdCodes(String pId, String pdCode) {
        List<SysDepartmentP> departments = sysDepartmentPService.list(Wrappers.<SysDepartmentP>lambdaQuery()
                .eq(SysDepartmentP::getpId, pId)
                .select(SysDepartmentP::getPdId, SysDepartmentP::getPdCode, SysDepartmentP::getPdParentid));
        if (CollectionUtils.isEmpty(departments)) {
            return Collections.emptyList();
        }
        Optional<SysDepartmentP> parent = departments.stream()
                .filter(item -> pdCode.equals(item.getPdCode()))
                .findFirst();
        if (!parent.isPresent()) {
            return Collections.emptyList();
        }

        Map<String, List<SysDepartmentP>> childMap = departments.stream()
                .filter(item -> StrUtil.isNotBlank(item.getPdParentid()))
                .collect(Collectors.groupingBy(SysDepartmentP::getPdParentid));
        List<String> pdCodes = new ArrayList<>();
        Deque<String> queue = new ArrayDeque<>();
        queue.add(parent.get().getPdId());
        while (!queue.isEmpty()) {
            List<SysDepartmentP> children = childMap.get(queue.poll());
            if (CollectionUtils.isEmpty(children)) {
                continue;
            }
            for (SysDepartmentP child : children) {
                if (StrUtil.isNotBlank(child.getPdCode())) {
                    pdCodes.add(child.getPdCode());
                }
                queue.add(child.getPdId());
            }
        }
        return pdCodes;
    }
}
