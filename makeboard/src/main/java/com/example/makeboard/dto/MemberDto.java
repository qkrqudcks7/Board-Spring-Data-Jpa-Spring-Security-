package com.example.makeboard.dto;

import com.example.makeboard.domain.Member;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
@ToString
@NoArgsConstructor
public class MemberDto {

    private Long id;
    @NotEmpty(message = "회원 아이디는 필수입니다.")
    @Size(min=4,max=20,message = "4~20 글자로 입력해주세요!")
    @Pattern(regexp = "[a-zA-Z0-9]*",message = "특수문자는 안됩니다.")
    private String userId;

    @NotEmpty(message = "회원 비밀번호는 필수입니다.")
    @Size(min=4,max=100,message = "4~20 글자로 입력해주세요!")
    @Pattern(regexp = "[a-zA-Z0-9]*",message = "특수문자는 안됩니다.")
    private String password;

    @NotEmpty(message = "비밀번호를 다시 입력해주세요!")
    @Size(min=4,max=100,message = "4~20 글자로 입력해주세요!")
    @Pattern(regexp = "[a-zA-Z0-9]*",message = "특수문자는 안됩니다.")
    private String password2;

    @NotEmpty(message = "회원 이름은 필수입니다.")
    @Size(min = 2,max = 4,message = "2~4 글자 사이로 입력해주세요")
    @Pattern(regexp = "[가-힣]*",message = "한글로 입력해주세요")
    private String name;

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .password2(password2)
                .name(name)
                .build();
    }
    @Builder
    public MemberDto(Long id,String name,String userId,String password, String password2) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.password2 = password2;
    }
}
