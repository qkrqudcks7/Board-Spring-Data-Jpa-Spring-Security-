package com.example.makeboard.service;

import com.example.makeboard.domain.Content;
import com.example.makeboard.domain.Member;
import com.example.makeboard.repository.ContentRepository;
import com.example.makeboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentService {

    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;

    public List<Content> findAll(){
       return contentRepository.findAll();
    }

    @Transactional
    public void saveContent(Content content){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Member member = memberRepository.findByUserId(name);
        content.setMember(member);
        if(content.getMember().getUserId()==name){
            contentRepository.save(content);
        }
    }

    public Content findOne(Long id){
        Optional<Content> byId = contentRepository.findById(id);
        Content content = byId.get();
        return content;
    }
    @Transactional
    public void UpdateContent(Content content){
        Content content1 = contentRepository.findById(content.getId()).get();
        content1.getMember().setName(content.getMember().getName());
        content1.setSubject(content.getSubject());
        content1.setText(content.getText());

    }
    @Transactional
    public void deleteBoard(Long id){
        contentRepository.deleteById(id);
    }
}
