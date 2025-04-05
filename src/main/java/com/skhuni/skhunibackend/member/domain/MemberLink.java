package com.skhuni.skhunibackend.member.domain;

import com.skhuni.skhunibackend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLink extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_link_id")
    private Long id;

    private String notion;

    private String github;

    private String linkedIn;

    private String etc1;

    private String etc2;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private MemberLink(Member member) {
        this.member = member;
    }

    public void updateLinks(String notion, String github, String linkedIn, String etc1, String etc2) {
        if (!Objects.equals(this.notion, notion)) {
            this.notion = notion;
        }

        if (!Objects.equals(this.github, github)) {
            this.github = github;
        }

        if (!Objects.equals(this.linkedIn, linkedIn)) {
            this.linkedIn = linkedIn;
        }

        if (!Objects.equals(this.etc1, etc1)) {
            this.etc1 = etc1;
        }

        if (!Objects.equals(this.etc2, etc2)) {
            this.etc2 = etc2;
        }
    }

}
