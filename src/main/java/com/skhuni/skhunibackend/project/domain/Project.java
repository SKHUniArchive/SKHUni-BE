package com.skhuni.skhunibackend.project.domain;

import com.skhuni.skhunibackend.global.entity.BaseEntity;
import com.skhuni.skhunibackend.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    private String title;

    private String picture;

    @Column(columnDefinition = "TEXT")
    private String introLine;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private String githubLink1;

    private String githubLink2;

    private String siteLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Project(String title, String picture, String introLine, String introduction, String githubLink1,
                    String githubLink2, String siteLink, Member member) {
        this.title = title;
        this.picture = picture;
        this.introLine = introLine;
        this.introduction = introduction;
        this.githubLink1 = githubLink1;
        this.githubLink2 = githubLink2;
        this.siteLink = siteLink;
        this.member = member;
    }

    public void update(String title, String picture, String introLine, String introduction, String githubLink1,
                       String githubLink2, String siteLink) {
        if (!Objects.equals(this.title, title)) {
            this.title = title;
        }

        if (!Objects.equals(this.picture, picture)) {
            this.picture = picture;
        }

        if (!Objects.equals(this.introLine, introLine)) {
            this.introLine = introLine;
        }

        if (!Objects.equals(this.introduction, introduction)) {
            this.introduction = introduction;
        }

        if (!Objects.equals(this.githubLink1, githubLink1)) {
            this.githubLink1 = githubLink1;
        }

        if (!Objects.equals(this.githubLink2, githubLink2)) {
            this.githubLink2 = githubLink2;
        }

        if (!Objects.equals(this.siteLink, siteLink)) {
            this.siteLink = siteLink;
        }
    }

}
