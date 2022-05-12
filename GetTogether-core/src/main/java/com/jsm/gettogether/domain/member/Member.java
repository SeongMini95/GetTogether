package com.jsm.gettogether.domain.member;

import com.jsm.gettogether.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 20)
    private String email;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "nickname", length = 10)
    private String nickname;

    @Column(name = "profile_path")
    private String profilePath;

    @Column(name = "is_leave", nullable = false)
    private boolean isLeave;

    @Column(name = "is_deny", nullable = false)
    private boolean isDeny;

    @Builder
    public Member(String email, String password, String nickname, String profilePath, boolean isLeave, boolean isDeny) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.isLeave = isLeave;
        this.isDeny = isDeny;
    }
}
