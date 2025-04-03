package com.skhuni.skhunibackend.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "oauth")
public class OauthProperties {
    
    private KakaoProperties kakao;
    private GoogleProperties google;

    public record KakaoProperties(
            String clientId,
            String redirectUri,
            String idTokenUrl
    ) {
    }

    public record GoogleProperties(
            String clientId,
            String clientSecret,
            String redirectUri,
            String idTokenUrl
    ) {
    }
}