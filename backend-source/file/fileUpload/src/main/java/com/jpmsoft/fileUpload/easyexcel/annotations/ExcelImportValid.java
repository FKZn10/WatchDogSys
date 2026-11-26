package com.jpmsoft.fileUpload.easyexcel.annotations;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.service.ISysDictionaryService;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;

/**
 * 验证ExcelValid注解标注字段是否为空
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 11:25
 */
@Component
public class ExcelImportValid {

    static ISysDictionaryService sysDictionaryService;

    // 将静态属性以入参的方式传入，然后通过@Autowired注入到spring容器中
    @Autowired
    public void setSysDictionaryService(ISysDictionaryService sysDictionaryService) {
        ExcelImportValid.sysDictionaryService = sysDictionaryService;
    }

    /**
     * @param object    行对象
     * @param cellIndex 当前为X行数据
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
            boolean isExcelValid = field.isAnnotationPresent(ExcelValid.class);
            if (isExcelValid) {
                ExcelValid excelValid = field.getAnnotation(ExcelValid.class);
                String errorMsg = ValidateUtil.isEmpty(excelValid.message()) ? MessageFormat.format(FileMsg.cellIsEmpty, cellIndex, ExcelUtil.numCovertLetter(field.getAnnotation(ExcelProperty.class).index() + 1)) : excelValid.message();
                if (excelValid.olnySpace() && ValidateUtil.isEmpty(fieldValue)) {
                    throw new JPMException(errorMsg);
                } else if (!excelValid.olnySpace()) {
                    //内容仅为空格不判定为空
                    if (ValidateUtil.isEmpty(fieldValue) && !(fieldValue + "").contains(" "))
                        throw new JPMException(errorMsg);
                }
                if (ValidateUtil.isNotEmpty(excelValid.dirCheck())) {
                    List<String> dirs = ExcelImportValid.sysDictionaryService.getCodeList(excelValid.dirCheck());
                    if (!CollectionUtils.isEmpty(dirs)) {
                        if (!dirs.contains(fieldValue))
                            throw new JPMException(String.format(FileMsg.unknownFormat, cellIndex, ExcelUtil.numCovertLetter(field.getAnnotation(ExcelProperty.class).index() + 1)));
                    } else
                        throw new JPMException(String.format(FileMsg.notFoundDictionary, excelValid.dirCheck()));
                }
            }
        }
    }
}
