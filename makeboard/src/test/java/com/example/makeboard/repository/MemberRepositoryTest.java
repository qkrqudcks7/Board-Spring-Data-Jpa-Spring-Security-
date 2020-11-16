package com.example.makeboard.repository;

import com.example.makeboard.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void test1(){
        Member member= new Member(1L,"aaaa","1234","1234","park");
        memberRepository.save(member);

        Member member1 = memberRepository.findByUserId("aaaa");

        if(member1==null){
            throw new NullPointerException("null");
        }

        System.out.println(member1.getId());
        System.out.println(member1.getUserId());
        System.out.println(member1.getPassword());
        System.out.println(member1.getPassword2());
        System.out.println(member1.getName());


    }
}