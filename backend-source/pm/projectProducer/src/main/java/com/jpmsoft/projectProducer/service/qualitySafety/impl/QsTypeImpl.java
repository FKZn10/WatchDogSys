package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsTypeInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsTypeModel;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsTypeMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsType;
import com.jpmsoft.projectProducer.pojo.request.qs.QsTypeRecordRequest;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsTypeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class QsTypeImpl extends ServiceImpl<QsTypeMapper, QsType> implements IQsTypeService {

    @Resource
    private IQsRecordService qsRecordService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "qstId", codeParam = "qstCode", method = "setQstCode", codeRule = "QsType", beanClass = QsTypeModel.class)
    public BaseResponse createOrUpdateQsType(QsTypeModel request) {
        String qstParentId = StrUtil.isBlank(request.getQstParentId())?"0":request.getQstParentId();
        QsType parentQsType = baseMapper.getQsTypeById(qstParentId);
        QsType entity = new QsType();
        if (ValidateUtil.isEmpty(request.getQstCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        if(request.getSeverityLevel()!=null){
            if(request.getSeverityLevel()==2){//重大问题
                entity.setQstIsImportant(1);
            }else{
                entity.setQstIsImportant(0);
            }
        }
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        wrapper.eq("Qst_Code", request.getQstCode());
        if (!ValidateUtil.isEmpty(entity.getQstId()))
            wrapper.ne("Qst_Id", request.getQstId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (ValidateUtil.isEmpty(entity.getQstId())) {
            entity.setQstId(GenerateUUID.getUUID());
            entity.setQstCreateTime(new Date());
            if (ValidateUtil.isEmpty(entity.getQstIsImportant())){
                 entity.setQstIsImportant(0);
            }
            if (ValidateUtil.isEmpty(entity.getQstParentId()))
                entity.setQstParentId("0");
            QueryWrapper<QsType> listWrapper = new QueryWrapper<>();
            listWrapper.eq("Qst_Parent_Id", request.getQstParentId());
            Integer count = baseMapper.selectCount(listWrapper).intValue();
            entity.setQstSort(count + 1);
            if(parentQsType!=null){
                String parentQstPath = parentQsType.getQstPath();
                if(StrUtil.isNotBlank(parentQstPath)){
                    entity.setQstPath(parentQstPath + "/" + entity.getQstId());
                }
            }else {
                entity.setQstPath(entity.getQstId());
            }
            baseMapper.insert(entity);
            redisUtils.delete("QsTypeTreeList");
            return BaseResponse.ok(entity.getQstId());
        } else {
            QsType model = baseMapper.selectById(entity.getQstId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (model.getQstId().equals(request.getQstParentId()))
                return BaseResponse.fail(ResMsg.parentNodeError);
            entity.setQstCreateTime(model.getQstCreateTime());
            entity.setQstCreateUid(model.getQstCreateUid());
            entity.setQstCreateUname(model.getQstCreateUname());
            if (ValidateUtil.isEmpty(entity.getQstIsImportant())){
                 entity.setQstIsImportant(0);
            }
            //修改path
            String oldQstParentId = model.getQstParentId();
            if(!qstParentId.equals(oldQstParentId)){//如果父级发生变化
                if(parentQsType!=null){//存在父级
                    String qstPath = model.getQstPath();
                    if(StrUtil.isNotBlank(qstPath)){
                        String parentQstPath = parentQsType.getQstPath();
                        if(StrUtil.isNotBlank(parentQstPath)){
                            entity.setQstPath(parentQstPath + "/" + entity.getQstId());
                        }
                    }
                }else{//当前节点变成第一级。
                    entity.setQstPath(entity.getQstId());
                }
                //改变所有子级的path
                updateChildQsTypePath(entity);
            }
            //批量修改组内的数据
            List<String> groupQstIdList = request.getGroupQstIdList();
            if(StrUtil.isNotBlank(request.getSelectGroupQstId()) && CollectionUtil.isNotEmpty(groupQstIdList)){
                List<QsType> qsTypeQstIdList = baseMapper.getQsTypeByQstIdList(groupQstIdList);
                for (QsType qsType : qsTypeQstIdList) {
                    qsType.setQstPunish(request.getQstPunish());
                    qsType.setQstScore(request.getQstScore());
                }
                baseMapper.batchUpdateQsType(qsTypeQstIdList);
            }

            baseMapper.updateById(entity);
            redisUtils.delete("QsTypeTreeList");
            return BaseResponse.ok(entity.getQstId());
        }
    }


    @Override
    public BaseResponse getGroupSameType(QsTypeModel request) {
        String qstId = request.getQstId();
        String selectGroupQstId = request.getSelectGroupQstId();
        if(StrUtil.isBlank(qstId) || StrUtil.isBlank(selectGroupQstId)){
            throw new JPMException("修改的类别id或选定组类别id不能为空");
        }
        String qstName = request.getQstName();
        if(StrUtil.isBlank(qstName)){
            throw new JPMException("类别名称不能为空");
        }
        List<QsType> allChildList = baseMapper.getAllChildByQstId(selectGroupQstId);
        String name = getChineseStr(qstName);
        List<QsType> sameList = new ArrayList<>();
        Map<String,String> qstNameMap=new HashMap<>(16);//提前收集，避免一些不必要的查询。
        if(CollectionUtil.isNotEmpty(allChildList)){
            for (QsType qsType : allChildList) {
                if(!qstId.equals(qsType.getQstId()) && qsType.getQstName().contains(name)){
                    sameList.add(qsType);
                }
                qstNameMap.put(qsType.getQstId(),qsType.getQstName());
            }
        }

        List<String> qstIdList = new ArrayList<>();//当前未知名称的id
        if(CollectionUtil.isNotEmpty(sameList)){
            for (QsType qsType : sameList) {
                String qstPath = qsType.getQstPath();
                if(StrUtil.isNotBlank(qstPath)){
                    String[] split = qstPath.split("/");
                    for (String id : split) {
                        if(!qstNameMap.containsKey(id)){
                            qstIdList.add(id);
                        }
                    }
                }
            }
        }

        if(CollectionUtil.isNotEmpty(qstIdList)){
            List<QsType> qsTypeList = baseMapper.getQsTypeByQstIdList(qstIdList);
            if(CollectionUtil.isNotEmpty(qsTypeList)){
                for (QsType qsType : qsTypeList) {
                    qstNameMap.put(qsType.getQstId(),qsType.getQstName());
                }
            }
        }

        List<QsTypeModel> resultList=new ArrayList<>();

        if(CollectionUtil.isNotEmpty(sameList)){
            resultList = com.jpmsoft.common.utils.BeanUtils.copyList(sameList, QsTypeModel.class);
            List<String> pathNameList=new ArrayList<>();
            for (QsTypeModel qsTypeModel : resultList) {
                List<String> pathList = Arrays.asList(qsTypeModel.getQstPath().split("/"));
                StringBuilder sb = new StringBuilder();
                pathNameList=new ArrayList<>();
                for (int i = 0; i < pathList.size(); i++) {
                    if (qstNameMap.containsKey(pathList.get(i))) {
                        String pathName = qstNameMap.get(pathList.get(i));
                        sb.append(pathName);
                        if(i<pathList.size()-1){
                            sb.append("/");
                        }
                        pathNameList.add(pathName);
                    }
                }
                qsTypeModel.setQstPathNameList(pathNameList);
                qsTypeModel.setQstPathName(sb.toString());
            }
        }


        return BaseResponse.ok(resultList);
    }


    /**
     * 修改子级path
     * @param qsType
     */
    private void updateChildQsTypePath(QsType qsType) {
        String qstPath = qsType.getQstPath();
        String qstId = qsType.getQstId();
        String delimiter=qsType.getQstId();
        List<QsType> allChildList = baseMapper.getAllChildByQstId(qstId);
        if(CollectionUtil.isNotEmpty(allChildList)){
            for (QsType childType : allChildList) {

                String childQstPath = childType.getQstPath();
                if(StrUtil.isNotBlank(childQstPath)){
                    String newQstPath = qstPath + childQstPath.split(delimiter)[1];
                    childType.setQstPath(newQstPath);
                }
            }
            baseMapper.batchUpdateQsType(allChildList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteQsTypeById(String qstId) {
        QsType model = baseMapper.selectById(qstId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        wrapper.eq("Qst_Parent_Id", qstId);
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        if (!qsRecordService.canToDeleteQsType(qstId))
            return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(qstId);
        List<QsType> sameLevelData = baseMapper.selectSameLevelBySort(model.getQstParentId(), model.getQstSort());
        if (!CollectionUtils.isEmpty(sameLevelData)) {
            List<QsType> updateSorts = new ArrayList<>();
            sameLevelData.forEach(entity -> {
                entity.setQstSort(entity.getQstSort() - 1);
                updateSorts.add(entity);
            });
            if (!CollectionUtils.isEmpty(updateSorts))
                updateBatchById(updateSorts);
        }
        redisUtils.delete("QsTypeTreeList");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getQsTypeTreeList(QsTypeModel request, String uId) {
        String treeKey = "QsTypeTreeList";
        if (ValidateUtil.isEmpty(request.getQstCode())){
            Object dictionaryTreeList = redisUtils.get(treeKey);
            if (dictionaryTreeList != null) {
                List<QsTypeModel> returnData = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<QsTypeModel>>() {
                });
                return BaseResponse.ok(returnData);
            }
        }
//        if (sysUserService.checkIsOutUser(uId))
//            return BaseResponse.fail(ResMsg.noPermission);
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        wrapper.eq("Qst_State", 1);
        wrapper.orderByAsc("Qst_Sort");
        List<QsType> list = baseMapper.selectList(wrapper);
        List<QsType> rootNodes;
        if (ValidateUtil.isEmpty(request.getQstCode()))
            rootNodes = list.stream().filter(n -> n.getQstParentId().equals("0")).collect(Collectors.toList());
        else
            rootNodes = list.stream().filter(n -> n.getQstCode().equals(request.getQstCode())).collect(Collectors.toList());

        List<QsTypeModel> trees = new ArrayList<>();
        rootNodes.forEach(node -> {
            QsTypeModel tree = new QsTypeModel();
            BeanUtils.copyProperties(node, tree);
            List<QsTypeModel> childNodes = new ArrayList<>();
            List<QsType> childs = list.stream().filter(n -> n.getQstParentId().equals(node.getQstId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getQstTypeChildNodes(childNodes, childs, list);
            tree.setChildNodes(childNodes);
            trees.add(tree);
        });
        if (ValidateUtil.isEmpty(request.getQstCode())) {
            redisUtils.set(treeKey, JacksonUtils.toJSONString(trees));
        }
        return BaseResponse.ok(trees);
    }

    @Override
    public BaseResponse getQsTypeList(QsTypeModel request, String uid) {
        QueryMap<QsTypeModel> params = new QueryMap<>(request);
        params.put("qstName").put("qstCode").put("qstParentId").put("qstCategory").put("qstIsImportant")
              .put("qstState").put("severityLevel");
        IPage<QsTypeModel> page = new Page<>(request.getPage(), request.getSize());
        List<QsTypeModel> datas = baseMapper.getQsTypeList(params.getQuery(),page);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse upOrDownQsType(String qstId, String action, String teId) {
        QsType entity = baseMapper.selectById(qstId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("Qst_Parent_Id", entity.getQstParentId());
        wrapper.orderByAsc("Qst_Sort");
        List<QsType> list = baseMapper.selectList(wrapper);
        if (list.size() > 1) {
            QsType entity1;
            int index = list.indexOf(entity);
            if (action.equalsIgnoreCase("UP")) {
                if (index == 0)
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = list.get(index - 1);
            } else if (action.equalsIgnoreCase("DOWN")) {
                if (index == list.size())
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = list.get(index + 1);
            } else
                return BaseResponse.fail(ResMsg.actError);
            Integer sort = entity.getQstSort();
            entity.setQstSort(entity1.getQstSort());
            entity1.setQstSort(sort);
            updateBatchById(Arrays.asList(entity, entity1));
        } else {
            return BaseResponse.fail(ResMsg.actError);
        }
        redisUtils.delete("QsTypeTreeList");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getQsTypeByParentId(String parentId) {
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        wrapper.eq("Qst_Parent_Id", parentId);
        wrapper.orderByAsc("Qst_Sort");
        List<QsType> list = baseMapper.selectList(wrapper);
        List<QsTypeModel> datas = new ArrayList<>();
        if (list != null && list.size() > 0) {
            list.forEach(data -> {
                QsTypeModel model = new QsTypeModel();
                BeanUtils.copyProperties(data, model);
                datas.add(model);
            });
        }
        return BaseResponse.ok(datas);
    }

    @Override
    public List<QsType> getChildQsType(List<String> qstIdList) {
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        List<QsType> qsTypes = baseMapper.selectList(wrapper);
        List<QsType> resultList = new ArrayList<>();
        List<QsType> parentList = qsTypes.stream().filter(qsType -> qstIdList.contains(qsType.getQstId())).collect(Collectors.toList());
        resultList.addAll(parentList);
        for (QsType qsType : parentList) {
            List<QsType> childList = qsTypes.stream().filter(item -> qsType.getQstId().equals(item.getQstParentId())).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(childList)){
                assembleChild(childList, qsTypes,resultList);
                resultList.addAll(childList);
            }
        }

        return resultList;
    }

    @Override
    public BaseResponse getQsTypeById(String qstId) {
        //等后面维护了path，再优化写法
        QsType qsType = baseMapper.getQsTypeById(qstId);
        String qstParentId = qsType.getQstParentId();
        List<String> qstNameList=new ArrayList<>();
        if(StrUtil.isNotBlank(qstParentId) && !"0".equals(qstParentId)){
            QsType parentType = baseMapper.getQsTypeById(qstParentId);
            getParentType(parentType,qstNameList);
        }
        Collections.reverse(qstNameList);
        qstNameList.add(qsType.getQstName());
        QsTypeModel qsTypeModel=new QsTypeModel();
        BeanUtil.copyProperties(qsType,qsTypeModel);
        qsTypeModel.setQstPathList(qstNameList);
        qsTypeModel.setQstPath(StrUtil.join("/",qstNameList));

        return BaseResponse.ok(qsTypeModel);
    }


    @Override
    public Workbook exportQsType(QsTypeModel request, String uid) {
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();

        QueryMap<QsTypeModel> params = new QueryMap<>(request);
        params.put("qstName").put("qstCode").put("qstParentId").put("qstCategory").put("qstIsImportant").put("qstState");
        List<QsTypeModel> models = baseMapper.getQsTypeList(params.getQuery(),null);
        List<QsTypeModel> resultList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(models)){
            List<QsTypeModel> firstList = models.stream().filter(item -> "0".equals(item.getQstParentId())).collect(Collectors.toList());
            int sort=1;
            for (QsTypeModel qsTypeModel : firstList) {
                qsTypeModel.setLevel(1);
                sort++;
                qsTypeModel.setQstNumber(generateQsTypeNumber("",qsTypeModel,sort));
                resultList.add(qsTypeModel);
                handleChildQsType(qsTypeModel,models,resultList);
            }
        }

        String[] rowTitle = new String[]{"序号","层级","类别名称", "是否重大问题", "严重程度", "金额", "分值", "单位", "排序号", "状态"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("质安问题类别");
        for (int i = 0; i < rowTitle.length; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }

        if (!CollectionUtils.isEmpty(resultList)) {
            for (QsTypeModel model : resultList) {
                row = sheet.createRow(rowNum++);

                cell = row.createCell(0);
                cell.setCellValue(model.getQstNumber());
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue(model.getLevel());
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(model.getQstName());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(model.getQstIsImportant()==null?"否":model.getQstIsImportant()==0?"否":"是");
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(model.getSeverityLevel() == null ? "" : String.valueOf(model.getSeverityLevel()));
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(model.getQstPunish());
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(model.getQstScore());
                cell.setCellStyle(style);

                cell = row.createCell(7);
                cell.setCellValue(model.getQstUnit());
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue(model.getQstSort());
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(model.getQstState()==null?"可用":model.getQstState()==0?"禁用":"可用");
                cell.setCellStyle(style);


            }
        }
        return workbook;
    }

    @Override
    public BaseResponse generateQsTypePath() {
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        List<QsType> qsTypes = baseMapper.selectList(wrapper);
        List<QsType> resultList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(qsTypes)){
            List<QsType> firstList = qsTypes.stream().filter(item -> "0".equals(item.getQstParentId())).collect(Collectors.toList());
            for (QsType qsType : firstList) {
                qsType.setQstPath(qsType.getQstId());
                handleChildPath(qsType,qsTypes,resultList);
                resultList.add(qsType);
            }
        }

        if(CollectionUtil.isNotEmpty(resultList)){
            saveOrUpdateBatch(resultList,resultList.size());
        }

        return BaseResponse.ok();
    }

    @Override
    public Workbook exportQsTypeAll(QsTypeModel request, String uid) {
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();
        QueryMap<QsTypeModel> params = new QueryMap<>(request);
        params.put("qstName").put("qstCode").put("qstParentId").put("qstCategory").put("qstIsImportant").put("qstState");
        List<QsTypeModel> models = baseMapper.getQsTypeList(params.getQuery(),null);
        List<QsTypeModel> resultList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(models)){
            List<QsTypeModel> firstList = models.stream().filter(item -> "0".equals(item.getQstParentId())).collect(Collectors.toList());
            int sort=1;
            for (QsTypeModel qsTypeModel : firstList) {
                qsTypeModel.setLevel(1);
                sort++;
                qsTypeModel.setQstNumber(generateQsTypeNumber("",qsTypeModel,sort));
                resultList.add(qsTypeModel);
                handleChildQsType(qsTypeModel,models,resultList);
            }
        }

        String[] rowTitle = new String[]{"序号","id","parentId","path","层级","类别名称", "是否重大问题", "严重程度", "金额", "分值", "单位", "排序号", "状态"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("质安问题类别");
        for (int i = 0; i < rowTitle.length; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }

        if (!CollectionUtils.isEmpty(resultList)) {
            for (QsTypeModel model : resultList) {
                row = sheet.createRow(rowNum++);

                cell = row.createCell(0);
                cell.setCellValue(model.getQstNumber());
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(model.getQstId());
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue(model.getQstParentId());
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue(model.getQstPath());
                cell.setCellStyle(style);
                cell = row.createCell(4);
                cell.setCellValue(model.getLevel());
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(model.getQstName());
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(model.getQstIsImportant()==null?"否":model.getQstIsImportant()==0?"否":"是");
                cell.setCellStyle(style);

                cell = row.createCell(7);
                cell.setCellValue(model.getSeverityLevel() == null ? "" : String.valueOf(model.getSeverityLevel()));
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue(model.getQstPunish());
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(model.getQstScore());
                cell.setCellStyle(style);

                cell = row.createCell(10);
                cell.setCellValue(model.getQstUnit());
                cell.setCellStyle(style);

                cell = row.createCell(11);
                cell.setCellValue(model.getQstSort());
                cell.setCellStyle(style);

                cell = row.createCell(12);
                cell.setCellValue(model.getQstState()==null?"可用":model.getQstState()==0?"禁用":"可用");
                cell.setCellStyle(style);


            }
        }
        return workbook;
    }

    @Override
    public BaseResponse getQsTypeInfo(QsTypeRecordRequest request) {
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        wrapper.eq("Qst_Parent_Id", request.getQstParentId());
        wrapper.orderByAsc("Qst_Sort");
        List<QsType> list = baseMapper.selectList(wrapper);
        QueryMap<QsTypeRecordRequest> params = new QueryMap<>(request);
        params.put("startTime").put("endTime").put("ptType");
        List<QsRecordResponse> qsRecordResponses = baseMapper.getQsRecordListByParams(params.getQuery());
        List<QsTypeInfoModel> datas = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.forEach(data -> {
                QsTypeInfoModel model = new QsTypeInfoModel();
                BeanUtils.copyProperties(data, model);
                //获取问题数量、整改数量、整改率
                Integer problemCount=0;
                Integer rectifiedProblemCount=0;
                BigDecimal rectifiedProblemRate = BigDecimal.ZERO;
                if(StrUtil.isNotEmpty(data.getQstId())){
                    List<QsRecordResponse> qsRecordResponsesByQstId
                            = qsRecordResponses.stream().filter(item -> data.getQstId().equals(item.getQstId())).collect(Collectors.toList());
                    if(CollectionUtil.isNotEmpty(qsRecordResponsesByQstId)){
                        problemCount=qsRecordResponsesByQstId.size();
                        if(problemCount>0){
                            rectifiedProblemCount= (int) qsRecordResponsesByQstId.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count();
                            rectifiedProblemRate=BigDecimal.valueOf(rectifiedProblemCount)
                                    .divide(BigDecimal.valueOf(problemCount),2, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(100));
                        }
                    }
                }
                model.setProblemCount(problemCount);
                model.setRectifiedProblemCount(rectifiedProblemCount);
                model.setRectifiedProblemRate(rectifiedProblemRate);
                datas.add(model);
            });
        }
        return BaseResponse.ok(datas);
    }

    @Override
    public Workbook exportQsTypeInfo(QsTypeRecordRequest request, String uid) {
        Workbook workbook = new XSSFWorkbook();
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        wrapper.eq("Qst_Parent_Id", "0");
        wrapper.orderByAsc("Qst_Sort");
        List<QsType> list = baseMapper.selectList(wrapper);
        QueryMap<QsTypeRecordRequest> params = new QueryMap<>(request);
        params.put("startTime").put("endTime").put("ptType");
        List<QsRecordResponse> qsRecordResponses = baseMapper.getQsRecordListByParams(params.getQuery());
        List<QsTypeInfoModel> datas = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            final int[] sort = {1};
            list.forEach(data -> {
                QsTypeInfoModel model = new QsTypeInfoModel();
                BeanUtils.copyProperties(data, model);
                model.setLevel(1);
                sort[0]++;
                model.setQstNumber(generateQsTypeNumber("",model, sort[0]));
                //获取问题数量、整改数量、整改率
                Integer problemCount=0;
                Integer rectifiedProblemCount=0;
                BigDecimal rectifiedProblemRate = BigDecimal.ZERO;
                if(StrUtil.isNotEmpty(data.getQstId())){
                    List<QsRecordResponse> qsRecordResponsesByQstId
                            = qsRecordResponses.stream().filter(item -> data.getQstId().equals(item.getQstId())).collect(Collectors.toList());
                    if(CollectionUtil.isNotEmpty(qsRecordResponsesByQstId)){
                        problemCount=qsRecordResponsesByQstId.size();
                        if(problemCount>0){
                            rectifiedProblemCount= (int) qsRecordResponsesByQstId.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count();
                            rectifiedProblemRate=BigDecimal.valueOf(rectifiedProblemCount)
                                    .divide(BigDecimal.valueOf(problemCount),2, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(100));
                        }
                    }
                }
                model.setProblemCount(problemCount);
                model.setRectifiedProblemCount(rectifiedProblemCount);
                model.setRectifiedProblemRate(rectifiedProblemRate);
                datas.add(model);
                QueryWrapper<QsType> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("Qst_Parent_Id", model.getQstId());
                wrapper2.orderByAsc("Qst_Sort");
                List<QsType> childlist = baseMapper.selectList(wrapper2);
                if (CollectionUtil.isNotEmpty(childlist)){
                    getChildList(model, qsRecordResponses, datas);
                }
            });
        }
        String[] rowTitle = new String[]{"序号","层级","类别名称", "是否重大问题", "金额", "分值", "问题数量", "整改数量", "整改率（%）", "单位", "排序号", "状态"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("质安问题类别");
        for (int i = 0; i < rowTitle.length; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }

        if (!CollectionUtils.isEmpty(datas)) {
            for (QsTypeInfoModel model : datas) {
                if (model != null){
                    row = sheet.createRow(rowNum++);
                    cell = row.createCell(0);
                    cell.setCellValue(model.getQstNumber());
                    cell.setCellStyle(style);
                    cell = row.createCell(1);
                    cell.setCellValue(model.getLevel());
                    cell.setCellStyle(style);
                    cell = row.createCell(2);
                    cell.setCellValue(model.getQstName());
                    cell.setCellStyle(style);
                    cell = row.createCell(3);
                    cell.setCellValue(model.getQstIsImportant()==null?"否":model.getQstIsImportant()==0?"否":"是");
                    cell.setCellStyle(style);
                    cell = row.createCell(4);
                    cell.setCellValue(model.getQstPunish());
                    cell.setCellStyle(style);
                    cell = row.createCell(5);
                    cell.setCellValue(model.getQstScore());
                    cell.setCellStyle(style);
                    cell = row.createCell(6);
                    cell.setCellValue(model.getProblemCount());
                    cell.setCellStyle(style);
                    cell = row.createCell(7);
                    cell.setCellValue(model.getRectifiedProblemCount());
                    cell.setCellStyle(style);
                    cell = row.createCell(8);
                    cell.setCellValue(model.getRectifiedProblemRate().doubleValue());
                    cell.setCellStyle(style);
                    cell = row.createCell(9);
                    cell.setCellValue(model.getQstUnit());
                    cell.setCellStyle(style);
                    cell = row.createCell(10);
                    cell.setCellValue(model.getQstSort());
                    cell.setCellStyle(style);
                    cell = row.createCell(11);
                    cell.setCellValue(model.getQstState()==null?"可用":model.getQstState()==0?"禁用":"可用");
                    cell.setCellStyle(style);
                }
            }
        }
        return workbook;
    }

    private void getChildList(QsTypeInfoModel data, List<QsRecordResponse> qsRecordResponses, List<QsTypeInfoModel> datas) {
        QueryWrapper<QsType> wrapper= new QueryWrapper<>();
        wrapper.eq("Qst_Parent_Id", data.getQstId());
        wrapper.orderByAsc("Qst_Sort");
        List<QsType> childlist = baseMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(childlist)){
            final int[] sort = {1};
            childlist.forEach(data2 -> {
                QsTypeInfoModel model = new QsTypeInfoModel();
                model.setLevel(data.getLevel()+1);
                sort[0]++;
                model.setQstNumber(generateQsTypeNumber("",model, sort[0]));
                BeanUtils.copyProperties(data2, model);
                //获取问题数量、整改数量、整改率
                Integer problemCount=0;
                Integer rectifiedProblemCount=0;
                BigDecimal rectifiedProblemRate = BigDecimal.ZERO;
                if(StrUtil.isNotEmpty(data2.getQstId())){
                    List<QsRecordResponse> qsRecordResponsesByQstId
                            = qsRecordResponses.stream().filter(item -> data2.getQstId().equals(item.getQstId())).collect(Collectors.toList());
                    if(CollectionUtil.isNotEmpty(qsRecordResponsesByQstId)){
                        problemCount=qsRecordResponsesByQstId.size();
                        if(problemCount>0){
                            rectifiedProblemCount= (int) qsRecordResponsesByQstId.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count();
                            rectifiedProblemRate=BigDecimal.valueOf(rectifiedProblemCount)
                                    .divide(BigDecimal.valueOf(problemCount),2, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(100));
                        }
                    }
                }
                model.setProblemCount(problemCount);
                model.setRectifiedProblemCount(rectifiedProblemCount);
                model.setRectifiedProblemRate(rectifiedProblemRate);
                datas.add(model);
                QueryWrapper<QsType> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("Qst_Parent_Id", model.getQstId());
                wrapper2.orderByAsc("Qst_Sort");
                List<QsType> childlists = baseMapper.selectList(wrapper2);
                if (CollectionUtil.isNotEmpty(childlists)){
                    getChildList(model, qsRecordResponses, datas);
                }
            });
        }
    }

    @Override
    public Workbook exportQsTypeInfoAll(QsTypeRecordRequest request, String uid) {
        Workbook workbook = new XSSFWorkbook();
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        wrapper.eq("Qst_Parent_Id", request.getQstParentId());
        wrapper.orderByAsc("Qst_Sort");
        List<QsType> list = baseMapper.selectList(wrapper);
        QueryMap<QsTypeRecordRequest> params = new QueryMap<>(request);
        params.put("startTime").put("endTime").put("ptType");
        List<QsRecordResponse> qsRecordResponses = baseMapper.getQsRecordListByParams(params.getQuery());
        List<QsTypeInfoModel> datas = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            final int[] sort = {1};
            list.forEach(data -> {
                QsTypeInfoModel model = new QsTypeInfoModel();
                BeanUtils.copyProperties(data, model);
                model.setLevel(1);
                sort[0]++;
                model.setQstNumber(generateQsTypeNumber("",model, sort[0]));
                //获取问题数量、整改数量、整改率
                Integer problemCount=0;
                Integer rectifiedProblemCount=0;
                BigDecimal rectifiedProblemRate = BigDecimal.ZERO;
                if(StrUtil.isNotEmpty(data.getQstId())){
                    List<QsRecordResponse> qsRecordResponsesByQstId
                            = qsRecordResponses.stream().filter(item -> data.getQstId().equals(item.getQstId())).collect(Collectors.toList());
                    if(CollectionUtil.isNotEmpty(qsRecordResponsesByQstId)){
                        problemCount=qsRecordResponsesByQstId.size();
                        if(problemCount>0){
                            rectifiedProblemCount= (int) qsRecordResponsesByQstId.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count();
                            rectifiedProblemRate=BigDecimal.valueOf(rectifiedProblemCount)
                                    .divide(BigDecimal.valueOf(problemCount),2, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(100));
                        }
                    }
                }
                model.setProblemCount(problemCount);
                model.setRectifiedProblemCount(rectifiedProblemCount);
                model.setRectifiedProblemRate(rectifiedProblemRate);
                datas.add(model);
                QueryWrapper<QsType> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("Qst_Parent_Id", model.getQstId());
                wrapper2.orderByAsc("Qst_Sort");
                List<QsType> childlist = baseMapper.selectList(wrapper2);
                if (CollectionUtil.isNotEmpty(childlist)){
                    getChildList(model, qsRecordResponses, datas);
                }
            });
        }
        String[] rowTitle = new String[]{"序号","id","parentId","path","层级","类别名称", "是否重大问题", "金额", "分值", "问题数量", "整改数量", "整改率（%）", "单位", "排序号", "状态"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("质安问题类别");
        for (int i = 0; i < rowTitle.length; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }

        if (!CollectionUtils.isEmpty(datas)) {
            for (QsTypeInfoModel model : datas) {
                if (model != null){
                    row = sheet.createRow(rowNum++);
                    cell = row.createCell(0);
                    cell.setCellValue(model.getQstNumber());
                    cell.setCellStyle(style);
                    cell = row.createCell(1);
                    cell.setCellValue(model.getQstId());
                    cell.setCellStyle(style);
                    cell = row.createCell(2);
                    cell.setCellValue(model.getQstParentId());
                    cell.setCellStyle(style);
                    cell = row.createCell(3);
                    cell.setCellValue(model.getQstPath());
                    cell.setCellStyle(style);
                    cell = row.createCell(4);
                    cell.setCellValue(model.getLevel());
                    cell.setCellStyle(style);
                    cell = row.createCell(5);
                    cell.setCellValue(model.getQstName());
                    cell.setCellStyle(style);
                    cell = row.createCell(6);
                    cell.setCellValue(model.getQstIsImportant()==null?"否":model.getQstIsImportant()==0?"否":"是");
                    cell.setCellStyle(style);
                    cell = row.createCell(7);
                    cell.setCellValue(model.getQstPunish());
                    cell.setCellStyle(style);
                    cell = row.createCell(8);
                    cell.setCellValue(model.getQstScore());
                    cell.setCellStyle(style);
                    cell = row.createCell(9);
                    cell.setCellValue(model.getProblemCount());
                    cell.setCellStyle(style);
                    cell = row.createCell(10);
                    cell.setCellValue(model.getRectifiedProblemCount());
                    cell.setCellStyle(style);
                    cell = row.createCell(11);
                    cell.setCellValue(model.getRectifiedProblemRate().doubleValue());
                    cell.setCellStyle(style);
                    cell = row.createCell(12);
                    cell.setCellValue(model.getQstUnit());
                    cell.setCellStyle(style);
                    cell = row.createCell(13);
                    cell.setCellValue(model.getQstSort());
                    cell.setCellStyle(style);
                    cell = row.createCell(14);
                    cell.setCellValue(model.getQstState()==null?"可用":model.getQstState()==0?"禁用":"可用");
                    cell.setCellStyle(style);
                }
            }
        }
        return workbook;
    }

    @Override
    public BaseResponse getQsTypeTree(QsTypeModel request) {
        LambdaQueryWrapper<QsType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(request.getTeId()), QsType::getTeId, request.getTeId());
        wrapper.orderByAsc(QsType::getQstSort);
        List<QsType> allList = baseMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(allList)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        boolean hasFilter = StrUtil.isNotBlank(request.getQstName())
                || StrUtil.isNotBlank(request.getQstCode())
                || StrUtil.isNotBlank(request.getQstParentId())
                || StrUtil.isNotBlank(request.getQstCategory())
                || request.getQstIsImportant() != null
                || request.getSeverityLevel() != null
                || request.getQstState() != null;
        List<QsType> treeSource = allList;
        if (hasFilter) {
            Map<String, QsType> nodeMap = allList.stream()
                    .collect(Collectors.toMap(QsType::getQstId, item -> item, (first, second) -> first));
            Set<String> retainedIds = new HashSet<>();
            allList.stream()
                    .filter(item -> matchesQsTypeFilter(item, request))
                    .forEach(item -> collectQsTypeAncestorIds(item, nodeMap, retainedIds));
            treeSource = allList.stream()
                    .filter(item -> retainedIds.contains(item.getQstId()))
                    .collect(Collectors.toList());
        }

        Set<String> treeNodeIds = treeSource.stream().map(QsType::getQstId).collect(Collectors.toSet());
        List<QsType> rootNodes = treeSource.stream()
                .filter(item -> StrUtil.isBlank(item.getQstParentId())
                        || "0".equals(item.getQstParentId())
                        || !treeNodeIds.contains(item.getQstParentId()))
                .collect(Collectors.toList());
        List<QsTypeModel> trees = new ArrayList<>();
        getQstTypeChildNodes(trees, rootNodes, treeSource);
        return BaseResponse.ok(trees);
    }

    /**
     * 判断质量安全类别是否符合请求中的全部筛选条件。
     *
     * @param qsType 质量安全类别
     * @param request 筛选条件
     * @return 是否匹配
     */
    private boolean matchesQsTypeFilter(QsType qsType, QsTypeModel request) {
        return (StrUtil.isBlank(request.getQstName()) || StrUtil.containsIgnoreCase(qsType.getQstName(), request.getQstName()))
                && (StrUtil.isBlank(request.getQstCode()) || StrUtil.containsIgnoreCase(qsType.getQstCode(), request.getQstCode()))
                && (StrUtil.isBlank(request.getQstParentId()) || Objects.equals(qsType.getQstParentId(), request.getQstParentId()))
                && (StrUtil.isBlank(request.getQstCategory()) || Objects.equals(qsType.getQstCategory(), request.getQstCategory()))
                && (request.getQstIsImportant() == null || Objects.equals(qsType.getQstIsImportant(), request.getQstIsImportant()))
                && (request.getSeverityLevel() == null || Objects.equals(qsType.getSeverityLevel(), request.getSeverityLevel()))
                && (request.getQstState() == null || Objects.equals(qsType.getQstState(), request.getQstState()));
    }

    /**
     * 收集命中节点及其一直到根节点的全部上级ID。
     *
     * @param node 当前节点
     * @param nodeMap 全部节点映射
     * @param retainedIds 需要保留的节点ID
     */
    private void collectQsTypeAncestorIds(QsType node, Map<String, QsType> nodeMap, Set<String> retainedIds) {
        QsType current = node;
        while (current != null && retainedIds.add(current.getQstId())) {
            current = nodeMap.get(current.getQstParentId());
        }
    }

    private String getChineseStr(String str){
        // 正则表达式，匹配汉字
        String regex = "[\\u4e00-\\u9fff]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        StringBuilder chineseCharacters = new StringBuilder();

        while (matcher.find()) {
            chineseCharacters.append(matcher.group());
        }
        return chineseCharacters.toString();
    }


    /**
     * 生成子级path
     * @param parentQsType
     * @param qsTypes
     * @param resultList
     */
    private void handleChildPath(QsType parentQsType, List<QsType> qsTypes, List<QsType> resultList) {

        List<QsType> childList = qsTypes.stream().filter(item -> parentQsType.getQstId().equals(item.getQstParentId())).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(childList)){
            for (QsType childQsType : childList) {
                childQsType.setQstPath(parentQsType.getQstPath()+"/"+childQsType.getQstId());
                handleChildPath(childQsType,qsTypes,resultList);
                resultList.add(childQsType);
            }
        }
    }

    private void handleChildQsType(QsTypeModel parentQsTypeModel, List<QsTypeModel> models, List<QsTypeModel> resultList) {
        String qstId = parentQsTypeModel.getQstId();
        List<QsTypeModel> childList = models.stream().filter(item -> qstId.equals(item.getQstParentId())).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(childList)){
            int sort=1;
            for (QsTypeModel childQsTypeModel : childList) {
                childQsTypeModel.setLevel(parentQsTypeModel.getLevel()+1);
                childQsTypeModel.setQstNumber(generateQsTypeNumber(parentQsTypeModel.getQstNumber(),childQsTypeModel,sort));
                resultList.add(childQsTypeModel);
                sort++;
                handleChildQsType(childQsTypeModel,models,resultList);
            }
        }
    }

    /**
     * 生成序号
     * @param parentNumber
     * @param qsTypeModel
     * @param sort
     * @return
     */
    private String generateQsTypeNumber(String parentNumber, QsTypeModel qsTypeModel, int sort) {
        if(qsTypeModel.getQstSort()!=null){
            sort=qsTypeModel.getQstSort();
        }
        if(StrUtil.isNotBlank(parentNumber)){
            return parentNumber + "." + sort;
        }
        return  String.valueOf(sort);
    }

    private void getParentType(QsType qstType,List<String> qstNameList) {
        if(qstType!=null){
            qstNameList.add(qstType.getQstName());
            String qstParentId = qstType.getQstParentId();
            QsType parentType = baseMapper.getQsTypeById(qstParentId);
            getParentType(parentType,qstNameList);
        }

    }

    private void assembleChild(List<QsType> qsTypeList, List<QsType> allList, List<QsType> resultList) {

        for (QsType qsType : qsTypeList) {
            List<QsType> childList = allList.stream().filter(item -> qsType.getQstId().equals(item.getQstParentId())).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(childList)){
                assembleChild(childList, allList, resultList);
                resultList.addAll(childList);
            }
        }


    }

    private void getQstTypeChildNodes(List<QsTypeModel> trees, List<QsType> list, List<QsType> allList) {
        list.forEach(node -> {
            QsTypeModel tree = new QsTypeModel();
            BeanUtils.copyProperties(node, tree);
            List<QsTypeModel> childNodes = new ArrayList<>();
            List<QsType> childs = allList.stream().filter(n -> n.getQstParentId().equals(node.getQstId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getQstTypeChildNodes(childNodes, childs, allList);
            tree.setChildNodes(childNodes);
            trees.add(tree);
        });
    }
}
