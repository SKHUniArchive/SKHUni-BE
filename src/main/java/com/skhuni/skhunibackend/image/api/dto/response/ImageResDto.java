package com.skhuni.skhunibackend.image.api.dto.response;

import com.skhuni.skhunibackend.image.domain.Image;
import lombok.Builder;

@Builder
public record ImageResDto(
        String convertImageUrl
) {
    public static ImageResDto from(Image image) {
        return ImageResDto.builder()
                .convertImageUrl(image.getConvertImageUrl())
                .build();
    }
}
