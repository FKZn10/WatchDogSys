package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 积木报表导出PDF请求入参
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/12 8:57
 */
@Data
public class JMExportDTO implements Serializable {
    private static final long serialVersionUID = 9143278467167787261L;
    private String excelConfigId;
    private QueryParam queryParam;

  @Data
  public static class QueryParam {
        private int currentPageNo = 1;
        private int currentPageSize = 10;
        private String id;
        private int pageNo = 1;
        private int pageSize = 10;
        private String token;
    }
}
