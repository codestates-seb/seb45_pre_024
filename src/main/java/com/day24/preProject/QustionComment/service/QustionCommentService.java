package com.day24.preProject.QustionComment.service;

import com.codestates.QustionComment.repository.QustionCommentRepository;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.day24.preProject.QustionComment.repository.QustionCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QustionCommentService {
    private final com.day24.preProject.QustionComment.repository.QustionCommentRepository QustionCommentRepository;

    public QustionCommentService(QustionCommentRepository QustionCommentRepository) {
        this.QustionCommentRepository = QustionCommentRepository;
    }

    public QustionComment createQustionComment(QustionComment QustionComment) {

        String QustionCommentCode = QustionComment.getQustionCommentCode().toUpperCase();


        verifyExistQustionComment(QustionCommentCode);
        QustionComment.setQustionCommentCode(QustionCommentCode);

        return QustionCommentRepository.save(QustionComment);
    }

    public QustionComment updateQustionComment(QustionComment QustionComment) {

        QustionComment findQustionComment = findVerifiedQustionComment(QustionComment.getQustionCommentId());

        Optional.ofNullable(QustionComment.getKorName())
                .ifPresent(korName -> findQustionComment.setKorName(korName));
        Optional.ofNullable(QustionComment.getEngName())
                .ifPresent(engName -> findQustionComment.setEngName(engName));
        Optional.ofNullable(QustionComment.getPrice())
                .ifPresent(price -> findQustionComment.setPrice(price));

        Optional.ofNullable(QustionComment.getQustionCommentStatus())
                .ifPresent(QustionCommentStatus -> findQustionComment.setQustionCommentStatus(QustionCommentStatus));

        return QustionCommentRepository.save(findQustionComment);
    }

    public QustionComment findQustionComment(long QustionCommentId) {
        return findVerifiedQustionCommentByQuery(QustionCommentId);
    }

    public Page<QustionComment> findQustionComments(int page, int size) {
        return QustionCommentRepository.findAll(PageRequest.of(page, size,
                Sort.by("QustionCommentId").descending()));
    }

    public void deleteQustionComment(long QustionCommentId) {
        QustionComment QustionComment = findVerifiedQustionComment(QustionCommentId);
        QustionCommentRepository.delete(QustionComment);
    }

    public QustionComment findVerifiedQustionComment(long QustionCommentId) {
        Optional<QustionComment> optionalQustionComment = QustionCommentRepository.findById(QustionCommentId);
        QustionComment findQustionComment =
                optionalQustionComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QustionComment_NOT_FOUND));

        return findQustionComment;
    }

    private void verifyExistQustionComment(String QustionCommentCode) {
        Optional<QustionComment> QustionComment = QustionCommentRepository.findByQustionCommentCode(QustionCommentCode);
        if(QustionComment.isPresent())
            throw new BusinessLogicException(ExceptionCode.QustionComment_CODE_EXISTS);
    }

    private QustionComment findVerifiedQustionCommentByQuery(long QustionCommentId) {
        Optional<QustionComment> optionalQustionComment = QustionCommentRepository.findByQustionComment(QustionCommentId);
        QustionComment findQustionComment =
                optionalQustionComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QustionComment_NOT_FOUND));

        return findQustionComment;
    }
}
