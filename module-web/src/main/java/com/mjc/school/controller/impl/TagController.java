package com.mjc.school.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/tags")
public class TagController implements BaseRestController<TagDtoRequest, TagDtoResponse, Long> {

    private final TagService tagService;
    private final ObjectMapper mapper;

    public TagController(TagService tagService, ObjectMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
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
            Link selfLink = linkTo(TagController.class).slash(id).withSelfRel();
            tagDtoResponse.add(selfLink);
        }
        Link link = linkTo(TagController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(tagDtoResponsePage, link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<TagDtoResponse> readById(
            @PathVariable @NotBlank Long id) {
        TagDtoResponse tagDtoResponse = tagService.readById(id);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<TagDtoResponse> create(
            @RequestBody @Valid TagDtoRequest createRequest) {
        TagDtoResponse tagDtoResponse = tagService.create(createRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Override
    public ResponseEntity<TagDtoResponse> update(
            @PathVariable @NotBlank Long id,
            @RequestBody @Valid TagDtoRequest updateRequest) {
        TagDtoResponse tagDtoResponse = tagService.update(updateRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @Override
    public ResponseEntity<TagDtoResponse> patch(
            @PathVariable @NotBlank Long id,
            @RequestBody JsonPatch patch) {
        try {
            TagDtoResponse tagDtoResponse = tagService.readById(id);
            TagDtoRequest tagDtoRequest = applyPatchAuthor(patch, tagDtoResponse);
            TagDtoResponse tagPatched = tagService.update(tagDtoRequest);
            return new ResponseEntity<>(tagPatched, HttpStatus.OK);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Boolean> deleteById(
            @PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(tagService.deleteById(id), HttpStatus.NO_CONTENT);
    }

    private TagDtoRequest applyPatchAuthor(JsonPatch patch, TagDtoResponse tagDtoResponse) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(mapper.convertValue(tagDtoResponse, JsonNode.class));
        return mapper.treeToValue(patched, TagDtoRequest.class);
    }
}
