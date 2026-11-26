package com.jpmsoft.fileUpload.service;

import javax.servlet.http.HttpServletResponse;

/**
 * word模板填充
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/10 8:51
 */
public interface IWordTempFillService {


    void exportQsPlanTaskWord(HttpServletResponse response, String id,String tmpCode);


    void exportQsPlanTaskExcel(HttpServletResponse response, String id);
}
