package com.jpmsoft.projectProducer.pojo.entity.design;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DesignResultsIssuedTreeNode {

    private String id;

    private String parentId;

    private String name;

    private List<DesignResultsIssuedTreeNode> children = new ArrayList<>();
}
