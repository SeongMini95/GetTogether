package com.jsm.gettogether.domain.memberrole.repository;

import com.jsm.gettogether.domain.memberrole.MemberRole;
import com.jsm.gettogether.domain.memberrole.MemberRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, MemberRoleId> {
}
