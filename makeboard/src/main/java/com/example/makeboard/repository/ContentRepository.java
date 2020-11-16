package com.example.makeboard.repository;

import com.example.makeboard.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content,Long> {

    List<Content> findAll();

    Optional<Content> findById(Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Content c set c.member.name=:name , c.subject=:subject , c.text=:text  where c.id=:id")
    void UpdateBoard(@Param("id") Long id,@Param("name") String name,@Param("subject") String subject,@Param("text")String text);
}
