package com.skhuni.skhunibackend.auth.application;

import com.skhuni.skhunibackend.auth.api.dto.response.MemberLoginResDto;
import com.skhuni.skhunibackend.auth.api.dto.response.UserInfo;
import com.skhuni.skhunibackend.auth.exception.ExistsMemberEmailException;
import com.skhuni.skhunibackend.global.discord.util.DiscordWebhookUtil;
import com.skhuni.skhunibackend.member.domain.Member;
import com.skhuni.skhunibackend.member.domain.MemberLink;
import com.skhuni.skhunibackend.member.domain.Role;
import com.skhuni.skhunibackend.member.domain.SocialType;
import com.skhuni.skhunibackend.member.domain.repository.MemberLinkRepository;
import com.skhuni.skhunibackend.member.domain.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthMemberService {

    private final MemberRepository memberRepository;
    private final MemberLinkRepository memberLinkRepository;
    private final DiscordWebhookUtil discordWebhookUtil;

    @Transactional
    public MemberLoginResDto saveUserInfo(UserInfo userInfo, SocialType provider) {
        Member member = getExistingMemberOrCreateNew(userInfo, provider);

        validateSocialType(member, provider);

        return MemberLoginResDto.from(member);
    }

    private Member getExistingMemberOrCreateNew(UserInfo userInfo, SocialType provider) {
        return memberRepository.findByEmail(userInfo.email()).orElseGet(() -> createMember(userInfo, provider));
    }

    private Member createMember(UserInfo userInfo, SocialType provider) {
        String userPicture = getUserPicture(userInfo.picture());
        String name = userInfo.nickname() != null ? userInfo.nickname() : userInfo.name();

        Member member = memberRepository.save(
                Member.builder()
                        .email(userInfo.email())
                        .name(name)
                        .picture(userPicture)
                        .socialType(provider)
                        .role(Role.ROLE_USER)
                        .build()
        );
        memberLinkRepository.save(
                MemberLink.builder()
                        .member(member)
                        .build()
        );

        int totalMembers = memberRepository.findAll().size();
        discordWebhookUtil.sendDiscordMessage(String.format("%d번째 회원입니다!", totalMembers), member.getId().toString());

        return member;
    }

    private String getUserPicture(String picture) {
        return Optional.ofNullable(picture)
                .map(this::convertToHighRes)
                .orElseThrow(null);
    }

    private String convertToHighRes(String url) {
        return url.replace("s96-c", "s2048-c");
    }

    private void validateSocialType(Member member, SocialType provider) {
        if (!provider.equals(member.getSocialType())) {
            throw new ExistsMemberEmailException();
        }
    }

}
