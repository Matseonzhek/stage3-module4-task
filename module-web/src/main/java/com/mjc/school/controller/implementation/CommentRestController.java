package com.mjc.school.controller.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mjc.school.controller.BaseRestController;
import com.mjc.school.controller.constants.Constants;
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

import static com.mjc.school.controller.constants.Constants.ID_NOT_NULL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/comments")
public class CommentRestController implements BaseRestController<CommentDtoRequest, CommentDtoResponse, Long> {


    private final CommentService commentService;
    private final ObjectMapper mapper;

    public CommentRestController(CommentService commentService, ObjectMapper mapper) {
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
            Link selfLink = linkTo(CommentRestController.class).slash(id).withSelfRel();
            commentDtoResponse.add(selfLink);
        }
        Link link = linkTo(CommentRestController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(commentDtoResponsePage, link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<CommentDtoResponse> readById(
            @PathVariable @NotBlank(message = ID_NOT_NULL) Long id) {
        return new ResponseEntity<>(commentService.readById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<CommentDtoResponse> create(@RequestBody @Valid CommentDtoRequest createRequest) {
        CommentDtoResponse commentDtoResponse = commentService.create(createRequest);
        return new ResponseEntity<>(commentDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<CommentDtoResponse> update(
            @PathVariable @NotBlank(message = ID_NOT_NULL) Long id,
            @RequestBody @Valid CommentDtoRequest updateRequest) {
        return new ResponseEntity<>(commentService.update(updateRequest), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @Override
    public ResponseEntity<CommentDtoResponse> patch(
            @PathVariable @NotBlank(message = ID_NOT_NULL)  Long id,
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
    public ResponseEntity<Boolean> deleteById(
            @PathVariable @NotBlank(message = ID_NOT_NULL)  Long id) {
        boolean result = commentService.deleteById(id);
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }

    private CommentDtoRequest applyPatchAuthor(JsonPatch patch, CommentDtoResponse commentDtoResponse) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(mapper.convertValue(commentDtoResponse, JsonNode.class));
        return mapper.treeToValue(patched, CommentDtoRequest.class);
    }
}
