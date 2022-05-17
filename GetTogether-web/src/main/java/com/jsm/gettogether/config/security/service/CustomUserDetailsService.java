package com.jsm.gettogether.config.security.service;

import com.jsm.gettogether.config.security.dto.CustomUserDetails;
import com.jsm.gettogether.domain.member.Member;
import com.jsm.gettogether.domain.member.repository.MemberRepository;
import com.jsm.gettogether.domain.memberrole.MemberRole;
import com.jsm.gettogether.domain.memberrole.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        Set<MemberRole> memberRoleSet = memberRoleRepository.findByMemberRoleIdMember(member);

        return new CustomUserDetails(member,
                member.getEmail(),
                member.getPassword(),
                memberRoleSet.stream()
                        .map(r -> new SimpleGrantedAuthority(r.getMemberRoleId().getRoleDiv().name()))
                        .collect(Collectors.toSet()));
    }
}
