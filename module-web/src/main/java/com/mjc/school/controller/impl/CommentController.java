package com.mjc.school.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/comments")
public class CommentController implements BaseRestController<CommentDtoRequest, CommentDtoResponse, Long> {


    private final CommentService commentService;
    private final ObjectMapper mapper;

    public CommentController(CommentService commentService, ObjectMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
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
            Link selfLink = linkTo(CommentController.class).slash(id).withSelfRel();
            commentDtoResponse.add(selfLink);
        }
        Link link = linkTo(CommentController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(commentDtoResponsePage, link), HttpStatus.OK);
    }

    @GetMapping(value = "news/{id}/comments")
    public ResponseEntity<List<CommentDtoResponse>> readCommentsByNewsId(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(commentService.readCommentsByNewsId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<CommentDtoResponse> readById(
            @PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(commentService.readById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<CommentDtoResponse> create(@RequestBody @Valid CommentDtoRequest createRequest) {
        CommentDtoResponse commentDtoResponse = commentService.create(createRequest);
        return new ResponseEntity<>(commentDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<CommentDtoResponse> update(
            @PathVariable @NotBlank Long id,
            @RequestBody @Valid CommentDtoRequest updateRequest) {
        return new ResponseEntity<>(commentService.update(updateRequest), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @Override
    public ResponseEntity<CommentDtoResponse> patch(
            @PathVariable @NotBlank Long id,
            @RequestBody JsonPatch patch) {
        try {
            CommentDtoResponse commentDtoResponse = commentService.readById(id);
            CommentDtoRequest updatedComment = applyPatchAuthor(patch, commentDtoResponse);
            CommentDtoResponse commentPatched = commentService.patch(updatedComment);
            return new ResponseEntity<>(commentPatched, HttpStatus.OK);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping(value = "/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable @NotBlank Long id) {
        commentService.deleteById(id);
    }

    private CommentDtoRequest applyPatchAuthor(JsonPatch patch, CommentDtoResponse commentDtoResponse) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(mapper.convertValue(commentDtoResponse, JsonNode.class));
        return mapper.treeToValue(patched, CommentDtoRequest.class);
    }
}
