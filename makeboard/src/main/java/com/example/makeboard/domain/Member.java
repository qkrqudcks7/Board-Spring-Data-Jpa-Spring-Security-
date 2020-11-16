package com.example.makeboard.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userId;
    private String password;
    private String password2;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Content> contents = new ArrayList<>();


    @Builder
    public Member(Long id, String userId, String password, String password2,String name) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.password2 = password2;
        this.name = name;
    }
}
