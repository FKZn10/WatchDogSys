package com.jpmsoft.projectProducer.utils.extension;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.ClazzUtil;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程表单创建更新方法
 * 根据入参注解识别（流程状态创建人等字段不为统一字段）
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/3 10:14
 */
public abstract class FileImpl<M extends BaseMapper<T>, T, R extends BaseFileRequest> extends ServiceImpl<M, T> {

    @Resource
    protected ISysFileService sysFileService;
    @Resource
    protected UserProducerFeign userProducerFeign;
    @Resource
    protected ISysFileAnnexService sysFileAnnexService;

    /**
     * 流程表单创建逻辑
     *
     * @param entity  表单实体
     * @param request 请求入参
     * @return 返回表单主键ID
     */
    protected String createOrUpdateFileEntity(T entity, R request) {
        String fileAction = "create";
        //BeanUtils.copyProperties(request,entity);
        List<Field> reqFields = ClazzUtil.getAllFields(request.getClass());
        //region 入参
        String id = null;
        Integer state = null;
        String createUid = null;
        String createUname = null;
        Date createTime = null;
        //endregion
        //region 字段名
        String createTimeField = null;
        String stateField = null;
        String stateTimeField = null;
        String createUidField = null;
        String createUnameField = null;
        //endregion
        //附件字段值
        Map<Integer, String> relationIds = new HashMap<>();
        Map<Integer, String> fileIds = new HashMap<>();
        //region 取注解标注的字段值 与 字段名
        for (Field f : reqFields) {
            // 判断字段注解是否存在
            boolean annotationPresent2 = f.isAnnotationPresent(ProcessFields.class);
            boolean fileAnnotation = f.isAnnotationPresent(FileFields.class);
            //region 获取附件字段
            if (fileAnnotation) {
                FileFields field = f.getAnnotation(FileFields.class);
                int index = field.index();
                Object fieldValue = getFileValue(f, request);
                if (field.type().equals("fileRelationId")) {
                    relationIds.put(index, ValidateUtil.isNull(fieldValue) ? null : fieldValue.toString());
                } else {
                    fileIds.put(index, ValidateUtil.isNull(fieldValue) ? null : fieldValue.toString());
                }
            }
            //endregion
            //region 获取流程字段数据
            if (annotationPresent2) {
                ProcessFields field = f.getAnnotation(ProcessFields.class);
                // 获取注解值
                ProcessFieldEnum fileType = field.type();
                //属性的值
                Object fieldValue = getFileValue(f, request);
                switch (fileType) {
                    case id:
                        if (ValidateUtil.isNotNull(fieldValue)&&ValidateUtil.isNotEmpty(fieldValue.toString())) {
                            fileAction = "update";
                            id = fieldValue.toString();
                        } else {
                            id = GenerateUUID.getUUID();
                            setFileValue(f, request, id);
                        }
                        break;
                    case state:
                        stateField = f.getName();
                        state = ValidateUtil.isNull(fieldValue) ? ApprovalStatusEnum.Not_Started.getIndex() : (int) fieldValue;
                        break;
                    case stateTime:
                        stateTimeField = f.getName();
                        break;
                    case createUid:
                        createUidField = f.getName();
                        createUid = ValidateUtil.isNull(fieldValue) ? null : fieldValue.toString();
                        break;
                    case createUname:
                        createUnameField = f.getName();
                        createUname = ValidateUtil.isNull(fieldValue) ? null : fieldValue.toString();
                        break;
                    case createTime:
                        createTimeField = f.getName();
                        createTime = ValidateUtil.isNull(fieldValue) ? null : (Date) fieldValue;
                        break;
                }
            }
            //endregion
        }
        //endregion
        //region 表单创建更新,附件状态更新
        BeanUtils.copyProperties(request, entity);
        List<Field> entityFields = ClazzUtil.getAllFields(entity.getClass());
        if (fileAction.equals("create")) {
            //赋默认值
            createTime = new Date();
            setEntityFileValue(entityFields, createTimeField, entity, new Date());
            setEntityFileValue(entityFields, stateField, entity, ValidateUtil.isNotNull(state) ? state : ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(sortFileFields(relationIds), id);
        } else {
            T old = baseMapper.selectById(id);
            if (old == null) throw new JPMException(ResMsg.infoNotExist);
            //不允许修改的字段 流程状态由流程业务修改 表单不操作流程状态
            createTime = (Date) getEntityFileValue(entityFields, old, createTimeField);
            state = (Integer) getEntityFileValue(entityFields, old, stateField);
            createUid = (String) getEntityFileValue(entityFields, old, createUidField);
            createUname = (String) getEntityFileValue(entityFields, old, createUnameField);
            setEntityFileValue(entityFields, createTimeField, entity, createTime);
            setEntityFileValue(entityFields, createUidField, entity, createUid);
            setEntityFileValue(entityFields, createUnameField, entity, createUname);
            setEntityFileValue(entityFields, stateField, entity, state);
            setEntityFileValue(entityFields, stateTimeField, entity, getEntityFileValue(entityFields, old, stateTimeField));
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(sortFileFields(relationIds), sortFileFields(fileIds), id);
        }
        //endregion
        //流程创建更新
        createOrUpdateFile(request, fileAction, id, state, createUid, createUname, createTime);
        return id;
    }

    /**
     * 流程文档创建更新
     */
    protected void createOrUpdateFile(R request, String action, String id, Integer state,
                                      String createUid, String createUname, Date createTime) {
        sysFileService.saveFile(id, request.gettId(), state + "", createUid, createUname, createTime,
                request.getpId(), request.getFileTitle(), request.getFileCode(), action, request.getFileRelationId()
                , request.getSecId(), request.gettCode(), request.getConId());
    }

    /**
     * 附件字段排序，将relationId和filedIds对应上
     *
     * @param ids
     * @return
     */
    private List<String> sortFileFields(Map<Integer, String> ids) {
        List<Integer> finalKeys = new ArrayList<>();
        ids.forEach((key, value) -> {
            finalKeys.add(key);
        });
        List<Integer> keys = finalKeys.stream().sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
        List<String> data = new ArrayList<>();
        for (Integer key : keys) {
            data.add(ids.get(key));
        }
        return data;
    }

    private Object getEntityFileValue(List<Field> entityFields, Object entity, String fieldName) {
        if (ValidateUtil.isEmpty(fieldName)) return null;
        for (Field f : entityFields) {
            if (f.getName().equals(fieldName))
                return getFileValue(f, entity);
        }
        return null;
    }

    private void setEntityFileValue(List<Field> entityFields, String fieldName, Object entity, Object value) {
        if (ValidateUtil.isEmpty(fieldName)) return;
        for (Field f : entityFields) {
            if (f.getName().equals(fieldName))
                setFileValue(f, entity, value);
        }
    }

    private Object getFileValue(Field f, Object entity) {
        //属性的值
        Object fieldValue = null;
        try {
            //设置可访问
            f.setAccessible(true);
            fieldValue = f.get(entity);
        } catch (IllegalAccessException e) {
            throw new JPMException("流程表单保存，字段值获取失败！");
        }
        return fieldValue;
    }

    private void setFileValue(Field f, Object entity, Object value) {
        try {
            //设置可访问
            f.setAccessible(true);
            f.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new JPMException("流程表单保存，字段赋值失败");
        }
    }
}
