package com.example.makeboard.controller;

import com.example.makeboard.domain.Member;
import com.example.makeboard.dto.MemberDto;
import com.example.makeboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/join")
    public String join(Model model) {
        model.addAttribute("memberDto",new MemberDto());
        return "member/join";
    }

    @PostMapping("/member/new")
    public String joinUser(@Valid MemberDto dto , BindingResult result) {
        if(result.hasErrors()){
            return "member/join";
        }
        memberService.joinUser(dto);

       return "redirect:/member/login";
    }

    @GetMapping("/member/login")
    public String login(@RequestParam(value = "fail" , defaultValue = "false") boolean fail,
                        Model model){

        model.addAttribute("fail",fail);

        return "/member/login";
    }

    @GetMapping("/member/login_success")
    public String login_success(){
        return "/member/login_success";
    }

    @GetMapping("/member/login_fail")
    public String login_fail(){
        return "/member/login_fail";
    }

    @GetMapping("/member/modify")
    public String modify(@ModelAttribute("member") Member member) {

        memberService.findMember(member);

        return "member/modify";

    }
    @PostMapping("/member/modify_pro")
    public String modify_pro(@ModelAttribute("member") Member member){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setPassword(encoder.encode(member.getPassword()));
        if(!encoder.matches(member.getPassword2(),member.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        memberService.modifyMemberInfo(member.getPassword(),member.getPassword2());

        return "redirect:/";
    }

    @GetMapping("/member/logout_result")
    public String logout_result() {

        return "member/logout";
    }

    @GetMapping("/member/not_login")
    public String not_login(){
        return "member/not_login";
    }
}
