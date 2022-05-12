package com.jsm.gettogether.domain.memberrole;

import com.jsm.gettogether.domain.member.Member;
import com.jsm.gettogether.domain.memberrole.converter.RoleDivConverter;
import com.jsm.gettogether.domain.memberrole.enums.RoleDiv;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"member", "roleDiv"})
@Getter
@Embeddable
public class MemberRoleId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Convert(converter = RoleDivConverter.class)
    @Column(name = "role_div", nullable = false)
    private RoleDiv roleDiv;

    @Builder
    public MemberRoleId(Member member, RoleDiv roleDiv) {
        this.member = member;
        this.roleDiv = roleDiv;
    }
}
