package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.implementation.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentController implements BaseController<CommentDtoRequest, CommentDtoResponse, Long> {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    public List<CommentDtoResponse> readAll() {
        return commentService.readAll();
    }

    @Override
    public CommentDtoResponse readById(Long id) {
        return commentService.readById(id);
    }

    @Override
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        return commentService.create(createRequest);
    }

    @Override
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        return commentService.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return commentService.deleteById(id);
    }
}
