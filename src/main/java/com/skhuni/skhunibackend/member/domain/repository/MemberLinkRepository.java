package com.skhuni.skhunibackend.member.domain.repository;

import com.skhuni.skhunibackend.member.domain.MemberLink;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLinkRepository extends JpaRepository<MemberLink, Long> {

    Optional<MemberLink> findByMemberId(Long memberId);

}
