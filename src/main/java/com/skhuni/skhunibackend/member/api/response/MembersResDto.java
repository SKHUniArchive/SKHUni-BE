package com.skhuni.skhunibackend.member.api.response;

import com.skhuni.skhunibackend.global.dto.PageInfoResDto;
import java.util.List;
import lombok.Builder;

@Builder
public record MembersResDto(
        List<MemberInfoResDto> members,
        PageInfoResDto pageInfo
) {
    public static MembersResDto from(List<MemberInfoResDto> members, PageInfoResDto pageInfoResDto) {
        return MembersResDto.builder()
                .members(members)
                .pageInfo(pageInfoResDto)
                .build();
    }
}
