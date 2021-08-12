package com.pro1.pro.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("com.pro1.pro")
public class ShopProperties {
    private String uploadPath;
}
