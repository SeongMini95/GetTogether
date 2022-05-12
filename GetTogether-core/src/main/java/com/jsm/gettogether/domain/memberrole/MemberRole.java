package com.jsm.gettogether.domain.memberrole;

import com.jsm.gettogether.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "memberRoleId", callSuper = false)
@Getter
@Entity
@Table(name = "member_role")
public class MemberRole extends BaseTimeEntity {

    @EmbeddedId
    private MemberRoleId memberRoleId;

    @Builder
    public MemberRole(MemberRoleId memberRoleId) {
        this.memberRoleId = memberRoleId;
    }
}
