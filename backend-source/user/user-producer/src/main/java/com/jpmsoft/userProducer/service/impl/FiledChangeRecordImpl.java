package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.FiledChangeRecordMapper;
import com.jpmsoft.userProducer.pojo.dto.FiledChangeRecordDto;
import com.jpmsoft.userProducer.pojo.dto.SendFiledChangeDto;
import com.jpmsoft.userProducer.pojo.entity.FiledChangeRecord;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.FiledChangeRecordVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FiledChangeRecordImpl extends ServiceImpl<FiledChangeRecordMapper, FiledChangeRecord> implements IFiledChangeRecordService {

    @Resource
    private ISysUserService ISysUserService;
    @Resource
    private ISysTableColumnService sysTableColumnService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;

    @Override
    public BaseResponse saveFiledChangeRecord(FiledChangeRecord data) {
        data.setFcrId(GenerateUUID.getUUID());
        baseMapper.insert(data);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse saveFiledChangeRecordBath(List<FiledChangeRecord> data) {
        for (FiledChangeRecord datum : data) {
            datum.setFcrId(GenerateUUID.getUUID());
        }
        saveBatch(data);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse insertFiledChangeRecord(SendFiledChangeDto model) {
        if (ValidateUtil.isAnObjEmpty(model.getNews(), model.getOlds(), model.getUserId(), model.getTableName()))
            return BaseResponse.fail(BaseMsg.notReqParam);
        List<SysTableColumn> columns = sysTableColumnService.getColumnListByTableCode(model.getTableName());
        if (columns == null)
            return BaseResponse.fail(BaseMsg.notTableConfig);
        List<SysTableColumn> pkCloumns = columns.stream().filter(n -> !ValidateUtil.isEmpty(n.getColumnPk()) && n.getColumnPk().equals("1")).collect(Collectors.toList());
        if (pkCloumns == null)
            return BaseResponse.fail(BaseMsg.notPrimaryConfig);
        String pk = pkCloumns.stream().findFirst().get().getColumnCode().replaceAll("_", "").toUpperCase(Locale.ENGLISH);
        SysUser user = ISysUserService.getById(model.getUserId());
        if (user == null)
            return BaseResponse.fail(BaseMsg.notUserInfo);
        List<FiledChangeRecord> entitys = new ArrayList<>();
        fileChangeRecordHandle(entitys, model, pk, user, columns);
        if (entitys.size() > 0)
            saveBatch(entitys);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse insertFiledChangeRecordBath(List<SendFiledChangeDto> models) {
        SendFiledChangeDto req = models.get(0);
        if (ValidateUtil.isAnObjEmpty(req.getNews(), req.getOlds(), req.getUserId(), req.getTableName()))
            return BaseResponse.fail(BaseMsg.notReqParam);
        List<SysTableColumn> columns = sysTableColumnService.getColumnListByTableCode(req.getTableName());
        if (columns == null)
            return BaseResponse.fail(BaseMsg.notTableConfig);
        List<SysTableColumn> pkCloumns = columns.stream().filter(n -> !ValidateUtil.isEmpty(n.getColumnPk()) && n.getColumnPk().equals("1")).collect(Collectors.toList());
        String pk = pkCloumns.stream().findFirst().get().getColumnCode().replaceAll("_", "").toUpperCase(Locale.ENGLISH);
        SysUser user = ISysUserService.getById(req.getUserId());
        if (user == null)
            return BaseResponse.fail(BaseMsg.notUserInfo);
        List<FiledChangeRecord> entitys = new ArrayList<>();
        for (SendFiledChangeDto model : models) {
            fileChangeRecordHandle(entitys, model, pk, user, columns);
        }
        if (entitys.size() > 0)
            saveBatch(entitys);
        return BaseResponse.ok();
    }

    /**
     * 字段变更对比主体逻辑
     *
     * @param entitys
     * @param model
     * @param pk
     * @param user
     * @param columns
     */
    private void fileChangeRecordHandle(List<FiledChangeRecord> entitys, SendFiledChangeDto model, String pk, SysUser user, List<SysTableColumn> columns) {
        Map<String, Object> olds = JacksonUtils.parseObject(model.getOlds(), Map.class);
        Map<String, Object> news = JacksonUtils.parseObject(model.getNews(), Map.class);
        FiledChangeRecord entity = new FiledChangeRecord();
        entity.setFcrUpdateTime(new Date());
        entity.setFcrUpdateUid(model.getUserId());
        entity.setFcrUpdateUname(user.getuName());
        entity.setFcrTable(model.getTableName());
        for (Map.Entry<String, Object> entry : olds.entrySet()) {
            if (entry.getKey().toUpperCase(Locale.ENGLISH).equals(pk)) {
                entity.setFcrKey(entry.getValue().toString());
                break;
            }
        }

        if (ValidateUtil.isEmpty(entity.getFcrKey())) {
            //旧的对象里没有主键值则去新的对象中查找
            for (Map.Entry<String, Object> entry : news.entrySet()) {
                if (entry.getKey().toUpperCase(Locale.ENGLISH).equals(pk)) {
                    entity.setFcrKey(entry.getValue().toString());
                    break;
                }
            }
        }

        FiledChangeRecord pojo;
        for (Map.Entry<String, Object> entry : olds.entrySet()) {
            List<SysTableColumn> specifyColumn = columns.stream().filter(n -> n.getColumnCode().replaceAll("_", "").toUpperCase(Locale.ENGLISH).equals(entry.getKey().toUpperCase(Locale.ENGLISH))).collect(Collectors.toList());
            if (specifyColumn != null && specifyColumn.size() > 0) {
                SysTableColumn column = specifyColumn.stream().findFirst().get();
                Object oldValue = entry.getValue();
                Object newValue = news.get(entry.getKey());
                if (oldValue == null && newValue == null)
                    continue;
                if (!ValidateUtil.isEmpty(column.getColumnType()) && column.getColumnType().equals("TIMESTAMP")) {
                    //时间类型转时间戳
                    if (ValidateUtil.isNotNull(oldValue)) {
                        Date oldTime = DateUtil.String2Date(oldValue.toString(), "yyyy-MM-dd'T'HH:mm:ss");
                        oldValue = oldTime.getTime();
                    }
                    if (ValidateUtil.isNotNull(newValue)) {
                        Date newTime = DateUtil.String2Date(newValue.toString(), "yyyy-MM-dd'T'HH:mm:ss");
                        newValue = newTime.getTime();
                    }
                }
                Boolean add = false;
                if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
                    add = true;
                else if (!oldValue.toString().equals(newValue.toString())) {
                    if (!ValidateUtil.isEmpty(column.getColumnType()) && column.getColumnType().equals("DECIMAL")) {
                        BigDecimal a = new BigDecimal(oldValue.toString());
                        BigDecimal b = new BigDecimal(newValue.toString());
                        if (a.compareTo(b) != 0)
                            add = true;
                    } else if (!ValidateUtil.isEmpty(column.getColumnType()) && column.getColumnType().equals("FLOAT")) {
                        Float a = Float.parseFloat(oldValue.toString());
                        Float b = Float.parseFloat(newValue.toString());
                        if (a.compareTo(b) != 0)
                            add = true;
                    } else
                        add = true;
                }
                if (add) {
                    pojo = new FiledChangeRecord();
                    BeanUtils.copyProperties(entity, pojo);
                    if (!ValidateUtil.isEmpty(column.getColumnType()) && column.getColumnType().equals("DECIMAL")) {
                        //防止被转换为科学计数
                        if (ValidateUtil.isNotNull(oldValue))
                            pojo.setFcrOld(new BigDecimal(String.valueOf(oldValue)).toPlainString());
                        if (ValidateUtil.isNotNull(newValue))
                            pojo.setFcrNew(new BigDecimal(String.valueOf(newValue)).toPlainString());
                    } else {
                        pojo.setFcrOld(oldValue == null ? null : oldValue.toString());
                        pojo.setFcrNew(newValue == null ? null : newValue.toString());
                    }
                    pojo.setFcrFiled(column.getColumnCode());
                    pojo.setFcrType(column.getColumnType());
                    pojo.setFcrId(GenerateUUID.getUUID());
                    InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
                    pojo.setSign(apiUtilsService.sign(ied, ied.getFieldChangeSign(), pojo));
                    entitys.add(pojo);
                }
//                }
            }
        }
    }


    @Override
    public BaseResponse getFiledChangeRecordList(FiledChangeRecordDto request) {
        QueryWrapper<FiledChangeRecord> queryWrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getActId()))
            queryWrapper.eq("act_Id", request.getActId());
        if (!ValidateUtil.isEmpty(request.getActName()))
            queryWrapper.eq("act_Name", request.getActName());
        if (!ValidateUtil.isEmpty(request.getFcrType()))
            queryWrapper.eq("Fcr_Type", request.getFcrType());
        if (!ValidateUtil.isEmpty(request.getFcrUpdateUid()))
            queryWrapper.eq("Fcr_Update_Uid", request.getFcrUpdateUid());
        if (!ValidateUtil.isEmpty(request.getFcrUpdateUname()))
            queryWrapper.like("Fcr_Update_Uname", request.getFcrUpdateUname());
        if (!ValidateUtil.isEmpty(request.getFcrTable()))
            queryWrapper.like("Fcr_Table", request.getFcrTable());
        if (!ValidateUtil.isEmpty(request.getFcrKey()))
            queryWrapper.like("Fcr_Key", request.getFcrKey());
        if (!ValidateUtil.isEmpty(request.getFcrFiled()))
            queryWrapper.like("Fcr_Filed", request.getFcrFiled());
        if (!ValidateUtil.isEmpty(request.getFcrOld()))
            queryWrapper.like("Fcr_Old", request.getFcrOld());
        if (!ValidateUtil.isEmpty(request.getFcrNew()))
            queryWrapper.like("Fcr_New", request.getFcrNew());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) {
                queryWrapper.orderByAsc(request.getSortFiled());
            } else {
                queryWrapper.orderByDesc(request.getSortFiled());
            }
        } else {
            queryWrapper.orderByDesc("Fcr_Update_Time");
        }
        IPage<FiledChangeRecord> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        List<FiledChangeRecord> pojos = iPage.getRecords();
        List<FiledChangeRecordVo> recordVos = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(pojos)) {
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            pojos.forEach(pojo -> {
                apiUtilsService.signVerify(ied, ied.getFieldChangeListVerify(), pojo, pojo.getSign());
                FiledChangeRecordVo vo = new FiledChangeRecordVo();
                BeanUtils.copyProperties(pojo, vo);
                recordVos.add(vo);
            });
        }
        Long longTotal = iPage.getTotal();
        return BaseResponse.ok(recordVos, longTotal.intValue());
    }

    @Override
    public BaseResponse getFiledLastChangeByKey(String fcrKey) {
        List<FiledChangeRecord> datas = baseMapper.getFiledLastChangeByKey(fcrKey);
        Map<String, String> map = new HashMap<>();
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        for (FiledChangeRecord data : datas) {
            apiUtilsService.signVerify(ied, ied.getFieldChangeListVerify(), data, data.getSign());
            if (data.getFcrType().toUpperCase(Locale.ENGLISH).equals("TIMESTAMP"))
                map.put(databaseTurnHump(data.getFcrFiled()), DateUtil.stampToTime(data.getFcrOld()));
            else
                map.put(databaseTurnHump(data.getFcrFiled()), data.getFcrOld());
        }
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse getAllFiledLastChangeByKey(String fcrKey) {
        LambdaQueryWrapper<FiledChangeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FiledChangeRecord::getFcrKey, fcrKey);
        List<FiledChangeRecord> datas = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(datas)) {
            signVerify(datas);
            return BaseResponse.ok(datas.stream().sorted(Comparator.comparing(FiledChangeRecord::getFcrUpdateTime).reversed()).collect(Collectors.toList()));
        }
        return BaseResponse.ok();
    }


    private void signVerify(List<FiledChangeRecord> data) {
        if (!CollectionUtils.isEmpty(data)) {
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            if (ied.getFieldChangeListVerify()) {
                for (FiledChangeRecord d : data) {
                    apiUtilsService.signVerify(ied, ied.getFieldChangeListVerify(), d, d.getSign());
                }
            }
        }
    }

    /**
     * 数据库字段转驼峰
     *
     * @param str
     * @return
     */
    private String databaseTurnHump(String str) {
        if (str == null || str.equals(""))
            return null;
        //先全转小写
        str = str.toLowerCase(Locale.ENGLISH);
        char[] letters = str.toCharArray();
        String filed = "";
        //下一位是否转大写
        Boolean flag = false;
        for (int i = 0; i < letters.length; i++) {
            String s = letters[i] + "";
            if (s.equals("_")) {
                flag = true;
            } else {
                if (flag) {
                    filed += s.toUpperCase(Locale.ENGLISH);
                    flag = false;
                } else
                    filed += s;
            }
            s = null;
        }
        letters = null;
        flag = null;
        str = null;
        return filed;
    }
}
