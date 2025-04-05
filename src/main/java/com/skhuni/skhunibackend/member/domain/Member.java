package com.skhuni.skhunibackend.member.domain;

import com.skhuni.skhunibackend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String name;

    private String picture;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    private String contactEmail;

    private String studentId;

    @Column(columnDefinition = "TEXT")
    private String introLine;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Enumerated(value = EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

    @Enumerated(value = EnumType.STRING)
    private FieldType fieldType;

    private String techStack;

    private boolean isCoffeeChatOpen;

    private boolean isCodeReviewOpen;

    @Builder
    private Member(Role role, String email, String name, String picture, SocialType socialType) {
        this.role = role;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.socialType = socialType;
    }

    public void updateInfo(String name, String contactEmail, String studentId, String introLine, String introduction,
                           EnrollmentStatus enrollmentStatus, FieldType fieldType,
                           String techStack, boolean isCoffeeChatOpen, boolean isCodeReviewOpen) {
        if (!Objects.equals(this.name, name)) {
            this.name = name;
        }

        if (!Objects.equals(this.contactEmail, contactEmail)) {
            this.contactEmail = contactEmail;
        }

        if (!Objects.equals(this.studentId, studentId)) {
            this.studentId = studentId;
        }

        if (!Objects.equals(this.introLine, introLine)) {
            this.introLine = introLine;
        }

        if (!Objects.equals(this.introduction, introduction)) {
            this.introduction = introduction;
        }

        if (this.enrollmentStatus != enrollmentStatus) {
            this.enrollmentStatus = enrollmentStatus;
        }

        if (this.fieldType != fieldType) {
            this.fieldType = fieldType;
        }

        if (!Objects.equals(this.techStack, techStack)) {
            this.techStack = techStack;
        }

        if (this.isCoffeeChatOpen != isCoffeeChatOpen) {
            this.isCoffeeChatOpen = isCoffeeChatOpen;
        }

        if (this.isCodeReviewOpen != isCodeReviewOpen) {
            this.isCodeReviewOpen = isCodeReviewOpen;
        }
    }

    public void updateStudentRole() {
        this.role = Role.ROLE_STUDENT;
    }

    public void updatePicture(String picture) {
        this.picture = picture;
    }

}
