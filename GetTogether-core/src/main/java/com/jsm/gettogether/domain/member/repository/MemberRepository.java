package com.jsm.gettogether.domain.member.repository;

import com.jsm.gettogether.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
