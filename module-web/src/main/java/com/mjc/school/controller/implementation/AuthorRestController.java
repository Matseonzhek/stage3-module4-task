package com.mjc.school.controller.implementation;

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
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "authors")
public class AuthorRestController implements BaseRestController<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
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
    public ResponseEntity<AuthorDtoResponse> readById(@PathVariable Long id) {
        AuthorDtoResponse authorDtoResponse = authorService.readById(id);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = {"application/json", "application/xml"})
    @Override
    public ResponseEntity<AuthorDtoResponse> create(@RequestBody AuthorDtoRequest createRequest) {
        AuthorDtoResponse authorDtoResponse = authorService.create(createRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = {"application/json", "application/xml"})
    @Override
    public ResponseEntity<AuthorDtoResponse> update(
            @PathVariable Long id,
            @RequestBody AuthorDtoRequest updateRequest) {
        AuthorDtoResponse authorDtoResponse = authorService.update(updateRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
