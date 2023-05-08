package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.implementation.TagService;
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
@RequestMapping(value = "/tags")
public class TagRestController implements BaseRestController<TagDtoRequest, TagDtoResponse, Long> {

    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<TagDtoResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<TagDtoResponse> tagDtoResponsePage = tagService.findAll(pageable);
        for (TagDtoResponse tagDtoResponse : tagDtoResponsePage) {
            Long id = tagDtoResponse.getId();
            Link selfLink = linkTo(TagRestController.class).slash(id).withSelfRel();
            tagDtoResponse.add(selfLink);
        }
        Link link = linkTo(TagRestController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(tagDtoResponsePage, link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<TagDtoResponse> readById(@PathVariable Long id) {
        TagDtoResponse tagDtoResponse = tagService.readById(id);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<TagDtoResponse> create(
            @RequestBody TagDtoRequest createRequest) {
        TagDtoResponse tagDtoResponse = tagService.create(createRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Override
    public ResponseEntity<TagDtoResponse> update(
            @PathVariable Long id,
            @RequestBody TagDtoRequest updateRequest) {
        TagDtoResponse tagDtoResponse = tagService.update(updateRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
