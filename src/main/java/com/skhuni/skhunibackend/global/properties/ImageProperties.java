package com.skhuni.skhunibackend.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "image")
public class ImageProperties {

    private String defaultProjectImage;
    
}
