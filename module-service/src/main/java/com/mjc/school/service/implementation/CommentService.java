package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.CommentRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotation.Validate;
import com.mjc.school.service.constants.Constants;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.interfaces.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService implements BaseService<CommentDtoRequest, CommentDtoResponse, Long> {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(NewsRepository newsRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDtoResponse> readAll() {
        return CommentMapper.INSTANCE.listCommentToCommentDtoResponse(commentRepository.readAll());
    }

    @Transactional
    @Validate
    @Override
    public CommentDtoResponse readById(Long id) {
        if (commentRepository.existById(id)) {
            CommentModel commentModel = commentRepository.readById(id).get();
            return CommentMapper.INSTANCE.commentToCommentDtoResponse(commentModel);
        } else throw new NotFoundException(Constants.COMMENT_NOT_EXIST);
    }

    @Transactional
    @Validate
    @Override
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        if (newsRepository.existById(createRequest.getNewsModelId())) {
            CommentModel commentModel = CommentMapper.INSTANCE.commentDtoRequestToCommentModel(createRequest);
            NewsModel newsModel = newsRepository.readById(createRequest.getNewsModelId()).get();
            commentModel.setNewsModel(newsModel);
            commentModel.setContent(createRequest.getContent());
            CommentModel createdCommentModel = commentRepository.create(commentModel);
            return CommentMapper.INSTANCE.commentToCommentDtoResponse(createdCommentModel);
        } else throw new NotFoundException(Constants.NEWS_NOT_EXIST);
    }

    @Transactional
    @Validate
    @Override
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        if (newsRepository.existById(updateRequest.getNewsModelId())) {
            NewsModel newsModel = newsRepository.readById(updateRequest.getNewsModelId()).get();
            if (commentRepository.existById(updateRequest.getId())) {
                CommentModel commentModel = commentRepository.readById(updateRequest.getId()).get();
                commentModel.setContent(updateRequest.getContent());
                commentModel.setNewsModel(newsModel);
                CommentModel updatedCommentModel = commentRepository.update(commentModel);
                return CommentMapper.INSTANCE.commentToCommentDtoResponse(updatedCommentModel);
            } else throw new NotFoundException(Constants.COMMENT_NOT_EXIST);
        } else throw new NotFoundException(Constants.NEWS_NOT_EXIST);
    }

    @Transactional
    @Validate
    @Override
    public boolean deleteById(Long id) {
        if (commentRepository.existById(id)) {
            commentRepository.deleteById(id);
            return true;
        } else throw new NotFoundException(Constants.COMMENT_NOT_EXIST);
    }
}