package com.jpmsoft.projectProducer.easyexcel.annotations;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.FileMsg;
import com.jpmsoft.projectProducer.service.other.ISysDictionaryService;
import com.jpmsoft.projectProducer.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;

/**
 * 验证ExcelDataValid注解标注字段是否为空
 *
 */
@Component
public class ExcelImportDataValid {

    static ISysDictionaryService sysDictionaryService;

    // 将静态属性以入参的方式传入，然后通过@Autowired注入到spring容器中
    @Autowired
    public void setSysDictionaryService(ISysDictionaryService sysDictionaryService) {
        ExcelImportDataValid.sysDictionaryService = sysDictionaryService;
    }

    /**
     * @param object    行对象
     * @param cellIndex 当前行数据
     * @throws JPMException
     */
    public static void valid(Object object, int cellIndex) throws JPMException {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            //设置可访问
            field.setAccessible(true);
            //属性的值
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new JPMException(FileMsg.excelValidFail);
            }
            //是否包含必填校验注解
            boolean isExcelValid = field.isAnnotationPresent(ExcelDataValid.class);
            if (isExcelValid) {
                ExcelDataValid excelDataValid = field.getAnnotation(ExcelDataValid.class);
                String dataType = excelDataValid.dataType();
                String dataTypeMsg = excelDataValid.dataTypeMsg();


                String errorMsg = ValidateUtil.isEmpty(excelDataValid.message()) ? MessageFormat.format(FileMsg.cellIsEmpty, cellIndex, ExcelUtil.numCovertLetter(field.getAnnotation(ExcelProperty.class).index() + 1)) : excelDataValid.message();
                if (excelDataValid.olnySpace() && ValidateUtil.isEmpty(fieldValue)) {
                    throw new JPMException(errorMsg);
                } else if (!excelDataValid.olnySpace()) {
                    //内容仅为空格不判定为空
                    if (ValidateUtil.isEmpty(fieldValue) && !(fieldValue + "").contains(" "))
                        throw new JPMException(errorMsg);
                }

                checkDataType(dataType,fieldValue,dataTypeMsg, cellIndex, ExcelUtil.numCovertLetter(field.getAnnotation(ExcelProperty.class).index() + 1));

                if (ValidateUtil.isNotEmpty(excelDataValid.dirCheck())) {
                    List<String> dirs = ExcelImportDataValid.sysDictionaryService.getCodeList(excelDataValid.dirCheck());
                    if (!CollectionUtils.isEmpty(dirs)) {
                        if (!dirs.contains(fieldValue))
                            throw new JPMException(String.format(FileMsg.unknownFormat, cellIndex, ExcelUtil.numCovertLetter(field.getAnnotation(ExcelProperty.class).index() + 1)));
                    } else
                        throw new JPMException(String.format(FileMsg.notFoundDictionary, excelDataValid.dirCheck()));
                }
            }
        }
    }

    private static String dataTypeToStr(String dataType) {
        switch (dataType){
            case "int":
            case "Integer":
            case "double":
            case "Double":
            case "long":
            case "Long":
                return "数值类型";
            case "date":
            case "Date":
            case "dateTime":
            case "DateTime":
                return "日期类型";
            case "string":
            case "String":
                return "字符串类型";
            default:
                return "";
        }
    }

    private static void checkDataType(String dataType, Object fieldValue, String dataTypeMsg, int cellIndex, String s) {
        String dataTypeMsgStr=StrUtil.isBlank(dataTypeMsg)?"请填写"+dataTypeToStr(dataType):dataTypeMsg;
        String msg=cellIndex+"行"+s+"列数据类型不正确，"+dataTypeMsgStr;
        Class<?> aClass = fieldValue.getClass();
        String simpleName = aClass.getSimpleName();
        if(StrUtil.isNotBlank(dataType)){
            switch (dataType){
                case "int":
                case "Integer":
                    if(!"int".equals(simpleName) && !"Integer".equals(simpleName)){//如果数据类型是int或Integer（就是excel数据已经转成了int），则不进行校验
                        try {
                            Integer.parseInt((String) fieldValue);
                        }catch (Exception e){
                            throw new JPMException(msg);
                        }
                    }
                    break;
                case "long":
                case "Long":
                    if(!"long".equals(simpleName) && !"Long".equals(simpleName)){
                        try {
                            Long.parseLong((String) fieldValue);
                        }catch (Exception e){
                            throw new JPMException(msg);
                        }
                    }
                    break;
                case "boolean":
                case "Boolean":
                    if(!"boolean".equals(simpleName) && !"Boolean".equals(simpleName)){
                        try {
                            Boolean.parseBoolean((String) fieldValue);
                        }catch (Exception e){
                            throw new JPMException(msg);
                        }
                    }
                    break;
                case "double":
                case "Double":
                    if(!"double".equals(simpleName) && !"Double".equals(simpleName)){
                        try {
                            Double.valueOf((String) fieldValue);
                        }catch (Exception e){
                            throw new JPMException(msg);
                        }
                    }
                    break;
                case "float":
                case "Float":
                    if("float".equals(simpleName) && !"Float".equals(simpleName)){
                        try {
                            Float.valueOf((String) fieldValue);
                        }catch (Exception e){
                            throw new JPMException(msg);
                        }
                    }
                    break;
                case "date":
                case "Date":
                    if(!"date".equals(simpleName) && !"Date".equals(simpleName)){
                        try {
                            DateUtil.parseDate((String) fieldValue);
                        }catch (Exception e){
                            throw new JPMException(msg);
                        }
                    }
                    break;
                case "dateTime":
                case "DateTime":
                    if(!"dateTime".equals(dataType) && !"DateTime".equals(simpleName)){
                        try {
                            DateUtil.parseDateTime((String) fieldValue);
                        }catch (Exception e){
                            throw new JPMException(msg);
                        }
                    }
                    break;
            }
        }
    }
}
