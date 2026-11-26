package com.jpmsoft.jputils.response;

import lombok.Data;

/**
 * @author yb.Sun
 * @date 2025/1/17 10:36
 * {
 *   "data": {
 *     "resultCount": 2,
 *     "results": [ // 识别结果，多张不同种类的票据，混贴在一起，返回多个
 *       {
 *         "type": "vat_invoice", // 票据种类, 详见【返回结果示例下方】
 *         "content": {
 *           //单张票据的识别结果,详见【返回结果示例下方】【注意：type不同值时，content中结果不一样】
 *         }
 *       }
 *     ],
 *     "pdfSize":1, //传入PDF文件的总页数，当 pdf_file 参数有效时返回该字段
 *   },
 *   "code": 200, // 详见code返回码说明
 *   "msg": "成功", // code 对应的描述
 *   "taskNo": "30853523396532582768",  // 本次请求号
 * }
 */
@Data
public class InvoiceResponse {
    private Boolean success;
    private Integer code;
    private String taskNo;
    private InvoiceDataResponse data;
    private String msg;

}
