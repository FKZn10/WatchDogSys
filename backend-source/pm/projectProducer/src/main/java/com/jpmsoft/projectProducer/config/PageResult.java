package com.jpmsoft.projectProducer.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -8574929449853309299L;
    private List<T> rows;
    private long total;
}
