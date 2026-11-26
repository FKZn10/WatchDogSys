package com.jpmsoft.jpbpm.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileSecidsVo {
    private String secId;
    private List<FileSecidVo> data;
}
