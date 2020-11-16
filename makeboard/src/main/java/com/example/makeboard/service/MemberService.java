package com.example.makeboard.service;

import com.example.makeboard.domain.Member;
import com.example.makeboard.dto.MemberDto;
import com.example.makeboard.dto.Role;
import com.example.makeboard.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
                                        // matches(평문,암호화)
        if (!passwordEncoder.matches(memberDto.getPassword2(),memberDto.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        return memberRepository.save(memberDto.toEntity()).getId();
    }


    public boolean validateDuplicateMember(String userid){
        Member member = memberRepository.findByUserId(userid);

        if(member==null){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

        Member member = memberRepository.findByUserId(userid);

        if(member==null){
            throw new UsernameNotFoundException("not found");
        }

        List<GrantedAuthority> authorities= new ArrayList<>();
        if(("qkrqudcks7").equals(userid)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(member.getUserId(),member.getPassword(),authorities);
    }

    public void findMember(Member member){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId=authentication.getName();;
        Member byUserId = memberRepository.findByUserId(userId);

        member.setUserId(byUserId.getUserId());
        member.setPassword(byUserId.getPassword());
        member.setPassword2(byUserId.getPassword2());
        member.setId(byUserId.getId());
        member.setName(byUserId.getName());
    }
    public void modifyMemberInfo(String password , String password2){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userId=authentication.getName();

        memberRepository.updateByUserId(password,password2,userId);
    }

}
