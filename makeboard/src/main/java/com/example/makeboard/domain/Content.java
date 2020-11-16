package com.example.makeboard.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
public class Content {
    @Id
    @GeneratedValue
    @Column(name = "content_id")
    private Long id;
    private String subject;
    private String text;
    private LocalDateTime localDateTime;

    @Column
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member){
        this.member=member;
        member.getContents().add(this);
    }
}
