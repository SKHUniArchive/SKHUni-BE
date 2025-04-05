package com.skhuni.skhunibackend.member.domain.repository;

import com.skhuni.skhunibackend.member.domain.MemberLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLinkRepository extends JpaRepository<MemberLink, Long> {

    MemberLink findByMemberId(Long memberId);
    
}
