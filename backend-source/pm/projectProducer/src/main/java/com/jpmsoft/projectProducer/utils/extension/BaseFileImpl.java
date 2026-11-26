package com.jpmsoft.projectProducer.utils.extension;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.ClazzUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * 流程表单创建修改
 *
 * @param <M>
 * @param <T> 表单实体  BaseEntity子类
 * @param <D> 表单创建修改入参模型 BaseFileDto子类
 */
public abstract class BaseFileImpl<M extends BaseMapper<T>, T extends BaseEntity, D extends BaseFileDto> extends ServiceImpl<M, T> {

    @Resource
    private ISysFileService sysFileService;

    /**
     * 插入表单数据创建流程File
     *
     * @param entity
     * @param request
     * @return
     */
    protected boolean saveFileEntity(T entity, D request) {
        boolean bol = this.save(entity);
        createOrUpdateFile(bol, request, "create", entity);
        return bol;
    }

    /**
     * 更新表单数据更新流程File
     *
     * @param entity
     * @param request
     * @return
     */
    protected boolean updateFileEntity(T entity, D request) {
        boolean bol = this.updateById(entity);
        createOrUpdateFile(bol, request, "update", entity);
        return bol;
    }

    protected T createOrUpdateEntity(T entity, D request) {
        return createOrUpdateEntity(entity, request, true);
    }

    protected T createOrUpdateEntityNotState(T entity, D request) {
        return createOrUpdateEntityNotState(entity, request, true);
    }

    /**
     * 通用流程表单创建修改
     *
     * @param entity
     * @param request
     * @return
     */
    protected T createOrUpdateEntity(T entity, D request, boolean checkState) {
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setCreateTime(new Date());
            int state = ValidateUtil.isNotNull(request.getState()) ? request.getState() : ApprovalStatusEnum.Not_Started.getIndex();
            entity.setState(state);
            request.setState(state);
            saveFileEntity(entity, request);
        } else {
            T old = baseMapper.selectById(request.getId());
            if (ValidateUtil.isNull(old))
                throw new JPMException(ResMsg.infoNotExist);
            if (checkState && JudgeUtil.nonModifyState(old.getState()))
                throw new JPMException(ResMsg.stateNotModify);
            entity.setCreateTime(old.getCreateTime());
            entity.setCreateUid(old.getCreateUid());
            entity.setCreateUname(old.getCreateUname());
            entity.setState(old.getState());
            entity.setStateTime(old.getStateTime());
            updateFileEntity(entity, request);
        }
        return entity;
    }

    protected T createOrUpdateEntityNotState(T entity, D request, boolean checkState) {
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setCreateTime(new Date());
            int state = ValidateUtil.isNotNull(request.getState()) ? request.getState() : ApprovalStatusEnum.Not_Started.getIndex();
            entity.setState(state);
            request.setState(state);
            saveFileEntity(entity, request);
        } else {
            T old = baseMapper.selectById(request.getId());
            if (ValidateUtil.isNull(old))
                throw new JPMException(ResMsg.infoNotExist);
           // if (checkState && JudgeUtil.nonModifyState(old.getState()))
              //  throw new JPMException(ResMsg.stateNotModify);
            entity.setCreateTime(old.getCreateTime());
            entity.setCreateUid(old.getCreateUid());
            entity.setCreateUname(old.getCreateUname());
            entity.setState(old.getState());
            entity.setStateTime(old.getStateTime());
            updateFileEntity(entity, request);
        }
        return entity;
    }

    /**
     * 创建修改Sys_File
     *
     * @param bol
     * @param request
     * @param action
     * @param entity
     */
    protected void createOrUpdateFile(boolean bol, D request, String action, T entity) {
        if (bol == false) throw new JPMException(ResMsg.formCUError);
        sysFileService.saveFile((String) getFieldValue(entity, "id"), request.getTId(), request.getState() + ""
                , request.getCreateUid(), request.getCreateUname(), (Date) getFieldValue(entity, "createTime"),
                request.getPId(), request.getFileTitle(), request.getFileCode(), action, request.getFileRelationId()
                , request.getSecId(), request.getTCode(), request.getConId());
    }

    /**
     * 反射获取对象值
     *
     * @param entity
     * @param field
     * @return
     */
    protected Object getFieldValue(T entity, String field) {
        try {
            List<Field> fieldList = ClazzUtil.getAllFields(entity.getClass());
            Field f = ClazzUtil.getField(fieldList, field);
            f.setAccessible(true);
            return f.get(entity);
        } catch (Exception ex) {
            throw new JPMException(String.format(ResMsg.notTheReq, field));
        }
    }

}
