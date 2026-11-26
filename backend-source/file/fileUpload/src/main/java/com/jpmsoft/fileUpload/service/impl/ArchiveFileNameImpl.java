package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.enums.AfnrTypeEnum;
import com.jpmsoft.fileUpload.mapper.ArchiveFileNameMapper;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveFileNameDTO;
import com.jpmsoft.fileUpload.pojo.entity.*;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:09
 */
@Slf4j
@Service
public class ArchiveFileNameImpl extends ServiceImpl<ArchiveFileNameMapper, ArchiveFileName> implements IArchiveFileNameService {

    @Resource
    private IArchiveFileNameRuleService archiveFileNameRuleService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysTableColumnService sysTableColumnService;
    @Resource
    private ISysDictionaryService sysDictionaryService;

    @Override
    public BaseResponse createOrUpdate(ArchiveFileNameDTO request, String token) {
        ArchiveFileName entity = new ArchiveFileName();
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        BeanUtils.copyProperties(request, entity);
        //一律小写存储
        entity.setTableCode(request.getTableCode().toLowerCase(Locale.ENGLISH));
        entity.setColumnCode(request.getColumnCode().toLowerCase(Locale.ENGLISH));
        if (ValidateUtil.isEmpty(request.getId())) {
            if (exist(request)) return BaseResponse.fail(FileMsg.fileIsConfNot2Re);
//            entity = new ArchiveFileName();
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            ArchiveFileName old = baseMapper.selectById(request.getId());
            if (old == null) return BaseResponse.fail(FileMsg.infoNotExist);
            entity.setCreateUname(old.getCreateUname());
            entity.setCreateUid(old.getCreateUid());
            entity.setCreateTime(old.getCreateTime());
            entity.setUpdateUname(user.getName());
            entity.setUpdateUid(user.getId());
            entity.setUpdateTime(new Date());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse getArchiveFileNameList(ArchiveFileNameDTO request) {
        QueryWrapper<ArchiveFileName> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotNull(request.getAfnStatus())) wrapper.eq("Afn_Status", request.getAfnStatus());
        if (ValidateUtil.isNotEmpty(request.getAfnName())) wrapper.like("afn_name", request.getAfnName());
        if (ValidateUtil.isNotEmpty(request.getTableCode())) wrapper.like("Table_Code", request.getTableCode());
        if (ValidateUtil.isNotEmpty(request.getColumnCode())) wrapper.like("Column_Code", request.getColumnCode());
        if (ValidateUtil.isNotEmpty(request.getCreateUname())) wrapper.like("Create_Uname", request.getCreateUname());
        if (ValidateUtil.isNotEmpty(request.getUpdateUname())) wrapper.like("Update_Uname", request.getUpdateUname());
        IPage<ArchiveFileName> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        List<ArchiveFileName> data = iPage.getRecords();
        if (!CollectionUtils.isEmpty(data)) return BaseResponse.ok(data.stream().map(d -> {
            ArchiveFileNameDTO s = new ArchiveFileNameDTO();
            BeanUtils.copyProperties(d, s);
            return s;
        }).collect(Collectors.toList()), total.intValue());
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse deleteById(String id) {
        ArchiveFileName afn = baseMapper.selectById(id);
        if (afn == null) return BaseResponse.fail(FileMsg.infoNotExist);
        archiveFileNameRuleService.deleteByAfnId(id);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public String name(SysFileAnnex file) {
        if (ValidateUtil.isAnObjEmpty(file.getTableCode(), file.getColumnCode())) return file.getFileName();
        QueryWrapper<ArchiveFileName> wrapper = new QueryWrapper<>();
        wrapper.eq("Table_Code", file.getTableCode().toLowerCase(Locale.ENGLISH));
        wrapper.eq("Column_Code", file.getColumnCode().toLowerCase(Locale.ENGLISH));
        List<ArchiveFileName> data = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(data)) return file.getFileName();
        ArchiveFileName afn = data.stream().findFirst().get();
        List<ArchiveFileNameRule> rules = archiveFileNameRuleService.selectRules(afn.getId());
        if (CollectionUtils.isEmpty(rules)) return file.getFileName();
        //region 根据规则生成编码
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rules.size(); i++) {
            ArchiveFileNameRule rule = rules.get(i);
            //判断当前单个规则是否生效 匹配配置字段值是否相同
            if (checkCurrent(rule, file)) {
                //根据规则获取值
                String str = oneRuleStr(rule, file);
                if (ValidateUtil.isNotEmpty(str)) {
                    sb.append(str);
                    if (ValidateUtil.isNotEmpty(afn.getAfnDelimiter()) && (i + 1) < rules.size())
                        sb.append(afn.getAfnDelimiter());
                }
            }
        }
        if (ValidateUtil.isEmpty(sb.toString())) return file.getFileName();
        //endregion
        return sb.toString() + "." + file.getFileSuffix();
    }

    private String oneRuleStr(ArchiveFileNameRule rule, SysFileAnnex file) {
        String value = null;
        switch (Objects.requireNonNull(AfnrTypeEnum.getEnum(rule.getAfnrType()))) {
            case CONST:
                value = rule.getAfnrValue();
                break;
            case PROJECT:
                value = selectProject(file.getFileEntityId(), rule.getAfnrValue(), rule.getAfnrDicCode());
                break;
            case CONTRACT:
                value = selectContract(file.getFileEntityId(), rule.getAfnrValue(), rule.getAfnrDicCode());
                break;
            case SECINFO:
                value = selectSecinfo(file.getFileEntityId(), rule.getAfnrValue(), rule.getAfnrDicCode());
                break;
            case CURRENT:
                value = dic2Str(selectCurrent(file, rule.getAfnrValue()), rule.getAfnrDicCode());
                break;
            case LINKED:
                value = selectLink(file, rule);
                break;
            case SQL:
                value = selectSql(rule);
                break;
        }
        return value;
    }

    /**
     * 判断常量字段
     *
     * @param rule
     * @param file
     * @return
     */
    @Deprecated
    private String getConst(ArchiveFileNameRule rule, SysFileAnnex file) {
        //不需要判断当前表单值是否满足需求
        if (ValidateUtil.isAnObjEmpty(rule.getAfnrField(), rule.getAfnrFieldVal())) return rule.getAfnrValue();
        String str = selectCurrent(file, rule.getAfnrField());
        if (ValidateUtil.isNotEmpty(str) && str.equals(rule.getAfnrFieldVal())) return rule.getAfnrValue();
        return null;
    }

    /**
     * 当前规则是否生效
     *
     * @param rule
     * @param file
     * @return
     */
    private Boolean checkCurrent(ArchiveFileNameRule rule, SysFileAnnex file) {
        if (ValidateUtil.isAnObjEmpty(rule.getAfnrField(), rule.getAfnrFieldVal())) return true;
        String str = selectCurrent(file, rule.getAfnrField());
        if (ValidateUtil.isNotEmpty(str) && str.equals(rule.getAfnrFieldVal())) return true;
        return false;
    }

    /**
     * 执行SQL返回结果
     *
     * @param rule
     * @return
     */
    private String selectSql(ArchiveFileNameRule rule) {
        try {
            return dic2Str(baseMapper.selectSql(rule.getAfnrValue()), rule.getAfnrDicCode());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("SQL执行报错！", ex);
            return null;
        }
    }


    /**
     * 链表信息查询
     *
     * @param file 附件信息
     * @param rule
     * @return
     */
    private String selectLink(SysFileAnnex file, ArchiveFileNameRule rule) {
        if (ValidateUtil.isAnObjEmpty(rule.getAfnrValue(), rule.getAfnrShipTable(), rule.getAfnrShipCode(), rule.getAfnrShipCurrent()))
            return null;
        //获取链表字段在本表单的值
        String str = selectCurrent(file, rule.getAfnrShipCurrent());
        if (ValidateUtil.isEmpty(str)) return null;
        try {
            return dic2Str(baseMapper.selectCurrent(str, rule.getAfnrValue(), rule.getAfnrShipTable(), rule.getAfnrShipCode()), rule.getAfnrDicCode());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("查询链表字段报错！", ex);
            return null;
        }
    }

    /**
     * 查询当前业务表字段数据
     *
     * @param file 附件信息
     * @param code 字段名
     * @return
     */
    private String selectCurrent(SysFileAnnex file, String code) {
        try {
            String pk = sysTableColumnService.selectPk(file.getTableCode());
            if (ValidateUtil.isEmpty(pk)) return null;
            return baseMapper.selectCurrent(file.getFileEntityId(), code, file.getTableCode(), pk);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("查询项目字段报错！", ex);
            return null;
        }
    }


    /**
     * 查询项目表的指定字段
     *
     * @param entityId 表单ID
     * @param code     项目的字段编码
     * @param dic      字典编码
     * @return
     */
    private String selectProject(String entityId, String code, String dic) {
        if (ValidateUtil.isEmpty(entityId)) return null;
        SysFile sf = sysFileService.getById(entityId);
        if (sf == null) return null;
        if (ValidateUtil.isEmpty(sf.getFileproject())) return null;
        try {
            return dic2Str(baseMapper.selectProject(sf.getFileproject(), code), dic);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("查询项目字段报错！", ex);
            return null;
        }
    }

    /**
     * 查询合同表的指定字段
     *
     * @param entityId 表单ID
     * @param code     合同的字段编码
     * @param dic      字典编码
     * @return
     */
    private String selectContract(String entityId, String code, String dic) {
        if (ValidateUtil.isEmpty(entityId)) return null;
        SysFile sf = sysFileService.getById(entityId);
        if (sf == null) return null;
        if (ValidateUtil.isEmpty(sf.getFilecontract())) return null;
        try {
            return dic2Str(baseMapper.selectContract(sf.getFilecontract(), code), dic);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("查询合同字段报错！", ex);
            return null;
        }
    }

    /**
     * 查询标段表的指定字段
     *
     * @param entityId 表单ID
     * @param code     标段的字段编码
     * @param dic      字典编码
     * @return
     */
    private String selectSecinfo(String entityId, String code, String dic) {
        if (ValidateUtil.isEmpty(entityId)) return null;
        SysFile sf = sysFileService.getById(entityId);
        if (sf == null) return null;
        if (ValidateUtil.isEmpty(sf.getFilesectionid())) return null;
        try {
            return dic2Str(baseMapper.selectSecinfo(sf.getFilesectionid(), code), dic);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("查询标段字段报错！", ex);
            return null;
        }
    }

    /**
     * 将字典值转为名称
     *
     * @param value 字典值
     * @param code  字典编码
     * @return
     */
    private String dic2Str(String value, String code) {
        if (ValidateUtil.isAnObjEmpty(value, code)) return value;
        List<SysDictionary> dics = sysDictionaryService.getDictionaryByParentCode(code);
        if (CollectionUtils.isEmpty(dics)) return null;
        List<SysDictionary> ds = dics.stream().filter(f -> f.getDCode().equals(value)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ds)) return null;
        return ds.stream().findFirst().get().getDName();
    }


    private boolean exist(ArchiveFileNameDTO request) {
        QueryWrapper<ArchiveFileName> wrapper = new QueryWrapper<>();
        wrapper.eq("Table_Code", request.getTableCode());
        wrapper.eq("Column_Code", request.getColumnCode());
        return baseMapper.exists(wrapper);
    }
}
