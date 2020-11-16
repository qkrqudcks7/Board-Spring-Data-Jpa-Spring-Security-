package com.example.makeboard.controller;

import com.example.makeboard.domain.Content;
import com.example.makeboard.domain.Member;
import com.example.makeboard.repository.MemberRepository;
import com.example.makeboard.service.ContentService;
import com.example.makeboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ContentService contentService;

    private final MemberRepository memberRepository;

    @GetMapping("/board/main")
    public String main(Model model){

        List<Content> contentList =contentService.findAll();
        model.addAttribute("contentList",contentList);

        return "/board/main";
    }

    @GetMapping("/board/write")
    public String write(@ModelAttribute("content") Content content) {

        return "/board/write";
    }

    @PostMapping("/board/write_pro")
    public String write_pro(@ModelAttribute("content") Content content,
                            @RequestParam("img") MultipartFile files) throws IOException {
        if(files.getSize()>0){
            String base="C:\\Users\\박병찬\\IdeaProjects\\makeboard\\src\\main\\resources\\static\\img";
            String filePath=base+"\\"+files.getOriginalFilename();
            files.transferTo(new File(filePath));
            content.setFileUrl(filePath);
        }
        contentService.saveContent(content);

        return "redirect:/board/main";
    }

    @GetMapping("/board/read")
    public String read(@RequestParam("id") Long id,
                        Model model){
        model.addAttribute("id",id);
        Content content = contentService.findOne(id);
        model.addAttribute("content",content);
        return "board/read";
    }

    @GetMapping("/board/modify")
    public String modify(@RequestParam("id") Long id,
                         Model model){
        Content content = contentService.findOne(id);
        model.addAttribute("content",content);
        model.addAttribute("id",id);

        return "board/modify";

    }
    @PostMapping("/board/modify_pro")
    public String modify_pro(@ModelAttribute("content") Content content,
                             @RequestParam("img") MultipartFile files,
                             Model model) throws IOException {
        if(files.getSize()>0){
            String base="C:\\Users\\박병찬\\IdeaProjects\\makeboard\\src\\main\\resources\\static\\img";
            String filePath=base+"\\"+files.getOriginalFilename();
            files.transferTo(new File(filePath));
            content.setFileUrl(filePath);
        }

        contentService.UpdateContent(content);

        return "board/main";
    }

    @GetMapping("/board/delete")
    public String delete(@RequestParam("id") Long id){
        contentService.deleteBoard(id);
        return "board/delete";
    }
}
