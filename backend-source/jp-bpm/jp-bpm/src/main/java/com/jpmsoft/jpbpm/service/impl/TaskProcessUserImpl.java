package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.config.RequestDataHelper;
import com.jpmsoft.jpbpm.mapper.TaskProcessUserMapper;
import com.jpmsoft.jpbpm.pojo.entity.File;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessUser;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessin;
import com.jpmsoft.jpbpm.pojo.vo.SysTaskProcessUserVo;
import com.jpmsoft.jpbpm.service.SysSplitTableDtlService;
import com.jpmsoft.jpbpm.service.TaskProcessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskProcessUserImpl extends ServiceImpl<TaskProcessUserMapper, TaskProcessUser> implements TaskProcessUserService {
    @Autowired
    private SysSplitTableDtlService sysSplitTableDtlService;
    String tableName = "Sys_Task_Process_User";

    public String getTableName(String cTode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(cTode, year, tableName);
        return tableCode;
    }

    @Override
    @Transactional
    public TaskProcessUser getTaskProcessUserById(String id, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            TaskProcessUser obj = baseMapper.selectById(id);
            if (obj != null) {
                return obj;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }

        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);
        TaskProcessUser obj = baseMapper.selectById(id);
        if (ValidateUtil.isEmpty(obj)) {
            return baseMapper.selectById(id);
        }
        return obj;

    }

    @Override
    @Transactional
    public List<TaskProcessUser> getListByIds(List<String> ids, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            List<TaskProcessUser> objs = baseMapper.selectBatchIds(ids);
            if (!ValidateUtil.isEmpty(objs)) {
                return objs;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);
        List<TaskProcessUser> obj = baseMapper.selectBatchIds(ids);
        
        if (ValidateUtil.isEmpty(obj)) {
            return baseMapper.selectBatchIds(ids);
        }
        return obj;
    }

    @Override
    @Transactional
    public List<TaskProcessUser> getList(QueryWrapper<TaskProcessUser> queryWrapper, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            List<TaskProcessUser> objs = baseMapper.selectList(queryWrapper);
            if (!ValidateUtil.isEmpty(objs)) {
                return objs;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);
        List<TaskProcessUser> obj = baseMapper.selectList(queryWrapper);
        
        if (ValidateUtil.isEmpty(obj)) {
            return baseMapper.selectList(queryWrapper);
        }
        return obj;
    }

    @Override
    @Transactional
    public List<TaskProcessUser> getList(LambdaQueryWrapper<TaskProcessUser> queryWrapper, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            List<TaskProcessUser> objs = baseMapper.selectList(queryWrapper);
            if (!ValidateUtil.isEmpty(objs)) {
                return objs;
            }
            tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
        }
        //存在分表信息
        RequestDataHelper.setRequestData(tableCode);
        List<TaskProcessUser> obj = baseMapper.selectList(queryWrapper);
        
        if (ValidateUtil.isEmpty(obj)) {
            return baseMapper.selectList(queryWrapper);
        }
        return obj;
    }

    @Override
    @Transactional
    public List<TaskProcessUser> getList(QueryWrapper<TaskProcessUser> queryWrapper, List<String> tCode, String year) {
        List<TaskProcessUser> dataList = new ArrayList<>();
        for (String t : tCode) {
            String tableCode = sysSplitTableDtlService.getTableCodeByTcode(t, year, tableName);
            if (tableCode.equals(tableName)) {
                List<TaskProcessUser> obj = baseMapper.selectList(queryWrapper);
                if (!CollectionUtils.isEmpty(obj)) {
                    dataList.addAll(obj);
                } else {
                    String tableCode2 = sysSplitTableDtlService.getTableCodeByTcode(t, "", tableName);
                    RequestDataHelper.setRequestData(tableCode2);
                    List<TaskProcessUser> obj2 = baseMapper.selectList(queryWrapper);
                    
                    if (!CollectionUtils.isEmpty(obj2)) {
                        dataList.addAll(obj2);
                    }
                }
            } else {
                RequestDataHelper.setRequestData(tableCode);
                List<TaskProcessUser> obj = baseMapper.selectList(queryWrapper);
                
                if (ValidateUtil.isEmpty(obj)) {
                    obj = baseMapper.selectList(queryWrapper);
                }
                dataList.addAll(obj);
            }
        }
        return duplicateRemoval(dataList);
    }

    @Override
    @Transactional
    public List<TaskProcessUser> getList(LambdaQueryWrapper<TaskProcessUser> queryWrapper, List<String> tCode, String year) {
        List<TaskProcessUser> dataList = new ArrayList<>();
        dataList.addAll(baseMapper.selectList(queryWrapper));
        for (String t : tCode) {
            String tableCode = sysSplitTableDtlService.getTableCodeByTcode(t, year, tableName);
            if (tableCode.equals(tableName)) {
                List<TaskProcessUser> obj = baseMapper.selectList(queryWrapper);
                if (!CollectionUtils.isEmpty(obj)) {
                    dataList.addAll(obj);
                } else {
                    String tableCode2 = sysSplitTableDtlService.getTableCodeByTcode(t, "", tableName);
                    RequestDataHelper.setRequestData(tableCode2);
                    List<TaskProcessUser> obj2 = baseMapper.selectList(queryWrapper);
                    
                    if (!CollectionUtils.isEmpty(obj2)) {
                        dataList.addAll(obj2);
                    }
                }
            } else {
                RequestDataHelper.setRequestData(tableCode);
                List<TaskProcessUser> obj = baseMapper.selectList(queryWrapper);
                
                if (ValidateUtil.isEmpty(obj)) {
                    obj = baseMapper.selectList(queryWrapper);
                }
                dataList.addAll(obj);
            }
        }
        return duplicateRemoval(dataList);
    }

    public List<TaskProcessUser> duplicateRemoval(List<TaskProcessUser> fileList) {
        List<TaskProcessUser> duplicateList = new ArrayList<>();
        //去重
        fileList.forEach(n -> {
            List<TaskProcessUser> lis = duplicateList.stream().filter(k -> k.getTpuId().equals(n.getTpuId())).collect(Collectors.toList());
            if (org.apache.commons.collections.CollectionUtils.isEmpty(lis)) {
                duplicateList.add(n);
            }
        });
        return duplicateList;
    }
    @Override
    @Transactional
    public boolean updateTaskProcessUserById(TaskProcessUser obj, String tCode, String year) {
        Boolean type = false;
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            type = baseMapper.updateById(obj) == 1 ? true : false;
            if (type == false) {
                String tableCode1 = sysSplitTableDtlService.getTableCodeByTcode(tCode, "", tableName);
                RequestDataHelper.setRequestData(tableCode1);
                type = baseMapper.updateById(obj) == 1 ? true : false;
            }
        } else {
            RequestDataHelper.setRequestData(tableCode);
            type = baseMapper.updateById(obj) == 1 ? true : false;
            
            if (type == false) {
                type = baseMapper.updateById(obj) == 1 ? true : false;
            }
        }

        return type;
    }


    @Override
    @Transactional
    public boolean saveTaskProcessUserBatch(List<TaskProcessUser> list, String tCode, String year) {
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            list.forEach(tp -> {
                baseMapper.insert(tp);
            });
        } else {
            //存在分表信息
            RequestDataHelper.setRequestData(tableCode);
            list.forEach(tp -> {
                baseMapper.insert(tp);
            });
            
        }
        return true;
    }

    @Override
    @Transactional
    public int insertTaskProcessUser(TaskProcessUser taskProcessUser, String tCode, String year) {
        int type = 0;
        String tableCode = sysSplitTableDtlService.getTableCodeByTcode(tCode, year, tableName);
        if (tableCode.equals(tableName)) {
            type = baseMapper.insert(taskProcessUser);
        } else {
            //存在分表信息
            RequestDataHelper.setRequestData(tableCode);
            type = baseMapper.insert(taskProcessUser);
            
        }
        return type;
    }

    @Override
    @Transactional
    public void GetNewTaskProcessUse(String processid, String taskuser, String tpusendactivityid, String customerId, String tCode, String year) {
        if (taskuser.contains(",")) {
            String[] userList = taskuser.split(",");
            for (int i = 0; i < userList.length; i++) {
                TaskProcessUser pojo = new TaskProcessUser();
                pojo.setTpuId(GenerateUUID.getUUID());
                pojo.setTpuTpId(processid);
                pojo.setTpuUser(userList[i]);
                pojo.setTpuSendActivityid(tpusendactivityid);
                if (!"All".equals(customerId)) {
                    pojo.setTeId(customerId);
                }
                insertTaskProcessUser(pojo, tCode, year);
            }
        } else {
            TaskProcessUser pojo = new TaskProcessUser();
            pojo.setTpuId(GenerateUUID.getUUID());
            pojo.setTpuTpId(processid);
            pojo.setTpuUser(taskuser);
            pojo.setTpuSendActivityid(tpusendactivityid);
            if (!"All".equals(customerId)) {
                pojo.setTeId(customerId);
            }
            insertTaskProcessUser(pojo, tCode, year);
        }

    }


    @Override
    public List<TaskProcessUser> GetTaskProcessUser(List<String> tpidlist, String customerId, String tCode, String year) {
        QueryWrapper<TaskProcessUser> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isEmpty(tpidlist)) {
            return new ArrayList<>();
        }
        wrapper.in("Tpu_Tp_Id", tpidlist);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<TaskProcessUser> list = getList(wrapper, tCode, year);
        return list;
    }


    @Override
    public List<SysTaskProcessUserVo> GetTaskProcessUserInfo(List<String> tpidlist, String customerId, String tCode, String year) {
        List<SysTaskProcessUserVo> list = baseMapper.selectTpuUserInfo(tpidlist, customerId, getTableName(tCode, year));
        return list;
    }
}
