package com.jpmsoft.projectProducer.pojo.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 轮播图返回体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselImageDTO {
    private String imgUrl;
    private String imgName;
}
