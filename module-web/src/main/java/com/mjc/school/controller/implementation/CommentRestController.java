package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.implementation.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/comments")
public class CommentRestController implements BaseRestController<CommentDtoRequest, CommentDtoResponse, Long> {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<CommentDtoResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "content") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<CommentDtoResponse> commentDtoResponsePage = commentService.findAll(pageable);
        for (CommentDtoResponse commentDtoResponse : commentDtoResponsePage) {
            Long id = commentDtoResponse.getId();
            Link selfLink = linkTo(CommentRestController.class).slash(id).withSelfRel();
            commentDtoResponse.add(selfLink);
        }
        Link link = linkTo(CommentRestController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(commentDtoResponsePage, link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<CommentDtoResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.readById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<CommentDtoResponse> create(@RequestBody CommentDtoRequest createRequest) {
        CommentDtoResponse commentDtoResponse = commentService.create(createRequest);
        return new ResponseEntity<>(commentDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<CommentDtoResponse> update(
            @PathVariable Long id,
            @RequestBody CommentDtoRequest updateRequest) {
        return new ResponseEntity<>(commentService.update(updateRequest),HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
