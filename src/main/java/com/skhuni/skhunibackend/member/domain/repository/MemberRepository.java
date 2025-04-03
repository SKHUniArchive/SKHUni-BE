package com.skhuni.skhunibackend.member.domain.repository;


import com.skhuni.skhunibackend.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
    Optional<Member> findByEmail(String email);

    boolean existsByNickname(String nickname);
}
