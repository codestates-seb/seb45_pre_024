package com.day24.preProject.QustionComment.repository;

import com.codestates.QustionComment.entity.QustionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QustionCommentRepository extends JpaRepository<QustionComment, Long> {
    Optional<QustionComment> findByQustionCommentCode(String QustionCommentCode);

    //    @Query(value = "FROM QustionComment c WHERE c.QustionCommentId = :QustionCommentId")
//    @Query(value = "SELECT * FROM QustionComment WHERE QustionComment_Id = :QustionCommentId", nativeQuery =true)
    @Query(value = "SELECT c FROM QustionComment c WHERE c.QustionCommentId = :QustionCommentId")
    Optional<QustionComment> findByQustionComment(long QustionCommentId);
}

