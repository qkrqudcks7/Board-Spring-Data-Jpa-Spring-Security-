package com.example.makeboard.repository;

import com.example.makeboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByName(String name);

    Member findByUserId(String userid);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.password=:password,m.password2=:password2 where m.userId=:userId")
    void updateByUserId(@Param("password") String password ,@Param("password2") String password2 ,@Param("userId") String userId);
}
