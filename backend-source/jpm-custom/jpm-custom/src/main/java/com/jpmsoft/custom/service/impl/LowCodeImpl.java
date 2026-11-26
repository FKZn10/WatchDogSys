package com.jpmsoft.custom.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.utils.DataAuthSqlUtil;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.custom.constants.CustomMsg;
import com.jpmsoft.custom.constants.TjQueryEnum;
import com.jpmsoft.custom.pojo.dto.*;
import com.jpmsoft.custom.pojo.entity.SysForm;
import com.jpmsoft.custom.pojo.entity.SysTable;
import com.jpmsoft.custom.pojo.entity.SysTableColumn;
import com.jpmsoft.custom.pojo.vo.LogUserInfoVo;
import com.jpmsoft.custom.service.*;
import com.jpmsoft.custom.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/30 9:35
 */
@Slf4j
@Service
public class LowCodeImpl implements ILowCodeService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IApiUtilsService iApiUtilsService;
    @Resource
    private ISysTableService tableService;
    @Resource
    private ISysTableColumnService tableColumnService;
    @Resource
    private ISysFormService sysFormService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysDepartmentService sysDepartmentService;

    @Value("${jpmAuth.idSelect}")
    private Boolean idAuth;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdate(Map<String, Object> request, String formId, LogUserInfoVo user) {
        if (user != null && user.isEnableTenant()) //租户ID
            request.put("teId", user.getTeId());
        SysForm fm = sysFormService.getById(formId);
        if (fm == null || ValidateUtil.isEmpty(fm.getSyDataContent()))
            return BaseResponse.fail(CustomMsg.unknowFormConf);
        CustomFormDTO cf = JacksonUtils.parseObject(fm.getSyDataContent(), CustomFormDTO.class);
        if (cf == null) return BaseResponse.fail(CustomMsg.formDataFormatError);
        //主键值
        String pk = request.containsKey(cf.getPrimaryKeyCode()) ? request.get(cf.getPrimaryKeyCode()).toString() : null;
        String codeRule = null;
        String action = "create";
        Boolean create = false;//判断是否新增数据
        if (ValidateUtil.isEmpty(pk)) {
            pk = GenerateUUID.getUUID();
            create = true;
        }
        String pId = cf.getMainTableCode().equalsIgnoreCase("Pre_Project") ? pk : getStr(request, "pId");
        String secId = cf.getMainTableCode().equalsIgnoreCase("Sec_Info") ? pk : getStr(request, "secId");
        String conId = cf.getMainTableCode().equalsIgnoreCase("Con_Contract") ? pk : getStr(request, "conId");
        Date createTime = getDate(request, cf.getCreateTimeCode());
        if (create) {
            //region 创建表单数据
            if (ValidateUtil.isNull(createTime)) createTime = new Date();
            //编码
            String generateCode = null;
            //region 生成编码
            if (cf.isGenerate() && genCode(cf, request)) { //需要生成编码
                if (iApiUtilsService.sequenceRestEnable(cf.getCodeRule())) {
                    generateCode = iApiUtilsService.generateCodeByCustomized(cf.getCodeRule(), pId, conId, secId, request);
                    if (ValidateUtil.isEmpty(generateCode)) return BaseResponse.fail(CustomMsg.codeGenerError);
                    codeRule = cf.getCodeRule();
                }
            }
            //endregion
            //执行插入SQL
            sysFileService.sql2Insert(generateInsertSql(request, cf, user, generateCode, pk));
            //endregion
        } else {
            action = "update";
            //region 修改表单数据
            sysFileService.sql2Upate(generateUpdateSql(request, cf, user, pk));
            //endregion
        }
        //region 刷新附件状态
        List<FileCodeDTO> fileCodes = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getFileCodes()), FileCodeDTO.class);
        if (!CollectionUtils.isEmpty(fileCodes)) {
            List<String> relationIds = new ArrayList<>();
            List<String> fileIds = new ArrayList<>();
            for (FileCodeDTO f : fileCodes) {
                if (request.containsKey(f.getFiledKey())) {
                    if (action.equalsIgnoreCase("create")) { //新增
                        relationIds.add(getStr(request, f.getFiledKey()));
                    } else {
                        String ids = request.containsKey(f.getFiledListKey()) ? getStr(request, f.getFiledListKey()) : null;
                        if (ids == null || !ids.equalsIgnoreCase("false")) {//前端附件组件获取值失败传入false
                            relationIds.add(getStr(request, f.getFiledKey()));
                            fileIds.add(request.containsKey(f.getFiledListKey()) ? getStr(request, f.getFiledListKey()) : null);
                        }
                    }
                }
            }
            if (action.equalsIgnoreCase("create")) sysFileAnnexService.addFormRefreshRelationFile(relationIds, pk);
            else sysFileAnnexService.refreshRelationFile(relationIds, fileIds, pk);
        }
        //endregion
        //region 创建修改流程file
        sysFileService.saveFile(pk, getStr(request, "tId"), getStr(request, cf.getStateCode()), getStr(request, cf.getCreateIdCode()), getStr(request, cf.getCreateNameCode()), createTime, pId, getStr(request, "fileTitle"), getStr(request, "fileCode"), action, getStr(request, "fileRelationId"), secId, getStr(request, "tCode"), conId);
        //endregion
        //region 子表单操作
        subTableAction(request, user, cf, pk);
        //endregion
        if (ValidateUtil.isNotEmpty(codeRule))//完成表单创建后刷新编码规则序号
            iApiUtilsService.refershAutoNoByCustomized(codeRule, pId, conId, secId, request);
        //region 调用配置的接口
        if (ValidateUtil.isNotNull(cf.getCreateServiceCallApi())) {
            CreateServiceCallApiDTO postApi = JacksonUtils.parseObject(JacksonUtils.toJSONString(cf.getCreateServiceCallApi()), CreateServiceCallApiDTO.class);
            if (postApi != null) {
                Object reqObj = null;
                if (ValidateUtil.isNotEmpty(postApi.getCustomizeForm()))
                    reqObj = request.get(postApi.getCustomizeForm());
                iApiUtilsService.sendPost(postApi.getApiUrl(), pk, reqObj);
            }
        }
        //endregion
        return BaseResponse.ok(pk);
    }


    /**
     * 子表操作
     * 先根据外键删除子表数据
     * 再重新创建子表数据
     *
     * @param cf
     * @param request  请求参数
     * @param masterPk 主表主表
     */
    public void subTableAction(Map<String, Object> request, LogUserInfoVo user, CustomFormDTO cf, String masterPk) {
        if (ValidateUtil.isNotEmpty(cf.getStructureModel()) && cf.getStructureModel().equals("1"))//一对多表结果 主从表
        {
            List<CustomFormDTO> childTables = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getChildTables()), CustomFormDTO.class);
            //region 先删除子表数据
            List<String> deleteSqls = childTables.stream().map(e -> deleteChildTableDataSql(e, masterPk)).collect(Collectors.toList());
            sysFileService.deleteBath(deleteSqls);
            //endregion
            //region 重新创建子表数据
            List<String> childInserts = new ArrayList<>();
            List<String> relationIds = new ArrayList<>();
            List<String> fileIds = new ArrayList<>();
            for (CustomFormDTO cfd : childTables) {
                Object childData = request.get(cfd.getRequestField());
                if (childData != null) {
                    List<Map> childReqs = JacksonUtils.parseArray(JacksonUtils.toJSONString(childData), Map.class);
                    //List<String> pks = new ArrayList<>(); //记录主键 用于刷新附件
                    if (!CollectionUtils.isEmpty(childReqs)) {
                        for (Map map : childReqs) {
                            //子表主键，优先取前端传入值（维护场景不修改主键ID） 否则生成ID
                            String pk = GenerateUUID.getUUID();
                            if (map.containsKey(cfd.getPrimaryKeyCode())) {
                                if (ValidateUtil.isNotEmpty(map.get(cfd.getPrimaryKeyCode()).toString()))
                                    pk = map.get(cfd.getPrimaryKeyCode()).toString();
                            }
                            cfd.setForeignValue(masterPk);
                            childInserts.add(generateInsertSql(map, cfd, user, null, pk));
                            //pks.add(pk);
                            subTableFile(relationIds, fileIds, map, cfd);
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(childInserts)) sysFileService.insertBath(childInserts);
            if (!CollectionUtils.isEmpty(relationIds) && !CollectionUtils.isEmpty(fileIds))
                sysFileAnnexService.refreshRelationFile(relationIds, fileIds, masterPk);
            //endregion
        }
    }

    /**
     * 子表附件处理
     *
     * @param relationIds
     * @param fileIds
     * @param request
     * @param cf
     */
    public void subTableFile(List<String> relationIds, List<String> fileIds, Map<String, Object> request, CustomFormDTO cf) {
        List<FileCodeDTO> fileCodes = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getFileCodes()), FileCodeDTO.class);
        if (!CollectionUtils.isEmpty(fileCodes)) {
            for (FileCodeDTO f : fileCodes) {
                if (request.containsKey(f.getFiledKey())) {
                    String ids = request.containsKey(f.getFiledListKey()) ? getStr(request, f.getFiledListKey()) : null;
                    if (ids == null || !ids.equalsIgnoreCase("false")) {//前端附件组件获取值失败传入false
                        relationIds.add(getStr(request, f.getFiledKey()));
                        fileIds.add(request.containsKey(f.getFiledListKey()) ? getStr(request, f.getFiledListKey()) : null);
                    }
                }
            }
        }
    }

    /**
     * 生成删除子表数据SQL
     *
     * @param cf         子表结构信息
     * @param foreignKey 外键ID
     * @return
     */
    public String deleteChildTableDataSql(CustomFormDTO cf, String foreignKey) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ").append(cf.getMainTableCode()).append(" where ");
        sb.append(cf.getForeignKey()).append("=").append("'").append(foreignKey).append("';");
        return sb.toString();
    }

    @Override
    public BaseResponse selectList(Map<String, Object> request, String formId, LogUserInfoVo user) {
        if (user != null && user.isEnableTenant()) //租户ID
            request.put("teId", user.getTeId());
        SysForm fm = sysFormService.getById(formId);
        if (fm == null || ValidateUtil.isEmpty(fm.getSyDataContent()))
            return BaseResponse.fail(CustomMsg.unknowFormConf);
        CustomFormDTO cf = JacksonUtils.parseObject(fm.getSyDataContent(), CustomFormDTO.class);
        if (cf == null) return BaseResponse.fail(CustomMsg.formDataFormatError);
        SysTable table = tableService.getByCode(cf.getMainTableCode());
        if (table == null) throw new JPMException(CustomMsg.notSyncTable);
        List<SysTableColumn> columns = tableColumnService.getColumnListByTableCode(cf.getMainTableCode());
        if (CollectionUtils.isEmpty(columns)) throw new JPMException(CustomMsg.notSyncTableColumn);
        //todo 鉴权
        String authSql = getAuthSql(user, cf, table, columns);
        String pageSql = generateSelectSql(request, null, authSql, cf, table, columns, "list");
        //统计查询SQL
        String sumSql = generateSelectSql(request, null, authSql, cf, table, columns, "list", true);
        //匹配排序字段
        if (request.containsKey("sortFiled")) {
            List<SysTableColumn> sortColumns = columns.stream().filter(n -> n.getColumnJavaSmallHump().equalsIgnoreCase(request.get("sortFiled").toString())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(sortColumns))
                request.put("sortFiled", sortColumns.stream().findFirst().get().getColumnCode());
            else request.put("sortFiled", null);//匹配不到排序失效
        }
        return sysFileService.queryPage(pageSql, sumSql, request);
    }

    @Override
    public BaseResponse selectedFilterCriteria(Map<String, Object> request, String formId, LogUserInfoVo user) {
        if (user != null && user.isEnableTenant()) //租户ID
            request.put("teId", user.getTeId());
        SysForm fm = sysFormService.getById(formId);
        if (fm == null || ValidateUtil.isEmpty(fm.getSyDataContent()))
            return BaseResponse.fail(CustomMsg.unknowFormConf);
        CustomFormDTO cf = JacksonUtils.parseObject(fm.getSyDataContent(), CustomFormDTO.class);
        if (cf == null) return BaseResponse.fail(CustomMsg.formDataFormatError);
        List<FieldAndFilterDTO> selectColumns = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getMinTableSet()), FieldAndFilterDTO.class);
        if (CollectionUtils.isEmpty(selectColumns)) throw new JPMException(CustomMsg.formDataFormatError);
        //支持筛选的列
        List<FieldAndFilterDTO> filterColumns = selectColumns.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSelectionState()) && n.getSelectionState().equals("1")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filterColumns)) return BaseResponse.ok();
        //支持下拉选择的列
        List<FieldAndFilterDTO> selectedFilters = filterColumns.stream().filter(f -> ValidateUtil.isNotEmpty(f.getSelectionType()) && f.getSelectionType().equalsIgnoreCase("multiPrecision")).collect(Collectors.toList());
        List<FieldFormatConvertDTO> multipleQueries = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getMultipleQuery()), FieldFormatConvertDTO.class);
        List<ClassInfoAnnotation> classInfoAnnotations = new ArrayList<>();
        if (!CollectionUtils.isEmpty(multipleQueries) && !CollectionUtils.isEmpty(selectedFilters)) {
            List<FieldFormatConvertDTO> multipleQuery = multipleQueries.stream().filter(f -> selectedFilters.stream().map(FieldAndFilterDTO::getColumnCode).collect(Collectors.toList()).contains(f.getColumnCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(multipleQuery)) { //开启下拉筛选并且支持筛选的列
                SysTable table = tableService.getByCode(cf.getMainTableCode());
                if (table == null) throw new JPMException(CustomMsg.notSyncTable);
                List<SysTableColumn> columns = tableColumnService.getColumnListByTableCode(cf.getMainTableCode());
                if (CollectionUtils.isEmpty(columns)) throw new JPMException(CustomMsg.notSyncTableColumn);
                //todo 鉴权
                String authSql = getAuthSql(user, cf, table, columns);
                String pageSql = generateSelectSql(request, null, authSql, cf, table, columns, "list");
                //过滤后查询到的所有数据
                List<Map<String, Object>> data = sysFileService.queryList(pageSql);
                //null值选项
                String nullValue = sysConfigService.getConfigByCode("lowCodeSelectedSearch");
                for (int i = 0; i < multipleQuery.size(); i++) {
                    FieldFormatConvertDTO f = multipleQuery.get(i);
                    ClassInfoAnnotation classInfo = new ClassInfoAnnotation();
                    classInfo.setNo(i + 1);
                    classInfo.setType(f.getInputType());
                    classInfo.setTitle(f.getColumnName());
                    classInfo.setDbValue(f.getColumnCode());
                    classInfo.setDictionary(f.getDynamicKey());
                    classInfo.setValue(selectedFilters.stream().filter(e -> e.getColumnCode().equalsIgnoreCase(f.getColumnCode())).findFirst().get().getColumnJavaSmallHump());
                    classInfo.setDbValue(f.getColumnCode());
                    List<Object> searchValues = new ArrayList<>();
                    if (!f.getInputType().equalsIgnoreCase("date") || (ValidateUtil.isNotNull(f.getDateSearchType()) && f.getDateSearchType() == 3)) {//非时间类型 或者时间类型未下拉选择的类型
                        if (!CollectionUtils.isEmpty(data)) {
                            for (Map<String, Object> d : data) {
                                if (ValidateUtil.isNotEmpty(nullValue) && !d.containsKey(classInfo.getValue())) {
                                    if (!searchValues.contains(nullValue))
                                        searchValues.add(nullValue);
                                    continue;
                                }
                                if (d.containsKey(classInfo.getValue())) {
                                    if (!f.getInputType().equalsIgnoreCase("date")) { //非时间类型字段
                                        if (!searchValues.contains(d.get(classInfo.getValue())))
                                            searchValues.add(d.get(classInfo.getValue()));
                                    } else {
                                        //将时间戳转为指定格式
                                        String time = DateUtil.timestampToString(Long.parseLong(d.get(classInfo.getValue()).toString()), f.javaConvert());
                                        if (!searchValues.contains(time))
                                            searchValues.add(time);
                                    }
                                }
                            }
                        }
                    }
                    //将null值选项放置在最后
                    if (ValidateUtil.isNotEmpty(nullValue) && !CollectionUtils.isEmpty(searchValues)) {
                        if (searchValues.contains(nullValue)) {
                            searchValues.remove(nullValue);
                            searchValues.add(nullValue);
                        }
                    }
                    classInfo.setDictionaryValues(searchValues.stream().distinct().collect(Collectors.toList()));
                    classInfoAnnotations.add(classInfo);
                }
            }
        }
        return BaseResponse.ok(classInfoAnnotations);
    }

    @Override
    public BaseResponse selectById(String id, String formId, LogUserInfoVo user) {
        SysForm fm = sysFormService.getById(formId);
        if (fm == null || ValidateUtil.isEmpty(fm.getSyDataContent()))
            return BaseResponse.fail(CustomMsg.unknowFormConf);
        CustomFormDTO cf = JacksonUtils.parseObject(fm.getSyDataContent(), CustomFormDTO.class);
        if (cf == null) return BaseResponse.fail(CustomMsg.formDataFormatError);
        SysTable table = tableService.getByCode(cf.getMainTableCode());
        if (table == null) throw new JPMException(CustomMsg.notSyncTable);
        List<SysTableColumn> columns = tableColumnService.getColumnListByTableCode(cf.getMainTableCode());
        if (CollectionUtils.isEmpty(columns)) throw new JPMException(CustomMsg.notSyncTableColumn);
        //鉴权
        String authSql = " WHERE 1=1 ";
        if (ValidateUtil.isNotNull(idAuth) && idAuth) {
            authSql = getAuthSql(user, cf, table, columns);
        }
        return BaseResponse.ok(getInfoById(id, cf, table, columns, authSql));
    }

    /**
     * 鉴权SQL
     *
     * @return
     */
    private String getAuthSql(LogUserInfoVo user, CustomFormDTO cf, SysTable table, List<SysTableColumn> columns) {
        if (ValidateUtil.isNull(cf.getAuthentication())) throw new JPMException(CustomMsg.authTypeNull);
        if (ValidateUtil.isEmpty(user.getUType())) throw new JPMException(CustomMsg.uTypeNull);
        String createUid = null;
        String stateCode = null;
        String pk = null;
        List<SysTableColumn> pks = columns.stream().filter(n -> ValidateUtil.isNotEmpty(n.getColumnJavaSmallHump()) && n.getColumnJavaSmallHump().equalsIgnoreCase(cf.getPrimaryKeyCode())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(pks)) pk = pks.get(0).getColumnCode();
        if (ValidateUtil.isEmpty(pk)) throw new JPMException(CustomMsg.columnNotPk);
        List<SysTableColumn> createIds = columns.stream().filter(n -> ValidateUtil.isNotEmpty(n.getColumnJavaSmallHump()) && n.getColumnJavaSmallHump().equalsIgnoreCase(cf.getCreateIdCode())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(createIds)) createUid = createIds.get(0).getColumnCode();
        List<SysTableColumn> stateCodes = columns.stream().filter(n -> ValidateUtil.isNotEmpty(n.getColumnJavaSmallHump()) && n.getColumnJavaSmallHump().equalsIgnoreCase(cf.getStateCode())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(stateCodes)) stateCode = stateCodes.get(0).getColumnCode();
        if (ValidateUtil.isAnObjEmpty(createUid, stateCode)) throw new JPMException(CustomMsg.createUidStateNotOnly);
        String authSql;
        //配置为不健全
        if (cf.getAuthentication() == -1 || noAuthConf(user, cf)) {
            authSql = " where 1=1 " + sysUserService.ShowCancelData(user.getId(), createUid, stateCode);
        }
        //配置为仅查看当前操作人创建的记录
        else if (cf.getAuthentication().equals(OutUserAuthEnum.Only_Creator.getIndex())) {
            authSql = " where " + DataAuthSqlUtil.dbCreateFiledAlias(createUid) + "='" + user.getId() + "'" + sysUserService.ShowCancelData(user.getId(), createUid, stateCode);
        }
        //配置为仅限创建人与审批人查看
        else if (cf.getAuthentication().equals(OutUserAuthEnum.Approver_Creator.getIndex())) {
            authSql = approverCreatorAuth(createUid, user, pk) + sysUserService.ShowCancelData(user.getId(), createUid, stateCode);
        }
        //外部用户 且无权
        else if ((cf.getAuthentication().equals(OutUserAuthEnum.NOT.getIndex()) || cf.getAuthentication().equals(OutUserAuthEnum.Only_Inner.getIndex())) && user.getUType().equals(UserTypeEnum.Out_User.getIndex().toString())) {
            throw new JPMException(CustomMsg.noPermission);
        }
        //内部用户且不鉴权
        else if (cf.getAuthentication().equals(OutUserAuthEnum.Only_Inner.getIndex()) && user.getUType().equals(UserTypeEnum.Inner_User.getIndex().toString())) {
            authSql = " where 1=1 " + sysUserService.ShowCancelData(user.getId(), createUid, stateCode);
        }
        // 其他
        else {
            return sysUserService.getAuthSql(table.getTableCode(), user.getId(), createUid, cf.getAuthentication(), stateCode, getContractAlise(cf));
        }
        // 针对特定用户与角色放开权限
        return sysUserService.showAllDataAuth(user.getId(), createUid, authSql);
    }


    private String approverCreatorAuth(String createUid, LogUserInfoVo user, String pk) {
        String mainAlias = DataAuthSqlUtil.alias(createUid) + ".";
        StringBuilder sb = new StringBuilder();
        //创建人
        sb.append(" where (").append(DataAuthSqlUtil.dbCreateFiledAlias(createUid)).append("='").append(user.getId()).append("'");
        //待审批人
        sb.append(" or (select count(*) from sys_task fstk where fstk.taskuser='").append(user.getId()).append("' and fstk.taskentityid=").append(mainAlias).append(pk).append(")>0");
        //已审批人
        sb.append(" or (select count(*) from SYS_TASK_PROCESSIN fstp where fstp.tp_user='").append(user.getId()).append("' and fstp.tp_entityid=").append(mainAlias).append(pk).append(")>0");
        //抄送
        sb.append(" or (select count(*) from SYS_CARBONCOPY fscy where fscy.cc_user='").append(user.getId()).append("' and fscy.cc_fileid=").append(mainAlias).append(pk).append(")>0");
        //待沟通
        sb.append(" or (select count(*) from SYS_COORDINATIONTASK fsct where fsct.ct_user='").append(user.getId()).append("' and fsct.ct_fileid=").append(mainAlias).append(pk).append(")>0");
        //已沟通
        sb.append(" or (select count(*) from SYS_COORDINATIONPROCESS fscp where fscp.cp_user='").append(user.getId()).append("' and fscp.cp_fileid=").append(mainAlias).append(pk).append(")>0");
        sb.append(")");
        return sb.toString();
    }

    /**
     * 外部单位鉴权 获取链表合同别名
     *
     * @param cf
     * @return
     */
    private String getContractAlise(CustomFormDTO cf) {
        List<LinkedRelationDTO> relations = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getDataTables()), LinkedRelationDTO.class);
        if (!CollectionUtils.isEmpty(relations)) {
            //排除主表
            List<LinkedRelationDTO> lbTables = relations.stream().filter(f -> ValidateUtil.isEmpty(f.getAssociationType()) || !f.getAssociationType().equals("主表"))
                    .sorted(Comparator.comparing(LinkedRelationDTO::getNum)).collect(Collectors.toList());
            for (int i = 0; i < lbTables.size(); i++) {
                LinkedRelationDTO r = lbTables.get(i);
                //链表关系ID 前端生成 非表管理ID
                List<LinkedRelationDTO> leftTb = relations.stream().filter(n -> n.getId().equals(r.getAssociationTableId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(leftTb)) {
                    LinkedRelationDTO left = leftTb.get(0);
                    if (r.getTableCode().equalsIgnoreCase("con_contract") && left.getTableAlias().equalsIgnoreCase("mn"))
                        return r.getTableAlias();
                }
            }
        }
        return null;
    }

    /**
     * 配置了指定部门或者用户不鉴权
     *
     * @param user
     * @param cf
     * @return
     */
    private Boolean noAuthConf(LogUserInfoVo user, CustomFormDTO cf) {
        //指定用户不鉴权
        if (!CollectionUtils.isEmpty(cf.getNoAuthUsers()) && cf.getNoAuthUsers().contains(user.getId()))
            return true;
        if (ValidateUtil.isEmpty(user.getUType()) && user.getUType().equals("1")
                && !CollectionUtils.isEmpty(cf.getNoAuthDeps()))//内部用户并且指定部门不鉴权
        {
            List<String> deps = sysDepartmentService.getUserDepIdsByUid(user.getId());
            if (!CollectionUtils.isEmpty(deps)) {
                List<String> dep = cf.getNoAuthDeps().stream().filter(deps::contains).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(dep)) return true;
            }
        }
        return false;
    }


    /**
     * 生成修改数据SQL
     *
     * @param user 操作用户信息
     * @param pk   主键ID
     * @return
     */
    private String generateUpdateSql(Map<String, Object> request, CustomFormDTO cf, LogUserInfoVo user, String pk) {
        SysTable table = tableService.getByCode(cf.getMainTableCode());
        if (table == null) throw new JPMException(CustomMsg.notSyncTable);
        List<SysTableColumn> columns = tableColumnService.getColumnListByTableCode(cf.getMainTableCode());
        if (CollectionUtils.isEmpty(columns)) throw new JPMException(CustomMsg.notSyncTableColumn);
        Map<String, Object> old = getInfoById(pk, cf, table, columns, null);
        if (old == null) throw new JPMException(CustomMsg.infoNotExist);
        //基础信息固定字段
        List<String> baseCode = Arrays.asList(cf.getPrimaryKeyCode(), cf.getCodeField(), cf.getCreateIdCode(), cf.getCreateNameCode(), cf.getCreateTimeCode(), cf.getCodeField());
        //所有有值且匹配上的列
        List<SysTableColumn> allColumns = new ArrayList<>();
        for (SysTableColumn column : columns) {
            if (baseCode.contains(column.getColumnJavaSmallHump()) || request.containsKey(column.getColumnJavaSmallHump()))
                allColumns.add(column);
        }
        allColumns = allColumns.stream().distinct().collect(Collectors.toList());
        //所有赋值的SQL 格式: 字段='值'
        List<String> upSql = new ArrayList<>();
        for (SysTableColumn c : allColumns) {
            Object value = getField(request, c, cf, user, old.get(cf.getCodeField()) + "", pk, "update", old);
            if (ValidateUtil.isNull(c.getColumnFile()) || !c.getColumnFile() || ValidateUtil.isNotNull(value)) { //表单附件字段修改时不允许修改为null
                if (ValidateUtil.isNotNull(value)) upSql.add(c.getColumnCode() + "='" + value + "'");
                else upSql.add(c.getColumnCode() + "=null");
            }
        }
        List<SysTableColumn> pks = columns.stream().filter(n -> ValidateUtil.isNotEmpty(n.getColumnJavaSmallHump()) && n.getColumnJavaSmallHump().equalsIgnoreCase(cf.getPrimaryKeyCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pks)) throw new JPMException(CustomMsg.columnNotPk);

        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(table.getTableCode()).append(" set ");
        sb.append(String.join(",", upSql));
        sb.append(" where ").append(pks.get(0).getColumnCode()).append("='");
        sb.append(pk).append("';");
        return sb.toString();
    }


    /**
     * 生成插入数据SQL
     *
     * @param code 生成的编码
     * @param pk   主键ID
     * @return
     */
    private String generateInsertSql(Map<String, Object> request, CustomFormDTO cf, LogUserInfoVo user, String code, String pk) {
        SysTable table = tableService.getByCode(cf.getMainTableCode());
        if (table == null) throw new JPMException(CustomMsg.notSyncTable);
        List<SysTableColumn> columns = tableColumnService.getColumnListByTableCode(cf.getMainTableCode());
        if (CollectionUtils.isEmpty(columns)) throw new JPMException(CustomMsg.notSyncTableColumn);
        //insert语句values内字符串
        StringBuilder valueStr = new StringBuilder();
        //基础信息固定字段
        List<String> baseCode = Arrays.asList(cf.getPrimaryKeyCode(), cf.getCodeField(), cf.getCreateIdCode(), cf.getCreateNameCode(), cf.getCreateTimeCode());
        //所有有值且匹配上的列
        List<SysTableColumn> allColumns = new ArrayList<>();
        for (SysTableColumn column : columns) {
            if (baseCode.contains(column.getColumnJavaSmallHump()) || request.containsKey(column.getColumnJavaSmallHump()))
                allColumns.add(column);
        }
        //去重
        allColumns = allColumns.stream().distinct().collect(Collectors.toList());
        //获取数据库字段集合
        List<String> sqlKey = new ArrayList<>();
        for (SysTableColumn column : allColumns) {
            //列名
            sqlKey.add(column.getColumnCode());
            //region Values部分SQL字符串
            Object value = getField(request, column, cf, user, code, pk, "create", null);
            if (valueStr.length() < 1) {
                if (ValidateUtil.isNull(value)) valueStr.append("null");
                else valueStr.append("'").append(value).append("'");
            } else {
                if (ValidateUtil.isNull(value)) valueStr.append(",").append("null");
                else valueStr.append(",'").append(value).append("'");
            }
            //endregion
        }
        //子表赋值主表ID值
        if (!ValidateUtil.isAnObjEmpty(cf.getForeignKey(), cf.getForeignValue())) {
            if (!sqlKey.contains(cf.getForeignKey())) {
                sqlKey.add(cf.getForeignKey());
                valueStr.append(",'").append(cf.getForeignValue()).append("'");
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(table.getTableCode()).append("(");
        //列名
        sb.append(String.join(",", sqlKey));
        sb.append(")").append(" VALUES(");
        //列对应值
        sb.append(valueStr);
        sb.append(")");
        return sb.toString();
    }

    /**
     * 新增修改从请求参数中获取对应值
     * 新增修改场景部分值不从入参内读取
     * 时间类型字段值需要格式化成指定格式
     *
     * @param request 请求body
     * @param column  当前列
     * @param cf      自定义表单配置
     * @param user    当前操作用户信息
     * @param code    生成的编码值
     * @param pk      主键ID
     * @param action  当前操作类型 新增create修改update
     * @param old     修改操作老的数据表单
     * @return
     */
    private Object getField(Map<String, Object> request, SysTableColumn column, CustomFormDTO cf, LogUserInfoVo user, String code, String pk, String action, Map<String, Object> old) {
        Object value = request.get(column.getColumnJavaSmallHump());
        //外键值
        if (!ValidateUtil.isAnObjEmpty(cf.getForeignKey(), cf.getForeignValue()) && column.getColumnCode().equals(cf.getForeignKey()))
            return cf.getForeignValue();
        if (column.getColumnJavaSmallHump().equalsIgnoreCase(cf.getPrimaryKeyCode())) value = pk;//主键ID
        else if (column.getColumnJavaSmallHump().equalsIgnoreCase(cf.getCodeField())) value = code;//编码
        else if (column.getColumnJavaSmallHump().equalsIgnoreCase(cf.getCreateIdCode()))//创建人ID
            value = action.equalsIgnoreCase("create") ? user.getId() : old.get(cf.getCreateIdCode());
        else if (column.getColumnJavaSmallHump().equalsIgnoreCase(cf.getCreateNameCode()))//创建人名
            value = action.equalsIgnoreCase("create") ? user.getName() : old.get(cf.getCreateNameCode());
        else if (column.getColumnJavaSmallHump().equalsIgnoreCase(cf.getCreateTimeCode()))//创建使时间
            value = action.equalsIgnoreCase("create") ? getDateStr(new Date().getTime()) : getDateStr(obj2Long(old.get(cf.getCreateTimeCode())));
        else if (column.getColumnJavaSmallHump().equalsIgnoreCase(cf.getStateCode())) {//审批状态
            if (action.equalsIgnoreCase("create") && ValidateUtil.isNull(request.get(cf.getStateCode())))
                value = ApprovalStatusEnum.Not_Started.getIndex();
            else if (action.equalsIgnoreCase("update")) value = old.get(cf.getStateCode());
        } else if (column.getColumnJavaSmallHump().equalsIgnoreCase(cf.getStateTimeCode())) {//流程审批时间
            if (action.equalsIgnoreCase("create") && ValidateUtil.isNull(request.get(cf.getStateTimeCode())))
                value = getDateStr(new Date().getTime());
            else if (action.equalsIgnoreCase("update")) value = getDateStr(obj2Long(old.get(cf.getStateTimeCode())));
        } else if (column.getColumnType().equalsIgnoreCase("TIMESTAMP") || column.getColumnType().equalsIgnoreCase("DATETIME"))
            value = getDateStr(value);
        else if (column.getColumnType().equalsIgnoreCase("date")) //日期格式
            value = getDateStr(value, "yyyy-MM-dd");
        else if (column.getColumnType().equalsIgnoreCase("time")) //时间格式
            value = getDateStr(value, "HH:mm:ss");
        else if (column.getColumnType().equalsIgnoreCase("boolean") || column.getColumnType().equalsIgnoreCase("smallint")) {
            if (ValidateUtil.isEmpty(value)) value = null;
            if (value.toString().equalsIgnoreCase("true")) value = 1;
            if (value.toString().equalsIgnoreCase("false")) value = 0;
        } else if (column.getColumnType().equalsIgnoreCase("float") || column.getColumnType().equalsIgnoreCase("decimal")
                || column.getColumnType().equalsIgnoreCase("integer") || column.getColumnType().equalsIgnoreCase("int"))//数值类型参数
        {
            //入参为空字符
            if (ValidateUtil.isEmpty(value)) return null;
        }
        if (ValidateUtil.isNotNull(value) && specialChar(value.toString())) {//包含特殊字符
            value = JacksonUtils.toJSONString(value);
        }
        return value;
    }

    /**
     * 生成查询SQL(非统计查询)
     */
    private String generateSelectSql(Map<String, Object> request, String id, String authSql, CustomFormDTO cf, SysTable table,
                                     List<SysTableColumn> columns, String action) {
        return generateSelectSql(request, id, authSql, cf, table, columns, action, false);
    }

    /**
     * 生成查询语句SQL
     *
     * @param request action 为分页查询时 请求body参数
     * @param id      action 为id查询时 id参数
     * @param table   主表信息
     * @param columns 主表的所有列信息
     * @param action  操作类型 id:id查询  list:分页查询  foreign:外键ID查询子表信息
     * @param authSql 鉴权SQL
     * @param tjQuery 是否生成统计查询SQL
     * @return
     */
    private String generateSelectSql(Map<String, Object> request, String id, String authSql, CustomFormDTO cf, SysTable table, List<SysTableColumn> columns, String action, Boolean tjQuery) {
        if (CollectionUtils.isEmpty(columns)) throw new JPMException(CustomMsg.notSyncTable);
        List<FieldAndFilterDTO> selectColumns = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getMinTableSet()), FieldAndFilterDTO.class);
        if (CollectionUtils.isEmpty(selectColumns)) throw new JPMException(CustomMsg.formDataFormatError);
        //查询的数据列 字符串格式 mn.id as id
        List<String> tjColumnSql = new ArrayList<>();//统计列
        List<String> selectColumnSql = selectColumns.stream().map(s -> {
            //响应格式达梦大字段jackson无法直接序列化
            String sql = s.getTableAlias() + "." + s.getColumnCode();
            if (tjQuery) {//统计查询
                String fun = TjQueryEnum.getFun(s.getTjQuery());//方法函数
                if (ValidateUtil.isNotEmpty(fun)) {//不为空则表示当前列配置需要统计
                    tjColumnSql.add(fun + "(" + sql + ")" + " as \"" + s.getColumnJavaSmallHump() + "\"");
                }
            }
//            if (s.getColumnType().equalsIgnoreCase("TEXT") || s.getColumnType().equalsIgnoreCase("LONGVARCHAR") || s.getColumnType().equalsIgnoreCase("CLOB"))
//                sql = "CONCAT(" + sql + ",'')";
            //时间转为毫秒时间戳
            if (s.getColumnType().equalsIgnoreCase("DATETIME") || s.getColumnType().equalsIgnoreCase("TIMESTAMP"))
                sql = "(case when " + sql + " is null then null else " + "UNIX_TIMESTAMP(" + sql + ")*1000 end)";
            return sql + " as \"" + s.getColumnJavaSmallHump() + "\"";
        }).collect(Collectors.toList());
        List<SysTableColumn> pks = columns.stream().filter(n -> ValidateUtil.isNotEmpty(n.getColumnJavaSmallHump()) && n.getColumnJavaSmallHump().equalsIgnoreCase(cf.getPrimaryKeyCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pks)) throw new JPMException(CustomMsg.columnNotPk);
        List<LinkedRelationDTO> relations = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getDataTables()), LinkedRelationDTO.class);
        //region 组装查询SQL
        StringBuilder sb = new StringBuilder();
        if (tjQuery) {//统计查询
            if (CollectionUtils.isEmpty(tjColumnSql)) return null;//统计查询没有列直接返回
            sb.append("select ").append(String.join(",", tjColumnSql));
        } else
            sb.append("select ").append(String.join(",", selectColumnSql));
        sb.append(" from ").append(table.getTableCode()).append(" mn ");
        //region 链表关系SQL
        if (!CollectionUtils.isEmpty(relations)) {
            //排除主表
            List<LinkedRelationDTO> lbTables = relations.stream().filter(f -> ValidateUtil.isEmpty(f.getAssociationType()) || !f.getAssociationType().equals("主表"))
                    .sorted(Comparator.comparing(LinkedRelationDTO::getNum)).collect(Collectors.toList());
            for (int i = 0; i < lbTables.size(); i++) {
                LinkedRelationDTO r = lbTables.get(i);
                //链表关系ID 前端生成 非表管理ID
                List<LinkedRelationDTO> leftTb = relations.stream().filter(n -> n.getId().equals(r.getAssociationTableId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(leftTb)) {
                    LinkedRelationDTO left = leftTb.get(0);
                    sb.append(" left join ").append(r.getTableCode()).append(" ").append(r.getTableAlias()).append(" on ").append(left.getTableAlias()).append(".").append(r.getAssociationField()).append("=").append(r.getTableAlias()).append(".").append(r.getTableField());
                }
            }
        }
        //endregion
        if (action.equalsIgnoreCase("id"))//id查询SQL组成
        {
            if (ValidateUtil.isEmpty(authSql)) {
                sb.append(" where mn." + pks.get(0).getColumnCode()).append("='").append(id).append("';");
            } else {
                sb.append(" ").append(authSql).append(" and mn." + pks.get(0).getColumnCode()).append("='").append(id).append("';");
            }
        }
        if (action.equalsIgnoreCase("foreign"))//外键id查询SQL组成(查询子表数据)
            sb.append(" where mn." + cf.getForeignKey()).append("='").append(id).append("';");
        else if (action.equalsIgnoreCase("list"))//分页查询
        {
            log.info("鉴权SQL组成部分:" + authSql);
            if (ValidateUtil.isEmpty(authSql)) sb.append(" where 1=1");
            else sb.append(" ").append(authSql);
            //需要支持过滤的列
            List<FieldAndFilterDTO> filterColumns = selectColumns.stream().filter(n -> ValidateUtil.isNotEmpty(n.getSelectionState()) && n.getSelectionState().equals("1")).collect(Collectors.toList());
            Boolean historyName = historySearchEnabled();
            //下拉过滤选择转换条件
            List<FieldFormatConvertDTO> multipleQueries = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getMultipleQuery()), FieldFormatConvertDTO.class);
            for (FieldAndFilterDTO c : filterColumns) {
                if (request.containsKey(c.getColumnJavaSmallHump()) && ValidateUtil.isNotNull(request.get(c.getColumnJavaSmallHump()))) {
                    //入参包含此列
                    if (c.getSelectionType().equalsIgnoreCase("accurate"))//全匹配
                    {
                        sb.append(" and ").append(c.getTableAlias()).append(".").append(c.getColumnCode());
                        sb.append("='").append(injectConversion(request.get(c.getColumnJavaSmallHump()))).append("'");
                    } else if (c.getSelectionType().equalsIgnoreCase("vague"))//模糊匹配
                    {
                        if (historyName && ValidateUtil.isNotEmpty(c.getTableCode())
                                && (c.getTableCode().equalsIgnoreCase("sys_company")
                                || c.getTableCode().equalsIgnoreCase("pre_project"))) {
                            if (c.getTableCode().equalsIgnoreCase("sys_company")) {
                                sb.append(" and ").append("likeCname('")
                                        .append(injectConversion(request.get(c.getColumnJavaSmallHump()))).append("',").append(c.getTableAlias())
                                        .append(".c_id)=true");
                            } else {
                                sb.append(" and ").append("likePname('")
                                        .append(injectConversion(request.get(c.getColumnJavaSmallHump()))).append("',").append(c.getTableAlias())
                                        .append(".p_id)=true");
                            }
                        } else {
                            sb.append(" and ").append(c.getTableAlias()).append(".").append(c.getColumnCode());
                            sb.append(" like '%").append(injectConversion(request.get(c.getColumnJavaSmallHump()))).append("%'");
                        }
                    } else if (c.getSelectionType().equalsIgnoreCase("interval"))//区间匹配
                    {
                        List<String> qj = JacksonUtils.parseArray(JacksonUtils.toJSONString(request.get(c.getColumnJavaSmallHump())), String.class);
                        if (!CollectionUtils.isEmpty(qj)) {
                            boolean time = c.getColumnType().equalsIgnoreCase("TIMESTAMP") || c.getColumnType().equalsIgnoreCase("DATETIME");
                            //前端传进来的是标准格式字符串不需要后端转换
                            // boolean time = false;
                            if (!CollectionUtils.isEmpty(qj)) {
                                if (qj.size() >= 1 && !ValidateUtil.isEmpNum(qj.get(0))) {
                                    sb.append(" and ").append(c.getTableAlias()).append(".").append(c.getColumnCode());
                                    // sb.append(" >= '").append(injectConversion(time ? getDateStr(qj.get(0)) : qj.get(0))).append("'");
                                    if (time && qj.get(0).length() == 10) {
                                        sb.append(" >= '").append(injectConversion(qj.get(0) + " 00:00:00")).append("'");
                                    } else {
                                        sb.append(" >= '").append(injectConversion(qj.get(0))).append("'");
                                    }
                                }
                                if (qj.size() >= 2 && !ValidateUtil.isEmpNum(qj.get(1))) {
                                    sb.append(" and ").append(c.getTableAlias()).append(".").append(c.getColumnCode());
                                    // sb.append(" <= '").append(injectConversion(time ? getDateStr(qj.get(1)) : qj.get(1))).append("'");
                                    if (time && qj.get(1).length() == 10) {
                                        sb.append(" <= '").append(injectConversion(qj.get(1) + " 23:59:59")).append("'");
                                    } else {
                                        sb.append(" <= '").append(injectConversion(qj.get(1))).append("'");
                                    }
                                }
                            }
                        }
                    } else if (c.getSelectionType().equalsIgnoreCase("multiPrecision") && !CollectionUtils.isEmpty(multipleQueries))//下拉选择类型过滤
                    {
                        List<FieldFormatConvertDTO> currentFormats = multipleQueries.stream().filter(n -> n.getColumnCode().equalsIgnoreCase(c.getColumnCode())).collect(Collectors.toList());
                        String nullValue = sysConfigService.getConfigByCode("lowCodeSelectedSearch");
                        Boolean haveNull = false;
                        if (!CollectionUtils.isEmpty(currentFormats)) {
                            FieldFormatConvertDTO currentFormat = currentFormats.stream().findFirst().get();
                            if (!currentFormat.getInputType().equalsIgnoreCase("date"))//非时间类型
                            {
                                //入参为多选类型数组集合
                                List<String> sz = JacksonUtils.parseArray(JacksonUtils.toJSONString(request.get(c.getColumnJavaSmallHump())), String.class);
                                if (!CollectionUtils.isEmpty(sz)) {
                                    if (ValidateUtil.isNotEmpty(nullValue) && sz.contains(nullValue)) {
                                        haveNull = true;
                                        sz.remove(nullValue);
                                    }
                                    if (!CollectionUtils.isEmpty(sz)) {
                                        sb.append(" and ");
                                        if (haveNull) sb.append("(");
                                        sb.append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(" in ( ");
                                        for (int i = 0; i < sz.size(); i++) {
                                            sb.append("'").append(injectConversion(sz.get(i))).append("'");
                                            if (i < sz.size() - 1)
                                                sb.append(",");
                                        }
                                        sb.append(" ) ");
                                        if (haveNull)
                                            sb.append(" or ").append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(" is NULL)");
                                    } else {
                                        sb.append(" and ").append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(" is NULL");
                                    }
                                }
                            } else {//时间类型筛选
                                if (currentFormat.getDateSearchType() == 1) {//范围查询
                                    List<String> fw = JacksonUtils.parseArray(JacksonUtils.toJSONString(request.get(c.getColumnJavaSmallHump())), String.class);
                                    if (!CollectionUtils.isEmpty(fw)) {
                                        if (ValidateUtil.isNotEmpty(fw.get(0))) {
                                            sb.append(" and DATE_FORMAT(").append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(",'").append(currentFormat.formatConvert()).append("') >= '")
                                                    .append(injectConversion(fw.get(0))).append("' ");
                                        }
                                        if (fw.size() > 1 && ValidateUtil.isNotEmpty(fw.get(1))) {
                                            sb.append(" and DATE_FORMAT(").append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(",'").append(currentFormat.formatConvert()).append("') <= '")
                                                    .append(injectConversion(fw.get(1))).append("' ");
                                        }
                                    }
                                } else if (currentFormat.getDateSearchType() == 2) {//时间单选
                                    sb.append(" and DATE_FORMAT(").append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(",'").append(currentFormat.formatConvert()).append("') = '")
                                            .append(injectConversion(request.get(c.getColumnJavaSmallHump()))).append("' ");
                                } else if (currentFormat.getDateSearchType() == 3) {//数据下拉选择
                                    List<String> sz = JacksonUtils.parseArray(JacksonUtils.toJSONString(request.get(c.getColumnJavaSmallHump())), String.class);
                                    if (!CollectionUtils.isEmpty(sz)) {
                                        if (ValidateUtil.isNotEmpty(nullValue) && sz.contains(nullValue)) {
                                            haveNull = true;
                                            sz.remove(nullValue);
                                        }
                                        if (!CollectionUtils.isEmpty(sz)) {
                                            sb.append(" and ");
                                            if (haveNull) sb.append("(");
                                            sb.append(" DATE_FORMAT(").append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(",'").append(currentFormat.formatConvert()).append("') in ( ");
                                            for (int i = 0; i < sz.size(); i++) {
                                                sb.append("'").append(sz.get(i)).append("'");
                                                if (i < sz.size() - 1)
                                                    sb.append(",");
                                            }
                                            sb.append(" ) ");
                                            if (haveNull)
                                                sb.append(" or ").append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(" is NULL)");
                                        } else {
                                            sb.append(" and ").append(c.getTableAlias()).append(".").append(c.getColumnCode()).append(" is NULL");
                                        }
                                    }
                                } else
                                    log.error("暂不支持的时间筛选类型[{0}]", currentFormat.getDateSearchType());
                            }
                        }
                    } else
                        sb.append("='").append(injectConversion(request.get(c.getColumnJavaSmallHump()))).append("'");
                }
            }
        }
        //endregion
        return sb.toString();
    }

    /**
     * id查询
     *
     * @return
     */
    private Map<String, Object> getInfoById(String id, CustomFormDTO cf, SysTable table, List<SysTableColumn> columns, String authSql) {
        String querySQL = generateSelectSql(null, id, authSql, cf, table, columns, "id");
        Map<String, Object> data = sysFileService.querySql(querySQL);
        //region 一对多 主从表 查询子表数据
        if (ValidateUtil.isNotEmpty(cf.getStructureModel()) && cf.getStructureModel().equals("1")) //一对多 主从表
        {
            if (!CollectionUtils.isEmpty(cf.getChildTables())) {
                List<CustomFormDTO> childTables = JacksonUtils.parseArray(JacksonUtils.toJSONString(cf.getChildTables()), CustomFormDTO.class);
                if (!CollectionUtils.isEmpty(childTables)) {
                    for (CustomFormDTO cfd : childTables) {
                        if (!ValidateUtil.isAnObjEmpty(cfd.getMainTableCode(), cfd.getRequestField())) {
                            SysTable tb = tableService.getByCode(cfd.getMainTableCode());
                            if (tb == null) throw new JPMException(CustomMsg.notSyncTable);
                            List<SysTableColumn> cms = tableColumnService.getColumnListByTableCode(cfd.getMainTableCode());
                            if (CollectionUtils.isEmpty(cms)) throw new JPMException(CustomMsg.notSyncTableColumn);
                            List<Map<String, Object>> childs = sysFileService.queryList(generateSelectSql(null, id, null, cfd, tb, cms, "foreign"));
                            data.put(cfd.getRequestField(), childs);
                        }
                    }
                }
            }
        }
        //endregion
        return data;
    }

    private String getStr(Map<String, Object> request, String key) {
        if (ValidateUtil.isNull(request)) return null;
        if (request.containsKey(key) && ValidateUtil.isNotNull(request.get(key))) return request.get(key).toString();
        else return null;
    }

    private Date getDate(Map<String, Object> request, String key) {
        if (ValidateUtil.isNull(request)) return null;
        try {
            if (request.containsKey(key) && ValidateUtil.isNotNull(request.get(key)))
                return new Date(Long.parseLong(request.get(key) + ""));
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("时间字段取值错误", ex);
            throw new JPMException(CustomMsg.dateFormatError);
        }
        return null;
    }

    /**
     * 转时间字符串
     *
     * @param obj 毫秒
     * @return
     */
    private String getDateStr(Object obj) {
        return getDateStr(obj, "yyyy-MM-dd HH:mm:ss");
    }

    private String getDateStr(Object obj, String format) {
        if (ValidateUtil.isEmpty(obj)) return null;
        try {
            return DateUtil.Date2String(new Date(Long.parseLong(obj + "")), format);
        } catch (Exception ex) {
            log.error("DateTime value:" + obj, ex);
            ex.printStackTrace();
            throw new JPMException(CustomMsg.dateFormatError);
        }
    }

    /**
     * 时间戳值转换为Long
     * 数据库查询数值带 .000小数位
     *
     * @param obj
     * @return
     */
    private Long obj2Long(Object obj) {
        if (ValidateUtil.isEmpty(obj)) return null;
        BigDecimal dic = new BigDecimal(obj.toString());
        return dic.longValue();
    }


    /**
     * 规则配置入参值为XX时不生成编码
     *
     * @param cf
     * @param data
     * @return
     */
    private Boolean genCode(CustomFormDTO cf, Map<String, Object> data) {
        if (ValidateUtil.isEmpty(cf.getIgnoreCode()) || CollectionUtils.isEmpty(cf.getIgnoreCodeValues()))
            return true;
        if (!data.containsKey(cf.getIgnoreCode())) return true;
        String str = data.get(cf.getIgnoreCode()).toString();
        if (cf.getIgnoreCodeValues().contains(str)) return false;
        return true;
    }

    @Resource
    private ISysConfigService sysConfigService;

    public Boolean historySearchEnabled() {
        String enable = sysConfigService.getConfigByCode("projectCompanyHistoryNameSearch");
        if (ValidateUtil.isEmpty(enable)) {
            return false;
        } else if (enable.equals("1")) {
            return true;
        } else
            return false;
    }


    /**
     * 验证参数特殊字符 防止注入
     *
     * @param str
     * @return
     */
    private boolean specialChar(String str) {
        if (str.contains("'")) return true;
        if (str.contains("\\\"")) return true;
        return false;
    }


    private String injectConversion(Object value) {
        String input = value + "";
        if (ValidateUtil.isEmpty(input)) {
            return null;
        }
        return StringEscapeUtils.escapeSql(input);
    }
}