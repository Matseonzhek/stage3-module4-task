package com.mjc.school.controller.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.implementation.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "authors")
@Validated
public class AuthorRestController implements BaseRestController<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private final AuthorService authorService;
    private final ObjectMapper mapper;


    public AuthorRestController(AuthorService authorService, ObjectMapper mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }


    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<AuthorDtoResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<AuthorDtoResponse> authorDtoResponsePage = authorService.findAll(pageable);
        for (AuthorDtoResponse authorDtoResponse : authorDtoResponsePage) {
            Long id = authorDtoResponse.getId();
            Link selLink = linkTo(AuthorRestController.class).slash(id).withSelfRel();
            authorDtoResponse.add(selLink);
        }
        Link link = linkTo(AuthorRestController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(authorDtoResponsePage, link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<AuthorDtoResponse> readById(@PathVariable @NotBlank Long id) {
        AuthorDtoResponse authorDtoResponse = authorService.readById(id);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = {"application/json", "application/xml"})
    @Override
    public ResponseEntity<AuthorDtoResponse> create(
            @RequestBody @Valid AuthorDtoRequest createRequest) {
        AuthorDtoResponse authorDtoResponse = authorService.create(createRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = {"application/json", "application/xml"})
    @Override
    public ResponseEntity<AuthorDtoResponse> update(
            @PathVariable @NotBlank Long id,
            @RequestBody @Valid AuthorDtoRequest updateRequest) {
        AuthorDtoResponse authorDtoResponse = authorService.update(updateRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<AuthorDtoResponse> patch(
            @PathVariable  Long id,
            @RequestBody JsonPatch patch) {
        try {
            AuthorDtoResponse authorDtoResponse = authorService.readById(id);
            AuthorDtoRequest updateRequest = applyPatchAuthor(patch, authorDtoResponse);
            AuthorDtoResponse authorPatched = authorService.update(updateRequest);
            return new ResponseEntity<>(authorPatched, HttpStatus.OK);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Boolean> deleteById(
            @PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(authorService.deleteById(id), HttpStatus.NO_CONTENT);
    }

    private AuthorDtoRequest applyPatchAuthor(JsonPatch patch, AuthorDtoResponse authorDtoResponse) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(mapper.convertValue(authorDtoResponse, JsonNode.class));
        return mapper.treeToValue(patched, AuthorDtoRequest.class);
    }
}
