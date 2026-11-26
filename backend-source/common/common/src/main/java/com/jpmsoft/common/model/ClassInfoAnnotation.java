package com.jpmsoft.common.model;

import lombok.Data;

import java.util.List;

@Data
public class ClassInfoAnnotation {
   private String title;
   private String type;
   private String value;
   private String dbValue;
   private String dictionary;
   private int no;
   private List<Object> dictionaryValues;
   private List<FilterObject> dictionaryFilterValues;
}
