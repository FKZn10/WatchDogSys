package com.jpmsoft.projectProducer.pojo.vo.materialsEquipment;

import lombok.Data;

import java.util.List;

@Data
public class MeTypeLibraryWithTreeVO {

    private String metId;

    private String label;

    private String value;

    private String parentId;

    private List<MeTypeLibraryWithTreeVO> children;

}
