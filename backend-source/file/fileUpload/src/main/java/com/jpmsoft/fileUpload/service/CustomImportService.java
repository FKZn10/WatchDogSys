package com.jpmsoft.fileUpload.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CustomImportService {

    /**
     * 自定义导入数据
     * @param files
     * @param userInfoByToken
     * @return
     */
    BaseResponse importData(MultipartFile[] files, LogUserInfoVo userInfoByToken);

    /**
     * 举报台账导入
     * @param file
     * @param userInfoByToken
     * @return
     */
    BaseResponse importComplaintData(MultipartFile file, LogUserInfoVo userInfoByToken);

    /**
     * 诉讼台账导入
     * @param file
     * @param userInfoByToken
     * @return
     */
    BaseResponse importLawsuitData(MultipartFile file, LogUserInfoVo userInfoByToken);

    /**
     * 省试点项目 补充导入
     *
     * @param file  Excel 文件
     * @param user  导入人
     * @param token 令牌（透传给 PP）
     * @return
     */
    BaseResponse importProvPilotProject(MultipartFile file, LogUserInfoVo user, String token);

    /**
     * 省试点企业 补充导入
     *
     * @param file  Excel 文件
     * @param user  导入人
     * @param token 令牌
     * @return
     */
    BaseResponse importProvPilotEnterprise(MultipartFile file, LogUserInfoVo user, String token);

}
